package mage.cards.u;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * Gatecrash FAQ 01/2013
 *
 * If you reveal a non-land card, you may cast it during the resolution of
 * Unexpected Results. Ignore timing restrictions based on the card's type.
 * Other timing restrictions, such as "Cast [this card] only during combat,"
 * must be followed.
 *
 * If you can't cast the card (perhaps because there are no legal targets), or
 * if you choose not to, the card will remain on top of the library.
 *
 * If you cast a spell "without paying its mana cost," you can't pay alternative
 * costs such as overload costs. You can pay additional costs such as kicker
 * costs. If the card has mandatory additional costs, you must pay those.
 *
 * If the card has X Mana in its mana cost, you must choose 0 as its value.
 *
 * If you reveal a land card, Unexpected Results will be returned to your hand
 * only if you put that land card onto the battlefield. If you don't, Unexpected
 * Results will be put into its owner's graveyard.
 *
 * If you reveal a land card and put that card onto the battlefield, Unexpected
 * Results will be put into its owner's hand directly from the stack. It won't
 * be put into any graveyard.
 *
 * @author LevelX2
 */
public final class UnexpectedResults extends CardImpl {

    public UnexpectedResults(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{U}");

        // Shuffle your library, then reveal the top card. If it's a nonland card, 
        // you may cast it without paying its mana cost. If it's a land card, you may 
        // put it onto the battlefield and return Unexpected Results to its owner's hand.
        this.getSpellAbility().addEffect(new UnexpectedResultEffect());

    }

    private UnexpectedResults(final UnexpectedResults card) {
        super(card);
    }

    @Override
    public UnexpectedResults copy() {
        return new UnexpectedResults(this);
    }
}

class UnexpectedResultEffect extends OneShotEffect {

    public UnexpectedResultEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Shuffle your library, then reveal the top card. "
                + "If it's a nonland card, you may cast it without paying its mana "
                + "cost. If it's a land card, you may put it onto the battlefield "
                + "and return {this} to its owner's hand";
    }

    private UnexpectedResultEffect(final UnexpectedResultEffect effect) {
        super(effect);
    }

    @Override
    public UnexpectedResultEffect copy() {
        return new UnexpectedResultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller == null 
                || sourceCard == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            controller.shuffleLibrary(source, game);
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.revealCards(sourceCard.getName(), new CardsImpl(card), game);
            if (card.isLand(game)) {
                String message = "Put " + card.getName() + " onto the battlefield?";
                if (controller.chooseUse(Outcome.PutLandInPlay, message, source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    controller.moveCards(sourceCard, Zone.HAND, source, game);
                    return true;
                }
            } else {
                if (controller.chooseUse(outcome, "Cast " + card.getName() 
                        + " without paying its mana cost?", source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    return cardWasCast;
                }
            }
            return true;
        }
        return false;
    }
}
