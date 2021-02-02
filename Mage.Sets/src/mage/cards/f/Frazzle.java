
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 * @author Loki
 */
public final class Frazzle extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("nonblue spell");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
    }

    public Frazzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Counter target nonblue spell.
    }

    private Frazzle(final Frazzle card) {
        super(card);
    }

    @Override
    public Frazzle copy() {
        return new Frazzle(this);
    }
}
