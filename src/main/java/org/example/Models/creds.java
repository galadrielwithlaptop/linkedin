package org.example.Models;

import java.util.UUID;

public class creds {
    private String email;
    private String password;
    private IUserAccount userAccount;

    public creds(String mail, String pswrd, String UserName)
    {
        if (email == null && password == null)
        {
            this.email = mail;
            this.password = pswrd;
            this.userAccount = new Account(UserName, UUID.randomUUID());
            // Todo: Add in the USER Database
        }
        else
        {
            System.out.println("Wrong attempt to initialize email and password");
        }
    }

    public String showCreds(String mailId, String pswrd) {
        if (this.email.equals(mailId) && this.password.equals(pswrd))
        {
            return String.join(" , ", email, password);
        }
        else {
            System.out.println("Wrong attempt. Password/EmailId did not match. Retry again.");
        }
        return "";
    }

    public IUserAccount getUserInfo(String mailId, String pswrd)
    {
        if (this.email.equals(mailId) && this.password.equals(pswrd))
        {
            return (IUserAccount) this.userAccount;
        }
        else {
            System.out.println("Wrong attempt. Password/EmailId did not match. Retry again.");
        }
        return null;
    }
}
