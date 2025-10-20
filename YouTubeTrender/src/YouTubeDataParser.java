import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeDataParser {
    public List<YouTubeVideo> parse(String filename) throws YouTubeDataParserException {
        List<YouTubeVideo> videos = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new FileInputStream(filename))) {
            JsonObject root = reader.readObject();
            JsonArray items = root.getJsonArray("items");

            for (int i = 0; i < items.size(); i++) {
                JsonObject item = items.getJsonObject(i);

                JsonObject snippet = item.getJsonObject("snippet");

                String channel = snippet.getString("channelTitle", "Unknown");
                String title = snippet.getString("title", "No Title");
                String description = snippet.getString("description", "");
                String date = snippet.getString("publishedAt", "");

                // statistics object
                JsonObject stats = item.getJsonObject("statistics");
                long viewCount = 0;
                if (stats != null && stats.containsKey("viewCount")) {
                    try {
                        viewCount = Long.parseLong(stats.getString("viewCount"));
                    } catch (NumberFormatException e) {
                        viewCount = 0;
                    }
                }

                // create video object
                YouTubeVideo video = new YouTubeVideo(channel, title, description, date, viewCount);
                videos.add(video);
            }

        } catch (FileNotFoundException e) {
            throw new YouTubeDataParserException("File not found: " + filename);
        } catch (Exception e) {
            throw new YouTubeDataParserException("Error parsing JSON: " + e.getMessage());
        }

        return videos;
    }
}
