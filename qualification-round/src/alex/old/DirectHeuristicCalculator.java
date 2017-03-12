package alex.old;

import alex.ScoreBuilder;
import alex.VideoWithScore;
import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.*;

public class DirectHeuristicCalculator {

    final Map<Cache, SortedSet<VideoWithScore>> map = new HashMap<>();

    public void buildScores(Problem problem) {
        for (Cache cache : problem.cacheMap.values()) {
            addVideosForCache(problem, cache);
        }
    }

    public void addVideosForCache(Problem problem, Cache cache) {
        System.out.println("doing cache n° " + cache.id + " / " + problem.C);
        map.put(cache, new TreeSet<>(newVideoWithScoreComparator()));
        for (Video video : problem.videoMap.values()) {
            double score = 0;
            for (Endpoint endpoint : problem.endpointMap.values()) {
                score += new ScoreBuilder(cache, endpoint, video).buildScore();
            }
            score = score / video.size;
            map.get(cache).add(new VideoWithScore(video, score));
        }
    }

    public void addVideosInOrder() {
        for (Map.Entry<Cache, SortedSet<VideoWithScore>> cacheSortedSetEntry : map.entrySet()) {
            Cache cache = cacheSortedSetEntry.getKey();
            int currentSize = 0;
            for (VideoWithScore videoWithScore : cacheSortedSetEntry.getValue()) {
                int possibleSize = currentSize + videoWithScore.video.size;
                if (cache.getSize() >= possibleSize) {
                    cache.videoSet.add(videoWithScore);
                    currentSize = possibleSize;
                }
                if (cache.getSize() <= currentSize) {
                    break;
                }
            }
        }
    }

    public static Comparator<VideoWithScore> newVideoWithScoreComparator() {
        return (o1, o2) -> {
            if(o1.score > o2.score) {
                return -1;
            } else if (o1.score < o2.score) {
                return 1;
            } else {
                return 0;
            }
        };
    }

}
