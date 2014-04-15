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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public class BearerOfTheHeavens extends CardImpl<BearerOfTheHeavens> {

    public BearerOfTheHeavens(UUID ownerId) {
        super(ownerId, 9991, "Bearer of the Heavens", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{7}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Giant");

        this.color.setRed(true);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When Bearer of the Heavens dies, destroy all permanents at the beginning of the next end step.
        DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new DestroyAllEffect(new FilterPermanent("permanents")));
        Effect effect = new CreateDelayedTriggeredAbilityEffect(delayedAbility);
        effect.setText("destroy all permanents at the beginning of the next end step");
        this.addAbility(new DiesTriggeredAbility(effect, false));
    }

    public BearerOfTheHeavens(final BearerOfTheHeavens card) {
        super(card);
    }

    @Override
    public BearerOfTheHeavens copy() {
        return new BearerOfTheHeavens(this);
    }
}
