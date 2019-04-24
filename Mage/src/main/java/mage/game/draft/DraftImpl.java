
package mage.game.draft;

import java.util.*;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.game.draft.DraftOptions.TimingOption;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DraftImpl implements Draft {

    protected final UUID id;
    protected final Map<UUID, DraftPlayer> players = new LinkedHashMap<>();
    protected final PlayerList table = new PlayerList();
    protected int numberBoosters;
    protected DraftCube draftCube;
    protected List<ExpansionSet> sets;
    protected List<String> setCodes;
    protected int boosterNum = 0;
    protected int cardNum = 0;
    protected TimingOption timing;
    protected int[] times = {75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20, 15, 10, 5};

    protected boolean abort = false;
    protected boolean started = false;

    protected transient TableEventSource tableEventSource = new TableEventSource();
    protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

    public DraftImpl(DraftOptions options, List<ExpansionSet> sets) {
        id = UUID.randomUUID();
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
    public void addPlayer(Player player) {
        DraftPlayer draftPlayer = new DraftPlayer(player);
        players.put(player.getId(), draftPlayer);
        table.add(player.getId());
    }

    @Override
    public boolean replacePlayer(Player oldPlayer, Player newPlayer) {
        if (newPlayer != null) {
            DraftPlayer newDraftPlayer = new DraftPlayer(newPlayer);
            DraftPlayer oldDraftPlayer = players.get(oldPlayer.getId());
            newDraftPlayer.setBooster(oldDraftPlayer.getBooster());
            Map<UUID, DraftPlayer> newPlayers = new LinkedHashMap<>();
            synchronized (players) {
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
            }
            synchronized (table) {
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
            if (oldDraftPlayer.isPicking()) {
                newDraftPlayer.setPicking();
                if (!newDraftPlayer.getBooster().isEmpty()) {
                    newDraftPlayer.getPlayer().pickCard(newDraftPlayer.getBooster(), newDraftPlayer.getDeck(), this);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Collection<DraftPlayer> getPlayers() {
        synchronized (players) {
            return players.values();
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
        this.addPick(playerId, players.get(playerId).getBooster().get(0).getId(), null);
    }

    protected void passLeft() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID nextId = table.getNext();
            DraftPlayer current = players.get(currentId);
            DraftPlayer next = players.get(nextId);
            List<Card> currentBooster = current.booster;
            while (true) {
                List<Card> nextBooster = next.booster;
                next.setBooster(currentBooster);
                if (Objects.equals(nextId, startId)) {
                    break;
                }
                currentBooster = nextBooster;
                nextId = table.getNext();
                next = players.get(nextId);
            }
        }
    }

    protected void passRight() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID prevId = table.getPrevious();
            DraftPlayer current = players.get(currentId);
            DraftPlayer prev = players.get(prevId);
            List<Card> currentBooster = current.booster;
            while (true) {
                List<Card> prevBooster = prev.booster;
                prev.setBooster(currentBooster);
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
        if (boosterNum < numberBoosters) {
            for (DraftPlayer player : players.values()) {
                if (draftCube != null) {
                    player.setBooster(draftCube.createBooster());
                } else {
                    player.setBooster(sets.get(boosterNum).createBooster());
                }
            }
        }
        boosterNum++;
        cardNum = 1;
        fireUpdatePlayersEvent();
    }

    protected boolean pickCards() {
        cardNum++;
        for (DraftPlayer player : players.values()) {
            if (player.getBooster().isEmpty()) {
                return false;
            }
            player.setPicking();
            player.getPlayer().pickCard(player.getBooster(), player.getDeck(), this);
        }
        synchronized (this) {
            while (!donePicking()) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
        return true;
    }

    protected boolean donePicking() {
        if (isAbort()) {
            return true;
        }
        return players.values()
                .stream()
                .noneMatch(DraftPlayer::isPicking);

    }

    @Override
    public boolean allJoined() {
        return players.values().stream()
                .allMatch(DraftPlayer::isJoined);
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
        DraftPlayer player = players.get(playerId);
        if (cardNum > 15) {
            cardNum = 15;
        }
        int time = times[cardNum - 1] * timing.getFactor();
        playerQueryEventSource.pickCard(playerId, "Pick card", player.getBooster(), time);
    }

    @Override
    public boolean addPick(UUID playerId, UUID cardId, Set<UUID> hiddenCards) {
        DraftPlayer player = players.get(playerId);
        if (player.isPicking()) {
            for (Card card : player.booster) {
                if (card.getId().equals(cardId)) {
                    player.addPick(card, hiddenCards);
                    player.booster.remove(card);
                    break;
                }
            }
            synchronized (this) {
                this.notifyAll();
            }
        }
        return !player.isPicking();
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

    @Override
    public void resetBufferedCards() {
        Set<ExpansionSet> setsDone = new HashSet<>();
        for (ExpansionSet set : sets) {
            if (!setsDone.contains(set)) {
                set.removeSavedCards();
                setsDone.add(set);
            }
        }

    }

}
