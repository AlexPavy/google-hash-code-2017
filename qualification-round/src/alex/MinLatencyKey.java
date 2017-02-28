package alex;

public class MinLatencyKey {

    private final int endpointId;
    private final int videoId;

    public MinLatencyKey(int endpointId, int videoId) {
        this.endpointId = endpointId;
        this.videoId = videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinLatencyKey that = (MinLatencyKey) o;

        if (endpointId != that.endpointId) return false;
        return videoId == that.videoId;
    }

    @Override
    public int hashCode() {
        int result = endpointId;
        result = 31 * result + videoId;
        return result;
    }
}
