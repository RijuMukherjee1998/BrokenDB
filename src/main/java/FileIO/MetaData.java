package FileIO;

public class MetaData
{
    public static  final byte []SIGNATURE = {127, 127, 127, 127, 0, 0, 0, 0};
    public static  final int MAX_COLS = 1024;
    public int row_size;
    public int col_size;
    public int []bytes_per_column;
    public char []col_type;
    public String []column_names;
    int metaDataSize; //in bytes
}
