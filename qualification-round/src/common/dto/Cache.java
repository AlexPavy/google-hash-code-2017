package common.dto;

import java.util.List;


public class Cache {

    public int id;
    public int size;
    public int totalSize;
    public List<Video> videoList;

    public Cache(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String printVideos() {
        String toString = id+"";
        for (Video video : getVideoList()){
            toString +=  " " + video.id;
        }
        return toString;
    }

}
