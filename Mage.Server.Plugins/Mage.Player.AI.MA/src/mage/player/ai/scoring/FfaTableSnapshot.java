package mage.player.ai.scoring;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class FfaTableSnapshot {

    private final List<FfaOpponentThreat> opponents;
    private final Map<UUID, FfaOpponentThreat> byPlayerId;
    private final int maxThreatScore;

    private FfaTableSnapshot(List<FfaOpponentThreat> opponents) {
        this.opponents = Collections.unmodifiableList(new ArrayList<>(opponents));
        this.byPlayerId = new HashMap<>();
        int maxScore = 0;
        for (FfaOpponentThreat opponent : opponents) {
            byPlayerId.put(opponent.getPlayerId(), opponent);
            maxScore = Math.max(maxScore, opponent.getScore());
        }
        this.maxThreatScore = maxScore;
    }

    public static FfaTableSnapshot fromGame(Game game, UUID playerId) {
        if (game == null || playerId == null) {
            return new FfaTableSnapshot(Collections.emptyList());
        }
        List<FfaOpponentThreat> opponents = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !opponent.isInGame() || opponent.hasLost()) {
                continue;
            }
            opponents.add(toThreat(game, playerId, opponent));
        }
        opponents.sort(Comparator.comparingInt(FfaOpponentThreat::getScore).reversed());
        return new FfaTableSnapshot(opponents);
    }

    public List<FfaOpponentThreat> getOpponents() {
        return opponents;
    }

    public FfaOpponentThreat getOpponent(UUID playerId) {
        return byPlayerId.get(playerId);
    }

    public int getMaxThreatScore() {
        return maxThreatScore;
    }

    public int getRank(UUID playerId) {
        for (int i = 0; i < opponents.size(); i++) {
            if (opponents.get(i).getPlayerId().equals(playerId)) {
                return i + 1;
            }
        }
        return 0;
    }

    private static FfaOpponentThreat toThreat(Game game, UUID playerId, Player opponent) {
        int creatureCount = 0;
        int creaturePower = 0;
        int evasivePower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            if (!permanent.isCreature(game)) {
                continue;
            }
            creatureCount++;
            int power = Math.max(0, permanent.getPower().getValue());
            creaturePower += power;
            if (isEvasive(permanent, game)) {
                evasivePower += power;
            }
        }

        int immediateAttackPower = 0;
        for (Permanent attacker : opponent.getAvailableAttackers(playerId, game)) {
            immediateAttackPower += Math.max(0, attacker.getPower().getValue());
        }

        int life = Math.max(0, opponent.getLife());
        int handSize = opponent.getHand().size();
        int score = creaturePower * 4
                + evasivePower * 3
                + immediateAttackPower * 5
                + handSize * 6
                + life * 2
                + creatureCount * 2;
        return new FfaOpponentThreat(
                opponent.getId(),
                opponent.getName(),
                opponent.getLife(),
                handSize,
                creatureCount,
                creaturePower,
                evasivePower,
                immediateAttackPower,
                score
        );
    }

    private static boolean isEvasive(Permanent permanent, Game game) {
        return permanent.getAbilities(game).containsClass(FlyingAbility.class)
                || permanent.getAbilities(game).containsClass(MenaceAbility.class)
                || permanent.getAbilities(game).containsClass(TrampleAbility.class);
    }
}
