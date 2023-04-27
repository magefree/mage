
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public final class Homarid extends CardImpl {

    public Homarid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HOMARID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Homarid enters the battlefield with a tide counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
            "with a tide counter on it."));
        // At the beginning of your upkeep, put a tide counter on Homarid.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
            TargetController.YOU, false));
        // As long as there is exactly one tide counter on Homarid, it gets -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(-1, -1, Duration.WhileOnBattlefield), new SourceHasCounterCondition(CounterType.TIDE, 1, 1),
            "As long as there is exactly one tide counter on {this}, it gets -1/-1.")));
        // As long as there are exactly three tide counters on Homarid, it gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), new SourceHasCounterCondition(CounterType.TIDE, 3, 3),
            "As long as there are exactly three tide counter on {this}, it gets +1/+1.")));
        // Whenever there are four tide counters on Homarid, remove all tide counters from it.
        this.addAbility(new HomaridTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.TIDE)));
    }

    private Homarid(final Homarid card) {
        super(card);
    }

    @Override
    public Homarid copy() {
        return new Homarid(this);
    }
}

class HomaridTriggeredAbility extends StateTriggeredAbility {

    public HomaridTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever there are four tide counters on {this}, ");
    }

    public HomaridTriggeredAbility(final HomaridTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HomaridTriggeredAbility copy() {
        return new HomaridTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return new CountersSourceCount(CounterType.TIDE).calculate(game, this, null) == 4;
    }
}
