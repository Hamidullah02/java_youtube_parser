public class YouTubeVideo {
    private String channel;
    private String title;
    private String description;
    private String date;
    private long viewcount;

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
    public String getDate() {
        return date;
    }
    public long getViewCount() {
        return viewcount;
    }
    @Override
    public String toString() {
        return channel + " | " + title + " | " + viewcount;
    }
}
