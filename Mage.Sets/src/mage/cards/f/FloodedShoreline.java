
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FloodedShoreline extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public FloodedShoreline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");

        // {U}{U}, Return two Islands you control to their owner's hand: Return target creature to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2,2, filter, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FloodedShoreline(final FloodedShoreline card) {
        super(card);
    }

    @Override
    public FloodedShoreline copy() {
        return new FloodedShoreline(this);
    }
}
