package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReapersScythe extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.SOUL);

    public ReapersScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // At the beginning of your end step, put a soul counter on this Equipment for each player who lost life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(
                CounterType.SOUL.createInstance(), ReapersScytheValue.instance
        )).addHint(ReapersScytheValue.getHint()), new ReapersScytheWatcher());

        // Equipped creature gets +1/+1 for each soul counter on this Equipment and is an Assassin in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.ASSASSIN, AttachmentType.EQUIPMENT
        ).setText("and is an Assassin in addition to its other types"));
        this.addAbility(ability);

        // Death Sickle -- Equip {2}
        this.addAbility(new EquipAbility(2).withFlavorWord("Death Sickle"));
    }

    private ReapersScythe(final ReapersScythe card) {
        super(card);
    }

    @Override
    public ReapersScythe copy() {
        return new ReapersScythe(this);
    }
}

enum ReapersScytheValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Players who lost life this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ReapersScytheWatcher.getCount(game, sourceAbility);
    }

    @Override
    public ReapersScytheValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "player who lost life this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class ReapersScytheWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ReapersScytheWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE) {
            set.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static int getCount(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ReapersScytheWatcher.class)
                .set
                .stream()
                .filter(game.getPlayer(source.getControllerId())::hasPlayerInRange)
                .mapToInt(x -> 1)
                .sum();
    }
}
