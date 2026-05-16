package mage.player.ai.scoring;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Explicit registry for built-in scoring modules.
 */
public final class AiScoreModuleRegistry {

    private final Map<AiScoreModuleId, AiScoreModuleFactory> factories = new EnumMap<>(AiScoreModuleId.class);

    public static AiScoreModuleRegistry createDefault() {
        AiScoreModuleRegistry registry = new AiScoreModuleRegistry();
        registry.register(AiScoreModuleId.DECK_STRATEGY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new DeckStrategyScoreModule(
                        config.getPlayerId(),
                        config.getMaxAppliedModifier(),
                        config.isApplyModifiers()
                );
            }
        });
        registry.register(AiScoreModuleId.FFA_TABLE_THREAT, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new FfaTableThreatScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.FFA_TARGET_PRIORITY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new FfaTargetPriorityScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.TARGET_QUALITY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new TargetQualityScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.COMBAT_PRESSURE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new CombatPressureScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.DEFENSE_RESERVE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new DefenseReserveScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.COMMANDER_LIFECYCLE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new CommanderLifecycleScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.PHASE_STRATEGY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new PhaseStrategyScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.GAME_STAGE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new GameStageScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.BOARD_ROLE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new BoardRoleScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.THREAT_PROJECTION, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new ThreatProjectionScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.RESOURCE_DEVELOPMENT, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new ResourceDevelopmentScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.COLOR_ACCESS, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new ColorAccessScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.HAND_PLAN, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new HandPlanScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.HAND_QUALITY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new HandQualityScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.INTERACTION_TIMING, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new InteractionTimingScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.SACRIFICE_AND_COST, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new SacrificeAndCostScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.SEARCH_AND_TUTOR, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new SearchAndTutorScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.TOKEN_SWARM, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new TokenSwarmScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.COMBO_PROGRESS, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new ComboProgressScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.OVERKILL_AND_WIN_CLOSURE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new OverkillAndWinClosureScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.POLITICAL_MEMORY, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new PoliticalMemoryScoreModule(config);
            }
        });
        registry.register(AiScoreModuleId.ALTERNATE_LOSS_PRESSURE, new AiScoreModuleFactory() {
            @Override
            public AiScoreModule create(AiScoreModuleConfig config) {
                return new AlternateLossPressureScoreModule(config);
            }
        });
        return registry;
    }

    public void register(AiScoreModuleId id, AiScoreModuleFactory factory) {
        if (id == null || factory == null) {
            return;
        }
        factories.put(id, factory);
    }

    public AiScoreModule create(AiScoreModuleId id, AiScoreModuleConfig config) {
        AiScoreModuleFactory factory = factories.get(id);
        if (factory == null) {
            return null;
        }
        return factory.create(config);
    }

    public List<AiScoreModule> createAll(List<AiScoreModuleId> ids, AiScoreModuleConfig config) {
        List<AiScoreModule> modules = new ArrayList<>();
        for (AiScoreModuleId id : ids) {
            AiScoreModule module = create(id, config);
            if (module != null) {
                modules.add(module);
            }
        }
        return modules;
    }
}
