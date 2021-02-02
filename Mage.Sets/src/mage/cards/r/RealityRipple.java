
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class RealityRipple extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public RealityRipple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Target artifact, creature, or land phases out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RealityRipple(final RealityRipple card) {
        super(card);
    }

    @Override
    public RealityRipple copy() {
        return new RealityRipple(this);
    }
}
