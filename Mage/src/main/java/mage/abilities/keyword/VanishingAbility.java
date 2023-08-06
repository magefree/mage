package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class VanishingAbility extends EntersBattlefieldAbility {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIME);
    private final int amount;

    public VanishingAbility(int amount) {
        super(new AddCountersSourceEffect(CounterType.TIME.createInstance(amount)));
        this.amount = amount;
        this.addSubAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new RemoveCounterSourceEffect(CounterType.TIME.createInstance()),
                        TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, if this permanent " +
                "has a time counter on it, remove a time counter from it."
        ).setRuleVisible(false));
        this.addSubAbility(new VanishingTriggeredAbility());
    }

    private VanishingAbility(final VanishingAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public VanishingAbility copy() {
        return new VanishingAbility(this);
    }

    @Override
    public String getRule() {
        if (amount > 0) {
            return "Vanishing " + amount + " <i>(This permanent enters the battlefield with " +
                    CardUtil.numberToText(amount) + " time counters on it. At the beginning of your upkeep, " +
                    "remove a time counter from it. When the last is removed, sacrifice it.)</i>";
        }
        return "Vanishing <i>(At the beginning of your upkeep, remove a time counter " +
                "from this permanent. When the last is removed, sacrifice it.)</i>";
    }
}

class VanishingTriggeredAbility extends TriggeredAbilityImpl {

    VanishingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.setRuleVisible(false);
    }

    private VanishingTriggeredAbility(final VanishingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VanishingTriggeredAbility copy() {
        return new VanishingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!CounterType.TIME.getName().equals(event.getData())) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getCounters(game).getCount(CounterType.TIME) < 1;
    }

    @Override
    public String getRule() {
        return "When the last time counter is removed from {this}, sacrifice it.";
    }
}
