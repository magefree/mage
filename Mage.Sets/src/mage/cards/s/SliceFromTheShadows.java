package mage.cards.s;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SliceFromTheShadows extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(ManacostVariableValue.REGULAR);

    public SliceFromTheShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Target creature gets -X/-X until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SliceFromTheShadows(final SliceFromTheShadows card) {
        super(card);
    }

    @Override
    public SliceFromTheShadows copy() {
        return new SliceFromTheShadows(this);
    }
}
