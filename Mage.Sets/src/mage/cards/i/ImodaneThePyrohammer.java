package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
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
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.UUID;
import java.util.stream.Stream;

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

class ImodaneThePyrohammerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control that targets only a single creature");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private static final Hint hint = new ValuePositiveHint("Damage dealt to the target", SavedDamageValue.MUCH);

    ImodaneThePyrohammerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(Outcome.Damage, SavedDamageValue.MUCH, TargetController.OPPONENT)
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
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForPermanentsEvent) event)
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
                .filter(e -> e.getAmount() > 0);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = filterBatchEvent(event, game)
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (amount <= 0) {
            return false;
        }

        this.getEffects().setValue("damage", amount);
        return true;
    }
}