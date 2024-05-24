package mage.cards.c;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrackleWithPower extends CardImpl {

    private static final DynamicValue value = new MultipliedValue(ManacostVariableValue.REGULAR, 5);

    public CrackleWithPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{X}{R}{R}");

        // Crackle with Power deals five times X damage to up to X targets.
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(value).setText("{this} deals five times X damage to each of up to X targets")
        );
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetAnyTarget(0, 1));
    }

    private CrackleWithPower(final CrackleWithPower card) {
        super(card);
    }

    @Override
    public CrackleWithPower copy() {
        return new CrackleWithPower(this);
    }
}
