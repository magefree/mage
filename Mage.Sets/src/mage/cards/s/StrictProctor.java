package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrictProctor extends CardImpl {

    public StrictProctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent entering the battlefield causes a triggered ability to trigger, counter that ability unless its controller pays {2}.
        this.addAbility(new StrictProctorTriggeredAbility());
    }

    private StrictProctor(final StrictProctor card) {
        super(card);
    }

    @Override
    public StrictProctor copy() {
        return new StrictProctor(this);
    }
}

class StrictProctorTriggeredAbility extends TriggeredAbilityImpl {

    StrictProctorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(2)));
    }

    private StrictProctorTriggeredAbility(final StrictProctorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ABILITY_TRIGGERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        GameEvent triggeringEvent = (GameEvent) game.getState().getValue(event.getId().toString());
        if (triggeringEvent == null || triggeringEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(triggeringEvent.getTargetId(), game));
        return true;
    }

    @Override
    public StrictProctorTriggeredAbility copy() {
        return new StrictProctorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent entering the battlefield causes a triggered ability to trigger, " +
                "counter that ability unless its controller pays {2}.";
    }
}
