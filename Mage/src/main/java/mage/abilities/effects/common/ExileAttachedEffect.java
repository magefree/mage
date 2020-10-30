/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class ExileAttachedEffect extends OneShotEffect {

    public ExileAttachedEffect() {
        super(Outcome.Exile);
        staticText = "exile enchanted creature";
    }

    public ExileAttachedEffect(final ExileAttachedEffect effect) {
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
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
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
