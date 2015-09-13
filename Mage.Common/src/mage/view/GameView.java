/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameState;
import mage.game.combat.CombatGroup;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameView implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final transient Logger logger = Logger.getLogger(GameView.class);

    private final int priorityTime;
    private final List<PlayerView> players = new ArrayList<>();
    private CardsView hand;
    private Set<UUID> canPlayInHand;
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
    private String priorityPlayerName = "";
    private final int turn;
    private boolean special = false;
    private final boolean isPlayer;
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
            if (stackObject instanceof StackAbility) {
                // Stack Ability
                MageObject object = game.getObject(stackObject.getSourceId());
                Card card = game.getCard(stackObject.getSourceId());
                if (card != null) {
                    if (object != null) {
                        if (object instanceof Permanent) {
                            boolean controlled = ((Permanent) object).getControllerId().equals(createdForPlayerId);
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, ((Permanent) object).getName(), new CardView(((Permanent) object), game, controlled, false)));
                        } else {
                            stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, card.getName(), new CardView(card, game, false, false)));
                        }
                    } else {
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, "", new CardView(card)));
                    }
                    if (card.canTransform()) {
                        updateLatestCardView(game, card, stackObject.getId());
                    }
                    checkPaid(stackObject.getId(), (StackAbility) stackObject);
                } else if (object != null) {
                    if (object instanceof PermanentToken) {
                        PermanentToken token = (PermanentToken) object;
                        stack.put(stackObject.getId(), new StackAbilityView(game, (StackAbility) stackObject, token.getName(), new CardView(token)));
                        checkPaid(stackObject.getId(), (StackAbility) stackObject);
                    } else if (object instanceof Emblem) {
                        Card sourceCard = game.getCard(((Emblem) object).getSourceId());
                        if (sourceCard != null) {
                            if (!sourceCard.getCardType().contains(CardType.PLANESWALKER)) {
                                if (sourceCard.getSecondCardFace() != null) {
                                    sourceCard = sourceCard.getSecondCardFace();
                                }
                            }
                            ((StackAbility) stackObject).setName("Emblem " + sourceCard.getName());
                            ((StackAbility) stackObject).setExpansionSetCode(sourceCard.getExpansionSetCode());
                        } else {
                            throw new IllegalArgumentException("Source card for emblem not found.");
                        }
                        stack.put(stackObject.getId(),
                                new StackAbilityView(game, (StackAbility) stackObject, object.getName(), new CardView(new EmblemView(((Emblem) object), sourceCard))));
                        checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                    } else {
                        if (object instanceof StackAbility) {
                            StackAbility stackAbility = ((StackAbility) object);
                            stackAbility.newId();
                            stack.put(stackObject.getId(), new CardView(((StackAbility) stackObject)));
                            checkPaid(stackObject.getId(), ((StackAbility) stackObject));
                        } else {
                            logger.fatal("Object can't be cast to StackAbility: " + object.getName() + " " + object.toString() + " " + object.getClass().toString());
                        }
                    }
                } else {
                    // can happen if a player times out while ability is on the stack
                    logger.debug("Stack Object for stack ability not found: " + stackObject.getStackAbility().getRule());
                }
            } else {
                // Spell
                stack.put(stackObject.getId(), new CardView((Spell) stackObject, game, stackObject.getControllerId().equals(createdForPlayerId)));
                checkPaid(stackObject.getId(), (Spell) stackObject);
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
        if (state.getPriorityPlayerId() != null) {
            this.priorityPlayerName = state.getPlayer(state.getPriorityPlayerId()).getName();
        } else {
            this.priorityPlayerName = "";
        }
        for (CombatGroup combatGroup : state.getCombat().getGroups()) {
            combat.add(new CombatGroupView(combatGroup, game));
        }
        if (isPlayer) {
            // has only to be set for active palyer with priority (e.g. pay mana by delve or Quenchable Fire special action)
            if (state.getPriorityPlayerId() == createdForPlayerId && createdForPlayer != null) {
                this.special = state.getSpecialActions().getControlledBy(state.getPriorityPlayerId(), createdForPlayer.isInPayManaMode()).size() > 0;
            }
        } else {
            this.special = false;
        }

        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get("CastSpellLastTurnWatcher");
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

    private void checkPaid(UUID uuid, Spell spell) {
        for (Cost cost : spell.getSpellAbility().getManaCostsToPay()) {
            if (!cost.isPaid()) {
                return;
            }
        }
        CardView cardView = stack.get(uuid);
        cardView.paid = true;
    }

    private void setPaid(UUID uuid) {
        CardView cardView = stack.get(uuid);
        cardView.paid = true;
    }

    private void updateLatestCardView(Game game, Card card, UUID stackId) {
        if (!card.canTransform()) {
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

    public Set<UUID> getCanPlayInHand() {
        return canPlayInHand;
    }

    public void setCanPlayInHand(Set<UUID> canPlayInHand) {
        this.canPlayInHand = canPlayInHand;
    }

    public int getSpellsCastCurrentTurn() {
        return spellsCastCurrentTurn;
    }

    public boolean isRollbackTurnsAllowed() {
        return rollbackTurnsAllowed;
    }

}
