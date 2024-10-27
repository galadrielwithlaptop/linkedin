package org.example.Models;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IUserAccount {

    public List<String> getConnections();
    public UUID getAccountUuid();
    public void setEducation(String ed);
    public void setExperience(String exp);
    public void setProfilePicture(BufferedImage picture);
    public void setHeadline(String headline);
    public Set<String> getPendingIncomingRequests();
    public void setPendingOutgoingRequests(UUID requestAccount);
    public void acceptIncomingRequest(UUID acceptIncomingRequestAccount);
    public void rejectIncomingRequest(UUID rejectIncomingRequestAccount);
    public void removeConnectionFromAccountOwner(UUID ConnectionToBeRemoved);
    public void removeOutgoingRequestFromAccountOwner(UUID requestToBeRemoved);
    public void sendMessage(UUID toUuid, String msg);
}
