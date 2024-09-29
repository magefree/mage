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

/**
 * See 508.7 in the CR for ruling details
 * 
 * @author Xanderhall
 */
public class ReselectDefenderAttackedByTargetEffect extends OneShotEffect {
    
    private final boolean includePermanents;
    private static final FilterPermanent filter = new FilterPermanent("permanent");

    static {
        filter.add(Predicates.or(
            CardType.PLANESWALKER.getPredicate(), 
            CardType.BATTLE.getPredicate()
        ));
    }

    public ReselectDefenderAttackedByTargetEffect(boolean includePermanents) {
        super(Outcome.Benefit);
        this.includePermanents = includePermanents;
    }

    protected ReselectDefenderAttackedByTargetEffect(final ReselectDefenderAttackedByTargetEffect effect) {
        super(effect);
        this.includePermanents = effect.includePermanents;
    }

    @Override
    public ReselectDefenderAttackedByTargetEffect copy() {
        return new ReselectDefenderAttackedByTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        
        for (UUID id : getTargetPointer().getTargets(game, source)) {

            Permanent attackingCreature = game.getPermanent(id);
            if (attackingCreature == null) {
                continue;
            }

            CombatGroup combatGroupTarget = game.getCombat().findGroup(attackingCreature.getId());
            if (combatGroupTarget == null) {
                continue;
            }

            // 508.7b: While reselecting which player, planeswalker, or battle a creature is attacking, 
            // that creature isn't affected by requirements or restrictions that apply to the declaration of attackers.

            // 508.7c. The reselected player, planeswalker, or battle must be an opponent of the attacking creature's controller, 
            // a planeswalker controlled by an opponent of the attacking creature's controller, 
            // or a battle protected by an opponent of the attacking creature's controller.

            Set<UUID> defenders = includePermanents ?
                game.getCombat().getDefenders() :
                game.getCombat().getAttackablePlayers(game).stream().collect(Collectors.toSet());

            // Select the new defender
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
