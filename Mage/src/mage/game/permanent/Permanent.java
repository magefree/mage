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

package mage.game.permanent;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;

public interface Permanent extends Card {

	public boolean isTapped();
	public boolean untap(Game game);
	public boolean tap(Game game);
	public void setTapped(boolean tapped);
	public boolean canTap();
	
	public boolean isFlipped();
	public boolean unflip(Game game);
	public boolean flip(Game game);

	public boolean isPhasedIn();
	public boolean phaseIn(Game game);
	public boolean phaseOut(Game game);

	public boolean isFaceUp();
	public boolean turnFaceUp(Game game);
	public boolean turnFaceDown(Game game);

	public List<UUID> getAttachments();
	public UUID getAttachedTo();
	public void attachTo(UUID permanentId);
	public boolean addAttachment(UUID permanentId, Game game);
	public boolean removeAttachment(UUID permanentId, Game game);
	
	public UUID getControllerId();
	public boolean changeControllerId(UUID controllerId, Game game);
	public boolean canBeTargetedBy(MageObject source);
	public boolean hasProtectionFrom(MageObject source);
	public boolean hasSummoningSickness();
	public int getDamage();
	public int damage(int damage, UUID sourceId, Game game, boolean preventable);
	public void removeAllDamage(Game game);
	public Counters getCounters();
	public void addCounters(String name, int amount);
	public void addCounters(Counter counter);
	public void removeCounters(String name, int amount, Game game);
	public void reset(Game game);
	public boolean destroy(UUID sourceId, Game game, boolean noRegen);
	public boolean sacrifice(UUID sourceId, Game game);
	public void entersBattlefield(UUID sourceId, Game game);
	public String getValue();
	public void setArt(String art);

	@Override
	public void addAbility(Ability ability);

	public void setLoyaltyUsed(boolean used);
	public boolean isLoyaltyUsed();

	public void endOfTurn(Game game);
	public void checkTriggers(GameEvent event, Game game);
	public int getTurnsOnBattlefield();

	public void addPower(int power);
	public void addToughness(int toughness);

	public boolean isAttacking();
	public int getBlocking();
	public void setAttacking(boolean attacking);
	public void setBlocking(int blocking);
	public int getMaxBlocks();
	public void setMaxBlocks(int maxBlocks);
	public boolean canAttack(Game game);
	public boolean canBlock(UUID attackerId, Game game);
	public boolean removeFromCombat(Game game);
	public boolean isDeathtouched();

	@Override
	public Permanent copy();

}
