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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author nantuko
 */
public class RelicPutrescence extends CardImpl<RelicPutrescence> {

    public RelicPutrescence (UUID ownerId) {
        super(ownerId, 77, "Relic Putrescence", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Aura");
		this.color.setBlack(true);
		TargetPermanent auraTarget = new TargetArtifactPermanent();
		this.getSpellAbility().addTarget(auraTarget);
		this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
		Ability ability = new EnchantAbility(auraTarget.getTargetName());
		this.addAbility(ability);
		this.addAbility(new RelicPutrescenceAbility());
    }

    public RelicPutrescence (final RelicPutrescence card) {
        super(card);
    }

    @Override
    public RelicPutrescence copy() {
        return new RelicPutrescence(this);
    }

}

class RelicPutrescenceAbility extends TriggeredAbilityImpl<RelicPutrescenceAbility> {

	public RelicPutrescenceAbility() {
		super(Zone.BATTLEFIELD, new AddCountersControllerEffect(CounterType.POISON.createInstance(), true));
	}

	public RelicPutrescenceAbility(final RelicPutrescenceAbility ability) {
		super(ability);
	}

	@Override
	public RelicPutrescenceAbility copy() {
		return new RelicPutrescenceAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.TAPPED) {
			Permanent enchantment = game.getPermanent(sourceId);
			if (enchantment != null && enchantment.getAttachedTo() != null) {
				if (event.getTargetId().equals(enchantment.getAttachedTo())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever enchanted artifact becomes tapped, its controller gets a poison counter.";
	}

}
