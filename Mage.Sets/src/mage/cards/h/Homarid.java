package mage.cards.h;

import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Homarid extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIDE, ComparisonType.EQUAL_TO, 1);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.TIDE, ComparisonType.EQUAL_TO, 3);

    public Homarid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HOMARID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Homarid enters the battlefield with a tide counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.TIDE.createInstance()), "with a tide counter on it."
        ));

        // At the beginning of your upkeep, put a tide counter on Homarid.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.TIDE.createInstance())
        ));

        // As long as there is exactly one tide counter on Homarid, it gets -1/-1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(-1, -1, Duration.WhileOnBattlefield),
                condition, "As long as there is exactly one tide counter on {this}, it gets -1/-1.")));

        // As long as there are exactly three tide counters on Homarid, it gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition2, "As long as there are exactly three tide counters on {this}, it gets +1/+1."
        )));

        // Whenever there are four or more tide counters on Homarid, remove all tide counters from it.
        this.addAbility(new HomaridTriggeredAbility());
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

    HomaridTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveAllCountersSourceEffect(CounterType.TIDE).setText("remove all tide counters from it"));
        setTriggerPhrase("Whenever there are four or more tide counters on {this}, ");
    }

    private HomaridTriggeredAbility(final HomaridTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HomaridTriggeredAbility copy() {
        return new HomaridTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return new CountersSourceCount(CounterType.TIDE).calculate(game, this, null) >= 4;
    }
}
