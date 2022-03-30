package mage.client.game;

import mage.client.components.MageUI;
import mage.remote.interfaces.MageClient;
import mage.remote.Connection;
import mage.utils.MageVersion;
import mage.choices.Choice;
import mage.client.MageFrame;
import mage.interfaces.ServerState;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import mage.remote.messages.MessageType;
import org.apache.log4j.Logger;
import org.junit.Ignore;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test for emulating the connection from multi mage clients.
 *
 * @author ayratn
 */
@Ignore
public class MultiConnectTest {

    private static final Logger logger = Logger.getLogger(MultiConnectTest.class);

    /**
     * Amount of games to be started from this test.
     */
    private static final Integer USER_CONNECT_COUNT = 200;

    private static final CountDownLatch latch = new CountDownLatch(USER_CONNECT_COUNT);

    private static final MageVersion version = new MageVersion(MultiConnectTest.class);

    private static volatile int connected;

    private final Object sync = new Object();
    private MageUI ui;

    private static class ClientMock implements MageClient {

        private MageFrame mageFrame;
        private final String username;

        public ClientMock(String username) {
            this.username = username;
        }

        public void connect() {
            try{
            mageFrame = new MageFrame();
            Connection connection = new Connection();
            connection.setUsername(username);
            connection.setHost("localhost");
            connection.setPort(17171);
            connection.setProxyType(Connection.ProxyType.NONE);

            mageFrame.connect(connection);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public MageVersion getVersion() {
            logger.info("getVersion");
            return version;
        }

        @Override
        public void connected(String message) {
            logger.info("connected: " + message);
            connected++;
        }

        public void disconnected(boolean askToReconnect) {
            logger.info("disconnected");
        }

        public void showMessage(String message) {
            logger.info("showMessage: " + message);
        }

        public void showError(String message) {
            logger.info("showError: " + message);
        }

        @Override
        public void inform(String title, String message, MessageType type) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void receiveChatMessage(UUID chatId, ChatMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void clientRegistered(ServerState state) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public ServerState getServerState() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameStarted(UUID gameId, UUID playerId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void initGame(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameUpdate(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameInform(UUID gameId, GameClientMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameInformPersonal(UUID gameId, GameClientMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameOver(UUID gameId, String message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameError(UUID gameId, String message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameChooseAbility(UUID gameId, AbilityPickerView abilities) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameChooseChoice(UUID gameId, Choice choice) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gamePlayXMana(UUID gameId, GameView gameView, String message) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameSelectAmount(UUID gameId, GameView gameView, String message, int min, int max) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameMultiAmount(UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void gameEndInfo(UUID gameId, GameEndView view) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void sideboard(UUID tableId, DeckView deck, int time, boolean limited) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void viewLimitedDeck(UUID tableId, DeckView deck, int time, boolean limited) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void viewSideboard(UUID gameId, UUID targetPlayerId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void construct(UUID tableId, DeckView deck, int time) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void startDraft(UUID draftId, UUID playerId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void draftInit(UUID draftId, DraftPickView draftPickView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void draftUpdate(UUID draftId, DraftView draftView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void draftPick(UUID draftId, DraftPickView draftPickView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void draftOver(UUID draftId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void showTournament(UUID tournamentId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void tournamentStarted(UUID tournamentId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void watchGame(UUID gameId, UUID chatId, GameView game) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void replayGame(UUID gameId) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void replayInit(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void replayDone(UUID gameId, String result) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void replayUpdate(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

    public static void main(String[] argv) throws Exception {
        new MultiConnectTest().startMultiGames();
    }

    public void startMultiGames() throws Exception {
        for (int i = 0; i < USER_CONNECT_COUNT; i++) {
            logger.info("Starting game");
            connect(i);
        }
        latch.await();
        logger.info("Finished");
        logger.info("Connected: " + connected + " of " + USER_CONNECT_COUNT);
    }

    private void connect(final int index) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.fatal(null, e));
        SwingUtilities.invokeLater(() -> {
            String username = "player" + index;
            ClientMock client = new ClientMock(username);
            client.connect();
            latch.countDown();
        });
    }

    private void sleep(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
