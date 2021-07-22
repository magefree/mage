
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class DisdainfulStroke extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("spell with mana value 4 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public DisdainfulStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell with converted mana cost 4 or greater.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private DisdainfulStroke(final DisdainfulStroke card) {
        super(card);
    }

    @Override
    public DisdainfulStroke copy() {
        return new DisdainfulStroke(this);
    }
}
