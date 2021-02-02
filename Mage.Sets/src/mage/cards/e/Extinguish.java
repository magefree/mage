
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class Extinguish extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public Extinguish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private Extinguish(final Extinguish card) {
        super(card);
    }

    @Override
    public Extinguish copy() {
        return new Extinguish(this);
    }
}
