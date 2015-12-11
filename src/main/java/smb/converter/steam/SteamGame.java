package smb.converter.steam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class SteamGame {
    private final Path gamePath;

    protected SteamGame(Path gamePath) {
        this.gamePath = gamePath;
    }

    public Optional<Path> findResource(String fileName) {
        return recursiveListFiles(gamePath).filter(path -> path.getFileName().toString().equals(fileName)).findFirst();
    }

    private static Stream<Path> recursiveListFiles(Path root) {
        try {
            return Files.list(root).flatMap(path -> {
                if (Files.isDirectory(path)) {
                    return recursiveListFiles(path);
                }
                return Stream.of(path);
            });
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
