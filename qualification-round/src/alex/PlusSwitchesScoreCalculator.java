package alex;

import common.model.Cache;
import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

import java.util.*;

public class PlusSwitchesScoreCalculator {

    final Map<MinLatencyKey, Integer> minLatencies = new HashMap<>();
    final Problem problem;
    final Map<Video, List<VideoWithScoreForCache>> indexByVideo = new HashMap<>();

    // Common list of videos for all caches
    PriorityQueue<VideoWithScoreForCache> commonList = new PriorityQueue<>(newVideoWithScoreForCacheComparator());

    private double maxScore;

    public PlusSwitchesScoreCalculator(Problem problem) {
        this.problem = problem;
    }

    public void buildScores() {
        createVideosForCachesList();
        int iterations = 0;

        maxScore = commonList.poll().score;

        while (commonList.size() > 0) {
            VideoWithScoreForCache nextVideoForCache = commonList.peek();
            commonList.remove(nextVideoForCache);
            iterations = printIteration(iterations, nextVideoForCache);

            if (nextVideoForCache.score <= 0) {
                return;
            }
            if (nextVideoForCache.cache.isFull()) {
                nextVideoForCache.cache.addToHeuristicLoad(nextVideoForCache, maxScore);
                continue;
            }

            if (!nextVideoForCache.isScoreUpToDate) {
                nextVideoForCache.reCalculateScore(problem, minLatencies);
                commonList.add(nextVideoForCache);
            } else {
                addVideoToCacheAndMark(nextVideoForCache);
            }
        }
    }

    public void addVideoToCacheAndMark(VideoWithScoreForCache nextVideoForCache) {
        boolean added = nextVideoForCache.addIfPossible();
        if (added) {
            for (Endpoint endpoint : nextVideoForCache.video.requestingEndpoints) {
                new ScoreBuilder(nextVideoForCache.cache, endpoint, nextVideoForCache.video)
                        .updateMinLatencyWithVideoAdded(minLatencies);
            }
            for (final VideoWithScoreForCache otherScoreOfVideo : indexByVideo.get(nextVideoForCache.video)) {
                otherScoreOfVideo.isScoreUpToDate = false;
            }
        }
    }

    private int printIteration(int iterations, VideoWithScoreForCache nextVideoForCache) {
        iterations ++;
        if (iterations % 100_000 == 0) {
            System.out.println(
                    "Iteration: " + iterations
                    + " nextVideoForCache Score: " + nextVideoForCache.score
            + " nb remaining: " + commonList.size());
        }
        return iterations;
    }

    private void createVideosForCachesList() {
        for (Cache cache : problem.cacheMap.values()) {
            for (Video video : problem.videoMap.values()) {
                double score = 0;
                final VideoWithScoreForCache videoForCache = new VideoWithScoreForCache(video, score, cache);
                videoForCache.reCalculateScore(problem, minLatencies);
                commonList.add(videoForCache);
                indexByVideo.computeIfAbsent(video, k -> new ArrayList<>());
                indexByVideo.get(video).add(videoForCache);
            }
        }
        System.out.println("createVideosForCachesList ends with global list size: " + commonList.size());
        createCacheSwitchOrder();
    }

    private static Comparator<VideoWithScoreForCache> newVideoWithScoreForCacheComparator() {
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

    private void createCacheSwitchOrder() {
        System.out.println("createCacheSwitchOrder");
        for (VideoWithScoreForCache videoForCache : commonList) {
            videoForCache.video.potentialCaches.add(videoForCache.cache);
        }
    }

    public void makeSwitches() {
        final PriorityQueue<Cache> cachesByHeuristicLoad = new PriorityQueue<>((o1, o2) -> {
            if (o1.loadHeuristic > o2.loadHeuristic) {
                return -1;
            } else if (o1.loadHeuristic < o2.loadHeuristic) {
                return 1;
            } else {
                return 0;
            }
        });
        cachesByHeuristicLoad.addAll(problem.cacheMap.values());

//        keep order of video insertion to know which cache is second best for that video
//
//        tabu search
//        mark current sol as best sol
//        for (Cache cache : cachesByHeuristicLoad) {
//            cache.nextPossible videos iterate -> pvideo
//                    while no size for pvideo
//            for (VideoWithScore videoWithScore : cache.videoSet) {
//                cache.videoSet.remove(videoWithScore);
//            }
//            get cache of pvideo and remove pvideo
//                    put pvideo into cache
//
//        }
//
//        search other heuristic
    }

}
