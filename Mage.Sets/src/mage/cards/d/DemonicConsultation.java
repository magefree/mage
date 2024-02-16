package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DemonicConsultation extends CardImpl {

    public DemonicConsultation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Name a card. Exile the top six cards of your library, then reveal cards from the top of your library until you reveal the named card. Put that card into your hand and exile all other cards revealed this way.
        this.getSpellAbility().addEffect(new DemonicConsultationEffect());
    }

    private DemonicConsultation(final DemonicConsultation card) {
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
        this.staticText = "choose a card name. Exile the top six cards of your library, " +
                "then reveal cards from the top of your library until you reveal a card with the chosen name. " +
                "Put that card into your hand and exile all other cards revealed this way";
    }

    private DemonicConsultationEffect(final DemonicConsultationEffect effect) {
        super(effect);
    }

    @Override
    public DemonicConsultationEffect copy() {
        return new DemonicConsultationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        // Name a card.
        String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(controller, game, source, false);

        // Exile the top six cards of your library,
        controller.moveCards(controller.getLibrary().getTopCards(game, 6), Zone.EXILED, source, game);

        // then reveal cards from the top of your library until you reveal the named card.
        Cards cardsToReveal = new CardsImpl();
        Card cardToHand = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            cardsToReveal.add(card);
            // Put that card into your hand
            if (CardUtil.haveSameNames(card.getName(), cardName)) {
                cardToHand = card;
                break;
            }
        }
        controller.moveCards(cardToHand, Zone.HAND, source, game);
        controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
        cardsToReveal.remove(cardToHand);
        controller.moveCards(cardsToReveal, Zone.EXILED, source, game);
        return true;
    }
}
