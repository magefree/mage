
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class HoldoutSettlement extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public HoldoutSettlement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public HoldoutSettlement(final HoldoutSettlement card) {
        super(card);
    }

    @Override
    public HoldoutSettlement copy() {
        return new HoldoutSettlement(this);
    }
}
