package org.example.Models;

import org.example.Managers.RequestModelManager;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestModel {
    private List<UUID> Connections;
    private Set<UUID> pendingIncomingRequests;
    private List<UUID> pendingOutgoingRequests;
    private Lock outgoingRequestLock;
    private Lock connectionsLock;
    private Lock incomingRequestLock;
    private UUID accountUuid;
    SingleTonUsersDatabase usersDb;

    public RequestModel(UUID uuid)
    {
        if (this.accountUuid == null)
        {
            this.accountUuid = uuid;
            Connections = new ArrayList<UUID>();
            pendingIncomingRequests = new HashSet<UUID>();
            pendingOutgoingRequests = new ArrayList<UUID>();
            outgoingRequestLock = new ReentrantLock();
            connectionsLock = new ReentrantLock();
            incomingRequestLock = new ReentrantLock();
            usersDb =  SingleTonUsersDatabase.getInstance();
        }
    }

    public List<String> getConnections() {
        List<String> connectionNames = new ArrayList<String>();
        for (UUID connections: this.Connections)
        {
            connectionNames.add(usersDb.getUserNames(connections));
        }
        return connectionNames;
    }

    public Set<String> getPendingIncomingRequests() {
        Set<String> connectionNames = new HashSet<String>();
        for (UUID pendingReq: this.pendingIncomingRequests)
        {
            connectionNames.add(usersDb.getUserNames(pendingReq));
        }
        return connectionNames;
    }

    public void setPendingOutgoingRequests(UUID requestAccount) {
        this.outgoingRequestLock.lock();
        try
        {
            if (this.pendingOutgoingRequests.contains(requestAccount) && this.Connections.contains(requestAccount))
            {
                System.out.printf(usersDb.getUserNames(requestAccount) + " is already in your connection or the request has alraedy been sent");
            }
            else if (this.pendingIncomingRequests.contains(requestAccount))
            {
                this.connectionsLock.lock();
                try
                {
                    this.Connections.add(requestAccount);
                    this.pendingIncomingRequests.remove(requestAccount);
                    //Todo: Send the confirmation to another account to their connections.
                    RequestModelManager.addAsConnection(accountUuid, requestAccount);
                }
                finally {
                    this.connectionsLock.unlock();
                }
            }
            else
            {
                this.pendingOutgoingRequests.add(requestAccount);
                // Todo: Send the request to another account.
                RequestModelManager.sendRequest(accountUuid, requestAccount);
            }
        }
        finally {
            this.outgoingRequestLock.unlock();
        }
    }

    public void outgoingRequestConfirmation(UUID confirmedNewConnection)
    {
        if (this.pendingOutgoingRequests.contains(confirmedNewConnection))
        {
            this.outgoingRequestLock.lock();
            this.connectionsLock.lock();
            try
            {
                this.Connections.add(confirmedNewConnection);
                this.pendingOutgoingRequests.remove(confirmedNewConnection);
            }
            finally {
                this.connectionsLock.unlock();
                this.outgoingRequestLock.unlock();
            }
        }
        else
        {
            // ToDo: Notify to remove the connection from the other account
            RequestModelManager.removeConnection(accountUuid, confirmedNewConnection);
        }
    }

    public void acceptIncomingRequest(UUID acceptIncomingRequestAccount)
    {
        if (this.pendingIncomingRequests.contains(acceptIncomingRequestAccount))
        {
            this.incomingRequestLock.lock();
            this.connectionsLock.lock();
            try
            {
                this.Connections.add(acceptIncomingRequestAccount);
                this.pendingIncomingRequests.remove(acceptIncomingRequestAccount);
            }
            finally {
                this.connectionsLock.unlock();
                this.incomingRequestLock.unlock();
            }
            // ToDo: Send Confirmation to other account.
            RequestModelManager.addAsConnection(this.accountUuid, acceptIncomingRequestAccount);
        }
    }

    public void rejectIncomingRequest(UUID rejectIncomingRequestAccount)
    {
        if (this.pendingIncomingRequests.contains(rejectIncomingRequestAccount))
        {
            this.incomingRequestLock.lock();
            try
            {
                this.pendingIncomingRequests.remove(rejectIncomingRequestAccount);
                // ToDo: Intimate other account about rejection
                RequestModelManager.removeFromOutgoingRequest(this.accountUuid, rejectIncomingRequestAccount);
            }
            finally {
                this.incomingRequestLock.unlock();
            }
        }
    }

    public void removeConnectionComingFromAnotherAccount(UUID ConnectionToBeRemoved)
    {
        if (this.Connections.contains(ConnectionToBeRemoved))
        {
            this.connectionsLock.lock();
            try{
                this.Connections.remove(ConnectionToBeRemoved);
            }
            finally {
                this.connectionsLock.unlock();
            }
        }
    }

    public void removeFromIncomingRequest(UUID IncomingRequestToBeRemoved)
    {
        if (this.pendingIncomingRequests.contains(IncomingRequestToBeRemoved))
        {
            this.incomingRequestLock.lock();
            try
            {
                this.pendingIncomingRequests.remove(IncomingRequestToBeRemoved);
            }
            finally {
                this.incomingRequestLock.unlock();
            }
        }
    }

    public void removeConnectionFromAccountOwner(UUID ConnectionToBeRemoved)
    {
        if (this.Connections.contains(ConnectionToBeRemoved))
        {
            this.connectionsLock.lock();
            try{
                this.Connections.remove(ConnectionToBeRemoved);
                // ToDo : Inform other party about removal of this account from their connections
                RequestModelManager.removeConnection(this.accountUuid, ConnectionToBeRemoved);
            }
            finally {
                this.connectionsLock.unlock();
            }
        }
    }

    public void removeOutgoingRequestFromOtherAccount(UUID requestToBeRemoved)
    {
        if (this.pendingOutgoingRequests.contains(requestToBeRemoved))
        {
            this.outgoingRequestLock.lock();
            try
            {
                this.pendingOutgoingRequests.remove(requestToBeRemoved);
            }
            finally {
                this.outgoingRequestLock.unlock();
            }
        }
        else if (this.Connections.contains(requestToBeRemoved))
        {
            System.out.println("The account you are trying to remove is in your Connections. Check there");
        }
    }

    public void removeOutgoingRequestFromAccountOwner(UUID requestToBeRemoved)
    {
        if (this.pendingOutgoingRequests.contains(requestToBeRemoved))
        {
            this.outgoingRequestLock.lock();
            try
            {
                this.pendingOutgoingRequests.remove(requestToBeRemoved);
                //ToDo: Intimate other account about rejecting request
                RequestModelManager.removeFromIncomingRequest(accountUuid, requestToBeRemoved);
            }
            finally {
                this.outgoingRequestLock.unlock();
            }
        }
        else if (this.Connections.contains(requestToBeRemoved))
        {
            System.out.println("The account you are trying to remove is in your Connections. Check there");
        }
    }

    public void updateIncomingRequest(UUID newAccount)
    {
        if(!this.pendingIncomingRequests.contains(newAccount))
        {
            this.incomingRequestLock.lock();
            try
            {
                this.pendingIncomingRequests.add(newAccount);
            }
            finally {
                this.incomingRequestLock.unlock();
            }
        }
    }

}
