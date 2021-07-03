package src;

import org.jibble.pircbot.*;
import packages.apiCall;

public class MyBot extends PircBot {
    public MyBot() {
        this.setName("rsp180007");
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.contains("weather")) {
            apiCall.getConnections(75019);
        }
        if (message.contains("Hello")) {
            sendMessage(channel, "Hey " + sender + "!");
        }  
    }
}