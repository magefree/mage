package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class DeathWind extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(ManacostVariableValue.REGULAR);

    public DeathWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");

        // Target creature gets -X/-X until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeathWind(final DeathWind card) {
        super(card);
    }

    @Override
    public DeathWind copy() {
        return new DeathWind(this);
    }
}
