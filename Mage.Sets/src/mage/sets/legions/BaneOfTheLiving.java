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
package mage.sets.legions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MorphManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class BaneOfTheLiving extends CardImpl {

    public BaneOfTheLiving(UUID ownerId) {
        super(ownerId, 60, "Bane of the Living", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Insect");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Morph {X}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{X}{B}{B}")));
        // When Bane of the Living is turned face up, all creatures get -X/-X until end of turn.
        DynamicValue morphX = new SignInversionDynamicValue(new MorphManacostVariableValue());
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BoostAllEffect(morphX, morphX, Duration.EndOfTurn, new FilterCreaturePermanent(), false, "", true)));
    }

    public BaneOfTheLiving(final BaneOfTheLiving card) {
        super(card);
    }

    @Override
    public BaneOfTheLiving copy() {
        return new BaneOfTheLiving(this);
    }
}
