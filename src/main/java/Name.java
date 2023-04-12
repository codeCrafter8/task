import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name {
    private String firstname;
    private String lastname;
    public String getFirstName(){
        return firstname;
    }
    public String getLastName(){
        return lastname;
    }
}
