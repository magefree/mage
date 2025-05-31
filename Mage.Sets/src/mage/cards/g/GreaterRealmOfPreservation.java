
package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
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
 * @author L_J
 */
public final class GreaterRealmOfPreservation extends CardImpl {

    private static final FilterSource filter = new FilterSource("black or red source");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public GreaterRealmOfPreservation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // {1}{W}: The next time a black or red source of your choice would deal damage to you this turn, prevent that damage.
        Effect effect = new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true, filter);
        this.addAbility(new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}{W}")));
    }

    private GreaterRealmOfPreservation(final GreaterRealmOfPreservation card) {
        super(card);
    }

    @Override
    public GreaterRealmOfPreservation copy() {
        return new GreaterRealmOfPreservation(this);
    }
}
