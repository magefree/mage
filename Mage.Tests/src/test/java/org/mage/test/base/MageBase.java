package org.mage.test.base;

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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.game.match.MatchOptions;

/**
 * Base for starting Mage server.
 * Controls interactions between MageAPI and Mage Server.
 *
 * @author nantuko
 */
public class MageBase {
    /**
     * MageBase single instance
     */
    private static MageBase fInstance = new MageBase();

    /**
     * Default logger
     */
    private static Logger logger = Logging.getLogger(MageBase.class.getName());

    public static MageBase getInstance() {
        return fInstance;
    }

    private static UUID sessionId;
    public static Server server;
    private static String userName;
    private static ServerState serverState;
    private static CallbackClientDaemon callbackDaemon;
    private static UUID gameId;
    private static UUID playerId;
    private static CardView cardPlayed;

    private static GameView gameView;
    private static String phaseToWait;
    private static Object sync = new Object();
    private static Object syncStart = new Object();

    public void start() throws Exception {
        if (server == null) {
            String[] args = new String[]{"-testMode=true"};
            Main.main(args);
            connect("player", "localhost", 17171);
            UUID roomId = server.getMainRoomId();

			MatchOptions options = new MatchOptions("1", "Two Player Duel");
			options.getPlayerTypes().add("Human");
			options.getPlayerTypes().add("Computer - default");
			options.setDeckType("Limited");
			options.setAttackOption(MultiplayerAttackOption.LEFT);
			options.setRange(RangeOfInfluence.ALL);
			options.setWinsNeeded(1);
            TableView table = server.createTable(sessionId, roomId, options);
            System.out.println("Cards in the deck: " + Sets.loadDeck("UW Control.dck").getCards().size());
            server.joinTable(sessionId, roomId, table.getTableId(), "Human", Sets.loadDeck("UW Control.dck"));
            server.joinTable(sessionId, roomId, table.getTableId(), "Computer", Sets.loadDeck("UW Control.dck"));
            server.startMatch(sessionId, roomId, table.getTableId());

            synchronized (syncStart) {
                int waitTime = 7000;
                Date prev = new Date();
                syncStart.wait(waitTime);
                Date intermediate = new Date();
                if (intermediate.getTime() - prev.getTime() > waitTime - 500) {
                    throw new IllegalStateException("Couldn't start server");
                }
            }
        }
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
                            synchronized (syncStart) {
                                syncStart.notify();
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
                            if (phaseToWait == null) {
                                synchronized (sync) {
                                    sync.wait();
                                }
                            }
                            if (!message.getMessage().startsWith(phaseToWait)) {
                                server.sendPlayerBoolean(gameId, sessionId, false);
                            } else {
                                phaseToWait = null;
                            }

                            /*if (!message.getMessage().startsWith("Precombat Main - play spells and sorceries.")) {
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

                            }  */
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


    public boolean giveme(String cardName) throws Exception {
        return server.cheat(gameId, sessionId, playerId, cardName);
    }

    public boolean checkIhave(String cardName) throws Exception {
        if (cardName == null) {
            return false;
        }
        gameView = server.getGameView(gameId, sessionId, playerId);
        for (CardView card : gameView.getHand().values()) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void goToPhase(String phase) {
        phaseToWait = phase;
        synchronized (sync) {
            sync.notify();
        }
    }

    public void playCard(String cardName) throws Exception {
        gameView = server.getGameView(gameId, sessionId, playerId);
        CardsView cards = gameView.getHand();
        CardView cardToPlay = null;
        for (CardView card : cards.values()) {
            if (card.getName().equals(cardName)) {
                cardToPlay = card;
            }
        }
        if (cardToPlay == null) {
            throw new IllegalArgumentException("Couldn't find " + cardName + " in the hand.");
        }
        if (cardToPlay != null) {
            logger.info("Playing " + cardToPlay);
            server.sendPlayerUUID(gameId, sessionId, cardToPlay.getId());
            cardPlayed = cardToPlay;
        }
    }

     public boolean checkBattlefield(String cardName) throws Exception {
         gameView = server.getGameView(gameId, sessionId, playerId);
         for (PlayerView player: gameView.getPlayers()) {
            if (player.getPlayerId().equals(playerId)) {
                for (PermanentView permanent : player.getBattlefield().values()) {
                    if (permanent.getName().equals(cardName)) {
                        return true;
                    }
                }
            }
         }
         return false;
     }

     public boolean checkGraveyardsEmpty() throws Exception {
         gameView = server.getGameView(gameId, sessionId, playerId);
         for (PlayerView player: gameView.getPlayers()) {
            if (player.getGraveyard().size() > 0) {
                return false;
            }
         }
         return true;
     }
}
