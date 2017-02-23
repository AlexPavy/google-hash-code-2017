package common.dto;

import java.util.Map;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Video {
    private int id;
    private int size;


    public Video(int id, int size) {
        this.id = id;
        this.size = size;
    }

    //Cle = idEndPoint, value = nbRequest
    private Map<Integer,Integer> video;

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

    public Map<Integer, Integer> getVideo() {
        return video;
    }

    public void setVideo(Map<Integer, Integer> video) {
        this.video = video;
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
