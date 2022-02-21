package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Snapback extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a blue card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Snapback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // You may exile a blue card from your hand rather than pay Snapback's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Return target creature to its owner's hand.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private Snapback(final Snapback card) {
        super(card);
    }

    @Override
    public Snapback copy() {
        return new Snapback(this);
    }
}
