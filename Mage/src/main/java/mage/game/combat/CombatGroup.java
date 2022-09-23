package mage.game.combat;

import mage.abilities.Ability;
import mage.abilities.common.ControllerAssignCombatDamageToBlockersAbility;
import mage.abilities.common.ControllerDivideCombatDamageAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.keyword.*;
import mage.constants.AsThoughEffectType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.BlockerDeclaredEvent;
import mage.game.events.DeclareBlockerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
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
     * @param defenderId             the player that controls the defending permanents
     * @param defenderIsPlaneswalker is the defending permanent a planeswalker
     * @param defendingPlayerId      regular controller of the defending permanents
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
        return Stream.concat(attackers.stream(), blockers.stream())
                .map(id -> game.getPermanent(id))
                .filter(Objects::nonNull)
                .anyMatch(this::hasFirstOrDoubleStrike);

    }

    public UUID getDefenderId() {
        return defenderId;
    }

    public UUID getDefendingPlayerId() {
        return defendingPlayerId;
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

    private boolean hasTrampleOverPlaneswalkers(Permanent perm) {
        return perm.getAbilities().containsKey(TrampleOverPlaneswalkersAbility.getInstance().getId());
    }

    private boolean hasBanding(Permanent perm) {
        return perm.getAbilities().containsKey(BandingAbility.getInstance().getId());
    }

    private boolean appliesBandsWithOther(List<UUID> creatureIds, Game game) {
        for (UUID creatureId : creatureIds) {
            Permanent perm = game.getPermanent(creatureId);
            if (perm != null && perm.getBandedCards() != null) {
                for (Ability ab : perm.getAbilities()) {
                    if (ab.getClass().equals(BandsWithOtherAbility.class)) {
                        BandsWithOtherAbility ability = (BandsWithOtherAbility) ab;
                        if (ability.getSubtype() != null) {
                            if (perm.hasSubtype(ability.getSubtype(), game)) {
                                for (UUID bandedId : creatureIds) {
                                    if (!bandedId.equals(creatureId)) {
                                        Permanent banded = game.getPermanent(bandedId);
                                        if (banded != null && banded.hasSubtype(ability.getSubtype(), game)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        if (ability.getSupertype() != null) {
                            if (perm.getSuperType().contains(ability.getSupertype())) {
                                for (UUID bandedId : creatureIds) {
                                    if (!bandedId.equals(creatureId)) {
                                        Permanent banded = game.getPermanent(bandedId);
                                        if (banded != null && banded.getSuperType().contains(ability.getSupertype())) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        if (ability.getName() != null) {
                            if (perm.getName().equals(ability.getName())) {
                                for (UUID bandedId : creatureIds) {
                                    if (!bandedId.equals(creatureId)) {
                                        Permanent banded = game.getPermanent(bandedId);
                                        if (banded != null && banded.getName().equals(ability.getName())) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void assignDamageToBlockers(boolean first, Game game) {
        if (!attackers.isEmpty() && (!first || hasFirstOrDoubleStrike(game))) {
            Permanent attacker = game.getPermanent(attackers.get(0));
            if (attacker != null && !assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(attacker, attacker.getControllerId(), first, game, true)) {
                if (blockers.isEmpty()) {
                    unblockedDamage(first, game);
                    return;
                } else {
                    Player player = game.getPlayer(defenderAssignsCombatDamage(game) ? defendingPlayerId : attacker.getControllerId());
                    if ((attacker.getAbilities().containsKey(DamageAsThoughNotBlockedAbility.getInstance().getId()) &&
                            player.chooseUse(Outcome.Damage, "Have " + attacker.getLogName() + " assign damage as though it weren't blocked?", null, game)) ||
                            game.getContinuousEffects().asThough(attacker.getId(), AsThoughEffectType.DAMAGE_NOT_BLOCKED,
                                    null, attacker.getControllerId(), game) != null) {
                        // for handling creatures like Thorn Elemental
                        blocked = false;
                        unblockedDamage(first, game);
                    }
                    if (blockers.size() == 1) {
                        singleBlockerDamage(player, first, game);
                    } else {
                        multiBlockerDamage(player, first, game);
                    }
                }
            }
        }
    }

    public void assignDamageToAttackers(boolean first, Game game) {
        if (!blockers.isEmpty() && (!first || hasFirstOrDoubleStrike(game))) {
            // this should only come up if Butcher Orgg is granted the ability to block multiple blockers
            for (UUID blockerId : blockers) {
                Permanent blocker = game.getPermanent(blockerId);
                if (assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                    return;
                }
            }
            if (attackers.size() != 1) {
                multiAttackerDamage(first, game);
                // } else {
                // singleAttackerDamage(first, game);
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
        if (defenderIsPlaneswalker) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                permanent.applyDamage(game);
            }
        }
    }

    /**
     * Determines if permanent can damage in current (First Strike or not)
     * combat damage step
     *
     * @param perm  Permanent to check
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
                    defenderDamage(attacker, getDamageValueFromPermanent(attacker, game), game, false);
                }
            }
        }
    }

    private void singleBlockerDamage(Player player, boolean first, Game game) {
        Permanent blocker = game.getPermanent(blockers.get(0));
        Permanent attacker = game.getPermanent(attackers.get(0));
        if (blocker != null && attacker != null) {
            int blockerDamage = getDamageValueFromPermanent(blocker, game); // must be set before attacker damage marking because of effects like Test of Faith
            if (blocked && canDamage(attacker, first)) {
                int damage = getDamageValueFromPermanent(attacker, game);
                if (hasTrample(attacker)) {
                    int lethalDamage = getLethalDamage(blocker, attacker, game);
                    if (lethalDamage >= damage) {
                        blocker.markDamage(damage, attacker.getId(), null, game, true, true);
                    } else {
                        int damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
                        blocker.markDamage(damageAssigned, attacker.getId(), null, game, true, true);
                        damage -= damageAssigned;
                        if (damage > 0) {
                            defenderDamage(attacker, damage, game, false);
                        }
                    }
                } else {
                    blocker.markDamage(damage, attacker.getId(), null, game, true, true);
                }
            }
            if (canDamage(blocker, first)) {
                if (checkSoleBlockerAfter(blocker, game)) { // blocking several creatures handled separately
                    if (!assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                        attacker.markDamage(blockerDamage, blocker.getId(), null, game, true, true);
                    }
                }
            }
        }
    }

    private void multiBlockerDamage(Player player, boolean first, Game game) {
        Permanent attacker = game.getPermanent(attackers.get(0));
        if (attacker == null) {
            return;
        }
        boolean oldRuleDamage = (Objects.equals(player.getId(), defendingPlayerId));
        int damage = getDamageValueFromPermanent(attacker, game);
        if (canDamage(attacker, first)) {
            // must be set before attacker damage marking because of effects like Test of Faith
            Map<UUID, Integer> blockerPower = new HashMap<>();
            for (UUID blockerId : blockerOrder) {
                Permanent blocker = game.getPermanent(blockerId);
                if (canDamage(blocker, first)) {
                    if (checkSoleBlockerAfter(blocker, game)) { // blocking several creatures handled separately
                        blockerPower.put(blockerId, getDamageValueFromPermanent(blocker, game));
                    }
                }
            }
            Map<UUID, Integer> assigned = new HashMap<>();
            if (blocked) {
                boolean excessDamageToDefender = true;
                for (UUID blockerId : new ArrayList<>(blockerOrder)) { // prevent ConcurrentModificationException
                    Permanent blocker = game.getPermanent(blockerId);
                    if (blocker != null) {
                        int lethalDamage = getLethalDamage(blocker, attacker, game);
                        if (lethalDamage >= damage) {
                            if (!oldRuleDamage) {
                                assigned.put(blockerId, damage);
                                damage = 0;
                                break;
                            } else if (damage == 0) {
                                break;
                            }
                        }
                        int damageAssigned = 0;
                        if (!oldRuleDamage) {
                            damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + blocker.getName(), game);
                        } else {
                            damageAssigned = player.getAmount(0, damage, "Assign damage to " + blocker.getName(), game);
                            if (damageAssigned < lethalDamage) {
                                excessDamageToDefender = false; // all blockers need to have lethal damage assigned before it can trample over to the defender
                            }
                        }
                        assigned.put(blockerId, damageAssigned);
                        damage -= damageAssigned;
                    }
                }
                if (damage > 0 && hasTrample(attacker) && excessDamageToDefender) {
                    defenderDamage(attacker, damage, game, false);
                } else if (!blockerOrder.isEmpty()) {
                    // Assign the damage left to first blocker
                    assigned.put(blockerOrder.get(0), assigned.get(blockerOrder.get(0)) == null ? 0 : assigned.get(blockerOrder.get(0)) + damage);
                }
            }
            for (UUID blockerId : blockerOrder) {
                Integer power = blockerPower.get(blockerId);
                if (power != null) {
                    // might be missing canDamage condition?
                    Permanent blocker = game.getPermanent(blockerId);
                    if (!assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                        attacker.markDamage(power, blockerId, null, game, true, true);
                    }
                }
            }
            for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
                Permanent blocker = game.getPermanent(entry.getKey());
                blocker.markDamage(entry.getValue(), attacker.getId(), null, game, true, true);
            }
        } else {
            for (UUID blockerId : blockerOrder) {
                Permanent blocker = game.getPermanent(blockerId);
                if (canDamage(blocker, first)) {
                    if (!assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                        attacker.markDamage(getDamageValueFromPermanent(blocker, game), blocker.getId(), null, game, true, true);
                    }
                }
            }
        }
    }

    private void defendingPlayerAndOrDefendingCreaturesDividedDamage(Permanent attacker, Player player, boolean first, Game game, boolean isAttacking) {
        // for handling Butcher Orgg
        if (!((blocked && blockers.isEmpty() && isAttacking) || (attackers.isEmpty() && !isAttacking))) {
            if (attacker == null) {
                return;
            }
            int damage = getDamageValueFromPermanent(attacker, game);
            if (canDamage(attacker, first)) {
                // must be set before attacker damage marking because of effects like Test of Faith
                Map<UUID, Integer> blockerPower = new HashMap<>();
                for (UUID blockerId : blockerOrder) {
                    Permanent blocker = game.getPermanent(blockerId);
                    if (canDamage(blocker, first)) {
                        if (checkSoleBlockerAfter(blocker, game)) { // blocking several creatures handled separately
                            blockerPower.put(blockerId, getDamageValueFromPermanent(blocker, game));
                        }
                    }
                }
                Map<UUID, Integer> assigned = new HashMap<>();
                for (Permanent defendingCreature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, defendingPlayerId, game)) {
                    if (defendingCreature != null) {
                        if (!(damage > 0)) {
                            break;
                        }
                        int damageAssigned = 0;
                        damageAssigned = player.getAmount(0, damage, "Assign damage to " + defendingCreature.getName(), game);
                        assigned.put(defendingCreature.getId(), damageAssigned);
                        damage -= damageAssigned;
                    }
                }
                if (damage > 0) {
                    Player defendingPlayer = game.getPlayer(defendingPlayerId);
                    if (defendingPlayer != null) {
                        defendingPlayer.damage(damage, attacker.getId(), null, game, true, true);
                    }
                }
                if (isAttacking) {
                    for (UUID blockerId : blockerOrder) {
                        Integer power = blockerPower.get(blockerId);
                        if (power != null) {
                            // might be missing canDamage condition?
                            Permanent blocker = game.getPermanent(blockerId);
                            if (!assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                                attacker.markDamage(power, blockerId, null, game, true, true);
                            }
                        }
                    }
                }
                for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
                    Permanent defendingCreature = game.getPermanent(entry.getKey());
                    defendingCreature.markDamage(entry.getValue(), attacker.getId(), null, game, true, true);
                }
            } else {
                if (isAttacking) {
                    for (UUID blockerId : blockerOrder) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (canDamage(blocker, first)) {
                            if (!assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(blocker, blocker.getControllerId(), first, game, false)) {
                                attacker.markDamage(getDamageValueFromPermanent(blocker, game), blocker.getId(), null, game, true, true);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkSoleBlockerAfter(Permanent blocker, Game game) {
        // this solves some corner cases (involving banding) when finding out whether a blocker is blocking alone or not
        if (blocker.getBlocking() == 1) {
            if (game.getCombat().blockingGroups.get(blocker.getId()) == null) {
                return true;
            } else {
                for (CombatGroup group : game.getCombat().getBlockingGroups()) {
                    if (group.blockers.contains(blocker.getId())) {
                        if (group.attackers.size() == 1) {
                            return true; // if blocker is blocking a band, this won't be true
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Damages attacking creatures by a creature that blocked several ones
     * Damages only attackers as blocker was damage in
     * {@link #singleBlockerDamage}.
     * <p>
     * Handles abilities like "{this} an block any number of creatures.".
     * <p>
     * Blocker damage for blockers blocking single creatures is handled in the
     * single/multi blocker methods, so this shouldn't be used anymore.
     *
     * @param first
     * @param game
     * @deprecated
     */
    @Deprecated
    private void singleAttackerDamage(boolean first, Game game) {
        Permanent blocker = game.getPermanent(blockers.get(0));
        Permanent attacker = game.getPermanent(attackers.get(0));
        if (blocker != null && attacker != null) {
            if (canDamage(blocker, first)) {
                int damage = getDamageValueFromPermanent(blocker, game);
                attacker.markDamage(damage, blocker.getId(), null, game, true, true);
            }
        }
    }

    /**
     * Damages attacking creatures by a creature that blocked several ones
     * Damages only attackers as blocker was damage in either
     * {@link #singleBlockerDamage} or {@link #multiBlockerDamage}.
     * <p>
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
        boolean oldRuleDamage = attackerAssignsCombatDamage(game); // handles banding
        Player player = game.getPlayer(oldRuleDamage ? game.getCombat().getAttackingPlayerId() : blocker.getControllerId());
        int damage = getDamageValueFromPermanent(blocker, game);

        if (canDamage(blocker, first)) {
            Map<UUID, Integer> assigned = new HashMap<>();
            for (UUID attackerId : attackerOrder) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    int lethalDamage = getLethalDamage(attacker, blocker, game);
                    if (lethalDamage >= damage) {
                        if (!oldRuleDamage) {
                            assigned.put(attackerId, damage);
                            damage = 0;
                            break;
                        } else if (damage == 0) {
                            break;
                        }
                    }
                    int damageAssigned = 0;
                    if (!oldRuleDamage) {
                        damageAssigned = player.getAmount(lethalDamage, damage, "Assign damage to " + attacker.getName(), game);
                    } else {
                        damageAssigned = player.getAmount(0, damage, "Assign damage to " + attacker.getName(), game);
                    }
                    assigned.put(attackerId, damageAssigned);
                    damage -= damageAssigned;
                }
            }
            if (damage > 0) {
                // Assign the damage left to first attacker
                assigned.put(attackerOrder.get(0), assigned.get(attackerOrder.get(0)) + damage);
            }

            for (Map.Entry<UUID, Integer> entry : assigned.entrySet()) {
                Permanent attacker = game.getPermanent(entry.getKey());
                attacker.markDamage(entry.getValue(), blocker.getId(), null, game, true, true);
            }
        }
    }
    /**
     * Do damage to attacked player or planeswalker
     * @param attacker
     * @param amount
     * @param game
     * @param damageToDefenderController excess damage to defender's controller (example: trample over planeswalker)
     */
    private void defenderDamage(Permanent attacker, int amount, Game game, boolean damageToDefenderController) {
        UUID affectedDefenderId = this.defenderId;
        if (damageToDefenderController) {
            affectedDefenderId = game.getControllerId(this.defenderId);
        }

        // on planeswalker
        Permanent planeswalker = game.getPermanent(affectedDefenderId);
        if (planeswalker != null) {
            // apply excess damage from "trample over planeswaslkers" ability (example: Thrasta, Tempest's Roar)
            if (hasTrampleOverPlaneswalkers(attacker)) {
                int lethalDamage = planeswalker.getLethalDamage(attacker.getId(), game);
                if (lethalDamage >= amount) {
                    // normal damage
                    planeswalker.markDamage(amount, attacker.getId(), null, game, true, true);
                } else {
                    // damage with excess (additional damage to planeswalker's controller)
                    planeswalker.markDamage(lethalDamage, attacker.getId(), null, game, true, true);
                    amount -= lethalDamage;
                    if (amount > 0) {
                        defenderDamage(attacker, amount, game, true);
                    }
                }
            } else {
                // normal damage
                planeswalker.markDamage(amount, attacker.getId(), null, game, true, true);
            }
        }

        // on player
        Player defender = game.getPlayer(affectedDefenderId);
        if (defender != null) {
            defender.damage(amount, attacker.getId(), null, game, true, true);
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
     * @param blockerId
     * @param playerId  controller of the blocking creature
     * @param game
     */
    public void addBlocker(UUID blockerId, UUID playerId, Game game) {
        for (UUID attackerId : attackers) {
            if (game.replaceEvent(new DeclareBlockerEvent(attackerId, blockerId, playerId))) {
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
     * @param playerId  controller of the blocking creature
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
        Player player = game.getPlayer(playerId); // game.getPlayer(defenderAssignsCombatDamage(game) ? defendingPlayerId : playerId); // this was incorrect because defenderAssignsCombatDamage might be false by the time damage is dealt
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
        if (!game.isSimulation() && blockerOrder.size() > 1) {
            logDamageAssignmentOrder("Creatures blocking ", attackers, blockerOrder, game);
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
                if (attackerId == null) {
                    break;
                }
                attackerOrder.add(attackerId);
                attackerList.remove(attackerId);
            }
        }
        if (!game.isSimulation() && attackerOrder.size() > 1) {
            logDamageAssignmentOrder("Creatures blocked by ", blockers, attackerOrder, game);
        }
    }

    private void logDamageAssignmentOrder(String prefix, List<UUID> assignedFor, List<UUID> assignedOrder, Game game) {
        StringBuilder sb = new StringBuilder(prefix);
        boolean first = true;
        for (UUID id : assignedFor) {
            Permanent perm = game.getPermanent(id);
            if (perm != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(perm.getLogName());
                first = false;
            }
        }
        sb.append(" are ordered: ");
        first = true;
        for (UUID id : assignedOrder) {
            Permanent perm = game.getPermanent(id);
            if (perm != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(perm.getLogName());
                first = false;
            }
        }
        game.informPlayers(sb.toString());
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
            attackerOrder.remove(creatureId);
        } else if (blockers.contains(creatureId)) {
            blockers.remove(creatureId);
            result = true;
            //20100423 - 509.2a
            blockerOrder.remove(creatureId);
        }
        return result;
    }

    public void acceptBlockers(Game game) {
        if (attackers.isEmpty()) {
            return;
        }
        for (UUID blockerId : blockers) {
            for (UUID attackerId : attackers) {
                game.fireEvent(new BlockerDeclaredEvent(attackerId, blockerId, players.get(blockerId)));
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
            if (attacker != null && this.blocked) {
                // Check if there are enough blockers to have a legal block
                if (attacker.getMinBlockedBy() > 1 && !blockers.isEmpty() && blockers.size() < attacker.getMinBlockedBy()) {
                    for (UUID blockerId : new ArrayList<>(blockers)) {
                        game.getCombat().removeBlocker(blockerId, game);
                    }
                    blockers.clear();
                    blockerOrder.clear();
                    if (!game.isSimulation()) {
                        game.informPlayers(attacker.getLogName() + " can't be blocked except by " + attacker.getMinBlockedBy() + " or more creatures. Blockers discarded.");
                    }
                    blockWasLegal = false;
                }
                // Check if there are too many blockers (maxBlockedBy = 0 means no restrictions)
                if (attacker.getMaxBlockedBy() > 0 && attacker.getMaxBlockedBy() < blockers.size()) {
                    for (UUID blockerId : new ArrayList<>(blockers)) {
                        game.getCombat().removeBlocker(blockerId, game);
                    }
                    blockers.clear();
                    blockerOrder.clear();
                    if (!game.isSimulation()) {
                        game.informPlayers(new StringBuilder(attacker.getLogName())
                                .append(" can't be blocked by more than ").append(attacker.getMaxBlockedBy())
                                .append(attacker.getMaxBlockedBy() == 1 ? " creature." : " creatures.")
                                .append(" Blockers discarded.").toString());
                    }
                    blockWasLegal = false;
                }
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

    public void setBlocked(boolean blocked, Game game) {
        this.blocked = blocked;
        for (UUID attackerId : attackers) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker != null) {
                for (UUID bandedId : attacker.getBandedCards()) {
                    if (!bandedId.equals(attackerId)) {
                        CombatGroup bandedGroup = game.getCombat().findGroup(bandedId);
                        if (bandedGroup != null) {
                            bandedGroup.blocked = blocked;
                        }
                    }
                }
            }
        }
    }

    public boolean getBlocked() {
        return blocked;
    }

    @Override
    public CombatGroup copy() {
        return new CombatGroup(this);
    }

    public boolean changeDefenderPostDeclaration(UUID newDefenderId, Game game) {
        if (!defenderId.equals(newDefenderId)) {
            for (UUID attackerId : attackers) { // changing defender will remove a banded attacker from its current band
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null && attacker.getBandedCards() != null) {
                    for (UUID bandedId : attacker.getBandedCards()) {
                        Permanent banded = game.getPermanent(bandedId);
                        if (banded != null) {
                            banded.removeBandedCard(attackerId);
                        }
                    }
                }
                attacker.clearBandedCards();
            }
            Permanent permanent = game.getPermanent(newDefenderId);
            if (permanent != null) {
                defenderId = newDefenderId;
                defendingPlayerId = permanent.getControllerId();
                defenderIsPlaneswalker = true;
                return true;
            } else {
                Player defender = game.getPlayer(newDefenderId);
                if (defender != null) {
                    defenderId = newDefenderId;
                    defendingPlayerId = newDefenderId;
                    defenderIsPlaneswalker = false;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Decides damage distribution for attacking banding creatures.
     *
     * @param game
     */
    public boolean attackerAssignsCombatDamage(Game game) {
        for (UUID attackerId : attackers) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker != null) {
                if (hasBanding(attacker)) { // 702.21k - only one attacker with banding necessary
                    return true;
                }
            }
        }
        // 702.21k - both a [quality] creature with “bands with other [quality]” and another [quality] creature (...)
        return appliesBandsWithOther(attackers, game);
    }

    /**
     * Decides damage distribution for blocking creatures with banding or if
     * defending player controls the Defensive Formation enchantment.
     *
     * @param game
     */
    public boolean defenderAssignsCombatDamage(Game game) {
        for (UUID blockerId : blockers) {
            Permanent blocker = game.getPermanent(blockerId);
            if (blocker != null) {
                if (hasBanding(blocker)) { // 702.21j - only one blocker with banding necessary
                    return true;
                }
            }
        }
        if (appliesBandsWithOther(blockers, game)) { // 702.21j - both a [quality] creature with “bands with other [quality]” and another [quality] creature (...)
            return true;
        }
        for (Permanent defensiveFormation : game.getBattlefield().getAllActivePermanents(defendingPlayerId)) {
            if (defensiveFormation.getAbilities().containsKey(ControllerAssignCombatDamageToBlockersAbility.getInstance().getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean assignsDefendingPlayerAndOrDefendingCreaturesDividedDamage(Permanent creature, UUID playerId, boolean first, Game game, boolean isAttacking) {
        // for handling Butcher Orgg
        if (creature.getAbilities().containsKey(ControllerDivideCombatDamageAbility.getInstance().getId())) {
            Player player = game.getPlayer(defenderAssignsCombatDamage(game) ? defendingPlayerId : (!isAttacking && attackerAssignsCombatDamage(game) ? game.getCombat().getAttackingPlayerId() : playerId));
            // 10/4/2004 	If it is blocked but then all of its blockers are removed before combat damage is assigned, then it won't be able to deal combat damage and you won't be able to use its ability.
            // (same principle should apply if it's blocking and its blocked attacker is removed from combat)
            if (!((blocked && blockers.isEmpty() && isAttacking) || (attackers.isEmpty() && !isAttacking)) && canDamage(creature, first)) {
                if (player.chooseUse(Outcome.Damage, "Have " + creature.getLogName() + " assign its combat damage divided among defending player and/or any number of defending creatures?", null, game)) {
                    defendingPlayerAndOrDefendingCreaturesDividedDamage(creature, player, first, game, isAttacking);
                    return true;
                }
            }
        }
        return false;
    }

    private static int getLethalDamage(Permanent blocker, Permanent attacker, Game game) {
        return blocker.getLethalDamage(attacker.getId(), game);
    }
}
