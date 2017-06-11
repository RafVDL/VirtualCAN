package be.rafvdl.virtualcan.bus;

public class Message {

    private final int id;
    private final int length;
    private final byte[] data;

    public Message(int id, int length, byte[] data) {
        this.id = id;
        this.length = length;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }

}
