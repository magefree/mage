package mage.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.designations.Designation;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameState;
import mage.game.combat.CombatGroup;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameView implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(GameView.class);
    private final int priorityTime;
    private final List<PlayerView> players = new ArrayList<>();
    private CardsView hand;
    private Set<UUID> canPlayObjects;
    private Map<String, SimpleCardsView> opponentHands;
    private Map<String, SimpleCardsView> watchedHands;
    private final CardsView stack = new CardsView();
    private final List<ExileView> exiles = new ArrayList<>();
    private final List<RevealedView> revealed = new ArrayList<>();
    private List<LookedAtView> lookedAt = new ArrayList<>();
    private final List<CombatGroupView> combat = new ArrayList<>();
    private final TurnPhase phase;
    private final PhaseStep step;
    private final UUID activePlayerId;
    private String activePlayerName = "";
    private String priorityPlayerName;
    private final int turn;
    private boolean special = false;
    private final boolean isPlayer; // false = watching user
    private final int spellsCastCurrentTurn;
    private final boolean rollbackTurnsAllowed;

    public GameView(GameState state, Game game, UUID createdForPlayerId, UUID watcherUserId) {
        Player createdForPlayer = null;
        this.isPlayer = createdForPlayerId != null;
        this.priorityTime = game.getPriorityTime();
        for (Player player : state.getPlayers().values()) {
            players.add(new PlayerView(player, state, game, createdForPlayerId, watcherUserId));
            if (player.getId().equals(createdForPlayerId)) {
                createdForPlayer = player;
            }
        }
        for (StackObject stackObject : state.getStack()) {
            if (stackObject instanceof Spell) {
                // Spell
                CardView spellView = new CardView((Spell) stackObject, game, stackObject.getControllerId().equals(createdForPlayerId));
                spellView.paid = ((Spell) stackObject).getSpellAbility().getManaCostsToPay().isPaid();
                stack.put(stackObject.getId(), spellView);
            } else if (stackObject instanceof StackAbility) {
                // Stack Ability
                MageObject object = game.getObject(stackObject.getSourceId());
                Card card = game.getCard(stackObject.getSourceId());
                if (card == null && (object instanceof PermanentCard)) {
                    card = ((PermanentCard) object).getCard();
                }
                if (card != null) {
                    if (object != null) {
                        if (object instanceof Permanent) {
                            boolean controlled = ((Permanent) object).getControllerId().equals(createdForPlayerId);
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, object.getName(), new CardView(((Permanent) object), game, controlled, false, false)));
                        } else {
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, card.getName(), new CardView(card, game, false, false, false)));
                        }
                    } else {
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, "", new CardView(card)));
                    }
                    if (card.isTransformable()) {
                        updateLatestCardView(game, card, stackObject.getId());
                    }
                    checkPaid(stackObject.getId(), (StackAbility) stackObject);
                } else if (object != null) {
                    if (object instanceof PermanentToken) {
                        PermanentToken token = (PermanentToken) object;
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, token.getName(), new CardView(token)));
                        checkPaid(stackObject.getId(), (StackAbility) stackObject);
                    } else if (object instanceof Emblem) {
                        CardView cardView = new CardView(new EmblemView((Emblem) object));
                        // Card sourceCard = (Card) ((Emblem) object).getSourceObject();
                        stackObject.setName(object.getName());
                        // ((StackAbility) stackObject).setExpansionSetCode(sourceCard.getExpansionSetCode());
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), cardView));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else if (object instanceof Plane) {
                        CardView cardView = new CardView(new PlaneView((Plane) object));
                        stackObject.setName(object.getName());
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), cardView));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else if (object instanceof Designation) {
                        Designation designation = (Designation) game.getObject(object.getId());
                        if (designation != null) {
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, designation.getName(), new CardView(designation)));
                        } else {
                            LOGGER.fatal("Designation object not found: " + object.getName() + ' ' + object.toString() + ' ' + object.getClass().toString());
                        }

                    } else if (object instanceof StackAbility) {
                        StackAbility stackAbility = ((StackAbility) object);
                        stackAbility.newId();
                        stack.put(stackObject.getId(), new CardView(stackObject));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else {
                        LOGGER.fatal("Object can't be cast to StackAbility: " + object.getName() + ' ' + object.toString() + ' ' + object.getClass().toString());
                    }
                } else {
                    // can happen if a player times out while ability is on the stack
                    LOGGER.debug("Stack Object for stack ability not found: " + stackObject.getStackAbility().getRule());
                }
            } else {
                LOGGER.fatal("Unknown type of StackObject: " + stackObject.getName() + ' ' + stackObject.toString() + ' ' + stackObject.getClass().toString());
            }
            //stackOrder.add(stackObject.getId());
        }
        //Collections.reverse(stackOrder);
        for (ExileZone exileZone : state.getExile().getExileZones()) {
            exiles.add(new ExileView(exileZone, game));
        }
        for (String name : state.getRevealed().keySet()) {
            revealed.add(new RevealedView(name, state.getRevealed().get(name), game));
        }
        this.phase = state.getTurn().getPhaseType();
        this.step = state.getTurn().getStepType();
        this.turn = state.getTurnNum();
        this.activePlayerId = state.getActivePlayerId();
        if (state.getActivePlayerId() != null) {
            this.activePlayerName = state.getPlayer(state.getActivePlayerId()).getName();
        } else {
            this.activePlayerName = "";
        }
        Player priorityPlayer = null;
        if (state.getPriorityPlayerId() != null) {
            priorityPlayer = state.getPlayer(state.getPriorityPlayerId());
            this.priorityPlayerName = priorityPlayer != null ? priorityPlayer.getName() : "";
        } else {
            this.priorityPlayerName = "";
        }
        for (CombatGroup combatGroup : state.getCombat().getGroups()) {
            combat.add(new CombatGroupView(combatGroup, game));
        }
        if (isPlayer) { // no watcher
            // has only to be set for active player with priority (e.g. pay mana by delve or Quenchable Fire special action)
            if (priorityPlayer != null && createdForPlayer != null && createdForPlayerId != null && createdForPlayer.isGameUnderControl()
                    && (createdForPlayerId.equals(priorityPlayer.getId()) // player controls the turn
                    || createdForPlayer.getPlayersUnderYourControl().contains(priorityPlayer.getId()))) { // player controls active players turn
                this.special = !state.getSpecialActions().getControlledBy(priorityPlayer.getId(), priorityPlayer.isInPayManaMode()).isEmpty();
            }
        } else {
            this.special = false;
        }

        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCastCurrentTurn = watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn();
        } else {
            spellsCastCurrentTurn = 0;
        }
        rollbackTurnsAllowed = game.getOptions().rollbackTurnsAllowed;
    }

    private void checkPaid(UUID uuid, StackAbility stackAbility) {
        for (Cost cost : stackAbility.getManaCostsToPay()) {
            if (!cost.isPaid()) {
                return;
            }
        }
        CardView cardView = stack.get(uuid);
        cardView.paid = true;
    }

    private void updateLatestCardView(Game game, Card card, UUID stackId) {
        if (!card.isTransformable()) {
            return;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(card.getId(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            if (permanent.isTransformed()) {
                StackAbilityView stackAbilityView = (StackAbilityView) stack.get(stackId);
                stackAbilityView.getSourceCard().setTransformed(true);
            }
        }
    }

    public List<PlayerView> getPlayers() {
        return players;
    }

    public CardsView getHand() {
        return hand;
    }

    public void setHand(CardsView hand) {
        this.hand = hand;
    }

    public Map<String, SimpleCardsView> getOpponentHands() {
        return opponentHands;
    }

    public void setOpponentHands(Map<String, SimpleCardsView> opponentHands) {
        this.opponentHands = opponentHands;
    }

    public Map<String, SimpleCardsView> getWatchedHands() {
        return watchedHands;
    }

    public void setWatchedHands(Map<String, SimpleCardsView> watchedHands) {
        this.watchedHands = watchedHands;
    }

    public TurnPhase getPhase() {
        return phase;
    }

    public PhaseStep getStep() {
        return step;
    }

    public CardsView getStack() {
        return stack;
    }

    public List<ExileView> getExile() {
        return exiles;
    }

    public List<RevealedView> getRevealed() {
        return revealed;
    }

    public List<LookedAtView> getLookedAt() {
        return lookedAt;
    }

    public void setLookedAt(List<LookedAtView> list) {
        this.lookedAt = list;
    }

    public List<CombatGroupView> getCombat() {
        return combat;
    }

    public int getTurn() {
        return this.turn;
    }

    public String getActivePlayerName() {
        return activePlayerName;
    }

    public String getPriorityPlayerName() {
        return priorityPlayerName;
    }

    public boolean getSpecial() {
        return special;
    }

    public int getPriorityTime() {
        return priorityTime;
    }

    public UUID getActivePlayerId() {
        return activePlayerId;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public Set<UUID> getCanPlayObjects() {
        return canPlayObjects;
    }

    public void setCanPlayObjects(Set<UUID> canPlayObjects) {
        this.canPlayObjects = canPlayObjects;
    }

    public int getSpellsCastCurrentTurn() {
        return spellsCastCurrentTurn;
    }

    public boolean isRollbackTurnsAllowed() {
        return rollbackTurnsAllowed;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
