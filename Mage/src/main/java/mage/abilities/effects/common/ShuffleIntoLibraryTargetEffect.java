package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class ShuffleIntoLibraryTargetEffect extends OneShotEffect {

    boolean optional;

    public ShuffleIntoLibraryTargetEffect() {
        this(false);
    }

    public ShuffleIntoLibraryTargetEffect(boolean optional) {
        super(Outcome.Detriment);
        this.optional = optional;
    }

    public ShuffleIntoLibraryTargetEffect(final ShuffleIntoLibraryTargetEffect effect) {
        super(effect);
        this.optional = effect.optional;
    }

    @Override
    public ShuffleIntoLibraryTargetEffect copy() {
        return new ShuffleIntoLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject cardObject = game.getObject(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cardObject != null && controller != null && cardObject instanceof Card) {
            if (!optional
                    || controller.chooseUse(Outcome.Benefit, "Shuffle " + cardObject.getIdName() + " into "
                            + (((Card) cardObject).getOwnerId().equals(source.getControllerId()) ? "your" : "its owners")
                            + " library?", source, game)) {
                Player owner = game.getPlayer(((Card) cardObject).getOwnerId());
                if (owner != null) {
                    return owner.shuffleCardsToLibrary(((Card) cardObject), game, source);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode
    ) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        } else {
            return "choose target " + mode.getTargets().get(0).getTargetName() + ". Its owner shuffles it into their library";
        }
    }
}
