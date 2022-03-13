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

/**
 *
 * @author LevelX2
 */

public class LookLibraryMayPutToBottomEffect extends OneShotEffect {

    public LookLibraryMayPutToBottomEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. You may put that card on the bottom of your library.";
    }

    public LookLibraryMayPutToBottomEffect(final LookLibraryMayPutToBottomEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null || controller == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            controller.lookAtCards(sourceObject.getName(), new CardsImpl(card), game);
            boolean toBottom = controller.chooseUse(outcome, "Put card on the bottom of your library?", source, game);
            return controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, !toBottom, false);
        }
        return true;
    }

    @Override
    public LookLibraryMayPutToBottomEffect copy() {
        return new LookLibraryMayPutToBottomEffect(this);
    }

}
