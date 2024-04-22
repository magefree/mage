
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ReturnToLibrarySpellEffect extends OneShotEffect {

    private final boolean toTop;

    public ReturnToLibrarySpellEffect(boolean top) {
        super(Outcome.Neutral);
        staticText = "Put {this} on " + (top ? "top" : "the bottom") + " of its owner's library";
        this.toTop = top;
    }

    protected ReturnToLibrarySpellEffect(final ReturnToLibrarySpellEffect effect) {
        super(effect);
        this.toTop = effect.toTop;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getSourceId());
            if (spell != null) {
                Card spellCard = spell.getCard();
                if (spellCard != null) {
                    controller.moveCardToLibraryWithInfo(spellCard, source, game, Zone.STACK, toTop, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ReturnToLibrarySpellEffect copy() {
        return new ReturnToLibrarySpellEffect(this);
    }
}
