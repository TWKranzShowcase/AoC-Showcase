package aoc.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.apache.commons.lang3.StringUtils;

import aoc.project.Constants;

public class FetchPuzzleInput {
    private String urlPattern = "https://adventofcode.com/{{yearNum}}/day/{{dayNum}}/input";
    private final String pathToProject;

    public FetchPuzzleInput(String fileSystemPathToAocProject) {
        pathToProject = fileSystemPathToAocProject;
    }

    public boolean fetchPuzzleInput(int year, int day) {
        String urlString = StringUtils.replace(StringUtils.replace(urlPattern, "{{yearNum}}", Integer.toString(year)),
                "{{dayNum}}", Integer.toString(day));

        URL url = null;
        HttpURLConnection con = null;
        Path puzzleInput = Path.of(pathToProject, "app/src/main/resources/puzzleInputs", "year" + year,
                    "day" + String.format("%02d", day) + "Puzzle.txt");

        if(!Files.exists(puzzleInput)){
            System.err.println("Puzzle Input file does not exist.  Be sure to run Generate Puzzle Files first.");
            return false;
        }

        try {
            System.out.println("Fetching Puzzle Input from: " + urlString);
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("cookie", Constants.SESSION_ID);

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();

            InputStream inputStream = null;

            if (status > 299) {
                inputStream = con.getErrorStream();
            } else {
                inputStream = con.getInputStream();
            }

            

            Files.copy(inputStream, puzzleInput, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return true;
    }
}
