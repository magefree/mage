
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class Intervene extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets a creature");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterCreaturePermanent()));
    }

    public Intervene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Counter target spell that targets a creature.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private Intervene(final Intervene card) {
        super(card);
    }

    @Override
    public Intervene copy() {
        return new Intervene(this);
    }
}
