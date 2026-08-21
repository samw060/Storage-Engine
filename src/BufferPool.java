import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BufferPool {
    private FileManager fileManager;
    private Frame[] frames;
    private Map<Integer, Integer> pageTable;
    private Deque<Integer> freeList;
    private int POOL_SIZE;

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


}
