package SharedClasses.Objects;

import SharedClasses.Utils.UserRole;

/**
 * Created by tiber on 4/17/2016.
 */
public class User {
    int id;
    String mail;
    String password;
    UserRole role;
    int managerId;
    
    public User(){}

    public int getManagerId(){
    	return managerId;
    }
    public void setManagerId(int managerId){
    	this.managerId = managerId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User(int id, String mail, String password, UserRole role, int managerId) {

        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.managerId = managerId;
    }
}
