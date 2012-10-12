/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;



/**
 *
 * @author LevelX2
 */

//
//    701.27. Populate
//
//    701.27a To populate means to choose a creature token you control and put a 
//    token onto the battlefield that’s a copy of that creature token.
//
//    701.27b If you control no creature tokens when instructed to populate, you 
//    won’t put a token onto the battlefield.
//


public class PopulateEffect extends OneShotEffect<PopulateEffect> {

    private static final FilterPermanent filter = new FilterPermanent("token for populate");
    
    static {
        filter.add(new TokenPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PopulateEffect() {
        this("");
    }
    
    public PopulateEffect(String prefixText) {
        super(Outcome.Copy);
        this.staticText = (prefixText.length()>0?prefixText+" p":"P")+"opulate <i>(Put a token onto the battlefield that's a copy of a creature token you control.)</i>";
    }

    public PopulateEffect(final PopulateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetPermanent(filter);
            target.setRequired(true);
            if (target.canChoose(source.getControllerId(), game)) {
                player.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent tokenToCopy = game.getPermanent(target.getFirstTarget());
                if (tokenToCopy != null && tokenToCopy instanceof PermanentToken) {
                    Token newToken = new Token("","");
                    CardUtil.copyTo(newToken).from(tokenToCopy);
                    if (newToken != null ) {
                        game.informPlayers("Token selected for populate: " + newToken.getName());
                        return newToken.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public PopulateEffect copy() {
        return new PopulateEffect(this);
    }
}