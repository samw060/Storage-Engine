import java.awt.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class BufferPool {
    private Frame[] frames;
    private Map<Integer, Integer> pageTable;
    private boolean[] freeList;

    public BufferPool(){
        frames = new Frame[5];
        pageTable = new HashMap<>();
        freeList = new boolean[5];
    }

}
