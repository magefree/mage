
package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
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
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author spjspj
 */
public final class FairgroundsTrumpeter extends CardImpl {

    public FairgroundsTrumpeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if a +1/+1 counter was put on a permanent under your control this turn, put a +1/+1 counter on Fairgrounds Trumpeter.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        TargetController.ANY, false), FairgroundsTrumpeterCondition.instance,
                        "At the beginning of each end step, if a +1/+1 counter was put on a permanent under your control this turn, put a +1/+1 counter on {this}."),
                new FairgroundsTrumpeterWatcher());
    }

    private FairgroundsTrumpeter(final FairgroundsTrumpeter card) {
        super(card);
    }

    @Override
    public FairgroundsTrumpeter copy() {
        return new FairgroundsTrumpeter(this);
    }
}

enum FairgroundsTrumpeterCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        FairgroundsTrumpeterWatcher watcher = game.getState().getWatcher(FairgroundsTrumpeterWatcher.class);
        return watcher != null && watcher.p1p1AddedToPermanent(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a +1/+1 counter was put on a permanent under your control this turn";
    }

}

class FairgroundsTrumpeterWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public FairgroundsTrumpeterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED && event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null) {
                players.add(permanent.getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        players.clear();
    }

    public boolean p1p1AddedToPermanent(UUID playerId) {
        return players.contains(playerId);
    }

}
