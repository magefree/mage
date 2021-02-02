
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LoneFox
 */
public final class BountyOfTheHunt extends CardImpl {

    public BountyOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}{G}");

        // You may exile a green card from your hand rather than pay Bounty of the Hunt's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("green card from your hand");
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Distribute three +1/+1 counters among one, two, or three target creatures. For each +1/+1 counter you put on a creature this way, remove a +1/+1 counter from that creature at the beginning of the next cleanup step.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.P1P1, 3, true, "one, two, or three target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3));
    }

    private BountyOfTheHunt(final BountyOfTheHunt card) {
        super(card);
    }

    @Override
    public BountyOfTheHunt copy() {
        return new BountyOfTheHunt(this);
    }
}
