package org.example.Models;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleTonUsersDatabase {
    private static SingleTonUsersDatabase singleTonUsersDatabase;
    private HashMap<UUID, IAccount> usersDB;
    private Lock userDBlock;

    public void initialize()
    {
        if (singleTonUsersDatabase == null)
        {
            singleTonUsersDatabase = new SingleTonUsersDatabase();
        }
    }
    private SingleTonUsersDatabase()
    {
        usersDB = new HashMap<UUID, IAccount>();
        userDBlock = new ReentrantLock();
    }

    public static SingleTonUsersDatabase getInstance()
    {
        if (singleTonUsersDatabase != null)
        {
            return singleTonUsersDatabase;
        }
        return null;
    }

    public void addUser(UUID uuid, IAccount account)
    {
        this.userDBlock.lock();
        try
        {
            if (!this.usersDB.containsKey(uuid))
            {
                this.usersDB.put(uuid, account);
            }
        }
        finally {
            this.userDBlock.unlock();
        }
    }

    public IAccount getUser(UUID uuid)
    {
        IAccount account = null;
        if (this.usersDB.containsKey(uuid))
        {
            account = this.usersDB.get(uuid);
        }
        return account;
    }

    public String getUserNames(UUID uuid)
    {
        IAccount account = null;
        if (this.usersDB.containsKey(uuid))
        {
            account = this.usersDB.get(uuid);
        }
        return account.getName();
    }

}
