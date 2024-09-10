package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801 & L_J
 */
public final class CruelFate extends CardImpl {

    public CruelFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Look at the top five cards of target opponent's library. Put one of those cards into that player's graveyard and the rest on top of their library in any order.
        this.getSpellAbility().addEffect(new CruelFateEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CruelFate(final CruelFate card) {
        super(card);
    }

    @Override
    public CruelFate copy() {
        return new CruelFate(this);
    }
}

class CruelFateEffect extends OneShotEffect {

    CruelFateEffect() {
        super(Outcome.Detriment);
        this.staticText = "Look at the top five cards of target opponent's library. " +
                "Put one of those cards into that player's graveyard and the rest on top of their library in any order";
    }

    private CruelFateEffect(final CruelFateEffect effect) {
        super(effect);
    }

    @Override
    public CruelFateEffect copy() {
        return new CruelFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 5));
        if (cards.isEmpty()) {
            return false;
        }
        if (cards.size() == 1) {
            return controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        TargetCard targetCard = new TargetCardInLibrary();
        controller.choose(outcome, cards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.GRAVEYARD, source, game);
            cards.remove(card);
        }
        return controller.putCardsOnTopOfLibrary(cards, game, source, true);
    }
}
