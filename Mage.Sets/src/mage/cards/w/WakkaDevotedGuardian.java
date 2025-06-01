package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WakkaDevotedGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact that player controls");

    static {
        filter.add(WakkaDevotedGuardianPredicate.instance);
    }

    public WakkaDevotedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Wakka deals combat damage to a player, destroy up to one target artifact that player controls and put a +1/+1 counter on Wakka.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Blitzball Captain -- At the beginning of your end step, if a counter was put on Wakka this turn, put a +1/+1 counter on each other creature you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE
        )).withInterveningIf(WakkaDevotedGuardianCondition.instance).withFlavorWord("Blitzball Captain"), new WakkaDevotedGuardianWatcher());
    }

    private WakkaDevotedGuardian(final WakkaDevotedGuardian card) {
        super(card);
    }

    @Override
    public WakkaDevotedGuardian copy() {
        return new WakkaDevotedGuardian(this);
    }
}

enum WakkaDevotedGuardianPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil
                .getEffectValueFromAbility(
                        input.getSource(), "damagedPlayer", UUID.class
                )
                .map(input.getObject()::isControlledBy)
                .orElse(false);
    }
}

enum WakkaDevotedGuardianCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String toString() {
        return "a counter was put on {this} this turn";
    }
}

class WakkaDevotedGuardianWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    WakkaDevotedGuardianWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            set.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPermanent(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(WakkaDevotedGuardianWatcher.class)
                .set
                .contains(new MageObjectReference(source.getSourceId(), game));
    }
}
