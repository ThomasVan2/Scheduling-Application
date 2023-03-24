package wgu.software2_c195.DAO;

import java.time.LocalTime;
/**This interface is used for the lambda expression that sets the time.*/
public interface Time {
    /** This method gets the hours and minutes and casts them to LocalTime.
     * The lambda expression is used because of how concise the expression is versus a whole method and because this code is only used twice.
      @param HH hours
     @param MM minutes
     @return returns LocalTime.*/
    public LocalTime getTime(int HH, int MM);

}
