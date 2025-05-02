package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.game.Game;
import mage.players.Player;

/**
 * Global hint for all lands
 *
 * @author JayDi85
 */
public enum CanPlayAdditionalLandsHint implements Hint {

    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return "";
        }

        // hide hint on default 1 land settings (useless to show)
        if (controller.getLandsPerTurn() == 1) {
            return "";
        }

        String stats = String.format(" (played %d of %s)",
                controller.getLandsPlayed(),
                (controller.getLandsPerTurn() == Integer.MAX_VALUE ? "any" : String.valueOf(controller.getLandsPerTurn()))
        );
        if (controller.canPlayLand()) {
            return HintUtils.prepareText("Can play more lands" + stats, null, HintUtils.HINT_ICON_GOOD);
        } else {
            return HintUtils.prepareText("Can't play lands" + stats, null, HintUtils.HINT_ICON_BAD);
        }
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
