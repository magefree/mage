
package mage.cards.r;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth, MTGFan & L_J
 */
public final class ReversePolarity extends CardImpl {

    public ReversePolarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // You gain X life, where X is twice the damage dealt to you so far this turn by artifacts.
        this.getSpellAbility().addEffect(new GainLifeEffect(new ReversePolarityAmount(), "You gain X life, where X is twice the damage dealt to you so far this turn by artifacts"));
        this.getSpellAbility().addWatcher(new ReversePolarityWatcher());
    }

    public ReversePolarity(final ReversePolarity card) {
        super(card);
    }

    @Override
    public ReversePolarity copy() {
        return new ReversePolarity(this);
    }
}

class ReversePolarityAmount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        ReversePolarityWatcher watcher = game.getState().getWatcher(ReversePolarityWatcher.class);
        if(watcher != null) {
            return watcher.getArtifactDamageReceivedThisTurn(source.getControllerId()) * 2;
        }
        return 0;
    }

    @Override
    public ReversePolarityAmount copy() {
        return new ReversePolarityAmount();
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class ReversePolarityWatcher extends Watcher {

    private final Map<UUID, Integer> artifactDamageReceivedThisTurn = new HashMap<>();

    public ReversePolarityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getTargetId();
            if (playerId != null) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.isArtifact()) {
                    artifactDamageReceivedThisTurn.putIfAbsent(playerId, 0);
                    artifactDamageReceivedThisTurn.compute(playerId, (k, v) -> v + event.getAmount());
                }
            }
        }
    }

    public int getArtifactDamageReceivedThisTurn(UUID playerId) {
        return artifactDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        artifactDamageReceivedThisTurn.clear();
    }

}
