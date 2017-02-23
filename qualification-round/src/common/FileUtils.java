package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

	public static List<String> readLines(String fileName) throws IOException {
		if(null==fileName){
			throw new InvalidParameterException("Le parametre 'fileInputStream' est null.");
		}
        List<String> lines = Files.readAllLines(Paths.get("qualification-round/" +fileName));

		return lines;
	}

	public static boolean writeLine(String fileName, List<? extends Object> toWrite) throws IOException {
        //List-Object to String
        List<String> lines = toWrite.stream().map(Object::toString).collect(Collectors.toList());
        Files.write(Paths.get(fileName), lines);
        return true;
    }
}
