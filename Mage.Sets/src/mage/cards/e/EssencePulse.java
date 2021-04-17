package mage.cards.e;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssencePulse extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ControllerGotLifeCount.instance, -1);

    public EssencePulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // You gain 2 life. Each creature gets -X/-X until end of turn, where X is the amount of life you gained this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("Each creature gets -X/-X until end of turn, where X is the amount of life you gained this turn"));
        this.getSpellAbility().addHint(ControllerGotLifeCount.getHint());
    }

    private EssencePulse(final EssencePulse card) {
        super(card);
    }

    @Override
    public EssencePulse copy() {
        return new EssencePulse(this);
    }
}
