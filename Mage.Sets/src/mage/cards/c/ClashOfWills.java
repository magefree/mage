
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class ClashOfWills extends CardImpl {

    public ClashOfWills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");

        // Counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ClashOfWills(final ClashOfWills card) {
        super(card);
    }

    @Override
    public ClashOfWills copy() {
        return new ClashOfWills(this);
    }
}
