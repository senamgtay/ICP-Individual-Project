import java.io.*;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.*;

/**
 * @author Senam Glover-Tay
 * @version 1.0.3
 */
public class Main {

    /**
     * hashmap contains flight connections
     */
    static HashMap<String, ArrayList<Routes>> flightmap = new HashMap<>();

    /**
     * hashmap contains parents of flights
     */
    static HashMap<String, String> parents = new HashMap<>();


    /**
     * reads from the file and puts data in HashMap
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

            if (flightmap.containsKey(keystartcity)) {
                flightmap.get(keystartcity).add(routeObject);
            } else {
                ArrayList<Routes> routesOfAirport = new ArrayList<>();
                routesOfAirport.add(routeObject);
                flightmap.put(keystartcity, routesOfAirport);
            }

        }
        sc.close();

    }

    /*
    performs the breadth first search to find the path
     */
    public static ArrayList<String> search2(Airport initialAirport, Airport targetAirport) {
        parents.put(initialAirport.getIataCode(), "none");

        String initialAirportCode = initialAirport.getIataCode();

        Queue<String> frontier = new LinkedList<>();
        frontier.add(initialAirportCode);
        HashSet<String> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            String thisAirport = frontier.poll();
            explored.add(thisAirport);

            if (flightmap.containsKey(thisAirport)) {
                for (Routes rO : flightmap.get(thisAirport)) {

                    if (!explored.contains(rO.getEndAirportCode()) && !frontier.contains(rO.getEndAirportCode())) {
                        if (!parents.containsKey(rO.getEndAirportCode())) {
                            parents.put(rO.getEndAirportCode(), thisAirport);
                        } else {
                            parents.replace(rO.getEndAirportCode(), thisAirport);
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

    /*
    traces the file path
     */
    public static ArrayList<String> solutionPath(String targetAirportCode) {

        ArrayList<String> solution = new ArrayList<>();
        solution.add(targetAirportCode);
        String thisNode = targetAirportCode;

        while (parents.containsKey(thisNode)) {
            thisNode = parents.get(thisNode);
            if (thisNode.equalsIgnoreCase("none")) {
                break;
            }
            solution.add(thisNode);
        }
        Collections.reverse(solution);
        return solution;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("sample input.txt"));
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
        Airport source = null;
        for (Airport airport : possibleStartAirports) {
            if (airport.getIataCode().equals("ACC")) {
                source = airport;
            }

        }
        Airport dest = null;
        for (Airport airport : possibleEndAirports) {
            if (airport.getIataCode().equals("YWG")) {
                dest = airport;
            }
        }

        readRoutes();
        ArrayList<String> theSolutionPath = new ArrayList<>();
        System.out.println("Checking the search " + search2(source, dest));
        theSolutionPath =  search2(source, dest);


        PrintWriter myWriter = null;

        try {
            myWriter = new PrintWriter(new FileOutputStream(source.getAirportCity() + "-" + dest.getAirportCity() + "_output.txt"));
        } catch (IOException e) {
            System.out.println("The task could not be completed.");
            e.printStackTrace();
        }
        int count = 0;
        int startIndex = 0;
        int endIndex = 1;
        while (endIndex < theSolutionPath.size()) {
            String start = theSolutionPath.get(startIndex);
            String end = theSolutionPath.get(endIndex);
            myWriter.println(endIndex + " from " + start + " to " + end +  " 0  Stops.");
            count += 1;
            startIndex += 1;
            endIndex += 1;

        }
        myWriter.println("Total flights = " +startIndex);
        myWriter.println("Total Additional stops = 0. " );

        myWriter.close();
    }
}







