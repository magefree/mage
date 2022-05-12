package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Contagion extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a black card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Contagion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // You may pay 1 life and exile a black card from your hand rather than pay Contagion's mana cost.
        AlternativeCostSourceAbility ability = new AlternativeCostSourceAbility(new PayLifeCost(1));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(filter)));
        this.addAbility(ability);

        // Distribute two -2/-1 counters among one or two target creatures.
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));
        this.getSpellAbility().addEffect(new DistributeCountersEffect(
                CounterType.M2M1, 2, false,
                "one or two target creatures"
        ));
    }

    private Contagion(final Contagion card) {
        super(card);
    }

    @Override
    public Contagion copy() {
        return new Contagion(this);
    }
}
