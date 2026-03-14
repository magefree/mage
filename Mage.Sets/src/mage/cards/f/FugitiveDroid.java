package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetSpell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FugitiveDroid extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("target spell that targets an artifact or creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    public FugitiveDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature can't be blocked if an artifact entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), FugitiveDroidCondition.instance,
                "{this} can't be blocked if an artifact entered the battlefield under your control this turn"
        )).addHint(FugitiveDroidCondition.getHint()), new FugitiveDroidWatcher());

        // {U}, Sacrifice this creature: Counter target spell that targets an artifact or creature you control.
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private FugitiveDroid(final FugitiveDroid card) {
        super(card);
    }

    @Override
    public FugitiveDroid copy() {
        return new FugitiveDroid(this);
    }
}

enum FugitiveDroidCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return FugitiveDroidWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "an artifact entered the battlefield under your control this turn";
    }
}

class FugitiveDroidWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    FugitiveDroidWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null && permanent.isArtifact(game)) {
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
                .getWatcher(FugitiveDroidWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
