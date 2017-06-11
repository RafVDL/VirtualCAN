package be.rafvdl.virtualcan.util;

public class BinaryUtil {

    public static boolean isBitHigh(byte data, int bit) {
        return ((data >> bit) & 0x1) == 0x1;
    }

}
