
package mage.target;

import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetSpell extends TargetObject {

    protected final FilterSpell filter;
    private final Set<UUID> sourceIds = new HashSet<>();

    public TargetSpell() {
        this(1, 1, StaticFilters.FILTER_SPELL);
    }

    public TargetSpell(FilterSpell filter) {
        this(1, 1, filter);
    }

    public TargetSpell(int numTargets, FilterSpell filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetSpell(int minNumTargets, int maxNumTargets, FilterSpell filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.STACK;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetSpell(final TargetSpell target) {
        super(target);
        this.filter = target.filter.copy();
        this.sourceIds.addAll(target.sourceIds);
    }

    @Override
    public FilterSpell getFilter() {
        return filter;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // rule 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source == null || source.getId().equals(id)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(id);
        return filter.match(spell, source.getControllerId(), source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (this.minNumberOfTargets == 0) {
            return true;
        }
        int count = 0;
        for (StackObject stackObject : game.getStack()) {
            // rule 114.4. A spell or ability on the stack is an illegal target for itself.
            if (source.getSourceId() != null && source.getSourceId().equals(stackObject.getSourceId())) {
                continue;
            }
            if (canBeChosen(stackObject, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return game.getStack().stream()
                .filter(stackObject -> canBeChosen(stackObject, sourceControllerId, source, game))
                .map(StackObject::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(sourceControllerId, null, game);
    }

    @Override
    public TargetSpell copy() {
        return new TargetSpell(this);
    }

    private boolean canBeChosen(StackObject stackObject, UUID sourceControllerId, Ability source, Game game) {
        return stackObject instanceof Spell
                && game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId())
                && filter.match(stackObject, sourceControllerId, source, game);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        Spell spell = game.getStack().getSpell(id);
        if (spell != null) { // remember the original sourceID
            sourceIds.add(spell.getSourceId());
        }
        super.addTarget(id, source, game, skipEvent);
    }

    public Set<UUID> getSourceIds() {
        return sourceIds;
    }

}
