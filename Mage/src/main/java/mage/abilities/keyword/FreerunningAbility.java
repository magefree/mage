package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class FreerunningAbility extends AlternativeSourceCostsImpl {

    private static final String FREERUNNING_KEYWORD = "Freerunning";
    private static final String FREERUNNING_REMINDER = "You may cast this spell for its freerunning cost " +
            "if you dealt combat damage to a player this turn with an Assassin or commander";

    public FreerunningAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public FreerunningAbility(Cost cost) {
        super(FREERUNNING_KEYWORD, FREERUNNING_REMINDER, cost);
        this.setRuleAtTheTop(true);
        this.addWatcher(new FreerunningWatcher());
        this.addHint(FreerunningCondition.getHint());
    }

    private FreerunningAbility(final FreerunningAbility ability) {
        super(ability);
    }

    @Override
    public FreerunningAbility copy() {
        return new FreerunningAbility(this);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return FreerunningCondition.instance.apply(game, source);
    }

    public static String getActivationKey() {
        return getActivationKey(FREERUNNING_KEYWORD);
    }
}

enum FreerunningCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "Freerunning can be used");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return FreerunningWatcher.checkPlayer(source.getControllerId(), game);
    }
}

class FreerunningWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    FreerunningWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedEvent) event).isCombatDamage()) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null
                && (permanent.hasSubtype(SubType.ASSASSIN, game)
                || CommanderPredicate.instance.apply(permanent, game))) {
            players.add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        players.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(FreerunningWatcher.class)
                .players
                .contains(playerId);
    }
}
