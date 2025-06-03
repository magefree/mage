package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class LookAtTopCardOfLibraryAnyTimeEffect extends ContinuousEffectImpl {

    public LookAtTopCardOfLibraryAnyTimeEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public LookAtTopCardOfLibraryAnyTimeEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = (duration.toString().isEmpty() ? "" : duration.toString() + ", ") +
                "you may look at the top card of your library any time";
    }

    protected LookAtTopCardOfLibraryAnyTimeEffect(final LookAtTopCardOfLibraryAnyTimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!canLookAtNextTopLibraryCard(game)) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of your library", topCard, game);
        return true;
    }

    @Override
    public LookAtTopCardOfLibraryAnyTimeEffect copy() {
        return new LookAtTopCardOfLibraryAnyTimeEffect(this);
    }
}
