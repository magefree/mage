package mage.cards.n;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NightmarishEnd extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(CardsInControllerHandCount.instance);

    public NightmarishEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets -X/-X until end of turn, where X is the number of cards in your hand.
        Effect effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-X until end of turn, where X is the number of cards in your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private NightmarishEnd(final NightmarishEnd card) {
        super(card);
    }

    @Override
    public NightmarishEnd copy() {
        return new NightmarishEnd(this);
    }
}
