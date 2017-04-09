package alex;

import common.model.Cache;
import common.model.Endpoint;
import common.model.Problem;
import common.model.Video;

import java.util.Map;

public class ScoreBuilder {

    public final Cache cache;
    public final Endpoint endpoint;
    public final Video video;

    public ScoreBuilder(Cache cache, Endpoint endpoint, Video video) {
        this.cache = cache;
        this.endpoint = endpoint;
        this.video = video;
    }

    public double buildScoreUpdated(Problem problem, Map<MinLatencyKey, Integer> minLatencies) {
        Map<Integer, Integer> connections = endpoint.getLatencyToCacheMap();
        Integer latencyInt = connections.get(cache.id);
        if (latencyInt == null) {
            return 0;
        }
        Integer minLatency = minLatencies.get(new MinLatencyKey(endpoint.id, video.id));
        if (minLatency == null) {
            minLatency = endpoint.datacenterLatency;
        }

        if (minLatency <= 0) {
            return 0;
        }
        double minLatencyDouble = (double) minLatency;
        double latencyGain = (minLatencyDouble - (double) latencyInt);

        double v = ((double) video.requestsMap.get(endpoint.getId())) * latencyGain;
        return v;
    }

    public void updateMinLatencyWithVideoAdded(Map<MinLatencyKey, Integer> minLatencies) {
        MinLatencyKey minLatencyKey = new MinLatencyKey(endpoint.id, video.id);
        Integer minLatency = minLatencies.get(minLatencyKey);
        if (minLatency == null) {
            minLatency = endpoint.datacenterLatency;
        }
        Integer latency = endpoint.getLatencyToCacheMap().get(cache.id);
        if (latency == null) {
            latency = minLatency;
        }
        minLatencies.put(minLatencyKey, Math.min(latency, minLatency));
    }

}
