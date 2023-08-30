package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ExileTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public ExileTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(graveyardExileOptional, searchWhatText, searchForText);
    }

    private ExileTargetAndSearchGraveyardHandLibraryEffect(final ExileTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        UUID targetPlayerId = null;
        // get Target to exile
        Target exileTarget = null;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPermanent) {
                exileTarget = target;
                break;
            }
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || exileTarget == null) {
            return result;
        }
        Permanent permanentToExile = game.getPermanent(exileTarget.getFirstTarget());
        if (permanentToExile != null) {
            targetPlayerId = permanentToExile.getControllerId();
            result = player.moveCards(permanentToExile, Zone.EXILED, source, game);
            this.applySearchAndExile(game, source, permanentToExile.getName(), targetPlayerId);
        }

        return result;
    }

    @Override
    public ExileTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new ExileTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        // TODO: Parent class sets static text so it must be overridden here for now
        StringBuilder sb = new StringBuilder();
        sb.append("Exile target ").append(mode.getTargets().get(0).getTargetName()).append(". ");
        sb.append(CardUtil.getTextWithFirstCharUpperCase(super.getText(mode)));
        return sb.toString();
    }
}
