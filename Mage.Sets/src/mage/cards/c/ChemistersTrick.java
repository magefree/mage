package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
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
public final class ChemistersTrick extends CardImpl {

    public ChemistersTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Target creature you don't control gets -2/-0 until end of turn and attacks this turn if able.
        // Overload {3}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.ImplementOverloadAbility(this, new ManaCostsImpl<>("{3}{U}{R}"),
                new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL), new BoostTargetEffect(-2, 0, Duration.EndOfTurn),
                new AttacksIfAbleTargetEffect(Duration.EndOfTurn).setText("and attacks this turn if able"));
    }

    private ChemistersTrick(final ChemistersTrick card) {
        super(card);
    }

    @Override
    public ChemistersTrick copy() {
        return new ChemistersTrick(this);
    }
}
