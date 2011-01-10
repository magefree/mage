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

package mage.players;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.counters.Counters;
import mage.filter.FilterAbility;
import mage.game.events.GameEvent;
import mage.game.Game;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.Copyable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Player extends MageItem, Copyable<Player> {

	public boolean isHuman();
	public String getName();
	public Library getLibrary();
	public Cards getGraveyard();
	public Abilities<Ability> getAbilities();
	public Counters getCounters();
	public int getLife();
	public void setLife(int life, Game game);
	public int loseLife(int amount, Game game);
	public void gainLife(int amount, Game game);
	public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable);
	public Cards getHand();
	public int getLandsPlayed();
	public boolean isPassed();
	public boolean isEmptyDraw();
	public void pass();
	public void resetPassed();
	public boolean hasLost();
	public boolean hasWon();
	public boolean hasLeft();
	public ManaPool getManaPool();
	public Set<UUID> getInRange();
	
	public void init(Game game);
	public void useDeck(Deck deck, Game game);
	public void reset();
	public void shuffleLibrary(Game game);
	public int drawCards(int num, Game game);
	public boolean cast(SpellAbility ability, Game game, boolean noMana);
	public boolean putInHand(Card card, Game game);
	public boolean removeFromHand(Card card, Game game);
	public boolean removeFromBattlefield(Permanent permanent, Game game);
	public boolean putInGraveyard(Card card, Game game, boolean fromBattlefield);
	public boolean removeFromGraveyard(Card card, Game game);
	public boolean searchLibrary(TargetCardInLibrary target, Game game);
	public boolean canPlayLand();
	public boolean playLand(Card card, Game game);
	public boolean activateAbility(ActivatedAbility ability, Game game);
	public boolean triggerAbility(TriggeredAbility ability, Game game);
	public boolean canBeTargetedBy(MageObject source);
	public boolean hasProtectionFrom(MageObject source);
	public boolean flipCoin();
	public void checkTriggers(GameEvent event, Game game);
	public void discard(int amount, Ability source, Game game);
	public void discardToMax(Game game);
	public boolean discard(Card card, Ability source, Game game);
	public void lost(Game game);
	public void won(Game game);
	public void leaveGame(Game game);
	public void concede(Game game);
	public void abort();
	
	public void revealCards(Cards cards, Game game);
	public void lookAtCards(Cards cards, Game game);
	
	@Override
	public Player copy();
	public void restore(Player player);

	public void setResponseString(String responseString);
	public void setResponseUUID(UUID responseUUID);
	public void setResponseBoolean(Boolean responseBoolean);
	public void setResponseInteger(Integer data);

	public abstract void priority(Game game);
	public abstract boolean choose(Outcome outcome, Target target, Game game);
	public abstract boolean choose(Cards cards, TargetCard target, Game game);
	public abstract boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game);
	public abstract boolean chooseTarget(Cards cards, TargetCard target, Ability source, Game game);
	public abstract boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game);
	public abstract boolean chooseMulligan(Game game);
	public abstract boolean chooseUse(Outcome outcome, String message, Game game);
	public abstract boolean choose(Outcome outcome, Choice choice, Game game);
	public abstract boolean playMana(ManaCost unpaid, Game game);
	public abstract boolean playXMana(VariableManaCost cost, Game game);
	public abstract int chooseEffect(List<ReplacementEffect> rEffects, Game game);
	public abstract TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game);
	public abstract void selectAttackers(Game game);
	public abstract void selectBlockers(Game game);
	public abstract void assignDamage(int damage, List<UUID> targets, UUID sourceId, Game game);
	public abstract int getAmount(int min, int max, String message, Game game);
	public abstract void sideboard(Table table, Deck deck);
	public abstract void construct(Table table, Deck deck);
	public abstract void pickCard(List<Card> cards, Deck deck, Draft draft);
	
	public void declareAttacker(UUID attackerId, UUID defenderId, Game game);
	public void declareBlocker(UUID blockerId, UUID attackerId, Game game);
	public List<Permanent> getAvailableAttackers(Game game);
	public List<Permanent> getAvailableBlockers(Game game);
	
	public void beginTurn(Game game);
	public void endOfTurn(Game game);
	public void phasing(Game game);
	public void untap(Game game);

	public List<Ability> getPlayable(Game game, FilterAbility filter, ManaOptions available, boolean hidden);
	public List<Ability> getPlayableOptions(Ability ability, Game game);
	
}
