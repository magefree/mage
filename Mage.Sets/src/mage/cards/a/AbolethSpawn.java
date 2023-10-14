package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Rowan-Gudmundsson
 */
public final class AbolethSpawn extends CardImpl {

    public AbolethSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever a creature entering the battlefield under an opponent’s control
        // causes a triggered ability of that creature to trigger, you may copy that
        // ability. You may choose new targets for the copy.
        this.addAbility(new AbolethSpawnTriggeredAbility());
    }

    private AbolethSpawn(final AbolethSpawn card) {
        super(card);
    }

    @Override
    public AbolethSpawn copy() {
        return new AbolethSpawn(this);
    }
}

class AbolethSpawnTriggeredAbility extends TriggeredAbilityImpl {

    AbolethSpawnTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect(), true);
    }

    private AbolethSpawnTriggeredAbility(final AbolethSpawnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRIGGERED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        Permanent permanent = game.getPermanent(event.getSourceId());

        if (stackObject == null || !(stackObject.getStackAbility() instanceof TriggeredAbility)) {
            return false;
        }

        TriggeredAbility ability = (TriggeredAbility) stackObject.getStackAbility();

        GameEvent triggerEvent = ability.getTriggerEvent();

        if (triggerEvent == null
                || triggerEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return false;
        }

        if (permanent == null
                || !permanent.isCreature(game)
                || permanent.getControllerId().equals(controllerId)) {
            return false;
        }

        this.getEffects().setValue("stackObject", stackObject);
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public AbolethSpawnTriggeredAbility copy() {
        return new AbolethSpawnTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature entering the battlefield under an opponent's control causes a triggered ability of that creature to trigger, you may copy that ability. You may choose new targets for the copy.";
    }
}
