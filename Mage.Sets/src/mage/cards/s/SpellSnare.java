
package mage.cards.s;

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
public final class SpellSnare extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 2");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 2));
    }

    public SpellSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target spell with converted mana cost 2.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private SpellSnare(final SpellSnare card) {
        super(card);
    }

    @Override
    public SpellSnare copy() {
        return new SpellSnare(this);
    }
}
