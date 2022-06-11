import FileIO.FileOps;
import FileIO.MetaData;
import Utility.Converter;

import java.util.ArrayList;

public class Main
{
    public static void main(String args[])
    {
        System.out.println("____MAIN CLASS____");

        FileOps fops = new FileOps();

        MetaData metaData = new MetaData();
        metaData.row_size = 1;
        metaData.col_size = 3;
        int[] b_p_c = {4,10,4};
        char[] col_type = {'I','N','I'};
        String[] col_names = {"ID\0","NAME\0","SALARY\0"};
        metaData.bytes_per_column = b_p_c;
        metaData.col_type = col_type;
        metaData.column_names = col_names;

        byte[] id = Converter.intToByteArray(1);
        ArrayList<Byte> name = Converter.stringToByteArray("MUKESH\0\0\0\0");
        byte[] salary = Converter.intToByteArray(5000);
        byte[] data = new byte[id.length + name.size() + salary.length];
        for (int i = 0; i< id.length; i++) {
            data[i] = id[i];
        }
        for (int i = 0; i< name.size(); i++) {
            data[id.length + i] = name.get(i);
        }
        for (int i = 0; i< salary.length; i++) {
            data[id.length + name.size() + i] = salary[i];
        }

        fops.fileWrite("C:\\Users\\ANT-PC\\Desktop\\FileTest\\mydb.rdb", data, metaData);

        fops.fileRead("C:\\Users\\ANT-PC\\Desktop\\FileTest\\mydb.rdb");
    }
}
