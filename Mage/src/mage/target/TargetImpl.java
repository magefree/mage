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

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetImpl<T extends TargetImpl<T>> implements Target {

    protected Map<UUID, Integer> targets = new LinkedHashMap<UUID, Integer>();
    protected Map<UUID, Integer> zoneChangeCounters = new HashMap<UUID, Integer>();

    protected String targetName;
    protected Zone zone;
    protected int maxNumberOfTargets;
    protected int minNumberOfTargets;
    protected boolean required = false;
    protected boolean chosen = false;
    protected boolean notTarget = false;

    @Override
    public abstract T copy();

    public TargetImpl() {
        this(false);
    }

    public TargetImpl(boolean notTarget) {
        this.notTarget = notTarget;
    }

    public TargetImpl(final TargetImpl<T> target) {
        this.targetName = target.targetName;
        this.zone = target.zone;
        this.maxNumberOfTargets = target.maxNumberOfTargets;
        this.minNumberOfTargets = target.minNumberOfTargets;
        this.required = target.required;
        this.chosen = target.chosen;
        this.targets.putAll(target.targets);
        this.zoneChangeCounters.putAll(target.zoneChangeCounters);
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
    public String getMessage() {
        if (maxNumberOfTargets != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Select ").append(targetName);
            if (maxNumberOfTargets > 0 && maxNumberOfTargets != Integer.MAX_VALUE) {
                sb.append(" (").append(targets.size()).append("/").append(maxNumberOfTargets).append(")");
            } else {
                sb.append(" (").append(targets.size()).append(")");
            }
            return sb.toString();
        }
        if (targetName.startsWith("another")) {
            return "Select " + targetName;
        }
        return "Select a " + targetName;
    }

    @Override
    public boolean isNotTarget() {
        return notTarget;
    }

    @Override
    public String getTargetName() {
        return targetName;
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
    public boolean isRequired() {
        return required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean isChosen() {
        if (maxNumberOfTargets == 0 && minNumberOfTargets == 0)
            return true;
        if (maxNumberOfTargets != 0 && targets.size() == maxNumberOfTargets)
            return true;
        return chosen;
    }

    @Override
    public boolean doneChosing() {
        if (maxNumberOfTargets == 0)
            return false;
        return targets.size() == maxNumberOfTargets;
    }

    @Override
    public void clearChosen() {
        targets.clear();
        zoneChangeCounters.clear();
        chosen = false;
    }

    @Override
    public void add(UUID id, Game game) {
        if (maxNumberOfTargets == 0 || targets.size() < maxNumberOfTargets) {
            if (!targets.containsKey(id)) {
                targets.put(id, 0);
                rememberZoneChangeCounter(id, game);
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
        addTarget(id, source, game, false);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        //20100423 - 113.3
        if (maxNumberOfTargets == 0 || targets.size() < maxNumberOfTargets) {
            if (!targets.containsKey(id)) {
                if (source != null) {
                    if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getId(), source.getControllerId()))) {
                        targets.put(id, 0);
                        rememberZoneChangeCounter(id, game);
                        chosen = targets.size() >= minNumberOfTargets;
                        if (!skipEvent)
                            game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getId(), source.getControllerId()));
                    }
                }
                else {
                    targets.put(id, 0);
                }
            }
        }
    }

    private void rememberZoneChangeCounter(UUID id, Game game) {
        Card card = game.getCard(id);
        if (card != null) {
            zoneChangeCounters.put(id, card.getZoneChangeCounter());
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
        if (source != null) {
            if (!game.replaceEvent(GameEvent.getEvent(EventType.TARGET, id, source.getId(), source.getControllerId()))) {
                targets.put(id, amount);
                rememberZoneChangeCounter(id, game);
                chosen = targets.size() >= minNumberOfTargets;
                if (!skipEvent)
                    game.fireEvent(GameEvent.getEvent(EventType.TARGETED, id, source.getId(), source.getControllerId()));
            }
        }
        else {
            targets.put(id, amount);
            rememberZoneChangeCounter(id, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Game game) {
        Player player = game.getPlayer(playerId);
        while (!isChosen() && !doneChosing()) {
            chosen = targets.size() >= minNumberOfTargets;
            if (!player.choose(outcome, this, sourceId, game)) {
                return chosen;
            }
            chosen = targets.size() >= minNumberOfTargets;
        }
        return chosen = true;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        Player player = game.getPlayer(playerId);
        while (!isChosen() && !doneChosing()) {
            chosen = targets.size() >= minNumberOfTargets;
            if (!player.chooseTarget(outcome, this, source, game)) {
                return chosen;
            }
            chosen = targets.size() >= minNumberOfTargets;
        }
        return chosen = true;
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        for (UUID targetId: targets.keySet()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                if (zoneChangeCounters.containsKey(targetId) && zoneChangeCounters.get(targetId) != card.getZoneChangeCounter()) {
                    continue; // it's not legal so continue to have a look at other targeted cards
                }
            }
            if (game.replaceEvent(GameEvent.getEvent(EventType.TARGET, targetId, source.getId(), source.getControllerId())))
                continue;
            if (canTarget(targetId, source, game))
                return true;
        }
        return false;
    }

    @Override
    public List<T> getTargetOptions(Ability source, Game game) {
        List<T> options = new ArrayList<T>();
        Set<UUID> possibleTargets = possibleTargets(source.getSourceId(), source.getControllerId(), game);
        possibleTargets.removeAll(getTargets());
        Iterator<UUID> it = possibleTargets.iterator();
        while (it.hasNext()) {
            UUID targetId = it.next();
            T target = this.copy();
            target.clearChosen();
            target.addTarget(targetId, source, game, true);
            if (!target.isChosen()) {
                Iterator<UUID> it2 = possibleTargets.iterator();
                while (it2.hasNext()&& !target.isChosen()) {
                    UUID nextTargetId = it2.next();
                    target.addTarget(nextTargetId, source, game, true);
                }
            }
            if (target.isChosen())
                options.add(target);
        }        
        return options;
    }

    @Override
    public List<UUID> getTargets() {
        return new ArrayList<UUID>(targets.keySet());
    }

    @Override
    public int getTargetAmount(UUID targetId) {
        if (targets.containsKey(targetId))
            return targets.get(targetId);
        return 0;
    }

//    @Override
//    public UUID getLastTarget() {
//        if (targets.size() > 0)
//            return targets.keySet().iterator().next();
//        return null;
//    }

    @Override
    public UUID getFirstTarget() {
        if (targets.size() > 0)
            return targets.keySet().iterator().next();
        return null;
    }

    @Override
    public void setNotTarget(boolean notTarget) {
        this.notTarget = notTarget;
    }
}
