package common;

import common.dto.Problem;

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
        problem.V = Integer.getInteger(tmp[0]);
        problem.E = Integer.getInteger(tmp[1]);
        problem.R = Integer.getInteger(tmp[2]);
        problem.C = Integer.getInteger(tmp[3]);
        problem.X = Integer.getInteger(tmp[4]);

        //Tail video
        tmp = listString.get(0).split(" ");



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
