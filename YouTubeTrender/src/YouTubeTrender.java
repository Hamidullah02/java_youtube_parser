import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * YouTubeTrender class
 */
public class YouTubeTrender {

    public static void test1() throws FileNotFoundException {

        System.out.println("Performing Test 1");
        String filename = "YouTubeTrender/data/youtubedata_15_50.json";

        // TODO: try the filename below instead and compare output
        // String filename = "data/youtubedata_malformed.json";

        int expectedSize = 50;

        System.out.println("Testing the file: " + filename);
        System.out.println("Expecting size of: " + expectedSize);

        // Read data
        JsonReader jsonReader = Json.createReader(new FileInputStream(filename));
        JsonObject jobj = jsonReader.readObject();

        // read the values of the item field
        JsonArray items = jobj.getJsonArray("items");

        System.out.println("Size of input: " + items.size());
        System.out.println("Success: " + (expectedSize == items.size()));
    }

    public static void test2() {
        System.out.println("Performing Test 2");
        String filename = "YouTubeTrender/data/youtubedata_15_50.json";
        int expectedSize = 50;

        System.out.println("Testing the file: " + filename);
        System.out.println("Expecting size of: " + expectedSize);

        try{
            YouTubeDataParser parser = new YouTubeDataParser();
            List<YouTubeVideo> list =parser.parse(filename);
            System.out.println("found size: " +list.size());
            System.out.println("Success: " + ( expectedSize == list.size()));

        }catch (YouTubeDataParserException e) {
            System.out.println("Parser failed with exception: " + e.getMessage());
        }

        // TODO: implement the remainder of this test
        // YouTubeDataParser parser = new YouTubeDataParser();
        // List<YouTubeVideo> list = parser.parse(filename);
        // System.out.println("Found size: " + list.size());

    }

    public static void test3() {
        System.out.println("Performing Test 3: Malformed file handling");

        // Use a non-existent or malformed file
        String badFilename = "YouTubeTrender/data/youtubedata_malformed.json";

        YouTubeDataParser parser = new YouTubeDataParser();

        try {
            parser.parse(badFilename);
            System.out.println("Fail: No exception thrown for malformed file");
        } catch (YouTubeDataParserException e) {
            System.out.println("Success: Caught YouTubeDataParserException");
            System.out.println("Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fail: Wrong exception type thrown");
            e.printStackTrace();
        }
    }



    public static void test4() {
        System.out.println("Performing Test 4: Sorting logic");

        String filename = "YouTubeTrender/data/youtubedata_15_50.json";
        YouTubeDataParser parser = new YouTubeDataParser();

        try {
            List<YouTubeVideo> videos = parser.parse(filename);

            // Test sort by Channel
            videos.sort(YouTubeVideo.BY_CHANNEL);
            System.out.println("Sorted by Channel: " + videos.get(0).getChannel());

            // Test sort by Title
            videos.sort(YouTubeVideo.BY_DESLENGTH);
            System.out.println("Sorted by Title: " + videos.get(0).getTitle());

            // Test sort by Date
            videos.sort(YouTubeVideo.BY_DATE);
            System.out.println("Sorted by Date: " + videos.get(0).getDate());

            // Test sort by View Count
            videos.sort(YouTubeVideo.BY_VIEWS);
            System.out.println("Sorted by Views: " + videos.get(0).getViewCount());

            System.out.println("Success: Sorting successful");
        } catch (YouTubeDataParserException e) {
            System.out.println("Failed to parse file: " + e.getMessage());
        }
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("YouTube Trender Application");

        try {
            test1();
            System.out.println();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        test2();
        test3();
        test4();
    }

}
