
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ChillToTheBone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonsnow creature");

    static {
        filter.add(Predicates.not(SuperType.SNOW.getPredicate()));
    }

    public ChillToTheBone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        // Destroy target nonsnow creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private ChillToTheBone(final ChillToTheBone card) {
        super(card);
    }

    @Override
    public ChillToTheBone copy() {
        return new ChillToTheBone(this);
    }
}
