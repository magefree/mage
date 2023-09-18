package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SarkhansScorn extends CardImpl {

    public SarkhansScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Sarkhan's Scorn deals damage equal to the number of turns you have begun to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(SarkhansScornValue.instance)
                .setText("{this} deals damage equal to the number of turns " +
                        "you have begun to target creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addWatcher(new SarkhansScornWatcher());
    }

    private SarkhansScorn(final SarkhansScorn card) {
        super(card);
    }

    @Override
    public SarkhansScorn copy() {
        return new SarkhansScorn(this);
    }
}

enum SarkhansScornValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return SarkhansScornWatcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public SarkhansScornValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class SarkhansScornWatcher extends Watcher {

    private final Map<UUID, Set<Integer>> playerMap = new HashMap<>();
    private static final Set<Integer> emptySet = Collections.unmodifiableSet(new HashSet<>());

    SarkhansScornWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.BEGINNING_PHASE_PRE) {
            return;
        }
        if (game.getTurnNum() == 1) {
            playerMap.clear();
        }
        playerMap
                .computeIfAbsent(game.getActivePlayerId(), x -> new HashSet<>())
                .add(game.getTurnNum());
    }

    static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(SarkhansScornWatcher.class)
                .playerMap
                .getOrDefault(playerId, emptySet)
                .size();
    }
}
