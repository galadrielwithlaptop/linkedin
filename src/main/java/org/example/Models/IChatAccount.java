package org.example.Models;

import java.util.UUID;

public interface IChatAccount {
    public void receiveMessage(UUID fromUuid, String Message);
}
