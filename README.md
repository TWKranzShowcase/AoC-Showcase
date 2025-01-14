# Advent of Code - Base Project
This project starts off with only a handful of utility classes and matching unit tests.  However, with some basic setup, those unit tests generate boiler plate code files for a AoC days challenge.  Additional classes can fetch puzzle inputs and store to a file when configured correctly.
## Constants
In aoc.project, there is a Constants.java file.  Start by setting values to the Constants with the file.
* PATH_TO_PROJECT - this represent the folder that VS Code has open for this project.
* SESSION_ID - this is the session cookie value assigned by AoC after logging into their website.  By inspecting the page in Chrome, the session cookie can be copied and set here.  Please note that this cookie is only good for 30 days then needs to be reset.
* AOC_YEAR - set the AoC event you are particpating in
## Project Structure

`app/src/main/java` and `app/src/test/java` are the project paths to the source files.  Simularly `app/src/main/resources` and `app/src/test/resources` are the project paths to the puzzle input files.

Java Packages will follow a naming convention of `aoc/project/year####/day##` for both the puzzle code and a place for unit tests.  Resources, i.e. puzzle inputs, will path like `puzzleInputs/year####/day##`.

## Generating a set of puzzle classes
By running a unit test, puzzle classes will be generates from templates in the resource folder.  Locate the GeneratePuzzleFilesTest class, and set the parameters to the event year and day you wish to generate the files for.  Then run the unit test.</p>
Note that if you want to change base behavior, feel free to change the text file to your liking.

## Auto retrival of puzzle input
Another unit test will fetch the puzzle input for a given event year and day.  This class is called FetchPuzzleInputTest.  Locate the file and set the event year and day.  Then run the unit test.</p>
This requires that the session cookie has been set.  The session cookie can be extracted from the website source.  By inspecting the page with a tool like Chrome, the Application/Storage/Cookies will have a cookie called "session" and a long hexidecimal string for a value.  Make a copy of that session value and paste into the Constants files for the SESSION_ID value.  Proper format for the string is "session=xxxxxxx" where xxxxxxx is the value from the browser.</p>
Note: This unit test requires that the puzzle input file already exist.  The file generates unit test from above would have created an empty input file.  So if the puzzle input file is missing, just rerun the file generation unit test again.
