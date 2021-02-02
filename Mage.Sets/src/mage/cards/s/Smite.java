
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Smite extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocked creature");

    static {
        filter.add(BlockedPredicate.instance);
    }

    public Smite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Destroy target blocked creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(false));
    }

    private Smite(final Smite card) {
        super(card);
    }

    @Override
    public Smite copy() {
        return new Smite(this);
    }
}
