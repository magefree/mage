package org.mage.test.load;

import mage.interfaces.callback.CallbackClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Session;
import mage.utils.CompressUtil;
import mage.view.GameClientMessage;
import mage.view.GameView;
import mage.view.SimpleCardView;
import mage.view.TableClientMessage;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author noxx
 */
public class LoadCallbackClient implements CallbackClient {

    private static final transient Logger log = Logger.getLogger(LoadCallbackClient.class);

    private Session session;
    private UUID gameId;
    private UUID playerId;
    private boolean gameOver;

    private volatile int controlCount;

    private GameView gameView;

    @Override
    public void processCallback(ClientCallback callback) {
        //TODO
        controlCount = 0;
        log.info(callback.getMethod());
        callback.setData(CompressUtil.decompress(callback.getData()));
        if (callback.getMethod().equals("startGame")) {
            TableClientMessage message = (TableClientMessage) callback.getData();
            gameId = message.getGameId();
            playerId = message.getPlayerId();
            session.joinGame(message.getGameId());
            startControlThread();
        } else if (callback.getMethod().equals("gameInform")) {
            GameClientMessage message = (GameClientMessage) callback.getData();
            log.info("Inform: " + message.getMessage());
            gameView = message.getGameView();
        } else if (callback.getMethod().equals("gameInit")) {

        } else if (callback.getMethod().equals("gameTarget")) {
            GameClientMessage message = (GameClientMessage) callback.getData();
            log.info("Target: " + message.getMessage());
            if (message.getMessage().equals("Select a starting player")) {
                session.sendPlayerUUID(gameId, playerId);
            } else if (message.getMessage().equals("Select a card to discard")) {
                log.info("hand size: " + gameView.getHand().size());
                SimpleCardView card = gameView.getHand().values().iterator().next();
                session.sendPlayerUUID(gameId, card.getId());
            }
        } else if (callback.getMethod().equals("gameAsk")) {
            GameClientMessage message = (GameClientMessage) callback.getData();
            log.info("Ask: " + message.getMessage());
            if (message.getMessage().equals("Do you want to take a mulligan?")) {
                session.sendPlayerBoolean(gameId, false);
            }
        } else if (callback.getMethod().equals("gameSelect")) {
            GameClientMessage message = (GameClientMessage) callback.getData();
            log.info("Select: " + message.getMessage());
            if (LoadPhaseManager.getInstance().isSkip(message.getGameView(), message.getMessage(), playerId)) {
                log.info("Skipped: " + message.getMessage());
                session.sendPlayerBoolean(gameId, false);
            }
        } else if (callback.getMethod().equals("gameOver")) {
            log.info("Game over");
            gameOver = true;
        }

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void startControlThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    controlCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (controlCount > 5) {
                        log.warn("Game seems freezed. Sending boolean message to server.");
                        session.sendPlayerBoolean(gameId, false);
                        controlCount = 0;
                    }
                }

            }
        }).start();
    }
}
