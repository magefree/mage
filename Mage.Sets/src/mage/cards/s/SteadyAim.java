package mage.cards.s;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteadyAim extends CardImpl {

    public SteadyAim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Untap target creature. It gets +1/+4 and gains reach until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 4, Duration.EndOfTurn
        ).setText("It gets +1/+4"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains reach until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SteadyAim(final SteadyAim card) {
        super(card);
    }

    @Override
    public SteadyAim copy() {
        return new SteadyAim(this);
    }
}
// I'm labor ready Rhode Scholar for the dollar
// Work for mines pay me by the hour