import java.nio.ByteBuffer;

public class Frame {

    ByteBuffer buffer;
    boolean isPinned;
    boolean isDirty;
    int pageID;

    public Frame(ByteBuffer buffer){
        this.buffer = buffer;
        this.isPinned = false;
        this.isDirty = false;
        this.pageID = -1;
    }

    public ByteBuffer getBuffer(){
        return buffer;
    }

    public void writeAt(int offset, ByteBuffer data) {
        buffer.put(offset, data, 0, data.remaining());
        isDirty = true;
    }

    public boolean isPinned(){
        return isPinned;
    }

    public void pin(){
        this.isPinned = true;
    }

    public void unpin(){
        this.isPinned = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void markDirty(){
        this.isDirty = true;
    }

    public void clearDirty(){
        this.isDirty = false;
    }

    public void setPageID(int pageID){
        this.pageID = pageID;
    }

    public int getPageID(){
        return this.pageID;
    }

    public void reset(){
        this.pageID = -1;
        this.isDirty = false;
    }

}
