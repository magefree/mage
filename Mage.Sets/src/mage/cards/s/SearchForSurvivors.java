package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */

/*
   The card is chosen at random, so the computer just picks a card at random from 
   the controller's graveyard.  Devs, feel free to set up something else...
 */
public final class SearchForSurvivors extends CardImpl {

    public SearchForSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Reorder your graveyard at random. An opponent chooses a card at random in your graveyard. If it's a creature card, put it onto the battlefield. Otherwise, exile it.
        this.getSpellAbility().addEffect(new SearchForSurvivorsEffect());

    }

    public SearchForSurvivors(final SearchForSurvivors card) {
        super(card);
    }

    @Override
    public SearchForSurvivors copy() {
        return new SearchForSurvivors(this);
    }
}

class SearchForSurvivorsEffect extends OneShotEffect {

    public SearchForSurvivorsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reorder your graveyard at random."
                + "An opponent chooses a card at random in your graveyard."
                + "If it's a creature card, put it onto the battlefield."
                + "Otherwise, exile it";
    }

    public SearchForSurvivorsEffect(final SearchForSurvivorsEffect effect) {
        super(effect);
    }

    @Override
    public SearchForSurvivorsEffect copy() {
        return new SearchForSurvivorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.informPlayers("A card will be chosen at random from the controller's graveyard. "
                    + "The result is essentially the same as the card rule");
            Cards cards = new CardsImpl();
            for (Card card : controller.getGraveyard().getCards(new FilterCard(), game)) {
                cards.add(card);
            }
            if (!cards.isEmpty()) {
                Card card = cards.getRandom(game);
                controller.revealCards(source, cards, game);
                if (card.isCreature()) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
