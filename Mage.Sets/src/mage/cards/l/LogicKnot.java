
package mage.cards.l;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class LogicKnot extends CardImpl {

    public LogicKnot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{U}");


        // Delve
        this.addAbility(new DelveAbility());

        // Counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private LogicKnot(final LogicKnot card) {
        super(card);
    }

    @Override
    public LogicKnot copy() {
        return new LogicKnot(this);
    }
}
