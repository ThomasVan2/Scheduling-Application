package wgu.software2_c195.DAO;

import java.time.LocalDate;
/**This class is used for the lambda expression that converts the date*/
public interface Date {
    /** Lambda expression gets the selected date from the calendar picker, casts it to local date variable and then returns that variable.
     This lambda expression is used because of how concise lambda expressions are and because this piece of code is only used twice.
     @return LocalDate the cast of the Date type.*/
    public LocalDate date();

}
