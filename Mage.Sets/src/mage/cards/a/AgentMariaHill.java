package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author muz
 */
public final class AgentMariaHill extends CardImpl {

    public AgentMariaHill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Agent Maria Hill becomes tapped to pay a teamwork cost, put a +1/+1 counter on her and draw a card.
        this.addAbility(new AgentMariaHillTriggeredAbility());
    }

    private AgentMariaHill(final AgentMariaHill card) {
        super(card);
    }

    @Override
    public AgentMariaHill copy() {
        return new AgentMariaHill(this);
    }
}

class AgentMariaHillTriggeredAbility extends TriggeredAbilityImpl {

    AgentMariaHillTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.setTriggerPhrase("Whenever {this} becomes tapped to pay a teamwork cost, ");
    }

    private AgentMariaHillTriggeredAbility(final AgentMariaHillTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AgentMariaHillTriggeredAbility copy() {
        return new AgentMariaHillTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PAID_TEAMWORK_COST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }
}
