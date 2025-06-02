
package mage.cards.p;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ChosenColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismaticCircle extends CardImpl {

    private static FilterSource filter = new FilterSource("a source of your choice of the chosen color");

    static {
        filter.add(ChosenColorPredicate.TRUE);
    }

    public PrismaticCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // As Prismatic Circle enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // {1}: The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true, filter),
                new ManaCostsImpl<>("{1}")
        ));
    }

    private PrismaticCircle(final PrismaticCircle card) {
        super(card);
    }

    @Override
    public PrismaticCircle copy() {
        return new PrismaticCircle(this);
    }
}