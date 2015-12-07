import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Unpacker {

    public static void main(String[] args) throws Exception {
        File out = new File("out/");
        File file = new File(args[0]);

        System.out.println("Unpacking sounds...");

        DataInputStream in = new DataInputStream(new FileInputStream(file));

        int numDirectories = readInt(in);
        System.out.println("Number of directories: " + numDirectories);
        for (int i = 0; i < numDirectories; i++) {
            readLong(in);
        }

        int numFiles = readInt(in);
        System.out.println("Number of files: " + numFiles);

        int[] fileSizes = new int[numFiles];
        for (int i = 0; i < numFiles; i++) {
            readInt(in);
            fileSizes[i] = readInt(in);
            readInt(in);
        }

        int dirNamesLength = readInt(in);

        int fileNamesLength = readInt(in);

        String[] dirNames = new String(read(in, dirNamesLength), Charset.forName("UTF8")).split("\0");
        String[] fileNames = new String(read(in, fileNamesLength), Charset.forName("UTF8")).split("\0");
        System.out.println("Directory names length: " + dirNames.length);
        System.out.println("File names length: " + fileNames.length);


        // Extract files
        for (String dirName : dirNames) {
            new File(out, dirName).mkdirs();
        }

        for (int i = 0; i < fileNames.length; i++) {
            Files.write(new File(out, fileNames[i]).toPath(), read(in, fileSizes[i]), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        in.close();
    }

    private static byte[] read(DataInputStream in, int numBytes) throws IOException {
        byte[] bytes = new byte[numBytes];
        in.readFully(bytes);
        return bytes;
    }

    private static int readInt(DataInputStream in) throws IOException {
        return (in.readByte() & 0xFF)       |
               (in.readByte() & 0xFF) << 8  |
               (in.readByte() & 0xFF) << 16 |
               (in.readByte() & 0xFF) << 24 ;
    }

    private static long readLong(DataInputStream in) throws IOException {
        return (in.readByte() & 0xFFL)       |
               (in.readByte() & 0xFFL) << 8  |
               (in.readByte() & 0xFFL) << 16 |
               (in.readByte() & 0xFFL) << 24 |
               (in.readByte() & 0xFFL) << 32 |
               (in.readByte() & 0xFFL) << 40 |
               (in.readByte() & 0xFFL) << 48 |
               (in.readByte() & 0xFFL) << 56 ;
    }
}
