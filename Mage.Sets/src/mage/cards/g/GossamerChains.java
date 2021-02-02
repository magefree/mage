
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class GossamerChains extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creature");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public GossamerChains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{W}");

        // Return Gossamer Chains to its owner's hand: Prevent all combat damage that would be dealt by target unblocked creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageByTargetEffect(Duration.EndOfTurn, true), new ReturnToHandFromBattlefieldSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GossamerChains(final GossamerChains card) {
        super(card);
    }

    @Override
    public GossamerChains copy() {
        return new GossamerChains(this);
    }
}
