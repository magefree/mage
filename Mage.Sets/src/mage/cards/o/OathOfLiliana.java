package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.ZombieToken;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OathOfLiliana extends CardImpl {

    public OathOfLiliana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Liliana enters the battlefield, each opponent sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
        ));

        // At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, create a 2/2 black Zombie creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new ZombieToken()),
                false, OathOfLilianaCondition.instance
        ), new OathOfLilianaWatcher());
    }

    private OathOfLiliana(final OathOfLiliana card) {
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
        OathOfLilianaWatcher watcher = game.getState().getWatcher(OathOfLilianaWatcher.class);
        return watcher != null && watcher.enteredPlaneswalkerForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "a planeswalker entered the battlefield under your control this turn";
    }

}

class OathOfLilianaWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public OathOfLilianaWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().isPlaneswalker(game)) {
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
}
