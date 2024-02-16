package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Downsize extends CardImpl {

    public Downsize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature you don't control gets -4/-0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0, Duration.EndOfTurn));

        // Overload {2}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new BoostAllEffect(-4, 0, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false), new ManaCostsImpl<>("{2}{U}")));

    }

    private Downsize(final Downsize card) {
        super(card);
    }

    @Override
    public Downsize copy() {
        return new Downsize(this);
    }
}
