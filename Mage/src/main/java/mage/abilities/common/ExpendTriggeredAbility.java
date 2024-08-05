package mage.abilities.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public class ExpendTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint("Mana you expended this turn", ExpendValue.instance);

    /**
     * For memory-usage purpose, we only support expend 4 and expend 8 so far.
     */
    public enum Expend {
        FOUR(4),
        EIGHT(8);

        private final int amount;

        public int getAmount() {
            return amount;
        }

        Expend(int amount) {
            this.amount = amount;
        }
    }

    private final Expend amount;

    public ExpendTriggeredAbility(Effect effect, Expend amount) {
        this(effect, amount, false);
    }

    public ExpendTriggeredAbility(Effect effect, Expend amount, boolean optional) {
        this(Zone.BATTLEFIELD, effect, amount, optional);
    }

    public ExpendTriggeredAbility(Zone zone, Effect effect, Expend amount, boolean optional) {
        super(zone, effect, optional);
        this.amount = amount;
        setTriggerPhrase("Whenever you expend " + amount.getAmount() + ", ");
        this.addWatcher(new ExpendWatcher());
        this.addHint(hint);
    }

    private ExpendTriggeredAbility(final ExpendTriggeredAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public ExpendTriggeredAbility copy() {
        return new ExpendTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ExpendWatcher.checkExpend(getControllerId(), event, amount, game);
    }
}

enum ExpendValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ExpendWatcher.getManaExpended(sourceAbility.getControllerId(), game);
    }

    @Override
    public ExpendValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "mana you expended this turn";
    }
}


/**
 * Watcher for mana expended this turn.
 * <p>
 * Of note, to reduce memory usage, only tracks the events that did expend 4 and expend 8.
 * If expend N extends to more than a couple values, storing the full list (in order) of event id is advised.
 */
class ExpendWatcher extends Watcher {

    // Player id -> number of mana expended this turn
    private final Map<UUID, Integer> manaExpended = new HashMap<>();

    // Player id -> event id for the 4th mana expended this turn
    private final Map<UUID, UUID> eventFor4thExpend = new HashMap<>();
    // Player id -> event id for the 8th mana expended this turn
    private final Map<UUID, UUID> eventFor8thExpend = new HashMap<>();

    public ExpendWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!GameEvent.EventType.MANA_PAID.equals(event.getType())) {
            return;
        }
        MageObject ability = game.getObject(event.getTargetId());
        if (!(ability instanceof Spell)) {
            // Only cares about mana paid for casting Spells
            return;
        }
        UUID playerId = event.getPlayerId();
        UUID eventId = event.getId();
        manaExpended.compute(playerId, CardUtil::setOrIncrementValue);
        int currentValue = manaExpended.get(playerId);
        switch (currentValue) {
            case 4:
                eventFor4thExpend.put(playerId, eventId);
                break;
            case 8:
                eventFor8thExpend.put(playerId, eventId);
                break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        manaExpended.clear();
        eventFor4thExpend.clear();
        eventFor8thExpend.clear();
    }

    public static boolean checkExpend(UUID playerId, GameEvent event, ExpendTriggeredAbility.Expend expendToCheck, Game game) {
        ExpendWatcher watcher = game.getState().getWatcher(ExpendWatcher.class);
        if (watcher == null) {
            return false;
        }
        switch (expendToCheck) {
            case FOUR:
                return watcher.eventFor4thExpend.containsKey(playerId)
                        && watcher.eventFor4thExpend.get(playerId).equals(event.getId());
            case EIGHT:
                return watcher.eventFor8thExpend.containsKey(playerId)
                        && watcher.eventFor8thExpend.get(playerId).equals(event.getId());
            default:
                throw new IllegalArgumentException("Unsupported expend value: " + expendToCheck.getAmount());
        }
    }

    public static int getManaExpended(UUID playerId, Game game) {
        ExpendWatcher watcher = game.getState().getWatcher(ExpendWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.manaExpended.getOrDefault(playerId, 0);
    }
}
