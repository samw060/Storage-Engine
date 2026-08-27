import java.nio.ByteBuffer;

public class Frame {
    /** Pointer to the byte buffer for this frame. */
    ByteBuffer buffer;

    /** Is if the frame is currently in use. */
    boolean isPinned;

    /** If the page in this frame needs writing to disk before being removed from the buffer pool. */
    boolean isDirty;

    /** The pageID of the page in this frame. */
    int pageID;

    /**
     * Constructs a frame for the page in the buffer.
     * @param buffer is the bytes of the page in the frame.
     */
    public Frame(ByteBuffer buffer){
        this.buffer = buffer;
        this.isPinned = false;
        this.isDirty = false;
        this.pageID = -1;
    }

    /**
     * @return the page in the frame.
     */
    public ByteBuffer getBuffer(){
        return buffer;
    }

    /**
     * Writes to the pages ByteBuffer, used for updating tuples.
     * @param offset where to add the new data.
     * @param data what to add to the page.
     */
    public void writeAt(int offset, ByteBuffer data) {
        buffer.put(offset, data, 0, data.remaining());
        isDirty = true;
    }

    /**
     * @return if the page is in use (pinned).
     */
    public boolean isPinned(){
        return isPinned;
    }

    /** Sets pinned to be true so the page is in use. */
    public void pin(){
        this.isPinned = true;
    }

    /** Sets pinned to false, so the page is no longer in use. */
    public void unpin(){
        this.isPinned = false;
    }

    /**
     * @return if bytes have been changed since being written to disk.
     */
    public boolean isDirty() {
        return isDirty;
    }

    /** Sets dirty so bytes have been changed since being written to disk last. */
    public void markDirty(){
        this.isDirty = true;
    }

    /** Removes dirty flag as bytes have been written back to disk since last change. */
    public void clearDirty(){
        this.isDirty = false;
    }

    /**
     * Sets the pageID of the page in the frame.
     * @param pageID of the page in the frame.
     */
    public void setPageID(int pageID){
        this.pageID = pageID;
    }

    /**
     * @return pageID of the page currently in the frame.
     */
    public int getPageID(){
        return this.pageID;
    }

    /** Resets the page ID, and the dirty flag. */
    public void reset(){
        this.pageID = -1;
        this.isDirty = false;
    }

}
