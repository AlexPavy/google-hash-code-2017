package common.dto;

import java.util.Map;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Video {
    public int id;
    public int size;


    public Video(int id, int size) {
        this.id = id;
        this.size = size;
    }

    //Cle = idEndPoint, value = nbRequest
    public Map<Integer,Integer> requests;

}
