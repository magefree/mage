
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author chrisasanford
 */
public final class FlowstoneSlide extends CardImpl {

    public FlowstoneSlide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{2}{R}{R}");

        DynamicValue xPos = new ManacostVariableValue();
        DynamicValue xNeg = new SignInversionDynamicValue(new ManacostVariableValue());
        
        // All creatures get +X/-X until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(xPos, xNeg, Duration.EndOfTurn));
    }

    public FlowstoneSlide(final FlowstoneSlide card) {
        super(card);
    }

    @Override
    public FlowstoneSlide copy() {
        return new FlowstoneSlide(this);
    }
}
