package mage.cards.t;

import java.util.UUID;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TethermagesAdvantage extends CardImpl {

    public TethermagesAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +2/+2 and gains reach until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
            2, 2, Duration.EndOfTurn
        ).setText("target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
            ReachAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains reach until end of turn"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TethermagesAdvantage(final TethermagesAdvantage card) {
        super(card);
    }

    @Override
    public TethermagesAdvantage copy() {
        return new TethermagesAdvantage(this);
    }
}
