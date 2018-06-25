
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.ZombieToken;
import mage.watchers.Watcher;

/**
 * @author fireshoes
 */
public final class OathOfLiliana extends CardImpl {

    public OathOfLiliana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        addSuperType(SuperType.LEGENDARY);

        // When Oath of Liliana enters the battlefield, each opponent sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(new FilterControlledCreaturePermanent("a creature")), false));

        // At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, create a 2/2 black Zombie creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()),
                TargetController.ANY, false), OathOfLilianaCondition.instance,
                "At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, "
                        + "create a 2/2 black Zombie creature token."), new OathOfLilianaWatcher());
    }

    public OathOfLiliana(final OathOfLiliana card) {
        super(card);
    }

    @Override
    public OathOfLiliana copy() {
        return new OathOfLiliana(this);
    }
}

enum OathOfLilianaCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        OathOfLilianaWatcher watcher = (OathOfLilianaWatcher) game.getState().getWatchers().get(OathOfLilianaWatcher.class.getSimpleName());
        return watcher != null && watcher.enteredPlaneswalkerForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a planeswalker entered the battlefield under your control this turn";
    }

}

class OathOfLilianaWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public OathOfLilianaWatcher() {
        super(OathOfLilianaWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public OathOfLilianaWatcher(final OathOfLilianaWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().isPlaneswalker()) {
                players.add(zEvent.getTarget().getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        players.clear();
    }

    public boolean enteredPlaneswalkerForPlayer(UUID playerId) {
        return players.contains(playerId);
    }

    @Override
    public OathOfLilianaWatcher copy() {
        return new OathOfLilianaWatcher(this);
    }

}
