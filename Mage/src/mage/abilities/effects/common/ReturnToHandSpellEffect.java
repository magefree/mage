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
 * @author Loki
 */
public class ReturnToHandSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ReturnToHandSpellEffect fINSTANCE = new ReturnToHandSpellEffect();

    public static ReturnToHandSpellEffect getInstance() {
        return fINSTANCE;
    }

    private ReturnToHandSpellEffect() {
        super(Outcome.Benefit);
        staticText = "Return {this} to its owner's hand";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
            controller.moveCards(spellCard, null, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public ReturnToHandSpellEffect copy() {
        return fINSTANCE;
    }
}
