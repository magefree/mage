package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ForceOfWill extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a blue card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ForceOfWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // You may pay 1 life and exile a blue card from your hand rather than pay Force of Will's mana cost.
        AlternativeCostSourceAbility ability = new AlternativeCostSourceAbility(new PayLifeCost(1));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(filter)));
        this.addAbility(ability);

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ForceOfWill(final ForceOfWill card) {
        super(card);
    }

    @Override
    public ForceOfWill copy() {
        return new ForceOfWill(this);
    }
}
