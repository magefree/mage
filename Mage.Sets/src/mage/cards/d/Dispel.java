
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class Dispel extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant spell");

    static {
            filter.add(CardType.INSTANT.getPredicate());
    }

    public Dispel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private Dispel(final Dispel card) {
        super(card);
    }

    @Override
    public Dispel copy() {
        return new Dispel(this);
    }
}
