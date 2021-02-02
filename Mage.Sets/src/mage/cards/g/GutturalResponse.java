
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class GutturalResponse extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("blue instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public GutturalResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R/G}");


        // Counter target blue instant spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private GutturalResponse(final GutturalResponse card) {
        super(card);
    }

    @Override
    public GutturalResponse copy() {
        return new GutturalResponse(this);
    }
}
