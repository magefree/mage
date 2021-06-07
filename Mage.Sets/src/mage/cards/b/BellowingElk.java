package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class BellowingElk extends CardImpl {

    public BellowingElk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // As long as you had another creature enter the battlefield under your control this turn, Bellowing Elk has trample and indestructible.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                BellowingElkCondition.instance, "As long as you had another creature" +
                " enter the battlefield under your control this turn, {this} has trample"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                BellowingElkCondition.instance, "and indestructible"
        ));
        this.addAbility(ability, new BellowingElkWatcher());
    }

    private BellowingElk(final BellowingElk card) {
        super(card);
    }

    @Override
    public BellowingElk copy() {
        return new BellowingElk(this);
    }
}

enum BellowingElkCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        BellowingElkWatcher watcher = game.getState().getWatcher(BellowingElkWatcher.class);
        return watcher != null && watcher.enteredCreatureForPlayer(source.getControllerId(), source.getSourceId());
    }

    @Override
    public String toString() {
        return "you had a creature enter the battlefield under your control this turn";
    }
}

class BellowingElkWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> playerMap = new HashMap<>();

    BellowingElkWatcher() {
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
// I'm not THAT loud...
