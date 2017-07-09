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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.command.emblems.SorinSolemnVisitorEmblem;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SorinSolemnVisitorVampireToken;

/**
 *
 * @author LevelX2
 */
public class SorinSolemnVisitor extends CardImpl {

    public SorinSolemnVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.subtype.add("Sorin");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Until your next turn, creatures you control get +1/+0 and gain lifelink.
        Effect effect = new BoostControlledEffect(1, 0, Duration.UntilYourNextTurn, new FilterCreaturePermanent());
        effect.setText("Until your next turn, creatures you control get +1/+0");
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.UntilYourNextTurn, new FilterCreaturePermanent());
        effect.setText("and gain lifelink");
        loyaltyAbility.addEffect(effect);
        this.addAbility(loyaltyAbility);

        // -2: Create a 2/2 black Vampire creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SorinSolemnVisitorVampireToken()), -2));

        // -6: You get an emblem with "At the beginning of each opponent's upkeep, that player sacrifices a creature."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SorinSolemnVisitorEmblem()), -6));

    }

    public SorinSolemnVisitor(final SorinSolemnVisitor card) {
        super(card);
    }

    @Override
    public SorinSolemnVisitor copy() {
        return new SorinSolemnVisitor(this);
    }
}