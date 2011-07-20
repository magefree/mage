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

package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class SuturePriest extends CardImpl<SuturePriest> {

    public SuturePriest (UUID ownerId) {
        super(ownerId, 25, "Suture Priest", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Cleric");
		this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SuturePriestFirstTriggeredAbility());
        this.addAbility(new SuturePriestSecondTriggeredAbility());
    }

    public SuturePriest (final SuturePriest card) {
        super(card);
    }

    @Override
    public SuturePriest copy() {
        return new SuturePriest(this);
    }
}

class SuturePriestFirstTriggeredAbility extends TriggeredAbilityImpl<SuturePriestFirstTriggeredAbility> {
    SuturePriestFirstTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    SuturePriestFirstTriggeredAbility(final SuturePriestFirstTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SuturePriestFirstTriggeredAbility copy() {
        return new SuturePriestFirstTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && !event.getTargetId().equals(this.getSourceId()) && event.getPlayerId().equals(this.controllerId)) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            Card card = zEvent.getTarget();
			if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD && card != null && card.getCardType().contains(CardType.CREATURE)) {
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield under your control, " + modes.getText(this);
    }
}

class SuturePriestSecondTriggeredAbility extends TriggeredAbilityImpl<SuturePriestSecondTriggeredAbility> {
    SuturePriestSecondTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), true);
    }

    SuturePriestSecondTriggeredAbility(final SuturePriestSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SuturePriestSecondTriggeredAbility copy() {
        return new SuturePriestSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            Card card = zEvent.getTarget();
			if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD && card != null && card.getCardType().contains(CardType.CREATURE)) {
                for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.";
    }
}