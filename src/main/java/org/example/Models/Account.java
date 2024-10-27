package org.example.Models;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Account implements IAccount, IUserAccount{
    private String Name;
    private UUID accountUuid;
    private ProfileDetails profileDetails;
    private RequestModel requestDetails;
    private ChatModel chatBoxDetails;

    public Account(String Name, UUID Uuid)
    {
        this.Name = Name;
        this.accountUuid = Uuid;
        this.profileDetails = new ProfileDetails();
        this.chatBoxDetails = new ChatModel(Uuid);
        this.requestDetails = new RequestModel(Uuid);
    }

    public void setEducation(String ed)
    {
        profileDetails.setEducation(ed);
    }

    public void setExperience(String exp)
    {
        profileDetails.setExperience(exp);
    }

    public void setProfilePicture(BufferedImage picture)
    {
        profileDetails.setProfilePicture(picture);
    }

    public void setHeadline(String headline)
    {
        profileDetails.setHeadline(headline);
    }

    public String getProfileData()
    {
        return profileDetails.toString();
    }

    public List<String> getConnections()
    {
        return requestDetails.getConnections();
    }

    @Override
    public UUID getAccountUuid() {
        return accountUuid;
    }

    public String getName() {
        return Name;
    }

    public Set<String> getPendingIncomingRequests() {
        return requestDetails.getPendingIncomingRequests();
    }

    public void setPendingOutgoingRequests(UUID requestAccount) {
        requestDetails.setPendingOutgoingRequests(requestAccount);
    }

    public void outgoingRequestConfirmation(UUID confirmedNewConnection)
    {
        requestDetails.outgoingRequestConfirmation(confirmedNewConnection);
    }

    public void acceptIncomingRequest(UUID acceptIncomingRequestAccount)
    {
        requestDetails.acceptIncomingRequest(acceptIncomingRequestAccount);
    }

    public void rejectIncomingRequest(UUID rejectIncomingRequestAccount)
    {
        requestDetails.rejectIncomingRequest(rejectIncomingRequestAccount);
    }

    public void removeConnectionComingFromAnotherAccount(UUID ConnectionToBeRemoved)
    {
        requestDetails.removeConnectionComingFromAnotherAccount(ConnectionToBeRemoved);
    }

    public void removeConnectionFromAccountOwner(UUID ConnectionToBeRemoved)
    {
        requestDetails.removeConnectionFromAccountOwner(ConnectionToBeRemoved);
    }

    public void removeOutgoingRequestFromOtherAccount(UUID requestToBeRemoved)
    {
        requestDetails.removeOutgoingRequestFromOtherAccount(requestToBeRemoved);
    }

    public void removeOutgoingRequestFromAccountOwner(UUID requestToBeRemoved)
    {
        requestDetails.removeOutgoingRequestFromAccountOwner(requestToBeRemoved);
    }

    public void updateIncomingRequest(UUID newAccount)
    {
        requestDetails.updateIncomingRequest(newAccount);
    }

    public void removeFromIncomingRequest(UUID IncomingRequestToBeRemoved)
    {
        requestDetails.removeFromIncomingRequest(IncomingRequestToBeRemoved);
    }

//    Chatting
    @Override
    public void sendMessage(UUID toUuid, String msg)
    {
        this.chatBoxDetails.sendMessage(toUuid, msg);
    }

    @Override
    public void receiveMessage(UUID fromUuid, String Message) {
        this.chatBoxDetails.receiveMessage(fromUuid, Message);
    }
}

