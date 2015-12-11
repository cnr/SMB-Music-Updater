package smb.converter;

import smb.converter.bandcamp.Bandcamp;
import smb.converter.dat.DatExtractor;
import smb.converter.steam.SteamHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
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


        // Download audio files from Bandcamp
        Path downloadDir = tmp.resolve("downloaded-audio");
        if (!downloadDir.toFile().mkdir()) {
            System.err.println("Couldn't create audio download folder");
            System.exit(1);
        }

        for (TrackListing listing : TrackListing.values()) {
            System.out.println("Downloading track: " + listing.name);
            Bandcamp.download(new URL(listing.url), downloadDir.resolve(listing.pcName)); // Wrong file extension, but that doesn't matter
        }
        System.out.println("Downloaded new tracks");
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
