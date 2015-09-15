/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetImpl implements Target {

    protected Map<UUID, Integer> targets = new LinkedHashMap<>();
    protected Map<UUID, Integer> zoneChangeCounters = new HashMap<>();

    protected String targetName;
    protected Zone zone;
    protected int maxNumberOfTargets;
    protected int minNumberOfTargets;
    protected boolean required = true;
    protected boolean requiredExplicitlySet = false;
    protected boolean chosen = false;
    // is the target handled as targeted spell/ability (notTarget = true is used for not targeted effects like e.g. sacrifice)
    protected boolean notTarget = false;
    protected boolean atRandom = false;
    protected UUID targetController = null; // if null the ability controller is the targetController
    protected UUID abilityController = null; // only used if target controller != ability controller

    @Override
    public abstract TargetImpl copy();

    public TargetImpl() {
        this(false);
    }

    public TargetImpl(boolean notTarget) {
        this.notTarget = notTarget;
    }

    public TargetImpl(final TargetImpl target) {
        this.targetName = target.targetName;
        this.zone = target.zone;
        this.maxNumberOfTargets = target.maxNumberOfTargets;
        this.minNumberOfTargets = target.minNumberOfTargets;
        this.required = target.required;
        this.requiredExplicitlySet = target.requiredExplicitlySet;
        this.chosen = target.chosen;
        this.targets.putAll(target.targets);
        this.zoneChangeCounters.putAll(target.zoneChangeCounters);
        this.atRandom = target.atRandom;
        this.notTarget = target.notTarget;
        this.targetController = target.targetController;
        this.abilityController = target.abilityController;
    }

    @Override
    public int getNumberOfTargets() {
        return this.minNumberOfTargets;
    }

    @Override
    public int getMaxNumberOfTargets() {
        return this.maxNumberOfTargets;
    }

    @Override
    public void setMinNumberOfTargets(int minNumberOftargets) {
        this.minNumberOfTargets = minNumberOftargets;
    }

    @Override
    public void setMaxNumberOfTargets(int maxNumberOftargets) {
        this.maxNumberOfTargets = maxNumberOftargets;
    }

    @Override
    public String getMessage() {
        String suffix = "";
        if (targetController != null) {
            // Hint for the selecting player that the targets must be valid from the point of the ability controller
            // e.g. select opponent text may be misleading otherwise
            suffix = " (target controlling!)";
        }
        if (getMaxNumberOfTargets() != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Select ").append(targetName);
            if (getMaxNumberOfTargets() > 0 && getMaxNumberOfTargets() != Integer.MAX_VALUE) {
                sb.append(" (").append(targets.size()).append("/").append(getMaxNumberOfTargets()).append(")");
            } else {
                sb.append(" (").append(targets.size()).append(")");
            }
            sb.append(suffix);
            return sb.toString();
        }
        if (targetName.startsWith("another") || targetName.startsWith("a ") || targetName.startsWith("an ")) {
            return "Select " + targetName + suffix;
        }
        return "Select a " + targetName + suffix;
    }

    @Override
    public boolean isNotTarget() {
        return notTarget;
    }

    @Override
    public String getTargetName() {
        StringBuilder sb = new StringBuilder(targetName);
        if (isRandom()) {
            sb.append(" chosen at random");
        }
        return sb.toString();
    }

    @Override
    public void setTargetName(String name) {
        this.targetName = name;
    }

    @Override
    public Zone getZone() {
        return zone;
    }

    @Override
    public boolean isRequired(UUID sourceId, Game game) {
        MageObject object = game.getObject(sourceId);
        if (!requiredExplicitlySet && object != null && object instanceof Ability) {
            return isRequired((Ability) object);
        } else {
            return isRequired();
        }
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isRequired(Ability ability) {
        return ability == null || ability.isActivated() || !(ability.getAbilityType().equals(AbilityType.SPELL) || ability.getAbilityType().equals(AbilityType.ACTIVATED));
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
        this.requiredExplicitlySet = true;
    }

    @Override
    public boolean isChosen() {
        if (getMaxNumberOfTargets() == 0 && getNumberOfTargets() == 0) {
            return true;
        }
        if (getMaxNumberOfTargets() != 0 && targets.size() == getMaxNumberOfTargets()) {
            return true;
        }
        return chosen;
    }

    @Override
    public boolean doneChosing() {
        if (getMaxNumberOfTargets() == 0) {
            return false;
        }
        return targets.size() == getMaxNumberOfTargets();
    }

    @Override
    public void clearChosen() {
        targets.clear();
        zoneChangeCounters.clear();
        chosen = false;
    }

    @Override
    public void add(UUID id, Game game) {
        if (getMaxNumberOfTargets() == 0 || targets.size() < getMaxNumberOfTargets()) {
            if (!targets.containsKey(id)) {
                targets.put(id, 0);
                rememberZoneChangeCounter(id, game);
                chosen = targets.size() >= getNumberOfTargets();
            }
        }
    }

    @Override
    public void remove(UUID id) {
        if (targets.containsKey(id)) {
            targets.remove(id);
            zoneChangeCounters.remove(id);
        }
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game) {
        addTarget(id, source, game, notTarget);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        //20100423 - 113.3
        if (getMaxNumberOfTargets() == 0 || targets.size() < getMaxNumberOfTargets()) {
            if (!targets.containsKey(id)) {
                if (source != null && !skipEvent) {
                    if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getSourceId(), source.getControllerId()))) {
                        targets.put(id, 0);
                        rememberZoneChangeCounter(id, game);
                        chosen = targets.size() >= getNumberOfTargets();
                        if (!skipEvent) {
                            game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getSourceId(), source.getControllerId()));
                        }
                    }
                } else {
                    targets.put(id, 0);
                }
            }
        }
    }

    @Override
    public void updateTarget(UUID id, Game game) {
        rememberZoneChangeCounter(id, game);
    }

    private void rememberZoneChangeCounter(UUID id, Game game) {
        Card card = game.getCard(id);
        if (card != null) {
            zoneChangeCounters.put(id, card.getZoneChangeCounter(game));
        }
    }

    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game) {
        addTarget(id, amount, source, game, false);
    }

    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent) {
        if (targets.containsKey(id)) {
            amount += targets.get(id);
        }
        if (source != null && !skipEvent) {
            if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getSourceId(), source.getControllerId()))) {
                targets.put(id, amount);
                rememberZoneChangeCounter(id, game);
                chosen = targets.size() >= getNumberOfTargets();
                if (!skipEvent) {
                    game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getSourceId(), source.getControllerId()));
                }
            }
        } else {
            targets.put(id, amount);
            rememberZoneChangeCounter(id, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Game game) {
        Player player = game.getPlayer(playerId);
        while (!isChosen() && !doneChosing()) {
            chosen = targets.size() >= getNumberOfTargets();
            if (!player.choose(outcome, this, sourceId, game)) {
                return chosen;
            }
            chosen = targets.size() >= getNumberOfTargets();
        }
        return chosen = true;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        while (!isChosen() && !doneChosing()) {
            chosen = targets.size() >= getNumberOfTargets();
            if (isRandom()) {
                Set<UUID> possibleTargets = possibleTargets(source.getSourceId(), playerId, game);
                if (possibleTargets.size() > 0) {
                    int i = 0;
                    int rnd = new Random().nextInt(possibleTargets.size());
                    Iterator it = possibleTargets.iterator();
                    while (i < rnd) {
                        it.next();
                        i++;
                    }
                    this.addTarget(((UUID) it.next()), source, game);
                } else {
                    return chosen;
                }
            } else {
                if (!getTargetController(game, playerId).chooseTarget(outcome, this, source, game)) {
                    return chosen;
                }
            }
            chosen = targets.size() >= getNumberOfTargets();
        }
        return chosen = true;
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        Set<UUID> illegalTargets = new HashSet<>();
//        int replacedTargets = 0;
        for (UUID targetId : targets.keySet()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                if (zoneChangeCounters.containsKey(targetId) && zoneChangeCounters.get(targetId) != card.getZoneChangeCounter(game)) {
                    illegalTargets.add(targetId);
                    continue; // it's not legal so continue to have a look at other targeted objects
                }
            }
            if (!notTarget && game.replaceEvent(GameEvent.getEvent(EventType.TARGET, targetId, source.getSourceId(), source.getControllerId()))) {
//                replacedTargets++;
                illegalTargets.add(targetId);
                continue;
            }
            if (!canTarget(targetId, source, game)) {
                illegalTargets.add(targetId);
            }
        }
        // remove illegal targets, needed to handle if only a subset of targets was illegal
        for (UUID targetId : illegalTargets) {
            targets.remove(targetId);
        }
//        if (replacedTargets > 0 && replacedTargets == targets.size()) {
//            return false;
//        }
        if (getNumberOfTargets() == 0 && targets.isEmpty()) {
            return true;
        }
        return targets.size() > 0;
    }

    /**
     * Returns all possible different target combinations
     *
     * @param source
     * @param game
     * @return
     */
    @Override
    public List<? extends TargetImpl> getTargetOptions(Ability source, Game game) {
        List<TargetImpl> options = new ArrayList<>();
        List<UUID> possibleTargets = new ArrayList<>();
        possibleTargets.addAll(possibleTargets(source.getSourceId(), source.getControllerId(), game));
        possibleTargets.removeAll(getTargets());

        // get the length of the array
        // e.g. for {'A','B','C','D'} => N = 4
        int N = possibleTargets.size();
        // not enough targets, return no option
        if (N < getNumberOfTargets()) {
            return options;
        }
        // not target but that's allowed, return one empty option
        if (N == 0) {
            TargetImpl target = this.copy();
            options.add(target);
            return options;
        }
        int maxK = getMaxNumberOfTargets() - getTargets().size();
        if (maxK > 5) { // Prevent endless iteration with targets set to INTEGER.maxvalue
            maxK = 5;
            if (N > 10) { // not more than 252 combinations
                maxK = 4;
            }
            if (N > 20) { // not more than 4845 combinations
                maxK = 3;
            }
        }
        if (N < maxK) { // less possible targets than the maximum allowed so reduce the max
            maxK = N;
        }
        int minK = getNumberOfTargets();
        if (getNumberOfTargets() == 0) { // add option without targets if possible
            TargetImpl target = this.copy();
            options.add(target);
            minK = 1;
        }
        for (int K = minK; K <= maxK; K++) {
            // get the combination by index
            // e.g. 01 --> AB , 23 --> CD
            int combination[] = new int[K];

            // position of current index
            //  if (r = 1)              r*
            //  index ==>        0   |   1   |   2
            //  element ==>      A   |   B   |   C
            int r = 0;
            int index = 0;

            while (r >= 0) {
                // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
                // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"

                // for r = 0 ==> index < (4+ (0 - 2)) = 2
                if (index <= (N + (r - K))) {
                    combination[r] = index;

                    // if we are at the last position print and increase the index
                    if (r == K - 1) {
                        //add the new target option
                        TargetImpl target = this.copy();
                        for (int i = 0; i < combination.length; i++) {
                            target.addTarget(possibleTargets.get(combination[i]), source, game, true);
                        }
                        options.add(target);
                        index++;
                    } else {
                        // select index for next position
                        index = combination[r] + 1;
                        r++;
                    }
                } else {
                    r--;
                    if (r > 0) {
                        index = combination[r] + 1;
                    } else {
                        index = combination[0] + 1;
                    }
                }
            }
        }
        return options;
    }

    @Override
    public List<UUID> getTargets() {
        ArrayList<UUID> newList = new ArrayList<>();
        newList.addAll(targets.keySet());
        return newList;
    }

    @Override
    public int getTargetAmount(UUID targetId) {
        if (targets.containsKey(targetId)) {
            return targets.get(targetId);
        }
        return 0;
    }

    @Override
    public UUID getFirstTarget() {
        if (targets.size() > 0) {
            return targets.keySet().iterator().next();
        }
        return null;
    }

    @Override
    public void setNotTarget(boolean notTarget) {
        this.notTarget = notTarget;
    }

    @Override
    public boolean isRandom() {
        return this.atRandom;
    }

    @Override
    public void setRandom(boolean atRandom) {
        this.atRandom = atRandom;
    }

    @Override
    public void setTargetController(UUID playerId) {
        this.targetController = playerId;
    }

    @Override
    public UUID getTargetController() {
        return targetController;
    }

    @Override
    public void setAbilityController(UUID playerId) {
        this.abilityController = playerId;
    }

    @Override
    public UUID getAbilityController() {
        return abilityController;
    }

    @Override
    public Player getTargetController(Game game, UUID playerId) {
        if (getTargetController() != null) {
            return game.getPlayer(getTargetController());
        } else {
            return game.getPlayer(playerId);
        }
    }

    @Override
    public boolean isRequiredExplicitlySet() {
        return requiredExplicitlySet;
    }

}
