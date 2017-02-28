package common.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Video {

    public int id;
    public int size;

    public Video(int id, int size) {
        this.id = id;
        this.size = size;
        this.requests = new HashMap<>();
    }

    // Key = idEndPoint, value = nbRequest
    public final Map<Integer,Integer> requests;

    // build endpoints
    public final List<Endpoint> possibleEndpoints = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
