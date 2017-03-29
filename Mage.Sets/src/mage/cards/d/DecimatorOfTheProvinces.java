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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DecimatorOfTheProvinces extends CardImpl {

    public DecimatorOfTheProvinces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{10}");
        this.subtype.add("Eldrazi");
        this.subtype.add("Boar");
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Emerge {6}{G}{G}{G}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{6}{G}{G}{G}")));

        // When you cast Decimator of the Provinces, creatures you control get +2/+2 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect.setText("creatures you control get +2/+2");
        Ability ability = new CastSourceTriggeredAbility(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    public DecimatorOfTheProvinces(final DecimatorOfTheProvinces card) {
        super(card);
    }

    @Override
    public DecimatorOfTheProvinces copy() {
        return new DecimatorOfTheProvinces(this);
    }
}
