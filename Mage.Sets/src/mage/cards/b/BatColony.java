package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaPaidEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.BatToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.Copyable;
import mage.watchers.Watcher;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class BatColony extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.CAVE, "a Cave");

    private static final Hint hint = new ValueHint("Mana spent from a Cave", BatColonyValue.instance);

    public BatColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Bat Colony enters the battlefield, create a 1/1 black Bat creature token with flying for each mana from a Cave spent to cast it.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new CreateTokenEffect(new BatToken(), BatColonyValue.instance)
                ).addHint(hint),
                new BatColonyWatcher()
        );

        // Whenever a Cave enters the battlefield under your control, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BatColony(final BatColony card) {
        super(card);
    }

    @Override
    public BatColony copy() {
        return new BatColony(this);
    }
}

enum BatColonyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility == null ? 0 : BatColonyWatcher.getCaveAmount(sourceAbility.getSourceId(), game);
    }

    @Override
    public BatColonyValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "mana from a Cave spent to cast it";
    }

}


/**
 * Inspired by {@link mage.watchers.common.ManaPaidSourceWatcher}
 * If more cards like Bat Colony care for mana spent by Caves in the future, best to refactor the tracking there.
 * For now the assumption is that it is a 1of, so don't want to track it in any game.
 */
class BatColonyWatcher extends Watcher {
    private static final class CaveManaPaidTracker implements Serializable, Copyable<CaveManaPaidTracker> {
        private int caveMana = 0;

        private CaveManaPaidTracker() {
            super();
        }

        private CaveManaPaidTracker(final CaveManaPaidTracker tracker) {
            this.caveMana = tracker.caveMana;
        }

        @Override
        public CaveManaPaidTracker copy() {
            return new CaveManaPaidTracker(this);
        }

        private void increment(MageObject sourceObject, Game game) {
            if (sourceObject != null && sourceObject.hasSubtype(SubType.CAVE, game)) {
                caveMana++;
            }
        }
    }

    private static final CaveManaPaidTracker emptyTracker = new CaveManaPaidTracker();
    private final Map<UUID, CaveManaPaidTracker> manaMap = new HashMap<>();

    public BatColonyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                        // Bug #9943 Memory Deluge cast from graveyard during the same turn
                        || ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
                    manaMap.remove(event.getTargetId());
                }
                return;
            case MANA_PAID:
                ManaPaidEvent manaEvent = (ManaPaidEvent) event;
                manaMap.computeIfAbsent(manaEvent.getTargetId(), x -> new CaveManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), game);
                manaMap.computeIfAbsent(manaEvent.getSourcePaidId(), x -> new CaveManaPaidTracker())
                        .increment(manaEvent.getSourceObject(), game);
        }
    }

    @Override
    public void reset() {
        super.reset();
        manaMap.clear();
    }

    public static int getCaveAmount(UUID sourceId, Game game) {
        BatColonyWatcher watcher = game.getState().getWatcher(BatColonyWatcher.class);
        return watcher == null ? 0 : watcher.manaMap.getOrDefault(sourceId, emptyTracker).caveMana;
    }
}
