package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author LevelX2
 */
public final class Blustersquall extends CardImpl {

    public Blustersquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Tap target creature you don't control.

        // Overload {3}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.ImplementOverloadAbility(this, new ManaCostsImpl<>("{3}{U}"),
                new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL), new TapTargetEffect());
    }

    private Blustersquall(final Blustersquall card) {
        super(card);
    }

    @Override
    public Blustersquall copy() {
        return new Blustersquall(this);
    }
}
