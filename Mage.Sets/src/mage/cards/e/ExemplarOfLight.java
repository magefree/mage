package mage.cards.e;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author TheElk801
 */
public final class ExemplarOfLight extends CardImpl {

    public ExemplarOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on this creature.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // Whenever you put one or more +1/+1 counters on this creature, draw a card. This ability triggers only once each turn.
        this.addAbility(new ExemplarOfLightTriggeredAbility());
    }

    private ExemplarOfLight(final ExemplarOfLight card) {
        super(card);
    }

    @Override
    public ExemplarOfLight copy() {
        return new ExemplarOfLight(this);
    }
}

class ExemplarOfLightTriggeredAbility extends TriggeredAbilityImpl {

    ExemplarOfLightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever you put one or more +1/+1 counters on this creature, ");
        this.setTriggersLimitEachTurn(1);
    }

    private ExemplarOfLightTriggeredAbility(final ExemplarOfLightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExemplarOfLightTriggeredAbility copy() {
        return new ExemplarOfLightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.P1P1.getName())
                && isControlledBy(event.getPlayerId())
                && event.getTargetId().equals(getSourceId());
    }

}
