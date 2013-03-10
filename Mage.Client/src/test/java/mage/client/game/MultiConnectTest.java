package mage.client.game;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import mage.client.components.MageUI;
import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;
import org.junit.Ignore;

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

    private static final MageVersion version = new MageVersion(1, 0, 1, "");

    private static volatile int connected;

    private Object sync = new Object();
    private MageUI ui;

    private class ClientMock implements MageClient {

        private Session session;
        private String username;

        public ClientMock(String username) {
            this.username = username;
        }

        public void connect() {
            session = new SessionImpl(this);
            Connection connection = new Connection();
            connection.setUsername(username);
            connection.setHost("localhost");
            connection.setPort(17171);
            connection.setProxyType(Connection.ProxyType.NONE);

            session.connect(connection);
        }

        @Override
        public UUID getId() {
            logger.info("getId");
            return null;
        }

        @Override
        public MageVersion getVersion() {
            logger.info("getVersion");
            return version;
        }

        @Override
        public void connected(String message) {
            logger.info("connected: " + message);
            connected++;
        }

        @Override
        public void disconnected() {
            logger.info("disconnected");
        }

        @Override
        public void showMessage(String message) {
            logger.info("showMessage: " + message);
        }

        @Override
        public void showError(String message) {
            logger.info("showError: " + message);
        }

        @Override
        public void processCallback(ClientCallback callback) {
            logger.info("processCallback");
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
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                logger.fatal(null, e);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String username = "player" + index;
                ClientMock client = new ClientMock(username);
                client.connect();
                latch.countDown();
            }
        });
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
