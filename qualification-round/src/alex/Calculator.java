package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

public class Calculator {

    public void buildScores(Problem problem) {
        for (Video video : problem.videoList.values()) {
            for (Endpoint endpoint : problem.endpointList.values()) {
                for (Cache cache : problem.cacheList.values()) {

                }
            }
        }
    }

}
