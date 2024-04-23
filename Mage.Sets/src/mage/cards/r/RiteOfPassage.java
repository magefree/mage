
package mage.cards.r;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Plopman
 */
public final class RiteOfPassage extends CardImpl {

    public RiteOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature you control is dealt damage, put a +1/+1 counter on it.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on it");
        this.addAbility(new RiteOfPassageTriggeredAbility(effect));

    }

    private RiteOfPassage(final RiteOfPassage card) {
        super(card);
    }

    @Override
    public RiteOfPassage copy() {
        return new RiteOfPassage(this);
    }
}

class RiteOfPassageTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    public RiteOfPassageTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever a creature you control is dealt damage, ");
    }

    private RiteOfPassageTriggeredAbility(final RiteOfPassageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiteOfPassageTriggeredAbility copy() {
        return new RiteOfPassageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForOnePermanentEvent) event)
                .getEvents()
                .stream()
                .filter(e -> e.getAmount() > 0)
                .filter(e -> Optional
                        .of(e)
                        .map(DamagedPermanentEvent::getTargetId)
                        .map(game::getPermanentOrLKIBattlefield)
                        .filter(p -> StaticFilters.FILTER_CONTROLLED_CREATURE.match(p, getControllerId(), this, game))
                        .isPresent()
                );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!filterBatchEvent(event, game).findAny().isPresent()) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}
