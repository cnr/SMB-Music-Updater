package smb.converter.dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

// An extractor for Super Meat Boy .dat files. The input is assumed to be well-formed
// See datformat.md for more information
public class DatExtractor {

    public static void extract(Path datFilePath, Path outDirectoryPath) throws IOException {
        File outDirectoryFile = outDirectoryPath.toFile();
        if (!outDirectoryFile.exists() && !outDirectoryFile.mkdir()) {
            throw new IOException("Output directory doesn't exist and could not be created");
        }

        // Read file into a buffer

        ByteBuffer buf;
        try (FileChannel in = new FileInputStream(datFilePath.toFile()).getChannel()) {
            buf = ByteBuffer.allocateDirect((int)in.size()).order(ByteOrder.LITTLE_ENDIAN);
            in.read(buf);
            buf.flip();
        }

        // --- Header

        int numDirectories = buf.getInt();
        // Ignore directory metadata (qword per directory)
        for (int i = 0; i < numDirectories; i++) {
            buf.getLong();
        }

        int numFiles = buf.getInt();
        FileMetadata[] fileMetadata = new FileMetadata[numFiles];
        for (int i = 0; i < numFiles; i++) {
            fileMetadata[i] = new FileMetadata(buf.getInt(), buf.getInt());
            buf.getInt(); // Discard directory ID
        }

        String[] dirNames;
        String[] fileNames;
        {
            byte[] dirNamesStringBytes = new byte[buf.getInt()];
            byte[] fileNamesStringBytes = new byte[buf.getInt()];
            buf.get(dirNamesStringBytes)
               .get(fileNamesStringBytes);
            dirNames = new String(dirNamesStringBytes, Charset.forName("UTF8")).split("\0");
            fileNames = new String(fileNamesStringBytes, Charset.forName("UTF8")).split("\0");
        }


        // Create directories

        for (String dirName : dirNames) {
            File dir = outDirectoryPath.resolve(Paths.get(dirName)).toFile();
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("Couldn't create directory contained in data: " + dirName);
            }
        }


        // --- Data

        for (int i = 0; i < numFiles; i++) {
            FileMetadata metadata = fileMetadata[i];
            String fileName = fileNames[i];

            Path filePath = outDirectoryPath.resolve(Paths.get(fileName));
            try (FileChannel out = new FileOutputStream(filePath.toFile()).getChannel()) {
                buf.position(metadata.offset);
                buf.limit(buf.position() + metadata.size);
                out.write(buf);
            }
        }
    }

    private static final class FileMetadata {
        public final int offset;
        public final int size;

        FileMetadata(int offset, int size) {
            this.offset = offset;
            this.size = size;
        }
    }
}
