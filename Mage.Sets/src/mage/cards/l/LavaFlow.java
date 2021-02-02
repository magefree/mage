
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author ilcartographer
 */
public final class LavaFlow extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("creature or land");
    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }
    
    public LavaFlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Destroy target creature or land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    private LavaFlow(final LavaFlow card) {
        super(card);
    }

    @Override
    public LavaFlow copy() {
        return new LavaFlow(this);
    }
}
