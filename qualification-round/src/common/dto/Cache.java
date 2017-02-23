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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cache cache = (Cache) o;

        return id == cache.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
