

package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author LevelX2
 */

public class RevealAndShuffleIntoLibrarySourceEffect extends OneShotEffect {

    public RevealAndShuffleIntoLibrarySourceEffect() {
        super(Outcome.Neutral);
        staticText = "reveal {this} and shuffle it into its owner's library instead";
    }

    protected RevealAndShuffleIntoLibrarySourceEffect(final RevealAndShuffleIntoLibrarySourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            Player owner = null;
            Cards cards = new CardsImpl();
            Permanent permanent = null;
            if (sourceObject instanceof Spell) {
                sourceObject = ((Spell) sourceObject).getCard();
            }
            if (sourceObject instanceof Permanent) {
                permanent = (Permanent) sourceObject;
                owner = game.getPlayer(permanent.getOwnerId());
                if (sourceObject instanceof PermanentCard) {
                    cards.add(permanent);
                }
            } else if (sourceObject instanceof Card) {
                owner = game.getPlayer(((Card) sourceObject).getOwnerId());
                cards.add((Card) sourceObject);
            }
            if (owner != null) {
                Zone fromZone = game.getState().getZone(sourceObject.getId());
                if (!cards.isEmpty()) {
                    controller.revealCards(sourceObject.getName(), cards, game);
                }
                if (permanent != null) {
                    controller.moveCardToLibraryWithInfo(permanent, source, game, fromZone, true, true);
                } else {
                    controller.moveCardToLibraryWithInfo((Card) sourceObject, source, game, fromZone, true, true);
                }
                if (!cards.isEmpty()) {
                    controller.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RevealAndShuffleIntoLibrarySourceEffect copy() {
        return new RevealAndShuffleIntoLibrarySourceEffect(this);
    }

}
