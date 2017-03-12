package alex;

import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static alex.CommonListScoreCalculator.newVideoWithScoreForCacheComparator;

public class WithSizePredictionCalculator {

    private static final int PRINT_PERIOD = 100_000;
    private static final int RECALC_THRESHOLD = 10_000;

    final Problem problem;
    final Map<MinLatencyKey, Integer> minLatencies = new HashMap<>();
    final Map<Video, List<VideoWithScoreForCache>> indexByVideo = new HashMap<>();

    // Common list of videos for all caches
    final SortedMultiset<VideoWithScoreForCache> commonList = TreeMultiset
            .create(newVideoWithScoreForCacheComparator());

    public WithSizePredictionCalculator(Problem problem) {
        this.problem = problem;
    }

    public void buildScores() {
        createVideosForCachesList();
        int iterations = 0;

        while (commonList.size() > 0) {
            final VideoWithScoreForCache nextVideoForCache = pollNextBestChoice();
            iterations = printIteration(iterations, nextVideoForCache);

            if (nextVideoForCache.score <= 0) {
                return;
            }
            if (nextVideoForCache.cache.isFull()) {
                continue;
            }

            if (!nextVideoForCache.isScoreUpToDate) {
                final double newScore = calculateScore(nextVideoForCache.cache, nextVideoForCache.video, iterations);
                nextVideoForCache.updateScore(newScore);
                addVideoToCommonList(nextVideoForCache);
            } else {
                boolean added = nextVideoForCache.addIfPossible();
                if (added) {
                    resetScoresForVideo(nextVideoForCache);
                }
            }
        }
    }

    private void resetScoresForVideo(VideoWithScoreForCache nextVideoForCache) {
        for (Endpoint endpoint : nextVideoForCache.video.possibleEndpoints) {
            new ScoreBuilder(nextVideoForCache.cache, endpoint, nextVideoForCache.video)
                    .updateMinLatencyWithVideoAdded(minLatencies);
        }
        for (VideoWithScoreForCache otherScoreOfVideo : indexByVideo.get(nextVideoForCache.video)) {
            otherScoreOfVideo.isScoreUpToDate = false;
        }
    }

    private VideoWithScoreForCache pollNextBestChoice() {
        VideoWithScoreForCache nextVideoForCache = commonList.pollFirstEntry().getElement();
        nextVideoForCache.cache.removeVideoFromEstimation(nextVideoForCache);
        nextVideoForCache.resetEstimatedSizeToActual();
        return nextVideoForCache;
    }

    private int printIteration(int iterations, VideoWithScoreForCache nextVideoForCache) {
        iterations++;
        if (iterations % PRINT_PERIOD == 0) {
            System.out.println(
                    "Iteration: " + iterations
                            + " videoForCache Score: " + nextVideoForCache.score
                            + " commonList Size: " + commonList.size());
        }
        return iterations;
    }

    private void createVideosForCachesList() {
        int iterations = 0;
        for (Cache cache : problem.cacheMap.values()) {
            for (Video video : problem.videoMap.values()) {
                final double score = calculateScore(cache, video, iterations);
                final VideoWithScoreForCache videoForCache = new VideoWithScoreForCache(video, score, cache);
                addVideoToCommonList(videoForCache);
                indexByVideo.computeIfAbsent(video, k -> new ArrayList<>());
                indexByVideo.get(video).add(videoForCache);
                iterations = printIteration(iterations, videoForCache);
            }
        }
        System.out.println("createVideosForCachesList ends with global list size: " + commonList.size());
    }

    private double calculateScore(Cache cache, Video video, int iterations) {
        double score = 0;
        for (Endpoint endpoint : video.possibleEndpoints) {
            score += new ScoreBuilder(cache, endpoint, video).buildScoreUpdated(problem, minLatencies);
        }
        boolean recalculate = shouldRecalculate(iterations);
        score = updateScoreWithSizes(recalculate, cache, video, score);
        return score;
    }

    private boolean shouldRecalculate(int iterations) {
        return iterations <= RECALC_THRESHOLD;
    }

    private double updateScoreWithSizes(boolean useCache, Cache cache, Video video, double score) {
        score = score * cache.getRemainingSize();
        double estimatedSize = cache.getEstimatedSize(useCache, commonList) + video.size;
        if (estimatedSize > 0) {
            score = score / estimatedSize;
        }
        return score;
    }

    private void addVideoToCommonList(VideoWithScoreForCache videoForCache) {
        commonList.add(videoForCache);
        videoForCache.cache.addVideoForEstimation(videoForCache);
    }

}
