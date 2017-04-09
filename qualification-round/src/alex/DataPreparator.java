package alex;

import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

public class DataPreparator {

    public Problem prepareData(Problem problem) {
        System.out.println("prepareData begins");
        for (Video video : problem.videoMap.values()) {
            for (Endpoint endpoint : problem.endpointMap.values()) {
                Integer latency = video.requestsMap.get(endpoint.id);
                if (latency != null) {
                    video.requestingEndpoints.add(endpoint);
                }
            }

        }
        System.out.println("prepareData ended");
        return problem;
    }
}
