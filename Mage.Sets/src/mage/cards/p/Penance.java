
package mage.cards.p;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCardFromHandOnTopOfLibraryCost;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSource;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Penance extends CardImpl {

    private static final FilterSource filter = new FilterSource("black or red source");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public Penance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Put a card from your hand on top of your library: The next time a black or red source of your choice would deal damage this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, false, filter),
                new PutCardFromHandOnTopOfLibraryCost()
        ));
    }

    private Penance(final Penance card) {
        super(card);
    }

    @Override
    public Penance copy() {
        return new Penance(this);
    }
}