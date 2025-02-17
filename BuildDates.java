import java.io.*;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BuildDates {
    private static final String BUILD_DATE_HISTORY_FILE = "build_date_history.txt";

    // history of previous pairs for build dates

    public static void main(String[] args){
        List<String> buildLeaders = new ArrayList<>(Arrays.asList("lexi", "nick", "madi", "zaria", "jae", "kay", "jake", "sienna", "james"));
        // list of build leaders

        // history of past build dates created by this program
        Set<String> pastBuildDates = loadDateHistory();

        // scanner for name input
        Scanner input = new Scanner(System.in);
        System.out.println("What's your name?:");
        String buildLeaderName = input.nextLine().toLowerCase(); // converts input to lowercase :)
        System.out.println("Hey " + buildLeaderName + ", let's find your BUILD date this week!");

        if (!buildLeaders.contains(buildLeaderName)){
            System.out.println("Sorry, I don't know that name! Could you try again? :)");
            return;
        }
        // read as follows: if the name inputted does not equal the names on the list, input another name

        Collections.shuffle(buildLeaders);
        // randomizes the list of buildLeaders :)

        // finds unique pairings!
        String buildDate = null;
        for (String buildLeader : buildLeaders) {
            if (!buildLeader.equals(buildLeaderName)){
                String buildDatePair = buildLeaderName + "&" + buildLeader;
                String reverseBuildDatePair = buildLeader + "&" + buildLeaderName;
                if (!pastBuildDates.contains(buildDatePair) && !pastBuildDates.contains(reverseBuildDatePair)){
                    buildDate = buildLeader;
                    pastBuildDates.add(buildDatePair);
                    break;
                }

                // essentially, if the build leader on the list = the name inputted, then the program will find 
                // a person on the list to pair with, and, if the past build dates that the program created and stored does not 
                // include the pair we just created, then it'll add it to the data, and will not repeat it.

                // for example, if madi types in her name, the system will find a date for madi, and put it into the system to make sure that
                // the next person that uses it does not get madi + the past build date.
            }
        }

        if (buildDate != null) {
            System.out.println("Okay " + buildLeaderName + ", we found it! Your BUILD date for this week is with " + buildDate + ".");
            saveDateHistory(pastBuildDates);
        } else {
            System.out.println("Sorry " + buildLeaderName + ", there's no unique pairs available. But, I can show you who I calculated anyway. Your BUILD date is supposed to be with " + buildDate + ".");

        }
        // essentially, if the person is found, then the message is sent. if the person isn't found, then the second message will be sent.
    }

    // these two methods:

    // first one will read the date history and format it like buildleader1 & buildleader2
    private static Set<String> loadDateHistory() {
        Set<String> dates = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BUILD_DATE_HISTORY_FILE))){
            String line;
            while ((line = reader.readLine()) != null){
                dates.add(line);
            }
        } catch (IOException e){
            // no history file exists yet
        }
        return dates;
    }

// second one will write a line in our build date history file, and make sure that the date history is saved in the program
    private static void saveDateHistory(Set<String> dates) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUILD_DATE_HISTORY_FILE))){
            for (String date : dates){
                writer.write(date);
                writer.newLine();
            }

        } catch (IOException e){
            System.err.println("Error saving BUILD date history: " + e.getMessage());
            // error to let me know just in case something goes wrong :(
        }
    }
}