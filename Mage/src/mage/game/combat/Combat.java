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

package mage.game.combat;

import java.io.Serializable;
import java.util.*;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.keyword.CanAttackOnlyAloneAbility;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetDefender;
import mage.util.Copyable;
import mage.util.trace.TraceUtil;


/**
 * @author BetaSteward_at_googlemail.com
 */
public class Combat implements Serializable, Copyable<Combat> {

    private static FilterPlaneswalkerPermanent filterPlaneswalker = new FilterPlaneswalkerPermanent();
    private static FilterCreatureForCombatBlock filterBlockers = new FilterCreatureForCombatBlock();

    protected List<CombatGroup> groups = new ArrayList<CombatGroup>();
    protected Map<UUID, CombatGroup> blockingGroups = new HashMap<UUID, CombatGroup>();
    protected Set<UUID> defenders = new HashSet<UUID>();
    protected UUID attackerId; //the player that is attacking
    // <creature that can block, <all attackers that force the creature to block it>>
    protected Map<UUID, Set<UUID>> creaturesForcedToBlockAttackers = new HashMap<UUID, Set<UUID>>();

    public Combat() {
    }

    public Combat(final Combat combat) {
        this.attackerId = combat.attackerId;
        for (CombatGroup group : combat.groups) {
            groups.add(group.copy());
        }
        defenders.addAll(combat.defenders);
        for (Map.Entry<UUID, CombatGroup> group : combat.blockingGroups.entrySet()) {
            blockingGroups.put(group.getKey(), group.getValue());
        }
    }

    public List<CombatGroup> getGroups() {
        return groups;
    }

    public Collection<CombatGroup> getBlockingGroups() {
        return blockingGroups.values();
    }

    public Set<UUID> getDefenders() {
        return defenders;
    }

    public List<UUID> getAttackers() {
        List<UUID> attackers = new ArrayList<UUID>();
        for (CombatGroup group : groups) {
            attackers.addAll(group.attackers);
        }
        return attackers;
    }

    public List<UUID> getBlockers() {
        List<UUID> blockers = new ArrayList<UUID>();
        for (CombatGroup group : groups) {
            blockers.addAll(group.blockers);
        }
        return blockers;
    }

    public void clear() {
        groups.clear();
        blockingGroups.clear();
        defenders.clear();
        attackerId = null;
        creaturesForcedToBlockAttackers.clear();
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(attackerId).append(defenders);
        for (CombatGroup group : groups) {
            sb.append(group.defenderId).append(group.attackers).append(group.attackerOrder).append(group.blockers).append(group.blockerOrder);
        }
        return sb.toString();
    }

    public void setAttacker(UUID playerId) {
        this.attackerId = playerId;
    }

    /**
     * Add an additional attacker to the combat (e.g. token of Geist of Saint Traft)
     * This method doesn't trigger ATTACKER_DECLARED event (as intended).
     *
     * @param creatureId - creature that shall be added to the combat
     * @param game
     * @return
     */
    public boolean addAttackingCreature(UUID creatureId, Game game) {
        Player player = game.getPlayer(attackerId);
        if (defenders.size() == 1) {
            declareAttacker(creatureId, defenders.iterator().next(), game);
            return true;
        }
        else {
            TargetDefender target = new TargetDefender(defenders, creatureId);
            target.setRequired(true);
            player.chooseTarget(Outcome.Damage, target, null, game);
            if (target.getFirstTarget() != null) {
                declareAttacker(creatureId, target.getFirstTarget(), game);
                return true;
            }
        }
        return false;
    }

    public void selectAttackers(Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, attackerId, attackerId))) {
            Player player = game.getPlayer(attackerId);
            //20101001 - 508.1d
            checkAttackRequirements(player, game);
            player.selectAttackers(game, attackerId);
            if (game.isPaused() || game.isGameOver()) {
                return;
            }
            checkAttackRestrictions(player, game);
            resumeSelectAttackers(game);
        }
    }

    public void resumeSelectAttackers(Game game) {
        Player player = game.getPlayer(attackerId);
        for (CombatGroup group: groups) {
            for (UUID attacker: group.getAttackers()) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ATTACKER_DECLARED, group.defenderId, attacker, attackerId));
            }
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, attackerId, attackerId));
        game.fireInformEvent(player.getName() + " attacks with " + groups.size() + " creatures");
    }

    protected void checkAttackRequirements(Player player, Game game) {
        //20101001 - 508.1d
        for (Permanent creature : player.getAvailableAttackers(game)) {
            for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, game).entrySet()) {
                RequirementEffect effect = (RequirementEffect)entry.getKey();
                if (effect.mustAttack(game)) {
                    for (Ability ability: (HashSet<Ability>)entry.getValue()) {
                        UUID defenderId = effect.mustAttackDefender(ability, game);
                        if (defenderId == null) {
                            if (defenders.size() == 1) {
                                player.declareAttacker(creature.getId(), defenders.iterator().next(), game);
                            } else {
                                TargetDefender target = new TargetDefender(defenders, creature.getId());
                                target.setRequired(true);
                                if (player.chooseTarget(Outcome.Damage, target, null, game)) {
                                    player.declareAttacker(creature.getId(), target.getFirstTarget(), game);
                                }
                            }
                        } else {
                            player.declareAttacker(creature.getId(), defenderId, game);
                        }
                    }
                }
            }
        }
    }

    protected void checkAttackRestrictions(Player player, Game game) {
        int count = 0;
        for (CombatGroup group: groups) {
            count += group.getAttackers().size();
        }

        if (count > 1) {
            List<UUID> tobeRemoved = new ArrayList<UUID>();
            for (CombatGroup group: groups) {
                for (UUID attackingCreatureId: group.getAttackers()) {
                    Permanent attacker = game.getPermanent(attackingCreatureId);
                    if (count >1 && attacker != null && attacker.getAbilities().containsKey(CanAttackOnlyAloneAbility.getInstance().getId())) {
                        game.informPlayers(attacker.getName() + " can only attack alone. Removing it from combat.");
                        tobeRemoved.add(attackingCreatureId);
                        count--;
                    }
                }
            }
            for (UUID attackingCreatureId : tobeRemoved) {
                this.removeAttacker(attackingCreatureId, game);
            }
        }

        if (count == 1) {
            List<UUID> tobeRemoved = new ArrayList<UUID>();
            for (CombatGroup group: groups) {
                for (UUID attackingCreatureId: group.getAttackers()) {
                    Permanent attacker = game.getPermanent(attackingCreatureId);
                    if (attacker != null && attacker.getAbilities().containsKey(CantAttackAloneAbility.getInstance().getId())) {
                        game.informPlayers(attacker.getName() + " can't attack alone. Removing it from combat.");
                        tobeRemoved.add(attackingCreatureId);
                    }
                }
            }
            for (UUID attackingCreatureId : tobeRemoved) {
                this.removeAttacker(attackingCreatureId, game);
            }

        }

    }

    public void selectBlockers(Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, attackerId, attackerId))) {

            // !! Attention: Changes to this block must be also done in card "OdricMaster Tactician".
            Player player = game.getPlayer(attackerId);
            //20101001 - 509.1c
            checkBlockRequirementsBefore(player, game);
            for (UUID defenderId : getPlayerDefenders(game)) {
                boolean choose = true;
                Player defender = game.getPlayer(defenderId);
                while (choose) {
                    game.getPlayer(defenderId).selectBlockers(game, defenderId);
                    if (game.isPaused() || game.isGameOver()) {
                        return;
                    }
                    if (!checkBlockRestrictions(game.getPlayer(defenderId), game)) {
                        // only human player can decide to do the block in another way
                        if (defender.isHuman()) {
                            continue;
                        }
                    }
                    
                    choose = !checkBlockRequirementsAfter(defender, defender, game);
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));
            }
            TraceUtil.traceCombatIfNeeded(game, this);
        }
    }

/**
 * Check the block restrictions
 * @param player
 * @param game
 * @return false - if block restrictions were not complied
 */
    public boolean checkBlockRestrictions(Player player, Game game) {
        int count = 0;
        boolean blockWasLegal = true;
        for (CombatGroup group: groups) {
            count += group.getBlockers().size();
        }
        for (CombatGroup group : groups) {
            blockWasLegal &= group.checkBlockRestrictions(game, count);
        }
        return blockWasLegal;
    }

    public void acceptBlockers(Game game) {
        for (CombatGroup group : groups) {
            group.acceptBlockers(game);
        }
    }

    public void resumeSelectBlockers(Game game) {
        //TODO: this isn't quite right - but will work fine for two-player games
        for (UUID defenderId : getPlayerDefenders(game)) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));
        }
    }

    public void checkBlockRequirementsBefore(Player player, Game game) {
        /*20101001 - 509.1c
         * 509.1c The defending player checks each creature he or she controls to see whether it's affected by
         * any requirements (effects that say a creature must block, or that it must block if some condition is met).
         * If the number of requirements that are being obeyed is fewer than the maximum possible number of
         * requirements that could be obeyed without disobeying any restrictions, the declaration of blockers is
         * illegal. If a creature can't block unless a player pays a cost, that player is not required to pay
         * that cost, even if blocking with that creature would increase the number of requirements being obeyed.
         *
         * Example: A player controls one creature that "blocks if able" and another creature with no abilities.
         * An effect states "Creatures can't be blocked except by two or more creatures." Having only the first
         * creature block violates the restriction. Having neither creature block fulfills the restriction but
         * not the requirement. Having both creatures block the same attacking creature fulfills both the restriction
         * and the requirement, so that's the only option.
         */
        for (Permanent creature : game.getBattlefield().getActivePermanents(filterBlockers, player.getId(), game)) {
            if (game.getOpponents(attackerId).contains(creature.getControllerId())) {
                for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, game).entrySet()) {
                    RequirementEffect effect = (RequirementEffect)entry.getKey();
                    if (effect.mustBlock(game)) {
                        for (Ability ability: (HashSet<Ability>)entry.getValue()) {
                            UUID attackId = effect.mustBlockAttacker(ability, game);
                            Player defender = game.getPlayer(creature.getControllerId());
                            if (attackId != null && defender != null) {
                                if (creaturesForcedToBlockAttackers.containsKey(creature.getId())) {
                                    creaturesForcedToBlockAttackers.get(creature.getId()).add(attackId);
                                } else {
                                    Set<UUID> forcingAttackers = new HashSet<UUID>();
                                    forcingAttackers.add(attackId);
                                    creaturesForcedToBlockAttackers.put(creature.getId(), forcingAttackers);
                                    // assign block to the first forcing attacker automatically
                                    defender.declareBlocker(defender.getId(), creature.getId(), attackId, game);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkBlockRequirementsAfter(Player player, Player controller, Game game) {
        // Get one time a list of all opponents in range
        Set<UUID> opponents = game.getOpponents(attackerId);
        //20101001 - 509.1c
        // map with attackers (UUID) that must be blocked by at least one blocker and a set of all creatures that can block it and don't block yet
        Map<UUID, Set<UUID>> mustBeBlockedByAtLeastOne = new HashMap<UUID, Set<UUID>>();

        // check mustBlockAny
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), game)) {
            // creature is controlled by an opponent of the attacker
            if (opponents.contains(creature.getControllerId())) {

                // Creature is already blocking but not forced to do so
                if (creature.getBlocking() > 0) {
                    // get all requirement effects that apply to the creature (ce.g. is able to block attacker)
                    for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, game).entrySet()) {
                        RequirementEffect effect = (RequirementEffect)entry.getKey();
                        // get possible mustBeBlockedByAtLeastOne blocker
                        for (Ability ability: (HashSet<Ability>)entry.getValue()) {
                            UUID toBeBlockedCreature = effect.mustBlockAttackerIfElseUnblocked(ability, game);
                            if (toBeBlockedCreature != null) {
                                Set<UUID> potentialBlockers;
                                if (mustBeBlockedByAtLeastOne.containsKey(toBeBlockedCreature)) {
                                    potentialBlockers = mustBeBlockedByAtLeastOne.get(toBeBlockedCreature);
                                } else {
                                    potentialBlockers = new HashSet<UUID>();
                                    mustBeBlockedByAtLeastOne.put(toBeBlockedCreature, potentialBlockers);
                                }
                                potentialBlockers.add(creature.getId());
                            }
                        }
                    }
                }
                
                // Creature is not blocking yet
                if (creature.getBlocking() == 0) {
                    // get all requirement effects that apply to the creature
                    for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, game).entrySet()) {
                        RequirementEffect effect = (RequirementEffect)entry.getKey();
                        // get possible mustBeBlockedByAtLeastOne blocker
                        for (Ability ability: (HashSet<Ability>)entry.getValue()) {
                            UUID toBeBlockedCreature = effect.mustBlockAttackerIfElseUnblocked(ability, game);
                            if (toBeBlockedCreature != null) {
                                Set<UUID> potentialBlockers;
                                if (mustBeBlockedByAtLeastOne.containsKey(toBeBlockedCreature)) {
                                    potentialBlockers = mustBeBlockedByAtLeastOne.get(toBeBlockedCreature);
                                } else {
                                    potentialBlockers = new HashSet<UUID>();
                                    mustBeBlockedByAtLeastOne.put(toBeBlockedCreature, potentialBlockers);
                                }
                                potentialBlockers.add(creature.getId());
                            }
                        }

                        // check the mustBlockAny requirement ----------------------------------------
                        if (effect.mustBlockAny(game)) {
                            // check that it can block at least one of the attackers
                            boolean mayBlock = false;
                            for (UUID attackingCreatureId : getAttackers()) {
                                if (creature.canBlock(attackingCreatureId, game)) {
                                    mayBlock = true;
                                    break;
                                }
                            }
                            // is so inform human player or set block for AI player
                            if (mayBlock) {
                                if (controller.isHuman()) {
                                    game.informPlayer(controller, "Creature should block this turn: " + creature.getName());
                                } else {
                                    Player defender = game.getPlayer(creature.getControllerId());
                                    if (defender != null) {
                                        for (UUID attackingCreatureId : getAttackers()) {
                                            if (creature.canBlock(attackingCreatureId, game)) {
                                                defender.declareBlocker(defender.getId(), creature.getId(), attackingCreatureId, game);
                                                break;
                                            }
                                        }
                                    }
                                }
                                return false;
                            }
                        }

                    }
                }

            }

        }

        // check attacking creature mustBeBlockedByAtLeastOne
        for (UUID toBeBlockedCreatureId: mustBeBlockedByAtLeastOne.keySet()) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(toBeBlockedCreatureId)) {
                    // creature is not blocked but has possible blockers
                    if (controller.isHuman()) {
                        Permanent toBeBlockedCreature = game.getPermanent(toBeBlockedCreatureId);
                        if (toBeBlockedCreature != null) {
                            // check if all possible blocker block other creatures they are forced to block
                            // read through all possible blockers
                            boolean possibleBlockerAvailable = false;
                            for (UUID possibleBlockerId : mustBeBlockedByAtLeastOne.get(toBeBlockedCreatureId)) {
                                Set<UUID> forcingAttackers = creaturesForcedToBlockAttackers.get(possibleBlockerId);
                                if (forcingAttackers == null) {
                                    // no other creature forces the blocker to block -> it's available
                                    possibleBlockerAvailable = true;
                                    break;
                                }
                                // get the attackers he blocks
                                List<UUID> blockedAttackers = null;
                                for (CombatGroup combatGroupToCheck : game.getCombat().getGroups()) {
                                    if (combatGroupToCheck.getBlockers().contains(possibleBlockerId)) {
                                        blockedAttackers = combatGroupToCheck.getAttackers();
                                        break;
                                    }
                                }
                                if (blockedAttackers == null) {
                                    // he blocks no other creature -> it's available
                                    possibleBlockerAvailable = true;
                                    break;
                                }

                                // get attackers forcing the possible blocker to block
                                possibleBlockerAvailable = true;
                                for (UUID blockedAttackerId : blockedAttackers) {
                                    if (creaturesForcedToBlockAttackers.get(possibleBlockerId).contains(blockedAttackerId)) {
                                        possibleBlockerAvailable = false;
                                        break;
                                    }
                                }
                                if (possibleBlockerAvailable) {
                                    break;
                                }
                            }
                            if (possibleBlockerAvailable) {
                                game.informPlayer(controller, new StringBuilder(toBeBlockedCreature.getName()).append(" has to be blocked by at least one creature.").toString());
                                return false;
                            }
                        }

                    } else {
                        // take the first potential blocker from the set to block for the AI
                        UUID blockingCreatureId = mustBeBlockedByAtLeastOne.get(toBeBlockedCreatureId).iterator().next();
                        Permanent blockingCreature = game.getPermanent(blockingCreatureId);
                        if (blockingCreature != null) {
                            Player defender = game.getPlayer(blockingCreature.getControllerId());
                            if (defender != null) {
                                defender.declareBlocker(defender.getId(), blockingCreatureId, toBeBlockedCreatureId, game);
                            }
                        }
                    }
                }
            }

        }
        // check if creatures are forced to block but do not block at all or block creatures they are not forced to block
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<UUID, Set<UUID>> entry :creaturesForcedToBlockAttackers.entrySet()) {
            boolean blockIsValid;
            Permanent creatureForcedToBlock = game.getPermanent(entry.getKey());
            // creature does not block -> not allowed
            if (creatureForcedToBlock.getBlocking() == 0) {
                blockIsValid = false;
            } else {
                blockIsValid = false;
                // which attacker is he blocking
                CombatGroups:
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().contains(creatureForcedToBlock.getId())) {
                        for (UUID forcingAttackerId :combatGroup.getAttackers()) {
                            if (entry.getValue().contains(forcingAttackerId)) {
                                // the creature is blocking a forcing attacker, so the block is ok
                                blockIsValid = true;
                                break CombatGroups;
                            } else {
                                // check if the blocker blocks a attacker that must be blocked at least by one and is the only blocker, this block is also valid
                                if (combatGroup.getBlockers().size() == 1) {
                                    if (mustBeBlockedByAtLeastOne.containsKey(forcingAttackerId)) {
                                        if (mustBeBlockedByAtLeastOne.get(forcingAttackerId).contains(creatureForcedToBlock.getId())) {
                                            blockIsValid = true;
                                            break CombatGroups;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            if (!blockIsValid) {
                sb.append(" ").append(creatureForcedToBlock.getName());
            }
        }
        if (sb.length() > 0) {
            sb.insert(0, "Some creatures are forced to block certain attacker(s):\n");
            sb.append("\nPlease block with each of these creatures an appropriate attacker.");
            game.informPlayer(controller, sb.toString());
            return false;
        }
        return true;
    }

    public void setDefenders(Game game) {
        Set<UUID> opponents = game.getOpponents(attackerId);
        PlayerList players;
        switch (game.getAttackOption()) {
            case LEFT:
                players = game.getState().getPlayerList(attackerId);
                while (true) {
                    Player opponent = players.getNext(game);
                    if (opponents.contains(opponent.getId())) {
                        addDefender(opponent.getId(), game);
                        break;
                    }
                }
                break;
            case RIGHT:
                players = game.getState().getPlayerList(attackerId);
                while (true) {
                    Player opponent = players.getPrevious(game);
                    if (opponents.contains(opponent.getId())) {
                        addDefender(opponent.getId(), game);
                        break;
                    }
                }
                break;
            case MULTIPLE:
                for (UUID opponentId : game.getOpponents(attackerId)) {
                    addDefender(opponentId, game);
                }
                break;
        }
    }

    private void addDefender(UUID defenderId, Game game) {
        defenders.add(defenderId);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterPlaneswalker, defenderId, game)) {
            defenders.add(permanent.getId());
        }
    }

    public void declareAttacker(UUID attackerId, UUID defenderId, Game game) {
        if (!defenders.contains(defenderId)) {
            return;
        }
        Permanent defender = game.getPermanent(defenderId);
        CombatGroup newGroup = new CombatGroup(defenderId, defender != null, defender != null ? defender.getControllerId(): defenderId);
        newGroup.attackers.add(attackerId);
        Permanent attacker = game.getPermanent(attackerId);
        if (!attacker.getAbilities().containsKey(VigilanceAbility.getInstance().getId())) {
            attacker.tap(game);
        }
        attacker.setAttacking(true);
        groups.add(newGroup);
    }

    // add blocking group for creatures that block more than one creature
    public void addBlockingGroup(UUID blockerId, UUID attackerId, UUID playerId, Game game) {
        Permanent blocker = game.getPermanent(blockerId);
        if (blockerId != null && blocker != null && blocker.getBlocking() > 1) {
            if (!blockingGroups.containsKey(blockerId)) {
                CombatGroup newGroup = new CombatGroup(playerId, false, playerId);
                newGroup.blockers.add(blockerId);
                // add all blocked attackers
                for (CombatGroup group : groups) {
                    if (group.getBlockers().contains(blockerId)) {
                        // take into account banding
                        for (UUID attacker : group.attackers) {
                            newGroup.attackers.add(attacker);
                        }
                    }
                }
                blockingGroups.put(blockerId, newGroup);
            } else {
                //TODO: handle banding
                blockingGroups.get(blockerId).attackers.add(attackerId);
            }
        }
    }

    public void removeFromCombat(UUID creatureId, Game game) {
        Permanent creature = game.getPermanent(creatureId);
        if (creature != null) {
            creature.setAttacking(false);
            creature.setBlocking(0);
            for (CombatGroup group : groups) {
                group.remove(creatureId);
            }
        }
    }

    public void endCombat(Game game) {
        Permanent creature;
        for (CombatGroup group : groups) {
            for (UUID attacker : group.attackers) {
                creature = game.getPermanent(attacker);
                if (creature != null) {
                    creature.setAttacking(false);
                    creature.setBlocking(0);
                }
            }
            for (UUID blocker : group.blockers) {
                creature = game.getPermanent(blocker);
                if (creature != null) {
                    creature.setAttacking(false);
                    creature.setBlocking(0);
                }
            }
        }
        clear();
    }

    public boolean hasFirstOrDoubleStrike(Game game) {
        for (CombatGroup group : groups) {
            if (group.hasFirstOrDoubleStrike(game)) {
                return true;
            }
        }
        return false;
    }

    public CombatGroup findGroup(UUID attackerId) {
        for (CombatGroup group : groups) {
            if (group.getAttackers().contains(attackerId)) {
                return group;
            }
        }
        return null;
    }

    public int totalUnblockedDamage(Game game) {
        int total = 0;
        for (CombatGroup group : groups) {
            if (group.getBlockers().isEmpty()) {
                total += group.totalAttackerDamage(game);
            }
        }
        return total;
    }

    public boolean attacksAlone() {
        return (groups.size() == 1 && groups.get(0).getAttackers().size() == 1);
    }

    public boolean noAttackers() {
        if (groups.isEmpty() || getAttackers().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isAttacked(UUID defenderId, Game game) {
        for (CombatGroup group : groups) {
            if (group.getDefenderId().equals(defenderId)) {
                return true;
            }
            if (group.defenderIsPlaneswalker) {
                Permanent permanent = game.getPermanent(group.getDefenderId());
                if (permanent.getControllerId().equals(defenderId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param attackerId
     * @return uuid of defending player or planeswalker
     */
    public UUID getDefenderId(UUID attackerId) {
        UUID defenderId = null;
        for (CombatGroup group : groups) {
            if (group.getAttackers().contains(attackerId)) {
                defenderId = group.getDefenderId();
                break;
            }
        }
        return defenderId;
    }

    public UUID getDefendingPlayerId(UUID attackerId, Game game) {
        UUID defenderId = null;
        for (CombatGroup group : groups) {
            if (group.getAttackers().contains(attackerId)) {
                defenderId = group.getDefenderId();
                if (group.defenderIsPlaneswalker) {
                    Permanent permanent = game.getPermanent(defenderId);
                    if (permanent != null) {
                        defenderId = permanent.getControllerId();
                    } else {
                        defenderId = null;
                    }
                }
                break;
            }
        }
        return defenderId;
    }

    public Set<UUID> getPlayerDefenders(Game game) {
        Set<UUID> playerDefenders = new HashSet<UUID>();
        for (CombatGroup group : groups) {
            if (group.defenderIsPlaneswalker) {
                Permanent permanent = game.getPermanent(group.getDefenderId());
                if (permanent != null) {
                    playerDefenders.add(permanent.getControllerId());
                }
            } else {
                playerDefenders.add(group.getDefenderId());
            }
        }
        return playerDefenders;
    }

    public void damageAssignmentOrder(Game game) {
        for (CombatGroup group : groups) {
            group.pickBlockerOrder(attackerId, game);
        }
        for (Map.Entry<UUID, CombatGroup> blockingGroup : blockingGroups.entrySet()) {
            Permanent blocker = game.getPermanent(blockingGroup.getKey());
            if (blocker != null) {
                blockingGroup.getValue().pickAttackerOrder(blocker.getControllerId(), game);
            }
        }
    }

    public void removeAttacker(UUID attackerId, Game game) {
        for (CombatGroup group : groups) {
            if (group.attackers.contains(attackerId)) {
                group.attackers.remove(attackerId);
                group.attackerOrder.remove(attackerId);
                Permanent creature = game.getPermanent(attackerId);
                if (creature != null) {
                    creature.setAttacking(false);
                    creature.setTapped(false);
                }
                if (group.attackers.isEmpty()) {
                    groups.remove(group);
                }
                return;
            }
        }
    }

    public void removeBlocker(UUID blockerId, Game game) {
        for (CombatGroup group : groups) {
            if (group.blockers.contains(blockerId)) {
                group.blockers.remove(blockerId);
                group.blockerOrder.remove(blockerId);
                if (group.blockers.isEmpty()) {
                    group.blocked = false;
                }
            }
        }
        Permanent creature = game.getPermanent(blockerId);
        if (creature != null) {
            creature.setBlocking(0);
        }
    }

    public UUID getAttackerId() {
        return attackerId;
    }

    @Override
    public Combat copy() {
        return new Combat(this);
    }

}
