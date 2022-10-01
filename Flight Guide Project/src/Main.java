import java.io.*;
import java.util.*;

/**
 * @author Senam Glover-Tay
 * @version 1.0.3
 */
public class Main {

    /**
     * hashmap contains flight connections
     */
    static HashMap<String, ArrayList<Routes>> flightMap = new HashMap<>();

    /**
     * hashmap contains parentChildMap of airports
     */
    static HashMap<String, String> parentChildMap = new HashMap<>();

    /**
     * hashmap of direct flights and their possible airlinesMap
     */
    static HashMap<String, ArrayList<String>> airlinesMap = new HashMap<>();



    /**
     * reads from the routes file and inputs data in flightMap
     */
    public static void readRoutes() throws FileNotFoundException {
        Scanner sc = null;

        try {
            sc = new Scanner(new FileInputStream("routes.csv"));
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] routeInfoArray = line.split(",");

            String newAirlineCode = routeInfoArray[0];
            String newStartAirportCode = routeInfoArray[2];
            String newEndAirportCode = routeInfoArray[4];
            int newstops = Integer.parseInt(routeInfoArray[7]);

            //creates new routeObject
            Routes routeObject = new Routes(newAirlineCode, newStartAirportCode, newEndAirportCode, newstops);
            String keystartcity = routeInfoArray[2];

            if (flightMap.containsKey(keystartcity)) {
                flightMap.get(keystartcity).add(routeObject);
            } else {
                ArrayList<Routes> routesOfAirport = new ArrayList<>();
                routesOfAirport.add(routeObject);
                flightMap.put(keystartcity, routesOfAirport);
            }

        }
        sc.close();

    }

    /**
     * reads from the routes file and creates airlinesMap hashmap of
     * direct flights and their possible airlinesMap
     */
    public static void readAirlines() {
        Scanner sc = null;

        try {
            sc = new Scanner(new FileInputStream("routes.csv"));
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] airlineInfoArray = line.split(",");

            String newAirlineCode = airlineInfoArray[0];
            String newSourceCode = airlineInfoArray[2];
            String newDestCode = airlineInfoArray[4];
            String stop = airlineInfoArray[7];

            String keyFlightPair = newSourceCode + "," + newDestCode;

            if (airlinesMap.containsKey(keyFlightPair)) {
                airlinesMap.get(keyFlightPair).add(newAirlineCode);
            } else {
                ArrayList<String> possAirlines = new ArrayList<>();
                possAirlines.add(newAirlineCode);
                airlinesMap.put(keyFlightPair, possAirlines);
            }

        }
    }

    /**
     * @param initialAirport
     * @param targetAirport
     * @return list from solution path
     */
    public static ArrayList<String> search2(Airport initialAirport, Airport targetAirport) {
        parentChildMap.put(initialAirport.getIataCode(), "none");
        String initialAirportCode = initialAirport.getIataCode();

        Queue<String> frontier = new LinkedList<>();
        frontier.add(initialAirportCode);
        HashSet<String> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            String thisAirport = frontier.poll();
            explored.add(thisAirport);
            if (flightMap.containsKey(thisAirport)) {
                for (Routes rO : flightMap.get(thisAirport)) {
                    if (!explored.contains(rO.getEndAirportCode()) && !frontier.contains(rO.getEndAirportCode())) {
                        if (!parentChildMap.containsKey(rO.getEndAirportCode())) {
                            parentChildMap.put(rO.getEndAirportCode(), thisAirport);
                        } else {
                            parentChildMap.replace(rO.getEndAirportCode(), thisAirport);
                        }
                        if (rO.getEndAirportCode().equals(targetAirport.getIataCode())) {
                            return solutionPath(rO.getEndAirportCode());
                        }
                        frontier.add(rO.getEndAirportCode());
                        explored.add(rO.getEndAirportCode());
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param targetAirportCode
     * @return the solution path
     */
    public static ArrayList<String> solutionPath(String targetAirportCode) {

        ArrayList<String> solution = new ArrayList<>();
        solution.add(targetAirportCode);
        String thisNode = targetAirportCode;

        while (parentChildMap.containsKey(thisNode)) {
            thisNode = parentChildMap.get(thisNode);
            if (thisNode.equalsIgnoreCase("none")) {
                break;
            }
            solution.add(thisNode);
        }
        Collections.reverse(solution);
        return solution;
    }


    public static void main(String[] args) throws IOException {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the name of your file with the extension .txt");
        String fileName = keyboard.nextLine();
        //Reads from input file to obtain source and destination location
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException newFileNot) {
            System.out.println(newFileNot.getMessage());
        }
        String sourceAddress = reader.readLine();
        String destinationAddress = reader.readLine();
        System.out.printf("%s\n%s\n", sourceAddress, destinationAddress);

        Airport.filereader();
        ArrayList<Airport> possibleStartAirports = Airport.airportmap.get(sourceAddress);
        ArrayList<Airport> possibleEndAirports = Airport.airportmap.get(destinationAddress);

        reader.close();


        //finds first valid airport in possible airports of source and destination locations
        Airport source = null;
        for (int i = 0; i < possibleStartAirports.size(); i++) {
            if (!possibleStartAirports.get(i).getIataCode().equals("\\N")) {
                source = possibleStartAirports.get(i);
                break;
            }
        }
        System.out.println(source);

        Airport dest = null;
        for (int i = 0; i < possibleEndAirports.size(); i++) {
            if (!possibleEndAirports.get(i).getIataCode().equals("\\N")) {
                dest = possibleEndAirports.get(i);
                break;
            }
        }
        System.out.println(dest);
        System.out.println(source.getIataCode()+","+ dest.getIataCode());


        //read the routes and finds the solution path by calling search function
        readRoutes();
        ArrayList<String> theSolutionPath;
        System.out.println("Checking the search " + search2(source, dest));
        theSolutionPath = search2(source, dest);

        ArrayList<String> airlinePath = new ArrayList<>();

        /**
         * Get the airline path from the airlinesMap hashmap
         */
        readAirlines();
        for (int i = 0; i < theSolutionPath.size() - 1; i++) {
            System.out.println(theSolutionPath.get(i) + "," + theSolutionPath.get(i + 1));
            if (airlinesMap.containsKey(theSolutionPath.get(i) + "," + theSolutionPath.get(i + 1))) {
                String thisAirline = airlinesMap.get(theSolutionPath.get(i) + "," + theSolutionPath.get(i + 1)).get(0);
                airlinePath.add(thisAirline);
            }
        }

        System.out.println(airlinePath);

            //writes the output to a new file
            PrintWriter myWriter = null;

            try {
                myWriter = new PrintWriter(new FileOutputStream(source.getAirportCity() + "-" + dest.getAirportCity() + "_output.txt"));
            } catch (IOException e) {
                System.out.println("The task could not be completed.");
                e.printStackTrace();
            }
            int count = 0;
            int i = 0;
            int j = i+1;
            while (j < theSolutionPath.size()) {
                String sourcePort = theSolutionPath.get(i);
                String destPort = theSolutionPath.get(j);
                myWriter.println((j) + "." + airlinePath.get(i)
                        + " from " + sourcePort + " to "
                        + destPort + " 0 Stops.");
                count += 1;
                i += 1;
                j += 1;
            }
            myWriter.println("Total flights = " + i);
            myWriter.println("Total Additional stops = 0. ");
            myWriter.close();
        }
    }









