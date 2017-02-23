package common.dto;

import java.util.List;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Cache {
    public int id;
    public int size;
    public int totalSize;
    public List<Video> videoList;

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


}
