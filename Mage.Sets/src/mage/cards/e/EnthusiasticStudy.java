package mage.cards.e;

import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnthusiasticStudy extends CardImpl {

    public EnthusiasticStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target creature gets +3/+1 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                3, 1
        ).setText("target creature gets +3/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private EnthusiasticStudy(final EnthusiasticStudy card) {
        super(card);
    }

    @Override
    public EnthusiasticStudy copy() {
        return new EnthusiasticStudy(this);
    }
}
