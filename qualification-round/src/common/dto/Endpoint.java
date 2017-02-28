package common.dto;

import java.util.HashMap;
import java.util.Map;

public class Endpoint {
    public int id;
    public int datacenterLatency;
    public int nbCache;

    public Endpoint(int id, int datacenterLatency, int nbCache) {
        this.id = id;
        this.datacenterLatency = datacenterLatency;
        this.nbCache = nbCache;
        this.connections = new HashMap<>();
    }

    //Cle = idCache, value = latency
    public Map<Integer,Integer> connections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getConnections() {
        return connections;
    }

    public void setConnections(Map<Integer, Integer> connections) {
        this.connections = connections;
    }

}
