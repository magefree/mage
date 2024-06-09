package mage.player.ai;

import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.ma.ArtificialScoringSystem;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;

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

    public static PlayerEvaluateScore evaluate(UUID playerId, Game game) {
        // TODO: add multi opponents support, so AI can take better actions
        Player player = game.getPlayer(playerId);
        Player opponent = game.getPlayer(game.getOpponents(playerId).stream().findFirst().orElse(null));
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
                int onePermScore = evaluatePermanent(permanent, game);
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
                int onePermScore = evaluatePermanent(permanent, game);
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

        int score = (playerLifeScore - opponentLifeScore)
                + (playerPermanentsScore - opponentPermanentsScore)
                + (playerHandScore - opponentHandScore);
        logger.debug(score
                + " total Score (life:" + (playerLifeScore - opponentLifeScore)
                + " permanents:" + (playerPermanentsScore - opponentPermanentsScore)
                + " hand:" + (playerHandScore - opponentHandScore) + ')');
        return new PlayerEvaluateScore(
                playerId,
                playerLifeScore, playerHandScore, playerPermanentsScore,
                opponentLifeScore, opponentHandScore, opponentPermanentsScore);
    }

    public static int evaluatePermanent(Permanent permanent, Game game) {
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
        value += ArtificialScoringSystem.getFixedPermanentScore(game, permanent)
                + ArtificialScoringSystem.getVariablePermanentScore(game, permanent);
        return value;
    }

    public static int evaluateCreature(Permanent creature, Game game) {
        int value = ArtificialScoringSystem.getFixedPermanentScore(game, creature)
                + ArtificialScoringSystem.getVariablePermanentScore(game, creature);
        return value;
    }

    public static class PlayerEvaluateScore {

        private UUID playerId;
        private int playerLifeScore = 0;
        private int playerHandScore = 0;
        private int playerPermanentsScore = 0;

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
            this.playerId = playerId;
            this.playerLifeScore = playerLifeScore;
            this.playerHandScore = playerHandScore;
            this.playerPermanentsScore = playerPermanentsScore;
            this.opponentLifeScore = opponentLifeScore;
            this.opponentHandScore = opponentHandScore;
            this.opponentPermanentsScore = opponentPermanentsScore;
        }

        public UUID getPlayerId() {
            return this.playerId;
        }

        public int getPlayerScore() {
            return playerLifeScore + playerHandScore + playerPermanentsScore;
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

        public String getPlayerInfoFull() {
            return "Life:" + playerLifeScore
                    + ", Hand:" + playerHandScore
                    + ", Perm:" + playerPermanentsScore;
        }

        public String getPlayerInfoShort() {
            return "L:" + playerLifeScore
                    + ",H:" + playerHandScore
                    + ",P:" + playerPermanentsScore;
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
