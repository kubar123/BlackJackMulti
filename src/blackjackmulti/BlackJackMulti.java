/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackmulti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.net.*;
import java.io.*;

/**
 *
 * @author Jakub Rybicki
 */
public class BlackJackMulti extends Thread {

    public BlackJackMulti(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    private static final int ACE_VALUE = 11;
    private static final int FACE_VALUE = 10;

    public static Integer serverPort = 8080;
	private static ServerSocket serverSocket = null;

    //maximum number of players WITHOUT the dealer
    private static int numberOfPlayers = 5;
    
//The list of playing cards we will be using
    private static String mDeck[] = {
        "♠ 2", "♠ 3", "♠ 4", "♠ 5", "♠ 6", "♠ 7", "♠ 8", "♠ 9", "♠ 10",
        "♠ K", "♠ Q", "♠ J", "♠ ACE", "♥ 2", "♥ 3", "♥ 4", "♥ 5", "♥ 6",
        "♥ 7", "♥ 8", "♥ 9", "♥ 10", "♥ K", "♥ Q", "♥ J", "♥ ACE", "♦ 2",
        "♦ 3", "♦ 4", "♦ 5", "♦ 6", "♦ 7", "♦ 8", "♦ 9", "♦ 10", "♦ K",
        "♦ Q", "♦ J", "♦ ACE", "♣ 2", "♣ 3", "♣ 4", "♣ 5", "♣ 6", "♣ 7",
        "♣ 8", "♣ 9", "♣ 10", "♣ K", "♣ Q", "♣ J", "♣ ACE"
    };

//    //the dealers current hand
//    private static Set<String> mDealerHand;
//    //the dealers point value
//    private static int mDealerPoints;
    //all players current hand and points
    // [0] = dealer ALWAYS
    public static ArrayList<ArrayList<String>> mPlayerHand = new ArrayList<>();
    //private static List<String> mPlayerHand=new ArrayList<>();
    private static int[] mPlayerPoints;
    private static int[] mPlayerID;
    protected int playerID;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //create point and card storage
        resetGame();
        
        int port=0;
       // if (args[0]!=null) port = Integer.parseInt(args[0]);
//        try {
//            Thread t = new BlackJackMulti(port);
//            t.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        startMainServer();
        startNewGame();
    }

    public static void requestACard(int playerID) {
        //take the card
        //add it to the players 'card' list.
        //calculate the players points
        //show cards and win/loss status
    }

    public static void resetGame(){
        mPlayerHand.clear();
        for (int i = 0; i < numberOfPlayers + 1; i++) {
            mPlayerHand.add(new ArrayList<>());
        }
    }
    
    public static void startNewGame() {
        
        //reset all points and players
        mPlayerHand.get(0).add("♦ 8");
        mPlayerHand.get(0).add("♦ 3");
        mPlayerHand.get(1).add("♦ 10");
        mPlayerHand.get(2).add("♦ 9");

        //mPlayerHand[1][1]="HI";
        System.out.println(mPlayerHand);

        //give deck of cards to dealer
        //ask dealer for 2*players cards
        //assign cards to mPlayerHand
    }

    private static void newPlayerJoinedGame() {
        //give player ID
    }

    private static void newUserJoinedLobby() {
        //give user an ID

    }
    
    private static void checkIfUserBusted(int userID){
        
    }

    protected static int getCardValue(String card) {
        int cardValue = 0;
        String selected = card.substring(2);
        //try converting the card into a number - if cannot it is a KQJ/ACE
        try {
            cardValue += Integer.parseInt(selected);
        } catch (NumberFormatException e) {
            if (selected.equalsIgnoreCase("ACE")) {
                cardValue += ACE_VALUE;
            } else {
                cardValue += FACE_VALUE;
            }
        }

        return cardValue;
    }

    private static int getHandValue(String[] playerHand) {
        int cardValue = 0;
        for (String card : playerHand) {
            String selected = card.substring(2);

            //try converting the card into a number - if cannot it is a KQJ/ACE
            try {
                cardValue += Integer.parseInt(selected);
            } catch (NumberFormatException e) {
                if (selected.equalsIgnoreCase("ACE")) {
                    cardValue += ACE_VALUE;
                } else {
                    cardValue += FACE_VALUE;
                }
            }

        }
        return cardValue;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port "
                    + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "
                    + server.getRemoteSocketAddress());
                DataInputStream in
                    = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out
                    = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to "
                    + server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    
    public static void startMainServer() {
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Server Connection Socket Created");
			
			try {
				while (true) {
					System.out.println("Waiting for new Connection");
					
					// Wait for a new connection, send it through to our
					// game server to process and communicate.
					//gServer.createCommThread(serverSocket.accept());
                    //Thread.sleep(350);
                    serverSocket.accept();
				}
			} catch (Exception e) {
				System.out.println("Sockets accept failed");
				System.exit(1);
			}
		} catch (IOException e) {
			System.out.println("Could not listen on port: "+serverPort+".");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Could not close port: "+serverPort+".");
				System.exit(1);
			}
		}
	}

}
