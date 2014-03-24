/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.player.ai;

import java.util.UUID;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.ma.ArtificialScoringSystem;
import mage.players.Player;

/**
 *
 * @author nantuko
 *
 * this evaluator is only good for two player games
 *
 */
public class GameStateEvaluator2 {

    private static final transient org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GameStateEvaluator2.class);

    public static final int WIN_GAME_SCORE = 100000000;
    public static final int LOSE_GAME_SCORE = -WIN_GAME_SCORE;

    private static final int LIFE_FACTOR = Config2.evaluatorLifeFactor;
    private static final int PERMANENT_FACTOR = Config2.evaluatorPermanentFactor;
    private static final int CREATURE_FACTOR = Config2.evaluatorCreatureFactor;
    private static final int HAND_FACTOR = Config2.evaluatorHandFactor;

    public static int evaluate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        Player opponent = game.getPlayer(game.getOpponents(playerId).iterator().next());
        if (game.isGameOver()) {
            if (player.hasLost() || opponent.hasWon()) {
                return LOSE_GAME_SCORE;
            }
            if (opponent.hasLost() || player.hasWon()) {
                return WIN_GAME_SCORE;
            }
        }
        //int lifeScore = (player.getLife() - opponent.getLife()) * LIFE_FACTOR;

        //int lifeScore = (ArtificialScoringSystem.getLifeScore(player.getLife()) - opponent.getLife()) * LIFE_FACTOR;
        int lifeScore = 0;
        if (player.getLife() <= 0) { // we don't want a tie
            lifeScore = ArtificialScoringSystem.LOSE_GAME_SCORE;
        } else if (opponent.getLife() <= 0) {
            lifeScore = ArtificialScoringSystem.WIN_GAME_SCORE;
        } else {
            lifeScore = ArtificialScoringSystem.getLifeScore(player.getLife()) - ArtificialScoringSystem.getLifeScore(opponent.getLife());
        }

        int permanentScore = 0;
        int playerScore = 0;
        int opponentScore = 0;
        try {
            StringBuilder sbPlayer = new StringBuilder();
            StringBuilder sbOpponent = new StringBuilder();
            // add values of player
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
                int onePermScore = evaluatePermanent(permanent, game);
                playerScore += onePermScore;
                if (logger.isDebugEnabled()) {
                    sbPlayer.append(permanent.getName()).append("[").append(onePermScore).append("] ");
                }
            }
            if (logger.isDebugEnabled()) {                
                sbPlayer.insert(0, playerScore + " - ");
                sbPlayer.insert(0, "Player..: ");
                logger.debug(sbPlayer);
            }

            // add values of opponent
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(opponent.getId())) {
                int onePermScore = evaluatePermanent(permanent, game);
                opponentScore += onePermScore;
                if (logger.isDebugEnabled()) {
                    sbOpponent.append(permanent.getName()).append("[").append(onePermScore).append("] ");
                }
            }
            if (logger.isDebugEnabled()) {
                sbOpponent.insert(0, opponentScore + " - ");
                sbOpponent.insert(0, "Opponent: ");
                
                logger.debug(sbOpponent);
            }
            permanentScore = playerScore - opponentScore;
        } catch (Throwable t) {
        }
        //permanentScore *= PERMANENT_FACTOR;

        int handScore;
        handScore = player.getHand().size() - opponent.getHand().size();
        handScore *= 5;

        int score = lifeScore + permanentScore + handScore;
        logger.debug(score + " total Score (life:" + lifeScore + " permanents:" + permanentScore + " hand:" + handScore +")");

        return score;
    }

    public static int evaluatePermanent(Permanent permanent, Game game) {
        /*int value = permanent.isTapped()?4:5;
        if (permanent.getCardType().contains(CardType.CREATURE)) {
            value += evaluateCreature(permanent, game) * CREATURE_FACTOR;
        }
        value += permanent.getAbilities().getManaAbilities(Zone.BATTLEFIELD).size();
        for (ActivatedAbility ability: permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
            if (!(ability instanceof ManaAbility) && ability.canActivate(ability.getControllerId(), game))
                value += ability.getEffects().size();
        }
        value += permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD).size();
        value += permanent.getAbilities().getTriggeredAbilities(Zone.BATTLEFIELD).size();
        value += permanent.getManaCost().convertedManaCost();
        */

        int value = ArtificialScoringSystem.getFixedPermanentScore(game, permanent)
                    + ArtificialScoringSystem.getVariablePermanentScore(game, permanent);

        //TODO: add a difficulty to calculation to ManaCost - sort permanents by difficulty for casting when evaluating game states
        return value;
    }

    public static int evaluateCreature(Permanent creature, Game game) {
        int value = ArtificialScoringSystem.getFixedPermanentScore(game, creature)
                    + ArtificialScoringSystem.getVariablePermanentScore(game, creature);

        /*int value = 0;
        value += creature.getPower().getValue();
        value += creature.getToughness().getValue();
//        if (creature.canAttack(game))
//            value += creature.getPower().getValue();
//        if (!creature.isTapped())
//            value += 2;
        value += creature.getAbilities().getEvasionAbilities().size();
        value += creature.getAbilities().getProtectionAbilities().size();
        value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
        value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
        value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;*/

        return value;
    }

}
