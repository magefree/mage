
package mage.player.ai;

import java.util.UUID;
import mage.abilities.ActivatedAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.counters.Counter;
import mage.counters.CounterType;
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
public final class GameStateEvaluator {

    private static final Logger logger = Logger.getLogger(GameStateEvaluator.class);

    private static final int LIFE_FACTOR = Config.evaluatorLifeFactor;
    private static final int PERMANENT_FACTOR = Config.evaluatorPermanentFactor;
    private static final int CREATURE_FACTOR = Config.evaluatorCreatureFactor;
    private static final int HAND_FACTOR = Config.evaluatorHandFactor;

    public static final int WIN_SCORE = Integer.MAX_VALUE - 1;
    public static final int LOSE_SCORE = Integer.MIN_VALUE + 1;

    public static int evaluate(UUID playerId, Game game) {
        return evaluate(playerId, game, false);
    }

    public static int evaluate(UUID playerId, Game game, boolean ignoreTapped) {
        Player player = game.getPlayer(playerId);
        Player opponent = game.getPlayer(game.getOpponents(playerId).stream().findFirst().orElse(null));
        if (opponent == null) {
            return WIN_SCORE;
        }

        if (game.checkIfGameIsOver()) {
            if (player.hasLost() || opponent.hasWon()) {
                return LOSE_SCORE;
            }
            if (opponent.hasLost() || player.hasWon()) {
                return WIN_SCORE;
            }
        }
        int lifeScore = (player.getLife() - opponent.getLife()) * LIFE_FACTOR;
        int poisonScore = (opponent.getCounters().getCount(CounterType.POISON) - player.getCounters().getCount(CounterType.POISON)) * LIFE_FACTOR * 2;
        int permanentScore = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            permanentScore += evaluatePermanent(permanent, game, ignoreTapped);
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            permanentScore -= evaluatePermanent(permanent, game, ignoreTapped);
        }
        permanentScore *= PERMANENT_FACTOR;

        int handScore = 0;
        handScore = player.getHand().size() - opponent.getHand().size();
        handScore *= HAND_FACTOR;

        int score = lifeScore + poisonScore + permanentScore + handScore;
        if (logger.isDebugEnabled()) {
            logger.debug("game state for player " + player.getName() + " evaluated to- lifeScore:" + lifeScore + " permanentScore:" + permanentScore + " handScore:" + handScore + " total:" + score);
        }
        return score;
    }

    public static int evaluatePermanent(Permanent permanent, Game game, boolean ignoreTapped) {
        int value = 0;
        if (ignoreTapped) {
            value = 5;
        } else {
            value = permanent.isTapped() ? 4 : 5;
        }
        if (permanent.getCardType(game).contains(CardType.CREATURE)) {
            value += evaluateCreature(permanent, game) * CREATURE_FACTOR;
        }
        value += permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD).size();
        for (ActivatedAbility ability : permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
            if (!(ability instanceof ActivatedManaAbilityImpl) && ability.canActivate(ability.getControllerId(), game).canActivate()) {
                value += ability.getEffects().size();
            }
        }
        for (Counter counter : permanent.getCounters(game).values()) {
            if (!(counter instanceof BoostCounter)) {
                value += counter.getCount();
            }
        }
        value += permanent.getAbilities().getStaticAbilities(Zone.BATTLEFIELD).size();
        value += permanent.getAbilities().getTriggeredAbilities(Zone.BATTLEFIELD).size();
        value += permanent.getManaCost().manaValue();
        //TODO: add a difficulty to calculation to ManaCost - sort permanents by difficulty for casting when evaluating game states
        return value;
    }

    public static int evaluateCreature(Permanent creature, Game game) {
        int value = 0;
        value += creature.getPower().getValue();
        value += creature.getToughness().getValue();
//        if (creature.canAttack(game))
//            value += creature.getPower().getValue();
//        if (!creature.isTapped())
//            value += 2;
        value += creature.getAbilities().getEvasionAbilities().size();
        value += creature.getAbilities().getProtectionAbilities().size();
        value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) ? 1 : 0;
        value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId()) ? 2 : 0;
        value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId()) ? 1 : 0;
        return value;
    }

}
