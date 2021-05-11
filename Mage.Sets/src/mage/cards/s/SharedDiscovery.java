
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SharedDiscovery extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SharedDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // As an additional cost to cast Shared Discovery, tap four untapped creatures you control.
        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledCreaturePermanent(4, 4, filter, true)));
    }

    private SharedDiscovery(final SharedDiscovery card) {
        super(card);
    }

    @Override
    public SharedDiscovery copy() {
        return new SharedDiscovery(this);
    }
}
