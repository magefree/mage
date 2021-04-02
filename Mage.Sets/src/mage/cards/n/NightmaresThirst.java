package mage.cards.n;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightmaresThirst extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ControllerGotLifeCount.instance, -1);

    public NightmaresThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // You gain 1 life. Target creature gets -X/-X until end of turn, where X is the amount of life you gained this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("Target creature gets -X/-X until end of turn, where X is the amount of life you gained this turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
        this.getSpellAbility().addHint(ControllerGotLifeCount.getHint());
    }

    private NightmaresThirst(final NightmaresThirst card) {
        super(card);
    }

    @Override
    public NightmaresThirst copy() {
        return new NightmaresThirst(this);
    }
}
