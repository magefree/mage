
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jonubuu
 */
public final class Misdirection extends CardImpl {

    private static final FilterSpell filter2 = new FilterSpell("spell with a single target");
    static {
        filter2.add(new NumberOfTargetsPredicate(1));
    }

    public Misdirection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");

        // You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        FilterOwnedCard filterCardInHand = new FilterOwnedCard("a blue card from your hand");
        filterCardInHand.add(new ColorPredicate(ObjectColor.BLUE));
        
        // the exile cost can never be paid with the card itself
        filterCardInHand.add(Predicates.not(new CardIdPredicate(this.getId())));       
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filterCardInHand))));

        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter2));
    }

    private Misdirection(final Misdirection card) {
        super(card);
    }

    @Override
    public Misdirection copy() {
        return new Misdirection(this);
    }
}

