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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */
public class SyggRiverCutthroat extends CardImpl<SyggRiverCutthroat> {

    public SyggRiverCutthroat(UUID ownerId) {
        super(ownerId, 176, "Sygg, River Cutthroat", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}");
        this.expansionSetCode = "SHM";
        this.supertype.add("Legendary");
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of each end step, if an opponent lost 3 or more life this turn, you may draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardControllerEffect(1),
                Constants.TargetController.ANY,
                new OpponentLostLifeCondition(Condition.ComparisonType.GreaterThan, 2),
                true));
    }

    public SyggRiverCutthroat(final SyggRiverCutthroat card) {
        super(card);
    }

    @Override
    public SyggRiverCutthroat copy() {
        return new SyggRiverCutthroat(this);
    }
}
