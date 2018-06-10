
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ClashWinReturnToHandSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ClashWinReturnToHandSpellEffect instance = new ClashWinReturnToHandSpellEffect();

    public static ClashWinReturnToHandSpellEffect getInstance() {
        return instance;
    }

    private ClashWinReturnToHandSpellEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Clash with an opponent. If you win, return {this} to its owner's hand";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (ClashEffect.getInstance().apply(game, source)) {
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    controller.moveCards(spellCard, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ClashWinReturnToHandSpellEffect copy() {
        return instance;
    }
}
