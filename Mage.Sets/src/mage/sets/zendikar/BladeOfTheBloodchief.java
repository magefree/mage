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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.counters.common.PlusOneCounter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author mamaurer, nantuko
 */
public class BladeOfTheBloodchief extends CardImpl<BladeOfTheBloodchief> {
	
	public BladeOfTheBloodchief ( UUID ownerId ) {
		super(ownerId, 196, "Blade of the Bloodchief", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "ZEN";
		this.getSubtype().add("Equipment");
		
		this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1)));
		this.addAbility(new BladeOfTheBloodChiefTriggeredAbility());
	}
	
	public BladeOfTheBloodchief(final BladeOfTheBloodchief card) {
        super(card);
    }

    @Override
    public BladeOfTheBloodchief copy() {
        return new BladeOfTheBloodchief(this);
    }
}

class BladeOfTheBloodChiefTriggeredAbility extends TriggeredAbilityImpl<BladeOfTheBloodChiefTriggeredAbility> {
	
	private static final String text = "Whenever a creature dies, put a +1/+1 counter on equipped "
			+ "creature. If equipped creature is a Vampire, put two +1/+1 counters on it instead.";
	
	BladeOfTheBloodChiefTriggeredAbility ( ) {
		super(Zone.BATTLEFIELD, new BladeOfTheBloodchiefEffect());
	}
	
	BladeOfTheBloodChiefTriggeredAbility ( final BladeOfTheBloodChiefTriggeredAbility ability ) {
		super(ability);
	}
	
	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if ( event.getType() == EventType.ZONE_CHANGE ) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if ( zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD ) {
				Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (p != null && p.getCardType().contains(CardType.CREATURE)) {
					Permanent enchantment = game.getPermanent(getSourceId());
					if (enchantment != null && enchantment.getAttachedTo() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public BladeOfTheBloodChiefTriggeredAbility copy() {
		return new BladeOfTheBloodChiefTriggeredAbility(this);
	}

	@Override
	public String getRule() {
		return text;
	}
}

class BladeOfTheBloodchiefEffect extends OneShotEffect<BladeOfTheBloodchiefEffect> {

	BladeOfTheBloodchiefEffect ( ) {
		super(Outcome.BoostCreature);
	}
	
	BladeOfTheBloodchiefEffect ( final BladeOfTheBloodchiefEffect ability ) {
		super(ability);
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
		Permanent enchantment = game.getPermanent(source.getSourceId());
		if (enchantment != null && enchantment.getAttachedTo() != null) {
			Permanent creature = game.getPermanent(enchantment.getAttachedTo());
			if (creature != null) {
				if ( creature.hasSubtype("Vampire") ) {
					creature.addCounters(new PlusOneCounter(2), game);
				}
				else {
					creature.addCounters(new PlusOneCounter(1), game);
				}
			}
		}
		return true;
	}
	
	@Override
	public BladeOfTheBloodchiefEffect copy() {
		return new BladeOfTheBloodchiefEffect(this);
	}
}
