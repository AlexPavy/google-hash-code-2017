package common.dto;

import alex.VideoWithScoreForCache;
import com.google.common.collect.SortedMultiset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Cache {

    private static int RECALC_PERIOD = 100_000;
    private static int UPDATE_CACHE_PERIOD = 1000;

    public final int id;
    public final List<Video> videoList;
    public final Set<VideoWithScoreForCache> estimatedVideos;

    private final int size;

    private int currentSize;
    private Integer estimatedSizeCache;

    private int resetCacheTimer;

    public Cache(int id, int size) {
        this.id = id;
        this.size = size;
        videoList = new ArrayList<>();
        currentSize = 0;
        estimatedVideos = new HashSet<>();
        estimatedSizeCache = null;
        resetCacheTimer = 0;
    }

    public boolean addVideoIfPossible(Video video) {
        if (currentSize + video.size <= size) {
            videoList.add(video);
            currentSize += video.size;
            return true;
        } else {
            return false;
        }
    }

    // Future size needed
    public double getEstimatedSize(boolean recalculate, SortedMultiset<VideoWithScoreForCache> commonList) {
        if (recalculate || resetCacheTimer % RECALC_PERIOD == 0) {
            resetCacheTimer = 0;
            return getRecalculatedEstimatedSizeFromVideos(commonList);
        }
        resetCacheTimer++;
        if (resetCacheTimer % UPDATE_CACHE_PERIOD == 0) {
            estimatedSizeCache = null;
        }

        if (estimatedSizeCache != null) {
            return estimatedSizeCache;
        } else {
            return getEstimatedSizeFromVideos();
        }
    }

    public double getEstimatedSizeFromVideos() {
        int estimatedSize = currentSize;
        for (VideoWithScoreForCache estimatedVideo : estimatedVideos) {
            estimatedSize += estimatedVideo.estimatedSize;
        }
        estimatedSizeCache = estimatedSize;
        return estimatedSize;
    }

    public double getRecalculatedEstimatedSizeFromVideos(SortedMultiset<VideoWithScoreForCache> commonList) {
        int estimatedSize = currentSize;
        for (VideoWithScoreForCache estimatedVideo : estimatedVideos) {
            estimatedSize += estimatedVideo.updateEstimatedSize(commonList);
        }
        estimatedSizeCache = estimatedSize;
        return estimatedSize;
    }

    public void addVideoForEstimation(VideoWithScoreForCache estimatedVideo) {
        resetCacheTimer++;
        estimatedVideos.add(estimatedVideo);
    }

    public void removeVideoFromEstimation(VideoWithScoreForCache estimatedVideo) {
        resetCacheTimer++;
        estimatedVideos.remove(estimatedVideo);
    }

    public double getRemainingSize() {
        return size - currentSize;
    }

    public boolean isFull() {
        return currentSize == size;
    }

    public int getSize() {
        return size;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public String printVideos() {
        String toString = id + "";
        for (Video video : getVideoList()){
            toString +=  " " + video.id;
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
