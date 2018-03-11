package model;

import java.util.Date;

public class UserAccount {

    private String userName;
    private String password;
    private String firstName; // First name.
    private String lastName; // Last name.
    private Date dateOfBirth; // Date of birth.
    private String securityQuestion; // Security Question.
    private String email; // Email.


    /**
     * Sets an account's password.
     *
     * @param password - password
     * @author Edgar Sosa
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets user's first name.
     *
     * @param firstName - first name
     * @author Edgar Sosa
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets user's last name.
     *
     * @param lastName - last name
     * @author Edgar Sosa
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets user's date of birth.
     *
     * @param dateOfBirth
     * @author Edgar Sosa
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Sets user's security question.
     *
     * @param securityQuestion
     * @author Edgar Sosa
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * Sets the email of the user.
     *
     * @param email
     * @author Edgar Sosa
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets user's first name.
     *
     * @return User account's first name.
     * @author Edgar Sosa
     */
    public String getFirstName() {

        return firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return User account's last name.
     * @author Edgar Sosa
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user account's date of birth.
     *
     * @return User account's date of birth.
     * @author Edgar Sosa
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the user's security question.
     *
     * @return User account's security question.
     * @author Edgar Sosa
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Gets the user's email.
     *
     * @return User account's email.
     * @author Edgar Sosa
     */
    public String getEmail() {
        return email;
    }


    public UserAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassworde(String password) {
        this.password = password;
    }

    public boolean isValidCredential(String userName, String password) {
        return matchUserName(userName) && matchPassword(password);
    }

    public boolean matchUserName(String userName) {
        return userName != null && userName.equals(this.userName);
    }

    private boolean matchPassword(String password) {
        return password != null && password.equals(this.password);
    }

}