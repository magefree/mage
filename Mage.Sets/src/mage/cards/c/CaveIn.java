
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class CaveIn extends CardImpl {

    public CaveIn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // You may exile a red card from your hand rather than pay Cave-In's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("a red card from your hand");
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.not(new CardIdPredicate(this.getId())));       
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
        
        // Cave-In deals 2 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2));
    }

    private CaveIn(final CaveIn card) {
        super(card);
    }

    @Override
    public CaveIn copy() {
        return new CaveIn(this);
    }
}
