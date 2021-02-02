
package mage.cards.f;

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
 * @author Jgod
 */
public final class Fissure extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("creature or land");
    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }
    
    public Fissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Destroy target creature or land. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    private Fissure(final Fissure card) {
        super(card);
    }

    @Override
    public Fissure copy() {
        return new Fissure(this);
    }
}

