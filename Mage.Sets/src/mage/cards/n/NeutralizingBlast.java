
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class NeutralizingBlast extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("multicolored spell");
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public NeutralizingBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target multicolored spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private NeutralizingBlast(final NeutralizingBlast card) {
        super(card);
    }

    @Override
    public NeutralizingBlast copy() {
        return new NeutralizingBlast(this);
    }
}
