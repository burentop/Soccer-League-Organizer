import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class Admin {

    private List<Team> mTeamList;
    private Map<String, String> mMenu;
    private Player[] mPlayerMasterList;
    private List<Player> mUsedPlayers;
    private BufferedReader mReader;

    public Admin() {
        mPlayerMasterList = Players.load();
        mUsedPlayers = new ArrayList<>();
        mTeamList = new ArrayList<>();
        mReader = new BufferedReader(new InputStreamReader (System.in));
        mMenu = new HashMap<>();
        mMenu.put("new", "Create a new team");
        mMenu.put("quit", "Exit program");
    }

    public String promptInput() throws IOException {
        System.out.printf("There are %d existing teams," +
            " and %d available players.", mTeamList.size(), 
            mPlayerMasterList.length - mUsedPlayers.size());
        System.out.println("Your options are:");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s\n", option.getKey(), option.getValue());
        }
        System.out.print("What would you like to do: ");
        String option = mReader.readLine();
        return option.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptInput();
                switch (choice) {
                    case "new":
                        createTeam();
                        break;
                    case "quit":
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.printf("Unknown choice: %s. Try again.\n", 
                            choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input.");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    private void createTeam() {
        String teamName = "";
        String coach = "";
        try {
            System.out.print("What is the name of the team: ");
            teamName = mReader.readLine();
            System.out.print("What is the coach's name: ");
            coach = mReader.readLine();
        } catch (IOException ioe) {
            System.out.println("Problem with input.");
            ioe.printStackTrace();
        }
        Team team = new Team(coach, teamName);
        mTeamList.add(team);
        System.out.printf("Team %s, with %s as the coach, has been created.\n",
            teamName, coach);
    }


}