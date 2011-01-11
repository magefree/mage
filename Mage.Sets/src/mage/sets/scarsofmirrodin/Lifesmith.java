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
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public class Lifesmith extends CardImpl<Lifesmith> {

    public Lifesmith (UUID ownerId) {
        super(ownerId, 124, "Lifesmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
		this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new LifesmithTriggeredAbility());
    }

    public Lifesmith (final Lifesmith card) {
        super(card);
    }

    @Override
    public Lifesmith copy() {
        return new Lifesmith(this);
    }
}

class LifesmithTriggeredAbility extends TriggeredAbilityImpl<LifesmithTriggeredAbility> {
    public LifesmithTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(3));

    }

    public LifesmithTriggeredAbility(final LifesmithTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LifesmithTriggeredAbility copy() {
        return new LifesmithTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may pay {1}. If you do, you gain 3 life.";
    }
}
