import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileManager {
    /** Number of bytes per page. */
    int PAGE_SIZE = 4096;

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

}
