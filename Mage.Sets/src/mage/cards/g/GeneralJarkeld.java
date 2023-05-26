
package mage.cards.g;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author L_J
 */
public final class GeneralJarkeld extends CardImpl {

    public GeneralJarkeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Switch the blocking creatures of two target attacking creatures. Activate this ability only during the declare blockers step.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new GeneralJarkeldSwitchBlockersEffect(), new TapSourceCost(), new IsStepCondition(PhaseStep.DECLARE_BLOCKERS, false));
        ability.addTarget(new TargetAttackingCreature(2));
        this.addAbility(ability);
    }

    private GeneralJarkeld(final GeneralJarkeld card) {
        super(card);
    }

    @Override
    public GeneralJarkeld copy() {
        return new GeneralJarkeld(this);
    }

}

class GeneralJarkeldSwitchBlockersEffect extends OneShotEffect {

    public GeneralJarkeldSwitchBlockersEffect() {
        super(Outcome.Benefit);
        this.staticText = "Switch the blocking creatures of two target attacking creatures";
    }

    public GeneralJarkeldSwitchBlockersEffect(final GeneralJarkeldSwitchBlockersEffect effect) {
        super(effect);
    }

    @Override
    public GeneralJarkeldSwitchBlockersEffect copy() {
        return new GeneralJarkeldSwitchBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        List<UUID> targets = source.getTargets().get(0).getTargets();
        if (controller != null && targets != null) {
            Permanent attacker1 = game.getPermanent(targets.get(0));
            Permanent attacker2 = game.getPermanent(targets.get(1));
            if (attacker1 != null && attacker2 != null) {
                CombatGroup chosenGroup1 = game.getCombat().findGroup(attacker1.getId());
                CombatGroup chosenGroup2 = game.getCombat().findGroup(attacker2.getId());
                if (chosenGroup1 != null && chosenGroup2 != null) {
                    Set<Permanent> blockers1 = new HashSet<>();
                    Set<Permanent> blockers2 = new HashSet<>();
                    Set<Permanent> multiBlockers = new HashSet<>();
                    
                    blockerSearch1:
                    for (UUID blockerId : chosenGroup1.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            if (game.getCombat().blockingGroupsContains(blocker.getId())) { // if (blocker.getBlocking() > 1) {
                                for (CombatGroup group : game.getCombat().getBlockingGroups()) {
                                    if (group.getBlockers().contains(blocker.getId())) { 
                                        int attackerCount = group.getAttackers().size();
                                        if (attackerCount > 1) {
                                            multiBlockers.add(blocker);
                                        } else if (attackerCount == 1) {
                                            blockers1.add(blocker);
                                        }
                                        continue blockerSearch1;
                                    }
                                }
                            } else {
                                blockers1.add(blocker);
                            }
                        }
                    }
                    
                    blockerSearch2:
                    for (UUID blockerId : chosenGroup2.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null) {
                            if (game.getCombat().blockingGroupsContains(blocker.getId())) { // if (blocker.getBlocking() > 1) {
                                for (CombatGroup group : game.getCombat().getBlockingGroups()) {
                                    if (group.getBlockers().contains(blocker.getId())) {
                                        int attackerCount = group.getAttackers().size();
                                        if (attackerCount > 1) {
                                            multiBlockers.add(blocker);
                                        } else if (attackerCount == 1) {
                                            blockers2.add(blocker);
                                        }
                                        continue blockerSearch2;
                                    }
                                }
                            } else {
                                blockers2.add(blocker);
                            }
                        }
                    }
                    
                    handleSingleBlockers(blockers1, chosenGroup1, chosenGroup2, controller, game);
                    handleSingleBlockers(blockers2, chosenGroup2, chosenGroup1, controller, game);
                    handleMultiBlockers(multiBlockers, chosenGroup1, chosenGroup2, controller, game);
                    
                    // the ability doesn't unblock a group that loses all blockers, however it will newly block a previously unblocked group if it gains a blocker this way
                    if (!(chosenGroup1.getBlockers().isEmpty())) {
                        chosenGroup1.setBlocked(true, game);
                        chosenGroup1.pickBlockerOrder(attacker1.getControllerId(), game);
                    }
                    if (!(chosenGroup2.getBlockers().isEmpty())) {
                        chosenGroup2.setBlocked(true, game);
                        chosenGroup2.pickBlockerOrder(attacker2.getControllerId(), game);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void handleSingleBlockers(Set<Permanent> blockers, CombatGroup chosenGroup, CombatGroup otherGroup, Player controller, Game game) {
        for (Permanent blocker : blockers) {
            chosenGroup.remove(blocker.getId());
            blocker.setBlocking(0);
            // 10/4/2004 	The new blocker does not trigger any abilities which trigger on creatures becoming blockers, because the creatures were already blockers and the simple change of who is blocking does not trigger such abilities.
            otherGroup.addBlockerToGroup(blocker.getId(), controller.getId(), game);
        }
    }

    private void handleMultiBlockers(Set<Permanent> blockers, CombatGroup chosenGroup1, CombatGroup chosenGroup2, Player controller, Game game) {
        // for handling multi-blockers (Two Headed Giant of Foriys, etc.)
        blockerIteration:
        for (Permanent blocker : blockers) {
            CombatGroup blockGroup = null;
            for (CombatGroup group : game.getCombat().getBlockingGroups()) {
                if (group.getBlockers().contains(blocker.getId())) {
                    blockGroup = group;
                    break;
                }
            }
            if (blockGroup != null) {
                CombatGroup chosenGroup = null;
                boolean sameBlocked = false;
                for (CombatGroup group : game.getCombat().getGroups()) {
                    if (group.getBlocked() && group.getBlockers().contains(blocker.getId())) {
                        if (group == chosenGroup1 || group == chosenGroup2) {
                            if (sameBlocked) {
                                continue blockerIteration;
                            }
                            sameBlocked = true;
                            chosenGroup = group;
                        }
                    }
                }
                
                if (sameBlocked && chosenGroup != null) { // if none (should not happen) or all the blockers correspond to Jarkeld's targets, the blockers remain the same
                    CombatGroup otherGroup = (chosenGroup.equals(chosenGroup1) ? chosenGroup2 : chosenGroup1);
                    chosenGroup.remove(blocker.getId());
                    for (UUID attacker : chosenGroup.getAttackers()) {
                        blockGroup.remove(attacker);
                    }
                    otherGroup.addBlockerToGroup(blocker.getId(), controller.getId(), game);
                    for (UUID attacker : otherGroup.getAttackers()) {
                        // 10/4/2004 	The new blocker does not trigger any abilities which trigger on creatures becoming blockers, because the creatures were already blockers and the simple change of who is blocking does not trigger such abilities.
                        game.getCombat().addBlockingGroup(blocker.getId(), attacker, controller.getId(), game);
                    }
                    blockGroup.pickAttackerOrder(blocker.getControllerId(), game);
                }
            }
        }
    }
}
