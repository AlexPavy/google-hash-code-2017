package common;

import alex.Calculator;
import common.dto.Cache;
import common.dto.Endpoint;
import common.dto.Problem;
import common.dto.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String inputFilename = args[0];
        String outputFilename = "output_"+inputFilename;
        //Read input
        List<String> listString =  FileUtils.readLines(args[0]);

        //Entete : 5 videos, 2 endpoints, 4 request descriptions, 3 caches,  100MB each.
        String[] tmp = listString.get(0).split(" ");
        Problem problem = new Problem();
        problem.V = Integer.parseInt(tmp[0]);
        problem.E = Integer.parseInt(tmp[1]);
        problem.R = Integer.parseInt(tmp[2]);
        problem.C = Integer.parseInt(tmp[3]);
        problem.X = Integer.parseInt(tmp[4]);

        // video
        tmp = listString.get(1).split(" ");
        for(int i = 0; i<tmp.length; i++){
            Video videoTmp = new Video(i,Integer.parseInt(tmp[i]));
            problem.videoList.put(i,videoTmp);
        }

        int lignNumber = 2;
        //Endpoint
        for(int i=0; i<problem.E; i++){
            tmp = listString.get(lignNumber).split(" "); //Description du endpoint
            Endpoint endpointTmp = new Endpoint(i,Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]));
            problem.endpointList.put(i,endpointTmp);
            for(int j=0; j<Integer.parseInt(tmp[1]);j++){
                lignNumber++;
                String[] tmpConnection = listString.get(lignNumber).split(" ");
                endpointTmp.connections.put(Integer.parseInt(tmpConnection[0]),Integer.parseInt(tmpConnection[1]));
            }
            lignNumber++;
        }


        //Requests
        for(int i=0; i<problem.R; i++){
            tmp = listString.get(lignNumber).split(" ");
            problem.videoList.get(Integer.parseInt(tmp[1])).requests.put(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[2]));
            lignNumber++;
        }


        for(int i=0; i<problem.C;i++){
            Cache cache = new Cache(i,problem.X);
            problem.cacheList.put(i,cache);
        }

        // algo
        Calculator calculator = new Calculator();
        calculator.buildScores(problem);
        calculator.addVideosInOrder();

        //contruction object
        for (String s : listString){

            System.out.println(s.toString());
        }

        List<String> output = new ArrayList<String>();
        output.add("kncdkfjnvdkfjv");
        //Write output
        FileUtils.writeLine(outputFilename,output);
    }
}
