package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class KnollspineInvocation extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card with mana value X");

    public KnollspineInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // {X}, Discard a card with converted mana cost X: Knollspine Invocation deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.REGULAR, true), new ManaCostsImpl<>("{X}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        ability.addTarget(new TargetAnyTarget());
        ability.setCostAdjuster(KnollspineInvocationAdjuster.instance);
        this.addAbility(ability);
    }

    private KnollspineInvocation(final KnollspineInvocation card) {
        super(card);
    }

    @Override
    public KnollspineInvocation copy() {
        return new KnollspineInvocation(this);
    }
}

enum KnollspineInvocationAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        for (Cost cost : ability.getCosts()) {
            if (!(cost instanceof DiscardTargetCost)) {
                continue;
            }
            DiscardTargetCost discardCost = (DiscardTargetCost) cost;
            discardCost.getTargets().clear();
            FilterCard adjustedFilter = new FilterCard("a card with mana value X");
            adjustedFilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
            discardCost.addTarget(new TargetCardInHand(adjustedFilter));
            return;
        }
    }
}
