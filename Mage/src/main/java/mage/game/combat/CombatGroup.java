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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.Copyable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroup implements Serializable, Copyable<CombatGroup> {

    protected List<UUID> attackers = new ArrayList<>();
    protected List<UUID> blockers = new ArrayList<>();
    protected List<UUID> blockerOrder = new ArrayList<>();
    protected List<UUID> attackerOrder = new ArrayList<>();
    protected Map<UUID, UUID> players = new HashMap<>();
    protected boolean blocked;
    protected UUID defenderId; // planeswalker or player
    protected UUID defendingPlayerId;
    protected boolean defenderIsPlaneswalker;

    /**
     * @param defenderId the player that controls the defending permanents
     * @param defenderIsPlaneswalker is the defending permanent a planeswalker
     * @param defendingPlayerId regular controller of the defending permanents
     */
    public CombatGroup(UUID defenderId, boolean defenderIsPlaneswalker, UUID defendingPlayerId) {
        this.defenderId = defenderId;
        this.defenderIsPlaneswalker = defenderIsPlaneswalker;
        this.defendingPlayerId = defendingPlayerId;
    }

    public CombatGroup(final CombatGroup group) {
        this.attackers.addAll(group.attackers);
        this.blockers.addAll(group.blockers);
        this.blockerOrder.addAll(group.blockerOrder);
        this.attackerOrder.addAll(group.attackerOrder);
        this.players.putAll(group.players);
        this.blocked = group.blocked;
        this.defenderId = group.defenderId;
        this.defendingPlayerId = group.defendingPlayerId;
        this.defenderIsPlaneswalker = group.defenderIsPlaneswalker;
    }

    public boolean hasFirstOrDoubleStrike(Game game) {
        for (UUID permId : attackers) {
            Permanent attacker = game.getPermanent(permId);
            if (attacker != null && hasFirstOrDoubleStrike(attacker)) {
                return true;
            }
        }
        for (UUID permId : blockers) {
            Permanent blocker = game.getPermanent(permId);
            if (blocker != null && hasFirstOrDoubleStrike(blocker)) {
                return true;
            }
        }
        return false;
    }

    public UUID getDefenderId() {
        return defenderId;
    }

    public List<UUID> getAttackers() {
        return attackers;
    }

    public List<UUID> getBlockers() {
        return blockers;
    }

    public List<UUID> getBlockerOrder() {
        return blockerOrder;
    }

    private boolean hasFirstOrDoubleStrike(Permanent perm) {
        return perm.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) || perm.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
    }

    private boolean hasFirstStrike(Permanent perm) {
        return perm.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId());
    }

    private boolean hasDoubleStrike(Permanent perm) {
        return perm.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
    }

    private boolean hasTrample(Permanent perm) {
        return perm.getAbilities().containsKey(TrampleAbility.getInstance().getId());
    }

    public void assignDamageToBlockers(boolean first, Game game) {
        if (!attackers.isEmpty() && (!first || hasFirstOrDoubleStrike(game))) {
            if (blockers.isEmpty()) {
                unblockedDamage(first, game);
            } else {
                Permanent attacker = game.getPermanent(attackers.get(0));
                if (attacker.getAbilities().containsKey(DamageAsThoughNotBlockedAbility.getInstance().getId())) {
                    Player player = game.getPlayer(attacker.getControllerId());
                    if (player.chooseUse(Outcome.Damage, "Do you wish to assign damage for " + attacker.getLogName() + " as though it weren't blocked?", null, game)) {
                        blocked = false;
                        unblockedDamage(first, game);
                    }
                }
                if (blockers.size() == 1) {
                    singleBlockerDamage(first, game);
                } else {
                    multiBlockerDamage(first, game);
                }
            }
        }
    }

    public void assignDamageToAttackers(boolean first, Game game) {
        if (!blockers.isEmpty() && (!first || hasFirstOrDoubleStrike(game))) {
            if (attackers.size() == 1) {
                singleAttackerDamage(first, game);
            } else {
                multiAttackerDamage(first, game);
            }
        }
    }

    public void applyDamage(Game game) {
        for (UUID uuid : attackers) {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                permanent.applyDamage(game);
            }
        }
        for (UUID uuid : blockers) {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                permanent.applyDamage(game);
            }
        }
    }

    /**
     * Determines if permanent can damage in current (First Strike or not)
     * combat damage step
     *
     * @param perm Permanent to check
     * @param first First strike or common combat damage step
     * @return
     */
    private boolean canDamage(Permanent perm, boolean first) {
        if (perm == null) {
            return false;
        }
        // if now first strike combat damage step
        if (first) {
            // should have first strike or double strike
            return hasFirstOrDoubleStrike(perm);
        } // if now not first strike combat
        else {
            if (hasFirstStrike(perm)) {
                // if it has first strike in non FS combat damage step
                // then it can damage only if it has ALSO double strike
                // Fixes Issue 200
                return hasDoubleStrike(perm);
            }
            // can damage otherwise
            return true;
        }
    }

    private void unblockedDamage(boolean first, Game game) {
        for (UUID attackerId : attackers) {
            Permanent attacker = game.getPermanent(attackerId);
            if (canDamage(attacker, first)) {
                //20091005 - 510.1c, 702.17c
                if (!blocked || hasTrample(attacker)) {
                    defenderDamage(attacker, getDamageValueFromPermanent(attacker, game), game);
                }
            }
        }
    }

    private void singleBlockerDamage(boolean first, Game game) {
        Permanent blocker = game.getPermanent(blockers.get(0));
        Permanent attacker = game.getPermanent(attackers.get(0));
        int blockerDamage = 0;
        if (blocker != null && attacker != null) {
            for(UUID attackerId : attackers){
                attacker = game.getPermanent(attackerId);
                blockerDamage = getDamageValueFromPermanent(blocker, game); // must be set before attacker damage marking because of effects like Test of Faith
                if (blocked && canDamage(attacker, first)) {
                    int damage = getDamageValueFromPermanent(attacker, game);
                    if (hasTrample(attacker)) {
                        int lethalDamage;
                        if (attacker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
                            lethalDamage = 1;
                        } else {
                            lethalDamage = blocker.getToughness().getValue() - blocker.getDamage();
                        }
                        if (lethalDamage >= damage) {
                            blocker.markDamage(damage, attacker.getId(), game, true, true);
                        } else {
                            Player player = game.getPlayer(attacker.getControllerId());
                            int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
                            blocker.markDamage(damageAssigned, attacker.getId(), game, true, true);
                            damage -= damageAssigned;
                            if (damage > 0) {
                                defenderDamage(attacker, damage, game);
                            }
                        }
                    } else {
                        blocker.markDamage(damage, attacker.getId(), game, true, true);
                    }
                }
            } //end of for
            if (canDamage(blocker, first)) {
                if (blocker.getBlocking() == 1) { // blocking several creatures handled separately
                    attacker.markDamage(blockerDamage, blocker.getId(), game, true, true);
                }
            }
        }   
    }

    private void multiBlockerDamage(boolean first, Game game) {
        //TODO:  handle banding
        Permanent attacker = game.getPermanent(attackers.get(0));
        if (attacker == null) {
            return;
        }
        Player player = game.getPlayer(attacker.getControllerId());
        int damage = getDamageValueFromPermanent(attacker, game);
        if (canDamage(attacker, first)) {
            // must be set before attacker damage marking because of effects like Test of Faith
            Map<UUID, Integer> blockerPower = new HashMap<>();
            for (UUID blockerId : blockerOrder) {
                Permanent blocker = game.getPermanent(blockerId);
                if (canDamage(blocker, first)) {
                    if (blocker.getBlocking() == 1) { // blocking several creatures handled separately
                        blockerPower.put(blockerId, getDamageValueFromPermanent(blocker, game));
                    }
                }
            }
            Map<UUID, Integer> assigned = new HashMap<>();
            if (blocked) {
                for (UUID blockerId : blockerOrder) {
                    Permanent blocker = game.getPermanent(blockerId);
                    int lethalDamage;
                    if (attacker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
                        lethalDamage = 1;
                    } else {
                        lethalDamage = blocker.getToughness().getValue() - blocker.getDamage();
                    }
                    if (lethalDamage >= damage) {
                        assigned.put(blockerId, damage);
                        damage = 0;
                        break;
                    }
                    int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
                    assigned.put(blockerId, damageAssigned);
                    damage -= damageAssigned;
                }
                if (damage > 0 && hasTrample(attacker)) {
                    defenderDamage(attacker, damage, game);
                } else if (!blockerOrder.isEmpty()) {
                    // Assign the damge left to first blocker
                    assigned.put(blockerOrder.get(0), assigned.get(blockerOrder.get(0)) + damage);
                }
            }
            for (UUID blockerId : blockerOrder) {
                Integer power = blockerPower.get(blockerId);
                if (power != null) {
                    attacker.markDamage(power, blockerId, game, true, true);
                }
            }
            for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
                Permanent blocker = game.getPermanent(entry.getKey());
                blocker.markDamage(entry.getValue(), attacker.getId(), game, true, true);
            }
        } else {
            for (UUID blockerId : blockerOrder) {
                Permanent blocker = game.getPermanent(blockerId);
                if (canDamage(blocker, first)) {
                    attacker.markDamage(getDamageValueFromPermanent(blocker, game), blocker.getId(), game, true, true);
                }
            }
        }
    }

    /**
     * Damages attacking creatures by a creature that blocked several ones
     * Damages only attackers as blocker was damage in
     * {@link #singleBlockerDamage}.
     *
     * Handles abilities like "{this} an block any number of creatures.".
     *
     * @param first
     * @param game
     */
    private void singleAttackerDamage(boolean first, Game game) {
        Permanent blocker = game.getPermanent(blockers.get(0));
        Permanent attacker = game.getPermanent(attackers.get(0));
        if (blocker != null && attacker != null) {
            if (canDamage(blocker, first)) {
                int damage = getDamageValueFromPermanent(blocker, game);
                attacker.markDamage(damage, blocker.getId(), game, true, true);
            }
        }
    }

    /**
     * Damages attacking creatures by a creature that blocked several ones
     * Damages only attackers as blocker was damage in either
     * {@link #singleBlockerDamage} or {@link #multiBlockerDamage}.
     *
     * Handles abilities like "{this} an block any number of creatures.".
     *
     * @param first
     * @param game
     */
    private void multiAttackerDamage(boolean first, Game game) {
        Permanent blocker = game.getPermanent(blockers.get(0));
        if (blocker == null) {
            return;
        }
        Player player = game.getPlayer(blocker.getControllerId());
        int damage = getDamageValueFromPermanent(blocker, game);

        if (canDamage(blocker, first)) {
            Map<UUID, Integer> assigned = new HashMap<>();
            for (UUID attackerId : attackerOrder) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    int lethalDamage;
                    if (blocker.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
                        lethalDamage = 1;
                    } else {
                        lethalDamage = attacker.getToughness().getValue() - attacker.getDamage();
                    }
                    if (lethalDamage >= damage) {
                        assigned.put(attackerId, damage);
                        break;
                    }
                    int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + attacker.getName(), game);
                    assigned.put(attackerId, damageAssigned);
                    damage -= damageAssigned;
                }
            }

            for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
                Permanent attacker = game.getPermanent(entry.getKey());
                attacker.markDamage(entry.getValue(), blocker.getId(), game, true, true);
            }
        }
    }

    private void defenderDamage(Permanent attacker, int amount, Game game) {
        if (this.defenderIsPlaneswalker) {
            Permanent defender = game.getPermanent(defenderId);
            if (defender != null) {
                defender.markDamage(amount, attacker.getId(), game, true, true);
            }
        } else {
            Player defender = game.getPlayer(defenderId);
            if (defender.isInGame()) {
                defender.damage(amount, attacker.getId(), game, true, true);
            }
        }
    }

    public boolean canBlock(Permanent blocker, Game game) {
        // player can't block if another player is attacked
        if (!defendingPlayerId.equals(blocker.getControllerId())) {
            return false;
        }
        for (UUID attackerId : attackers) {
            if (!blocker.canBlock(attackerId, game)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param blockerId
     * @param playerId controller of the blocking creature
     * @param game
     */
    public void addBlocker(UUID blockerId, UUID playerId, Game game) {
        for (UUID attackerId : attackers) {
            if (game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKER, attackerId, blockerId, playerId))) {
                return;
            }
        }
        addBlockerToGroup(blockerId, playerId, game);
    }

    /**
     * Adds a blocker to a combat group without creating a DECLARE_BLOCKER
     * event.
     *
     * @param blockerId
     * @param playerId controller of the blocking creature
     * @param game
     */
    public void addBlockerToGroup(UUID blockerId, UUID playerId, Game game) {
        Permanent blocker = game.getPermanent(blockerId);
        if (blockerId != null && blocker != null) {
            blocker.setBlocking(blocker.getBlocking() + 1);
            blockers.add(blockerId);
            blockerOrder.add(blockerId);
            this.blocked = true;
            this.players.put(blockerId, playerId);
        }
    }

    public void pickBlockerOrder(UUID playerId, Game game) {
        if (blockers.isEmpty()) {
            return;
        }
        Player player = game.getPlayer(playerId);
        List<UUID> blockerList = new ArrayList<>(blockers);
        blockerOrder.clear();
        while (player.canRespond()) {
            if (blockerList.size() == 1) {
                blockerOrder.add(blockerList.get(0));
                break;
            } else {
                List<Permanent> blockerPerms = new ArrayList<>();
                for (UUID blockerId : blockerList) {
                    blockerPerms.add(game.getPermanent(blockerId));
                }
                UUID blockerId = player.chooseBlockerOrder(blockerPerms, this, blockerOrder, game);
                blockerOrder.add(blockerId);
                blockerList.remove(blockerId);
            }
        }
    }

    public void pickAttackerOrder(UUID playerId, Game game) {
        if (attackers.isEmpty()) {
            return;
        }
        Player player = game.getPlayer(playerId);
        List<UUID> attackerList = new ArrayList<>(attackers);
        attackerOrder.clear();
        while (true) {
            if (attackerList.size() == 1) {
                attackerOrder.add(attackerList.get(0));
                break;
            } else {
                List<Permanent> attackerPerms = new ArrayList<>();
                for (UUID attackerId : attackerList) {
                    attackerPerms.add(game.getPermanent(attackerId));
                }
                UUID attackerId = player.chooseAttackerOrder(attackerPerms, game);
                if (!player.isInGame()) {
                    break;
                }
                attackerOrder.add(attackerId);
                attackerList.remove(attackerId);
            }
        }
    }

    public int totalAttackerDamage(Game game) {
        int total = 0;
        for (UUID attackerId : attackers) {
            total += getDamageValueFromPermanent(game.getPermanent(attackerId), game);
        }
        return total;
    }

    public boolean isDefenderIsPlaneswalker() {
        return defenderIsPlaneswalker;
    }

    public boolean removeAttackedPlaneswalker(UUID planeswalkerId) {
        if (defenderIsPlaneswalker && defenderId.equals(planeswalkerId)) {
            defenderId = null;
            return true;
        }
        return false;
    }

    public boolean remove(UUID creatureId) {
        boolean result = false;
        if (attackers.contains(creatureId)) {
            attackers.remove(creatureId);
            result = true;
        } else if (blockers.contains(creatureId)) {
            blockers.remove(creatureId);
            result = true;
            //20100423 - 509.2a
            if (blockerOrder.contains(creatureId)) {
                blockerOrder.remove(creatureId);
            }
        }
        return result;
    }

    public void acceptBlockers(Game game) {
        if (attackers.isEmpty()) {
            return;
        }
        for (UUID blockerId : blockers) {
            for (UUID attackerId : attackers) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BLOCKER_DECLARED, attackerId, blockerId, players.get(blockerId)));
            }
        }

        if (!blockers.isEmpty()) {
            for (UUID attackerId : attackers) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, attackerId, null));
            }
        }
    }

    public boolean checkBlockRestrictions(Game game, int blockersCount) {
        boolean blockWasLegal = true;
        if (attackers.isEmpty()) {
            return blockWasLegal;
        }
        if (blockersCount == 1) {
            List<UUID> toBeRemoved = new ArrayList<>();
            for (UUID blockerId : getBlockers()) {
                Permanent blocker = game.getPermanent(blockerId);
                if (blocker != null && blocker.getAbilities().containsKey(CantBlockAloneAbility.getInstance().getId())) {
                    blockWasLegal = false;
                    if (!game.isSimulation()) {
                        game.informPlayers(blocker.getLogName() + " can't block alone. Removing it from combat.");
                    }
                    toBeRemoved.add(blockerId);
                }
            }

            for (UUID blockerId : toBeRemoved) {
                game.getCombat().removeBlocker(blockerId, game);
            }
            if (blockers.isEmpty()) {
                this.blocked = false;
            }
        }

        for (UUID uuid : attackers) {
            Permanent attacker = game.getPermanent(uuid);
            // Check if there are enough blockers to have a legal block
            if (attacker != null && this.blocked && attacker.getMinBlockedBy() > 1 && !blockers.isEmpty() && blockers.size() < attacker.getMinBlockedBy()) {
                for (UUID blockerId : blockers) {
                    Permanent blocker = game.getPermanent(blockerId);
                    if (blocker != null) {
                        blocker.setBlocking(blocker.getBlocking() - 1);
                    }
                }
                blockers.clear();
                blockerOrder.clear();
                this.blocked = false;
                if (!game.isSimulation()) {
                    game.informPlayers(attacker.getLogName() + " can't be blocked except by " + attacker.getMinBlockedBy() + " or more creatures. Blockers discarded.");
                }
                blockWasLegal = false;
            }
            // Check if there are to many blockers (maxBlockedBy = 0 means no restrictions)
            if (attacker != null && this.blocked && attacker.getMaxBlockedBy() > 0 && attacker.getMaxBlockedBy() < blockers.size()) {
                for (UUID blockerId : blockers) {
                    Permanent blocker = game.getPermanent(blockerId);
                    if (blocker != null) {
                        blocker.setBlocking(blocker.getBlocking() - 1);
                    }
                }
                blockers.clear();
                blockerOrder.clear();
                this.blocked = false;
                if (!game.isSimulation()) {
                    game.informPlayers(new StringBuilder(attacker.getLogName())
                            .append(" can't be blocked by more than ").append(attacker.getMaxBlockedBy())
                            .append(attacker.getMaxBlockedBy() == 1 ? " creature." : " creatures.")
                            .append(" Blockers discarded.").toString());
                }
                blockWasLegal = false;
            }

        }
        return blockWasLegal;
    }

    /**
     * There are effects that let creatures assigns combat damage equal to its
     * toughness rather than its power. So this method takes this into account
     * to get the value of damage a creature will assign
     *
     * @param permanent
     * @param game
     * @return
     */
    private int getDamageValueFromPermanent(Permanent permanent, Game game) {
        if (game.getCombat().useToughnessForDamage(permanent, game)) {
            return permanent.getToughness().getValue();
        } else {
            return permanent.getPower().getValue();
        }
    }

    /**
     * There are effects, that set an attacker to be blcoked. Therefore this
     * setter can be used.
     *
     * @param blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public CombatGroup copy() {
        return new CombatGroup(this);
    }
}
