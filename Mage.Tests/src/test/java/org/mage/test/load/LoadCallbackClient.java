package org.mage.test.load;

import mage.remote.Session;
import mage.view.*;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class LoadCallbackClient  {

    //private static final Logger log = Logger.getLogger(LoadCallbackClient.class);
    private static final Logger log = Logger.getLogger("Load call");

    private Session session;
    private UUID gameId;
    private UUID playerId;
    private boolean gameOver;
    private String gameResult = "unknown";
    private boolean needToConcede = false; // will concede on first priority
    private boolean joinGameChat = false; // process CHATMESSAGE

    private volatile int controlCount;
    private UUID chatId;

    private GameView gameView;

    public LoadCallbackClient(boolean joinGameChat) {
        this.joinGameChat = joinGameChat;
    }

    private PlayerView getPlayer() {
        if ((this.gameView != null) && (this.playerId != null)) {
            for (PlayerView p : this.gameView.getPlayers()) {
                if (p.getPlayerId().equals(this.playerId)) {
                    return p;
                }
            }
        }
        return null;
    }

    private String getLogStartInfo() {
        String mes = "";

        //throw new IllegalArgumentException("test exception");
        if (this.session != null) {
            mes += session.getUserName() + ": ";
        }

        PlayerView p = getPlayer();
        if (this.gameView != null && p != null && this.gameView.getStep() != null) {
            mes += "T" + this.gameView.getTurn() + "-" + this.gameView.getStep().getIndex() + ", L:" + p.getLibraryCount() + ", H:" + getPlayer().getHandCount() + ": ";
        }

        return mes;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void startControlThread() {
        new Thread(() -> {
            while (true) {
                controlCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isGameOver()) {
                    return;
                }

                if (controlCount > 5) {
                    log.warn(getLogStartInfo() + "Game seems frozen. Sending boolean message to server.");
                    session.sendPlayerBoolean(gameId, false);
                    controlCount = 0;
                }
            }

        }).start();
    }

    public void setConcede(boolean needToConcede) {
        this.needToConcede = needToConcede;
    }

    public String getLastGameResult() {
        return this.gameResult;
    }

    public GameView getLastGameView() {
        return this.gameView;
    }
}
