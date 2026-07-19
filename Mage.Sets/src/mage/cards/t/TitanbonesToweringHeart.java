package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author muz
 */
public final class TitanbonesToweringHeart extends CardImpl {

    public TitanbonesToweringHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you gain life, put two +1/+1 counters on Titanbones.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))));

        // When you discard this card, you gain 3 life.
        this.addAbility(new TitanbonesToweringHeartTriggeredAbility());
    }

    private TitanbonesToweringHeart(final TitanbonesToweringHeart card) {
        super(card);
    }

    @Override
    public TitanbonesToweringHeart copy() {
        return new TitanbonesToweringHeart(this);
    }
}

class TitanbonesToweringHeartTriggeredAbility extends TriggeredAbilityImpl {

    TitanbonesToweringHeartTriggeredAbility() {
        super(Zone.ALL, new GainLifeEffect(3));
        this.setTriggerPhrase("When you discard this card, ");
    }

    private TitanbonesToweringHeartTriggeredAbility(final TitanbonesToweringHeartTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TitanbonesToweringHeartTriggeredAbility copy() {
        return new TitanbonesToweringHeartTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.getSourceId().equals(event.getTargetId());
    }
}
