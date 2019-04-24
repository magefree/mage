
package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.BlockedByOnlyOneCreatureThisCombatWatcher;

/**
 *
 * @author L_J
 */
public final class FalseOrders extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature defending player controls");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new FalseOrdersDefendingPlayerControlsPredicate());
    }

    public FalseOrders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Cast False Orders only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, PhaseStep.DECLARE_BLOCKERS, null, "Cast this spell only during the declare blockers step"));

        // Remove target creature defending player controls from combat. Creatures it was blocking that had become blocked by only that creature this combat become unblocked. You may have it block an attacking creature of your choice.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new FalseOrdersUnblockEffect());
        this.getSpellAbility().addWatcher(new BlockedByOnlyOneCreatureThisCombatWatcher());
    }

    public FalseOrders(final FalseOrders card) {
        super(card);
    }

    @Override
    public FalseOrders copy() {
        return new FalseOrders(this);
    }

}

class FalseOrdersDefendingPlayerControlsPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        return game.getCombat().getPlayerDefenders(game).contains(input.getObject().getControllerId());
    }
}

class FalseOrdersUnblockEffect extends OneShotEffect {

    public FalseOrdersUnblockEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove target creature defending player controls from combat. Creatures it was blocking that had become blocked by only that creature this combat become unblocked. You may have it block an attacking creature of your choice";
    }

    public FalseOrdersUnblockEffect(final FalseOrdersUnblockEffect effect) {
        super(effect);
    }

    @Override
    public FalseOrdersUnblockEffect copy() {
        return new FalseOrdersUnblockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }

        // Remove target creature from combat
        Effect effect = new RemoveFromCombatTargetEffect();
        effect.apply(game, source);

        // Make blocked creatures unblocked
        BlockedByOnlyOneCreatureThisCombatWatcher watcher = (BlockedByOnlyOneCreatureThisCombatWatcher) game.getState().getWatchers().get(BlockedByOnlyOneCreatureThisCombatWatcher.class.getSimpleName());
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

        if (!permanent.isCreature()
                || !controller.chooseUse(Outcome.Benefit, "Do you want " + permanent.getLogName() + " to block an attacking creature?", source, game)) {
            return false;
        }
        // Choose new creature to block

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
        if (targetsController == null) {
            return false;
        }
        FilterAttackingCreature filter = new FilterAttackingCreature("creature attacking " + targetsController.getLogName());
        filter.add(new PermanentInListPredicate(list));
        TargetAttackingCreature target = new TargetAttackingCreature(1, 1, filter, true);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
            while (!target.isChosen() && target.canChoose(controller.getId(), game) && controller.canRespond()) {
                controller.chooseTarget(outcome, target, source, game);
            }
        } else {
            return true;
        }
        Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
        if (chosenPermanent == null || !chosenPermanent.isCreature()) {
            return false;
        }
        CombatGroup chosenGroup = game.getCombat().findGroup(chosenPermanent.getId());
        if (chosenGroup != null) {
            // Relevant ruling for Balduvian Warlord:
            // 7/15/2006 	If an attacking creature has an ability that triggers “When this creature becomes blocked,” 
            // it triggers when a creature blocks it due to the Warlord's ability only if it was unblocked at that point.
            boolean notYetBlocked = chosenGroup.getBlockers().isEmpty();
            chosenGroup.addBlockerToGroup(permanent.getId(), controller.getId(), game);
            game.getCombat().addBlockingGroup(permanent.getId(), chosenPermanent.getId(), controller.getId(), game); // 702.21h
            if (notYetBlocked) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, chosenPermanent.getId(), null));
                for (UUID bandedId : chosenPermanent.getBandedCards()) {
                    CombatGroup bandedGroup = game.getCombat().findGroup(bandedId);
                    if (bandedGroup != null && chosenGroup.getBlockers().size() == 1) {
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, bandedId, null));
                    }
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BLOCKER_DECLARED, chosenPermanent.getId(), permanent.getId(), permanent.getControllerId()));
        }
        CombatGroup blockGroup = findBlockingGroup(permanent, game); // a new blockingGroup is formed, so it's necessary to find it again
        if (blockGroup != null) {
            blockGroup.pickAttackerOrder(permanent.getControllerId(), game);
        }
        return true;
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
