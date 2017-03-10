package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

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
                double newScore = 0;
                for (Endpoint endpoint : nextVideoForCache.video.possibleEndpoints) {
                    newScore += new ScoreCalculator(nextVideoForCache.cache, endpoint, nextVideoForCache.video)
                            .buildScoreUpdated(problem, minLatencies);
                }
                newScore = newScore * nextVideoForCache.cache.getRemainingSize() / nextVideoForCache.video.size;
                nextVideoForCache.updateScore(newScore);
                commonList.add(nextVideoForCache);
            } else {
                boolean added = nextVideoForCache.cache.addVideoIfPossible(nextVideoForCache.video);
                if (added) {
                    for (Endpoint endpoint : nextVideoForCache.video.possibleEndpoints) {
                        new ScoreCalculator(nextVideoForCache.cache, endpoint, nextVideoForCache.video)
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
        for (Cache cache : problem.cacheList.values()) {
            for (Video video : problem.videoList.values()) {
                double score = 0;
                for (Endpoint endpoint : video.possibleEndpoints) {
                    score += new ScoreCalculator(cache, endpoint, video).buildScoreUpdated(problem, minLatencies);
                }
                score = score * cache.getRemainingSize() / video.size;
                VideoWithScoreForCache videoForCache = new VideoWithScoreForCache(video, score, cache);
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
