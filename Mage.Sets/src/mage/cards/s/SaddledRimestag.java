package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SaddledRimestag extends CardImpl {

    public SaddledRimestag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Saddled Rimestag gets +2/+2 as long as you had another creature enter the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                SaddledRimestagCondition.instance, "{this} gets +2/+2 as long as " +
                "you had another creature enter the battlefield under your control this turn."
        )), new SaddledRimestagWatcher());
    }

    private SaddledRimestag(final SaddledRimestag card) {
        super(card);
    }

    @Override
    public SaddledRimestag copy() {
        return new SaddledRimestag(this);
    }
}

enum SaddledRimestagCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SaddledRimestagWatcher watcher = game.getState().getWatcher(SaddledRimestagWatcher.class);
        return watcher != null && watcher.enteredCreatureForPlayer(source.getControllerId(), source.getSourceId());
    }

    @Override
    public String toString() {
        return "you had a creature enter the battlefield under your control this turn";
    }
}

class SaddledRimestagWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    SaddledRimestagWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().isCreature(game)) {
                playerMap.putIfAbsent(zEvent.getTarget().getControllerId(), new HashSet<>());
                playerMap.get(zEvent.getTarget().getControllerId()).add(zEvent.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
    }

    boolean enteredCreatureForPlayer(UUID playerId, UUID creatureId) {
        Set<UUID> s = playerMap.getOrDefault(playerId, null);
        return s != null && s.stream().anyMatch((UUID id) -> (id != creatureId));
    }
}
