
package mage.target.common;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TargetActivatedOrTriggeredAbilityOrLegendarySpell extends TargetObject {

    protected final FilterStackObject filter;

    public TargetActivatedOrTriggeredAbilityOrLegendarySpell() {
        this(new FilterStackObject("activated ability, triggered ability, or legendary spell"));
    }

    public TargetActivatedOrTriggeredAbilityOrLegendarySpell(FilterStackObject filter) {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
        this.filter = filter;
    }

    public TargetActivatedOrTriggeredAbilityOrLegendarySpell(final TargetActivatedOrTriggeredAbilityOrLegendarySpell target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // rule 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        return isActivatedOrTriggeredAbilityOrLegendarySpell(stackObject) && source != null && filter.match(stackObject, source.getControllerId(), source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (isActivatedOrTriggeredAbilityOrLegendarySpell(stackObject)
                    && filter.match(stackObject, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getStack()
                .stream()
                .anyMatch(TargetActivatedOrTriggeredAbilityOrLegendarySpell::isActivatedOrTriggeredAbilityOrLegendarySpell);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return game.getStack().stream()
                .filter(TargetActivatedOrTriggeredAbilityOrLegendarySpell::isActivatedOrTriggeredAbilityOrLegendarySpell)
                .map(stackObject -> stackObject.getStackAbility().getId())
                .collect(Collectors.toSet());
    }

    @Override
    public TargetActivatedOrTriggeredAbilityOrLegendarySpell copy() {
        return new TargetActivatedOrTriggeredAbilityOrLegendarySpell(this);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static boolean isActivatedOrTriggeredAbilityOrLegendarySpell(StackObject stackObject) {
        if (stackObject == null) {
            return false;
        }
        if (stackObject instanceof Ability) {
            Ability ability = (Ability) stackObject;
            return ability.getAbilityType() == AbilityType.TRIGGERED
                    || ability.getAbilityType() == AbilityType.ACTIVATED;
        }
        if (stackObject instanceof Spell) {
            Spell spell = (Spell) stackObject;
            return spell.isLegendary();
        }
        return false;
    }
}
