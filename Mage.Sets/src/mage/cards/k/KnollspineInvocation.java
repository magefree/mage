
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
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
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author anonymous
 */
public final class KnollspineInvocation extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card with converted mana cost X");

    public KnollspineInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");

        // {X}, Discard a card with converted mana cost X: Knollspine Invocation deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.instance, true), new ManaCostsImpl<>("{X}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof DiscardTargetCost) {
                    DiscardTargetCost discardCost = (DiscardTargetCost) cost;
                    discardCost.getTargets().clear();
                    FilterCard adjustedFilter = filter.copy(); // don't use it directly, it's static!!!!
                    adjustedFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
                    discardCost.addTarget(new TargetCardInHand(adjustedFilter));
                    return;
                }
            }
        }
    }

    public KnollspineInvocation(final KnollspineInvocation card) {
        super(card);
    }

    @Override
    public KnollspineInvocation copy() {
        return new KnollspineInvocation(this);
    }
}
