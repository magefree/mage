package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.decorator.OptionalOneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierFirebendingToken;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Grath
 */
public final class FirebenderAscension extends CardImpl {

    public FirebenderAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When this enchantment enters, create a 2/2 red Soldier creature token with firebending 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierFirebendingToken())));

        // Whenever a creature you control attacking causes a triggered ability of that creature to trigger, put a quest
        // counter on this enchantment. Then if it has four or more quest counters on it, you may copy that ability. You
        // may choose new targets for the copy.
        this.addAbility(new FirebenderAscensionTriggeredAbility());
    }

    private FirebenderAscension(final FirebenderAscension card) {
        super(card);
    }

    @Override
    public FirebenderAscension copy() {
        return new FirebenderAscension(this);
    }
}

class FirebenderAscensionTriggeredAbility extends TriggeredAbilityImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 4);

    FirebenderAscensionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(), true), false);
        addEffect(new ConditionalOneShotEffect(new OptionalOneShotEffect(new CopyStackObjectEffect()), condition,
                "Then if it has four or more quest counters on it, you may copy that ability. You may choose new targets for the copy."));
        setTriggerPhrase("Whenever a creature you control attacking causes a triggered ability of that creature to trigger, ");
    }

    private FirebenderAscensionTriggeredAbility(final FirebenderAscensionTriggeredAbility ability) {
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
        if (stackObject == null || permanent == null || !permanent.isCreature(game)) {
            return false; // only creatures
        }
        if (this.getControllerId() != permanent.getControllerId()) {
            return false; // only your creatures
        }
        Ability stackAbility = stackObject.getStackAbility();
        if (!stackAbility.isTriggeredAbility()) {
            return false;
        }
        GameEvent triggerEvent = ((TriggeredAbility) stackAbility).getTriggerEvent();
        GameEvent.EventType eventType = triggerEvent.getType();
        if (triggerEvent == null || (eventType != GameEvent.EventType.ATTACKER_DECLARED &&
                                     eventType != GameEvent.EventType.DECLARED_ATTACKERS &&
                                     eventType != GameEvent.EventType.DEFENDER_ATTACKED)) {
            return false; // only attacking triggers
        }
        switch (triggerEvent.getType()) {
            case ATTACKER_DECLARED:
                if (triggerEvent.getSourceId() != permanent.getId()) {
                    return false;
                } // only triggered abilities of that creature
                break;
            case DECLARED_ATTACKERS:
                if (game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .noneMatch(permanent.getId()::equals)) {
                    return false;
                } // only if the creature was one of the attackers that were declared
                // This might give some false positives?
                break;
            case DEFENDER_ATTACKED:
                if (((DefenderAttackedEvent) triggerEvent)
                        .getAttackers(game)
                        .stream()
                        .noneMatch(permanent::equals)) {
                    return false;
                } // only if the creature was one of the attackers that were declared
                // This might give some false positives?
        }
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public FirebenderAscensionTriggeredAbility copy() {
        return new FirebenderAscensionTriggeredAbility(this);
    }

}