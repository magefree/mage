package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */

public class PutTopCardOfLibraryIntoGraveControllerEffect extends OneShotEffect {

    private final int numberCards;

    public PutTopCardOfLibraryIntoGraveControllerEffect(int numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.staticText = setText();
    }

    public PutTopCardOfLibraryIntoGraveControllerEffect(final PutTopCardOfLibraryIntoGraveControllerEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public PutTopCardOfLibraryIntoGraveControllerEffect copy() {
        return new PutTopCardOfLibraryIntoGraveControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getLibrary().getTopCards(game, numberCards), Zone.GRAVEYARD, source, game);
        }
        return false;
    }

    private String setText() {
        return "mill " + (numberCards == 1 ? "a card" : CardUtil.numberToText(numberCards) + " cards");
    }
}
