package mage.cards.w;

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
public final class WitchsWeb extends CardImpl {

    public WitchsWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 and gains reach until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 2, Duration.EndOfTurn
        ).setText("Target creature gets +1/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains reach until end of turn"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WitchsWeb(final WitchsWeb card) {
        super(card);
    }

    @Override
    public WitchsWeb copy() {
        return new WitchsWeb(this);
    }
}
