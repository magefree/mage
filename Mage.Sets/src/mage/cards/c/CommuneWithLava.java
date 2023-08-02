package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CommuneWithLava extends CardImpl {

    public CommuneWithLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Exile the top X cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new CommuneWithLavaEffect());

    }

    private CommuneWithLava(final CommuneWithLava card) {
        super(card);
    }

    @Override
    public CommuneWithLava copy() {
        return new CommuneWithLava(this);
    }
}

class CommuneWithLavaEffect extends OneShotEffect {

    public CommuneWithLavaEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top X cards of your library. Until the end of your next turn, you may play those cards";
    }

    public CommuneWithLavaEffect(final CommuneWithLavaEffect effect) {
        super(effect);
    }

    @Override
    public CommuneWithLavaEffect copy() {
        return new CommuneWithLavaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            int amount = source.getManaCostsToPay().getX();
            Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
            controller.moveCardsToExile(cards, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard.getIdName());

            for (Card card : cards) {
                CardUtil.makeCardPlayable(game, source, card, Duration.UntilEndOfYourNextTurn);
            }

            return true;
        }
        return false;
    }
}
