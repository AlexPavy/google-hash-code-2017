package common.model;

import alex.VideoWithScore;
import alex.VideoWithScoreForCache;

import java.util.HashSet;
import java.util.Set;


public class Cache {

    public final int id;
    public final Set<VideoWithScore> videoSet;
    public final Set<VideoWithScoreForCache> estimatedVideos;

    private final int size;

    private int currentSize;
    private Integer estimatedSizeCache;

    private int resetCacheTimer;

    public double loadHeuristic;

    public Cache(int id, int size) {
        this.id = id;
        this.size = size;
        videoSet = new HashSet<>();
        currentSize = 0;
        estimatedVideos = new HashSet<>();
        estimatedSizeCache = null;
        resetCacheTimer = 0;
        loadHeuristic = 0;
    }

    public boolean addVideoIfPossible(VideoWithScoreForCache videoWithScoreForCache) {
        if (currentSize + videoWithScoreForCache.video.size <= size) {
            videoSet.add(new VideoWithScore(videoWithScoreForCache.video, videoWithScoreForCache.score));
            currentSize += videoWithScoreForCache.video.size;
            loadHeuristic += videoWithScoreForCache.video.size;
            return true;
        } else {
            return false;
        }
    }

    public void addToHeuristicLoad(VideoWithScoreForCache videoForCache, double maxScore) {
        loadHeuristic += ((double) videoForCache.video.size) * videoForCache.score / maxScore;
    }

    public int getRemainingSize() {
        return size - currentSize;
    }

    public boolean isFull() {
        return currentSize == size;
    }

    public int getSize() {
        return size;
    }

    public Set<VideoWithScore> getVideoSet() {
        return videoSet;
    }

    public String printVideos() {
        String toString = id + "";
        for (VideoWithScore videoWithScore : getVideoSet()){
            toString +=  " " + videoWithScore.video.id;
        }
        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cache cache = (Cache) o;

        return id == cache.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
