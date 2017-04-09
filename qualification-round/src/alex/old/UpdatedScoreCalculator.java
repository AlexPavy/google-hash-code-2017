package alex.old;

import alex.MinLatencyKey;
import alex.ScoreBuilder;
import alex.VideoWithScore;
import common.model.Cache;
import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class UpdatedScoreCalculator {

    final Map<MinLatencyKey, Integer> minLatencies = new HashMap<>();
    final Problem problem;

    public UpdatedScoreCalculator(Problem problem) {
        this.problem = problem;
    }

    public void buildScores() {
        for (Cache cache : problem.cacheMap.values()) {
            addVideosForCache(cache);
        }
    }

    private void addVideosForCache(Cache cache) {
        SortedSet<VideoWithScore> videoWithScores;
        System.out.println("doing cache n° " + cache.id + " / " + problem.C);
        videoWithScores = new TreeSet<>((o1, o2) -> {
            if (o1.score > o2.score) {
                return -1;
            } else if (o1.score < o2.score) {
                return 1;
            } else {
                return 0;
            }
        });
        for (Video video : problem.videoMap.values()) {
            double score = 0;
            for (Endpoint endpoint : video.requestingEndpoints) {
                score += new ScoreBuilder(cache, endpoint, video).buildScoreUpdated(problem, minLatencies);
            }
            score = score / video.size;
            videoWithScores.add(new VideoWithScore(video, score));
        }
        addVideosInOrder(cache, videoWithScores);
    }

    private void addVideosInOrder(Cache cache, SortedSet<VideoWithScore> videoWithScores) {
        int currentSize = 0;
        for (VideoWithScore videoWithScore : videoWithScores) {
            if (currentSize > 0 && videoWithScore.score <= 0) {
                break;
            }
            int possibleSize = currentSize + videoWithScore.video.size;
            if (cache.getSize() >= possibleSize) {
                cache.videoSet.add(videoWithScore);
                for (Endpoint endpoint : videoWithScore.video.requestingEndpoints) {
                    new ScoreBuilder(cache, endpoint, videoWithScore.video)
                            .updateMinLatencyWithVideoAdded(minLatencies);
                }
                currentSize = possibleSize;
            }
            if (cache.getSize() <= currentSize) {
                break;
            }
        }

    }

}
