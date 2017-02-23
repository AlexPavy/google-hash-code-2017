package alex;

import common.dto.Cache;
import common.dto.Problem;
import common.dto.Video;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class DataWriter {

    public void write(Problem problem) {
        try{
            PrintWriter writer = new PrintWriter(
                    "qualification-round/src/alex/alex.out", "UTF-8");

            Collection<Cache> caches = problem.cacheList.values();
            System.out.println(caches.size());
            for (Cache cache:caches) {
                if (cache.getVideoList().size() > 0) {
                    System.out.println(cache.id);
                    for (Video video:cache.getVideoList()) {
                        System.out.println(" " + video.id);
                    }
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}
