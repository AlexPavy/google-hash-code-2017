package common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Video {

    public final int id;
    public final int size;
    public final Map<Integer, Integer> requestsMap; // Key = idEndPoint, value = nbRequest
    public final List<Endpoint> requestingEndpoints = new ArrayList<>(); // build endpoints
    public final List<Cache> potentialCaches = new ArrayList<>();

    public Video(int id, int size) {
        this.id = id;
        this.size = size;
        this.requestsMap = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        return id == video.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
