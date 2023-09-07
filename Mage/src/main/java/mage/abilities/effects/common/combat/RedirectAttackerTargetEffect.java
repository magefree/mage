package mage.abilities.effects.common.combat;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDefender;

public class RedirectAttackerTargetEffect extends OneShotEffect {
    
    private final boolean includePermanents;
    private static final FilterPermanent filter = new FilterPermanent("permanent");

    static {
        filter.add(Predicates.or(
            CardType.PLANESWALKER.getPredicate(), 
            CardType.BATTLE.getPredicate()
        ));
    }

    public RedirectAttackerTargetEffect(boolean includePermanents) {
        super(Outcome.Benefit);
        this.includePermanents = includePermanents;
    }

    public RedirectAttackerTargetEffect(final RedirectAttackerTargetEffect effect) {
        super(effect);
        this.includePermanents = effect.includePermanents;
    }

    @Override
    public RedirectAttackerTargetEffect copy() {
        return new RedirectAttackerTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        
        // Get combat group for attacking creatures.
        for (UUID id : getTargetPointer().getTargets(game, source)) {

            Permanent attackingCreature = game.getPermanent(id);
            if (attackingCreature == null) {
                continue;
            }

            CombatGroup combatGroupTarget = game.getCombat().findGroup(attackingCreature.getId());
            if (combatGroupTarget == null) {
                continue;
            }

            // Reselecting which player or permanent a creature is attacking ignores all requirements, restrictions, and costs associated with attacking.

            // Update possible defender
            Set<UUID> defenders = new LinkedHashSet<>();
            for (UUID playerId : game.getCombat().getAttackablePlayers(game)) {
                defenders.add(playerId);
                if (includePermanents) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        defenders.add(permanent.getId());
                    }
                }
            }

            // Select the new defender(s)
            TargetDefender defender = new TargetDefender(defenders);
            if (controller.chooseTarget(Outcome.Damage, defender, source, game)) {
                UUID firstTarget = defender.getFirstTarget();
                if (combatGroupTarget.getDefenderId() != null && !combatGroupTarget.getDefenderId().equals(firstTarget)) {
                    if (combatGroupTarget.changeDefenderPostDeclaration(firstTarget, game)) {
                        String attacked = "";
                        Player player = game.getPlayer(firstTarget);
                        if (player != null) {
                            attacked = player.getLogName();
                        } else {
                            Permanent permanent = game.getPermanent(firstTarget);
                            if (permanent != null) {
                                attacked = permanent.getLogName();
                            }
                        }
                        game.informPlayers(attackingCreature.getLogName() + " now attacks " + attacked);
                    }
                }
            }
        }

        return true;
    }
}
