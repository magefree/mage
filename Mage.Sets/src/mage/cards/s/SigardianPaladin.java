package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardianPaladin extends CardImpl {

    public SigardianPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as you've put one or more +1/+1 counters on a creature this turn, Sigardian Paladin has trample and lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                SigardianPaladinCondition.instance, "as long as you've put " +
                "one or more +1/+1 counters on a creature this turn, {this} has trample"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        LifelinkAbility.getInstance(), Duration.WhileOnBattlefield
                ), SigardianPaladinCondition.instance, "and lifelink"
        ));
        this.addAbility(ability.addHint(SigardianPaladinCondition.getHint()), new SigardianPaladinWatcher());

        // {1}{G}{W}: Target creature you control with a +1/+1 counter on it gains trample and lifelink until end of turn.
        ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(TrampleAbility.getInstance())
                        .setText("target creature you control with a +1/+1 counter on it gains trample"),
                new ManaCostsImpl<>("{1}{G}{W}")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance()
        ).setText("and lifelink until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1));
        this.addAbility(ability);
    }

    private SigardianPaladin(final SigardianPaladin card) {
        super(card);
    }

    @Override
    public SigardianPaladin copy() {
        return new SigardianPaladin(this);
    }
}

enum SigardianPaladinCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "You've put one or more +1/+1 counters on a creature this turn"
    );

    @Override
    public boolean apply(Game game, Ability source) {
        return SigardianPaladinWatcher.checkPlayer(source.getControllerId(), game);
    }

    public static Hint getHint() {
        return hint;
    }
}

class SigardianPaladinWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    SigardianPaladinWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED
                || playerSet.contains(event.getPlayerId())
                || !event.getData().equals(CounterType.P1P1.getName())) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState()
                .getWatcher(SigardianPaladinWatcher.class)
                .playerSet
                .contains(playerId);
    }
}
