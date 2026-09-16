package mage.interfaces.callback;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Network: pending callbacks of a single user session, see mage.server.Session#fireCallback
 * <p>
 * Server uses single one wayback connection to send data back to clients
 * Slow clients can take 500-1500 ms per message
 * <p>
 * There are two type of messages:
 * - important (can't be lost like ask dialogs - without client's response game can't continue)
 * - not important (client can lost it, e.g. chat messages)
 * <p>
 * Two queues allow to split and prioritize messages and send important first without lost
 * It's required in async world
 * <p>
 * Two queues instead of one two side queue: a single deque puts every new important message to the
 * head, so two important messages in a row arrive reversed (seen in a real run: END_GAME_INFO
 * before GAME_OVER). Separate queues keep the order inside each priority
 *
 * @author JayDi85
 */
public class ClientCallbacksQueue {

    private static final Logger logger = Logger.getLogger(ClientCallbacksQueue.class);

    /**
     * A stuck client must not eat all the memory, so a queue is limited
     * Choose values like x10 per parallel game-draft
     * <p>
     * Increase it if you see too many SESSION LOCK, possible connection problem
     */
    private static final int MAX_SIZE = 20;

    private final LinkedBlockingDeque<ClientCallback> importantQueue = new LinkedBlockingDeque<>(MAX_SIZE);
    private final LinkedBlockingDeque<ClientCallback> normalQueue = new LinkedBlockingDeque<>(MAX_SIZE);

    /**
     * @param call              a callback that could not be sent right now
     * @param isImportant       important ones are taken first, see ClientCallbackType#canComeInAnyOrder
     * @param droppedAsOverflow dropped messages due queue max limit reach
     * @param droppedAsOutdated dropped messages due outdated data like UPDATE
     */
    public void add(ClientCallback call, boolean isImportant,
                    List<ClientCallback> droppedAsOverflow, List<ClientCallback> droppedAsOutdated) {
        LinkedBlockingDeque<ClientCallback> queue = isImportant ? this.importantQueue : this.normalQueue;
        if (queue.offerLast(call)) {
            return;
        }

        // no room left, drop the oldest message of the same priority and try once more
        ClientCallback dropped = queue.pollFirst();
        if (dropped != null) {
            logDropped(dropped, isImportant, "add");
            droppedAsOverflow.add(dropped);
        }
        if (!queue.offerLast(call)) {
            logDropped(call, isImportant, "add, new one");
            droppedAsOverflow.add(call);
        }
    }

    /**
     * @param call              an update callback that can replace all older ones (a user needs the latest only)
     * @param droppedAsOverflow dropped messages due queue max limit reach
     * @param droppedAsOutdated dropped messages due outdated data like UPDATE
     */
    public void addUpdate(ClientCallback call, List<ClientCallback> droppedAsOverflow, List<ClientCallback> droppedAsOutdated) {
        // only the newest update matters: every update carries a full GameView, so older ones are
        // useless the moment a newer one arrives.
        // updates are never important, so a normal queue only

        // make sure it's try to add actual message
        boolean hasNewer = false;
        for (ClientCallback queued : this.normalQueue) {
            if (isSameSource(queued, call) && queued.getMessageId() > call.getMessageId()) {
                hasNewer = true;
                break;
            }
        }
        if (hasNewer) {
            droppedAsOutdated.add(call); // a newer state is already waiting, this one is useless
            return;
        }

        // remove outdated messages
        Iterator<ClientCallback> it = this.normalQueue.iterator();
        while (it.hasNext()) {
            ClientCallback queued = it.next();
            if (isSameSource(queued, call) && queued.getMessageId() < call.getMessageId()) {
                it.remove();
                droppedAsOutdated.add(queued);
            }
        }

        // add new message
        add(call, false, droppedAsOverflow, droppedAsOutdated);
    }

    /**
     * Returns a message that could not be sent right now, keeping its place at the head
     */
    public void returnBack(ClientCallback call, boolean isImportant, List<ClientCallback> droppedAsOverflow, List<ClientCallback> droppedAsOutdated) {
        LinkedBlockingDeque<ClientCallback> queue = isImportant ? this.importantQueue : this.normalQueue;
        if (queue.offerFirst(call)) {
            return;
        }

        // no room: the queue filled up while we were trying to send, so this message is the
        // oldest one now - drop it instead of pushing out something newer
        droppedAsOverflow.add(call);
        logDropped(call, isImportant, "returnBack");
    }

    /**
     * Same message type from the same game/table, so a newer one fully replaces an older one
     */
    private static boolean isSameSource(ClientCallback left, ClientCallback right) {
        return left.getMethod() == right.getMethod()
                && Objects.equals(left.getObjectId(), right.getObjectId());
    }

    /**
     * Next message to send, null when there is nothing left. Important goes first
     */
    public ClientCallback poll() {
        ClientCallback res = this.importantQueue.pollFirst();
        return res != null ? res : this.normalQueue.pollFirst();
    }

    public boolean isEmpty() {
        return this.importantQueue.isEmpty() && this.normalQueue.isEmpty();
    }

    public int size() {
        return this.importantQueue.size() + this.normalQueue.size();
    }

    public void clear() {
        this.importantQueue.clear();
        this.normalQueue.clear();
    }

    /**
     * Dropped important messages are a real problem (dialogs, table changes), so always visible.
     * Dropped normal ones (chats, game logs, updates) are expected for slow clients and can come in
     * hundreds per burst - debug level only, enable it in a logger config if needed (test lab does it)
     */
    private static void logDropped(ClientCallback call, boolean isImportant, String reason) {
        if (isImportant) {
            // if too much spam in production logs then switch to debug
            logger.warn("CALLBACKS QUEUE is full, important message dropped (" + reason + "): " + call.getInfo());
        } else if (logger.isDebugEnabled()) {
            logger.debug("CALLBACKS QUEUE is full, message dropped (" + reason + "): " + call.getInfo());
        }
    }
}