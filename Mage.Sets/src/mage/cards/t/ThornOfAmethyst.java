
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Plopman
 */
public final class ThornOfAmethyst extends CardImpl {

    private static final FilterCard filter = new FilterCard("Noncreature spells");
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }
    public ThornOfAmethyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Noncreature spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasementAllEffect(filter, 1)));
    }

    public ThornOfAmethyst(final ThornOfAmethyst card) {
        super(card);
    }

    @Override
    public ThornOfAmethyst copy() {
        return new ThornOfAmethyst(this);
    }
}
