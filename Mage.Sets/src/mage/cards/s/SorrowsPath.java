package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.BlockerDeclaredEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class SorrowsPath extends CardImpl {

    private static final FilterOpponentsCreaturePermanent filter = new FilterOpponentsCreaturePermanent("blocking creatures an opponent controls");

    static {
        filter.add(BlockingPredicate.instance);
    }

    public SorrowsPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Choose two target blocking creatures an opponent controls. If each of those creatures could block all creatures that the other is blocking, remove both of them from combat. Each one then blocks all creatures the other was blocking.
        Ability ability = new SimpleActivatedAbility(new SorrowsPathSwitchBlockersEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanentSameController(2, filter));
        this.addAbility(ability);

        // Whenever Sorrow's Path becomes tapped, it deals 2 damage to you and each creature you control.
        Ability ability2 = new BecomesTappedSourceTriggeredAbility(new DamageControllerEffect(2));
        ability2.addEffect(new DamageAllEffect(
                2, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and each creature you control"));
        this.addAbility(ability2);
    }

    private SorrowsPath(final SorrowsPath card) {
        super(card);
    }

    @Override
    public SorrowsPath copy() {
        return new SorrowsPath(this);
    }

}

class SorrowsPathSwitchBlockersEffect extends OneShotEffect {

    SorrowsPathSwitchBlockersEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target blocking creatures an opponent controls. " +
                "If each of those creatures could block all creatures that the other is blocking, " +
                "remove both of them from combat. Each one then blocks all creatures the other was blocking";
    }

    private SorrowsPathSwitchBlockersEffect(final SorrowsPathSwitchBlockersEffect effect) {
        super(effect);
    }

    @Override
    public SorrowsPathSwitchBlockersEffect copy() {
        return new SorrowsPathSwitchBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        List<UUID> targets = source.getTargets().get(0).getTargets();
        if (controller != null && targets != null) {
            Permanent blocker1 = game.getPermanent(targets.get(0));
            Permanent blocker2 = game.getPermanent(targets.get(1));
            if (blocker1 != null && blocker2 != null) {
                Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                // 10/1/2009: When determining whether a creature could block all creatures the other is blocking, take into account evasion abilities (like flying), protection 
                // abilities, and other blocking restrictions, as well as abilities that allow a creature to block multiple creatures or block as though a certain condition were true. 
                // Take into account whether those creatures are tapped, but not whether they have costs to block (since those apply only as blockers are declared).
                CombatGroup chosenGroup1 = findBlockingGroup(blocker1, game);
                CombatGroup chosenGroup2 = findBlockingGroup(blocker2, game);
                Set<Permanent> attackers1 = new HashSet<>();
                Set<Permanent> attackers2 = new HashSet<>();
                boolean blockPossible = false;

                if (chosenGroup1 != null && chosenGroup2 != null) {
                    blockPossible = getRestrictions(chosenGroup1, blocker2, attackers1, sourcePermanent, controller, game) && getRestrictions(chosenGroup2, blocker1, attackers2, sourcePermanent, controller, game);
                }
                if (!blockPossible) {
                    return true;
                }

                // 10/1/2009: When the first ability resolves, if all the creatures that one of the targeted creatures was blocking have left combat, then the other targeted creature 
                // is considered to be able to block all creatures the first creature is blocking. If the ability has its full effect, the second creature will be removed from combat 
                // but not returned to combat; it doesn't block anything.
                game.getCombat().removeFromCombat(blocker1.getId(), game, false);
                game.getCombat().removeFromCombat(blocker2.getId(), game, false);
                blocker1.setRemovedFromCombat(attackers2.isEmpty());
                blocker2.setRemovedFromCombat(attackers1.isEmpty());

                // 10/1/2009: Abilities that trigger whenever one of the targeted creatures blocks will trigger when the first ability resolves, because those creatures will change from 
                // not blocking (since they're removed from combat) to blocking. It doesn't matter if those abilities triggered when those creatures blocked the first time. Abilities 
                // that trigger whenever one of the attacking creatures becomes blocked will not trigger again, because they never stopped being blocked creatures. Abilities that 
                // trigger whenever a creature blocks one of the attacking creatures will trigger again, though; those kinds of abilities trigger once for each creature that blocks.
                reassignBlocker(blocker1, attackers2, game);
                reassignBlocker(blocker2, attackers1, game);
                Set<MageObjectReference> morSet = new HashSet<>();
                attackers1
                        .stream()
                        .map(permanent -> new MageObjectReference(permanent, game))
                        .forEach(morSet::add);
                attackers2
                        .stream()
                        .map(permanent -> new MageObjectReference(permanent, game))
                        .forEach(morSet::add);
                String key = UUID.randomUUID().toString();
                game.getState().setValue("becameBlocked_" + key, morSet);
                game.fireEvent(GameEvent.getEvent(
                        GameEvent.EventType.BATCH_BLOCK_NONCOMBAT,
                        source.getSourceId(), source,
                        source.getControllerId(), key, 0)
                );
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
        return game.getCombat().findGroupOfBlocker(blocker.getId());
    }

    private boolean getRestrictions(CombatGroup chosenGroup, Permanent blocker, Set<Permanent> attackers, Permanent sourcePermanent, Player controller, Game game) {
        for (UUID attackerId : chosenGroup.getAttackers()) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker != null) {
                if (blocker.canBlock(attackerId, game) && (blocker.getMaxBlocks() == 0 || chosenGroup.getAttackers().size() <= blocker.getMaxBlocks())) {
                    attackers.add(attacker);
                } else {
                    game.informPlayer(controller, "Illegal block detected (" + blocker.getName() + "), effect of " + sourcePermanent.getName() + " doesn't apply.");
                    return false;
                }
            }
        }
        return true;
    }

    private void reassignBlocker(Permanent blocker, Set<Permanent> attackers, Game game) {
        for (Permanent attacker : attackers) {
            CombatGroup group = game.getCombat().findGroup(attacker.getId());
            if (group != null) {
                group.addBlockerToGroup(blocker.getId(), blocker.getControllerId(), game);
                game.getCombat().addBlockingGroup(blocker.getId(), attacker.getId(), blocker.getControllerId(), game);
                // TODO: find an alternate event solution for multi-blockers (as per issue #4285), this will work fine for single blocker creatures though
                game.fireEvent(new BlockerDeclaredEvent(attacker.getId(), blocker.getId(), blocker.getControllerId()));
                group.pickBlockerOrder(attacker.getControllerId(), game);
            }
        }
        CombatGroup blockGroup = findBlockingGroup(blocker, game); // a new blockingGroup is formed, so it's necessary to find it again
        if (blockGroup != null) {
            blockGroup.pickAttackerOrder(blocker.getControllerId(), game);
        }
    }

}
