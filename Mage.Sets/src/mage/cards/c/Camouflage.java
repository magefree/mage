
package mage.cards.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.BlockerDeclaredEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.RandomUtil;

/**
 *
 * @author L_J
 */
public final class Camouflage extends CardImpl {

    public Camouflage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Cast Camouflage only during your declare attackers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.DECLARE_ATTACKERS, MyTurnCondition.instance, "Cast this spell only during your declare attackers step"));

        // This turn, instead of declaring blockers, each defending player chooses any number of creatures they control and divides them into a number of piles equal to the number of attacking creatures for whom that player is the defending player. Creatures they control that can block additional creatures may likewise be put into additional piles. Assign each pile to a different one of those attacking creatures at random. Each creature in a pile that can block the creature that pile is assigned to does so. (Piles can be empty.)
        this.getSpellAbility().addEffect(new CamouflageEffect());
    }

    private Camouflage(final Camouflage card) {
        super(card);
    }

    @Override
    public Camouflage copy() {
        return new Camouflage(this);
    }
}

class CamouflageEffect extends ContinuousRuleModifyingEffectImpl {

    public CamouflageEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "This turn, instead of declaring blockers, each defending player chooses any number of creatures they control and divides them into a number of piles equal to the number of attacking creatures for whom that player is the defending player. Creatures they control that can block additional creatures may likewise be put into additional piles. Assign each pile to a different one of those attacking creatures at random. Each creature in a pile that can block the creature that pile is assigned to does so";
    }

    public CamouflageEffect(final CamouflageEffect effect) {
        super(effect);
    }

    @Override
    public CamouflageEffect copy() {
        return new CamouflageEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, List<List<Permanent>>> masterMap = new HashMap<>();
            // Each defending player chooses any number of creatures they control
            // and divides them into a number of piles equal to the number of attacking creatures for whom that player is the defending player (piles can be empty)
            for (UUID defenderId : game.getCombat().getPlayerDefenders(game)) {
                Player defender = game.getPlayer(defenderId);
                if (defender != null) {
                    List<List<Permanent>> masterList = new ArrayList<>();
                    int attackerCount = 0;
                    for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                        if (combatGroup.getDefendingPlayerId().equals(defenderId)) {
                            attackerCount += combatGroup.getAttackers().size();
                        }
                    }
                    // This shouldn't be necessary, but just in case
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), defenderId, game)) {
                        permanent.setBlocking(0);
                    }
                    
                    boolean declinedChoice = false;
                    while (masterList.size() < attackerCount) {
                        List<Permanent> newPile = new ArrayList<>();
                        if (!declinedChoice) {
                            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control not yet assigned to a pile");
                            for (List<Permanent> list : masterList) {
                                // Creatures they control that can block additional creatures may likewise be put into additional piles.
                                // (This temporarily manipulates Blocking values to "test" how many blockers the creature has still left to assign)
                                List<Permanent> spentBlockers = new ArrayList<>();
                                for (Permanent possibleBlocker : list) {
                                    if (possibleBlocker.getMaxBlocks() != 0 && possibleBlocker.getBlocking() >= possibleBlocker.getMaxBlocks()) {
                                        spentBlockers.add(possibleBlocker);
                                    }
                                }
                                filter.add(Predicates.not(new PermanentInListPredicate(spentBlockers)));
                            }
                            if (defender.chooseUse(Outcome.Neutral, "Make a new blocker pile? If not, all remaining piles stay empty. (remaining piles: " + (attackerCount - masterList.size()) + ')', source, game)) {
                                Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
                                if (target.canChoose(defenderId, source, game)) {
                                    if (defender.chooseTarget(Outcome.Neutral, target, source, game)) {
                                        for (UUID creatureId : target.getTargets()) {
                                            Permanent creature = game.getPermanent(creatureId);
                                            if (creature != null) {
                                                creature.setBlocking(creature.getBlocking() + 1);
                                                newPile.add(creature);
                                            }
                                        }
                                    }
                                }
                            } else {
                                declinedChoice = true;
                            }
                        }
                        masterList.add(newPile);
                        
                        StringBuilder sb = new StringBuilder("Blocker pile of ").append(defender.getLogName()).append(" (no. " + masterList.size() + "): ");
                        int i = 0;
                        for (Permanent permanent : newPile) {
                            i++;
                            sb.append(permanent.getLogName());
                            if (i < newPile.size()) {
                                sb.append(", ");
                            }
                        }
                        game.informPlayers(sb.toString());
                    }
                    // Clear all test Blocking values before assigning piles
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), defenderId, game)) {
                        permanent.setBlocking(0);
                    }
                    masterMap.put(defenderId, masterList);
                }
            }
            // Assign each pile to a different one of those attacking creatures at random. Each creature in a pile that can block the creature that pile is assigned to does so
            if (!masterMap.isEmpty()) {
                for (UUID playerId : masterMap.keySet()) {
                    List<Permanent> available = new ArrayList<>();
                    for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                        if (combatGroup.getDefendingPlayerId().equals(playerId)) {
                            for (UUID attackerId : combatGroup.getAttackers()) {
                                Permanent permanent = game.getPermanent(attackerId);
                                if (permanent != null && permanent.isCreature(game)) {
                                    available.add(permanent);
                                }
                            }
                        }
                    }
                    
                    List<List<Permanent>> allPiles = masterMap.get(playerId);
                    for (List<Permanent> pile : allPiles) {
                        if (available.isEmpty()) {
                            break;
                        }
                        int randomAttacker = RandomUtil.nextInt(available.size());
                        Permanent attacker = available.get(randomAttacker);
                        if (attacker != null) {
                            available.remove(randomAttacker);
                            for (Permanent blocker : pile) {
                                CombatGroup group = game.getCombat().findGroup(attacker.getId());
                                if (group != null) {
                                    if (blocker.canBlock(attacker.getId(), game) && (blocker.getMaxBlocks() == 0 || group.getAttackers().size() <= blocker.getMaxBlocks())) {
                                        boolean notYetBlocked = group.getBlockers().isEmpty();
                                        group.addBlockerToGroup(blocker.getId(), blocker.getControllerId(), game);
                                        game.getCombat().addBlockingGroup(blocker.getId(), attacker.getId(), blocker.getControllerId(), game);
                                        if (notYetBlocked) {
                                            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, attacker.getId(), source, null));
                                        }
                                        // TODO: find an alternate event solution for multi-blockers (as per issue #4285), this will work fine for single blocker creatures though
                                        game.fireEvent(new BlockerDeclaredEvent(attacker.getId(), blocker.getId(), blocker.getControllerId()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
