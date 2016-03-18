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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class ArlinnKord extends CardImpl {

    public ArlinnKord(UUID ownerId) {
        super(ownerId, 243, "Arlinn Kord", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Arlinn");

        this.canTransform = true;
        this.secondSideCard = new ArlinnEmbracedByTheMoon(ownerId);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Until end of turn, up to one target creature gets +2/+2 and gains vigilance and haste.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Until end of turn, up to one target creature gets +2/+2");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and haste");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // 0: Put a 2/2 green Wolf creature token onto the battlefield. Transform Arlinn Kord.
        this.addAbility(new TransformAbility());
        ability = new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0);
        ability.addEffect(new TransformSourceEffect(true));
        this.addAbility(ability);
    }

    public ArlinnKord(final ArlinnKord card) {
        super(card);
    }

    @Override
    public ArlinnKord copy() {
        return new ArlinnKord(this);
    }
}
