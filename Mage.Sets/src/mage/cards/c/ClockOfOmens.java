
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class ClockOfOmens extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ClockOfOmens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Tap two untapped artifacts you control: Untap target artifact.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new UntapTargetEffect(),
                new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private ClockOfOmens(final ClockOfOmens card) {
        super(card);
    }

    @Override
    public ClockOfOmens copy() {
        return new ClockOfOmens(this);
    }
}
