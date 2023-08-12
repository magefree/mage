
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class Ensnare extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public Ensnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // You may return two Islands you control to their owner's hand rather than pay Ensnare's mana cost.
        AlternativeCostSourceAbility ability;
        ability = new AlternativeCostSourceAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, filter, true)));
        this.addAbility(ability);

        // Tap all creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private Ensnare(final Ensnare card) {
        super(card);
    }

    @Override
    public Ensnare copy() {
        return new Ensnare(this);
    }
}
