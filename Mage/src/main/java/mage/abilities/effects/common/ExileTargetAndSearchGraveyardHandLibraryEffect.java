
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class ExileTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public ExileTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(graveyardExileOptional, searchWhatText, searchForText);
    }

    public ExileTargetAndSearchGraveyardHandLibraryEffect(final ExileTargetAndSearchGraveyardHandLibraryEffect effect) {
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
        if (exileTarget != null) {
            Permanent permanentToExile = game.getPermanent(exileTarget.getFirstTarget());
            if (permanentToExile != null) {
                targetPlayerId = permanentToExile.getControllerId();
                result = permanentToExile.moveToExile(null, "", source.getSourceId(), game);
                this.applySearchAndExile(game, source, permanentToExile.getName(), targetPlayerId);
            }
        }

        return result;
    }

    @Override
    public ExileTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new ExileTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exile target ").append(mode.getTargets().get(0).getTargetName()).append(". ");
        sb.append(super.getText(mode));
        return sb.toString();
    }
}
