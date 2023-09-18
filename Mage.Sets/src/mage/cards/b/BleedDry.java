package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class BleedDry extends CardImpl {

    public BleedDry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Target creature gets -13/-13 until end of turn. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-13, -13, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BleedDry(final BleedDry card) {
        super(card);
    }

    @Override
    public BleedDry copy() {
        return new BleedDry(this);
    }
}
