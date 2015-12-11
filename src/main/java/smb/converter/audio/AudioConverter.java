package smb.converter.audio;

import java.io.IOException;
import java.nio.file.Path;

// Mediocre audio converter that calls out to ffmpeg
public class AudioConverter {

    // TODO: Test on Windows
    public static void convertAndTrim(Path source, Path dest, double trimBegin, double trimEnd) throws IOException {
        StringBuilder commandStringBuilder = new StringBuilder("ffmpeg -y -i ").append(source.toAbsolutePath().toString())
                                                                               .append(" -acodec adpcm_ms");

        if (trimBegin != 0) {
            commandStringBuilder.append(" -ss ").append(String.format("%.2f", trimBegin));
        }

        if (trimEnd != 0) {
            commandStringBuilder.append(" -t ").append(String.format("%.2f", (trimEnd - trimBegin)));
        }

        commandStringBuilder.append(" ").append(dest.toAbsolutePath().toString());

        // TODO: check exit code of each conversion
        try {
            if (Runtime.getRuntime().exec(commandStringBuilder.toString()).waitFor() != 0) {
                throw new IOException("Conversion terminated abnormally: " + source.toString());
            }
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
