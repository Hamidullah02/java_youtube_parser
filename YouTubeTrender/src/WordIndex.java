import java.util.ArrayList;
import java.util.List;

public class WordIndex {
    private  String word;
    private int count;
    private List<YouTubeVideo> videos;


    public WordIndex(String word){
        this.word=word;
        this.count=0;
        this.videos = new ArrayList<>();
    }

    public void increment(YouTubeVideo video){
        count++;
        if (!videos.contains(video)){
            videos.add(video);
        }
    }

    public String getWord(){
        return word;
    }

    public int getCount(){
        return count;
    }
    public List<YouTubeVideo> getVideos(){
        return videos;
    }

    @Override
    public String toString() {
        return word + " (" + count + ")";
    }
}
