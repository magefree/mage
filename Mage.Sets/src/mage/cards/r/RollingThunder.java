package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author fireshoes
 */
public final class RollingThunder extends CardImpl {

    public RollingThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}");

        // Rolling Thunder deals X damage divided as you choose among any number of targets.
        DynamicValue xValue = ManacostVariableValue.REGULAR;
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(xValue));  
    }

    private RollingThunder(final RollingThunder card) {
        super(card);
    }

    @Override
    public RollingThunder copy() {
        return new RollingThunder(this);
    }
}
