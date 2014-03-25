/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class PutCopySourceTokenOntoBattlefield extends OneShotEffect<PutCopySourceTokenOntoBattlefield> {

    public PutCopySourceTokenOntoBattlefield() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token that's a copy of {this} onto the battlefield";
    }

    public PutCopySourceTokenOntoBattlefield(final PutCopySourceTokenOntoBattlefield effect) {
        super(effect);
    }

    @Override
    public PutCopySourceTokenOntoBattlefield copy() {
        return new PutCopySourceTokenOntoBattlefield(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject thisCard = game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (thisCard != null && thisCard instanceof Permanent) {
                EmptyToken token = new EmptyToken();
                CardUtil.copyTo(token).from((Permanent)thisCard);
                if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
                    game.informPlayers(new StringBuilder(controller.getName())
                    .append(" puts a ").append(token.getName()).append(" token ").append("onto the Battlefield").toString());
                    return true;
                }
            } else { // maybe it's token
                Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                if (permanent != null) {
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from(permanent);
                    if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
                        game.informPlayers(new StringBuilder(controller.getName())
                        .append(" puts a ").append(token.getName()).append(" token ").append("onto the Battlefield").toString());
                        return true;
                    }
                }
            }
        }
        return false;
    }

}