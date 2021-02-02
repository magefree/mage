
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author markedagain
 */
public final class Thwart extends CardImpl {
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Islands");
    static{
        filter.add(SubType.ISLAND.getPredicate());
    }
    public Thwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // You may return three Islands you control to their owner's hand rather than pay Thwart's mana cost.
        AlternativeCostSourceAbility ability;   
        ability = new AlternativeCostSourceAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(3, 3, filter, true)));
        this.addAbility(ability);
        
        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Thwart(final Thwart card) {
        super(card);
    }

    @Override
    public Thwart copy() {
        return new Thwart(this);
    }
}
