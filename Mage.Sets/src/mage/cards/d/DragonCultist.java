package mage.cards.d;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DragonToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonCultist extends CardImpl {

    public DragonCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "At the beginning of your end step, if a source you controlled dealt 5 or more damage this turn, create a 4/4 red Dragon creature token with flying."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new DragonToken()), TargetController.YOU,
                        DragonCultistCondition.instance, false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )), new DragonCultistWatcher());
    }

    private DragonCultist(final DragonCultist card) {
        super(card);
    }

    @Override
    public DragonCultist copy() {
        return new DragonCultist(this);
    }
}

enum DragonCultistCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DragonCultistWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "if a source you controlled dealt 5 or more damage this turn";
    }
}

class DragonCultistWatcher extends Watcher {

    private final Map<UUID, Map<MageObjectReference, Integer>> map = new HashMap<>();

    DragonCultistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event instanceof DamagedEvent) {
            map.computeIfAbsent(game.getControllerId(event.getSourceId()), x -> new HashMap<>())
                    .compute(new MageObjectReference(event.getSourceId(), game), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(DragonCultistWatcher.class)
                .map
                .getOrDefault(playerId, Collections.emptyMap())
                .values()
                .stream()
                .anyMatch(x -> x >= 5);
    }
}
