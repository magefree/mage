package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheRedTerror extends CardImpl {

    public TheRedTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Advanced Species -- Whenever a red source you control deals damage to one or more permanents and/or players, put a +1/+1 counter on The Red Terror.
        this.addAbility(new TheRedTerrorTrigger().withFlavorWord("Advanced Species"));
    }

    private TheRedTerror(final TheRedTerror card) {
        super(card);
    }

    @Override
    public TheRedTerror copy() {
        return new TheRedTerror(this);
    }
}

class TheRedTerrorTrigger extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

    public TheRedTerrorTrigger() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)));
        setTriggerPhrase("Whenever a red source you control deals damage to one or more permanents and/or players");
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchBySourceEvent batchBySourceEvent = (DamagedBatchBySourceEvent) event;

        UUID sourceController = null;
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(batchBySourceEvent.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
            if (sourceObject instanceof Controllable) {
                sourceController = ((Controllable) sourceObject).getControllerId();
            }
        } else {
            sourceObject = sourcePermanent;
            sourceController = sourcePermanent.getControllerId();
        }

        return sourceObject.getColor(game).isRed()             // a red source
                && getControllerId().equals(sourceController)      // you control
                && batchBySourceEvent.getAmount() > 0;             // deals damage (skipping the permanent and/or player check, they are the only damageable)
    }

    private TheRedTerrorTrigger(final TheRedTerrorTrigger trigger) {
        super(trigger);
    }

    @Override
    public TheRedTerrorTrigger copy() {
        return new TheRedTerrorTrigger(this);
    }
}
