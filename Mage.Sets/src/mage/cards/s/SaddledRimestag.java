package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
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
import mage.game.permanent.Permanent;
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
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (watcher == null || sourcePermanent == null) {
            return false;
        }
        return watcher.anotherCreatureEntered(source.getControllerId(), new MageObjectReference(sourcePermanent, game));
    }

    @Override
    public String toString() {
        return "you had a creature enter the battlefield under your control this turn";
    }
}

class SaddledRimestagWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap<>();

    SaddledRimestagWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.isPermanentMoved()
                    && zEvent.getTarget().isCreature(game)) {
                playerMap
                        .computeIfAbsent(zEvent.getTarget().getControllerId(), x -> new HashSet<>())
                        .add(new MageObjectReference(zEvent.getTarget(), game));
            }
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
    }

    // a permanent that left and returned is a different object, so an earlier instance of the source counts as another creature
    boolean anotherCreatureEntered(UUID playerId, MageObjectReference sourceRef) {
        return playerMap
                .getOrDefault(playerId, Collections.emptySet())
                .stream()
                .anyMatch(mor -> !mor.equals(sourceRef));
    }
}
