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
 * @author raphael-schulz
 * Implementation taken from LookAtTopCardOfLibraryAnyTimeEffect and adjusted accordingly
 */
public class LookAtTopCardOfLibraryAnyTimeTargetEffect extends ContinuousEffectImpl {

    public LookAtTopCardOfLibraryAnyTimeTargetEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may look at the top card of target player's library any time.";
    }

    private LookAtTopCardOfLibraryAnyTimeTargetEffect(final LookAtTopCardOfLibraryAnyTimeTargetEffect effect) {
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

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }

        Card topCard = targetPlayer.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        if (!canLookAtNextTopLibraryCard(game)) {
            return false;
        }
        controller.lookAtCards("Top card of " + targetPlayer.getName() + "'s library", topCard, game);
        return true;
    }

    @Override
    public LookAtTopCardOfLibraryAnyTimeTargetEffect copy() {
        return new LookAtTopCardOfLibraryAnyTimeTargetEffect(this);
    }
}
