package FileIO;

import Utility.Converter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataOps {
    public static MetaData extractMetaData(String filepath){
        MetaData metaData = new MetaData();
        int MAX_COLS = 0;
        int row_size  = 0;
        int col_size = 0;
        try {
            FileInputStream inputStream = new FileInputStream(filepath);

            byte[] signature = new byte[8];
            byte [] rc_data = new byte[4];

            inputStream.readNBytes(signature,0,8);
            inputStream.readNBytes(rc_data, 0, 4);
            MAX_COLS = Converter.byteArrayToInt(rc_data);
            inputStream.readNBytes(rc_data, 0,4);
            row_size = Converter.byteArrayToInt(rc_data);
            inputStream.readNBytes(rc_data, 0, 4);
            col_size = Converter.byteArrayToInt(rc_data);

            int []bytes_per_column = new int[col_size];
            char []col_type = new char[col_size];
            byte []col_data = new byte[4]; //4 bytes per int
            String []column_names = new String[col_size];
            if(checkIsFileValid(signature, metaData.SIGNATURE) == 0){

                for(int i = 0; i < col_size; i++) {
                    int value = 0;
                    for (int j = 0; j < 4; j++) {
                        col_data[j] = (byte) inputStream.read();
                    }
                    value = Converter.byteArrayToInt(col_data);
                    bytes_per_column[i] = value;
                }
                for(int i = 0; i < col_size; i++){
                    char value = (char) inputStream.read();
                    col_type[i] = value;
                }
                String str = "";
                int count_char = 0;
                for(int i = 0; i < col_size; i++) {
                    str = "";
                    for(int j=0; j<32; j++){
                        char c = (char)inputStream.read();
                        if(c == '\0'){
                            count_char ++;
                            break;
                        }
                        str = str + c;
                        count_char ++;
                    }
                    column_names[i] = str;
                }
                metaData.row_size = row_size;
                metaData.col_size = col_size;
                metaData.bytes_per_column = bytes_per_column;
                metaData.col_type = col_type;
                metaData.column_names = column_names;
                metaData.metaDataSize = 20 + col_size * 4 + col_size * 1 + count_char + 4; //(byte,int,int,int) + (no_of_columns * int) + (no_of_columns * char) + (All char for column_names) + (int)
                inputStream.close();
            }
            else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return metaData;
    }

    public static List<Byte> extractBytesFromMetaData(MetaData metaData){
        List<Byte> mData = new ArrayList<>();
        for(int i = 0; i<8; i++){
            mData.add(metaData.SIGNATURE[i]);
        }
        byte[] b;
        b = Converter.intToByteArray(metaData.MAX_COLS);
        for(int i=0; i<b.length; i++){
            mData.add(b[i]);
        }
        b = Converter.intToByteArray(metaData.row_size);
        for(int i=0; i<b.length; i++){
            mData.add(b[i]);
        }
        b = Converter.intToByteArray(metaData.col_size);
        for(int i=0; i<b.length; i++){
            mData.add(b[i]);
        }

        for(int i=0; i<metaData.bytes_per_column.length; i++){
            b = Converter.intToByteArray(metaData.bytes_per_column[i]);
            for(int j=0; j<b.length; j++){
                mData.add(b[j]);
            }
        }
        for(int i=0; i<metaData.col_type.length; i++){
            byte bb = (byte)metaData.col_type[i];
            mData.add(bb);
        }
        for(int i=0; i<metaData.column_names.length; i++){
            List<Byte> bb = Converter.stringToByteArray(metaData.column_names[i]);
            for(int j=0; j<bb.size(); j++){
                mData.add(bb.get(j));
            }
        }

        b = Converter.intToByteArray(metaData.metaDataSize);
        for(int i=0; i<b.length; i++){
            mData.add(b[i]);
        }
        System.out.println(mData);
        return mData;
    }

    private static int checkIsFileValid(byte []signature, byte []actualSignature){
        for(int i=0; i<8; i++){
            if(signature[i] != actualSignature[i])
                return -1;
        }
        return 0;
    }
}
