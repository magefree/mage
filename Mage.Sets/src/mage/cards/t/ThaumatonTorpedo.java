package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThaumatonTorpedo extends CardImpl {

    public ThaumatonTorpedo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {6}, {T}, Sacrifice this artifact: Destroy target nonland permanent. This ability costs {3} less to activate if you attacked with a Spacecraft this turn.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {3} less to activate if you attacked with a Spacecraft this turn"));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability
                .setCostAdjuster(ThaumatonTorpedoAdjuster.instance)
                .addHint(ThaumatonTorpedoCondition.getHint()), new ThaumatonTorpedoWatcher());
    }

    private ThaumatonTorpedo(final ThaumatonTorpedo card) {
        super(card);
    }

    @Override
    public ThaumatonTorpedo copy() {
        return new ThaumatonTorpedo(this);
    }
}

enum ThaumatonTorpedoCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return ThaumatonTorpedoWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you attacked with a Spacecraft this turn";
    }
}

enum ThaumatonTorpedoAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        if (ThaumatonTorpedoCondition.instance.apply(game, ability)) {
            CardUtil.reduceCost(ability, 3);
        }
    }
}

class ThaumatonTorpedoWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ThaumatonTorpedoWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null && permanent.hasSubtype(SubType.SPACECRAFT, game)) {
            set.add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ThaumatonTorpedoWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
