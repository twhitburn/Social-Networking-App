import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class SocialNetworkingApp {

    /**
     * Returns a social network as defined in the file 'filename'.
     * See assignment handout on the expected file format.
     * @param filename filename of file containing social connection data
     * @return
     * @throws FileNotFoundException if file does not exist
     */
    public static SocialGraph loadConnections(String filename) throws FileNotFoundException {
    	SocialGraph temp = new SocialGraph();
    	Scanner sc = new Scanner(new File(filename));
    	while (sc.hasNextLine()) {
    		String s = sc.nextLine().substring(0, 3);
    		temp.addVertex(s);
    	}
    	sc = new Scanner(new File(filename));
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			String[] names = s.split(" ");
			if (names.length > 1) {
				for (int i = 1; i < names.length; i++) {
					temp.addEdge(names[0], names[i]);
				}
			}
		}
		sc.close();
		return temp;
    }

    static Scanner stdin = new Scanner(System.in);
    static SocialGraph graph;
    static String prompt = ">> ";  // Command prompt

    /**
     * Access main menu options to login or exit the application.
     * 
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     */
    public static void enterMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.print(prompt);
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String person = (tokens.length > 1 ? tokens[1] : null);

            switch(cmd) {
                case "login":
                    System.out.println("Logged in as " + person);
                    enterSubMenu(person);
                    System.out.println("Logged out");
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    /**
     * Access submenu options to view the social network from the perspective
     * of currUser. Assumes currUser exists in the network.
     * @param currUser
     */
    private static String printList (ArrayList<String> list) {
    	String s = "[";
		for (int i = 0; i < list.size()-1; i++) {
			s += list.get(i) + ", ";
		}
		s += list.get(list.size()-1) + "]";
		return s;
    }
    public static void enterSubMenu(String currUser) {

        // Define the set of commands that have no arguments or one argument
        String commands =
                "friends fof logout print\n" +
                "connection friend unfriend";
        Set<String> noArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[0].split(" ")));
        Set<String> oneArgCmds = new HashSet<String>
                (Arrays.asList(commands.split("\n")[1].split(" ")));

        boolean logout = false;
        while (!logout) {
            System.out.print(prompt);

            // Read user commands
            String[] tokens = stdin.nextLine().split(" ");
            String cmd = tokens[0];
            String otherPerson = (tokens.length > 1 ? tokens[1] : null);

            // Reject erroneous commands
            // You are free to do additional error checking of user input, but
            // this isn't necessary to receive a full grade.
            if (tokens.length == 0) continue;
            if (!noArgCmds.contains(cmd) && !oneArgCmds.contains(cmd)) {
                System.out.println("Invalid command");
                continue;
            }
            if (oneArgCmds.contains(cmd) && otherPerson == null) {
                System.out.println("Did not specify person");
                continue;
            }

            switch(cmd) {

            case "connection": {
            	String p2 = tokens[1];
				if (graph.getPathBetween(currUser, p2) == null) {
					System.out.println("You are not connected to " + p2);
					break;
				}
				ArrayList<String> temp = (ArrayList<String>) graph.getPathBetween(currUser, p2);
				System.out.println(printList(temp));
                break;
            }
            
            case "friends": {
                if (graph.getNeighbors(currUser).isEmpty()) {
                	System.out.println("You do not have any friends");
                	break;
                }
                ArrayList<String> temp = new ArrayList<String>();
                temp.addAll(graph.getNeighbors(currUser));
                Collections.sort(temp);
                System.out.println(printList(temp));
                break;
            }

            case "fof": {
            	ArrayList<String> temp = new ArrayList<String>();
            	temp.addAll(graph.friendsOfFriends(currUser));
            	Collections.sort(temp);
            	if (temp.isEmpty()) {
            		System.out.println("You do not have any friends of friends");
            		break;
            	}
            	System.out.println(printList(temp));
                break;
            }

            case "friend": {
            	String p2 = tokens[1];
            	if (graph.addEdge(currUser, p2)) {
            		System.out.println("You are now friends with " + p2);
            		break;
            	}
            	System.out.println("You are already friends with " + p2);
                break;
            }

            case "unfriend": {
            	String p2 = tokens[1];
            	if (graph.getNeighbors(currUser).contains(p2)) {
            		graph.removeEdge(currUser, p2);
            		System.out.println("You are no longer friends with " + p2);
            		break;
            	}
            	System.out.println("You are already not friends with " + p2);
                break;
            }
            
            case "print": {
                // This command is left here for your debugging needs.
                // You may want to call graph.toString() or graph.pprint() here
            	// You are free to modify this or remove this command entirely.
            	//
                // YOU DO NOT NEED TO COMPLETE THIS COMMAND
                // THIS COMMAND WILL NOT BE PART OF GRADING
            	System.out.println(graph.toString());
                break;
            }

            case "logout":
                logout = true;
                break;
            }  // End switch
        }
    }

    /**
     * Commandline interface for a social networking application.
     *
     * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
     *
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java SocialNetworkingApp <filename>");
            return;
        }
        try {
            graph = loadConnections(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        enterMainMenu();

    }

}
