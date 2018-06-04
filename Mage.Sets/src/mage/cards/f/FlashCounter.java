
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class FlashCounter extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant spell");

    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public FlashCounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target instant spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public FlashCounter(final FlashCounter card) {
        super(card);
    }

    @Override
    public FlashCounter copy() {
        return new FlashCounter(this);
    }
}
