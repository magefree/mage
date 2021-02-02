
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class OutOfBounds extends CardImpl {

    public OutOfBounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Assist
        this.addAbility(new AssistAbility());

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private OutOfBounds(final OutOfBounds card) {
        super(card);
    }

    @Override
    public OutOfBounds copy() {
        return new OutOfBounds(this);
    }
}
