
package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class Gush extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Islands");
    static{
        filter.add(SubType.ISLAND.getPredicate());
    }
    public Gush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");


        // You may return two Islands you control to their owner's hand rather than pay Gush's mana cost.
        AlternativeCostSourceAbility ability;   
        ability = new AlternativeCostSourceAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, filter, true)));
        this.addAbility(ability);
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private Gush(final Gush card) {
        super(card);
    }

    @Override
    public Gush copy() {
        return new Gush(this);
    }
}
