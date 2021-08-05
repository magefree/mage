package mage.util.trace;

import java.util.UUID;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public class TraceInfo {
    public String info;
    public String playerName;
    public String sourceName;
    public String rule;
    public UUID abilityId;
    public UUID effectId;
    public Duration duration;
    public long order;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public UUID getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(UUID abilityId) {
        this.abilityId = abilityId;
    }

    public UUID getEffectId() {
        return effectId;
    }

    public void setEffectId(UUID effectId) {
        this.effectId = effectId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }
    
    
}
