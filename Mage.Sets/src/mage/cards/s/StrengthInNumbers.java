package mage.cards.s;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class StrengthInNumbers extends CardImpl {

    private static final DynamicValue xValue = new AttackingCreatureCount();

    public StrengthInNumbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Until end of turn, target creature gains trample and gets +X/+X, where X is the number of attacking creatures.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, target creature gains trample"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("and gets +X/+X, where X is the number of attacking creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StrengthInNumbers(final StrengthInNumbers card) {
        super(card);
    }

    @Override
    public StrengthInNumbers copy() {
        return new StrengthInNumbers(this);
    }
}
