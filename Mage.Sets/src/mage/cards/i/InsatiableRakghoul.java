
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author Styxo
 */
public final class InsatiableRakghoul extends CardImpl {

    public InsatiableRakghoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Insatiable Rakghoul enters the battlefield with a +1/+1 counter on it, if a non-artifact creature died this turn.
        this.addAbility(new EntersBattlefieldAbility(new InsatiableRakghoulEffect(), false), new NonArtifactCreaturesDiedWatcher());
    }

    private InsatiableRakghoul(final InsatiableRakghoul card) {
        super(card);
    }

    @Override
    public InsatiableRakghoul copy() {
        return new InsatiableRakghoul(this);
    }
}

class InsatiableRakghoulEffect extends OneShotEffect {

    InsatiableRakghoulEffect() {
        super(Outcome.BoostCreature);
        staticText = "with a +1/+1 counter on it if a non-artifact creature died this turn";
    }

    private InsatiableRakghoulEffect(final InsatiableRakghoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            NonArtifactCreaturesDiedWatcher watcher = game.getState().getWatcher(NonArtifactCreaturesDiedWatcher.class);
            if (watcher != null && watcher.conditionMet()) {
                Permanent permanent = game.getPermanentEntering(source.getSourceId());
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public InsatiableRakghoulEffect copy() {
        return new InsatiableRakghoulEffect(this);
    }
}

class NonArtifactCreaturesDiedWatcher extends Watcher {

    public NonArtifactCreaturesDiedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent() && zEvent.getTarget() != null
                    && zEvent.getTarget().isCreature(game)
                    && !zEvent.getTarget().isArtifact(game)) {
                condition = true;
            }
        }
    }

}
