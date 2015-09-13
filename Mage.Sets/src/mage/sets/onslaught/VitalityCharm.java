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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.InsectToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class VitalityCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Beast");

    static {
        filter.add(new SubtypePredicate("Beast"));
    }

    public VitalityCharm(UUID ownerId) {
        super(ownerId, 296, "Vitality Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ONS";

        // Choose one - Put a 1/1 green Insect creature token onto the battlefield
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectToken()));
        // or target creature gets +1/+1 and gains trample until end of turn
        Mode mode = new Mode();
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("target creature gets +1/+1");
        mode.getEffects().add(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or regenerate target Beast.
        mode = new Mode();
        mode.getEffects().add(new RegenerateTargetEffect());
        mode.getTargets().add(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    public VitalityCharm(final VitalityCharm card) {
        super(card);
    }

    @Override
    public VitalityCharm copy() {
        return new VitalityCharm(this);
    }
}
