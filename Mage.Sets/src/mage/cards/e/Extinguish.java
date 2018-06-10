
package mage.cards.e;

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
 * @author LoneFox
 */
public final class Extinguish extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public Extinguish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public Extinguish(final Extinguish card) {
        super(card);
    }

    @Override
    public Extinguish copy() {
        return new Extinguish(this);
    }
}
