package common.dto;

import java.util.Map;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Endpoint {
    private int id;

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
