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

import mage.Constants;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CopyPermanentEffect extends OneShotEffect<CopyPermanentEffect> {

    private FilterPermanent filter;

     public CopyPermanentEffect() {
         this(new FilterCreaturePermanent());
     }

    public CopyPermanentEffect(FilterPermanent filter) {
        super(Outcome.Copy);
        this.filter = filter;
        this.staticText = "You may have {this} enter the battlefield as a copy of any " + filter.getMessage() + " on the battlefield";
    }

    public CopyPermanentEffect(final CopyPermanentEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            Target target = new TargetPermanent(filter);
            if (target.canChoose(source.getControllerId(), game)) {
                player.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent copyFromPermanent = game.getPermanent(target.getFirstTarget());
                if (copyFromPermanent != null) {
                    game.copyPermanent(copyFromPermanent, sourcePermanent, source, new ApplyToPermanent() {
                        @Override
                        public Boolean apply(Game game, Permanent permanent) {
                            if (!permanent.getCardType().contains(Constants.CardType.ARTIFACT)) {
                                permanent.getCardType().add(Constants.CardType.ARTIFACT);
                            }
                            return true;
                        }
                    });

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CopyPermanentEffect copy() {
        return new CopyPermanentEffect(this);
    }

}