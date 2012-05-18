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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author noxx
 */
public class SoulOfTheHarvest extends CardImpl<SoulOfTheHarvest> {

    public SoulOfTheHarvest(UUID ownerId) {
        super(ownerId, 195, "Soul of the Harvest", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(TrampleAbility.getInstance());

        // Whenever another nontoken creature enters the battlefield under your control, you may draw a card.
        this.addAbility(new SoulOfTheHarvestAbility());
    }

    public SoulOfTheHarvest(final SoulOfTheHarvest card) {
        super(card);
    }

    @Override
    public SoulOfTheHarvest copy() {
        return new SoulOfTheHarvest(this);
    }
}

class SoulOfTheHarvestAbility extends TriggeredAbilityImpl<SoulOfTheHarvestAbility> {

    public SoulOfTheHarvestAbility() {
        super(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), true);
    }

    public SoulOfTheHarvestAbility(final SoulOfTheHarvestAbility ability) {
        super(ability);
    }

    @Override
    public SoulOfTheHarvestAbility copy() {
        return new SoulOfTheHarvestAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && !event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getControllerId().equals(this.controllerId) && !(permanent instanceof PermanentToken) && permanent.getCardType().contains(CardType.CREATURE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another nontoken creature enters the battlefield under your control, you may draw a card";
    }

}
