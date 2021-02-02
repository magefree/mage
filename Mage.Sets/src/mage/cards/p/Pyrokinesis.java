
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author Plopman
 */
public final class Pyrokinesis extends CardImpl {

    public Pyrokinesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}{R}");


        // You may exile a red card from your hand rather than pay Pyrokinesis's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("a red card from your hand");
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
        
        // Pyrokinesis deals 4 damage divided as you choose among any number of target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4));
    }

    private Pyrokinesis(final Pyrokinesis card) {
        super(card);
    }

    @Override
    public Pyrokinesis copy() {
        return new Pyrokinesis(this);
    }
}
