package mage.server.bdd;

import mage.interfaces.MageException;
import mage.interfaces.Server;
import mage.interfaces.ServerState;
import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.CallbackClientDaemon;
import mage.interfaces.callback.ClientCallback;
import mage.server.Main;
import mage.sets.Sets;
import mage.util.Logging;
import mage.view.*;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Proof of concept of running game from tests.\
 * Will be removed later when BDD is finished.
 *
 * @author nantuko
 */
public class StoryRunPOC {

    private static Logger logger = Logging.getLogger(StoryRunPOC.class.getName());

    private static UUID sessionId;
    private static Server server;
    private static String userName;
    private static ServerState serverState;
    private static CallbackClientDaemon callbackDaemon;
    private static UUID gameId;
    private static UUID playerId;
    private static CardView cardPlayed;

    @Test
    public void testEmpty() {

    }

    public static void main(String[] argv) throws Exception {
        String[] args = new String[] {"-testMode=true"};
        Main.main(args);
        connect("player", "localhost", 17171);
        UUID roomId = server.getMainRoomId();

        List<String> playerTypes = new ArrayList<String>();
        playerTypes.add("Human");
        playerTypes.add("Computer - default");
        TableView table = server.createTable(sessionId, roomId, "Two Player Duel", "Limited", playerTypes, null, null);
        System.out.println("Cards in the deck: " + Sets.loadDeck("UW Control.dck").getCards().size());
        server.joinTable(sessionId, roomId, table.getTableId(), "Human", Sets.loadDeck("UW Control.dck"));
        server.joinTable(sessionId, roomId, table.getTableId(), "Computer", Sets.loadDeck("UW Control.dck"));
        server.startGame(sessionId, roomId, table.getTableId());
    }

    public static void connect(String userName, String serverName, int port) {
        try {
            System.setSecurityManager(null);
            Registry reg = LocateRegistry.getRegistry(serverName, port);
            server = (Server) reg.lookup("mage-server");
            sessionId = server.registerClient(userName, UUID.randomUUID());
            CallbackClient client = new CallbackClient(){
                @Override
                public void processCallback(ClientCallback callback) {
                    logger.info("IN >> " + callback.getMessageId() + " - " + callback.getMethod());
                    try {
                        if (callback.getMethod().equals("startGame")) {
                            UUID[] data = (UUID[]) callback.getData();
                            gameId = data[0];
                            playerId = data[1];
                            server.joinGame(gameId, sessionId);
                        } else if (callback.getMethod().equals("gameInit")) {
                            server.ack("gameInit", sessionId);
            			} else if (callback.getMethod().equals("gameAsk")) {
				            GameClientMessage message = (GameClientMessage) callback.getData();
                            logger.info("ASK >> " + message.getMessage());
                            if (message.getMessage().equals("Do you want to take a mulligan?")) {
                                server.sendPlayerBoolean(gameId, sessionId, false);
                            }
			            } else if (callback.getMethod().equals("gameTarget")) {
                            GameClientMessage message = (GameClientMessage) callback.getData();
                            logger.info("TARGET >> " + message.getMessage() + " >> " + message.getTargets());
                            if (message.getMessage().equals("Select a starting player")) {
                                logger.info("  Sending >> " + playerId);
                                server.sendPlayerUUID(gameId, sessionId, playerId);
                            }
                        } else if (callback.getMethod().equals("gameSelect")) {
				            GameClientMessage message = (GameClientMessage) callback.getData();
				            logger.info("SELECT >> " + message.getMessage());
                            if (!message.getMessage().startsWith("Precombat Main - play spells and sorceries.")) {
                                server.sendPlayerBoolean(gameId, sessionId, false);
                            } else {
                                if (cardPlayed == null) {
                                    CardsView cards = message.getGameView().getHand();
                                    CardView landToPlay = null;
                                    for (CardView card : cards.values()) {
                                        //System.out.println(card.getName());
                                        if (card.getName().equals("Plains") || card.getName().equals("Island")) {
                                            landToPlay = card;
                                        }
                                    }
                                    if (landToPlay != null) {
                                        logger.info("Playing " + landToPlay);
                                        server.sendPlayerUUID(gameId, sessionId, landToPlay.getId());
                                        cardPlayed = landToPlay;
                                    } else {
                                        logger.warning("Couldn't find land to play");
                                    }
                                } else {
                                    logger.info("Checking battlefield...");
                                    boolean foundPlayer = false;
                                    boolean foundLand = false;
                                    for (PlayerView player: message.getGameView().getPlayers()) {
                                        if (player.getPlayerId().equals(playerId)) {
                                            foundPlayer = true;
                                            for (PermanentView permanent : player.getBattlefield().values()) {
                                                if (permanent.getId().equals(cardPlayed.getId())) {
                                                    foundLand = true;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    logger.info("  found player: " + foundPlayer);
                                    logger.info("  found land: " + foundLand);
                                    System.exit(0);
                                }
                            }
			            }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
            };
            callbackDaemon = new CallbackClientDaemon(sessionId, client, server);
            serverState = server.getServerState();
        } catch (MageException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logger.log(Level.SEVERE, "Unable to connect to server - ", ex);
        } catch (NotBoundException ex) {
            logger.log(Level.SEVERE, "Unable to connect to server - ", ex);
        }
    }
}
