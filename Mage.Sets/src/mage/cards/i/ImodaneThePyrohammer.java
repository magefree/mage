package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValuePositiveHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.game.Game;
import mage.game.events.DamagedBatchForPermanentsEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ImodaneThePyrohammer extends CardImpl {

    public ImodaneThePyrohammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever an instant or sorcery spell you control that targets only a single creature deals damage to that creature, Imodane deals that much damage to each opponent.
        this.addAbility(new ImodaneThePyrohammerTriggeredAbility());
    }

    private ImodaneThePyrohammer(final ImodaneThePyrohammer card) {
        super(card);
    }

    @Override
    public ImodaneThePyrohammer copy() {
        return new ImodaneThePyrohammer(this);
    }
}

class ImodaneThePyrohammerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control that targets only a single creature");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private static final Hint hint = new ValuePositiveHint("Damage dealt to the target", ImodaneThePyrohammerDynamicValue.instance);

    ImodaneThePyrohammerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(Outcome.Damage, ImodaneThePyrohammerDynamicValue.instance, TargetController.OPPONENT)
                .setText("{this} deals that much damage to each opponent"), false);
        setTriggerPhrase("Whenever an instant or sorcery spell you control that targets only a single creature deals damage to that creature, ");
        addHint(hint);
    }

    private ImodaneThePyrohammerTriggeredAbility(final ImodaneThePyrohammerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImodaneThePyrohammerTriggeredAbility copy() {
        return new ImodaneThePyrohammerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PERMANENTS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchForPermanentsEvent dEvent = (DamagedBatchForPermanentsEvent) event;
        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> {
                    MageObject sourceObject = game.getObject(damagedEvent.getSourceId());
                    Permanent target = game.getPermanentOrLKIBattlefield(damagedEvent.getTargetId());
                    // We keep only the events
                    // 1/ That have sourceId matching the spell filter
                    // 2/ That have targetId as the spell's only target
                    return sourceObject != null && target != null
                            && sourceObject instanceof StackObject
                            && filter.match((StackObject) sourceObject, controllerId, this, game)
                            && target.getId().equals(((StackObject) sourceObject).getStackAbility().getFirstTarget());
                })
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }

        this.getEffects().setValue(ImodaneThePyrohammerDynamicValue.IMODANE_VALUE_KEY, damage);
        return true;
    }
}

enum ImodaneThePyrohammerDynamicValue implements DynamicValue {
    instance;

    static final String IMODANE_VALUE_KEY = "Imodane-Damage-Amount";

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        StackObject source = game.getStack().getStackObject(sourceAbility.getSourceId());
        if (source == null) {
            return 0;
        }

        Integer value = (Integer) sourceAbility.getEffects().get(0).getValue(IMODANE_VALUE_KEY);
        return value == null ? 0 : value;
    }

    @Override
    public ImodaneThePyrohammerDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that much damage";
    }
}
