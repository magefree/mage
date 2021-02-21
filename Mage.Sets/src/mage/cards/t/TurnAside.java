

package mage.cards.t;

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
 * @author ayratn
 */
public final class TurnAside extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a permanent you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledPermanent()));
    }

    public TurnAside(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target spell that targets a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private TurnAside(final TurnAside card) {
        super(card);
    }

    @Override
    public TurnAside copy() {
        return new TurnAside(this);
    }
}
