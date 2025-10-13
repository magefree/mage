package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author LevelX2
 */
public final class Downsize extends CardImpl {

    public Downsize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature you don't control gets -4/-0 until end of turn.
        // Overload {2}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.ImplementOverloadAbility(this, new ManaCostsImpl<>("{2}{U}"),
                new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL), new BoostTargetEffect(-4, 0, Duration.EndOfTurn));
    }

    private Downsize(final Downsize card) {
        super(card);
    }

    @Override
    public Downsize copy() {
        return new Downsize(this);
    }
}
