package finalcompilation.finalfivemin.entity;

import java.io.Serializable;

/**
 * Created by Hengjie on 27/03/2017.
 */
public class User implements Serializable{

    private int id;
    private String name;
    private String email;
    private String password;
    private String credential;
    private int professional;
    private int preference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String getCredential(){return credential;}

    public void setCredential(String credential) {this.credential = credential;}

    public int getProfessional() {return professional;}

    public void setProfessional(int professional){this.professional = professional;}

    public int getPreference() {return preference;}

    public void setPreference(int preference) {this.preference = preference;}

}
