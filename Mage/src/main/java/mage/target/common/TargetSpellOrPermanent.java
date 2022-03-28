package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetImpl;
import mage.util.GameLog;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX
 */
public class TargetSpellOrPermanent extends TargetImpl {

    protected FilterSpellOrPermanent filter;

    public TargetSpellOrPermanent() {
        this(1);
    }

    public TargetSpellOrPermanent(FilterSpellOrPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetSpellOrPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetSpellOrPermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetSpellOrPermanent(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, new FilterSpellOrPermanent(), notTarget);
        this.notTarget = notTarget;
    }

    public TargetSpellOrPermanent(int minNumTargets, int maxNumTargets, FilterSpellOrPermanent filter, boolean notTarget) {
        super(notTarget);
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.ALL;
        this.notTarget = notTarget;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetSpellOrPermanent(final TargetSpellOrPermanent target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    public FilterPermanent getPermanentFilter() {
        return this.filter.getPermanentFilter();
    }

    public void setFilter(FilterSpellOrPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Spell spell = game.getStack().getSpell(id);
        return filter.match(spell, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (source != null) {
                MageObject targetSource = game.getObject(source);
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(permanent, source.getControllerId(), source, game);
            } else {
                return filter.match(permanent, game);
            }
        }
        Spell spell = game.getStack().getSpell(id);
        // 114.4. A spell or ability on the stack is an illegal target for itself.
        return spell != null && !source.getSourceId().equals(id) && filter.match(spell, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    /**
     * Checks if there are enough {@link mage.game.permanent.Permanent} or
     * {@link mage.game.stack.Spell} that can be chosen. Should only be used for
     * Ability targets since this checks for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the target event source
     * @param source
     * @param game
     * @return - true if enough valid {@link mage.game.permanent.Permanent} or
     * {@link mage.game.stack.Spell} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(source);
        for (StackObject stackObject : game.getStack()) {
            Spell spell = game.getStack().getSpell(stackObject.getId());
            if (spell != null
                    && !source.getSourceId().equals(spell.getSourceId())
                    && filter.match(spell, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceControllerId, source, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are enough {@link mage.game.permanent.Permanent} or
     * {@link mage.game.stack.Spell} that can be selected. Should not be used
     * for Ability targets since this does not check for protection, shroud etc.
     *
     * @param sourceControllerId - controller of the select event
     * @param game
     * @return - true if enough valid {@link mage.game.permanent.Permanent} or
     * {@link mage.game.stack.Spell} exist
     */
    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject : game.getStack()) {
            Spell spell = game.getStack().getSpell(stackObject.getId());
            if (spell != null
                    && filter.match(spell, sourceControllerId, null, game) && filter.match(spell, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, null, game) && filter.match(permanent, game)) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        for (StackObject stackObject : game.getStack()) {
            Spell spell = game.getStack().getSpell(stackObject.getId());
            if (spell != null
                    && !source.getSourceId().equals(spell.getSourceId())
                    && filter.match(spell, sourceControllerId, source, game)) {
                possibleTargets.add(spell.getId());
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceControllerId, source, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            Spell spell = game.getStack().getSpell(stackObject.getId());
            if (spell != null
                    && filter.match(spell, sourceControllerId, null, game)) {
                possibleTargets.add(spell.getId());
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, null, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append(' ');
            } else {
                Spell spell = game.getStack().getSpell(targetId);
                if (spell != null) {
                    if (spell.isFaceDown(game)) {
                        sb.append(GameLog.getNeutralColoredText("face down spell"));
                    } else {
                        sb.append(spell.getLogName()).append(' ');
                    }
                }
            }
        }
        return sb.toString().trim();
    }

    @Override
    public TargetSpellOrPermanent copy() {
        return new TargetSpellOrPermanent(this);
    }

}
