package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSpellEffect extends OneShotEffect {

    public ExileSpellEffect() {
        super(Outcome.Exile);
        staticText = "Exile {this}";
    }

    private ExileSpellEffect(final ExileSpellEffect effect) {
        super(effect);
    }

    @Override
    public ExileSpellEffect copy() {
        return new ExileSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getId());
        if (controller == null || spell == null) {
            return true;
        }
        return controller.moveCards(spell.getCard(), Zone.EXILED, source, game);
    }
}
