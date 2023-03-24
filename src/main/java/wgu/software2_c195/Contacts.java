package wgu.software2_c195;
/**Contacts Class*/
public class Contacts {

    private int Id;
    private String name;
    private String email;

    public Contacts(int Id, String name, String email) {
        this.Id = Id;
        this.name = name;
        this.email = email;
    }
    /** @return id*/
    public int getId() {
        return Id;
    }
    /** @return name*/
    public String getName() {
        return name;
    }
    /** @return email*/
    public String getEmail() {
        return email;
    }
}
