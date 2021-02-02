
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ReachOfShadows extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that's one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public ReachOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}");

        // Destroy target creature that's one or more colors.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private ReachOfShadows(final ReachOfShadows card) {
        super(card);
    }

    @Override
    public ReachOfShadows copy() {
        return new ReachOfShadows(this);
    }
}
