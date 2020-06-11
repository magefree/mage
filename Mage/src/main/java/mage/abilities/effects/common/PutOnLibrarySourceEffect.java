
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class PutOnLibrarySourceEffect extends OneShotEffect {

    boolean onTop;

    public PutOnLibrarySourceEffect(boolean onTop) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
    }

    public PutOnLibrarySourceEffect(boolean onTop, String rule) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
        this.staticText = rule;
    }

    public PutOnLibrarySourceEffect(final PutOnLibrarySourceEffect effect) {
        super(effect);
        this.onTop = effect.onTop;
    }

    @Override
    public PutOnLibrarySourceEffect copy() {
        return new PutOnLibrarySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject == null) {
            return false;
        }
        if (sourceObject instanceof Permanent) {
            ((Permanent) sourceObject).moveToZone(Zone.LIBRARY, source.getSourceId(), game, onTop);
            return true;
        } else if (sourceObject instanceof Card && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            for (Player player : game.getPlayers().values()) {
                if (player.getGraveyard().contains(sourceObject.getId())) {
                    ((Card) sourceObject).moveToZone(Zone.LIBRARY, source.getSourceId(), game, onTop);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (this.staticText != null && !this.staticText.isEmpty()) {
            sb.append(staticText);
        } else {
            // Put Champion of Stray Souls on top of your library from your graveyard
            sb.append("put {this} on ");
            sb.append(onTop ? "top" : "the bottom").append(" of its owner's library");
        }
        return sb.toString();

    }
}
