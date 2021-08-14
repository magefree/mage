package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParadoxZone extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.GROWTH);

    public ParadoxZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Paradox Zone enters the battlefield with a growth counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.GROWTH.createInstance()),
                "with a growth counter on it"
        ));

        // At the beginning of your end step, double the number of growth counters on Paradox Zone. Then create a 0/0 blue and green Fractal creature token. Put X +1/+1 counters on it, where X is the number of growth counters on Paradox Zone.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DoubleCountersSourceEffect(CounterType.GROWTH), TargetController.YOU, false
        );
        Effect effect = FractalToken.getEffect(
                xValue, "Put X +1/+1 counters on it, where X is the number of growth counters on {this}"
        );
        ability.addEffect(effect.concatBy("Then"));
        this.addAbility(ability);
    }

    private ParadoxZone(final ParadoxZone card) {
        super(card);
    }

    @Override
    public ParadoxZone copy() {
        return new ParadoxZone(this);
    }
}
