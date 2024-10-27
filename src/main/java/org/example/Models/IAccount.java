package org.example.Models;

import java.util.UUID;

public interface IAccount extends IRequestAccount, IChatAccount {
    public String getProfileData();
    public UUID getAccountUuid();
    public String getName();
}
