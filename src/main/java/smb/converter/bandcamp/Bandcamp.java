package smb.converter.bandcamp;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A really mediocre bandcamp downloader
// I'd rather not pull single-use dependencies into the project
// Media download URLs can be found in a `trackinfo` JSON array:
//     trackinfo: [{"file":{"mp3-128":"DOWNLOAD_URL"}}]
public class Bandcamp {
    private static final Pattern FILE_LIST_PATTERN = Pattern.compile("\"file\":\\{(.+)\\}");
    private static final Pattern FILE_ENTRY_PATTERN = Pattern.compile("\"[^\"]+\":\"([^\"]+)\"");

    public static void download(URL url, Path dest) throws IOException {
        // Find media download URL
        String mediaDownloadURL;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            Optional<String> maybeDownloadURL = reader.lines()
                    .map(FILE_LIST_PATTERN::matcher) // Find file list
                    .filter(Matcher::find)
                    .map(m -> FILE_ENTRY_PATTERN.matcher(m.group(1))) // Match entries in list
                    .filter(Matcher::find)
                    .map(m -> m.group(1)) // Extract URL
                    .findFirst(); // We only care about the first file entry
            if (!maybeDownloadURL.isPresent()) {
                throw new IOException("Couldn't find download URL");
            }
            mediaDownloadURL = "http:" + maybeDownloadURL.get();
        }

        // Read media into destination file
        try (ReadableByteChannel channel = Channels.newChannel(new URL(mediaDownloadURL).openStream())
           ; FileChannel out = new FileOutputStream(dest.toFile()).getChannel()) {
            out.transferFrom(channel, 0, Integer.MAX_VALUE);
        }
    }
}
