
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author TheElk801
 */
public final class MeteorShower extends CardImpl {

    public MeteorShower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Meteor Shower deals X plus 1 damage divided as you choose among any number of targets.
        DynamicValue xValue = new IntPlusDynamicValue(1, ManacostVariableValue.REGULAR);
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(xValue));
    }

    private MeteorShower(final MeteorShower card) {
        super(card);
    }

    @Override
    public MeteorShower copy() {
        return new MeteorShower(this);
    }
}
