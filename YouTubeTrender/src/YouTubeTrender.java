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

        try {
            YouTubeDataParser parser = new YouTubeDataParser();
            List<YouTubeVideo> list = parser.parse(filename);
            System.out.println("found size: " + list.size());
            System.out.println("Success: " + (expectedSize == list.size()));

        } catch (YouTubeDataParserException e) {
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

            videos.sort(YouTubeVideo.BY_CHANNEL);
            boolean sorted = true;
            for (int i = 0; i < videos.size() - 1; i++) {
                String c1 = videos.get(i).getChannel();
                String c2 = videos.get(i + 1).getChannel();
                if (c1.compareToIgnoreCase(c2) > 0) {
                    System.out.println("Out of order: " + c1 + " > " + c2);
                    sorted = false;
                    break;
                }
            }
            System.out.println("Sorted by Channel: " + sorted);


            videos.sort(YouTubeVideo.BY_DESLENGTH);
            boolean descSorted = true;
            for (int i = 0; i < videos.size() - 1; i++) {
                if (videos.get(i).getDescription().length() > videos.get(i + 1).getDescription().length()) {
                    descSorted = false;
                    break;
                }
            }
            System.out.println("Sorted by Description Length: " + descSorted);

            videos.sort(YouTubeVideo.BY_VIEWS);
            boolean viewsSorted = true;
            for (int i = 0; i < videos.size() - 1; i++) {
                if (videos.get(i).getViewCount() > videos.get(i + 1).getViewCount()) {
                    viewsSorted = false;
                    break;
                }
            }
            System.out.println("Sorted by Views (ascending): " + viewsSorted);


            videos.sort(YouTubeVideo.BY_DATE);
            boolean dateSorted = true;
            for (int i = 0; i < videos.size() - 1; i++) {
                if (videos.get(i).getDate().compareTo(videos.get(i + 1).getDate()) < 0) {
                    dateSorted = false;
                    break;
                }
            }
            System.out.println("Sorted by Date (descending): " + dateSorted);

        } catch (YouTubeDataParserException e) {
            System.out.println("Failed to parse file: " + e.getMessage());
        }
    }

    public static void test5() {
        System.out.println("Performing Test 5: indextest.json word counts");
        try {
            List<YouTubeVideo> videos = new YouTubeDataParser().parse("YouTubeTrender/data/youtubedata_indextest.json");
            trendingAnalyzer analyzer = new trendingAnalyzer();
            analyzer.indexVideos(videos);

            System.out.println("ONE: " + analyzer.getWord("ONE").getCount() + " (expected: 1)");
            System.out.println("FIVE: " + analyzer.getWord("FIVE").getCount() + " (expected: 5)");
            System.out.println("Most used: " + analyzer.getMostUsedWord().getWord());

        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }


    public static void test6() {
        System.out.println("Performing Test 6: Multiple videos");
        try {
            List<YouTubeVideo> videos = new YouTubeDataParser().parse("YouTubeTrender/data/youtubedata_loremipsum.json");
            System.out.println("Videos: " + videos.size() + " (expected: 10)");
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    public static void test7() {
        System.out.println("Performing Test 7: Basic single video parsing");
        try {
            List<YouTubeVideo> videos = new YouTubeDataParser().parse("YouTubeTrender/data/youtubedata.json");
            System.out.println("Single video parsed: " + (videos.size() == 1));
            System.out.println("Title: " + videos.get(0).getTitle());
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
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
        test5();
        test6();
        test7();
    }

}
