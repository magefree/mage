
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class Snapback extends CardImpl {

    public Snapback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // You may exile a blue card from your hand rather than pay Snapback's mana cost.
        FilterOwnedCard filterCardInHand = new FilterOwnedCard("a blue card from your hand");
        filterCardInHand.add(new ColorPredicate(ObjectColor.BLUE));
        filterCardInHand.add(Predicates.not(new CardIdPredicate(this.getId())));       
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filterCardInHand))));
        
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
