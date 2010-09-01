/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.sets.RiseOfTheEldrazi;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Vengevine extends CardImpl<Vengevine> {

	public Vengevine(UUID ownerId) {
		super(ownerId, "Vengevine", new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
		this.expansionSetId = RiseOfTheEldrazi.getInstance().getId();
		this.subtype.add("Elemental");
		this.color.setGreen(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(3);

		this.addAbility(HasteAbility.getInstance());
		this.addAbility(new VengevineAbility());
		this.watchers.add(new VengevineWatcher(ownerId));
	}

	public Vengevine(final Vengevine card) {
		super(card);
	}

	@Override
	public Vengevine copy() {
		return new Vengevine(this);
	}

	@Override
	public String getArt() {
		return "127350_typ_reg_sty_010.jpg";
	}

}

class VengevineAbility extends TriggeredAbilityImpl<VengevineAbility> {

	public VengevineAbility() {
		super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), true);
	}

	public VengevineAbility(final VengevineAbility ability) {
		super(ability);
	}

	@Override
	public VengevineAbility copy() {
		return new VengevineAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && event.getPlayerId().equals(controllerId)) {
			Watcher watcher = game.getState().getWatchers().get(controllerId, "CreatureCast");
			if (watcher != null && watcher.conditionMet()) {
				trigger(game, event.getPlayerId());
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever you cast a spell, if it's the second creature spell you cast this turn, you may return Vengevine from your graveyard to the battlefield.";
	}

}


class VengevineWatcher extends WatcherImpl<VengevineWatcher> {

	int creatureSpellCount = 0;

	public VengevineWatcher(UUID controllerId) {
		super("CreatureCast", controllerId);
	}

	public VengevineWatcher(final VengevineWatcher watcher) {
		super(watcher);
	}

	@Override
	public VengevineWatcher copy() {
		return new VengevineWatcher(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && event.getPlayerId().equals(controllerId)) {
			Spell spell = (Spell)game.getStack().getStackObject(event.getTargetId());
			if (spell.getCardType().contains(CardType.CREATURE)) {
				creatureSpellCount++;
				if (creatureSpellCount == 2)
					condition = true;
			}
		}
	}

	@Override
	public void reset() {
		super.reset();
		creatureSpellCount = 0;
	}

}
