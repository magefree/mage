package mage.player.ai.scoring;

import java.util.UUID;

public final class FfaOpponentThreat {

    private final UUID playerId;
    private final String playerName;
    private final int life;
    private final int handSize;
    private final int creatureCount;
    private final int creaturePower;
    private final int evasivePower;
    private final int immediateAttackPower;
    private final int score;

    FfaOpponentThreat(UUID playerId,
                      String playerName,
                      int life,
                      int handSize,
                      int creatureCount,
                      int creaturePower,
                      int evasivePower,
                      int immediateAttackPower,
                      int score) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.life = life;
        this.handSize = handSize;
        this.creatureCount = creatureCount;
        this.creaturePower = creaturePower;
        this.evasivePower = evasivePower;
        this.immediateAttackPower = immediateAttackPower;
        this.score = score;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getLife() {
        return life;
    }

    public int getHandSize() {
        return handSize;
    }

    public int getCreatureCount() {
        return creatureCount;
    }

    public int getCreaturePower() {
        return creaturePower;
    }

    public int getEvasivePower() {
        return evasivePower;
    }

    public int getImmediateAttackPower() {
        return immediateAttackPower;
    }

    public int getScore() {
        return score;
    }

    public String describe() {
        return "life " + life
                + ", hand " + handSize
                + ", creatures " + creatureCount
                + ", power " + creaturePower
                + ", evasive " + evasivePower
                + ", pressure " + immediateAttackPower;
    }
}
