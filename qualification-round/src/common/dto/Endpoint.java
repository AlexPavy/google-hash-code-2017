package common.dto;

import java.util.Map;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Endpoint {
    public int id;
    public int datacenter_latency;
    public int nbCache;

    public Endpoint(int id, int datacenter_latency, int nbCache) {
        this.id = id;
        this.datacenter_latency = datacenter_latency;
        this.nbCache = nbCache
    }

    //Cle = idCache, value = latency
    private Map<Integer,Integer> connections;

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
