
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class StormFleetAerialist extends CardImpl {

    public StormFleetAerialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Raid - Storm Fleet Aerialist enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1), false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.",
                "{this} enters the battlefield with a +1/+1 counter"),
                new PlayerAttackedWatcher());
    }

    public StormFleetAerialist(final StormFleetAerialist card) {
        super(card);
    }

    @Override
    public StormFleetAerialist copy() {
        return new StormFleetAerialist(this);
    }
}
