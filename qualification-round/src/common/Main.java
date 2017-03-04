package common;

import alex.CommonListScoreCalculator;
import alex.DataPreparator;
import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args[0] == null) {
            System.out.println("No filename specified");
            return;
        }
        if (args[0].equals("all")) {
            executeForFileName("kittens");
            executeForFileName("me_at_the_zoo");
            executeForFileName("trending_today");
            executeForFileName("videos_worth_spreading");
        } else {
            executeForFileName(args[0]);
        }
    }

    public static void executeForFileName(String arg) throws IOException {
        System.out.println("executeForFileName : " + arg);
        final String inputFilename = arg + ".in";
        final String outputFilename = arg + ".out";

        final List<String> listString = FileUtils.readLines(inputFilename);
        String[] tmp = listString.get(0).split(" ");
        Problem problem = readProblemData(listString, tmp);

        problem = new DataPreparator().prepareData(problem);

        // algo
        System.out.println("Algo starts");
        CommonListScoreCalculator scoreCalculator = new CommonListScoreCalculator(problem);
        scoreCalculator.buildScores();
        System.out.println("Algo ends");

        // output
        writeToFile(outputFilename, problem);
    }

    private static Problem readProblemData(List<String> listString, String[] tmp) {
        System.out.println("readProblemData begins");
        Problem problem = new Problem();
        problem.V = Integer.parseInt(tmp[0]);
        problem.E = Integer.parseInt(tmp[1]);
        problem.R = Integer.parseInt(tmp[2]);
        problem.C = Integer.parseInt(tmp[3]);
        problem.X = Integer.parseInt(tmp[4]);

        tmp = listString.get(1).split(" ");
        for (int i = 0; i < tmp.length; i++) {
            problem.videoList.put(i, new Video(i, Integer.parseInt(tmp[i])));
        }

        int lineNumber = 2;
        for (int i = 0; i < problem.E; i++) {
            tmp = listString.get(lineNumber).split(" ");
            Endpoint endpointTmp = new Endpoint(i, Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
            problem.endpointList.put(i, endpointTmp);
            for (int j = 0; j < Integer.parseInt(tmp[1]); j++) {
                lineNumber++;
                String[] tmpConnection = listString.get(lineNumber).split(" ");
                endpointTmp.connections.put(Integer.parseInt(tmpConnection[0]), Integer.parseInt(tmpConnection[1]));
            }
            lineNumber++;
        }

        for (int i = 0; i < problem.R; i++) {
            tmp = listString.get(lineNumber).split(" ");
            problem.videoList.get(Integer.parseInt(tmp[0])).requests.put(
                    Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2])
            );
            lineNumber++;
        }

        for (int i = 0; i < problem.C; i++) {
            Cache cache = new Cache(i, problem.X);
            problem.cacheList.put(i, cache);
        }
        System.out.println("readProblemData ended");
        return problem;
    }

    private static void writeToFile(String outputFilename, Problem problem) throws IOException {
        System.out.println("writeToFile begins");
        List<String> output = new ArrayList<>();
        output.add(problem.cacheList.size() + "");
        for (Integer s : problem.cacheList.keySet()) {
            output.add(problem.cacheList.get(s).printVideos());
        }
        FileUtils.writeLine(outputFilename, output);
        System.out.println("writeToFile ended");
    }
}
