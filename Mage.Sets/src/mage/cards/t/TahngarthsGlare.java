package mage.cards.t;

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
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TahngarthsGlare extends CardImpl {

    public TahngarthsGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Look at the top three cards of target opponent's library, then put them back in any order. That player looks at the top three cards of your library, then puts them back in any order.
        this.getSpellAbility().addEffect(new TahngarthsGlareEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TahngarthsGlare(final TahngarthsGlare card) {
        super(card);
    }

    @Override
    public TahngarthsGlare copy() {
        return new TahngarthsGlare(this);
    }
}

class TahngarthsGlareEffect extends OneShotEffect {

    TahngarthsGlareEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top three cards of target opponent's library, then put them back in any order. " +
                "That player looks at the top three cards of your library, then puts them back in any order";
    }

    private TahngarthsGlareEffect(final TahngarthsGlareEffect effect) {
        super(effect);
    }

    @Override
    public TahngarthsGlareEffect copy() {
        return new TahngarthsGlareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        lookAndPutBack(controller, opponent, source, game);
        lookAndPutBack(opponent, controller, source, game);
        return true;
    }

    private static final void lookAndPutBack(Player player1, Player player2, Ability source, Game game) {
        Cards cards = new CardsImpl(player2.getLibrary().getTopCards(game, 3));
        player1.lookAtCards(player2.getName() + "'s library", cards, game);
        player1.putCardsOnTopOfLibrary(cards, game, source, true);
    }
}
