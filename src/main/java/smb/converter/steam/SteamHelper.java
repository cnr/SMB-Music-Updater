package smb.converter.steam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class SteamHelper {

    // TODO: implement for Windows
    public static Optional<Path> findGamePath(String game) throws IOException {
        Path homeDirectory = Paths.get(System.getProperty("user.home"));
        Path steamAppsDirectory = homeDirectory.resolve("Library/Application Support/Steam/SteamApps/");

        if (!Files.isDirectory(steamAppsDirectory)) {
            return Optional.empty();
        }

        return Files.list(steamAppsDirectory)
                    .flatMap(path -> { // Go two user directories
                        try {
                            return Files.list(path);
                        } catch (IOException e) { // Darn checked exceptions ruining this..
                            return Stream.empty();
                        }
                    })
                    .filter(path -> path.getFileName().getFileName().toString().equals(game))
                    .findFirst();
    }

    public static Optional<Path> findGameFile(Path root, String fileName) {
        return recursiveListFiles(root).filter(path -> path.getFileName().toString().equals(fileName)).findFirst();
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
