package org.example.Managers;

import org.example.Models.IAccount;
import org.example.Models.IRequestAccount;
import org.example.Models.SingleTonUsersDatabase;

import java.util.UUID;

public class RequestModelManager {

    public static void addAsConnection(UUID source , UUID dest)
    {
        IRequestAccount targetAccount = getDestinationRequestAccount(dest);
        targetAccount.outgoingRequestConfirmation(source);
    }

    public static void removeConnection(UUID source , UUID dest)
    {
        IRequestAccount targetAccount = getDestinationRequestAccount(dest);
        targetAccount.removeConnectionComingFromAnotherAccount(source);
    }

    public static void removeFromOutgoingRequest(UUID source , UUID dest)
    {
        IRequestAccount targetAccount = getDestinationRequestAccount(dest);
        targetAccount.removeOutgoingRequestFromOtherAccount(source);
    }

    public static void removeFromIncomingRequest(UUID source , UUID dest)
    {
        IRequestAccount targetAccount = getDestinationRequestAccount(dest);
        targetAccount.removeFromIncomingRequest(source);
    }

    public static void sendRequest(UUID source , UUID dest)
    {
        IRequestAccount targetAccount = getDestinationRequestAccount(dest);
        targetAccount.updateIncomingRequest(source);
    }

    private static IRequestAccount getDestinationRequestAccount(UUID uuid)
    {
        return (IRequestAccount) SingleTonUsersDatabase.getInstance().getUser(uuid);
    }
}
