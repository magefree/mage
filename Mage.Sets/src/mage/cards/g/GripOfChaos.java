package mage.cards.g;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author emerald000
 */
public final class GripOfChaos extends CardImpl {

    public GripOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        // Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.
        this.addAbility(new GripOfChaosTriggeredAbility());
    }

    private GripOfChaos(final GripOfChaos card) {
        super(card);
    }

    @Override
    public GripOfChaos copy() {
        return new GripOfChaos(this);
    }
}

class GripOfChaosTriggeredAbility extends TriggeredAbilityImpl {

    GripOfChaosTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GripOfChaosEffect());
    }

    GripOfChaosTriggeredAbility(final GripOfChaosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.TRIGGERED_ABILITY
                || event.getType() == GameEvent.EventType.COPIED_STACKOBJECT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        StackObject stackObject = null;
        for (Effect effect : this.getEffects()) {
            stackObject = game.getStack().getStackObject(effect.getTargetPointer().getFirst(game, this));
        }
        if (stackObject != null) {
            int numberOfTargets = 0;
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    numberOfTargets += target.getTargets().size();
                }
            }
            return numberOfTargets == 1;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.";
    }

    @Override
    public GripOfChaosTriggeredAbility copy() {
        return new GripOfChaosTriggeredAbility(this);
    }
}

class GripOfChaosEffect extends OneShotEffect {

    GripOfChaosEffect() {
        super(Outcome.Neutral);
        this.staticText = "reselect its target at random";
    }

    GripOfChaosEffect(final GripOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public GripOfChaosEffect copy() {
        return new GripOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(this.getTargetPointer().getFirst(game, source));
        if (stackObject != null) {
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    UUID oldTargetId = target.getFirstTarget();
                    Set<UUID> possibleTargets = target.possibleTargets(stackObject.getControllerId(), source, game);
                    if (possibleTargets.contains(stackObject.getId())) { // The stackObject can't target itself
                        possibleTargets.remove(stackObject.getId());
                    }
                    if (!possibleTargets.isEmpty()) {
                        int i = 0;
                        int rnd = RandomUtil.nextInt(possibleTargets.size());
                        Iterator<UUID> it = possibleTargets.iterator();
                        while (i < rnd) {
                            it.next();
                            i++;
                        }
                        UUID newTargetId = it.next();
                        target.remove(oldTargetId);
                        target.add(newTargetId, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
