package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

public class RevealTopLandToBattlefieldElseHandEffect extends OneShotEffect {

    public RevealTopLandToBattlefieldElseHandEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put that card into your hand";
    }

    public RevealTopLandToBattlefieldElseHandEffect(final RevealTopLandToBattlefieldElseHandEffect effect) {
        super(effect);
    }

    @Override
    public RevealTopLandToBattlefieldElseHandEffect copy() {
        return new RevealTopLandToBattlefieldElseHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null || controller == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            if (card.isLand(game)) {
                return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            } else {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
