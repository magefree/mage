
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author andyfries
 */
public final class Sunscour extends CardImpl {

    private static final FilterCard filter = new FilterCard("white cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Sunscour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // You may exile two white cards from your hand rather than pay Sunscour's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private Sunscour(final Sunscour card) {
        super(card);
    }

    @Override
    public Sunscour copy() {
        return new Sunscour(this);
    }
}
