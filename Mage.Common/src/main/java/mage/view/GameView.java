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
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.PlayableObjectsList;
import mage.players.Player;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class GameView implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(GameView.class);

    private final int priorityTime;
    private final int bufferTime;
    private final List<PlayerView> players = new ArrayList<>();
    private UUID myPlayerId = null; // null for watcher
    private final CardsView myHand = new CardsView();
    private PlayableObjectsList canPlayObjects;
    private final Map<String, SimpleCardsView> opponentHands = new HashMap<>();
    private final Map<String, SimpleCardsView> watchedHands = new HashMap<>();
    private final CardsView stack = new CardsView();
    private final List<ExileView> exiles = new ArrayList<>();
    private final List<RevealedView> revealed = new ArrayList<>();
    private final List<LookedAtView> lookedAt = new ArrayList<>();
    private final List<RevealedView> companion = new ArrayList<>();
    private final List<CombatGroupView> combat = new ArrayList<>();
    private final TurnPhase phase;
    private final PhaseStep step;
    private final UUID activePlayerId;
    private String activePlayerName = "";
    private final String priorityPlayerName;
    private final int turn;
    private boolean special = false;
    private final boolean rollbackTurnsAllowed;
    private int totalErrorsCount;

    public GameView(GameState state, Game game, UUID createdForPlayerId, UUID watcherUserId) {
        Player createdForPlayer = null;
        this.priorityTime = game.getPriorityTime();
        this.bufferTime = game.getBufferTime();

        for (Player player : state.getPlayers().values()) {
            PlayerView playerView = new PlayerView(player, state, game, createdForPlayerId, watcherUserId);
            players.add(playerView);
            if (player.getId().equals(createdForPlayerId)) {
                createdForPlayer = player;
                this.myPlayerId = player.getId();
                this.myHand.putAll(new CardsView(game, player.getHand().getCards(game), createdForPlayerId));
            }
        }
        for (StackObject stackObject : state.getStack()) {
            if (stackObject instanceof Spell) {
                // Spell
                Spell spell = (Spell) stackObject;
                CardView spellView = new CardView(spell, game, CardUtil.canShowAsControlled(spell, createdForPlayerId));
                spellView.paid = spell.getSpellAbility().getManaCostsToPay().isPaid();
                stack.put(spell.getId(), spellView);
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
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, object.getName(), object, new CardView(((Permanent) object), game, controlled, false)));
                        } else {
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, card.getName(), card, new CardView(card, game, false, false)));
                        }
                    } else {
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, "", card, new CardView(card, game)));
                    }
                    if (card.isTransformable()) {
                        updateLatestCardView(game, card, stackObject.getId());
                    }
                    checkPaid(stackObject.getId(), (StackAbility) stackObject);
                } else if (object != null) {
                    if (object instanceof PermanentToken) {
                        PermanentToken token = (PermanentToken) object;
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, token.getName(), token, new CardView(token, game)));
                        checkPaid(stackObject.getId(), (StackAbility) stackObject);
                    } else if (object instanceof Emblem) {
                        CardView cardView = new CardView(new EmblemView((Emblem) object));
                        // Card sourceCard = (Card) ((Emblem) object).getSourceObject();
                        stackObject.setName(object.getName());
                        // ((StackAbility) stackObject).setExpansionSetCode(sourceCard.getExpansionSetCode());
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), object, cardView));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else if (object instanceof Dungeon) {
                        CardView cardView = new CardView(new DungeonView((Dungeon) object));
                        stackObject.setName(object.getName());
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), object, cardView));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else if (object instanceof Plane) {
                        CardView cardView = new CardView(new PlaneView((Plane) object));
                        stackObject.setName(object.getName());
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), object, cardView));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else if (object instanceof Designation) {
                        Designation designation = (Designation) game.getObject(object.getId());
                        if (designation != null) {
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, designation.getName(), designation, new CardView(designation, (StackAbility) stackObject)));
                        } else {
                            throw new IllegalArgumentException("Designation object not found: " + object + " - " + object.getClass().toString());
                        }
                    } else if (object instanceof StackAbility) {
                        StackAbility stackAbility = ((StackAbility) object);
                        stackAbility.newId();
                        stack.put(stackObject.getId(), new CardView(stackObject, game));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else {
                        throw new IllegalArgumentException("Object can't be cast to StackAbility: " + object + " - " + object.getClass().toString());
                    }
                } else {
                    // can happen if a player times out while ability is on the stack
                    LOGGER.debug("Stack Object for stack ability not found: " + stackObject.getStackAbility().getRule());
                }
            } else if (stackObject != null) {
                throw new IllegalArgumentException("Unknown type of StackObject: " + stackObject + " - " + stackObject.getClass().toString());
            }
        }

        for (ExileZone exileZone : state.getExile().getExileZones()) {
            exiles.add(new ExileView(exileZone, game, createdForPlayerId));
        }
        for (String name : state.getRevealed().keySet()) {
            revealed.add(new RevealedView(name, state.getRevealed().get(name), game));
        }
        if (this.myPlayerId != null) {
            for (String name : state.getLookedAt(this.myPlayerId).keySet()){
                lookedAt.add(new LookedAtView(name, state.getLookedAt(this.myPlayerId).get(name), game));
            }
        }
        for (String name : state.getCompanion().keySet()) {
            // Only show the companion window when the companion is still outside the game.
            if (state.getCompanion().get(name).stream().anyMatch(cardId -> state.getZone(cardId) == Zone.OUTSIDE)) {
                companion.add(new RevealedView(name, state.getCompanion().get(name), game));
            }
        }
        this.phase = state.getTurnPhaseType();
        this.step = state.getTurnStepType();
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
        if (this.myPlayerId != null) { // no watcher
            // has only to be set for active player with priority (e.g. pay mana by delve or Quenchable Fire special action)
            if (priorityPlayer != null && createdForPlayer != null && createdForPlayer.isGameUnderControl()
                    && (createdForPlayerId.equals(priorityPlayer.getId()) // player controls the turn
                    || createdForPlayer.getPlayersUnderYourControl().contains(priorityPlayer.getId()))) { // player controls active players turn
                this.special = !state.getSpecialActions().getControlledBy(priorityPlayer.getId(), priorityPlayer.isInPayManaMode()).isEmpty();
            }
        } else {
            this.special = false;
        }
        this.rollbackTurnsAllowed = game.getOptions().rollbackTurnsAllowed;
        this.totalErrorsCount = game.getTotalErrorsCount();
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

    public CardsView getMyHand() {
        return myHand;
    }

    public PlayerView getMyPlayer() {
        if (this.myPlayerId == null) {
            return null;
        } else {
            return players.stream().filter(p -> p.getPlayerId().equals(this.myPlayerId)).findFirst().orElse(null);
        }
    }

    public Map<String, SimpleCardsView> getOpponentHands() {
        return opponentHands;
    }

    public Map<String, SimpleCardsView> getWatchedHands() {
        return watchedHands;
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

    public List<RevealedView> getCompanion() {
        return companion;
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

    public int getBufferTime() {
        return bufferTime;
    }

    public UUID getActivePlayerId() {
        return activePlayerId;
    }

    public boolean isPlayer() {
        return this.myPlayerId != null;
    }

    public PlayableObjectsList getCanPlayObjects() {
        return canPlayObjects;
    }

    public void setCanPlayObjects(PlayableObjectsList canPlayObjects) {
        this.canPlayObjects = canPlayObjects;
    }

    public boolean isRollbackTurnsAllowed() {
        return rollbackTurnsAllowed;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public int getTotalErrorsCount() {
        return this.totalErrorsCount;
    }
}
