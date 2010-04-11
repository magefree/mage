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

package mage.game;

import mage.game.stack.SpellStack;
import mage.MageObject;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import mage.MageItem;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.events.TableEvent;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.turn.Turn;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;

public interface Game extends MageItem, Serializable {

	public String getGameType();
	public int getNumPlayers();
	public int getLife();
	
	//game data methods
	public MageObject getObject(UUID objectId);
	public Permanent getPermanent(UUID permanentId);
	public void addPlayer(Player player) throws GameException;
	public Player getPlayer(UUID playerId);
	public Players getPlayers();
	public PlayerList getPlayerList(UUID playerId);
	public List<UUID> getOpponents(UUID controllerId);
	public Turn getTurn();
	public int getTurnNum();
	public boolean isMainPhase();
	public boolean canPlaySorcery(UUID playerId);
	public UUID getActivePlayerId();
	public UUID getPriorityPlayerId();
	public boolean isGameOver();
	public Battlefield getBattlefield();
	public SpellStack getStack();
	public Exile getExile();
	public Combat getCombat();
	public GameStates getGameStates();
	public GameState getState();
	public String getWinner();
	public ContinuousEffects getContinuousEffects();

	//client event methods
	public void addTableEventListener(Listener<TableEvent> listener);
	public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);
	public void fireAskPlayerEvent(UUID playerId, String message);
	public void fireChooseEvent(UUID playerId, Choice choice);
	public void fireSelectTargetEvent(UUID playerId, String message, boolean required);
	public void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required);
	public void fireSelectTargetEvent(UUID playerId, String message, TriggeredAbilities abilities, boolean required);
	public void fireSelectEvent(UUID playerId, String message);
	public void firePriorityEvent(UUID playerId);
	public void firePlayManaEvent(UUID playerId, String message);
	public void firePlayXManaEvent(UUID playerId, String message);
	public void fireGetChoiceEvent(UUID playerId, String message, Collection<? extends ActivatedAbility> choices);
	public void fireGetAmountEvent(UUID playerId, String message, int min, int max);
	public void fireInformEvent(String message);
	public void fireUpdatePlayersEvent();
	public void informPlayers(String message);

	//game event methods
	public void fireEvent(GameEvent event);
	public boolean replaceEvent(GameEvent event);

	//game play methods
	public void start();
	public void end();
	public void mulligan(UUID playerId);
	public void quit(UUID playerId);
	public void concede(UUID playerId);
	public void emptyManaPools();
	public void addEffect(ContinuousEffect continuousEffect);
	public void addTriggeredAbility(TriggeredAbility ability);
	public void applyEffects();
//	public boolean checkStateAndTriggered();
//	public boolean checkStateBasedActions();
	public void playPriority(UUID activePlayerId);
	public boolean playUntapStep(UUID activePlayerId);
	public boolean playUpkeepStep(UUID activePlayerId);
	public boolean playDrawStep(UUID activePlayerId);
	public boolean playPreCombatMainStep(UUID activePlayerId);
	public boolean playBeginCombatStep(UUID activePlayerId);
	public boolean playDeclareAttackersStep(UUID activePlayerId);
	public boolean playDeclareBlockersStep(UUID activePlayerId);
	public boolean playCombatDamageStep(UUID activePlayerId, boolean first);
	public boolean playEndCombatStep(UUID activePlayerId);
	public boolean playPostMainStep(UUID activePlayerId);
	public boolean playEndStep(UUID activePlayerId);
	public boolean playCleanupStep(UUID activePlayerId);

	//game transaction methods
	public void saveState();
	public void bookmarkState();
	public void restoreState();
	public void removeLastBookmark();

}
