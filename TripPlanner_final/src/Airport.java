import java.io.*;
import java.util.*;
/**
 * @author Senam Glover-Tay
 * @version 1.0.3
 */
public class Airport {
    /**
     * Instance Variables/Fields
     */
    private String airportCity;
    private String airportCountry;
    private String airportName;
    private int airportID;
    private String iataCode;

    static HashMap<String, ArrayList<Airport>> airportmap = new HashMap<>();


    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param city the name of the city of the airport
     * @param airportName the name of the airport
     * @param airportID the ID of the airport
     * @param country the name of the country of the airport
     * @param iataCode The three letter airport code
     */
    public Airport(String city, String airportName, int airportID, String country, String iataCode) {
        this.airportCity = city;
        this.airportName = airportName;
        this.airportID = airportID;
        this.airportCountry = country;
        this.iataCode = iataCode;
    }


    /**
     * @return the name of the city of the airport
     */
    public String getAirportCity() {
        return airportCity;
    }

    /**
     * @return the name of the airport
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * @return the ID of the airport
     */
    public int getAirportID() {
        return airportID;
    }

    /**
     * @return the name of the country of the airport
     */
    public String getAirportCountry() {
        return airportCountry;
    }

    /**
     * @return the three letter airport code
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * @param airportCity
     */
    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    /**
     * @param airportName
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * @param airportID
     */
    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    /**
     * @param airportCountry
     */
    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
    }

    /**
     * @param iataCode
     */
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    /**
     * @return a string of the airport object information
     */
    @Override
    public String toString () {
           return "City: " + this.airportCity +
                   "Country: " + this.airportCountry +
                   "Name: " + this.airportName +
                   "ID: " + this.airportID +
                   "Code: " + this.iataCode;
            }


    /**
     * @throws FileNotFoundException
     * reads the file and places data in HashMap
     */
    public static void filereader() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream("airports.csv"));

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] airportInfoArray = line.split(",");

            String keyLocation = airportInfoArray[2] + ", " + airportInfoArray[3];

            int newAirportID = Integer.parseInt(airportInfoArray[0]);
            String newAirportName = airportInfoArray[1];
            String newAirportCity = airportInfoArray[2];
            String newAirportCountry = airportInfoArray[3];
            String newAirportCode = airportInfoArray[4];

            //Creates new airport object
            Airport airportObject = new Airport(newAirportCity, newAirportName, newAirportID,
                    newAirportCountry, newAirportCode);

            String keychecker = airportObject.getAirportCity() + ", " + airportObject.getAirportCountry();

            if (airportmap.containsKey(keychecker)) {
                airportmap.get(keychecker).add(airportObject);
            } else {
                ArrayList<Airport> airportsInCity = new ArrayList<>();
                airportsInCity.add(airportObject);
                airportmap.put(keychecker, airportsInCity);
            }
    }
        sc.close();
}
}











