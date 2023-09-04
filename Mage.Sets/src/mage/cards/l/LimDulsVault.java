package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LimDulsVault extends CardImpl {

    public LimDulsVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Look at the top five cards of your library. As many times as you choose, you may pay 1 life, put those cards on the bottom of your library in any order, then look at the top five cards of your library. Then shuffle your library and put the last cards you looked at this way on top of it in any order.
        this.getSpellAbility().addEffect(new LimDulsVaultEffect());
    }

    private LimDulsVault(final LimDulsVault card) {
        super(card);
    }

    @Override
    public LimDulsVault copy() {
        return new LimDulsVault(this);
    }
}

class LimDulsVaultEffect extends OneShotEffect {

    public LimDulsVaultEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top five cards of your library. As many times as you choose, "
                + "you may pay 1 life, put those cards on the bottom of your library in any order, then look at the top five cards of your library. "
                + "Then shuffle and put the last cards you looked at this way on top of it in any order";
    }

    private LimDulsVaultEffect(final LimDulsVaultEffect effect) {
        super(effect);
    }

    @Override
    public LimDulsVaultEffect copy() {
        return new LimDulsVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean doAgain;
        do {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
            player.lookAtCards(source, null, cards, game);

            doAgain = player.chooseUse(outcome, "Pay 1 life and look at the next 5 cards?", source, game);
            if (doAgain) {
                player.loseLife(1, game, source, false);
                player.putCardsOnBottomOfLibrary(cards, game, source, true);
            } else {
                player.shuffleLibrary(source, game);
                player.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            // AI must stop using it as infinite
            if (player.isComputer()) {
                break;
            }
        } while (doAgain);

        return true;
    }
}
