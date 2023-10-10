
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class BalanceOfPower extends CardImpl {

    public BalanceOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");


        // If target opponent has more cards in hand than you, draw cards equal to the difference.
        this.getSpellAbility().addEffect(new BalanceOfPowerEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BalanceOfPower(final BalanceOfPower card) {
        super(card);
    }

    @Override
    public BalanceOfPower copy() {
        return new BalanceOfPower(this);
    }
}

class BalanceOfPowerEffect extends OneShotEffect {

    public BalanceOfPowerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "If target opponent has more cards in hand than you, draw cards equal to the difference";
    }

    private BalanceOfPowerEffect(final BalanceOfPowerEffect effect) {
        super(effect);
    }

    @Override
    public BalanceOfPowerEffect copy() {
        return new BalanceOfPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());

        if (opponent != null && player != null && opponent.getHand().size() > player.getHand().size()) {
            player.drawCards(opponent.getHand().size() - player.getHand().size(), source, game);
            return true;
        }

        return false;
    }
}
