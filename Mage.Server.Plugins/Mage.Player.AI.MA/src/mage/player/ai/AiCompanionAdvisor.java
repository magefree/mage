package mage.player.ai;

import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Read-only human-facing advice from the experimental AI evaluator.
 */
public final class AiCompanionAdvisor {

    private static final String TOP_N_PROPERTY = "xmage.ai.companion.top";
    private static final int CACHE_MAX_ENTRIES = 128;
    private static final Map<AdviceCacheKey, String> ADVICE_CACHE = new LinkedHashMap<AdviceCacheKey, String>(CACHE_MAX_ENTRIES, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<AdviceCacheKey, String> eldest) {
            return size() > CACHE_MAX_ENTRIES;
        }
    };

    private AiCompanionAdvisor() {
    }

    public static String getAdvice(Game game, UUID playerId) {
        if (game == null || playerId == null || game.isSimulation()) {
            return "";
        }
        Player player = game.getPlayer(playerId);
        if (player == null || !player.isInGame()) {
            return "";
        }

        AdviceCacheKey cacheKey = AdviceCacheKey.from(game, playerId);
        synchronized (ADVICE_CACHE) {
            String cached = ADVICE_CACHE.get(cacheKey);
            if (cached != null) {
                return cached;
            }
        }

        CompanionStrategicPlayer companion = new CompanionStrategicPlayer(playerId, player.getName());
        String combatAdvice = companion.calculateCombat(game);
        if (!combatAdvice.isEmpty()) {
            synchronized (ADVICE_CACHE) {
                ADVICE_CACHE.put(cacheKey, combatAdvice);
            }
            return combatAdvice;
        }

        if (!playerId.equals(game.getPriorityPlayerId())) {
            return "AI companion waiting for your priority.";
        }

        List<ComputerPlayer6.AiCandidateEvaluation> suggestions = companion.calculate(game);

        int top = Math.max(1, Integer.getInteger(TOP_N_PROPERTY, 3));
        StringBuilder message = new StringBuilder("AI companion suggestions");
        for (int i = 0; i < Math.min(top, suggestions.size()); i++) {
            ComputerPlayer6.AiCandidateEvaluation suggestion = suggestions.get(i);
            message.append('\n')
                    .append(i + 1)
                    .append(". ")
                    .append(suggestion.getActionText())
                    .append(" (")
                    .append(formatSigned(suggestion.getScore() - suggestion.getStartedScore()))
                    .append(" score");
            AiStrategyScore strategyScore = suggestion.getStrategyScore();
            if (strategyScore != null && strategyScore.hasStrategySignal()) {
                message.append(", strategy raw ")
                        .append(formatSigned(strategyScore.getRawModifier()))
                        .append("/applied ")
                        .append(formatSigned(strategyScore.getAppliedModifier()));
            }
            message.append(')');
            String detail = describeStrategicDetail(suggestion);
            if (!detail.isEmpty()) {
                message.append("\n   ").append(detail);
            }
        }
        if (suggestions.isEmpty()) {
            message.append('\n').append("Strategic search found no executable candidate actions.");
        }
        String advice = message.toString();
        synchronized (ADVICE_CACHE) {
            ADVICE_CACHE.put(cacheKey, advice);
        }
        return advice;
    }

    private static String describeStrategicDetail(ComputerPlayer6.AiCandidateEvaluation suggestion) {
        List<String> details = new ArrayList<>();
        AiStrategyScore strategyScore = suggestion.getStrategyScore();
        if (strategyScore != null && strategyScore.hasStrategySignal()) {
            details.add("base " + strategyScore.getBaseScore()
                    + ", adjusted " + strategyScore.getAdjustedScore());
            String strategy = strategyScore.getContributions()
                    .stream()
                    .limit(4)
                    .map(contribution -> contribution.getLabel()
                            + " "
                            + formatSigned(contribution.getValue())
                            + (contribution.getDetail().isEmpty() ? "" : ": " + contribution.getDetail()))
                    .collect(Collectors.joining("; "));
            if (!strategy.isEmpty()) {
                details.add(strategy);
            }
        }
        String bestLine = suggestion.getBestLine()
                .stream()
                .limit(3)
                .map(step -> step.action)
                .collect(Collectors.joining(" -> "));
        if (!bestLine.isEmpty()) {
            details.add("line: " + bestLine);
        }
        return String.join("; ", details);
    }

    private static final class CompanionStrategicPlayer extends ComputerPlayerMadStrategic {
        private CompanionStrategicPlayer(UUID id, String name) {
            super(id, Integer.getInteger("xmage.ai.companion.skill", 4));
            this.name = name;
        }

        private List<ComputerPlayer6.AiCandidateEvaluation> calculate(Game game) {
            return calculateAdvisoryCandidates(game);
        }

        private String calculateCombat(Game game) {
            return calculateCombatAdvice(game);
        }

        @Override
        protected AiDecisionTrace createDecisionTrace() {
            return NoOpAiDecisionTrace.INSTANCE;
        }
    }

    private static final class AdviceCacheKey {
        private final UUID gameId;
        private final UUID playerId;
        private final UUID priorityPlayerId;
        private final int stateHash;
        private final int turn;

        private AdviceCacheKey(UUID gameId, UUID playerId, UUID priorityPlayerId, int stateHash, int turn) {
            this.gameId = gameId;
            this.playerId = playerId;
            this.priorityPlayerId = priorityPlayerId;
            this.stateHash = stateHash;
            this.turn = turn;
        }

        private static AdviceCacheKey from(Game game, UUID playerId) {
            return new AdviceCacheKey(
                    game.getId(),
                    playerId,
                    game.getPriorityPlayerId(),
                    game.getState().getValue(true).hashCode(),
                    game.getTurnNum()
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AdviceCacheKey)) {
                return false;
            }
            AdviceCacheKey that = (AdviceCacheKey) o;
            return stateHash == that.stateHash
                    && turn == that.turn
                    && Objects.equals(gameId, that.gameId)
                    && Objects.equals(playerId, that.playerId)
                    && Objects.equals(priorityPlayerId, that.priorityPlayerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gameId, playerId, priorityPlayerId, stateHash, turn);
        }
    }

    private static String formatSigned(int value) {
        return value >= 0 ? "+" + value : Integer.toString(value);
    }
}
