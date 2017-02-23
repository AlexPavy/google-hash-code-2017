package common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
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

    public Map<Integer,Cache> cacheList;
    public Map<Integer,Video> videoList;
    public Map<Integer,Endpoint> endpointList;

}
