package org.example.Models;

import java.util.UUID;

public interface IRequestAccount {
    public void outgoingRequestConfirmation(UUID confirmedNewConnection);
    public void removeConnectionComingFromAnotherAccount(UUID ConnectionToBeRemoved);
    public void removeOutgoingRequestFromOtherAccount(UUID requestToBeRemoved);
    public void updateIncomingRequest(UUID newAccount);
    public void removeFromIncomingRequest(UUID IncomingRequestToBeRemoved);
    public UUID getAccountUuid();
}
