package org.example.Managers;

import org.example.Models.IChatAccount;
import org.example.Models.SingleTonUsersDatabase;

import java.util.UUID;

public class ChatModelManager {
    public static void sendMessage(UUID fromUuid, UUID toUuid, String Message)
    {
        IChatAccount chatAccount = getChatAccount(toUuid);
        chatAccount.receiveMessage(fromUuid, Message);
    }
    private static IChatAccount getChatAccount(UUID uuid)
    {
        return (IChatAccount) SingleTonUsersDatabase.getInstance().getUser(uuid);
    }
}
