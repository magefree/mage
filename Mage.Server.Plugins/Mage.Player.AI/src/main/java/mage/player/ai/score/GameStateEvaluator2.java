package mage.player.ai.score;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.StrategicRoleEvaluator;
import mage.player.ai.combo.ComboDetectionEngine;
import mage.player.ai.synergy.SynergyDetectionEngine;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.UUID;

/**
 * @author nantuko
 * <p>
 * This evaluator is only good for two player games
 */
public final class GameStateEvaluator2 {

    private static final Logger logger = Logger.getLogger(GameStateEvaluator2.class);

    public static final int WIN_GAME_SCORE = 100000000;
    public static final int LOSE_GAME_SCORE = -WIN_GAME_SCORE;

    public static final int HAND_CARD_SCORE = 5;

    // Thread-local combo detection engine for AI evaluation
    private static final ThreadLocal<ComboDetectionEngine> comboEngineHolder = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> comboPiecesHolder = new ThreadLocal<>();

    // Thread-local synergy detection engine for AI evaluation
    private static final ThreadLocal<SynergyDetectionEngine> synergyEngineHolder = new ThreadLocal<>();

    /**
     * Set the combo detection engine for current AI evaluation.
     * This enables combo-aware scoring during game state evaluation.
     */
    public static void setComboEngine(ComboDetectionEngine engine, Set<String> comboPieces) {
        comboEngineHolder.set(engine);
        comboPiecesHolder.set(comboPieces);
    }

    /**
     * Set the synergy detection engine for current AI evaluation.
     * This enables synergy-aware scoring during game state evaluation.
     */
    public static void setSynergyEngine(SynergyDetectionEngine engine) {
        synergyEngineHolder.set(engine);
    }

    /**
     * Clear the combo engine after AI evaluation completes.
     */
    public static void clearComboEngine() {
        comboEngineHolder.remove();
        comboPiecesHolder.remove();
    }

    /**
     * Clear the synergy engine after AI evaluation completes.
     */
    public static void clearSynergyEngine() {
        synergyEngineHolder.remove();
    }

    /**
     * Clear all engines after AI evaluation completes.
     */
    public static void clearAllEngines() {
        clearComboEngine();
        clearSynergyEngine();
    }

    /**
     * Get the current synergy engine (for use by CombatUtil).
     */
    public static SynergyDetectionEngine getSynergyEngine() {
        return synergyEngineHolder.get();
    }

    public static PlayerEvaluateScore evaluate(UUID playerId, Game game) {
        return evaluate(playerId, game, true);
    }

    public static PlayerEvaluateScore evaluate(UUID playerId, Game game, boolean useCombatPermanentScore) {
        // TODO: add multi opponents support, so AI can take better actions
        Player player = game.getPlayer(playerId);
        // must find all leaved opponents
        Player opponent = game.getPlayer(game.getOpponents(playerId, false).stream().findFirst().orElse(null));
        if (opponent == null) {
            return new PlayerEvaluateScore(playerId, WIN_GAME_SCORE);
        }

        if (game.checkIfGameIsOver()) {
            if (player.hasLost()
                    || opponent.hasWon()) {
                return new PlayerEvaluateScore(playerId, LOSE_GAME_SCORE);
            }
            if (opponent.hasLost()
                    || player.hasWon()) {
                return new PlayerEvaluateScore(playerId, WIN_GAME_SCORE);
            }
        }

        int playerLifeScore = 0;
        int opponentLifeScore = 0;
        if (player.getLife() <= 0) { // we don't want a tie
            playerLifeScore = ArtificialScoringSystem.LOSE_GAME_SCORE;
        } else if (opponent.getLife() <= 0) {
            playerLifeScore = ArtificialScoringSystem.WIN_GAME_SCORE;
        } else {
            playerLifeScore = ArtificialScoringSystem.getLifeScore(player.getLife());
            opponentLifeScore = ArtificialScoringSystem.getLifeScore(opponent.getLife()); // TODO: minus
        }

        int playerPermanentsScore = 0;
        int opponentPermanentsScore = 0;
        try {
            StringBuilder sbPlayer = new StringBuilder();
            StringBuilder sbOpponent = new StringBuilder();

            // add values of player
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                int onePermScore = evaluatePermanent(permanent, game, useCombatPermanentScore);
                playerPermanentsScore += onePermScore;
                if (logger.isDebugEnabled()) {
                    sbPlayer.append(permanent.getName()).append('[').append(onePermScore).append("] ");
                }
            }
            if (logger.isDebugEnabled()) {
                sbPlayer.insert(0, playerPermanentsScore + " - ");
                sbPlayer.insert(0, "Player..: ");
                logger.debug(sbPlayer);
            }

            // add values of opponent
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
                int onePermScore = evaluatePermanent(permanent, game, useCombatPermanentScore);
                opponentPermanentsScore += onePermScore;
                if (logger.isDebugEnabled()) {
                    sbOpponent.append(permanent.getName()).append('[').append(onePermScore).append("] ");
                }
            }
            if (logger.isDebugEnabled()) {
                sbOpponent.insert(0, opponentPermanentsScore + " - ");
                sbOpponent.insert(0, "Opponent: ");
                logger.debug(sbOpponent);
            }
        } catch (Throwable t) {
        }

        // TODO: add card evaluator like permanent evaluator
        // - same card on battlefield must score x2 compared to hand, so AI will want to play it;
        // - other zones must score cards same way, example: battlefield = x, hand = x * 0.1, graveyard = x * 0.5, exile = x * 0.3
        // - possible bug in wrong score: instant and sorcery on hand will be more valuable compared to other zones,
        //   so AI will keep it in hand. Possible fix: look at card type and apply zones multipliers due special
        //   table like:
        //   * battlefield needs in creatures and enchantments/auras;
        //   * hand needs in instants and sorceries
        //   * graveyard needs in anything after battlefield and hand;
        //   * exile needs in nothing;
        //   * commander zone needs in nothing;
        // - additional improve: use revealed data to score opponent's hand:
        //   * known card by card evaluator;
        //   * unknown card by max value (so AI will use reveal to make opponent's total score lower -- is it helps???)
        int playerHandScore = player.getHand().size() * HAND_CARD_SCORE;
        int opponentHandScore = opponent.getHand().size() * HAND_CARD_SCORE;

        // Add combo progress bonus
        int playerComboScore = evaluateComboProgress(playerId, game);

        // Add synergy progress bonus
        int playerSynergyScore = evaluateSynergyProgress(playerId, game);

        // Apply strategic role-based weight adjustments
        // Beatdown: values dealing damage more, cares less about own life
        // Control: values preserving life and board presence more
        int role = StrategicRoleEvaluator.determineRole(player, opponent, game);

        double ownLifeWeight = 1.0;
        double opponentLifeWeight = 1.0;
        double permanentWeight = 1.0;

        if (role == StrategicRoleEvaluator.ROLE_BEATDOWN) {
            // Beatdown: prioritize dealing damage, willing to trade life
            opponentLifeWeight = 1.5;    // Prioritize reducing opponent life
            ownLifeWeight = 0.7;         // More willing to trade own life
            permanentWeight = 0.8;       // Less concerned about board trades
        } else if (role == StrategicRoleEvaluator.ROLE_CONTROL) {
            // Control: prioritize preserving life and board presence
            ownLifeWeight = 1.3;         // Prioritize preserving own life
            permanentWeight = 1.2;       // Value board presence more
            opponentLifeWeight = 0.8;    // Less focused on opponent's life
        }

        int adjustedLifeDiff = (int) ((playerLifeScore * ownLifeWeight) - (opponentLifeScore * opponentLifeWeight));
        int adjustedPermanentDiff = (int) ((playerPermanentsScore - opponentPermanentsScore) * permanentWeight);

        // Combine combo and synergy scores
        int playerComboAndSynergyScore = playerComboScore + playerSynergyScore;

        int score = adjustedLifeDiff
                + adjustedPermanentDiff
                + (playerHandScore - opponentHandScore)
                + playerComboAndSynergyScore;

        if (logger.isDebugEnabled()) {
            logger.debug(score
                    + " total Score (role:" + StrategicRoleEvaluator.getRoleName(role)
                    + " life:" + adjustedLifeDiff
                    + " permanents:" + adjustedPermanentDiff
                    + " hand:" + (playerHandScore - opponentHandScore)
                    + " combo:" + playerComboScore
                    + " synergy:" + playerSynergyScore + ')');
        }
        return new PlayerEvaluateScore(
                playerId,
                playerLifeScore, playerHandScore, playerPermanentsScore,
                opponentLifeScore, opponentHandScore, opponentPermanentsScore,
                playerComboAndSynergyScore);
    }

    /**
     * Evaluate combo progress and return a score bonus.
     * Rewards having combo pieces and gives large bonus when combo is executable.
     */
    private static int evaluateComboProgress(UUID playerId, Game game) {
        ComboDetectionEngine engine = comboEngineHolder.get();
        if (engine == null) {
            return 0;
        }
        return engine.calculateComboScoreBonus(game, playerId);
    }

    /**
     * Evaluate synergy progress and return a score bonus.
     * Rewards having synergy enablers with payoffs and gives larger bonus when synergy is active.
     */
    private static int evaluateSynergyProgress(UUID playerId, Game game) {
        SynergyDetectionEngine engine = synergyEngineHolder.get();
        if (engine == null) {
            return 0;
        }
        return engine.calculateSynergyScoreBonus(game, playerId);
    }

    public static int evaluatePermanent(Permanent permanent, Game game, boolean useCombatPermanentScore) {
        // prevent AI from attaching bad auras to its own permanents ex: Brainwash and Demonic Torment (no immediate penalty on the battlefield)
        int value = 0;
        if (!permanent.getAttachments().isEmpty()) {
            for (UUID attachmentId : permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                for (Ability a : attachment.getAbilities(game)) {
                    for (Effect e : a.getEffects()) {
                        if (e.getOutcome().equals(Outcome.Detriment)
                                && attachment.getControllerId().equals(permanent.getControllerId())) {
                            value -= 1000;  // seems to work well ; -300 is not effective enough
                        }
                    }
                }
            }
        }
        value += ArtificialScoringSystem.getFixedPermanentScore(game, permanent);
        value += ArtificialScoringSystem.getDynamicPermanentScore(game, permanent);
        if (useCombatPermanentScore) {
            value += ArtificialScoringSystem.getCombatPermanentScore(game, permanent);
        }
        return value;
    }

    public static class PlayerEvaluateScore {

        private UUID playerId;
        private int playerLifeScore = 0;
        private int playerHandScore = 0;
        private int playerPermanentsScore = 0;
        private int playerComboScore = 0;

        private int opponentLifeScore = 0;
        private int opponentHandScore = 0;
        private int opponentPermanentsScore = 0;

        private int specialScore = 0; // special score (ignore all others, e.g. for win/lose game states)

        public PlayerEvaluateScore(UUID playerId, int specialScore) {
            this.playerId = playerId;
            this.specialScore = specialScore;
        }

        public PlayerEvaluateScore(UUID playerId,
                                   int playerLifeScore, int playerHandScore, int playerPermanentsScore,
                                   int opponentLifeScore, int opponentHandScore, int opponentPermanentsScore) {
            this(playerId, playerLifeScore, playerHandScore, playerPermanentsScore,
                    opponentLifeScore, opponentHandScore, opponentPermanentsScore, 0);
        }

        public PlayerEvaluateScore(UUID playerId,
                                   int playerLifeScore, int playerHandScore, int playerPermanentsScore,
                                   int opponentLifeScore, int opponentHandScore, int opponentPermanentsScore,
                                   int playerComboScore) {
            this.playerId = playerId;
            this.playerLifeScore = playerLifeScore;
            this.playerHandScore = playerHandScore;
            this.playerPermanentsScore = playerPermanentsScore;
            this.opponentLifeScore = opponentLifeScore;
            this.opponentHandScore = opponentHandScore;
            this.opponentPermanentsScore = opponentPermanentsScore;
            this.playerComboScore = playerComboScore;
        }

        public UUID getPlayerId() {
            return this.playerId;
        }

        public int getPlayerScore() {
            return playerLifeScore + playerHandScore + playerPermanentsScore + playerComboScore;
        }

        public int getOpponentScore() {
            return opponentLifeScore + opponentHandScore + opponentPermanentsScore;
        }

        public int getTotalScore() {
            if (specialScore != 0) {
                return specialScore;
            } else {
                return getPlayerScore() - getOpponentScore();
            }
        }

        public int getPlayerLifeScore() {
            return playerLifeScore;
        }

        public int getPlayerHandScore() {
            return playerHandScore;
        }

        public int getPlayerPermanentsScore() {
            return playerPermanentsScore;
        }

        public int getPlayerComboScore() {
            return playerComboScore;
        }

        public String getPlayerInfoFull() {
            String info = "Life:" + playerLifeScore
                    + ", Hand:" + playerHandScore
                    + ", Perm:" + playerPermanentsScore;
            if (playerComboScore > 0) {
                info += ", Combo:" + playerComboScore;
            }
            return info;
        }

        public String getPlayerInfoShort() {
            String info = "L:" + playerLifeScore
                    + ",H:" + playerHandScore
                    + ",P:" + playerPermanentsScore;
            if (playerComboScore > 0) {
                info += ",C:" + playerComboScore;
            }
            return info;
        }

        public String getOpponentInfoFull() {
            return "Life:" + opponentLifeScore
                    + ", Hand:" + opponentHandScore
                    + ", Perm:" + opponentPermanentsScore;
        }

        public String getOpponentInfoShort() {
            return "L:" + opponentLifeScore
                    + ",H:" + opponentHandScore
                    + ",P:" + opponentPermanentsScore;
        }
    }
}
