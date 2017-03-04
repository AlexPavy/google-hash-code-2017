package common.dto;

import java.util.HashMap;
import java.util.Map;

public class Problem {

    public int V; //nb video
    public int E; //nb endpoint
    public int R; //nb request descriptions
    public int C; //nb cache
    public int X; //cache capacity

    public Problem() {
        cacheList = new HashMap<>();
        videoList = new HashMap<>();
        endpointList = new HashMap<>();
    }

    public final Map<Integer,Cache> cacheList;
    public final Map<Integer,Video> videoList;
    public final Map<Integer,Endpoint> endpointList;
}
