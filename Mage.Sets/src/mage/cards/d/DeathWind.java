
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

    public DeathWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");


        // Target creature gets -X/-X until end of turn.
        DynamicValue x = new SignInversionDynamicValue(ManacostVariableValue.REGULAR);
        this.getSpellAbility().addEffect(new BoostTargetEffect(x, x, Duration.EndOfTurn, true));
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
