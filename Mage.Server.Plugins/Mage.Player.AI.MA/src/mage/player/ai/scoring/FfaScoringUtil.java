package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;

import java.util.UUID;

final class FfaScoringUtil {

    private FfaScoringUtil() {
    }

    static boolean isHostileAction(Ability action) {
        if (action == null) {
            return false;
        }
        for (Effect effect : action.getAllEffects()) {
            Outcome outcome = effect.getOutcome();
            if (outcome != null && !outcome.isGood()) {
                return true;
            }
        }
        return false;
    }

    static UUID findHighestThreatTargetedOpponent(Game game, Ability action, UUID playerId) {
        if (game == null || action == null || playerId == null) {
            return null;
        }
        UUID bestOpponentId = null;
        int bestThreatScore = Integer.MIN_VALUE;
        FfaTableSnapshot snapshot = FfaTableSnapshot.fromGame(game, playerId);
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                UUID opponentId = getTargetController(game, targetId);
                FfaOpponentThreat threat = snapshot.getOpponent(opponentId);
                if (threat != null && threat.getScore() > bestThreatScore) {
                    bestThreatScore = threat.getScore();
                    bestOpponentId = opponentId;
                }
            }
        }
        return bestOpponentId;
    }

    static UUID getTargetController(Game game, UUID targetId) {
        if (game == null || targetId == null) {
            return null;
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.getId();
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return permanent.getControllerId();
        }
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null) {
            return stackObject.getControllerId();
        }
        return null;
    }

    static int estimateTargetPriority(Game game, UUID targetId) {
        if (game == null || targetId == null) {
            return 0;
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return 20 + Math.max(0, player.getHand().size() * 2) + Math.max(0, player.getLife() / 5);
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            int value = 6;
            if (permanent.isCreature(game)) {
                value += Math.max(0, permanent.getPower().getValue()) * 3;
                value += Math.max(0, permanent.getToughness().getValue()) * 2;
            }
            value += permanent.getAttachments().size() * 4;
            value += permanent.getAbilities(game).size();
            return value;
        }
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null) {
            return 18;
        }
        return 0;
    }

    static String getTargetName(Game game, UUID targetId) {
        if (game == null || targetId == null) {
            return "";
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.getName();
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return permanent.getName();
        }
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null) {
            return stackObject.getName();
        }
        return String.valueOf(targetId);
    }
}
