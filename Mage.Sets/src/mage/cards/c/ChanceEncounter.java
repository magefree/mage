
package mage.cards.c;

import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.CoinFlippedEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChanceEncounter extends CardImpl {

    public ChanceEncounter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Whenever you win a coin flip, put a luck counter on Chance Encounter.
        this.addAbility(new ChanceEncounterTriggeredAbility());

        // At the beginning of your upkeep, if Chance Encounter has ten or more luck counters on it, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceHasCounterCondition(CounterType.LUCK, 10, Integer.MAX_VALUE),
                "At the beginning of your upkeep, if {this} has ten or more luck counters on it, you win the game."));
    }

    private ChanceEncounter(final ChanceEncounter card) {
        super(card);
    }

    @Override
    public ChanceEncounter copy() {
        return new ChanceEncounter(this);
    }
}

class ChanceEncounterTriggeredAbility extends TriggeredAbilityImpl {

    ChanceEncounterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LUCK.createInstance()), false);
        setTriggerPhrase("Whenever you win a coin flip, ");
    }

    private ChanceEncounterTriggeredAbility(final ChanceEncounterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChanceEncounterTriggeredAbility copy() {
        return new ChanceEncounterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return flipEvent.getPlayerId().equals(controllerId)
                && flipEvent.isWinnable()
                && (flipEvent.getChosen() == flipEvent.getResult());
    }
}
