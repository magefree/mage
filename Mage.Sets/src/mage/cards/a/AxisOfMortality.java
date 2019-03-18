
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class AxisOfMortality extends CardImpl {

    public AxisOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // At the beginning of your upkeep, you may have two target players exchange life totals.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AxisOfMortalityEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public AxisOfMortality(final AxisOfMortality card) {
        super(card);
    }

    @Override
    public AxisOfMortality copy() {
        return new AxisOfMortality(this);
    }
}

class AxisOfMortalityEffect extends OneShotEffect {

    public AxisOfMortalityEffect() {
        super(Outcome.Neutral);
        this.staticText = "two target players exchange life totals";
    }

    public AxisOfMortalityEffect(final AxisOfMortalityEffect effect) {
        super(effect);
    }

    @Override
    public AxisOfMortalityEffect copy() {
        return new AxisOfMortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(source.getFirstTarget());
        Player player2 = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player1 != null && player2 != null) {
            int lifePlayer1 = player1.getLife();
            int lifePlayer2 = player2.getLife();

            if (lifePlayer1 == lifePlayer2) {
                return false;
            }

            if (!player1.isLifeTotalCanChange() || !player2.isLifeTotalCanChange()) {
                return false;
            }

            // 20110930 - 118.7, 118.8
            if (lifePlayer1 < lifePlayer2 && (!player1.isCanGainLife() || !player2.isCanLoseLife())) {
                return false;
            }

            if (lifePlayer1 > lifePlayer2 && (!player1.isCanLoseLife() || !player2.isCanGainLife())) {
                return false;
            }

            player1.setLife(lifePlayer2, game, source);
            player2.setLife(lifePlayer1, game, source);
            return true;
        }
        return false;
    }
}
