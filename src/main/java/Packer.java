import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Packer {

    public static void main(String[] args) throws Exception {
        File outFile = new File("out.dat");
        Path dir = new File(args[0]).toPath();

        DataOutputStream out = new DataOutputStream(new FileOutputStream(outFile));


        List<Path> directories = new ArrayList<>();
        List<Path> files = new ArrayList<>();

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) {
                if (!path.equals(dir)) {
                    directories.add(path);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                if (!path.toFile().getName().startsWith(".")) {
                    files.add(path);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        // Real file (ints): directory.size(), 0, 0, 1, 53
        writeInt(out, directories.size());
        for (int i = 0; i < directories.size(); i++) {
            writeLong(out, 0);
        }

        writeInt(out, files.size());

        int offset = 21439; // Need to un-hardcode this
        for (Path path : files) {
            int size = (int) Files.size(path);
            writeInt(out, offset);
            writeLong(out, size);
            offset += size;
        }

        String dirNames = concatPaths(dir, directories);
        String fileNames = concatPaths(dir, files);

        writeInt(out, dirNames.length());
        writeInt(out, fileNames.length());

        for (int i = 0; i < dirNames.length(); i++) {
            out.writeByte(dirNames.charAt(i));
        }
        for (int i = 0; i < fileNames.length(); i++) {
            out.writeByte(fileNames.charAt(i));
        }

        for (Path path : files) {
            byte[] bytes = Files.readAllBytes(path);
            out.write(bytes, 0, bytes.length);
        }

        out.close();
    }

    private static void writeLong(DataOutputStream out, long val) throws IOException {
        out.write((int)((val & 0x00000000000000FFL)      ));
        out.write((int)((val & 0x000000000000FF00L) >>  8));
        out.write((int)((val & 0x0000000000FF0000L) >> 16));
        out.write((int)((val & 0x00000000FF000000L) >> 24));
        out.write((int)((val & 0x000000FF00000000L) >> 32));
        out.write((int)((val & 0x0000FF0000000000L) >> 40));
        out.write((int)((val & 0x00FF000000000000L) >> 48));
        out.write((int)((val & 0xFF00000000000000L) >> 56));
    }

    private static void writeInt(DataOutputStream out, int val) throws IOException {
        out.write((val & 0x000000FF)      );
        out.write((val & 0x0000FF00) >>  8);
        out.write((val & 0x00FF0000) >> 16);
        out.write((val & 0xFF000000) >> 24);
    }

    private static String concatPaths(Path base, List<Path> paths) {
        return paths.stream().map(base::relativize)
                             .map(Object::toString)
                             .collect(Collectors.joining("\0"));
    }
}
