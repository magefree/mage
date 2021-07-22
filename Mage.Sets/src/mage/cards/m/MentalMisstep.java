
package mage.cards.m;

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
 * @author Loki
 */
public final class MentalMisstep extends CardImpl {

    private static final FilterSpell FILTER = new FilterSpell("spell with mana value 1");

    static {
        FILTER.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
    }

    public MentalMisstep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U/P}");

        // Counter target spell with converted mana cost 1.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(FILTER));
    }

    private MentalMisstep(final MentalMisstep card) {
        super(card);
    }

    @Override
    public MentalMisstep copy() {
        return new MentalMisstep(this);
    }

}
