package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.Modes;
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

import java.util.*;
import java.util.stream.Collectors;

/**
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

    private GripOfChaosTriggeredAbility(final GripOfChaosTriggeredAbility ability) {
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
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        StackObject stackObject = this
                .getEffects()
                .stream()
                .findFirst()
                .filter(Objects::nonNull)
                .map(Effect::getTargetPointer)
                .map(tp -> tp.getFirst(game, this))
                .map(game.getStack()::getStackObject)
                .orElse(null);
        if (stackObject == null) {
            return false;
        }
        Modes modes = stackObject.getStackAbility().getModes();
        return modes
                .getSelectedModes()
                .stream()
                .map(modes::get)
                .map(Mode::getTargets)
                .mapToInt(ArrayList::size)
                .sum() == 1;
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

    private GripOfChaosEffect(final GripOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public GripOfChaosEffect copy() {
        return new GripOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(this.getTargetPointer().getFirst(game, source));
        if (stackObject == null) {
            return false;
        }
        Modes modes = stackObject.getStackAbility().getModes();
        for (Target target : modes
                .getSelectedModes()
                .stream()
                .map(modes::get)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())) {
            UUID oldTargetId = target.getFirstTarget();
            Set<UUID> possibleTargets = target.possibleTargets(
                    stackObject.getControllerId(), stackObject.getStackAbility(), game
            );
            if (possibleTargets.contains(stackObject.getId())) { // The stackObject can't target itself
                possibleTargets.remove(stackObject.getId());
            }
            if (!possibleTargets.isEmpty()) {
                UUID newTargetId = RandomUtil.randomFromCollection(possibleTargets);
                target.remove(oldTargetId);
                target.add(newTargetId, game);
            }
        }
        return true;
    }
}
