package smb.converter.dat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

// A packer for Super Meat Boy .dat files. This creates .dat files
// identical to those created by the internal tool
public class DatPacker {
    // To directly emulate the original tool's behavior, we use a String comparator that places
    // underscores *after* alpha characters, as opposed to Java's default, which is *before*
    private static final Comparator<Path> DAT_ENTRY_ORDERING = Comparator.comparing(Path::toString, (s1, s2) -> {
        int min = Math.min(s1.length(), s2.length());

        for (int i = 0; i < min; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            if (c1 != c2) {
                c1 = Character.toUpperCase(c1);
                c2 = Character.toUpperCase(c2);

                if (c1 != c2) {
                    return c1 == '_' ?  1 :
                           c2 == '_' ? -1 :
                           c1 - c2;
                }
            }
        }
        return s1.length() - s2.length();
    });

    public static void pack(Path dirInputPath, Path datOutputPath) throws IOException {
        Map<Path, DirectoryMetadata> directories = new TreeMap<>(DAT_ENTRY_ORDERING);
        Map<Path, FileMetadata> files = new TreeMap<>(DAT_ENTRY_ORDERING);

        Files.walkFileTree(dirInputPath, new SimpleFileVisitor<Path>() {
            private int foundFiles = -1; // Used for directory metadata
            private int directoryIdCounter = -1;
            private final List<Integer> directoryIdStack = new ArrayList<>(); // Current directory ID can be found at index 0

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                directoryIdStack.add(0, directoryIdCounter++);
                if (dir.equals(dirInputPath)) {
                    return FileVisitResult.CONTINUE;
                }
                directories.put(dir, new DirectoryMetadata(Math.max(0, foundFiles)
                                                         , dirInputPath.relativize(dir).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                directoryIdStack.remove(0);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.put(file, new FileMetadata((int)Files.size(file)
                                               , directoryIdStack.get(0)
                                               , dirInputPath.relativize(file).toString()));
                foundFiles++;
                return FileVisitResult.CONTINUE;
            }
        });

        byte[] directoryBytes = directories.values().stream()
                                                    .map(metadata -> metadata.relativePathString)
                                                    .collect(Collectors.joining("\0"))
                                                    .concat("\0")
                                                    .getBytes(Charset.forName("UTF8"));
        byte[] joinedFiles = files.values().stream()
                                           .map(metadata -> metadata.relativePathString)
                                           .collect(Collectors.joining("\0"))
                                           .concat("\0")
                                           .getBytes(Charset.forName("UTF8"));

        // --- Header
        ByteBuffer headerBuf = ByteBuffer.allocate(
                (Integer.BYTES + (directories.size() * Integer.BYTES * 2)) + // Directory count + two ints per directory
                (Integer.BYTES + (files.size() * (Integer.BYTES + Long.BYTES))) + // File count + one int + one long
                (Integer.BYTES + directoryBytes.length) + // Length-prefixed directory list
                (Integer.BYTES + joinedFiles.length) // Length-prefixed file list
        ).order(ByteOrder.LITTLE_ENDIAN);

        // Write directory header
        headerBuf.putInt(directories.size());
        {
            int i = 0;
            for (DirectoryMetadata metadata : directories.values()) {
                headerBuf.putInt(i++)
                         .putInt(metadata.foundFiles);
            }
        }

        // Write files header
        headerBuf.putInt(files.size());
        {
            int offset = headerBuf.limit();
            for (FileMetadata metadata : files.values()) {
                headerBuf.putInt(offset);
                offset += metadata.size;
                headerBuf.putInt(metadata.size)
                         .putInt(metadata.directoryId);
            }
        }

        // Write directory and file listings
        headerBuf.putInt(directoryBytes.length)
                 .putInt(joinedFiles.length)
                 .put(directoryBytes)
                 .put(joinedFiles);


        // --- Data

        try (FileChannel out = new FileOutputStream(datOutputPath.toFile()).getChannel()) {
            headerBuf.flip();
            out.write(headerBuf);
            for (Path filePath : files.keySet()) {
                try (FileChannel in = new FileInputStream(filePath.toFile()).getChannel()) {
                    in.transferTo(0, in.size(), out);
                }
            }
        }
    }

    private static final class DirectoryMetadata {
        public final int foundFiles;
        public final String relativePathString;

        DirectoryMetadata(int foundFiles, String relativePathString) {
            this.foundFiles = foundFiles;
            this.relativePathString = relativePathString;
        }
    }

    private static final class FileMetadata {
        public final int size;
        public final int directoryId;
        public final String relativePathString;

        FileMetadata(int size, int directoryId, String relativePathString) {
            this.size = size;
            this.directoryId = directoryId;
            this.relativePathString = relativePathString;
        }
    }
}
