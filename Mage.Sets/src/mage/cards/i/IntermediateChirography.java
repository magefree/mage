package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LoseLifeFirstTimeEachTurnTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InklingToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author muz
 */
public final class IntermediateChirography extends CardImpl {

    public IntermediateChirography(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When this Class enters, create a 2/1 white and black Inkling creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new InklingToken())));

        // {1}{B}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{B}"));

        // Whenever you lose life for the first time each turn, put a +1/+1 counter on target creature you control.
        Ability ability = new LoseLifeFirstTimeEachTurnTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {2}{B}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{B}"));

        // At the beginning of each end step, if a modified creature died under your control this turn, create a 2/1 white and black Inkling creature token with flying.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new BeginningOfEndStepTriggeredAbility(TargetController.ANY, new CreateTokenEffect(new InklingToken()), false)
                        .withInterveningIf(IntermediateChirographyCondition.instance)
                        .addHint(IntermediateChirographyCondition.getHint()),
                3
        )), new IntermediateChirographyWatcher());
    }

    private IntermediateChirography(final IntermediateChirography card) {
        super(card);
    }

    @Override
    public IntermediateChirography copy() {
        return new IntermediateChirography(this);
    }
}

enum IntermediateChirographyCondition implements Condition {
    instance;

    private static final Hint hint = new ConditionHint(instance, "A modified creature died under your control this turn");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        IntermediateChirographyWatcher watcher = game.getState().getWatcher(IntermediateChirographyWatcher.class);
        return watcher != null && watcher.modifiedCreatureDied(source.getControllerId());
    }

    @Override
    public String toString() {
        return "a modified creature died under your control this turn";
    }
}

class IntermediateChirographyWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    IntermediateChirographyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (!zce.isDiesEvent()) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = zce.getTarget();
        }
        if (permanent == null || !permanent.isCreature(game) || !ModifiedPredicate.instance.apply(permanent, game)) {
            return;
        }
        players.add(permanent.getControllerId());
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    boolean modifiedCreatureDied(UUID playerId) {
        return players.contains(playerId);
    }
}
