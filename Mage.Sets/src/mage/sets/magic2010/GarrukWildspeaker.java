/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.magic2010;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GarrukWildspeaker extends CardImpl {

    private static BeastToken beastToken = new BeastToken();

    public GarrukWildspeaker(UUID ownerId) {
        super(ownerId, 183, "Garruk Wildspeaker", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}");
        this.expansionSetCode = "M10";
        this.subtype.add("Garruk");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Untap two target lands.
        LoyaltyAbility ability1 = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability1.addTarget(new TargetLandPermanent(2));
        this.addAbility(ability1);

        // −1: Put a 3/3 green Beast creature token onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(beastToken), -1));

        // −4: Creatures you control get +3/+3 and gain trample until end of turn.
        Effects effects1 = new Effects();
        Effect effect = new BoostControlledEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Creatures you control get +3/+3");
        effects1.add(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("and gain trample until end of turn");
        effects1.add(effect);
        this.addAbility(new LoyaltyAbility(effects1, -4));
    }

    public GarrukWildspeaker(final GarrukWildspeaker card) {
        super(card);
    }

    @Override
    public GarrukWildspeaker copy() {
        return new GarrukWildspeaker(this);
    }
}
