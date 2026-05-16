package mage.player.ai.scoring;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

import java.util.Locale;
import java.util.UUID;

final class AiScoreSupport {

    private AiScoreSupport() {
    }

    static boolean isEnabled(String property) {
        return Boolean.parseBoolean(System.getProperty(property, "true"));
    }

    static int moduleCap(String property, int defaultValue) {
        return Math.max(0, Integer.getInteger(property, defaultValue));
    }

    static int apply(int rawModifier, boolean globalApplyModifiers, String applyProperty) {
        return globalApplyModifiers && Boolean.getBoolean(applyProperty) ? rawModifier : 0;
    }

    static int clamp(int value, int maxAbs) {
        if (maxAbs <= 0) {
            return 0;
        }
        return Math.max(-maxAbs, Math.min(maxAbs, value));
    }

    static boolean isMainPhase(Game game) {
        PhaseStep step = game == null ? null : game.getTurnStepType();
        return step == PhaseStep.PRECOMBAT_MAIN || step == PhaseStep.POSTCOMBAT_MAIN;
    }

    static boolean isCombatStep(Game game) {
        PhaseStep step = game == null ? null : game.getTurnStepType();
        return step != null && !step.isBefore(PhaseStep.BEGIN_COMBAT) && !step.isAfter(PhaseStep.END_COMBAT);
    }

    static boolean isBeforeDrawStep(Game game) {
        PhaseStep step = game == null ? null : game.getTurnStepType();
        return step != null && step.isBefore(PhaseStep.DRAW);
    }

    static int actionManaValue(Ability action) {
        if (action == null) {
            return 0;
        }
        try {
            return Math.max(action.getManaCosts().manaValue(), action.getManaCostsToPay().manaValue());
        } catch (RuntimeException ignored) {
            return 0;
        }
    }

    static int countLands(Game game, UUID playerId) {
        return countPermanents(game, playerId, permanent -> permanent.isLand(game));
    }

    static int countCreatures(Game game, UUID playerId) {
        return countPermanents(game, playerId, permanent -> permanent.isCreature(game));
    }

    static int countTreasures(Game game, UUID playerId) {
        return countPermanents(game, playerId, permanent -> permanent.hasSubtype(SubType.TREASURE, game));
    }

    static int countManaSources(Game game, UUID playerId) {
        return countPermanents(game, playerId, permanent ->
                permanent.isLand(game)
                        || permanent.isArtifact(game) && rules(permanent).contains("add {")
        );
    }

    static int totalCreaturePower(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int power = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent != null && permanent.isCreature(game)) {
                power += Math.max(0, permanent.getPower().getValue());
            }
        }
        return power;
    }

    static int maxAvailableAttackPower(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int maxPower = 0;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.isInGame() || opponent.hasLost()) {
                continue;
            }
            int power = 0;
            for (Permanent attacker : player.getAvailableAttackers(opponentId, game)) {
                if (attacker != null) {
                    power += Math.max(0, attacker.getPower().getValue());
                }
            }
            maxPower = Math.max(maxPower, power);
        }
        return maxPower;
    }

    static int playerLife(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        return player == null ? 0 : player.getLife();
    }

    static int handSize(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        return player == null ? 0 : player.getHand().size();
    }

    static int handCount(Game game, UUID playerId, String cardName) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null || cardName == null || cardName.isEmpty()) {
            return 0;
        }
        return (int) player.getHand().getCards(game).stream()
                .filter(card -> card != null && cardName.equals(card.getName()))
                .count();
    }

    static int countLivingOpponents(Game game, UUID playerId) {
        if (game == null || playerId == null) {
            return 0;
        }
        int count = 0;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost()) {
                count++;
            }
        }
        return count;
    }

    static UUID lowestLifeOpponent(Game game, UUID playerId) {
        UUID best = null;
        int bestLife = Integer.MAX_VALUE;
        if (game == null || playerId == null) {
            return null;
        }
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost() && opponent.getLife() < bestLife) {
                bestLife = opponent.getLife();
                best = opponentId;
            }
        }
        return best;
    }

    static int maxOpponentThreat(Game game, UUID playerId) {
        int threat = 0;
        if (game == null || playerId == null) {
            return threat;
        }
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            threat = Math.max(threat, totalCreaturePower(game, opponentId) + countCreatures(game, opponentId) * 2);
        }
        return threat;
    }

    static boolean hasOutcome(Ability action, Outcome... outcomes) {
        if (action == null || outcomes == null) {
            return false;
        }
        for (Effect effect : action.getAllEffects()) {
            Outcome outcome = effect.getOutcome();
            for (Outcome expected : outcomes) {
                if (outcome == expected) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasBadOutcome(Ability action) {
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

    static boolean targetsOpponent(Game game, Ability action, UUID playerId) {
        return findFirstTargetedOpponent(game, action, playerId) != null;
    }

    static boolean developsOwnMana(Game game, Ability action, UUID playerId) {
        if (game == null || action == null || playerId == null) {
            return false;
        }
        if (hasBadOutcome(action) && targetsOpponent(game, action, playerId)) {
            return false;
        }
        if (hasOutcome(action, Outcome.PutLandInPlay, Outcome.PutManaInPool, Outcome.PlayForFree)) {
            return true;
        }
        String actionText = actionText(action, game);
        return actionText.contains("search your library")
                && (actionText.contains("land") || actionText.contains("basic"))
                && !actionText.contains("its controller may search");
    }

    static UUID findFirstTargetedOpponent(Game game, Ability action, UUID playerId) {
        if (game == null || action == null || playerId == null) {
            return null;
        }
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                Player targetPlayer = game.getPlayer(targetId);
                if (targetPlayer != null && !targetPlayer.getId().equals(playerId)) {
                    return targetPlayer.getId();
                }
                UUID controllerId = FfaScoringUtil.getTargetController(game, targetId);
                if (controllerId != null && !controllerId.equals(playerId)) {
                    return controllerId;
                }
            }
        }
        return null;
    }

    static String rules(MageObject object) {
        if (object == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (object.getAbilities() != null) {
                object.getAbilities().getRules().forEach(rule -> sb.append(rule).append('\n'));
            }
        } catch (RuntimeException ignored) {
        }
        return sb.toString().toLowerCase(Locale.ROOT);
    }

    static String actionText(Ability action, Game game) {
        StringBuilder sb = new StringBuilder();
        if (action == null) {
            return "";
        }
        try {
            sb.append(action.getRule()).append(' ');
        } catch (RuntimeException ignored) {
        }
        try {
            MageObject source = action.getSourceObject(game);
            sb.append(rules(source)).append(' ');
        } catch (RuntimeException ignored) {
        }
        try {
            for (Cost cost : action.getCosts()) {
                sb.append(cost.getText()).append(' ');
            }
        } catch (RuntimeException ignored) {
        }
        return sb.toString().toLowerCase(Locale.ROOT);
    }

    private static int countPermanents(Game game, UUID playerId, PermanentPredicate predicate) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent != null && predicate.test(permanent)) {
                count++;
            }
        }
        return count;
    }

    private interface PermanentPredicate {
        boolean test(Permanent permanent);
    }
}
