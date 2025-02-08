package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SabotageStrategist extends CardImpl {

    public SabotageStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever one or more creatures attack you, those creatures get -1/-0 until end of turn.
        this.addAbility(new SabotageStrategistTriggeredAbility());

        // Exhaust -- {5}{U}{U}: Put three +1/+1 counters on this creature.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), new ManaCostsImpl<>("{5}{U}{U}")
        ));
    }

    private SabotageStrategist(final SabotageStrategist card) {
        super(card);
    }

    @Override
    public SabotageStrategist copy() {
        return new SabotageStrategist(this);
    }
}

class SabotageStrategistTriggeredAbility extends TriggeredAbilityImpl {

    SabotageStrategistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1, 0).setText("those creatures get -1/-0 until end of turn"));
        this.setTriggerPhrase("Whenever one or more creatures attack you, ");
    }

    private SabotageStrategistTriggeredAbility(final SabotageStrategistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SabotageStrategistTriggeredAbility copy() {
        return new SabotageStrategistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DefenderAttackedEvent dEvent = (DefenderAttackedEvent) event;
        if (!isControlledBy(dEvent.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(dEvent.getAttackers(game), game));
        return true;
    }
}
