package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.BlockerDeclaredEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.BlockedByOnlyOneCreatureThisCombatWatcher;

import java.util.*;

/**
 * @author L_J
 */
public final class BalduvianWarlord extends CardImpl {

    public BalduvianWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {T}: Remove target blocking creature from combat. Creatures it was blocking that hadn't become blocked by another creature this combat become unblocked, then it blocks an attacking creature of your choice. Activate this ability only during the declare blockers step.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new BalduvianWarlordUnblockEffect(), new TapSourceCost(), new IsStepCondition(PhaseStep.DECLARE_BLOCKERS, false));
        ability.addTarget(new TargetPermanent(new FilterBlockingCreature()));
        this.addAbility(ability, new BlockedByOnlyOneCreatureThisCombatWatcher());
    }

    private BalduvianWarlord(final BalduvianWarlord card) {
        super(card);
    }

    @Override
    public BalduvianWarlord copy() {
        return new BalduvianWarlord(this);
    }

}

class BalduvianWarlordUnblockEffect extends OneShotEffect {

    BalduvianWarlordUnblockEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove target blocking creature from combat. Creatures it was blocking that hadn't become blocked by another creature this combat become unblocked, then it blocks an attacking creature of your choice";
    }

    private BalduvianWarlordUnblockEffect(final BalduvianWarlordUnblockEffect effect) {
        super(effect);
    }

    @Override
    public BalduvianWarlordUnblockEffect copy() {
        return new BalduvianWarlordUnblockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (controller != null && permanent != null) {

            // Remove target creature from combat
            Effect effect = new RemoveFromCombatTargetEffect();
            effect.apply(game, source);

            // Make blocked creatures unblocked
            BlockedByOnlyOneCreatureThisCombatWatcher watcher = game.getState().getWatcher(BlockedByOnlyOneCreatureThisCombatWatcher.class);
            if (watcher != null) {
                Set<CombatGroup> combatGroups = watcher.getBlockedOnlyByCreature(permanent.getId());
                if (combatGroups != null) {
                    for (CombatGroup combatGroup : combatGroups) {
                        if (combatGroup != null) {
                            combatGroup.setBlocked(false, game);
                        }
                    }
                }
            }

            // Choose new creature to block
            if (permanent.isCreature(game)) {
                // according to the following mail response from MTG Rules Management about False Orders:
                // "if Player A attacks Players B and C, Player B's creatures cannot block creatures attacking Player C"
                // therefore we need to single out creatures attacking the target blocker's controller (disappointing, I know)

                List<Permanent> list = new ArrayList<>();
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getDefendingPlayerId().equals(permanent.getControllerId())) {
                        for (UUID attackingCreatureId : combatGroup.getAttackers()) {
                            Permanent targetsControllerAttacker = game.getPermanent(attackingCreatureId);
                            list.add(targetsControllerAttacker);
                        }
                    }
                }
                Player targetsController = game.getPlayer(permanent.getControllerId());
                if (targetsController != null) {
                    FilterAttackingCreature filter = new FilterAttackingCreature("creature attacking " + targetsController.getLogName());
                    filter.add(new PermanentInListPredicate(list));
                    TargetAttackingCreature target = new TargetAttackingCreature(1, 1, filter, true);
                    if (target.canChoose(controller.getId(), source, game)) {
                        while (!target.isChosen() && target.canChoose(controller.getId(), source, game) && controller.canRespond()) {
                            controller.chooseTarget(outcome, target, source, game);
                        }
                    } else {
                        return true;
                    }
                    Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
                    if (chosenPermanent != null && chosenPermanent.isCreature(game)) {
                        CombatGroup chosenGroup = game.getCombat().findGroup(chosenPermanent.getId());
                        if (chosenGroup != null) {
                            // Relevant ruling for Balduvian Warlord:
                            // 7/15/2006 	If an attacking creature has an ability that triggers “When this creature becomes blocked,” 
                            // it triggers when a creature blocks it due to the Warlord's ability only if it was unblocked at that point.
                            boolean notYetBlocked = chosenGroup.getBlockers().isEmpty();
                            chosenGroup.addBlockerToGroup(permanent.getId(), controller.getId(), game);
                            game.getCombat().addBlockingGroup(permanent.getId(), chosenPermanent.getId(), controller.getId(), game); // 702.21h
                            if (notYetBlocked) {
                                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, chosenPermanent.getId(), source, null));
                                Set<MageObjectReference> morSet = new HashSet<>();
                                morSet.add(new MageObjectReference(chosenPermanent, game));
                                for (UUID bandedId : chosenPermanent.getBandedCards()) {
                                    CombatGroup bandedGroup = game.getCombat().findGroup(bandedId);
                                    if (bandedGroup != null && chosenGroup.getBlockers().size() == 1) {
                                        morSet.add(new MageObjectReference(bandedId, game));
                                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, bandedId, source, null));
                                    }
                                }
                                String key = UUID.randomUUID().toString();
                                game.getState().setValue("becameBlocked_" + key, morSet);
                                game.fireEvent(GameEvent.getEvent(
                                        GameEvent.EventType.BATCH_BLOCK_NONCOMBAT,
                                        source.getSourceId(), source,
                                        source.getControllerId(), key, 0)
                                );
                            }
                            game.fireEvent(new BlockerDeclaredEvent(chosenPermanent.getId(), permanent.getId(), permanent.getControllerId()));
                        }
                        CombatGroup blockGroup = findBlockingGroup(permanent, game); // a new blockingGroup is formed, so it's necessary to find it again
                        if (blockGroup != null) {
                            blockGroup.pickAttackerOrder(permanent.getControllerId(), game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private CombatGroup findBlockingGroup(Permanent blocker, Game game) {
        if (game.getCombat().blockingGroupsContains(blocker.getId())) { // if (blocker.getBlocking() > 1) {
            for (CombatGroup group : game.getCombat().getBlockingGroups()) {
                if (group.getBlockers().contains(blocker.getId())) {
                    return group;
                }
            }
        }
        return null;
    }
}
