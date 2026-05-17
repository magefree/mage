package mage.player.ai.scoring;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DefenseReserveScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.defenseReserve.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.defenseReserve.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.defenseReserve.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public DefenseReserveScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "defense-reserve";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!Boolean.parseBoolean(System.getProperty(ENABLED_PROPERTY, "true"))) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game decisionGame = context.getDecisionGame();
        Game finalGame = context.getFinalGame();
        UUID playerId = context.getPlayerId();
        if (decisionGame == null || finalGame == null || playerId == null || countLivingOpponents(finalGame, playerId) <= 1) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int beforeDefense = estimateAvailableDefense(decisionGame, playerId);
        int afterDefense = estimateAvailableDefense(finalGame, playerId);
        int beforePressure = estimateIncomingPressure(decisionGame, playerId);
        int afterPressure = estimateIncomingPressure(finalGame, playerId);

        int defenseSpent = Math.max(0, beforeDefense - afterDefense);
        int beforeExposure = Math.max(0, beforePressure - beforeDefense);
        int afterExposure = Math.max(0, afterPressure - afterDefense);
        int exposureIncrease = Math.max(0, afterExposure - beforeExposure);
        if (defenseSpent <= 0 && exposureIncrease <= 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int reserveGap = Math.max(0, afterPressure + 4 - afterDefense);
        int risk = exposureIncrease * 5 + reserveGap * 2 + defenseSpent;
        if (risk <= 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int maxModifier = Math.max(0, Integer.getInteger(MAX_MODIFIER_PROPERTY, 50));
        int rawModifier = -Math.min(maxModifier, risk);
        int appliedModifier = globalApplyModifiers && Boolean.getBoolean(APPLY_PROPERTY) ? rawModifier : 0;

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        contributions.add(new AiStrategyScore.Contribution(
                "defense-reserve:exposure",
                rawModifier,
                "defense " + beforeDefense + " -> " + afterDefense
                        + ", pressure " + beforePressure + " -> " + afterPressure
                        + ", exposure " + beforeExposure + " -> " + afterExposure
        ));
        return AiStrategyScore.of(context.getBaseScore(), rawModifier, appliedModifier, contributions);
    }

    private static int estimateAvailableDefense(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int defense = 0;
        for (Permanent blocker : player.getAvailableBlockers(game)) {
            defense += estimateBlockerValue(blocker, game);
        }
        return defense;
    }

    private static int estimateIncomingPressure(Game game, UUID playerId) {
        int totalPressure = 0;
        List<Integer> opponentPressures = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.isInGame() || opponent.hasLost()) {
                continue;
            }
            int pressure = 0;
            for (Permanent attacker : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, opponentId, game)) {
                if (!attacker.canAttackInPrinciple(playerId, game)) {
                    continue;
                }
                pressure += estimateAttackerValue(attacker, game);
            }
            if (pressure > 0) {
                opponentPressures.add(pressure);
            }
        }
        opponentPressures.sort((left, right) -> Integer.compare(right, left));
        for (int i = 0; i < opponentPressures.size(); i++) {
            int pressure = opponentPressures.get(i);
            totalPressure += i == 0 ? pressure : pressure / 2;
        }
        return totalPressure;
    }

    private static int estimateBlockerValue(Permanent permanent, Game game) {
        if (permanent == null) {
            return 0;
        }
        int value = Math.max(0, permanent.getToughness().getValue())
                + Math.max(0, permanent.getPower().getValue());
        if (permanent.getAbilities(game).containsClass(DeathtouchAbility.class)) {
            value += 4;
        }
        if (permanent.getAbilities(game).containsClass(FirstStrikeAbility.class)
                || permanent.getAbilities(game).containsClass(DoubleStrikeAbility.class)) {
            value += 2;
        }
        if (permanent.getAbilities(game).containsClass(FlyingAbility.class)
                || permanent.getAbilities(game).containsClass(ReachAbility.class)) {
            value += 2;
        }
        return value;
    }

    private static int estimateAttackerValue(Permanent permanent, Game game) {
        if (permanent == null) {
            return 0;
        }
        int value = Math.max(0, permanent.getPower().getValue());
        if (permanent.getAbilities(game).containsClass(FlyingAbility.class)) {
            value += 2;
        }
        if (permanent.getAbilities(game).containsClass(DeathtouchAbility.class)) {
            value += 2;
        }
        if (permanent.getAbilities(game).containsClass(DoubleStrikeAbility.class)) {
            value += Math.max(0, permanent.getPower().getValue());
        }
        return value;
    }

    private static int countLivingOpponents(Game game, UUID playerId) {
        int count = 0;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.isInGame() && !opponent.hasLost()) {
                count++;
            }
        }
        return count;
    }
}
