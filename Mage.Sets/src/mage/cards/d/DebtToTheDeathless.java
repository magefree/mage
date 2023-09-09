
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DebtToTheDeathless extends CardImpl {

    public DebtToTheDeathless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}{B}{B}");

        // Each opponent loses two times X life. You gain life equal to the life lost this way.
        this.getSpellAbility().addEffect(new DebtToTheDeathlessEffect());
    }

    private DebtToTheDeathless(final DebtToTheDeathless card) {
        super(card);
    }

    @Override
    public DebtToTheDeathless copy() {
        return new DebtToTheDeathless(this);
    }
}

class DebtToTheDeathlessEffect extends OneShotEffect {

    public DebtToTheDeathlessEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent loses two times X life. You gain life equal to the life lost this way";
    }

    private DebtToTheDeathlessEffect(final DebtToTheDeathlessEffect effect) {
        super(effect);
    }

    @Override
    public DebtToTheDeathlessEffect copy() {
        return new DebtToTheDeathlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int lifeLost = 0;
            int xValue = source.getManaCostsToPay().getX();
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    lifeLost += opponent.loseLife(xValue * 2, game, source, false);
                }
            }
            controller.gainLife(lifeLost, game, source);
            return true;
        }
        return false;
    }
}
