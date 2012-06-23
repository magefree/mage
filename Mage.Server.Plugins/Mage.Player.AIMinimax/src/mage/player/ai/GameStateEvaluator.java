/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
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
public class GameStateEvaluator {

    private final static transient Logger logger = Logger.getLogger(GameStateEvaluator.class);

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
        Player opponent = game.getPlayer(game.getOpponents(playerId).iterator().next());
        if (game.isGameOver()) {
            if (player.hasLost() || opponent.hasWon())
                return LOSE_SCORE;
            if (opponent.hasLost() || player.hasWon())
                return WIN_SCORE;
        }
        int lifeScore = (player.getLife() - opponent.getLife()) * LIFE_FACTOR;
        int poisonScore = (opponent.getCounters().getCount(CounterType.POISON) - player.getCounters().getCount(CounterType.POISON)) * LIFE_FACTOR * 2;
        int permanentScore = 0;
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
            permanentScore += evaluatePermanent(permanent, game, ignoreTapped);
        }
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            permanentScore -= evaluatePermanent(permanent, game, ignoreTapped);
        }
        permanentScore *= PERMANENT_FACTOR;

        int handScore = 0;
        handScore = player.getHand().size() - opponent.getHand().size();
        handScore *= HAND_FACTOR;

        int score = lifeScore + poisonScore + permanentScore + handScore;
        if (logger.isDebugEnabled())
            logger.debug("game state for player " + player.getName() + " evaluated to- lifeScore:" + lifeScore + " permanentScore:" + permanentScore + " handScore:" + handScore + " total:" + score);
        return score;
    }

    public static int evaluatePermanent(Permanent permanent, Game game, boolean ignoreTapped) {
        int value = 0;
        if (ignoreTapped)
            value = 5;
        else
            value = permanent.isTapped()?4:5;
        if (permanent.getCardType().contains(CardType.CREATURE)) {
            value += evaluateCreature(permanent, game) * CREATURE_FACTOR;
        }
        value += permanent.getAbilities().getManaAbilities(Zone.BATTLEFIELD).size();
        for (ActivatedAbility ability: permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
            if (!(ability instanceof ManaAbility) && ability.canActivate(ability.getControllerId(), game))
                value += ability.getEffects().size();
        }
        for (Counter counter: permanent.getCounters().values()) {
            if (!(counter instanceof BoostCounter)) {
                value += counter.getCount();
            }
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
//        if (creature.canAttack(game))
//            value += creature.getPower().getValue();
//        if (!creature.isTapped())
//            value += 2;
        value += creature.getAbilities().getEvasionAbilities().size();
        value += creature.getAbilities().getProtectionAbilities().size();
        value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
        value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
        value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;
        return value;
    }

}
