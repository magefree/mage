package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IchorShade extends CardImpl {

    public IchorShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if an artifact or creature was put into a graveyard from the battlefield this turn, put a +1/+1 counter on Ichor Shade.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, IchorShadeCondition.instance, false
        ), new IchorShadeWatcher());
    }

    private IchorShade(final IchorShade card) {
        super(card);
    }

    @Override
    public IchorShade copy() {
        return new IchorShade(this);
    }
}

enum IchorShadeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getWatcher(IchorShadeWatcher.class).conditionMet();
    }

    @Override
    public String toString() {
        return "an artifact or creature was put into a graveyard from the battlefield this turn";
    }
}

class IchorShadeWatcher extends Watcher {

    IchorShadeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null && (permanent.isArtifact(game) || permanent.isCreature(game))) {
            condition = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
