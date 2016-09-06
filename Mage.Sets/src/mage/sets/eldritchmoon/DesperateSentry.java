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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class DesperateSentry extends CardImpl {

    public DesperateSentry(UUID ownerId) {
        super(ownerId, 21, "Desperate Sentry", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Desperate Sentry dies, put a 3/2 colorless Eldrazi Horror creature token onto the battlefield.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));

        // <i>Delirium</i> &mdash; Desperate Sentry gets +3/+0 as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield), DeliriumCondition.getInstance(),
                "<i>Delirium</i> &mdash; {this} gets +3/+0 as long as there are four or more card types among cards in your graveyard.");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
    }

    public DesperateSentry(final DesperateSentry card) {
        super(card);
    }

    @Override
    public DesperateSentry copy() {
        return new DesperateSentry(this);
    }
}
