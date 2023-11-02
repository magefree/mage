package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Styxo
 */
public class ExileAttachedEffect extends OneShotEffect {

    public ExileAttachedEffect() {
        super(Outcome.Exile);
        staticText = "exile enchanted creature";
    }

    protected ExileAttachedEffect(final ExileAttachedEffect effect) {
        super(effect);
    }

    @Override
    public ExileAttachedEffect copy() {
        return new ExileAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // The LKI must be used for this step.  608.2g 
        Permanent enchantment = source.getSourcePermanentOrLKI(game);
        if (controller != null
                && enchantment != null
                && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                controller.moveCardsToExile(creature, source, game, true, null, "");
            }
        }
        return false;
    }
}
