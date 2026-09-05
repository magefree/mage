package mage.client.game;

import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.players.PlayerType;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.utils.MageVersion;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Minimal headless single-shot connect check - no Swing UI, no JavaFX, no
 * display required. Built for automated Java-version / add-opens
 * compatibility matrix testing (see MultiConnectTest for the original
 * multi-client pattern this is trimmed down from).
 * <p>
 * IMPORTANT: connectStart() returning true only means no exception was
 * thrown while reading the connectUser/getServerState/connectSetUserData
 * RPC replies. Native java.io deserialization does NOT throw on a class
 * that gained new fields since the sender's version - it silently leaves
 * unmatched fields at their default (null / empty array). So a "successful"
 * connect can still carry a corrupted, partially-empty ServerState. This
 * check additionally inspects session.getPlayerTypes() after connecting and
 * treats an empty/null result as a failure, not just the boolean return.
 * <p>
 * Usage:
 * 1. Compile (only needed after editing this file or any RPC-related class):
 * mvn compile test-compile -pl Mage.Client -am -DskipTests
 * 2. Run:
 * java -Djava.awt.headless=true [--add-opens=java.base/java.io=ALL-UNNAMED] \
 * -cp &lt;classpath incl. target/test-classes&gt; \
 * mage.client.game.HeadlessConnectCheck &lt;host&gt; &lt;port&gt; &lt;username&gt; [timeoutSeconds]
 * <p>
 * Exit codes:
 * 0 = connected, logged in, AND server sent a non-empty player type list
 * 1 = connectStart() returned false, threw, or ServerState came back empty/corrupted
 * 2 = bad arguments
 * 3 = timed out waiting for connectStart() to return at all
 *
 * @author JayDi85
 */
public class HeadlessConnectCheck {

    private static final MageVersion VERSION = new MageVersion(mage.client.MageFrame.class);
    private static final CountDownLatch CALLBACK_LATCH = new CountDownLatch(1);
    private static final AtomicReference<String> LAST_MESSAGE = new AtomicReference<>("");

    private static class ClientMock implements MageClient {

        @Override
        public MageVersion getVersion() {
            return VERSION;
        }

        @Override
        public void connected(String message) {
            System.out.println("CALLBACK connected: " + message);
            LAST_MESSAGE.set(message);
            CALLBACK_LATCH.countDown();
        }

        @Override
        public void disconnected(boolean askToReconnect, boolean keepMySessionActive) {
            System.out.println("CALLBACK disconnected (askToReconnect=" + askToReconnect + ")");
        }

        @Override
        public void showMessage(String message) {
            System.out.println("CALLBACK showMessage: " + message);
            LAST_MESSAGE.set(message);
        }

        @Override
        public void showError(String message) {
            System.out.println("CALLBACK showError: " + message);
            LAST_MESSAGE.set(message);
        }

        @Override
        public void onNewConnection() {
            System.out.println("CALLBACK onNewConnection");
        }

        @Override
        public void onCallback(ClientCallback callback) {
            // not needed for a plain connect check
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: HeadlessConnectCheck <host> <port> <username> [timeoutSeconds]");
            System.exit(2);
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];
        int timeoutSeconds = args.length >= 4 ? Integer.parseInt(args[3]) : 20;

        System.out.println("RUNNING: java=" + System.getProperty("java.version")
                + " host=" + host + " port=" + port + " username=" + username);

        ClientMock client = new ClientMock();
        Session session = new SessionImpl(client);
        Connection connection = new Connection();
        connection.setUsername(username);
        connection.setHost(host);
        connection.setPort(port);
        connection.setProxyType(Connection.ProxyType.NONE);
        connection.setUserIdStr("vscode:Linux::F2-44-AF-EB-60-0F");

        // Reconstruct the exact real UserData Олег dumped from his live GUI client
        // (avatarId/flagName/skip-priority-steps/etc), instead of a bare default one,
        // to test whether this specific accumulated user data is what triggers the
        // connectSetUserData EOFException that only the full GUI client hits.
        mage.players.net.UserData realUserData = mage.players.net.UserData.getDefaultUserDataView();
        realUserData.setGroupId(1);
        realUserData.setAvatarId(10);
        realUserData.setAllowRequestShowHandCards(true);
        realUserData.setConfirmEmptyManaPool(true);
        realUserData.setFlagName("world");
        realUserData.setAskMoveToGraveOrder(false);
        realUserData.setManaPoolAutomatic(true);
        realUserData.setManaPoolAutomaticRestricted(true);
        realUserData.setPassPriorityCast(false);
        realUserData.setPassPriorityActivation(false);
        realUserData.setAutoOrderTrigger(true);
        realUserData.setAutoTargetLevel(1);
        realUserData.setUseFirstManaAbility(false);
        realUserData.setMatchHistory("");
        realUserData.setMatchQuitRatio(0);
        realUserData.setTourneyHistory("");
        realUserData.setTourneyQuitRatio(0);
        realUserData.setGeneralRating(0);
        realUserData.setConstructedRating(0);
        realUserData.setLimitedRating(0);

        mage.players.net.UserSkipPrioritySteps skipSteps = new mage.players.net.UserSkipPrioritySteps();
        skipSteps.setStopOnAllEndPhases(false);
        skipSteps.setStopOnAllMainPhases(false);
        skipSteps.setStopOnStackNewObjects(false);
        // stopOnDeclareAttackers=true, stopOnDeclareBlockersWithAnyPermanents=true,
        // stopOnDeclareBlockersWithZeroPermanents=false are already the class defaults

        for (mage.players.net.SkipPrioritySteps turnSteps : new mage.players.net.SkipPrioritySteps[]{
                skipSteps.getYourTurn(), skipSteps.getOpponentTurn()}) {
            turnSteps.setBeforeCombat(true);
            turnSteps.setDraw(true);
            turnSteps.setEndOfCombat(true);
            turnSteps.setEndOfTurn(true);
            turnSteps.setMain1(true);
            turnSteps.setMain2(true);
            turnSteps.setUpkeep(true);
        }
        realUserData.setUserSkipPrioritySteps(skipSteps);

        connection.setUserData(realUserData);

        // connectStart() already blocks internally until it has an answer, so we
        // just run it on a plain worker thread and enforce our own outer timeout
        // in case it ever hangs indefinitely instead of returning/throwing.
        final boolean[] connectResult = {false};
        final Throwable[] connectError = {null};
        CountDownLatch finished = new CountDownLatch(1);

        Thread attempt = new Thread(() -> {
            try {
                connectResult[0] = session.connectStart(connection);
            } catch (Throwable t) {
                connectError[0] = t;
            } finally {
                finished.countDown();
            }
        }, "connect-attempt");
        attempt.setDaemon(true);
        attempt.start();

        boolean finishedInTime = finished.await(timeoutSeconds, TimeUnit.SECONDS);

        if (!finishedInTime) {
            System.out.println("RESULT: TIMEOUT after " + timeoutSeconds + "s (connectStart never returned)");
            System.exit(3);
            return;
        }

        if (connectError[0] != null) {
            System.out.println("RESULT: EXCEPTION - " + connectError[0]);
            connectError[0].printStackTrace(System.out);
            System.exit(1);
            return;
        }

        if (!connectResult[0]) {
            System.out.println("RESULT: FAIL - lastError=" + session.getLastError()
                    + " lastMessage=" + LAST_MESSAGE.get());
            System.exit(1);
            return;
        }

        // connectStart() returned true - now verify the actual payload the server
        // sent back is not silently empty/corrupted (see class-level javadoc).
        PlayerType[] playerTypes;
        try {
            playerTypes = session.getPlayerTypes();
        } catch (Throwable t) {
            System.out.println("RESULT: FAIL - connected, but getPlayerTypes() threw: " + t);
            t.printStackTrace(System.out);
            session.connectStop(false, false);
            System.exit(1);
            return;
        }

        int playerTypeCount = (playerTypes == null) ? 0 : playerTypes.length;
        System.out.println("ServerState playerTypes count: " + playerTypeCount
                + (playerTypes == null ? " (null!)" : " " + java.util.Arrays.toString(playerTypes)));

        session.connectStop(false, false);

        if (playerTypeCount == 0) {
            System.out.println("RESULT: FAIL - connectStart() returned true but ServerState.playerTypes is empty/null "
                    + "(likely a silently-truncated/corrupted deserialization, not a clean protocol rejection)");
            System.exit(1);
            return;
        }

        System.out.println("RESULT: OK");
        System.exit(0);
    }
}