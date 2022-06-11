package Utility;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Converter {

    public static byte[] shortToByteArray(short value) {
        return  ByteBuffer.allocate(2).putShort(value).array();
    }

    public static short byteArrayToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }


    public static byte[] intToByteArray(int value) { return  ByteBuffer.allocate(4).putInt(value).array(); }

    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] longToByteArray(int value) { return  ByteBuffer.allocate(8).putLong(value).array(); }

    public static long byteArrayToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static byte[] floatToByteArray(float value) {
        return  ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static float byteArrayToFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }

    public static byte[] doubleToByteArray(double value) {
        return  ByteBuffer.allocate(8).putDouble(value).array();
    }

    public static double byteArrayToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static byte[] charToByteArray(char value) { return  ByteBuffer.allocate(1).putChar(value).array(); }

    public static double byteArrayToChar(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getChar();
    }

    public static ArrayList<Byte> stringToByteArray(String value)
    {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for(int i = 0; i<value.length(); i++){
            byte b = (byte)value.charAt(i);
            bytes.add(b);
        }
        return bytes;
    }

    public static String byteArrayToString(byte[] bytes){
        String s = "";
        for (int i=0; i<bytes.length; i++){
            s += (char)bytes[i];
        }
        return  s;
    }

}
