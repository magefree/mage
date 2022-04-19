package mage.cards.s;

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
 * @author awjackson
 */
public final class SealedFate extends CardImpl {

    public SealedFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");

        // Look at the top X cards of target opponent's library. Exile one of those cards and put the rest back on top of that player's library in any order.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SealedFateEffect());
    }

    private SealedFate(final SealedFate card) {
        super(card);
    }

    @Override
    public SealedFate copy() {
        return new SealedFate(this);
    }
}

class SealedFateEffect extends OneShotEffect {

    SealedFateEffect() {
        super(Outcome.Detriment);
        this.staticText = "Look at the top X cards of target opponent's library. " +
                "Exile one of those cards and put the rest back on top of that player's library in any order";
    }

    private SealedFateEffect(final SealedFateEffect effect) {
        super(effect);
    }

    @Override
    public SealedFateEffect copy() {
        return new SealedFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        int xValue = source.getManaCostsToPay().getX();

        if (controller == null || opponent == null) {
            return false;
        }

        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, xValue));
        if (cards.isEmpty()) {
            return false;
        }
        if (cards.size() == 1) {
            return controller.moveCards(cards, Zone.EXILED, source, game);
        }
        TargetCard targetCard = new TargetCardInLibrary();
        controller.choose(outcome, cards, targetCard, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
            cards.remove(card);
        }
        return controller.putCardsOnTopOfLibrary(cards, game, source, true);
    }
}
