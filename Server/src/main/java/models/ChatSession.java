package models;

import io.grpc.stub.StreamObserver;
import proto.generated.Messaging;

public class ChatSession {

    private int firstChatterID;
    private int secondChatterID;

    private StreamObserver<Messaging.MessageFromServer> firstObserver;
    private StreamObserver<Messaging.MessageFromServer> secondObserver;

    private boolean isFirstOnline;
    private boolean isSecondOnline;

    public ChatSession() {

        firstChatterID = 0;
        secondChatterID = 0;

        firstObserver = null;
        secondObserver = null;

        isFirstOnline = false;
        isSecondOnline = false;
    }
    public void sendMessage(Messaging.MessageFromServer messageToSend){
        if(firstObserver != null)
            firstObserver.onNext(messageToSend);
        if(secondObserver != null)
            secondObserver.onNext(messageToSend);
    }

    public void setOffline(int chatterID){
        if(chatterID == firstChatterID){
            isFirstOnline = false;
            this.firstChatterID = 0;
            firstObserver = null;
        }
        else if(chatterID == secondChatterID){
            isSecondOnline = false;
            this.secondChatterID = 0;
            secondObserver = null;
        }
    }
    public void setOnline(int chatterID,StreamObserver<Messaging.MessageFromServer> newObserver){

        if(!isFirstOnline){
            isFirstOnline = true;
            this.firstChatterID = chatterID;
            firstObserver = newObserver;
        }
        else if(!isSecondOnline && chatterID != firstChatterID){
            isSecondOnline = true;
            this.secondChatterID = chatterID;
            secondObserver = newObserver;
        }
    }
}
