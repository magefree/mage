package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonicBargain extends CardImpl {

    public DemonicBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Exile the top thirteen cards of your library, then search your library for a card. Put that card into your hand, then shuffle.
        this.getSpellAbility().addEffect(new DemonicBargainEffect());
    }

    private DemonicBargain(final DemonicBargain card) {
        super(card);
    }

    @Override
    public DemonicBargain copy() {
        return new DemonicBargain(this);
    }
}

class DemonicBargainEffect extends OneShotEffect {

    DemonicBargainEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top thirteen cards of your library, " +
                "then search your library for a card. Put that card into your hand, then shuffle";
    }

    private DemonicBargainEffect(final DemonicBargainEffect effect) {
        super(effect);
    }

    @Override
    public DemonicBargainEffect copy() {
        return new DemonicBargainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(player.getLibrary().getTopCards(game, 13), Zone.EXILED, source, game);
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
