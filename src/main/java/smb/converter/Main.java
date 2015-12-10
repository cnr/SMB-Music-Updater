package smb.converter;

import smb.converter.dat.DatExtractor;
import smb.converter.steam.SteamHelper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {
        // Find Super Meat Boy
        Path audioDatPath;
        {
            Optional<Path> maybeAudioDatPath = SteamHelper.findGamePath("Super Meat Boy")
                                                          .flatMap(path -> SteamHelper.findGameFile(path, "gameaudio.dat"));
            if (!maybeAudioDatPath.isPresent()) {
                System.err.println("Couldn't find Super Meat Boy game audio");
                System.exit(1);
            }

            audioDatPath = maybeAudioDatPath.get();
        }
        System.out.println("Found gameaudio.dat");


        // Unpack game audio into temporary directory
        Path tmp = Files.createTempDirectory("smb-music-converter");
        cleanupOnExit(tmp);
        System.out.println("Temporary directory: " + tmp.toString());

        DatExtractor.extract(audioDatPath, tmp.resolve("gameaudio/"));
        System.out.println("Unpacked into temporary directory");
    }

    private static void cleanupOnExit(Path dir) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException ignored) {}
            }
        });
    }
}
