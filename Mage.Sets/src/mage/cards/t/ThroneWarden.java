
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class ThroneWarden extends CardImpl {

    public ThroneWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if you're the monarch, put a +1/+1 counter on Throne Warden.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, MonarchIsSourceControllerCondition.instance, false)
                .addHint(MonarchHint.instance));
    }

    private ThroneWarden(final ThroneWarden card) {
        super(card);
    }

    @Override
    public ThroneWarden copy() {
        return new ThroneWarden(this);
    }
}
