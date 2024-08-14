package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedBatchAllEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class TheRedTerror extends CardImpl {


    public TheRedTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever a red source you control deals damage to one or more permanents and/or players, put a +1/+1 counter on The Red Terror.
        this.addAbility(new TheRedTerrorTriggeredAbility().withFlavorWord("Advanced Species"));

    }

    private TheRedTerror(final TheRedTerror card) {
        super(card);
    }

    @Override
    public TheRedTerror copy() {
        return new TheRedTerror(this);
    }
}

class TheRedTerrorTriggeredAbility extends TriggeredAbilityImpl {

    TheRedTerrorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(1), false
        ).setText("put a +1/+1 counter on {this}"));
        setTriggerPhrase("Whenever a red source you control deals damage to one or more permanents and/or players, ");
    }

    private TheRedTerrorTriggeredAbility(final TheRedTerrorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRedTerrorTriggeredAbility copy() {
        return new TheRedTerrorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ALL;
    }

    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchAllEvent batchAllEvent = (DamagedBatchAllEvent) event;
        for (DamagedEvent batchAllEvent1 : batchAllEvent.getEvents()) {
            int damage = ((DamagedBatchAllEvent) event)
                    .getEvents()
                    .stream()
                    .filter(damagedEvent -> isControlledBy(game.getControllerId(damagedEvent.getAttackerId())))
                    .mapToInt(GameEvent::getAmount)
                    .sum();
            if (damage < 1) {
                return false;
            }

            MageObject sourceObject = game.getObject(batchAllEvent1.getSourceId());
            if (sourceObject != null && sourceObject.getColor(game).isRed()) {
                return true;
            }
        }
        return false;
    }
}
