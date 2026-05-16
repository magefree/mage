package mage.player.ai.scoring;

public interface AiScoreModuleFactory {

    AiScoreModule create(AiScoreModuleConfig config);
}
