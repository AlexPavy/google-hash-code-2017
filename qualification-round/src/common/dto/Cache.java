package common.dto;

import java.util.ArrayList;
import java.util.List;


public class Cache {

    public final int id;
    public final int size;
    public final List<Video> videoList;

    public int currentSize;

    public Cache(int id, int size) {
        this.id = id;
        this.size = size;
        videoList = new ArrayList<>();
        currentSize = 0;
    }

    public boolean addVideoIfPossible(Video video) {
        if (currentSize + video.size <= size) {
            videoList.add(video);
            currentSize += video.size;
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull() {
        return currentSize == size;
    }

    public int getSize() {
        return size;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public String printVideos() {
        String toString = id + "";
        for (Video video : getVideoList()){
            toString +=  " " + video.id;
        }
        return toString;
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
