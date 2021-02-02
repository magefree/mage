
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Purge extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature or black creature");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
            (new ColorPredicate(ObjectColor.BLACK))));
    }

    public Purge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Destroy target artifact creature or black creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
    }

    private Purge(final Purge card) {
        super(card);
    }

    @Override
    public Purge copy() {
        return new Purge(this);
    }
}
