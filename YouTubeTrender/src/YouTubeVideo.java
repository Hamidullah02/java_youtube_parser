import java.util.Comparator;

public class YouTubeVideo {
    private final String channel;
    private final String title;
    private final String description;
    private final String date;
    private final long viewcount;

    public YouTubeVideo(String channel, String title, String description, String date, long viewcount){
        this.channel = channel;
        this.title = title;
        this.description = description;
        this.date = date;
        this.viewcount = viewcount;
    }
    public String getChannel() {
        return channel;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public int getDeslen() {
        return description.length();
    }

    public String getDate() {
        return date;
    }
    public long getViewCount() {
        return viewcount;
    }
    @Override
    public String toString() {
        return title;
    }


    public static final Comparator<YouTubeVideo> BY_CHANNEL =
            Comparator.comparing(YouTubeVideo::getChannel, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<YouTubeVideo> BY_DESLENGTH =
            Comparator.comparing(YouTubeVideo::getDeslen);

    public static final Comparator<YouTubeVideo> BY_DATE =
            Comparator.comparing(YouTubeVideo::getDate);

    public static final Comparator<YouTubeVideo> BY_VIEWS =
            Comparator.comparingLong(YouTubeVideo::getViewCount);
}
