package org.example.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.example.Managers.ChatModelManager;

public class ChatModel {
    private HashMap<UUID, Messages> allMessages;
    private UUID UserUuid;
    SingleTonUsersDatabase usersDb;
    public ChatModel(UUID Uuid)
    {
        if (allMessages == null)
        {
            this.allMessages = new HashMap<UUID, Messages>();
        }
        if (this.UserUuid == null)
        {
            this.UserUuid = Uuid;
            usersDb =  SingleTonUsersDatabase.getInstance();
        }
    }
    public void sendMessage(UUID toUuid, String msg)
    {
        if (allMessages.containsKey(toUuid))
        {
            allMessages.get(toUuid).SendMessage(msg);
        }
        else
        {
            allMessages.put(toUuid, new Messages(usersDb.getUserNames(toUuid)));
            allMessages.get(toUuid).SendMessage(msg);
        }
        // ToDo: send the message to other party
        ChatModelManager.sendMessage(this.UserUuid, toUuid, msg);
    }

    public void receiveMessage(UUID fromUuid, String msg)
    {
        if (allMessages.containsKey(fromUuid))
        {
            allMessages.get(fromUuid).receiveMessage(msg);
        }
        else
        {
            allMessages.put(fromUuid, new Messages(usersDb.getUserNames(fromUuid)));
            allMessages.get(fromUuid).receiveMessage(msg);
        }
    }

}

class Messages
{
    private HashMap<Boolean, String> mapping;
    private List<UniqueMessage> message;

    public Messages(String To)
    {
        if (mapping == null) {
            mapping.put(false, "You");
            mapping.put(true, To);
        }
    }

    public void SendMessage(String msg)
    {
        message.add(new UniqueMessage(false, msg));
    }

    public void receiveMessage(String msg)
    {
        message.add(new UniqueMessage(true, msg));
    }

    public Multimap<String, String> showChat()
    {
        Multimap<String, String> allChat = ArrayListMultimap.create();
        for (UniqueMessage uniqueMessage: this.message)
        {
            allChat.put(mapping.get(uniqueMessage.getIndicator()), uniqueMessage.getMessage());
        }
        return allChat;
    }
}

class UniqueMessage
{
    private Boolean indicator;
    private String message;

    public UniqueMessage(Boolean ind, String msg)
    {
        this.indicator = ind;
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getIndicator() {
        return indicator;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}