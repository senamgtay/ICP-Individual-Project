/**
 * @author Senam Glover-Tay
 * @version 1.0.3
 */
public class Routes {
    /**
     * Instance Variables/Fields
     */
    private String airlineCode;
    private String startAirportCode;
    private String endAirportCode;
    private int stops;


    /**
     * Constructor:
     * Build and initialise objects of this class
     * @param airlineCode      The two lettered airline code
     * @param startAirportCode the code of the source airport
     * @param endAirportCode   the code of the target airport
     * @param stops            the number of stops on the flight
     */
    public Routes(String airlineCode, String startAirportCode, String endAirportCode, int stops) {
        this.airlineCode = airlineCode;
        this.startAirportCode = startAirportCode;
        this.endAirportCode = endAirportCode;
        this.stops = stops;
    }

    /**
     * @return The two lettered airline code
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * @param airlineCode
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    /**
     * @return
     */
    public String getStartAirportCode() {
        return startAirportCode;
    }

    /**
     * @param startAirportCode
     */
    public void setStartAirportCode(String startAirportCode) {
        this.startAirportCode = startAirportCode;
    }

    /**
     * @return
     */
    public String getEndAirportCode() {
        return endAirportCode;
    }

    /**
     * @param endAirportCode
     */
    public void setEndAirportCode(String endAirportCode) {
        this.endAirportCode = endAirportCode;
    }

    /**
     * @return
     */
    public int getStops() {
        return stops;
    }

    /**
     * @param stops
     */
    public void setStops(int stops) {
        this.stops = stops;
    }


}






