/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class LoseLifeControllerEffect extends OneShotEffect<LoseLifeControllerEffect> {

    protected int amount;

    public LoseLifeControllerEffect(int amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public LoseLifeControllerEffect(final LoseLifeControllerEffect effect) {
        super(effect);
    }

    @Override
    public LoseLifeControllerEffect copy() {
        return new LoseLifeControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null) {
            Player player = game.getPlayer(targetPermanent.getControllerId());
            if (player != null) {
                player.loseLife(amount, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Ability source) {
        return "Its controller loses " + amount + " life";
    }
}
