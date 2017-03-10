package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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


    static int zombieCluster(String[] zombies) {
        if (zombies == null) {
            return -1;
        }

        Map<Integer, Integer> clustersByZombies = new HashMap<>(); // zombieID to clusterID
        Map<Integer, Set<Integer>> zombiesByClusters = new HashMap<>(); // clusterID to list of zombieIDs
        Integer lastClusterId = 0;
        for (Integer i = 0; i< zombies.length; i++) {
            if (zombies[i] == null) {
                return -1;
            }
            for (Integer j = 0; j< zombies[i].length(); j++) {
                Integer clusterI = clustersByZombies.get(i);
                Integer clusterJ = clustersByZombies.get(j);
                if (zombies[i].charAt(j) == '1') {
                    if (clusterI != null) {
                        if (clusterJ != null) { // J merge into I
                            for (Integer zombieID : zombiesByClusters.get(clusterJ)) {
                                clustersByZombies.put(zombieID, clusterI);
                                zombiesByClusters.get(clusterI).add(zombieID);
                            }
                            zombiesByClusters.remove(clusterJ);
                        } else { // j to I
                            clustersByZombies.put(j, clusterI);
                            zombiesByClusters.get(clusterI).add(j);
                        }
                    } else if (clusterJ != null) { // i to J
                        clustersByZombies.put(i, clusterJ);
                        zombiesByClusters.get(clusterJ).add(i);
                    } else { // create (i,j)
                        lastClusterId++;
                        clustersByZombies.put(i, lastClusterId);
                        clustersByZombies.put(j, lastClusterId);
                        zombiesByClusters.put(lastClusterId, new HashSet<>());
                        zombiesByClusters.get(lastClusterId).add(i);
                        zombiesByClusters.get(lastClusterId).add(j);
                    }
                } else {
                    if (clusterI == null) { // create (i)
                        lastClusterId++;
                        clustersByZombies.put(i, lastClusterId);
                        zombiesByClusters.put(lastClusterId, new HashSet<>());
                        zombiesByClusters.get(lastClusterId).add(i);
                    }
                }
            }
        }
        return zombiesByClusters.keySet().size();
    }
}
