
package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.other.PlayerWithTheMostLifePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * Dethrone triggers whenever a creature with dethrone attacks the player with
 * the most life or tied for the most life. When the ability resolves, you put a
 * +1/+1 counter on the creature. This happens before blockers are declared.
 * Once the ability triggers, it doesn't matter what happens to anybody's life
 * total. If the defending player doesn't have the most life when the ability
 * resolves, the creature will still get the +1/+1 counter. Note that dethrone
 * won't trigger if the creature attacks a Planeswalker. You're going after the
 * crown, after all, not the royal advisors. If you have the most life, your
 * dethrone abilities won't trigger, but you may find a few choice ways to avoid
 * that situation.
 *
 * @author LevelX2
 */
public class DethroneAbility extends TriggeredAbilityImpl {

    public DethroneAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    protected DethroneAbility(final DethroneAbility ability) {
        super(ability);
    }

    @Override
    public DethroneAbility copy() {
        return new DethroneAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId == null) {
            return false;
        }

        Player attackedPlayer = game.getPlayer(defenderId);
        return PlayerWithTheMostLifePredicate.instance.apply(
                new ObjectSourcePlayer<>(attackedPlayer, getControllerId(), null),
                game
        );
    }

    @Override
    public String getRule() {
        return "Dethrone (<i>Whenever this creature attacks the player with the most life or tied for most life, put a +1/+1 counter on it.</i>)";
    }
}
