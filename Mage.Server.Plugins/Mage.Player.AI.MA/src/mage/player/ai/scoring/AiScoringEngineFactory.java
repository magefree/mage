package mage.player.ai.scoring;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class AiScoringEngineFactory {

    private static final List<AiScoreModuleId> STRATEGIC_MODULES = Arrays.asList(
            AiScoreModuleId.DECK_STRATEGY,
            AiScoreModuleId.FFA_TABLE_THREAT,
            AiScoreModuleId.FFA_TARGET_PRIORITY,
            AiScoreModuleId.TARGET_QUALITY,
            AiScoreModuleId.COMBAT_PRESSURE,
            AiScoreModuleId.DEFENSE_RESERVE,
            AiScoreModuleId.COMMANDER_LIFECYCLE,
            AiScoreModuleId.PHASE_STRATEGY,
            AiScoreModuleId.GAME_STAGE,
            AiScoreModuleId.BOARD_ROLE,
            AiScoreModuleId.THREAT_PROJECTION,
            AiScoreModuleId.RESOURCE_DEVELOPMENT,
            AiScoreModuleId.COLOR_ACCESS,
            AiScoreModuleId.HAND_PLAN,
            AiScoreModuleId.HAND_QUALITY,
            AiScoreModuleId.INTERACTION_TIMING,
            AiScoreModuleId.SACRIFICE_AND_COST,
            AiScoreModuleId.SEARCH_AND_TUTOR,
            AiScoreModuleId.TOKEN_SWARM,
            AiScoreModuleId.COMBO_PROGRESS,
            AiScoreModuleId.OVERKILL_AND_WIN_CLOSURE,
            AiScoreModuleId.POLITICAL_MEMORY,
            AiScoreModuleId.ALTERNATE_LOSS_PRESSURE
    );

    private final AiScoreModuleRegistry registry;

    private AiScoringEngineFactory(AiScoreModuleRegistry registry) {
        this.registry = registry;
    }

    public static AiScoringEngineFactory createDefault() {
        return new AiScoringEngineFactory(AiScoreModuleRegistry.createDefault());
    }

    public AiScoringEngine createStrategicEngine(UUID playerId, int maxAppliedModifier, boolean applyModifiers) {
        AiScoreModuleConfig config = new AiScoreModuleConfig(playerId, maxAppliedModifier, applyModifiers);
        List<AiScoreModule> modules = registry.createAll(STRATEGIC_MODULES, config);
        return AiScoringEngine.of(modules, maxAppliedModifier);
    }
}
