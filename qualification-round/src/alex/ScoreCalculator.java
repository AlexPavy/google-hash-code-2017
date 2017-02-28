package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.Map;

public class ScoreCalculator {

    public final Cache cache;
    public final Endpoint endpoint;
    public final Video video;

    public ScoreCalculator(Cache cache, Endpoint endpoint, Video video) {
        this.cache = cache;
        this.endpoint = endpoint;
        this.video = video;
    }

    public double buildScore() {
        Map<Integer, Integer> connections = endpoint.getConnections();
        Integer latencyInt = connections.get(cache.id);
        if (latencyInt == null) {
            return 0;
        }
        double latencyDifference = latencyInt - endpoint.datacenterLatency; // why does this give less good result ?
        double latencyDouble = (double) latencyInt;
        if (endpoint.datacenterLatency <= latencyDouble) {
            return 0;
        }
        double v;

        if (video.requests.get(endpoint.getId()) == null) {
            v = 0;
        } else {
            v = ((double) video.requests.get(endpoint.getId())) / latencyDouble;
        }
        return v;
    }

    public double buildScoreUpdated(Problem problem, Map<MinLatencyKey, Integer> minLatencies) {
        Map<Integer, Integer> connections = endpoint.getConnections();
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

        double v = ((double) video.requests.get(endpoint.getId())) * latencyGain;
        return v;
    }

    public void updateMinLatencyWithVideoAdded(Map<MinLatencyKey, Integer> minLatencies) {
        MinLatencyKey minLatencyKey = new MinLatencyKey(endpoint.id, video.id);
        Integer minLatency = minLatencies.get(minLatencyKey);
        if (minLatency == null) {
            minLatency = endpoint.datacenterLatency;
        }
        Integer latency = endpoint.getConnections().get(cache.id);
        if (latency == null) {
            latency = minLatency;
        }
        minLatencies.put(minLatencyKey, Math.min(latency, minLatency));
    }
}
