
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class Pulverize extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains");
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Pulverize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // You may sacrifice two Mountains rather than pay Pulverize's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));
        
        // Destroy all artifacts.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS));
    }

    private Pulverize(final Pulverize card) {
        super(card);
    }

    @Override
    public Pulverize copy() {
        return new Pulverize(this);
    }
}
