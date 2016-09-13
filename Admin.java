import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class Admin {

    private List<Team> mTeamList;
    private Map<String, String> mMenu;
    private List<Player> mAvailPlayers;
    private List<Player> mUsedPlayers;
    private BufferedReader mReader;

    public Admin() {
        mAvailPlayers = new ArrayList<>(Arrays.asList(Players.load()));
        mUsedPlayers = new ArrayList<>();
        mTeamList = new ArrayList<>();
        mReader = new BufferedReader(new InputStreamReader (System.in));
        mMenu = new HashMap<>();
        mMenu.put("new", "Create a new team");
        mMenu.put("add", "Add player to existing team");
        mMenu.put("balance", "See league balance report");
        mMenu.put("print", "Print team roster");
        mMenu.put("remove", "Remove player from existing team");
        mMenu.put("report", "See team roster by height");
        mMenu.put("quit", "Exit program");
    }

    public String promptInput() throws IOException {
        System.out.printf("There are %d existing teams," +
            " and %d available players.\n", mTeamList.size(), 
            mAvailPlayers.size());
        System.out.println("Your options are:\n");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s\n", option.getKey(), option.getValue());
        }
        System.out.print("\nWhat would you like to do: ");
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
                        // 11 players to a team
                        if (mTeamList.size() < ((mAvailPlayers.size() + 
                            mUsedPlayers.size()) / 11)) {
                            createTeam();
                        } else {
                            System.out.println("There are not enough players" +
                                " to create more teams.");
                            System.out.println();
                        }
                        break;
                    case "add":
                        if (mTeamList.size() > 0) {
                            Team teamToAdd = chooseTeam(mTeamList);
                            Player player = choosePlayer(mAvailPlayers);
                            teamToAdd.addPlayer(player);
                            mUsedPlayers.add(player);
                            mAvailPlayers.remove(player);
                            System.out.printf("%s %s was added to Team %s\n",
                                    player.getFirstName(),
                                    player.getLastName(), 
                                    teamToAdd.getTeamName());
                            System.out.println();
                            break;
                        } else {
                            System.out.println("No teams have been created.");
                            break;
                        }
                    case "balance":
                        System.out.println("League Balance Report: ");
                        for (Team team : mTeamList) {
                            System.out.println(team.getTeamName());
                            printBalanceReport(team.getBalanceReport());
                            double exp = team.getExpPlayers();
                            System.out.printf("Experienced Percent: %f\n", exp);
                            System.out.println();
                        }
                        break;
                    case "print":
                        System.out.println("Which team for Balance Report: ");
                        Team teamToPrint = chooseTeam(mTeamList);
                        printTeam(teamToPrint);
                        break;
                    case "remove":
                        Team teamToRemove = chooseTeam(mTeamList);
                        System.out.println("Which player to remove: ");
                        List<Player> players = 
                                    new ArrayList<>(teamToRemove.getPlayers());
                        Player player = choosePlayer(players);
                        teamToRemove.removePlayer(player);
                        mUsedPlayers.remove(player);
                        mAvailPlayers.add(player);
                        System.out.printf("%s %s was removed from Team %s\n",
                                    player.getFirstName(),
                                    player.getLastName(),
                                    teamToRemove.getTeamName());
                        break;
                    case "report":
                        System.out.println("Which team for report: ");
                        Team teamForReport = chooseTeam(mTeamList);
                        printHeightReport(teamForReport.getHeightReport());
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
        System.out.println();
    }

    private Team chooseTeam(List list) throws IOException {
        System.out.println();
        System.out.println("Available teams: ");
        List<Team> sortedTeam = list;
        sortedTeam.sort(new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                if (team1.equals(team2)) {
                    return 0;
                }
                return team1.getTeamName().compareTo(team2.getTeamName());
            }
        });
        int index = promptForIndex(sortedTeam);
        return (Team) sortedTeam.get(index);
    }

    private Player choosePlayer(List list) throws IOException {
        System.out.println();
        System.out.println("Available players: ");
        List<Player> sortedPlayer = list;
        sortedPlayer.sort(new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                if (player1.equals(player2)) {
                    return 0;
                }
                return player1.compareTo(player2);
            }
        });
        int index = promptForIndex(sortedPlayer);
        return (Player) sortedPlayer.get(index);
    }

    private int promptForIndex(List list) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%s. %s\n", i + 1, list.get(i));
        }
        System.out.print("Your choice: ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private void printHeightReport(Map<String, Set<Player>> report) {
        Iterator it = report.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println();
            System.out.println(pair.getKey());
            System.out.println("*********");
            TreeSet<Player> players = (TreeSet) pair.getValue();
            for (Player player : players) {
                System.out.println(player);
            }
        }
        System.out.println();
    }

    private void printBalanceReport(Map<String, Integer> report) {
        Iterator it = report.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    } 

    private void printTeam(Team team) {
        System.out.println();
        System.out.println("Team Roster");
        System.out.println("***********");
        for (Player player : team.getPlayers()) {
            System.out.println(player);
        }
        System.out.println();
    }


}