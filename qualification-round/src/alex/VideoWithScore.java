package alex;

import common.dto.Video;

public class VideoWithScore {

    public final Video video;
    public final double score;

    public VideoWithScore(Video video, double score) {
        this.video = video;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoWithScore that = (VideoWithScore) o;

        return video.equals(that.video);
    }

    @Override
    public int hashCode() {
        return video.hashCode();
    }
}
