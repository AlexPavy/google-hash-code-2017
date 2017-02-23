package alex;

import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Video;

public class ScoreKey {

    public final Cache cache;
    public final Endpoint endpoint;
    public final Video video;

    public ScoreKey(Cache cache, Endpoint endpoint, Video video) {
        this.cache = cache;
        this.endpoint = endpoint;
        this.video = video;
    }

    public double buildScore() {
<<<<<<< HEAD
        return ((double) video.requests.get(endpoint.getId()))
                / ((double) endpoint.getConnections().get(cache.id));
=======
            double latency = endpoint.getConnections().get(cache.id);
            if (latency >= endpoint.datacenter_latency) {
                return 0;
            }
            return ((double) video.requests.get(endpoint.getId()))
                    / latency;
>>>>>>> 976bcc3cb0281eabd25e8de6a1d5fc4ecabb9c48
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreKey scoreKey = (ScoreKey) o;

        if (cache != null ? !cache.equals(scoreKey.cache) : scoreKey.cache != null) return false;
        if (endpoint != null ? !endpoint.equals(scoreKey.endpoint) : scoreKey.endpoint != null) return false;
        return video != null ? video.equals(scoreKey.video) : scoreKey.video == null;
    }

    @Override
    public int hashCode() {
        int result = cache != null ? cache.hashCode() : 0;
        result = 31 * result + (endpoint != null ? endpoint.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        return result;
    }
}
