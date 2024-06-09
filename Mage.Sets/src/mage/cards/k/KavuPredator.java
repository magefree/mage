
package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KavuPredator extends CardImpl {

    public KavuPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever an opponent gains life, put that many +1/+1 counters on Kavu Predator.
        this.addAbility(new KavuPredatorTriggeredAbility());
    }

    private KavuPredator(final KavuPredator card) {
        super(card);
    }

    @Override
    public KavuPredator copy() {
        return new KavuPredator(this);
    }
}

class KavuPredatorTriggeredAbility extends TriggeredAbilityImpl {

    public KavuPredatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedGainedLifeValue.MANY));
        setTriggerPhrase("Whenever an opponent gains life, ");
    }

    private KavuPredatorTriggeredAbility(final KavuPredatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KavuPredatorTriggeredAbility copy() {
        return new KavuPredatorTriggeredAbility(this);
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setValue(SavedGainedLifeValue.VALUE_KEY, event.getAmount());
        return true;
    }
}