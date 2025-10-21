import java.util.*;

public class trendingAnalyzer {
    private final Map<String, WordIndex> wordMap;

    public trendingAnalyzer() {
        wordMap = new HashMap<>();
    }

    public void indexVideos(List<YouTubeVideo> videos) {
        wordMap.clear();
        for (YouTubeVideo video : videos) {
            String content = (video.getTitle() + " " + video.getDescription());
            String[] words = content.split("\\s+");

            for (String w : words) {
                WordIndex wi = wordMap.getOrDefault(w, new WordIndex(w));
                wi.increment(video);
                wordMap.put(w, wi);
            }
        }
    }
    public WordIndex getWord(String word) {
        return wordMap.get(word);
    }

    public WordIndex getMostUsedWord() {
        return wordMap.values().stream()
                .max(Comparator.comparingInt(WordIndex::getCount))
                .orElse(null);
    }

    public List<WordIndex> getWordsSortedByCount() {
        List<WordIndex> list = new ArrayList<>(wordMap.values());
        list.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
        return list;
    }
}
