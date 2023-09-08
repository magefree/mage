package mage.abilities.effects.common.combat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDefender;

public class ReselectDefenderTargetEffect extends OneShotEffect {
    
    private final boolean includePermanents;
    private static final FilterPermanent filter = new FilterPermanent("permanent");

    static {
        filter.add(Predicates.or(
            CardType.PLANESWALKER.getPredicate(), 
            CardType.BATTLE.getPredicate()
        ));
    }

    public ReselectDefenderTargetEffect(boolean includePermanents) {
        super(Outcome.Benefit);
        this.includePermanents = includePermanents;
    }

    public ReselectDefenderTargetEffect(final ReselectDefenderTargetEffect effect) {
        super(effect);
        this.includePermanents = effect.includePermanents;
    }

    @Override
    public ReselectDefenderTargetEffect copy() {
        return new ReselectDefenderTargetEffect(this);
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

            // Players
            Set<UUID> defenders = game.getCombat().getAttackablePlayers(game).stream().collect(Collectors.toSet());

            // Planeswalkers and Battles
            if (includePermanents) {
                game.getBattlefield().getAllActivePermanents(filter, game)
                    .stream()
                    .filter(p -> 
                        (p.isPlaneswalker(game) && !p.isControlledBy(attackingCreature.getControllerId()))
                        || (p.isBattle(game) && !p.isProtectedBy(attackingCreature.getControllerId())))
                    .map(Permanent::getId)
                    .forEach(defenders::add);                        
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "reselect which " + (includePermanents ? "player or permanent " : "player ") + 
            getTargetPointer().describeTargets(mode.getTargets(), "that creature") 
            + " is attacking";
    }

}
