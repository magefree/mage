
package mage.cards.o;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author FenrisulfrX
 */
public final class Overrule extends CardImpl {

    public Overrule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}{U}");

        // Counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetSpell());

        // You gain X life.
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR));
    }

    private Overrule(final Overrule card) {
        super(card);
    }

    @Override
    public Overrule copy() {
        return new Overrule(this);
    }
}
