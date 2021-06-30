package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class PackTacticsAbility extends TriggeredAbilityImpl {

    public PackTacticsAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.addWatcher(new PackTacticsAbilityWatcher());
    }

    private PackTacticsAbility(final PackTacticsAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return PackTacticsAbilityWatcher.checkPlayer(getControllerId(), game);
    }

    @Override
    public PackTacticsAbility copy() {
        return new PackTacticsAbility(this);
    }

    @Override
    public String getRule() {
        return AbilityWord.PACK_TACTICS.formatWord() +
                "Whenever {this} attacks, if you attacked with creatures " +
                "with total power 6 or greater this combat, " + super.getRule();
    }
}

class PackTacticsAbilityWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    PackTacticsAbilityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGIN_COMBAT_STEP_PRE:
                playerMap.clear();
                return;
            case ATTACKER_DECLARED:
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent == null) {
                    return;
                }
                playerMap.compute(
                        permanent.getControllerId(),
                        (u, i) -> i == null ?
                                permanent.getPower().getValue() :
                                Integer.sum(permanent.getPower().getValue(), i)
                );
        }
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        PackTacticsAbilityWatcher watcher = game.getState().getWatcher(PackTacticsAbilityWatcher.class);
        return watcher != null && watcher.playerMap.getOrDefault(playerId, 0) >= 6;
    }
}
