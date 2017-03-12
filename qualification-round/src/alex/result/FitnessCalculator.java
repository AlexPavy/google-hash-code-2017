package alex.result;

import alex.VideoWithScore;
import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.util.HashMap;
import java.util.Map;

public class FitnessCalculator {

    private Map<String, Integer> fitnessByInputFile = new HashMap<>();

    public void addFitnessForInputFile(final String fileName, final Problem problem) {
        fitnessByInputFile.put(fileName, fitness(problem));
    }

    public int fitness(final Problem problem) {
        int totalGain = 0;
        int totalRequestNb = 0;

        for (Video video : problem.videoMap.values()) {
            for (Map.Entry<Integer, Integer> request : video.requestsMap.entrySet()) {
                int gain = getGainForRequest(request, problem, video);
                totalGain += gain * request.getValue();
                totalRequestNb += request.getValue();
            }
        }
        return (int) Math.floor(1_000d * (double) totalGain / (double) totalRequestNb);
    }

    public int getGainForRequest(Map.Entry<Integer, Integer> request, Problem problem, Video video) {
        int gain = 0;
        Endpoint endpoint = problem.endpointMap.get(request.getKey());
        for (Cache cache : problem.cacheMap.values()) {
            if (cache.videoSet.contains(new VideoWithScore(video, 0))) {
                Integer latencyToCache = endpoint.getLatencyToCacheMap().get(cache.id);
                if (latencyToCache != null) {
                    int currentGain = endpoint.datacenterLatency - latencyToCache;
                    gain = Math.max(gain, currentGain);
                }
            }
        }
        return gain;
    }

    public void printAllFilesFitness() {
        System.out.println("");
        System.out.println("Results");
        int total = 0;
        for (Map.Entry<String, Integer> fitnessForFile : fitnessByInputFile.entrySet()) {
            System.out.println(fitnessForFile.getKey() + ": " + fitnessForFile.getValue());
            total += fitnessForFile.getValue();
        }
        System.out.println("total: " + total);
    }

}
