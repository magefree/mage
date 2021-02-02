
package mage.cards.t;

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
 * @author North
 */
public final class Terror extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        FILTER.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        FILTER.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Terror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(FILTER));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
    }

    private Terror(final Terror card) {
        super(card);
    }

    @Override
    public Terror copy() {
        return new Terror(this);
    }
}
