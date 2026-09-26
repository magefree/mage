package mage.game.draft;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.game.draft.DraftOptions.TimingOption;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.players.Player;
import mage.players.PlayerList;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public abstract class DraftImpl implements Draft {

    protected static final Logger logger = Logger.getLogger(DraftImpl.class);

    protected final UUID id;
    protected UUID tableId = null;
    protected final Map<UUID, DraftPlayer> players = new LinkedHashMap<>(); // must use sync access for any changes like synchronized (players)
    protected final PlayerList table = new PlayerList();
    protected int numberBoosters;
    protected DraftCube draftCube;
    protected List<ExpansionSet> sets;
    protected List<String> setCodes;
    
    
    protected TimingOption timing;

    protected final int BOOSTER_LOADING_INTERVAL_SECS = 2; // re-send interval in seconds for not confirmed boosters
    protected final int AUTOPICK_BUFFER_SECS = 3; // autopick happens after the pick deadline + buffer (client's timer can lag behind the server)

    // WARNING
    // ---
    // daft works under multiple threads (booster send/resend, picks processing, timeouts), 
    // so all changes must be syncronized under same lock (players)
    // look at DraftView and DraftPickView to find used fields
    //
    // access to that fields by snapshots or synchronized (players) { xxx }
    protected int cardNum = 1; // starts with card number 1, increases by +1 after each picking
    protected int boosterNum = 1; // starts with booster 1
    // player state like picking already synced inside locked (players)
    // table change must be done under same lock too (e.g. players list)
    // ---

    protected boolean abort = false;
    protected boolean started = false;

    protected transient TableEventSource tableEventSource = new TableEventSource();
    protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

    protected ScheduledFuture<?> boosterSendingWorker;
    protected ScheduledExecutorService boosterSendingExecutor = null;

    public DraftImpl(DraftOptions options, List<ExpansionSet> sets) {
        this.id = UUID.randomUUID();
        this.setCodes = options.getSetCodes();
        this.draftCube = options.getDraftCube();
        this.timing = options.getTiming();
        this.sets = sets;
        this.numberBoosters = options.getNumberBoosters();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getTableId() {
        return tableId;
    }

    @Override
    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    @Override
    public void addPlayer(Player player) {
        DraftPlayer draftPlayer = new DraftPlayer(player);
        players.put(player.getId(), draftPlayer);
        table.add(player.getId());
    }

    @Override
    public boolean replacePlayer(Player oldPlayer, Player newPlayer) {
        if (newPlayer != null) {
            DraftPlayer newDraftPlayer = new DraftPlayer(newPlayer);
            Map<UUID, DraftPlayer> newPlayers = new LinkedHashMap<>();
            synchronized (players) {
                // boosters send to all players by timeout, so don't need to send it manually here
                DraftPlayer oldDraftPlayer = players.get(oldPlayer.getId());
                if (oldDraftPlayer == null) {
                    logger.error("Draft " + this.id + ": can't replace unknown player " + oldPlayer.getName()
                            + ", pack " + boosterNum + " pick " + cardNum);
                    return false;
                }
                newDraftPlayer.setBoosterAndLoad(oldDraftPlayer.getBooster());
                if (oldDraftPlayer.isPicking()) {
                    newDraftPlayer.setPickingAndSending();
                }

                for (Map.Entry<UUID, DraftPlayer> entry : players.entrySet()) {
                    if (entry.getKey().equals(oldPlayer.getId())) {
                        newPlayers.put(newPlayer.getId(), newDraftPlayer);
                    } else {
                        newPlayers.put(entry.getKey(), entry.getValue());
                    }
                }
                players.clear();
                for (Map.Entry<UUID, DraftPlayer> entry : newPlayers.entrySet()) {
                    players.put(entry.getKey(), entry.getValue());
                }

                // move table change inside lock (players), 
                // so it will contain only actual ids and no NPE on boosters open
                UUID currentId = table.get();
                if (currentId.equals(oldPlayer.getId())) {
                    currentId = newPlayer.getId();
                }
                table.clear();
                for (UUID playerId : players.keySet()) {
                    table.add(playerId);
                }
                table.setCurrent(currentId);
            }

            boosterSendingStart(); // if it's AI then make pick from it

            return true;
        }
        return false;
    }

    @Override
    public Collection<DraftPlayer> getPlayers() {
        synchronized (players) {
            return new ArrayList<>(players.values());
        }
    }

    @Override
    public DraftPlayer getPlayer(UUID playerId) {
        return players.get(playerId);
    }

    @Override
    public DraftCube getDraftCube() {
        return draftCube;
    }

    /**
     * Number of boosters that each player gets in this draft
     *
     * @return
     */
    @Override
    public int getNumberBoosters() {
        return numberBoosters;
    }

    @Override
    public List<ExpansionSet> getSets() {
        return sets;
    }

    @Override
    public int getBoosterNum() {
        return boosterNum;
    }

    @Override
    public int getCardNum() {
        return cardNum;
    }

    @Override
    public void leave(UUID playerId) {
        //TODO: implement this
    }

    @Override
    public void autoPick(UUID playerId) {
        // WARNING, can be called from any thread like CALL
        // make sure current booster is open
        // (a pick can come at the same time, so check picking state again)
        synchronized (players) {
            DraftPlayer player = players.get(playerId);
            if (player == null || !player.isPicking()) {
                return;
            }
            List<Card> booster = player.getBooster();
            if (booster != null && !booster.isEmpty()) {
                // user's marked card has priority (it's always from the current booster)
                // TODO: replace last card choice by smark choice, so offline player/ai will get good deck?
                UUID cardId = player.getMarkedCard() != null
                        ? player.getMarkedCard()
                        : booster.get(booster.size() - 1).getId();
                this.addPick(playerId, cardId, null);
            }
        }
    }

    @Override
    public void setMarkedCard(UUID playerId, UUID cardId) {
        // WARNING, can be called from any thread like CALL
        // mark request can come at any order (actual or outdated), so accept it for the current booster only
        synchronized (players) {
            DraftPlayer player = players.get(playerId);
            if (player == null) {
                logger.warn("Draft " + this.id + ": ignored outdated mark from unknown player " + playerId
                        + ", pack " + boosterNum + " pick " + cardNum + ", card " + cardId);
                return;
            }

            String outdatedReason = null;
            if (!player.isPicking()) {
                outdatedReason = "player already picked in this round";
            } else if (player.booster.stream().noneMatch(card -> card.getId().equals(cardId))) {
                outdatedReason = "card is not in the current booster";
            }

            if (outdatedReason != null) {
                logger.warn("Draft " + this.id + ": ignored outdated mark from " + player.getPlayer().getName()
                        + ", pack " + boosterNum + " pick " + cardNum + ", card " + cardId
                        + " - " + outdatedReason);
                return;
            }

            player.setMarkedCard(cardId);
        }
    }

    protected void passBoosterToLeft() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID nextId = table.getNext(); // getNext return left player by default
            DraftPlayer current = players.get(currentId);
            DraftPlayer next = players.get(nextId);
            List<Card> currentBooster = current.booster;
            while (true) {
                List<Card> nextBooster = next.booster;
                next.setBoosterAndLoad(currentBooster);
                if (Objects.equals(nextId, startId)) {
                    break;
                }
                currentBooster = nextBooster;
                nextId = table.getNext();
                next = players.get(nextId);
            }
        }
    }

    protected void passBoosterToRight() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID prevId = table.getPrevious(); // getPrevious return right player by default
            DraftPlayer current = players.get(currentId);
            DraftPlayer prev = players.get(prevId);
            List<Card> currentBooster = current.booster;
            while (true) {
                List<Card> prevBooster = prev.booster;
                prev.setBoosterAndLoad(currentBooster);
                if (Objects.equals(prevId, startId)) {
                    break;
                }
                currentBooster = prevBooster;
                prevId = table.getPrevious();
                prev = players.get(prevId);
            }
        }
    }

    protected void openBooster() {
        synchronized (players) {
            if (boosterNum <= numberBoosters) {
                for (DraftPlayer player : players.values()) {
                    if (draftCube != null) {
                        player.setBoosterAndLoad(draftCube.createBooster());
                    } else {
                        player.setBoosterAndLoad(sets.get(boosterNum - 1).createBooster());
                    }
                }
            }
        }
    }

    protected boolean pickCards() {
        synchronized (players) {
            for (DraftPlayer player : players.values()) {
                if (player.getBooster().isEmpty()) {
                    return false;
                }
                player.setPickingAndSending();
            }
        }

        while (!donePicking()) {
            boosterSendingStart();
            picksWait();
        }

        synchronized (players) {
            cardNum++;
        }
        return true;
    }

    protected void startPickDeadline(DraftPlayer player) {
        // must be called under players lock
        // pick time runs from the first booster send to that player, whatever happens with a player's connection,
        // so re-sends and reconnects get a remaining time only;
        // a send order can be slow (sync sending one by one), so a next player's time starts on its own send
        if (!player.isPicking() || player.getPickDeadline() > 0) {
            return;
        }
        int time = getRoundPickTimeout();
        if (time > 0) {
            player.setPickDeadline(System.currentTimeMillis() + time * 1000L);
        }
    }

    public void boosterSendingStart() {
        if (this.boosterSendingExecutor == null) {
            this.boosterSendingExecutor = Executors.newSingleThreadScheduledExecutor(
                    new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_TOURNEY_BOOSTERS_SEND + " " + this.getId())
            );
        }

        if (boosterSendingWorker == null) {
            try {
                boosterSendingWorker = boosterSendingExecutor.scheduleAtFixedRate(() -> {
                    try {
                        if (isAbort() || sendBoostersToPlayers()) {
                            boosterSendingEndRound();
                        }
                    } catch (Throwable ex) {
                        // any error must be logged and must not kill the periodic task (a dead task stops all booster sends)
                        logger.fatal("Fatal boosterLoadingHandle error in draft " + id + " pack " + boosterNum + " pick " + cardNum, ex);
                    }
                }, 0, BOOSTER_LOADING_INTERVAL_SECS, TimeUnit.SECONDS);
            } catch (RejectedExecutionException e) {
                // draft already ended and the executor is shut down (e.g. a late reconnect), nothing to send
                logger.warn("Booster sending start after the draft end, ignored: draft " + id
                        + " pack " + boosterNum + " pick " + cardNum);
            }
        }
    }

    private void boosterSendingEndRound() {
        // round end: stop re-sends of current pick
        if (boosterSendingWorker != null) {
            boosterSendingWorker.cancel(false); // false, e.g. must wait jboss send end (if interrupted then client catch ping fail and disconnect)
            boosterSendingWorker = null;
        }
    }

    protected void boosterSendingEndDraft() {
        // draft end: stop re-sends and free the thread (each draft has its own executor)
        boosterSendingEndRound();
        if (boosterSendingExecutor != null) {
            boosterSendingExecutor.shutdown();
        }
    }

    protected boolean sendBoostersToPlayers() {
        // WARNING, running from task thread
        // re-send boosters logic:
        // - first attempt goes to all clients
        // - second+ attempts goes to not-confirmed clients only

        // find not-confirmed clients
        List<DraftPlayer> needSend = new ArrayList<>();
        synchronized (players) {
            for (DraftPlayer player : players.values()) {
                if (!player.isPicking()) {
                    continue;
                }
                if (!player.isBoosterSent()) {
                    player.setBoosterSent();
                    needSend.add(player);
                } else if (!player.isBoosterLoaded()) {
                    needSend.add(player);
                }
            }
        }

        // send boosters to all one by one
        // TODO: send boosters in async style in new thread here like game init does
        //   it's require code and logic rework:
        //   - split humans and bots between threads;
        //   - sending flag;
        //   - synchronized remove from DraftController;
        //   - pick timeout calc before sent
        for (DraftPlayer player : needSend) {
            synchronized (players) {
                // pick time starts right before the first send to that player
                startPickDeadline(player);
            }
            try {
                player.getPlayer().pickCard(player.getBooster(), player.getDeck(), this);
            } catch (Throwable e) {
                // warning
                // one broken player (a bot's pick, a view build or a send) must not stop sends to other players,
                // its pick deadline is already started, so an autopick closes its pick
                logger.error("Draft " + this.id + ": can't send booster to " + player.getPlayer().getName()
                        + ", pack " + boosterNum + " pick " + cardNum, e);
            }
        }

        return needSend.isEmpty();
    }

    protected boolean donePicking() {
        if (isAbort()) {
            return true;
        }

        synchronized (players) {
            return players.values()
                    .stream()
                    .noneMatch(DraftPlayer::isPicking);
        }
    }

    @Override
    public boolean allJoined() {
        synchronized (players) {
            return players.values().stream()
                    .allMatch(DraftPlayer::isJoined);
        }
    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    @Override
    public void fireUpdatePlayersEvent() {
        tableEventSource.fireTableEvent(EventType.UPDATE, null, this);
    }

    @Override
    public void fireEndDraftEvent() {
        tableEventSource.fireTableEvent(EventType.END, null, this);
    }

    @Override
    public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
        playerQueryEventSource.addListener(listener);
    }

    @Override
    public void firePickCardEvent(UUID playerId) {
        List<Card> booster;
        synchronized (players) {
            DraftPlayer player = players.get(playerId);
            if (player == null) {
                // a send task took the player before a replacement (quit -> draftbot)
                logger.warn("Draft " + this.id + ": ignored booster send to unknown player " + playerId
                        + ", pack " + boosterNum + " pick " + cardNum);
                return;
            }
            booster = player.getBooster();
        }
        playerQueryEventSource.pickCard(playerId, "Pick card", booster, getPickTimeout(playerId));
    }

    /**
     * Full pick time of the current pick in seconds, 0 - unlimited
     */
    protected int getRoundPickTimeout() {
        return timing.getPickTimeout(cardNum);
    }

    @Override
    public int getPickTimeout(UUID playerId) {
        // remaining pick time of the player's current pick, the same for any send (first send, re-send, reconnect)
        // the time is always at least 1 second unless it's set to 0, i.e. unlimited time
        // (0 - no deadline and no auto-pick, e.g. Rich Man draft with NONE timing or a future option to disable the timer)
        synchronized (players) {
            int time = getRoundPickTimeout();
            DraftPlayer player = players.get(playerId);
            if (time <= 0 || player == null || !player.isPicking() || player.getPickDeadline() == 0) {
                // unlimited, the booster is not sent yet (e.g. draft init) or no pick now (e.g. between rounds)
                return time;
            }
            long leftMs = player.getPickDeadline() - System.currentTimeMillis();
            return (int) Math.max(1, (leftMs + 999) / 1000);
        }
    }

    public void picksCheckDone() {
        // notify main thread about changes, can be called from user's thread
        synchronized (this) {
            this.notifyAll();
        }
    }

    protected void picksWait() {
        // main thread waiting any picks, changes or a pick deadline
        long waitMs = 10000; // checked every 10s to make sure the draft moves on
        long autoPickTime = 0; // the nearest one
        boolean notSentYet = false;
        synchronized (players) {
            for (DraftPlayer player : players.values()) {
                if (!player.isPicking()) {
                    continue;
                }
                if (player.getPickDeadline() > 0) {
                    long playerTime = player.getPickDeadline() + AUTOPICK_BUFFER_SECS * 1000L;
                    autoPickTime = autoPickTime == 0 ? playerTime : Math.min(autoPickTime, playerTime);
                } else {
                    notSentYet = true;
                }
            }
        }
        if (autoPickTime > 0) {
            waitMs = Math.max(1, Math.min(waitMs, autoPickTime - System.currentTimeMillis()));
        }
        if (notSentYet) {
            // deadlines start on sends by the booster sending task, so check it again soon
            waitMs = Math.min(waitMs, 500);
        }

        synchronized (this) {
            // require additional donePicking() cause it can be changed by income answer after parent's donePicking()
            // so make sure there aren't extra waits
            // TODO: can be deleted after draft migrage to single thread logic like game thread
            if (!donePicking()) {
                try {
                    this.wait(waitMs);
                } catch (InterruptedException ignore) {
                }
            }
        }

        autoPickByDeadline();

        if (donePicking()) {
            boosterSendingEndRound();
        }
    }

    protected void autoPickByDeadline() {
        // pick timeout: auto-pick for all players who didn't pick in time (online or offline)
        List<UUID> latePlayers = new ArrayList<>();
        synchronized (players) {
            if (isAbort()) {
                return;
            }
            long now = System.currentTimeMillis();
            for (Map.Entry<UUID, DraftPlayer> entry : players.entrySet()) {
                DraftPlayer player = entry.getValue();
                if (player.isPicking() && player.getPickDeadline() > 0
                        && now >= player.getPickDeadline() + AUTOPICK_BUFFER_SECS * 1000L) {
                    latePlayers.add(entry.getKey());
                }
            }
        }

        for (UUID playerId : latePlayers) {
            // uses user's marked card or a default card
            autoPick(playerId);
        }
    }

    @Override
    public DraftPlayerSnapshot addPick(UUID playerId, UUID cardId, Set<UUID> hiddenCards) {
        // WARNING, can be called from any thread like CALL
        // pick request can come from any thread at any order (user's call, pick timeout, AI - actual or outdated)
        // make sure it's an actual pick by card id
        DraftPlayerSnapshot res;
        DraftPlayer player;
        synchronized (players) {
            player = players.get(playerId);
            if (player == null) {
                logger.warn("Draft " + this.id + ": ignored outdated pick from unknown player " + playerId
                        + ", pack " + boosterNum + " pick " + cardNum + ", card " + cardId);
                return null;
            }

            String outdatedReason = null;
            Card pickedCard = null;
            if (!player.isPicking()) {
                outdatedReason = "player already picked in this round";
            } else {
                pickedCard = player.booster.stream()
                        .filter(card -> card.getId().equals(cardId))
                        .findFirst()
                        .orElse(null);
                if (pickedCard == null) {
                    outdatedReason = "card is not in the current booster";
                }
            }

            if (outdatedReason != null) {
                boolean alreadyPicked = player.getDeck().getSideboard().stream().anyMatch(card -> card.getId().equals(cardId));
                logger.warn("Draft " + this.id + ": ignored outdated pick from " + player.getPlayer().getName()
                        + ", pack " + boosterNum + " pick " + cardNum + ", card " + cardId
                        + (alreadyPicked ? " (already picked before)" : "")
                        + " - " + outdatedReason);
                return null;
            }

            player.addPick(pickedCard, hiddenCards);
            // answer data must be from the pick moment, the draft thread can start the next round right after the lock
            res = makePlayerSnapshot(player);
        }

        picksCheckDone();
        //ThreadUtils.sleep(50); // simulate low CPU in test lab (OS's threads queue)
        return res;
    }

    @Override
    public DraftPlayerSnapshot getPlayerSnapshot(UUID playerId) {
        synchronized (players) {
            DraftPlayer player = players.get(playerId);
            if (player == null) {
                return null;
            }
            return makePlayerSnapshot(player);
        }
    }

    private DraftPlayerSnapshot makePlayerSnapshot(DraftPlayer player) {
        // must be called under players lock
        List<String> playerNames = new ArrayList<>();
        for (DraftPlayer draftPlayer : players.values()) {
            playerNames.add(draftPlayer.getPlayer().getName());
        }
        return new DraftPlayerSnapshot(boosterNum, cardNum, playerNames,
                player.getBooster(), new ArrayList<>(player.getDeck().getSideboard()), player.isPicking());
    }

    @Override
    public void setBoosterLoaded(UUID playerId) {
        // WARNING, can be called from any thread like CALL
        // confirm request can come at any order (actual or outdated), outdated one can only stop resends
        synchronized (players) {
            DraftPlayer player = players.get(playerId);
            if (player == null) {
                logger.warn("Draft " + this.id + ": ignored outdated booster confirm from unknown player " + playerId
                        + ", pack " + boosterNum + " pick " + cardNum);
                return;
            }
            player.setBoosterLoaded();
        }
    }

    @Override
    public boolean isAbort() {
        return abort;
    }

    @Override
    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void setStarted() {
        started = true;
    }

}
