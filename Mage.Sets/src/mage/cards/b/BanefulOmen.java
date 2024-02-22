package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BanefulOmen extends CardImpl {

    public BanefulOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}{B}");

        // At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new BanefulOmenEffect(), true));
    }

    private BanefulOmen(final BanefulOmen card) {
        super(card);
    }

    @Override
    public BanefulOmen copy() {
        return new BanefulOmen(this);
    }

}

class BanefulOmenEffect extends OneShotEffect {

    BanefulOmenEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If you do, each opponent loses life equal to that card's mana value";
    }

    private BanefulOmenEffect(final BanefulOmenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards("Baneful Omen", new CardsImpl(card), game);

        int mv = card.getManaValue();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(mv, game, source, false);
            }
        }
        return true;
    }

    @Override
    public BanefulOmenEffect copy() {
        return new BanefulOmenEffect(this);
    }
}
