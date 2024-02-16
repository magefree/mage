package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ExileTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public ExileTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(graveyardExileOptional, searchWhatText, searchForText);
        this.staticText = ""; // since parent class overrides static text but we need to use a target
    }

    private ExileTargetAndSearchGraveyardHandLibraryEffect(final ExileTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanentToExile = game.getPermanent(source.getFirstTarget());
        if (player == null || permanentToExile == null) {
            return false;
        }
        player.moveCards(permanentToExile, Zone.EXILED, source, game);
        this.applySearchAndExile(game, source, permanentToExile.getName(), permanentToExile.getControllerId());
        return true;
    }

    @Override
    public ExileTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new ExileTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "exile " + getTargetPointer().describeTargets(mode.getTargets(), "that permanent")
                + ". Search " + searchWhatText + " graveyard, hand, and library for " + searchForText + " and exile them. Then that player shuffles";
    }
}
