package FileIO;

import Utility.Converter;
import org.json.JSONObject;


import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileOps
{

    public int fileRead(String filepath)
    {
        MetaData metaData = MetaDataOps.extractMetaData(filepath);
        if(metaData == null) {
            System.out.println("File Type not supported");
        }
        else {
            System.out.println("MetaData Values");
            System.out.println(Arrays.toString(metaData.SIGNATURE));
            System.out.println(metaData.MAX_COLS);
            System.out.println(metaData.row_size);
            System.out.println(metaData.col_size);
            System.out.println(Arrays.toString(metaData.bytes_per_column));
            System.out.println(Arrays.toString(metaData.col_type));
            System.out.println(Arrays.toString(metaData.column_names));
            System.out.println(metaData.metaDataSize);

            try {
                FileInputStream inputStream = new FileInputStream(filepath);
                inputStream.skip(metaData.metaDataSize);
                JSONObject obj = new JSONObject();

                for (int r = 0; r < metaData.row_size; r++){
                    for (int c = 0; c < metaData.col_size; c++){
                        int bpc = metaData.bytes_per_column[c];
                        char type = metaData.col_type[c];
                        byte[] b = new byte[bpc];
                        inputStream.readNBytes(b,0,bpc);
                        switch (type){
                            case 'S':
                                short s = Converter.byteArrayToShort(b);
                                obj.put(metaData.column_names[c],s);
                                break;
                            case 'I':
                                int i = Converter.byteArrayToInt(b);
                                obj.put(metaData.column_names[c],i);
                                break;
                            case 'L':
                                long l = Converter.byteArrayToLong(b);
                                obj.put(metaData.column_names[c],l);
                                break;
                            case 'F':
                                float f = Converter.byteArrayToFloat(b);
                                obj.put(metaData.column_names[c],f);
                                break;
                            case 'D':
                                double d = Converter.byteArrayToDouble(b);
                                obj.put(metaData.column_names[c], d);
                                break;
                            case 'N':
                                String ss = Converter.byteArrayToString(b);
                                obj.put(metaData.column_names[c], ss);
                                break;

                            default:
                                throw new IllegalStateException("Unexpected value: " + type);
                        }

                    }
                    System.out.println(obj);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    public int fileWrite(String filepath, byte[] data, MetaData metaData){
        byte[] tableData = new byte[0];
        File file = new File(filepath);
        if(!file.exists()) {
            List<Byte> out;
            out = MetaDataOps.extractBytesFromMetaData(metaData);
            tableData = new byte[out.size() + data.length];
            for (int i = 0; i < out.size(); i++) {
                tableData[i] = out.get(i);
            }
            for (int i = 0; i < data.length; i++) {
                tableData[out.size() + i] = data[i];
            }
        } else {
            if(MetaDataOps.extractMetaData(filepath) != null) {
                tableData = new byte[data.length];
                for (int i = 0; i < data.length; i++) {
                    tableData[i] = data[i];
                }
            }
            else{
                System.out.println("UnSupported File");
            }
        }
    
        try {
            if(tableData.length != 0) {
                FileOutputStream outputStream = new FileOutputStream(filepath,true);
                outputStream.write(tableData);
                outputStream.close();
                System.out.println("Data written to file");
            }
            else {
                System.out.println("No Data to Write");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


