package common.model;

import java.util.HashMap;
import java.util.Map;

public class Endpoint {

    public final int id;
    public final int datacenterLatency;
    public final int nbCache;
    public final Map<Integer,Integer> latencyToCacheMap; // key = idCache, value = latency

    public Endpoint(int id, int datacenterLatency, int nbCache) {
        this.id = id;
        this.datacenterLatency = datacenterLatency;
        this.nbCache = nbCache;
        this.latencyToCacheMap = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getLatencyToCacheMap() {
        return latencyToCacheMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        return id == endpoint.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
