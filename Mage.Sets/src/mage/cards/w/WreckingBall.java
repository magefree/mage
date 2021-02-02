
package mage.cards.w;

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
 * @author JotaPeRL

 */
public final class WreckingBall extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("creature or land");
    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public WreckingBall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{R}");

        // Destroy target creature or land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    private WreckingBall(final WreckingBall card) {
        super(card);
    }

    @Override
    public WreckingBall copy() {
        return new WreckingBall(this);
    }
}
