package smb.converter.steam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Steam {
    private final Path steamDirectory;

    public static Optional<Steam> getSteam() {
        Platform platform = getPlatform();

        switch (platform) {
            case WINDOWS:
                Path programFiles = Paths.get("C:\\Program Files\\");
                Path programFilesX86 = Paths.get("C:\\Program Files (x86)\\");
                return Stream.of(programFiles, programFilesX86)
                             .map(path -> path.resolve("Steam"))
                             .filter(Files::isDirectory)
                             .findFirst()
                             .map(Steam::new);

            case OSX:
                Path homeDirectory = Paths.get(System.getProperty("user.home"));
                Path steamDirectory = homeDirectory.resolve("Library/Application Support/Steam/");
                return Files.isDirectory(steamDirectory) ? Optional.of(new Steam(steamDirectory)) : Optional.empty();

            default:
                return Optional.empty();
        }
    }

    private Steam(Path steamDirectory) {
        this.steamDirectory = steamDirectory;
    }

    public Optional<SteamGame> getGame(String name) {
        Path steamAppsDirectory = steamDirectory.resolve("SteamApps");

        if (!Files.isDirectory(steamAppsDirectory)) {
            return Optional.empty();
        }

        try {
            return Files.list(steamAppsDirectory)
                        .flatMap(path -> { // Browse user directories
                            try {
                                return Files.list(path);
                            } catch (IOException e) { // Darn checked exceptions ruining this..
                                return Stream.empty();
                            }
                        })
                        .filter(path -> path.getFileName().getFileName().toString().equals(name))
                        .findFirst()
                        .map(SteamGame::new);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static Platform getPlatform() {
        String osName = System.getProperty("os.name", "other").toLowerCase();

        return osName.startsWith("windows") ? Platform.WINDOWS :
               osName.startsWith("mac")     ? Platform.OSX     :
               Platform.OTHER;
    }

    private enum Platform {
        OSX,
        WINDOWS,
        OTHER,
    }
}
