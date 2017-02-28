package alex;

import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

public class DataPreparator {

    public Problem prepareData(Problem problem) {
        System.out.println("prepareData begins");
        for (Video video : problem.videoList.values()) {
            for (Endpoint endpoint : problem.endpointList.values()) {
                Integer latency = video.requests.get(endpoint.id);
                if (latency != null) {
                    video.possibleEndpoints.add(endpoint);
                }
            }

        }
        System.out.println("prepareData ended");
        return problem;
    }
}
