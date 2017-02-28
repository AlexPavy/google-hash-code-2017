package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.*;

public class UpdatedScoreCalculator {

    final Map<MinLatencyKey, Integer> minLatencies = new HashMap<>();
    final Problem problem;

    public UpdatedScoreCalculator(Problem problem) {
        this.problem = problem;
    }

    public void buildScores() {
        for (Cache cache : problem.cacheList.values()) {
            addVideosForCache(cache);
        }
    }

    public void addVideosForCache(Cache cache) {
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
        for (Video video : problem.videoList.values()) {
            double score = 0;
            for (Endpoint endpoint : video.possibleEndpoints) {
                score += new ScoreCalculator(cache, endpoint, video).buildScoreUpdated(problem, minLatencies);
            }
            score = score / video.size;
            videoWithScores.add(new VideoWithScore(video, score));
        }
        addVideosInOrder(cache, videoWithScores);
    }

    private void addVideosInOrder(Cache cache, SortedSet<VideoWithScore> videoWithScores) {
        cache.videoList = new ArrayList<>();
        int currentSize = 0;
        for (VideoWithScore videoWithScore : videoWithScores) {
            if (currentSize > 0 && videoWithScore.score <= 0) {
                break;
            }
            int possibleSize = currentSize + videoWithScore.video.size;
            if (cache.getSize() >= possibleSize) {
                cache.videoList.add(videoWithScore.video);
                for (Endpoint endpoint : videoWithScore.video.possibleEndpoints) {
                    new ScoreCalculator(cache, endpoint, videoWithScore.video)
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
