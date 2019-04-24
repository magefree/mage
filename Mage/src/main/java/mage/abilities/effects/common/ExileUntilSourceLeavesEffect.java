/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class ExileUntilSourceLeavesEffect extends OneShotEffect {

    public ExileUntilSourceLeavesEffect(String targetName) {
        super(Outcome.Removal);
        this.staticText = "exile target " + targetName + " an opponent controls until {this} leaves the battlefield";
    }

    public ExileUntilSourceLeavesEffect(final ExileUntilSourceLeavesEffect effect) {
        super(effect);
    }

    @Override
    public ExileUntilSourceLeavesEffect copy() {
        return new ExileUntilSourceLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
