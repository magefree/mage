package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
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
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrictProctor extends CardImpl {

    public StrictProctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT, SubType.CLERIC);
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
        return event.getType() == GameEvent.EventType.TRIGGERED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        if (stackObject == null) {
            return false;
        }
        Ability ability = stackObject.getStackAbility();
        if (!(ability instanceof TriggeredAbility)) {
            return false;
        }
        GameEvent triggerEvent = ((TriggeredAbility) ability).getTriggerEvent();
        if (triggerEvent == null || triggerEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return false;
        }
        // set the target to the ability that gets triggered from the enter the battlefield trigger
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public StrictProctorTriggeredAbility copy() {
        return new StrictProctorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent entering the battlefield causes a triggered ability to trigger, "
                + "counter that ability unless its controller pays {2}.";
    }
}
