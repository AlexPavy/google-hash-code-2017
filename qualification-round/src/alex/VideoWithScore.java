package alex;

import common.dto.Video;

public class VideoWithScore {

    public final Video video;
    public final double score;

    public VideoWithScore(Video video, double score) {
        this.video = video;
        this.score = score;
    }
}
