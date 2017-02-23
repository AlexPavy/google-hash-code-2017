package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.*;

public class Calculator {

    final Map<Cache, SortedSet<VideoWithScore>> map = new HashMap<>();

    public void buildScores(Problem problem) {
        for (Cache cache : problem.cacheList.values()) {
            map.put(cache, new TreeSet<>((o1, o2) -> {
                if(o1.score > o2.score) {
                    return 1;
                } else if (o1.score < o2.score) {
                    return -1;
                } else {
                    return 0;
                }
            }));
            for (Video video : problem.videoList.values()) {
                double score = 0;
                for (Endpoint endpoint : problem.endpointList.values()) {
                    score += new ScoreKey(cache, endpoint, video).buildScore();
                }
                score = score / video.size;
                map.get(cache).add(new VideoWithScore(video, score));
            }
        }
    }

    public void addVideosInOrder() {
        for (Map.Entry<Cache, SortedSet<VideoWithScore>> cacheSortedSetEntry : map.entrySet()) {
            Cache cache = cacheSortedSetEntry.getKey();
            cache.videoList = new ArrayList<>();
            int currentSize = 0;
            for (VideoWithScore videoWithScore : cacheSortedSetEntry.getValue()) {
                currentSize += videoWithScore.video.size;
                if (cache.getSize() >= currentSize) {
                    cache.videoList.add(videoWithScore.video);
                } else {
                    break;
                }
            }

        }

    }

}
