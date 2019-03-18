
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DemonicConsultation extends CardImpl {

    public DemonicConsultation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Name a card. Exile the top six cards of your library, then reveal cards from the top of your library until you reveal the named card. Put that card into your hand and exile all other cards revealed this way.
        this.getSpellAbility().addEffect(new DemonicConsultationEffect());
    }

    public DemonicConsultation(final DemonicConsultation card) {
        super(card);
    }

    @Override
    public DemonicConsultation copy() {
        return new DemonicConsultation(this);
    }
}

class DemonicConsultationEffect extends OneShotEffect {

    DemonicConsultationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Name a card. Exile the top six cards of your library, then reveal cards from the top of your library until you reveal the named card. Put that card into your hand and exile all other cards revealed this way";
    }

    DemonicConsultationEffect(final DemonicConsultationEffect effect) {
        super(effect);
    }

    @Override
    public DemonicConsultationEffect copy() {
        return new DemonicConsultationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            // Name a card.
            Choice choice = new ChoiceImpl();
            choice.setChoices(CardRepository.instance.getNames());
            if (!controller.choose(Outcome.Benefit, choice, game)) {
                return false;
            }
            String name = choice.getChoice();
            game.informPlayers("Card named: " + name);

            // Exile the top six cards of your library,
            controller.moveCards(controller.getLibrary().getTopCards(game, 6), Zone.EXILED, source, game);

            // then reveal cards from the top of your library until you reveal the named card.
            Cards cardsToReaveal = new CardsImpl();
            Card cardToHand = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card != null) {
                    cardsToReaveal.add(card);
                    // Put that card into your hand
                    if (card.getName().equals(name)) {
                        cardToHand = card;
                        break;
                    }
                }
            }
            controller.moveCards(cardToHand, Zone.HAND, source, game);
            controller.revealCards(sourceObject.getIdName(), cardsToReaveal, game);
            cardsToReaveal.remove(cardToHand);
            controller.moveCards(cardsToReaveal, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
