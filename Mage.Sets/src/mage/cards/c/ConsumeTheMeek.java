
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author anonymous
 */
public final class ConsumeTheMeek extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with mana value 3 or less");

    static {
    filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ConsumeTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{B}");


        // Destroy each creature with converted mana cost 3 or less. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }

    private ConsumeTheMeek(final ConsumeTheMeek card) {
        super(card);
    }

    @Override
    public ConsumeTheMeek copy() {
        return new ConsumeTheMeek(this);
    }
}
