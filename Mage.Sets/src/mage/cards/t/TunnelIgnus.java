
package mage.cards.t;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * @author Loki
 */
public final class TunnelIgnus extends CardImpl {

    public TunnelIgnus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new TunnelIgnusTriggeredAbility(), new TunnelIgnusWatcher());
    }

    public TunnelIgnus(final TunnelIgnus card) {
        super(card);
    }

    @Override
    public TunnelIgnus copy() {
        return new TunnelIgnus(this);
    }

}

class TunnelIgnusWatcher extends Watcher {

    protected Map<UUID, Integer> counts = new HashMap<>();

    public TunnelIgnusWatcher() {
        super(TunnelIgnusWatcher.class.getSimpleName(), WatcherScope.PLAYER);
    }

    public TunnelIgnusWatcher(final TunnelIgnusWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.counts.entrySet()) {
            counts.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public TunnelIgnusWatcher copy() {
        return new TunnelIgnusWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.isLand() && game.getOpponents(this.controllerId).contains(permanent.getControllerId())) {

                int count = counts.getOrDefault(permanent.getControllerId(), 0);
                counts.put(permanent.getControllerId(), count + 1);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        counts.clear();
    }
}

class TunnelIgnusTriggeredAbility extends TriggeredAbilityImpl {

    TunnelIgnusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3));
    }

    TunnelIgnusTriggeredAbility(final TunnelIgnusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TunnelIgnusTriggeredAbility copy() {
        return new TunnelIgnusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand() && game.getOpponents(this.controllerId).contains(permanent.getControllerId())) {
            TunnelIgnusWatcher watcher = (TunnelIgnusWatcher) game.getState().getWatchers().get(TunnelIgnusWatcher.class.getSimpleName(), this.controllerId);
            if (watcher != null && watcher.counts.get(permanent.getControllerId()) > 1) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield under an opponent's control, if that player had another land enter the battlefield under their control this turn, {this} deals 3 damage to that player.";
    }
}
