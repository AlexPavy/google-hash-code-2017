package alex;

import common.model.Cache;
import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

import java.util.*;

public class CommonListScoreCalculator {

    final Map<MinLatencyKey, Integer> minLatencies = new HashMap<>();
    final Problem problem;
    final Map<Video, List<VideoWithScoreForCache>> indexByVideo = new HashMap<>();

    // Common list of videos for all caches
    PriorityQueue<VideoWithScoreForCache> commonList = new PriorityQueue<>(newVideoWithScoreForCacheComparator());

    public CommonListScoreCalculator(Problem problem) {
        this.problem = problem;
    }

    public void buildScores() {
        createVideosForCachesList();
        int iterations = 0;

        while (commonList.size() > 0) {

            VideoWithScoreForCache nextVideoForCache = commonList.peek();
            commonList.remove(nextVideoForCache);
            iterations = printIteration(iterations, nextVideoForCache);

            if (nextVideoForCache.score <= 0) {
                return;
            }
            if (nextVideoForCache.cache.isFull()) {
                continue;
            }

            if (!nextVideoForCache.isScoreUpToDate) {
                nextVideoForCache.reCalculateScore(problem, minLatencies);
                commonList.add(nextVideoForCache);
            } else {
                boolean added = nextVideoForCache.addIfPossible();
                if (added) {
                    for (Endpoint endpoint : nextVideoForCache.video.requestingEndpoints) {
                        new ScoreBuilder(nextVideoForCache.cache, endpoint, nextVideoForCache.video)
                                .updateMinLatencyWithVideoAdded(minLatencies);
                    }
                    for (VideoWithScoreForCache otherScoreOfVideo : indexByVideo.get(nextVideoForCache.video)) {
                        otherScoreOfVideo.isScoreUpToDate = false;
                    }
                }
            }
        }
    }

    public int printIteration(int iterations, VideoWithScoreForCache nextVideoForCache) {
        iterations ++;
        if (iterations % 100_000 == 0) {
            System.out.println(
                    "Iteration: " + iterations
                    + " nextVideoForCache Score: " + nextVideoForCache.score
            + " nb remaining: " + commonList.size());
        }
        return iterations;
    }

    public void createVideosForCachesList() {
        for (Cache cache : problem.cacheMap.values()) {
            for (Video video : problem.videoMap.values()) {
                final VideoWithScoreForCache videoForCache = new VideoWithScoreForCache(video, 0, cache);
                videoForCache.reCalculateScore(problem, minLatencies);
                commonList.add(videoForCache);
                indexByVideo.computeIfAbsent(video, k -> new ArrayList<>());
                indexByVideo.get(video).add(videoForCache);
            }
        }
        System.out.println("createVideosForCachesList ends with global list size: " + commonList.size());
    }

    public static Comparator<VideoWithScoreForCache> newVideoWithScoreForCacheComparator() {
        return (o1, o2) -> {
            if (o1.score > o2.score) {
                return -1;
            } else if (o1.score < o2.score) {
                return 1;
            } else {
                return 0;
            }
        };
    }

}
