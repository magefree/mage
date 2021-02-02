
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Gainsay extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("blue spell");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Gainsay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target blue spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        Target target = new TargetSpell(filter);
        this.getSpellAbility().addTarget(target);
    }

    private Gainsay(final Gainsay card) {
        super(card);
    }

    @Override
    public Gainsay copy() {
        return new Gainsay(this);
    }
}
