import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileManager {
    /** Number of bytes per page. */
    final int PAGE_SIZE = 4096;

    /** The java.nio way of writing to files. */
    private final FileChannel channel;

    /** Opens or creates a new db file if one doesn't exist. */
    public FileManager(){
        Path path = Path.of("database.db");
        try {
            if (!Files.exists(path)) {
                channel = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            } else {
                channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes byte buffer to disk at the page ID spot
     * @param bytes to write to disk.
     * @param pageID of where to write bytes.
     * @throws IOException if an I/O error occurs when writing to disk.
     */
    public void writePage(ByteBuffer bytes, int pageID) throws IOException {
        if (bytes.remaining() != PAGE_SIZE) {
            throw new IOException("Buffer is not a full page: " + bytes.remaining() + " bytes");
        }
        long position = (long) PAGE_SIZE * pageID;
        int written = channel.write(bytes, position);
        if (written != PAGE_SIZE){
            throw new IOException("Didn't write correct number of bytes.");
        }
    }

    /**
     * Gets the page bytes from disk.
     * @param pageID which page you want to grab.
     * @return the page bytes wrapped in a byte buffer.
     * @throws IOException if an I/O error occurs while reading from the file channel.
     */
    public ByteBuffer readPage(int pageID, ByteBuffer frame) throws IOException{
        frame.clear();
        long position = (long) pageID * PAGE_SIZE;
        int bytesRead = channel.read(frame, position);
        if (bytesRead == -1){
            throw new IOException("Tried to read past the end of the file.");
        }else if (bytesRead != PAGE_SIZE){
            throw new IOException("Didn't read full page");
        }
        frame.flip();
        return frame;
    }
}
