/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.predicate.mageobject.FromSetPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetImpl;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public abstract class CopySpellForEachItCouldTargetEffect<T extends MageItem> extends OneShotEffect {
    
    protected final FilterInPlay<T> filter;

    public CopySpellForEachItCouldTargetEffect(FilterInPlay<T> filter) {
        super(Outcome.Copy);
        this.staticText = "copy the spell for each other "+filter.getMessage()+" that spell could target. Each copy targets a different one";
        this.filter = filter;
    }

    public CopySpellForEachItCouldTargetEffect(final CopySpellForEachItCouldTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    protected abstract Spell getSpell(Game game, Ability source);
    protected abstract boolean changeTarget(Target target, Game game, Ability source);
    protected abstract void modifyCopy(Spell copy, Game game, Ability source);
    
    protected void modifyCopy(Spell copy, T newTarget, Game game, Ability source){
        modifyCopy(copy, game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = getSpell(game, source);
        if (spell != null) {
            Set<TargetAddress> targetsToBeChanged = new HashSet<>();
            boolean madeACopy = false;
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                if (targetInstance.getNumberOfTargets() > 1) {
                    throw new UnsupportedOperationException("Changing Target instances with multiple targets is unsupported");
                }
                if (changeTarget(targetInstance, game, source)) {
                    targetsToBeChanged.add(addr);
                }
            }
            
            if (targetsToBeChanged.size() < 1) {
                return false;
            }

            // collect objects that can be targeted
            Spell copy = spell.copySpell();
            copy.setCopiedSpell(true);
            modifyCopy(copy, game, source);
            Target sampleTarget = targetsToBeChanged.iterator().next().getTarget(copy);
            sampleTarget.setNotTarget(true);
                
            Map<UUID, Map<UUID, Spell>> playerTargetCopyMap = new HashMap<>();
            for (UUID objId : sampleTarget.possibleTargets(controller.getId(), game)) {
                MageItem obj = game.getObject(objId);
                if (obj == null) {
                    obj = game.getPlayer(objId);
                }
                if (obj != null) {
                    copy = spell.copySpell();
                    copy.setCopiedSpell(true);

                    try {
                        modifyCopy(copy, (T) obj, game, source);
                        if (!filter.match((T) obj, game)) {
                            continue;
                        }
                    } catch (ClassCastException e) {
                        continue;
                    }

                    boolean legal = true;
                    for (TargetAddress addr : targetsToBeChanged) {
                        // potential target must be legal for all targets that we're about to change
                        Target targetInstance = addr.getTarget(copy);
                        legal &= targetInstance.canTarget(objId, addr.getSpellAbility(copy), game);
                        if (!legal) {
                            break;
                        }

                        // potential target must not be the thing that was targeted initially
                        targetInstance = addr.getTarget(spell);
                        legal &= !targetInstance.getTargets().contains(objId);
                        if (!legal) {
                            break;
                        }
                    }

                    if (legal) {
                        for (TargetAddress addr : targetsToBeChanged) {
                            Target targetInstance = addr.getTarget(copy);
                            targetInstance.clearChosen();
                            targetInstance.add(objId, game);
                        }
                        if (!playerTargetCopyMap.containsKey(copy.getControllerId())) {
                            playerTargetCopyMap.put(copy.getControllerId(), new HashMap<UUID, Spell>());
                        }
                        playerTargetCopyMap.get(copy.getControllerId()).put(objId, copy);
                    }
                }
            }

            // allow the copies' controller to choose the order that they go on the stack
            for (Player player : game.getPlayers().values()) {
                if (playerTargetCopyMap.containsKey(player.getId())) {
                    Map<UUID, Spell> targetCopyMap = playerTargetCopyMap.get(player.getId());
                    if (targetCopyMap != null) {
                        while (targetCopyMap.size() > 0) {
                            FilterInPlay<T> setFilter = filter.copy();
                            setFilter.add(new FromSetPredicate(targetCopyMap.keySet()));
                            Target target = new TargetWithAdditionalFilter(sampleTarget, setFilter);
                            target.setMinNumberOfTargets(0);
                            target.setMaxNumberOfTargets(1);
                            target.setTargetName(filter.getMessage() + " that " + spell.getLogName() + " could target (" + targetCopyMap.size() + " remaining)");

                            // shortcut if there's only one possible target remaining
                            if (targetCopyMap.size() > 1
                                && target.canChoose(spell.getId(), player.getId(), game)) {
                                player.choose(Outcome.Neutral, target, spell.getId(), game);
                            }
                            Collection<UUID> chosenIds = target.getTargets();
                            if (chosenIds.isEmpty()) {
                                chosenIds = targetCopyMap.keySet();
                            }

                            List<UUID> toDelete = new ArrayList<>();
                            for (UUID chosenId : chosenIds) {
                                Spell chosenCopy = targetCopyMap.get(chosenId);
                                if (chosenCopy != null) {
                                    game.getStack().push(chosenCopy);
                                    toDelete.add(chosenId);
                                    madeACopy = true;
                                }
                            }

                            for (UUID idToDelete : toDelete) {
                                targetCopyMap.remove(idToDelete);
                            }
                        }
                    }
                }
            }
            return madeACopy;
        }
        return false;
    }
}



class CompoundFilter<T extends MageItem> extends FilterImpl<T> implements FilterInPlay<T> {

    protected final FilterInPlay<T> filterA;
    protected final FilterInPlay<T> filterB;

    public CompoundFilter(String name) {
        super(name);
        this.filterA = null;
        this.filterB = null;
    }

    public CompoundFilter(FilterInPlay<T> filterA, FilterInPlay<T> filterB, String name) {
        super(name);
        this.filterA = filterA;
        this.filterB = filterB;
    }

    @Override
    public boolean match(T obj, Game game) {
        return (filterA == null
                || !filterA.match(obj, game))
            && (filterB == null
                || !filterB.match(obj, game));
    }

    @Override
    public boolean match(T obj, UUID sourceId, UUID playerId, Game game) {
        return (filterA == null
                || !filterA.match(obj, sourceId, playerId, game))
            && (filterB == null
                || !filterB.match(obj, sourceId, playerId, game));
    }

    @Override
    public CompoundFilter copy() {
        return new CompoundFilter(filterA == null ? null : filterA.copy(),
                                  filterB == null ? null : filterB.copy(),
                                  message);
    }
}


class TargetWithAdditionalFilter<T extends MageItem> extends TargetImpl {
    
    protected final FilterInPlay<T> additionalFilter;
    protected final Target originalTarget;
    protected static final Integer minNumberOfTargets = null;
    protected static final Integer maxNumberOfTargets = null;
    protected static final Zone zone = null;

    public TargetWithAdditionalFilter(final TargetWithAdditionalFilter target){
        this(target.originalTarget, target.additionalFilter, false);        
    }

    public TargetWithAdditionalFilter(Target originalTarget, FilterInPlay<T> additionalFilter){
        this(originalTarget, additionalFilter, false);
    }

    public TargetWithAdditionalFilter(Target originalTarget, FilterInPlay<T> additionalFilter, boolean notTarget){
        originalTarget = originalTarget.copy();
        originalTarget.clearChosen();
        this.originalTarget = originalTarget;
        this.targetName = originalTarget.getFilter().getMessage();
        this.notTarget = notTarget;
        this.additionalFilter = additionalFilter;
    }

    @Override
    public int getNumberOfTargets() {
        return originalTarget.getNumberOfTargets();
    }

    @Override
    public int getMaxNumberOfTargets() {
        return originalTarget.getMaxNumberOfTargets();
    }
    
    @Override
    public void setMinNumberOfTargets(int minNumberOfTargets) {
        originalTarget.setMinNumberOfTargets(minNumberOfTargets);
    }

    @Override
    public void setMaxNumberOfTargets(int maxNumberOfTargets) {
        originalTarget.setMaxNumberOfTargets(maxNumberOfTargets);
    }

    @Override
    public Zone getZone() {
        return originalTarget.getZone();
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        MageItem obj = game.getObject(id);
        if (obj == null) {
            obj = game.getPlayer(id);
        }
        
        try {
            return obj != null
                && originalTarget.canTarget(id, game)
                && additionalFilter.match((T) obj, game);
        } catch (ClassCastException e) {
            return false;
        }
    }


    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        MageItem obj = game.getObject(id);
        if (obj == null) {
            obj = game.getPlayer(id);
        }
        
        try {
            return obj != null
                && originalTarget.canTarget(id, source, game)
                && additionalFilter.match((T) obj, source.getSourceId(), source.getControllerId(), game);
        } catch (ClassCastException e) {
            return false;
        }
    }


    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        MageItem obj = game.getObject(id);
        if (obj == null) {
            obj = game.getPlayer(id);
        }
        
        try {
            return obj != null
                && originalTarget.canTarget(controllerId, id, source, game)
                && additionalFilter.match((T) obj, source.getSourceId(), controllerId, game);
        } catch (ClassCastException e) {
            return false;
        }
    }


    @Override
    public FilterInPlay<T> getFilter() {
        return new CompoundFilter((FilterInPlay<T>) originalTarget.getFilter(), additionalFilter, originalTarget.getFilter().getMessage());
    }


    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int remainingTargets = getNumberOfTargets() - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }

        int count = 0;
        for (UUID objId : originalTarget.possibleTargets(sourceId, sourceControllerId, game)) {
            MageItem obj = game.getObject(objId);
            if (obj == null) {
                obj = game.getPlayer(objId);
            }
            try {
                if (!targets.containsKey(objId)
                    && obj != null
                    && additionalFilter.match((T) obj, sourceId, sourceControllerId, game)) {
                    count++;
                    if (count >= remainingTargets) {
                        return true;
                    }
                }
            } catch (ClassCastException e) {}
        }
        return false;
    }


    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int remainingTargets = getNumberOfTargets() - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }

        int count = 0;
        for (UUID objId : originalTarget.possibleTargets(sourceControllerId, game)) {
            MageItem obj = game.getObject(objId);
            if (obj == null) {
                obj = game.getPlayer(objId);
            }
            try {
                if (!targets.containsKey(objId)
                    && obj != null
                    && additionalFilter.match((T) obj, game)) {
                    count++;
                    if (count >= remainingTargets) {
                        return true;
                    }
                }
            } catch (ClassCastException e) {}
        }
        return false;
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> ret = new HashSet<>();
        for (UUID id : originalTarget.possibleTargets(sourceId, sourceControllerId, game)) {
            MageItem obj = game.getObject(id);
            if (obj == null) {
                obj = game.getPlayer(id);
            }
            try {
                if (obj != null
                    && additionalFilter.match((T) obj, sourceId, sourceControllerId, game)) {
                    ret.add(id);
                }
            } catch (ClassCastException e) {}
        }
        return ret;
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> ret = new HashSet<>();
        for (UUID id : originalTarget.possibleTargets(sourceControllerId, game)) {
            MageItem obj = game.getObject(id);
            if (obj == null) {
                obj = game.getPlayer(id);
            }
            try {
                if (obj != null
                    && additionalFilter.match((T) obj, game)) {
                    ret.add(id);
                }
            } catch (ClassCastException e) {}
        }
        return ret;
    }


    @Override
    public TargetWithAdditionalFilter copy() {
        return new TargetWithAdditionalFilter(this);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId: getTargets()) {
            MageObject object = game.getObject(targetId);
            if (object != null) {
                sb.append(object.getLogName()).append(" ");
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    sb.append(player.getLogName()).append(" ");
                }
            }
        }
        return sb.toString();
    }
}
