
package mage.cards.d;

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
public final class DuneriderOutlaw extends CardImpl {

    private static final String ruleText = "At the beginning of each end step, if {this} dealt damage to an opponent this turn, put a +1/+1 counter on it.";

    public DuneriderOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ROGUE);

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
        // At the beginning of each end step, if Dunerider Outlaw dealt damage to an opponent this turn, put a +1/+1 counter on it.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each end step", true, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new DealtDamageToAnOpponent(), ruleText));
    }

    private DuneriderOutlaw(final DuneriderOutlaw card) {
        super(card);
    }

    @Override
    public DuneriderOutlaw copy() {
        return new DuneriderOutlaw(this);
    }
}
