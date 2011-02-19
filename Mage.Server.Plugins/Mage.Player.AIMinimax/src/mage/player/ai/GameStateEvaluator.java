/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.player.ai;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * this evaluator is only good for two player games
 *
 */
public class GameStateEvaluator {

	private final static transient Logger logger = Logger.getLogger(GameStateEvaluator.class);

	private static final int LIFE_FACTOR = Config.evaluatorLifeFactor;
	private static final int PERMANENT_FACTOR = Config.evaluatorPermanentFactor;
	private static final int CREATURE_FACTOR = Config.evaluatorCreatureFactor;
	private static final int HAND_FACTOR = Config.evaluatorHandFactor;

	public static int evaluate(UUID playerId, Game game) {
		Player player = game.getPlayer(playerId);
		Player opponent = game.getPlayer(game.getOpponents(playerId).iterator().next());
		if (game.isGameOver()) {
			if (player.hasLost() || opponent.hasWon())
				return Integer.MIN_VALUE;
			if (opponent.hasLost() || player.hasWon())
				return Integer.MAX_VALUE;
		}
		int lifeScore = (player.getLife() - opponent.getLife()) * LIFE_FACTOR;
		int permanentScore = 0;
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
			permanentScore += evaluatePermanent(permanent, game);
		}
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(opponent.getId())) {
			permanentScore -= evaluatePermanent(permanent, game);
		}
		permanentScore *= PERMANENT_FACTOR;

		int handScore = 0;
		handScore = player.getHand().size() - opponent.getHand().size();
		handScore *= HAND_FACTOR;

		int score = lifeScore + permanentScore + handScore;
		if (logger.isDebugEnabled())
			logger.debug("game state evaluated to- lifeScore:" + lifeScore + " permanentScore:" + permanentScore + " handScore:" + handScore + " total:" + score);
		return score;
	}

	public static int evaluatePermanent(Permanent permanent, Game game) {
		int value = permanent.isTapped()?4:5;
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
		//TODO: add a difficulty to calculation to ManaCost - sort permanents by difficulty for casting when evaluating game states
		return value;
	}

	public static int evaluateCreature(Permanent creature, Game game) {
		int value = 0;
		value += creature.getPower().getValue();
		value += creature.getToughness().getValue();
//		if (creature.canAttack(game))
//			value += creature.getPower().getValue();
//		if (!creature.isTapped())
//			value += 2;
		value += creature.getAbilities().getEvasionAbilities().size();
		value += creature.getAbilities().getProtectionAbilities().size();
		value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
		value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
		value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;
		return value;
	}

}
