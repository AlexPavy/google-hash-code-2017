package alex;

import com.google.common.collect.SortedMultiset;
import common.dto.Cache;
import common.dto.Video;

import static com.google.common.collect.BoundType.OPEN;

public class VideoWithScoreForCache {

    public final Video video;
    public final Cache cache;

    public double score;
    public boolean isScoreUpToDate;
    public double estimatedSize;

    public VideoWithScoreForCache(Video video, double score, Cache cache) {
        this.video = video;
        this.score = score;
        this.cache = cache;
        isScoreUpToDate = true;
        resetEstimatedSizeToActual();
    }

    public void resetEstimatedSizeToActual() {
        estimatedSize = this.video.size;
    }

    public void updateScore(double newScore) {
        score = newScore;
        isScoreUpToDate = true;
    }

    public double updateEstimatedSize(SortedMultiset<VideoWithScoreForCache> commonList) {
        double pos = commonList.headMultiset(this, OPEN).size() - 1;
        double maxPos = commonList.size();
        estimatedSize = video.size * (1 - (pos / maxPos));
        return estimatedSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoWithScoreForCache that = (VideoWithScoreForCache) o;

        if (video != null ? !video.equals(that.video) : that.video != null) return false;
        return cache != null ? cache.equals(that.cache) : that.cache == null;
    }

    @Override
    public int hashCode() {
        int result = video != null ? video.hashCode() : 0;
        result = 31 * result + (cache != null ? cache.hashCode() : 0);
        return result;
    }
}
