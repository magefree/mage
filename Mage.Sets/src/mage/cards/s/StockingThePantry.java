package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StockingThePantry extends CardImpl {

    public StockingThePantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Whenever you put one or more +1/+1 counters on a creature you control, put a supply counter on Stocking the Pantry.
        this.addAbility(new StockingThePantryTriggeredAbility());

        // {2}, Remove a supply counter from Stocking the Pantry: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.SUPPLY.createInstance()));
        this.addAbility(ability);
    }

    private StockingThePantry(final StockingThePantry card) {
        super(card);
    }

    @Override
    public StockingThePantry copy() {
        return new StockingThePantry(this);
    }
}

class StockingThePantryTriggeredAbility extends TriggeredAbilityImpl {

    StockingThePantryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.SUPPLY.createInstance()));
        setTriggerPhrase("Whenever you put one or more +1/+1 counters on a creature you control, ");
    }

    private StockingThePantryTriggeredAbility(final StockingThePantryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StockingThePantryTriggeredAbility copy() {
        return new StockingThePantryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getData().equals(CounterType.P1P1.getName()) || !isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && permanent.isControlledBy(this.getControllerId());
    }
}
