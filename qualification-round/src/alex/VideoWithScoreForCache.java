package alex;

import common.model.Cache;
import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

import java.util.Map;

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

    public void reCalculateScore(final Problem problem, final Map<MinLatencyKey, Integer> minLatencies) {
        double newScore = 0;
        for (Endpoint endpoint : video.requestingEndpoints) {
            newScore += new ScoreBuilder(cache, endpoint, video)
                    .buildScoreUpdated(problem, minLatencies);
        }
        newScore = newScore * cache.getRemainingSize() / video.size;
        updateScore(newScore);
    }

    public boolean addIfPossible() {
        return cache.addVideoIfPossible(this);
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
