
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class RebuffTheWicked extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a permanent you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledPermanent()));
    }

    public RebuffTheWicked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Counter target spell that targets a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private RebuffTheWicked(final RebuffTheWicked card) {
        super(card);
    }

    @Override
    public RebuffTheWicked copy() {
        return new RebuffTheWicked(this);
    }
}
