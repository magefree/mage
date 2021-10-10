package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodlineCulling extends CardImpl {

    public BloodlineCulling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Choose one —
        // • Target creature gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Creature tokens get -2/-2 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_TOKENS, false
        )));
    }

    private BloodlineCulling(final BloodlineCulling card) {
        super(card);
    }

    @Override
    public BloodlineCulling copy() {
        return new BloodlineCulling(this);
    }
}
