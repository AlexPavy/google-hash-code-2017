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
        cacheMap = new HashMap<>();
        videoMap = new HashMap<>();
        endpointMap = new HashMap<>();
    }

    public final Map<Integer,Cache> cacheMap;
    public final Map<Integer,Video> videoMap;
    public final Map<Integer,Endpoint> endpointMap;
}
