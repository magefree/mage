
package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.common.DealtDamageToAnOpponent;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class DuneriderOutlaw extends CardImpl {

    public DuneriderOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ROGUE);

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));

        // At the beginning of each end step, if Dunerider Outlaw dealt damage to an opponent this turn, put a +1/+1 counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                false, DealtDamageToAnOpponent.instance
        ));
    }

    private DuneriderOutlaw(final DuneriderOutlaw card) {
        super(card);
    }

    @Override
    public DuneriderOutlaw copy() {
        return new DuneriderOutlaw(this);
    }
}
