package mage.cards.s;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ShangChiAndTheTenRings extends CardImpl {

    public ShangChiAndTheTenRings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you draw a card, put a +1/+1 counter on Shang-Chi.
        this.addAbility(new DrawCardControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // When the tenth +1/+1 counter is put on Shang-Chi, you draw five cards and gain 5 life.
        this.addAbility(new ShangChiAndTheTenRingsTriggeredAbility());
    }

    private ShangChiAndTheTenRings(final ShangChiAndTheTenRings card) {
        super(card);
    }

    @Override
    public ShangChiAndTheTenRings copy() {
        return new ShangChiAndTheTenRings(this);
    }
}

class ShangChiAndTheTenRingsTriggeredAbility extends TriggeredAbilityImpl {

    ShangChiAndTheTenRingsTriggeredAbility() {
        super(Zone.ALL, new DrawCardSourceControllerEffect(5));
        this.addEffect(new GainLifeEffect(5).concatBy("and"));
        this.setTriggerPhrase("When the tenth +1/+1 counter is put on {this}, ");
    }

    private ShangChiAndTheTenRingsTriggeredAbility(final ShangChiAndTheTenRingsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId()) || !event.getData().equals(CounterType.P1P1.getName())) {
            return false;
        }
        int amountAdded = event.getAmount();
        Permanent sourcePermanent = Optional
            .ofNullable(game.getPermanent(getSourceId()))
            .orElse(game.getPermanentEntering(getSourceId()));
        int p1p1Counters;
        if (sourcePermanent != null) {
            p1p1Counters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
        } else {
            p1p1Counters = amountAdded;
        }
        return p1p1Counters - amountAdded < 10 && 10 <= p1p1Counters;
    }

    @Override
    public ShangChiAndTheTenRingsTriggeredAbility copy() {
        return new ShangChiAndTheTenRingsTriggeredAbility(this);
    }
}
