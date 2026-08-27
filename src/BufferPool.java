import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BufferPool {
    private FileManager fileManager;
    private Frame[] frames;
    private Map<Integer, Integer> pageTable;
    /** Holds frames that have never had a page loaded into them, or have been reset() after eviction. */
    private Deque<Integer> freeList;
    private final int POOL_SIZE;

    public BufferPool(){
        this.POOL_SIZE = 5;
        this.fileManager = new FileManager();
        this.frames = new Frame[POOL_SIZE];
        this.pageTable = new HashMap<>();
        this.freeList = new ArrayDeque<>();

        for (int i = 0; i < POOL_SIZE; i++) {
            frames[i] = new Frame(ByteBuffer.allocate(FileManager.PAGE_SIZE));
            freeList.add(i);
        }
    }

    public Frame fetchPage(int pageID) throws IOException {
        boolean isCached = pageTable.containsKey(pageID);
        // Is cached (in page table)
        if (isCached){
            int frameIndex = pageTable.get(pageID);
            Frame currentFramePointer = frames[frameIndex];
            currentFramePointer.pin();
            return currentFramePointer;
        }
        // Cache miss, but free frame available
        else if (!freeList.isEmpty()){
            int freeIndex = freeList.pop();
            Frame currentFramePointer = frames[freeIndex];
            fileManager.readPage(pageID, currentFramePointer.getBuffer());
            currentFramePointer.setPageID(pageID);
            currentFramePointer.pin();
            pageTable.put(pageID, freeIndex);
            return currentFramePointer;
        }
        // Cache miss, no free frames available (not all pinned)
    }

    public void unpinFrame(int pageID){
        frame.unpin();
    }
}
