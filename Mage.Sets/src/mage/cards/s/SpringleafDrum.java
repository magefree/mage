
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class SpringleafDrum extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SpringleafDrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        this.addAbility(ability);
    }

    private SpringleafDrum(final SpringleafDrum card) {
        super(card);
    }

    @Override
    public SpringleafDrum copy() {
        return new SpringleafDrum(this);
    }
}
