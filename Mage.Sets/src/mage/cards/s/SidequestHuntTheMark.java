package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SidequestHuntTheMark extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.TREASURE), ComparisonType.MORE_THAN, 2
    );
    private static final Hint hint = new ValueHint(
            "Treasures you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.TREASURE))
    );

    public SidequestHuntTheMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.secondSideCardClazz = mage.cards.y.YiazmatUltimateMark.class;

        // When this enchantment enters, destroy up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // At the beginning of your end step, if a creature died under an opponent's control this turn, create a Treasure token. Then if you control three or more Treasures, transform this enchantment.
        this.addAbility(new TransformAbility());
        ability = new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new TreasureToken()))
                .withInterveningIf(SidequestHuntTheMarkCondition.instance);
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if you control three or more Treasures, transform {this}"
        ));
        this.addAbility(ability.addHint(hint).addHint(SidequestHuntTheMarkCondition.getHint()), new SidequestHuntTheMarkWatcher());
    }

    private SidequestHuntTheMark(final SidequestHuntTheMark card) {
        super(card);
    }

    @Override
    public SidequestHuntTheMark copy() {
        return new SidequestHuntTheMark(this);
    }
}

enum SidequestHuntTheMarkCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SidequestHuntTheMarkWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "a creature died under an opponent's control this turn";
    }
}

class SidequestHuntTheMarkWatcher extends Watcher {

    private static final Set<UUID> set = new HashSet<>();

    SidequestHuntTheMarkWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.isDiesEvent() && zEvent.getTarget().isCreature(game)) {
            set.addAll(game.getOpponents(zEvent.getTarget().getControllerId()));
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
                .getWatcher(SidequestHuntTheMarkWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
