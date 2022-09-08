package mage.game.combat;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.special.JohanVigilanceAbility;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AttackingSameNotBandedPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.*;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDefender;
import mage.util.CardUtil;
import mage.util.Copyable;
import mage.util.trace.TraceUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Combat implements Serializable, Copyable<Combat> {

    private static final Logger logger = Logger.getLogger(Combat.class);

    private static FilterCreatureForCombatBlock filterBlockers = new FilterCreatureForCombatBlock();
    // There are effects that let creatures assigns combat damage equal to its toughness rather than its power
    private boolean useToughnessForDamage;
    private final List<FilterCreaturePermanent> useToughnessForDamageFilters = new ArrayList<>();

    protected List<CombatGroup> groups = new ArrayList<>();
    protected Map<UUID, CombatGroup> blockingGroups = new HashMap<>();
    // player and planeswalker ids
    protected Set<UUID> defenders = new HashSet<>();
    // how many creatures attack defending player
    protected Map<UUID, Set<UUID>> numberCreaturesDefenderAttackedBy = new HashMap<>();
    protected UUID attackingPlayerId; //the player that is attacking
    // <creature that can block, <all attackers that force the creature to block it>>
    protected Map<UUID, Set<UUID>> creatureMustBlockAttackers = new HashMap<>();

    // which creature is forced to attack which defender(s). If set is empty, the creature can attack every possible defender
    private final Map<UUID, Set<UUID>> creaturesForcedToAttack = new HashMap<>();
    private int maxAttackers = Integer.MIN_VALUE;

    private final HashSet<UUID> attackersTappedByAttack = new HashSet<>();

    public Combat() {
        this.useToughnessForDamage = false;
    }

    public Combat(final Combat combat) {
        this.attackingPlayerId = combat.attackingPlayerId;
        for (CombatGroup group : combat.groups) {
            groups.add(group.copy());
        }
        defenders.addAll(combat.defenders);
        for (Map.Entry<UUID, CombatGroup> group : combat.blockingGroups.entrySet()) {
            blockingGroups.put(group.getKey(), group.getValue());
        }
        this.useToughnessForDamage = combat.useToughnessForDamage;
        for (Map.Entry<UUID, Set<UUID>> group : combat.numberCreaturesDefenderAttackedBy.entrySet()) {
            this.numberCreaturesDefenderAttackedBy.put(group.getKey(), group.getValue());
        }

        for (Map.Entry<UUID, Set<UUID>> group : combat.creatureMustBlockAttackers.entrySet()) {
            this.creatureMustBlockAttackers.put(group.getKey(), group.getValue());
        }
        for (Map.Entry<UUID, Set<UUID>> group : combat.creaturesForcedToAttack.entrySet()) {
            this.creaturesForcedToAttack.put(group.getKey(), group.getValue());
        }
        this.maxAttackers = combat.maxAttackers;
        this.attackersTappedByAttack.addAll(combat.attackersTappedByAttack);
    }

    public List<CombatGroup> getGroups() {
        return groups;
    }

    public Collection<CombatGroup> getBlockingGroups() {
        return blockingGroups.values();
    }

    public boolean blockingGroupsContains(UUID blockerId) {
        return blockingGroups.containsKey(blockerId);
    }

    /**
     * Get all possible defender (players and planeswalkers) That does not mean
     * necessarily mean that they are really attacked
     *
     * @return
     */
    public Set<UUID> getDefenders() {
        return defenders;
    }

    public Set<UUID> getAttackers() {
        Set<UUID> attackers = new HashSet<>();
        for (CombatGroup group : groups) {
            attackers.addAll(group.attackers);
        }
        return attackers;
    }

    public Set<UUID> getBlockers() {
        Set<UUID> blockers = new HashSet<>();
        for (CombatGroup group : groups) {
            blockers.addAll(group.blockers);
        }
        return blockers;
    }

    public boolean useToughnessForDamage(Permanent permanent, Game game) {
        if (useToughnessForDamage) {
            for (FilterCreaturePermanent filter : useToughnessForDamageFilters) {
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setUseToughnessForDamage(boolean useToughnessForDamage) {
        this.useToughnessForDamage = useToughnessForDamage;
    }

    public void addUseToughnessForDamageFilter(FilterCreaturePermanent filter) {
        this.useToughnessForDamageFilters.add(filter);
    }

    public void reset(Game game) {
        this.useToughnessForDamage = false;
        this.useToughnessForDamageFilters.clear();
    }

    public void checkForRemoveFromCombat(Game game) {
        for (UUID creatureId : getAttackers()) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null && !creature.isCreature(game)) {
                removeFromCombat(creatureId, game, true);
            }
        }
        for (UUID creatureId : getBlockers()) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null && !creature.isCreature(game)) {
                removeFromCombat(creatureId, game, true);
            }
        }
    }

    public void clear() {
        groups.clear();
        blockingGroups.clear();
        defenders.clear();
        attackingPlayerId = null;
        creatureMustBlockAttackers.clear();
        numberCreaturesDefenderAttackedBy.clear();
        creaturesForcedToAttack.clear();
        maxAttackers = Integer.MIN_VALUE;
        attackersTappedByAttack.clear();
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(attackingPlayerId).append(defenders);
        for (CombatGroup group : groups) {
            sb.append(group.defenderId).append(group.attackers).append(group.attackerOrder).append(group.blockers).append(group.blockerOrder);
        }
        return sb.toString();
    }

    public void setAttacker(UUID playerId) {
        this.attackingPlayerId = playerId;
    }

    /**
     * Add an additional attacker to the combat (e.g. token of Geist of Saint
     * Traft) This method doesn't trigger ATTACKER_DECLARED event (as intended).
     * If the creature has to be tapped that won't do this method.
     *
     * @param creatureId - creature that shall be added to the combat
     * @param game
     * @return
     */
    public boolean addAttackingCreature(UUID creatureId, Game game) {
        return this.addAttackingCreature(creatureId, game, null);
    }

    public boolean addAttackingCreature(UUID creatureId, Game game, UUID playerToAttack) {
        Set<UUID> possibleDefenders;
        if (playerToAttack != null) {
            possibleDefenders = new HashSet<>();
            for (UUID objectId : defenders) {
                Permanent planeswalker = game.getPermanent(objectId);
                if (planeswalker != null && planeswalker.isControlledBy(playerToAttack)) {
                    possibleDefenders.add(objectId);
                } else if (playerToAttack.equals(objectId)) {
                    possibleDefenders.add(objectId);
                }
            }
        } else {
            possibleDefenders = new HashSet<>(defenders);
        }
        Player player = game.getPlayer(attackingPlayerId);
        if (player == null) {
            return false;
        }
        if (possibleDefenders.size() == 1) {
            addAttackerToCombat(creatureId, possibleDefenders.iterator().next(), game);
            return true;
        } else {
            TargetDefender target = new TargetDefender(possibleDefenders, creatureId);
            target.setNotTarget(true);
            target.setRequired(true);
            player.chooseTarget(Outcome.Damage, target, null, game);
            if (target.getFirstTarget() != null) {
                addAttackerToCombat(creatureId, target.getFirstTarget(), game);
                return true;
            }
        }
        return false;
    }

    public void selectAttackers(Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, attackingPlayerId, attackingPlayerId))) {
            //20101001 - 508.1d
            Player player = game.getPlayer(attackingPlayerId);
            if (player != null) {
                game.getCombat().checkAttackRequirements(player, game);
                boolean firstTime = true;
                do {
                    if (!firstTime
                            || !game.getPlayer(game.getActivePlayerId()).getAvailableAttackers(game).isEmpty()) {
                        player.selectAttackers(game, attackingPlayerId);
                    }
                    firstTime = false;
                    if (game.isPaused()
                            || game.checkIfGameIsOver()
                            || game.executingRollback()) {
                        return;
                    }
                    // because of possible undo during declare attackers it's neccassary to call here the methods with "game.getCombat()." to get the current combat object!!!
                    // I don't like it too - it has to be redesigned
                } while (!game.getCombat().checkAttackRestrictions(player, game));
            }
            game.getCombat().resumeSelectAttackers(game);
        }
    }

    @SuppressWarnings("deprecation")
    public void resumeSelectAttackers(Game game) {
        Map<UUID, Set<MageObjectReference>> morSetMap = new HashMap<>();
        for (CombatGroup group : groups) {
            for (UUID attacker : group.getAttackers()) {
                if (attackersTappedByAttack.contains(attacker)) {
                    Permanent attackingPermanent = game.getPermanent(attacker);
                    if (attackingPermanent != null) {
                        attackingPermanent.setTapped(false);
                        attackingPermanent.tap(true, null, game); // to tap with event finally here is needed to prevent abusing of Vampire Envoy like cards
                    }
                }
                handleBanding(attacker, game);
                // This can only be used to modify the event, the attack can't be replaced here
                game.replaceEvent(new AttackerDeclaredEvent(group.defenderId, attacker, attackingPlayerId));
                game.addSimultaneousEvent(new AttackerDeclaredEvent(group.defenderId, attacker, attackingPlayerId));
                morSetMap.computeIfAbsent(group.defenderId, x -> new HashSet<>()).add(new MageObjectReference(attacker, game));
            }
        }
        attackersTappedByAttack.clear();

        DefenderAttackedEvent.makeAddEvents(morSetMap, attackingPlayerId, game);
        game.addSimultaneousEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_ATTACKERS, attackingPlayerId, attackingPlayerId));
        if (!game.isSimulation()) {
            Player player = game.getPlayer(attackingPlayerId);
            if (player != null) {
                if (groups.size() > 0) {
                    game.informPlayers(player.getLogName() + " attacks with " + groups.size() + (groups.size() == 1 ? " creature" : " creatures"));
                } else {
                    game.informPlayers(player.getLogName() + " skip attack");
                }
            }
        }
    }

    private void handleBanding(UUID creatureId, Game game) {
        Player player = game.getPlayer(attackingPlayerId);
        Permanent attacker = game.getPermanent(creatureId);
        if (attacker == null
                || player == null) {
            return;
        }
        CombatGroup combatGroup = findGroup(attacker.getId());
        if (combatGroup == null
                || !attacker.getBandedCards().isEmpty()
                || getAttackers().size() <= 1) {
            return;
        }
        boolean canBand = attacker.hasAbility(BandingAbility.getInstance(), game);
        List<Ability> bandsWithOther = new ArrayList<>();
        for (Ability ability : attacker.getAbilities()) {
            if (ability.getClass().equals(BandsWithOtherAbility.class)) {
                bandsWithOther.add(ability);
            }
        }
        boolean canBandWithOther = !bandsWithOther.isEmpty();
        if (!canBand && !canBandWithOther) {
            return;
        }
        boolean isBanded = false;
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking creature to band with " + attacker.getLogName());
        filter.add(Predicates.not(new PermanentIdPredicate(creatureId)));
        filter.add(new AttackingSameNotBandedPredicate(combatGroup.getDefenderId())); // creature that isn't already banded, and is attacking the same player or planeswalker
        List<Predicate<MageObject>> predicates = new ArrayList<>();
        if (!canBand
                && canBandWithOther) {
            for (Ability ab : bandsWithOther) {
                BandsWithOtherAbility ability = (BandsWithOtherAbility) ab;
                if (ability.getSubtype() != null) {
                    predicates.add(ability.getSubtype().getPredicate());
                }
                if (ability.getSupertype() != null) {
                    predicates.add(ability.getSupertype().getPredicate());
                }
                if (ability.getName() != null) {
                    predicates.add(new NamePredicate(ability.getName()));
                }
            }
            filter.add(Predicates.or(predicates));
        }
        while (player.canRespond()) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
            target.setRequired(false);
            canBand &= target.canChoose(attackingPlayerId, game);
            canBandWithOther &= target.canChoose(attackingPlayerId, game);
            if (game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_ATTACKERS, attackingPlayerId, attackingPlayerId))
                    || (!canBand && !canBandWithOther)
                    || !player.chooseUse(Outcome.Benefit,
                            (isBanded ? "Band " + attacker.getLogName()
                                    + " with another " : "Form a band with " + attacker.getLogName() + " and an ")
                            + "attacking creature?", null, game)) {
                break;
            }

            if (canBand && canBandWithOther) {
                if (player.chooseUse(Outcome.Detriment, "Choose type of banding ability to apply:",
                        attacker.getLogName(), "Banding", "Bands with other", null, game)) {
                    canBandWithOther = false;
                } else {
                    canBand = false;
                    for (Ability ab : bandsWithOther) {
                        BandsWithOtherAbility ability = (BandsWithOtherAbility) ab;
                        if (ability.getSubtype() != null) {
                            predicates.add(ability.getSubtype().getPredicate());
                        }
                        if (ability.getSupertype() != null) {
                            predicates.add(ability.getSupertype().getPredicate());
                        }
                        if (ability.getName() != null) {
                            predicates.add(new NamePredicate(ability.getName()));
                        }
                    }
                    filter.add(Predicates.or(predicates));
                }
            }

            if (target.choose(Outcome.Benefit, attackingPlayerId, null, null, game)) {
                isBanded = true;
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {

                        for (UUID bandedId : attacker.getBandedCards()) {
                            permanent.addBandedCard(bandedId);
                            Permanent banded = game.getPermanent(bandedId);
                            if (banded != null) {
                                banded.addBandedCard(targetId);
                            }
                        }
                        permanent.addBandedCard(creatureId);
                        attacker.addBandedCard(targetId);
                        if (canBand) {
                            if (!permanent.hasAbility(BandingAbility.getInstance(), game)) {
                                filter.add(new AbilityPredicate(BandingAbility.class));
                                canBandWithOther = false;
                            }
                        } else if (canBandWithOther) {
                            List<Predicate<MageObject>> newPredicates = new ArrayList<>();
                            for (Predicate<MageObject> predicate : predicates) {
                                if (predicate.apply(permanent, game)) {
                                    newPredicates.add(predicate);
                                }
                            }
                            filter.add(Predicates.or(newPredicates));
                            canBand = false;
                        }
                    }

                }
            }
        }
        if (!isBanded) {
            return;
        }
        StringBuilder sb = new StringBuilder(player.getLogName()).append(" formed a band with ")
                .append(attacker.getBandedCards().size()).append(1).append(" creatures: ");
        sb.append(attacker.getLogName());
        for (UUID id : attacker.getBandedCards()) {
            sb.append(", ");
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                sb.append(permanent.getLogName());
            }
        }
        game.informPlayers(sb.toString());
    }

    protected void checkAttackRequirements(Player player, Game game) {
        //20101001 - 508.1d
        for (Permanent creature : player.getAvailableAttackers(game)) {
            boolean mustAttack = false;
            Set<UUID> defendersForcedToAttack = new HashSet<>();
            if (creature.getGoadingPlayers().isEmpty()) {
                // check if a creature has to attack
                for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, false, game).entrySet()) {
                    RequirementEffect effect = entry.getKey();
                    if (!effect.mustAttack(game)) {
                        continue;
                    }
                    mustAttack = true;
                    for (Ability ability : entry.getValue()) {
                        UUID defenderId = effect.mustAttackDefender(ability, game);
                        if (defenderId != null && defenders.contains(defenderId)) {
                            defendersForcedToAttack.add(defenderId);
                        }
                        break;
                    }
                }
            } else {
                // if creature is goaded then we start with assumption that it needs to attack any player
                mustAttack = true;
                // Filter out the planeswalkers
                defendersForcedToAttack.addAll(defenders.stream().map(game::getPlayer).filter(Objects::nonNull).map(Player::getId).collect(Collectors.toSet()));
//                defendersForcedToAttack.addAll(defenders);
            }
            if (!mustAttack) {
                continue;
            }
            // check which defenders the forced to attack creature can attack without paying a cost
            Set<UUID> defendersCostlessAttackable = new HashSet<>(defenders);
            for (UUID defenderId : defenders) {
                if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                        new DeclareAttackerEvent(defenderId, creature.getId(), creature.getControllerId()), game
                )) {
                    defendersCostlessAttackable.remove(defenderId);
                    defendersForcedToAttack.remove(defenderId);
                    continue;
                }
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(creature, game).entrySet()) {
                    if (entry
                            .getValue()
                            .stream()
                            .anyMatch(ability -> entry.getKey().canAttack(
                                    creature, defenderId, ability, game, false
                            ))) {
                        continue;
                    }
                    defendersCostlessAttackable.remove(defenderId);
                    defendersForcedToAttack.remove(defenderId);
                    break;
                }
            }
            // Remove all the players which have goaded the attacker
            defendersForcedToAttack.removeAll(creature.getGoadingPlayers());

            // force attack only if a defender can be attacked without paying a cost
            if (defendersCostlessAttackable.isEmpty()) {
                continue;
            }
            creaturesForcedToAttack.put(creature.getId(), defendersForcedToAttack);
            // No need to attack a special defender
            Set<UUID> defendersToChooseFrom = defendersForcedToAttack.isEmpty() ? defendersCostlessAttackable : defendersForcedToAttack;
            if (defendersToChooseFrom.size() == 1) {
                player.declareAttacker(creature.getId(), defendersToChooseFrom.iterator().next(), game, false);
                continue;
            }
            TargetDefender target = new TargetDefender(defendersToChooseFrom, creature.getId());
            target.setRequired(true);
            target.setTargetName("planeswalker or player for " + creature.getLogName() + " to attack (must attack effect)");
            if (player.chooseTarget(Outcome.Damage, target, null, game)) {
                player.declareAttacker(creature.getId(), target.getFirstTarget(), game, false);
            }
        }
    }

    /**
     * @param player
     * @param game
     * @return true if the attack with that set of creatures and attacked
     * players/planeswalkers is possible
     */
    protected boolean checkAttackRestrictions(Player player, Game game) {
        boolean check = true;
        int numberOfChecks = 0;
        UUID attackerToRemove = null;
        Player attackingPlayer = game.getPlayer(attackingPlayerId);
        Check:
        while (check) {
            check = false;
            numberOfChecks++;
            int numberAttackers = 0;
            for (CombatGroup group : groups) {
                numberAttackers += group.getAttackers().size();
            }
            if (attackerToRemove != null) {
                removeAttacker(attackerToRemove, game);
            }
            for (UUID attackingCreatureId : this.getAttackers()) {
                Permanent attackingCreature = game.getPermanent(attackingCreatureId);
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(attackingCreature, game).entrySet()) {
                    RestrictionEffect effect = entry.getKey();
                    for (Ability ability : entry.getValue()) {
                        if (effect.canAttackCheckAfter(numberAttackers, ability, game, true)) {
                            continue;
                        }
                        MageObject sourceObject = ability.getSourceObject(game);
                        if (attackingPlayer.isHuman()) {
                            attackingPlayer.resetPlayerPassedActions();
                            game.informPlayer(attackingPlayer, attackingCreature.getIdName() + " can't attack this way (" + (sourceObject == null ? "null" : sourceObject.getIdName()) + ')');
                            return false;
                        }
                        // remove attacking creatures for AI that are not allowed to attack
                        // can create possible not allowed attack scenarios, but not sure how to solve this
                        if (this.getGroups()
                                .stream()
                                .map(CombatGroup::getAttackers)
                                .flatMap(Collection::stream)
                                .anyMatch(attackingCreatureId::equals)) {
                            attackerToRemove = attackingCreatureId;
                        }
                        check = true; // do the check again
                        if (numberOfChecks > 50) {
                            logger.error("Seems to be an AI declare attacker lock (reached 50 check iterations) " + (sourceObject == null ? "null" : sourceObject.getIdName()));
                            return true; // break the check
                        }
                        continue Check;
                    }
                }
            }
        }
        return true;
    }

    public void selectBlockers(Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARING_BLOCKERS, attackingPlayerId, attackingPlayerId))) {
            game.getCombat().selectBlockers(null, null, game);
        }
        for (UUID attackingCreatureID : game.getCombat().getAttackers()) {
            Permanent permanent = game.getPermanent(attackingCreatureID);
            CombatGroup group = game.getCombat().findGroup(attackingCreatureID);
            if (permanent != null && group != null && !group.getBlocked()) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.UNBLOCKED_ATTACKER, attackingCreatureID, attackingPlayerId));
            }
        }
    }

    /**
     * Handle the blocker selection process
     *
     * @param blockController player that controls how to block, if null the
     * defender is the controller
     * @param game
     */
    public void selectBlockers(Player blockController, Ability source, Game game) {
        Player attacker = game.getPlayer(attackingPlayerId);
        //20101001 - 509.1c
        game.getCombat().retrieveMustBlockAttackerRequirements(attacker, game);
        Player controller;
        for (UUID defenderId : getPlayerDefenders(game)) {
            Player defender = game.getPlayer(defenderId);
            if (defender != null) {
                boolean choose = true;
                if (blockController == null) {
                    controller = defender;
                } else {
                    controller = blockController;
                }
                while (choose) {
                    controller.selectBlockers(source, game, defenderId);
                    if (game.isPaused() || game.checkIfGameIsOver() || game.executingRollback()) {
                        return;
                    }
                    if (!game.getCombat().checkBlockRestrictions(defender, game)) {
                        if (controller.isHuman()) { // only human player can decide to do the block in another way
                            continue;
                        }
                    }
                    choose = !game.getCombat().checkBlockRequirementsAfter(defender, controller, game);
                    if (!choose) {
                        choose = !game.getCombat().checkBlockRestrictionsAfter(defender, controller, game);
                    }
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defenderId, defenderId));

                // add info about attacker blocked by blocker to the game log
                if (!game.isSimulation()) {
                    game.getCombat().logBlockerInfo(defender, game);
                }
            }
        }
        // tool to catch the bug about flyers blocked by non flyers or intimidate blocked by creatures with other colors
        TraceUtil.traceCombatIfNeeded(game, game.getCombat());
    }

    /**
     * Add info about attacker blocked by blocker to the game log
     */
    private void logBlockerInfo(Player defender, Game game) {
        boolean shownDefendingPlayer = game.getPlayers().size() < 3; // only two players no need to saw the attacked player
        for (CombatGroup group : game.getCombat().getGroups()) {
            if (group.defendingPlayerId.equals(defender.getId())) {
                if (!shownDefendingPlayer) {
                    game.informPlayers("Attacked player: " + defender.getLogName());
                    shownDefendingPlayer = true;
                }
                StringBuilder sb = new StringBuilder();
                boolean attackerExists = false;
                for (UUID attackingCreatureId : group.getAttackers()) {
                    attackerExists = true;
                    Permanent attackingCreature = game.getPermanent(attackingCreatureId);
                    if (attackingCreature != null) {
                        sb.append("Attacker: ");
                        sb.append(attackingCreature.getLogName()).append(" (");
                        sb.append(attackingCreature.getPower().getValue()).append('/').append(attackingCreature.getToughness().getValue()).append(") ");
                    } else {
                        // creature left battlefield
                        attackingCreature = (Permanent) game.getLastKnownInformation(attackingCreatureId, Zone.BATTLEFIELD);
                        if (attackingCreature != null) {
                            sb.append(attackingCreature.getLogName()).append(" [left battlefield)] ");
                        }
                    }
                }
                if (attackerExists) {
                    if (!group.getBlockers().isEmpty()) {
                        sb.append("blocked by ");
                        for (UUID blockingCreatureId : group.getBlockerOrder()) {
                            Permanent blockingCreature = game.getPermanent(blockingCreatureId);
                            if (blockingCreature != null) {
                                sb.append(blockingCreature.getLogName()).append(" (");
                                sb.append(blockingCreature.getPower().getValue()).append('/').append(blockingCreature.getToughness().getValue()).append(") ");
                            }
                        }

                    } else {
                        sb.append("unblocked");
                    }
                }
                game.informPlayers(sb.toString());
            }
        }
    }

    /**
     * Check the block restrictions
     *
     * @param player
     * @param game
     * @return false - if block restrictions were not complied
     */
    public boolean checkBlockRestrictions(Player player, Game game) {
        int count = 0;
        boolean blockWasLegal = true;
        for (CombatGroup group : groups) {
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

    /**
     * Retrieves all requirements that apply and creates a Map with blockers and
     * attackers it contains only records if attackers can be retrieved //
     * Map<creature that can block,
     * Set< all attackers the creature can block and force it to block the attacker>>
     *
     * @param attackingPlayer - attacker
     * @param game
     */
    private void retrieveMustBlockAttackerRequirements(Player attackingPlayer, Game game) {
        if (attackingPlayer == null) {
            return;
        }
        if (!game.getContinuousEffects().existRequirementEffects()) {
            return;
        }
        for (Permanent possibleBlocker : game.getBattlefield().getActivePermanents(filterBlockers, attackingPlayer.getId(), game)) {
            for (Map.Entry<RequirementEffect, Set<Ability>> requirementEntry : game.getContinuousEffects().getApplicableRequirementEffects(possibleBlocker, false, game).entrySet()) {
                if (requirementEntry.getKey().mustBlock(game)) {
                    for (Ability ability : requirementEntry.getValue()) {
                        UUID attackingCreatureId = requirementEntry.getKey().mustBlockAttacker(ability, game);
                        Player defender = game.getPlayer(possibleBlocker.getControllerId());
                        if (attackingCreatureId != null && defender != null && possibleBlocker.canBlock(attackingCreatureId, game)) {
                            Permanent attackingCreature = game.getPermanent(attackingCreatureId);
                            if (attackingCreature == null || !attackingCreature.isAttacking()) {
                                // creature that must be blocked is not attacking
                                continue;
                            }
                            // check if the possible blocker has to pay cost to block, if so don't force
                            if (game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                                    new DeclareBlockerEvent(attackingCreatureId, possibleBlocker.getId(), possibleBlocker.getControllerId()), game)) {
                                // has cost to block to pay so remove this attacker
                                continue;
                            }
                            if (!getDefendingPlayerId(attackingCreatureId, game).equals(possibleBlocker.getControllerId())) {
                                // Creature can't block if not the controller or a planeswalker of the controller of the possible blocker is attacked
                                continue;
                            }
                            if (creatureMustBlockAttackers.containsKey(possibleBlocker.getId())) {
                                creatureMustBlockAttackers.get(possibleBlocker.getId()).add(attackingCreatureId);
                            } else {
                                Set<UUID> forcingAttackers = new HashSet<>();
                                forcingAttackers.add(attackingCreatureId);
                                creatureMustBlockAttackers.put(possibleBlocker.getId(), forcingAttackers);
                                // assign block to the first forcing attacker automatically
                                defender.declareBlocker(defender.getId(), possibleBlocker.getId(), attackingCreatureId, game, false);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 509.1c The defending player checks each creature they control to see
     * whether it's affected by any requirements (effects that say a creature
     * must block, or that it must block if some condition is met). If the
     * number of requirements that are being obeyed is fewer than the maximum
     * possible number of requirements that could be obeyed without disobeying
     * any restrictions, the declaration of blockers is illegal. If a creature
     * can't block unless a player pays a cost, that player is not required to
     * pay that cost, even if blocking with that creature would increase the
     * number of requirements being obeyed.
     * <p>
     * <p>
     * Example: A player controls one creature that "blocks if able" and another
     * creature with no abilities. An effect states "Creatures can't be blocked
     * except by two or more creatures." Having only the first creature block
     * violates the restriction. Having neither creature block fulfills the
     * restriction but not the requirement. Having both creatures block the same
     * attacking creature fulfills both the restriction and the requirement, so
     * that's the only option.
     *
     * @param player
     * @param controller
     * @param game
     * @return
     */
    public boolean checkBlockRequirementsAfter(Player player, Player controller, Game game) {
        // Get once a list of all opponents in range
        Set<UUID> opponents = game.getOpponents(attackingPlayerId);
        //20101001 - 509.1c
        // map with attackers (UUID) that must be blocked by at least one blocker and a set of all creatures that can block it and don't block yet
        Map<UUID, Set<UUID>> mustBeBlockedByAtLeastX = new HashMap<>();
        Map<UUID, Integer> minNumberOfBlockersMap = new HashMap<>();

        // check mustBlock requirements of creatures from opponents of attacking player
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED, player.getId(), game)) {
            // creature is controlled by an opponent of the attacker
            if (opponents.contains(creature.getControllerId())) {

                // Creature is already blocking but not forced to do so
                if (creature.getBlocking() > 0) {
                    // get all requirement effects that apply to the creature (e.g. is able to block attacker)
                    for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, false, game).entrySet()) {
                        RequirementEffect effect = entry.getKey();
                        // get possible mustBeBlockedByAtLeastX blocker
                        for (Ability ability : entry.getValue()) {
                            UUID toBeBlockedCreature = effect.mustBlockAttackerIfElseUnblocked(ability, game);
                            if (toBeBlockedCreature != null) {
                                CombatGroup toBeBlockedGroup = findGroup(toBeBlockedCreature);
                                if (toBeBlockedGroup != null && toBeBlockedGroup.getDefendingPlayerId().equals(creature.getControllerId())) {
                                    minNumberOfBlockersMap.put(toBeBlockedCreature, effect.getMinNumberOfBlockers());
                                    Set<UUID> potentialBlockers;
                                    if (mustBeBlockedByAtLeastX.containsKey(toBeBlockedCreature)) {
                                        potentialBlockers = mustBeBlockedByAtLeastX.get(toBeBlockedCreature);
                                    } else {
                                        potentialBlockers = new HashSet<>();
                                        mustBeBlockedByAtLeastX.put(toBeBlockedCreature, potentialBlockers);
                                    }
                                    potentialBlockers.add(creature.getId());
                                }
                            }
                        }
                        // check the mustBlockAllAttackers requirement for creatures already blocking (Blaze of Glory) -------------------------------
                        if (effect.mustBlockAllAttackers(game)) {
                            // find all the attackers that the creature can block (and no restictions prevent this)
                            Set<UUID> attackersToBlock = new HashSet<>();
                            boolean mayBlock = false;
                            for (UUID attackingCreatureId : getAttackers()) {
                                if (creature.canBlock(attackingCreatureId, game)) {
                                    Permanent attackingCreature = game.getPermanent(attackingCreatureId);
                                    if (attackingCreature != null) {
                                        // check if the attacker is already blocked by a max of blockers, so blocker can't block it also
                                        if (attackingCreature.getMaxBlockedBy() != 0) { // 0 = no restriction about the number of possible blockers
                                            int alreadyBlockingCreatures = 0;
                                            for (CombatGroup group : getGroups()) {
                                                if (group.getAttackers().contains(attackingCreatureId)) {
                                                    alreadyBlockingCreatures = group.getBlockers().size();
                                                    break;
                                                }
                                            }
                                            if (attackingCreature.getMaxBlockedBy() <= alreadyBlockingCreatures) {
                                                continue; // Attacker can't be blocked by more blockers so check next attacker
                                            }
                                        }
                                        // check restrictions of the creature to block that prevent it can be blocked (note L_J: not sure what this refers to...)

                                        // check if enough possible blockers are available, if true, mayBlock can be set to true
                                        if (attackingCreature.getMinBlockedBy() > 1) {
                                            int alreadyBlockingCreatures = 0;
                                            for (CombatGroup group : getGroups()) {
                                                if (group.getAttackers().contains(attackingCreatureId)) {
                                                    alreadyBlockingCreatures = group.getBlockers().size();
                                                    break;
                                                }
                                            }
                                            if (attackingCreature.getMinBlockedBy() >= alreadyBlockingCreatures) {
                                                continue; // Attacker can't be blocked by the current blocker amount so check next attacker
                                            }
                                        } else {
                                            attackersToBlock.add(attackingCreatureId);
                                        }
                                    }
                                }
                            }
                            if (!attackersToBlock.isEmpty()) {
                                for (UUID attackerId : attackersToBlock) {
                                    if (!findGroup(attackerId).getBlockers().contains(creature.getId())) {
                                        mayBlock = true;
                                        break;
                                    }
                                }
                            }
                            // if creature can block more attackers, inform human player or set blocks for AI player
                            if (mayBlock) {
                                if (controller.isHuman()) {
                                    controller.resetPlayerPassedActions();
                                    game.informPlayer(controller, "Creature should block all attackers it's able to this turn: " + creature.getIdName());
                                } else {
                                    Player defender = game.getPlayer(creature.getControllerId());
                                    if (defender != null) {
                                        for (UUID attackingCreatureId : getAttackers()) {
                                            if (creature.canBlock(attackingCreatureId, game)
                                                    && !findGroup(attackingCreatureId).getBlockers().contains(creature.getId())
                                                    && attackersToBlock.contains(attackingCreatureId)) {
                                                // TODO: might need to revisit this (calls some pickBlockerOrder instances even for a single blocker - damage distribution appears to be working correctly however)
                                                defender.declareBlocker(defender.getId(), creature.getId(), attackingCreatureId, game);
                                            }
                                        }
                                    }
                                }
                                return false;
                            }
                        }
                    }
                }

                // Creature is not blocking yet
                if (creature.getBlocking() == 0) {
                    // get all requirement effects that apply to the creature
                    for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(creature, false, game).entrySet()) {
                        RequirementEffect effect = entry.getKey();
                        // get possible mustBeBlockedByAtLeastX blocker
                        for (Ability ability : entry.getValue()) {
                            UUID toBeBlockedCreature = effect.mustBlockAttackerIfElseUnblocked(ability, game);
                            if (toBeBlockedCreature != null) {
                                CombatGroup toBeBlockedGroup = findGroup(toBeBlockedCreature);
                                if (toBeBlockedGroup != null && toBeBlockedGroup.getDefendingPlayerId().equals(creature.getControllerId())) {
                                    minNumberOfBlockersMap.put(toBeBlockedCreature, effect.getMinNumberOfBlockers());
                                    Set<UUID> potentialBlockers;
                                    if (mustBeBlockedByAtLeastX.containsKey(toBeBlockedCreature)) {
                                        potentialBlockers = mustBeBlockedByAtLeastX.get(toBeBlockedCreature);
                                    } else {
                                        potentialBlockers = new HashSet<>();
                                        mustBeBlockedByAtLeastX.put(toBeBlockedCreature, potentialBlockers);
                                    }
                                    potentialBlockers.add(creature.getId());
                                }
                            }
                        }

                        // check the mustBlockAny requirement (and mustBlockAllAttackers for not blocking creatures) ----------------------------------------
                        if (effect.mustBlockAny(game) || effect.mustBlockAllAttackers(game)) {
                            // check that it can block at least one of the attackers and no restictions prevent this
                            boolean mayBlock = false;
                            for (UUID attackingCreatureId : getAttackers()) {
                                if (creature.canBlock(attackingCreatureId, game)) {
                                    Permanent attackingCreature = game.getPermanent(attackingCreatureId);
                                    if (attackingCreature != null) {
                                        // check if the attacker is already blocked by a max of blockers, so blocker can't block it also
                                        if (attackingCreature.getMaxBlockedBy() != 0) { // 0 = no restriction about the number of possible blockers
                                            int alreadyBlockingCreatures = 0;
                                            for (CombatGroup group : getGroups()) {
                                                if (group.getAttackers().contains(attackingCreatureId)) {
                                                    alreadyBlockingCreatures = group.getBlockers().size();
                                                    break;
                                                }
                                            }
                                            if (attackingCreature.getMaxBlockedBy() <= alreadyBlockingCreatures) {
                                                continue; // Attacker can't be blocked by more blockers so check next attacker
                                            }
                                        }
                                        // check restrictions of the creature to block that prevent it can be blocked (note L_J: not sure what this refers to...)

                                        // check if enough possible blockers are available, if true, mayBlock can be set to true
                                        if (attackingCreature.getMinBlockedBy() > 1) {
                                            int alreadyBlockingCreatures = 0;
                                            for (CombatGroup group : getGroups()) {
                                                if (group.getAttackers().contains(attackingCreatureId)) {
                                                    alreadyBlockingCreatures = group.getBlockers().size();
                                                    break;
                                                }
                                            }
                                            if (attackingCreature.getMinBlockedBy() >= alreadyBlockingCreatures) {
                                                continue; // Attacker can't be blocked by the current blocker amount so check next attacker
                                            }
                                        } else {
                                            mayBlock = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            // if creature can block, inform human player or set block for AI player
                            if (mayBlock) {
                                if (controller.isHuman()) {
                                    controller.resetPlayerPassedActions();
                                    game.informPlayer(controller, "Creature should block this turn: " + creature.getIdName());
                                } else {
                                    Player defender = game.getPlayer(creature.getControllerId());
                                    if (defender != null) {
                                        for (UUID attackingCreatureId : getAttackers()) {
                                            if (creature.canBlock(attackingCreatureId, game)
                                                    && !findGroup(attackingCreatureId).getBlockers().contains(creature.getId())) {
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

        // check if for attacking creatures with mustBeBlockedByAtLeastX requirements are fulfilled
        for (UUID toBeBlockedCreatureId : mustBeBlockedByAtLeastX.keySet()) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getAttackers().contains(toBeBlockedCreatureId)) {
                    boolean requirementFulfilled = false;
                    // Check whether an applicable creature is blocking.
                    for (UUID blockerId : combatGroup.getBlockers()) {
                        if (mustBeBlockedByAtLeastX.get(toBeBlockedCreatureId).contains(blockerId)) {
                            requirementFulfilled = true;
                            break;
                        }
                    }
                    requirementFulfilled &= (combatGroup.getBlockers().size() >= Math.min(minNumberOfBlockersMap.get(toBeBlockedCreatureId), mustBeBlockedByAtLeastX.get(toBeBlockedCreatureId).size()));
                    if (!requirementFulfilled) {
                        // creature is not blocked but has possible blockers
                        if (controller.isHuman()) {
                            Permanent toBeBlockedCreature = game.getPermanent(toBeBlockedCreatureId);
                            if (toBeBlockedCreature != null) {
                                // check if all possible blocker block other creatures they are forced to block
                                // read through all possible blockers
                                for (UUID possibleBlockerId : mustBeBlockedByAtLeastX.get(toBeBlockedCreatureId)) {
                                    if (combatGroup.getBlockers().contains(possibleBlockerId)) {
                                        continue;
                                    }
                                    String blockRequiredMessage = isCreatureDoingARequiredBlock(
                                            possibleBlockerId, toBeBlockedCreatureId, mustBeBlockedByAtLeastX, game);
                                    if (blockRequiredMessage != null) { // message means not required
                                        removeBlocker(possibleBlockerId, game);
                                        controller.resetPlayerPassedActions();
                                        game.informPlayer(controller, blockRequiredMessage + " Existing block removed. It's a requirement to block " + toBeBlockedCreature.getIdName() + '.');
                                        return false;
                                    }
                                }
                            }

                        } else {
                            // take the first potential blocker from the set to block for the AI
                            for (UUID possibleBlockerId : mustBeBlockedByAtLeastX.get(toBeBlockedCreatureId)) {
                                String blockRequiredMessage = isCreatureDoingARequiredBlock(
                                        possibleBlockerId, toBeBlockedCreatureId, mustBeBlockedByAtLeastX, game);
                                if (blockRequiredMessage != null) {
                                    // set the block
                                    Permanent possibleBlocker = game.getPermanent(possibleBlockerId);
                                    Player defender = game.getPlayer(possibleBlocker.getControllerId());
                                    if (defender != null) {
                                        if (possibleBlocker.getBlocking() > 0) {
                                            removeBlocker(possibleBlockerId, game);
                                        }
                                        defender.declareBlocker(defender.getId(), possibleBlockerId, toBeBlockedCreatureId, game);
                                    }
                                    if (combatGroup.getBlockers().size() >= minNumberOfBlockersMap.get(toBeBlockedCreatureId)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        // check if creatures are forced to block but do not block at all or block creatures they are not forced to block
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<UUID, Set<UUID>> entry : creatureMustBlockAttackers.entrySet()) {
            boolean blockIsValid = true;
            Permanent creatureForcedToBlock = game.getPermanent(entry.getKey());
            if (creatureForcedToBlock == null) {
                break;
            }
            if (!creatureForcedToBlock.isControlledBy(player.getId())) {
                // ignore creatures controlled by other players
                continue;
            }
            // creature does not block -> not allowed
            // Check if blocker is really able to block one or more attackers (maybe not if the attacker has menace) - if not continue with the next forced blocker
            // TODO: Probably there is some potential to abuse the check if forced blockers are assigned to differnt attackers with e.g. menace.
            // While if assigned all to one the block is possible
            if (creatureForcedToBlock.getBlocking() == 0) {
                blockIsValid = entry.getValue().isEmpty();
                for (UUID possibleAttackerId : entry.getValue()) {
                    CombatGroup attackersGroup = game.getCombat().findGroup(possibleAttackerId);
                    Permanent attackingCreature = game.getPermanent(possibleAttackerId);
                    if (attackersGroup == null || attackingCreature == null) {
                        continue;
                    }
                    if (attackingCreature.getMinBlockedBy() > 1) { // e.g. Menace
                        if (attackersGroup.getBlockers().size() + 1 < attackingCreature.getMinBlockedBy()) {
                            blockIsValid = true;
                        }
                    }
                }
            } else {
                blockIsValid = false;
                // which attacker is the creature blocking
                CombatGroups:
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().contains(creatureForcedToBlock.getId())) {
                        for (UUID forcingAttackerId : combatGroup.getAttackers()) {
                            if (entry.getValue().contains(forcingAttackerId)) {
                                // the creature is blocking a forcing attacker, so the block is ok
                                blockIsValid = true;
                                break CombatGroups;
                            } else // check if the blocker blocks a attacker that must be blocked at least by one and is the only blocker, this block is also valid
                            {
                                if (combatGroup.getBlockers().size() == 1) {
                                    if (mustBeBlockedByAtLeastX.containsKey(forcingAttackerId)) {
                                        if (mustBeBlockedByAtLeastX.get(forcingAttackerId).contains(creatureForcedToBlock.getId())) {
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
                sb.append(' ').append(creatureForcedToBlock.getIdName());
            }
        }
        if (sb.length() > 0) {
            if (controller.isHuman()) {
                controller.resetPlayerPassedActions();
                sb.insert(0, "Some creatures are forced to block certain attacker(s):\n");
                sb.append("\nPlease block with each of these creatures an appropriate attacker.");
                game.informPlayer(controller, sb.toString());
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if a possible creature for a block is already doing another
     * required block
     *
     * @param possibleBlockerId
     * @param toBeBlockedCreatureId
     * @param mustBeBlockedByAtLeastX
     * @param game
     * @return null block is required otherwise message with reason why not
     */
    protected String isCreatureDoingARequiredBlock(UUID possibleBlockerId, UUID toBeBlockedCreatureId, Map<UUID, Set<UUID>> mustBeBlockedByAtLeastX, Game game) {
        Permanent possibleBlocker = game.getPermanent(possibleBlockerId);
        if (possibleBlocker != null) {
            if (possibleBlocker.getBlocking() == 0) {
                return possibleBlocker.getIdName() + " does not block, but could block creatures with requirement to be blocked.";
            }
            Set<UUID> forcingAttackers = creatureMustBlockAttackers.get(possibleBlockerId);
            if (forcingAttackers == null) {
                // no other creature forces the blocker to block -> it's available
                // check now, if it already blocks a creature that mustBeBlockedByAtLeastX
                if (possibleBlocker.getBlocking() > 0) {
                    CombatGroup combatGroupOfPossibleBlocker = findGroupOfBlocker(possibleBlockerId);
                    if (combatGroupOfPossibleBlocker != null) {
                        for (UUID blockedAttackerId : combatGroupOfPossibleBlocker.getAttackers()) {
                            if (mustBeBlockedByAtLeastX.containsKey(blockedAttackerId)) {
                                // blocks a creature that has to be blocked by at least one
                                if (combatGroupOfPossibleBlocker.getBlockers().size() == 1) {
                                    Set<UUID> blockedSet = mustBeBlockedByAtLeastX.get(blockedAttackerId);
                                    Set<UUID> toBlockSet = mustBeBlockedByAtLeastX.get(toBeBlockedCreatureId);
                                    if (toBlockSet == null) {
                                        // This should never happen.
                                        return null;
                                    } else if (toBlockSet.containsAll(blockedSet)) {
                                        // the creature already blocks alone a creature that has to be blocked by at least one
                                        // and has more possible blockers, so this is ok
                                        return null;
                                    }
                                }
                                // TODO: Check if the attacker is already blocked by another creature
                                // and despite there is need that this attacker blocks this attacker also
                                // I don't know why
                                Permanent blockedAttacker = game.getPermanent(blockedAttackerId);
                                return possibleBlocker.getIdName() + " blocks with other creatures " + blockedAttacker.getIdName() + ", which has to be blocked by only one creature. ";
                            }
                            // The possible blocker blocks an attacker for that is no attack forced
                            Permanent blockedAttacker = game.getPermanent(blockedAttackerId);
                            return possibleBlocker.getIdName() + " blocks " + blockedAttacker.getIdName() + ", which not has to be blocked as a requirement.";
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks the canBeBlockedCheckAfter RestrictionEffect Is the block still
     * valid after all block decisions are done
     *
     * @param player
     * @param controller
     * @param game
     * @return
     */
    public boolean checkBlockRestrictionsAfter(Player player, Player controller, Game game) {
        // Restrictions applied to blocking creatures
        for (UUID blockingCreatureId : this.getBlockers()) {
            Permanent blockingCreature = game.getPermanent(blockingCreatureId);
            if (blockingCreature != null) {
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(blockingCreature, game).entrySet()) {
                    RestrictionEffect effect = entry.getKey();
                    for (Ability ability : entry.getValue()) {
                        if (!effect.canBlockCheckAfter(ability, game, true)) {
                            if (controller.isHuman()) {
                                controller.resetPlayerPassedActions();
                                game.informPlayer(controller, blockingCreature.getLogName() + " can't block this way.");
                                return false;
                            } else {
                                // remove blocking creatures for AI
                                removeBlocker(blockingCreatureId, game);
                            }
                        }
                    }
                }
            }
        }
        // Restrictions applied because of attacking creatures
        for (UUID attackingCreatureId : this.getAttackers()) {
            Permanent attackingCreature = game.getPermanent(attackingCreatureId);
            if (attackingCreature != null) {
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(attackingCreature, game).entrySet()) {
                    RestrictionEffect effect = entry.getKey();
                    for (Ability ability : entry.getValue()) {
                        if (!effect.canBeBlockedCheckAfter(attackingCreature, ability, game, true)) {
                            if (controller.isHuman()) {
                                controller.resetPlayerPassedActions();
                                game.informPlayer(controller, attackingCreature.getLogName() + " can't be blocked this way.");
                                return false;
                            } else {
                                // remove blocking creatures for AI
                                for (CombatGroup combatGroup : this.getGroups()) {
                                    if (combatGroup.getAttackers().contains(attackingCreatureId)) {
                                        for (UUID blockerId : combatGroup.getBlockers()) {
                                            removeBlocker(blockerId, game);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void setDefenders(Game game) {
        for (UUID playerId : getAttackablePlayers(game)) {
            addDefender(playerId, game);
        }
    }

    public List<UUID> getAttackablePlayers(Game game) {
        List<UUID> attackablePlayers = new ArrayList<>();
        Player attackingPlayer = game.getPlayer(attackingPlayerId);
        if (attackingPlayer != null) {
            PlayerList players;
            Player opponent;
            switch (game.getAttackOption()) {
                case LEFT:
                    players = game.getState().getPlayerList(attackingPlayerId);
                    opponent = players.getNext(game, false);
                    while (opponent != null && attackingPlayer.isInGame()) {
                        if (attackingPlayer.hasOpponent(opponent.getId(), game)) {
                            attackablePlayers.add(opponent.getId());
                            break;
                        }
                        opponent = players.getNext(game, false);
                    }
                    break;
                case RIGHT:
                    players = game.getState().getPlayerList(attackingPlayerId);
                    opponent = players.getPrevious(game);
                    while (opponent != null && attackingPlayer.isInGame()) {
                        if (attackingPlayer.hasOpponent(opponent.getId(), game)) {
                            attackablePlayers.add(opponent.getId());
                            break;
                        }
                        opponent = players.getPrevious(game);
                    }
                    break;
                case MULTIPLE:
                    attackablePlayers.addAll(game.getOpponents(attackingPlayerId));
                    break;
            }
        }
        return attackablePlayers;
    }

    private void addDefender(UUID defenderId, Game game) {
        if (!defenders.contains(defenderId)) {
            if (maxAttackers < Integer.MAX_VALUE) {
                Player defendingPlayer = game.getPlayer(defenderId);
                if (defendingPlayer != null) {
                    if (defendingPlayer.getMaxAttackedBy() == Integer.MAX_VALUE) {
                        maxAttackers = Integer.MAX_VALUE;
                    } else if (maxAttackers == Integer.MIN_VALUE) {
                        maxAttackers = defendingPlayer.getMaxAttackedBy();
                    } else {
                        maxAttackers += defendingPlayer.getMaxAttackedBy();
                    }
                }
            }
            defenders.add(defenderId);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, defenderId, game)) {
                defenders.add(permanent.getId());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public boolean declareAttacker(UUID creatureId, UUID defenderId, UUID playerId, Game game) {
        Permanent attacker = game.getPermanent(creatureId);
        if (attacker == null
                || game.replaceEvent(new DeclareAttackerEvent(defenderId, creatureId, playerId))
                || !addAttackerToCombat(creatureId, defenderId, game)) {
            return false;
        }
        if (attacker.hasAbility(VigilanceAbility.getInstance(), game)
                || attacker.hasAbility(JohanVigilanceAbility.getInstance(), game)) {
            return true;
        }
        if (!attacker.isTapped()) {
            attacker.setTapped(true);
            attackersTappedByAttack.add(attacker.getId());
        }
        return true;
    }

    public boolean addAttackerToCombat(UUID attackerId, UUID defenderId, Game game) {
        if (!defenders.contains(defenderId)) {
            return false;
        }
        Permanent defender = game.getPermanent(defenderId);
        // Check if defending player can be attacked with another creature
        if (!canDefenderBeAttacked(attackerId, defenderId, game)) {
            return false;
        }
        Permanent attacker = game.getPermanent(attackerId);
        if (attacker == null) {
            return false;
        }
        CombatGroup newGroup = new CombatGroup(defenderId, defender != null, defender != null ? defender.getControllerId() : defenderId);
        newGroup.attackers.add(attackerId);
        attacker.setAttacking(true);
        groups.add(newGroup);
        return true;
    }

    public boolean canDefenderBeAttacked(UUID attackerId, UUID defenderId, Game game) {
        Permanent defender = game.getPermanent(defenderId);
        // Check if defending player can be attacked with another creature
        if (defender != null) {
            // a planeswalker is attacked, there exits no restriction yet for attacking planeswalker
            return true;
        }
        Player defendingPlayer = game.getPlayer(defenderId);
        if (defendingPlayer == null) {
            return false;
        }
        Set<UUID> defenderAttackedBy;
        if (numberCreaturesDefenderAttackedBy.containsKey(defendingPlayer.getId())) {
            defenderAttackedBy = numberCreaturesDefenderAttackedBy.get(defendingPlayer.getId());
        } else {
            defenderAttackedBy = new HashSet<>();
            numberCreaturesDefenderAttackedBy.put(defendingPlayer.getId(), defenderAttackedBy);
        }
        if (defenderAttackedBy.size() >= defendingPlayer.getMaxAttackedBy()) {
            Player attackingPlayer = game.getPlayer(game.getControllerId(attackerId));
            if (attackingPlayer != null && attackingPlayer.isHuman()) {
                attackingPlayer.resetPlayerPassedActions();
                game.informPlayer(attackingPlayer, "No more than "
                        + CardUtil.numberToText(defendingPlayer.getMaxAttackedBy())
                        + " creatures can attack "
                        + defendingPlayer.getLogName());
            }
            return false;
        }
        defenderAttackedBy.add(attackerId);
        return true;
    }

    /**
     * Add blocking group for creatures that already block more than one
     * creature
     *
     * @param blockerId
     * @param attackerId
     * @param playerId
     * @param game
     */
    public void addBlockingGroup(UUID blockerId, UUID attackerId, UUID playerId, Game game) {
        addBlockingGroup(blockerId, attackerId, playerId, game, true);
    }

    /**
     * Use the previous addBlockingGroup instead (solveBanding should always be
     * true outside this method)
     *
     * @param blockerId
     * @param attackerId
     * @param playerId
     * @param game
     * @param solveBanding check whether also add creatures banded with
     * attackerId
     */
    public void addBlockingGroup(UUID blockerId, UUID attackerId, UUID playerId, Game game, boolean solveBanding) {
        Permanent blocker = game.getPermanent(blockerId);
        if (blockerId != null && blocker != null && blocker.getBlocking() > 1) {
            if (!blockingGroupsContains(blockerId)) {
                CombatGroup newGroup = new CombatGroup(playerId, false, playerId);
                newGroup.blockers.add(blockerId);
                // add all blocked attackers
                for (CombatGroup group : groups) {
                    if (group.getBlockers().contains(blockerId)) {
                        newGroup.attackers.addAll(group.attackers);
                    }
                }
                blockingGroups.put(blockerId, newGroup);
            } else {
                blockingGroups.get(blockerId).attackers.add(attackerId);
            }
            // "blocker.setBlocking(blocker.getBlocking() + 1)" is handled by the attacking combat group (in addBlockerToGroup)
        }
        if (solveBanding) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker != null) {
                for (UUID bandedId : attacker.getBandedCards()) {
                    if (!bandedId.equals(attackerId)) {
                        if (blockingGroups.get(blockerId) == null || !blockingGroups.get(blockerId).attackers.contains(bandedId)) {
                            Permanent banded = game.getPermanent(bandedId);
                            CombatGroup bandedGroup = findGroup(bandedId);
                            if (banded != null && bandedGroup != null) {
                                bandedGroup.addBlockerToGroup(blockerId, playerId, game);
                                addBlockingGroup(blockerId, bandedId, playerId, game, false);
                                blocker.setBlocking(blocker.getBlocking() - 1); // this intends to offset the blocking addition from bandedGroup.addBlockerToGroup
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean removePlaneswalkerFromCombat(UUID planeswalkerId, Game game, boolean withInfo) {
        boolean result = false;
        for (CombatGroup group : groups) {
            if (group.getDefenderId() != null && group.getDefenderId().equals(planeswalkerId)) {
                group.removeAttackedPlaneswalker(planeswalkerId);
                result = true;
            }
        }
        return result;
    }

    public boolean removeFromCombat(UUID creatureId, Game game, boolean withInfo) {
        boolean result = false;
        Permanent creature = game.getPermanent(creatureId);
        if (creature != null) {
            creature.setAttacking(false);
            creature.setBlocking(0);
            creature.setRemovedFromCombat(true);
            for (CombatGroup group : groups) {
                for (UUID attackerId : group.attackers) {
                    Permanent attacker = game.getPermanent(attackerId);
                    if (attacker != null) {
                        attacker.removeBandedCard(creatureId);
                    }
                }
                result |= group.remove(creatureId);
            }
            for (CombatGroup blockingGroup : getBlockingGroups()) {
                result |= blockingGroup.remove(creatureId);
            }
            creature.clearBandedCards();
            blockingGroups.remove(creatureId);
            if (result && withInfo) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.REMOVED_FROM_COMBAT, creatureId, null, null));
                game.informPlayers(creature.getLogName() + " removed from combat");
            }
        }
        return result;
    }

    public void endCombat(Game game) {
        Permanent creature;
        for (CombatGroup group : groups) {
            for (UUID attacker : group.attackers) {
                creature = game.getPermanent(attacker);
                if (creature != null) {
                    creature.setAttacking(false);
                    creature.setBlocking(0);
                    creature.clearBandedCards();
                }
            }
            for (UUID blocker : group.blockers) {
                creature = game.getPermanent(blocker);
                if (creature != null) {
                    creature.setAttacking(false);
                    creature.setBlocking(0);
                    creature.clearBandedCards();
                }
            }
        }
        // reset the removeFromCombat flag on all creatures on the battlefield
        for (Permanent creaturePermanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
            creaturePermanent.setRemovedFromCombat(false);
        }
        clear();
    }

    public boolean hasFirstOrDoubleStrike(Game game) {
        return groups.stream()
                .anyMatch(group -> group.hasFirstOrDoubleStrike(game));
    }

    public CombatGroup findGroup(UUID attackerId) {
        for (CombatGroup group : groups) {
            if (group.getAttackers().contains(attackerId)) {
                return group;
            }
        }
        return null;
    }

    public CombatGroup findGroupOfBlocker(UUID blockerId) {
        for (CombatGroup group : groups) {
            if (group.getBlockers().contains(blockerId)) {
                return group;
            }
        }
        return null;
    }

    //    public int totalUnblockedDamage(Game game) {
//        int total = 0;
//        for (CombatGroup group : groups) {
//            if (group.getBlockers().isEmpty()) {
//                total += group.totalAttackerDamage(game);
//            }
//        }
//        return total;
//    }
    public boolean attacksAlone() {
        return (groups.size() == 1 && groups.get(0).getAttackers().size() == 1);
    }

    public boolean noAttackers() {
        return groups.isEmpty() || getAttackers().isEmpty();
    }

    public boolean isAttacked(UUID defenderId, Game game) {
        for (CombatGroup group : groups) {
            if (group.getDefenderId().equals(defenderId)) {
                return true;
            }
            if (group.defenderIsPlaneswalker) {
                Permanent permanent = game.getPermanent(group.getDefenderId());
                if (permanent.isControlledBy(defenderId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPlaneswalkerAttacked(UUID defenderId, Game game) {
        for (CombatGroup group : groups) {
            if (group.defenderIsPlaneswalker) {
                Permanent permanent = game.getPermanent(group.getDefenderId());
                if (permanent.isControlledBy(defenderId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
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

    /**
     * Returns the playerId of the player that is attacked by given attacking
     * creature
     *
     * @param attackingCreatureId
     * @param game
     * @return
     */
    public UUID getDefendingPlayerId(UUID attackingCreatureId, Game game) {
        UUID defenderId = null;
        for (CombatGroup group : groups) {
            if (group.getAttackers().contains(attackingCreatureId)) {
                defenderId = group.getDefenderId();
                if (group.defenderIsPlaneswalker) {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(defenderId);
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
        return getPlayerDefenders(game, true);
    }

    public Set<UUID> getPlayerDefenders(Game game, boolean includePlaneswalkers) {
        Set<UUID> playerDefenders = new HashSet<>();
        for (CombatGroup group : groups) {
            if (group.defenderIsPlaneswalker && !includePlaneswalkers) {
                continue;
            }
            if (group.defenderIsPlaneswalker) {
                Permanent permanent = game.getPermanent(group.getDefenderId());
                if (permanent != null) {
                    playerDefenders.add(permanent.getControllerId());
                } else {
                    playerDefenders.add(group.getDefendingPlayerId());
                }
            } else {
                playerDefenders.add(group.getDefenderId());
            }
        }
        return playerDefenders;
    }

    public void damageAssignmentOrder(Game game) {
        for (CombatGroup group : groups) {
            group.pickBlockerOrder(attackingPlayerId, game);
        }
        for (Map.Entry<UUID, CombatGroup> blockingGroup : blockingGroups.entrySet()) {
            Permanent blocker = game.getPermanent(blockingGroup.getKey());
            if (blocker != null) {
                blockingGroup.getValue().pickAttackerOrder(blocker.getControllerId(), game);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void removeAttacker(UUID attackerId, Game game) {
        for (CombatGroup group : groups) {
            if (group.attackers.contains(attackerId)) {
                group.attackers.remove(attackerId);
                group.attackerOrder.remove(attackerId);
                for (Set<UUID> attackingCreatures : numberCreaturesDefenderAttackedBy.values()) {
                    attackingCreatures.remove(attackerId);
                }
                Permanent creature = game.getPermanent(attackerId);
                if (creature != null) {
                    creature.setAttacking(false);
                    if (attackersTappedByAttack.contains(creature.getId())) {
                        creature.setTapped(false);
                        attackersTappedByAttack.remove(creature.getId());
                    }
                }
                if (group.attackers.isEmpty()) {
                    groups.remove(group);
                }
                return;
            }
        }
    }

    /**
     * Manual player action for undoing one declared blocker (used for
     * multi-blocker creatures)
     *
     * @param blockerId
     * @param groupToUnblock
     * @param game
     */
    public void removeBlockerGromGroup(UUID blockerId, CombatGroup groupToUnblock, Game game) {
        Permanent creature = game.getPermanent(blockerId);
        if (creature != null) {
            List<CombatGroup> groupsToCheck = new ArrayList<>();
            for (CombatGroup group : groups) {
                if (group.equals(groupToUnblock) && group.blockers.contains(blockerId)) {
                    groupsToCheck.add(group);
                    for (UUID attackerId : group.getAttackers()) {
                        Permanent attacker = game.getPermanent(attackerId);
                        if (attacker != null) {
                            for (UUID bandedId : attacker.getBandedCards()) {
                                if (!bandedId.equals(attackerId)) {
                                    CombatGroup bandedGroup = findGroup(bandedId);
                                    if (bandedGroup != null) {
                                        groupsToCheck.add(bandedGroup);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (CombatGroup group : groupsToCheck) {
                group.blockers.remove(blockerId);
                group.blockerOrder.remove(blockerId);
                if (group.blockers.isEmpty()) {
                    group.blocked = false;
                }
                if (creature.getBlocking() > 0) {
                    if (group.equals(groupToUnblock)) {
                        creature.setBlocking(creature.getBlocking() - 1);
                    }
                } else {
                    throw new UnsupportedOperationException("Trying to unblock creature, but blocking number value of creature < 1");
                }
                boolean canRemove = false;
                for (CombatGroup blockGroup : getBlockingGroups()) {
                    if (blockGroup.blockers.contains(blockerId)) {
                        for (UUID attackerId : group.getAttackers()) {
                            blockGroup.attackers.remove(attackerId);
                            blockGroup.attackerOrder.remove(attackerId);
                        }
                        if (creature.getBlocking() == 0) {
                            blockGroup.blockers.remove(blockerId);
                            blockGroup.attackerOrder.clear();
                        }
                    }
                    if (blockGroup.blockers.isEmpty()) {
                        canRemove = true;
                    }
                }
                if (canRemove) {
                    blockingGroups.remove(blockerId);
                }
            }
        }
    }

    /**
     * Manual player action for undoing all declared blockers (used for
     * single-blocker creatures and multi-blockers exceeding blocking limit)
     *
     * @param blockerId
     * @param game
     */
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
        boolean canRemove = false;
        for (CombatGroup group : getBlockingGroups()) {
            if (group.blockers.contains(blockerId)) {
                group.blockers.remove(blockerId);
                group.attackerOrder.clear();
            }
            if (group.blockers.isEmpty()) {
                canRemove = true;
            }
        }
        if (canRemove) {
            blockingGroups.remove(blockerId);
        }
        Permanent creature = game.getPermanent(blockerId);
        if (creature != null) {
            creature.setBlocking(0);
        }
    }

    public UUID getAttackingPlayerId() {
        return attackingPlayerId;
    }

    public Map<UUID, Set<UUID>> getCreaturesForcedToAttack() {
        return creaturesForcedToAttack;
    }

    public int getMaxAttackers() {
        return maxAttackers;
    }

    @Override
    public Combat copy() {
        return new Combat(this);
    }

}
