
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author fireshoes
 */
public final class Flameshot extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a Mountain card");
    
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Flameshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // You may discard a Mountain card rather than pay Flameshot's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filter))));
        
        // Flameshot deals 3 damage divided as you choose among one, two, or three target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3).setText("{this} deals 3 damage divided as you choose among one, two, or three target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3));
    }

    private Flameshot(final Flameshot card) {
        super(card);
    }

    @Override
    public Flameshot copy() {
        return new Flameshot(this);
    }
}
