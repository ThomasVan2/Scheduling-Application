package wgu.software2_c195;
/**States Class*/
public class States {
    private int id;
    private String name;
    private int countryId;

    public States(int id, String name, int countryId) {

        this.name = name;
        this.id = id;
        this.countryId = countryId;

    }
    /** @return name*/
    public String getName() {
        return name;
    }
    /** @return id*/
    public int getId() {return id;}
    /** @return  country id*/
    public int getCountryId() {return countryId;}
}
