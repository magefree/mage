
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageByAllObjectsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class RepelTheAbominable extends CardImpl {

    private static final FilterObject filter = new FilterObject("non-Human sources");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public RepelTheAbominable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Prevent all damage that would be dealt this turn by non-Human sources.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllObjectsEffect(filter, Duration.EndOfTurn, false));
    }

    private RepelTheAbominable(final RepelTheAbominable card) {
        super(card);
    }

    @Override
    public RepelTheAbominable copy() {
        return new RepelTheAbominable(this);
    }
}
