
package mage.cards.u;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class Unmask extends CardImpl {

    private static final FilterCard filter = new FilterCard("a black card from your hand");
    private static final FilterCard filterNonLand = new FilterCard("nonland card");

    static {
        filterNonLand.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public Unmask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // You may exile a black card from your hand rather than pay Unmask's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));     
        
        // Target player reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filterNonLand, TargetController.ANY));
    }

    public Unmask(final Unmask card) {
        super(card);
    }

    @Override
    public Unmask copy() {
        return new Unmask(this);
    }
}
