
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class StrengthInNumbers extends CardImpl {

    public StrengthInNumbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Until end of turn, target creature gains trample and gets +X/+X, where X is the number of attacking creatures.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new BoostTargetEffect(new AttackingCreatureCount("the number of attacking creatures"), new AttackingCreatureCount(), Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public StrengthInNumbers(final StrengthInNumbers card) {
        super(card);
    }

    @Override
    public StrengthInNumbers copy() {
        return new StrengthInNumbers(this);
    }
}
