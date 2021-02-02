
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.DealtDamageToAnOpponent;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX
 */
public final class WhirlingDervish extends CardImpl {

    private static final String ruleText = "At the beginning of each end step, if {this} dealt damage to an opponent this turn, put a +1/+1 counter on it.";

    public WhirlingDervish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        // At the beginning of each end step, if Whirling Dervish dealt damage to an opponent this turn, put a +1/+1 counter on it.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each end step", true, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new DealtDamageToAnOpponent(), ruleText));
    }

    private WhirlingDervish(final WhirlingDervish card) {
        super(card);
    }

    @Override
    public WhirlingDervish copy() {
        return new WhirlingDervish(this);
    }
}
