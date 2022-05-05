package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Mael
 */
public class TargetOpponentsChoicePermanent extends TargetPermanent {

    protected UUID opponentId = null;

    public TargetOpponentsChoicePermanent(int minNumTargets, int maxNumTargets, FilterPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetOpponentsChoicePermanent(final TargetOpponentsChoicePermanent target) {
        super(target);
        this.opponentId = target.opponentId;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game, boolean flag) {
        return opponentId != null && super.canTarget(opponentId, id, source, game, flag);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (opponentId != null) {
            if (permanent != null) {
                if (source != null) {
                    boolean canSourceControllerTarget = true;
                    if (!isNotTarget()) {
                        if (!permanent.canBeTargetedBy(game.getObject(source.getId()), controllerId, game)
                                || !permanent.canBeTargetedBy(game.getObject(source), controllerId, game)) {
                            canSourceControllerTarget = false;
                        }
                    }
                    canSourceControllerTarget &= super.canTarget(opponentId, id, source, game);
                    canSourceControllerTarget &= filter.match(permanent, opponentId, source, game);
                    return canSourceControllerTarget;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        // choose opponent
        if (opponentId == null) {
            TargetOpponent target = new TargetOpponent(true); // notTarget true = can't cancel
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            }
        }
        if (opponentId == null) {
            return false;
        }

        // opponent choose real targets
        return super.chooseTarget(outcome, opponentId, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        MageObject sourceObject = game.getObject(source);
        Player player = game.getPlayer(sourceControllerId);
        if (sourceObject == null || player == null) {
            return false;
        }
        int counter;
        for (UUID oppId : game.getState().getPlayersInRange(player.getId(), game)) {
            counter = 0;
            Player opp = game.getPlayer(oppId);
            if (opp != null && player.hasOpponent(opp.getId(), game)) {
                for (Permanent perm : game.getBattlefield().getActivePermanents(opp.getId(), game)) {
                    if (!targets.containsKey(perm.getId())
                            && filter.match(perm, opp.getId(), source, game)
                            && perm.canBeTargetedBy(sourceObject, sourceControllerId, game)) {
                        counter++;
                        if (counter >= minNumberOfTargets) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TargetOpponentsChoicePermanent copy() {
        return new TargetOpponentsChoicePermanent(this);
    }

    @Override
    public boolean isRequired() {
        return true; // opponent can't cancel the spell
    }

    @Override
    public boolean isRequired(UUID sourceId, Game game) {
        return true; // opponent can't cancel the spell
    }

    @Override
    public boolean isRequired(Ability ability) {
        return true; // opponent can't cancel the spell
    }
}
