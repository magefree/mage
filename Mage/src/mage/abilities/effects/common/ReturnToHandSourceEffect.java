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

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class ReturnToHandSourceEffect extends OneShotEffect<ReturnToHandSourceEffect> {

    boolean fromBattlefieldOnly = false;

    public ReturnToHandSourceEffect() {
        this(false);
    }

    public ReturnToHandSourceEffect(boolean fromBattlefieldOnly) {
        super(Outcome.ReturnToHand);
        this.fromBattlefieldOnly = fromBattlefieldOnly;
        staticText = "Return {this} to it's owner's hand";
    }

    public ReturnToHandSourceEffect(final ReturnToHandSourceEffect effect) {
        super(effect);
        this.fromBattlefieldOnly = effect.fromBattlefieldOnly;
    }

    @Override
    public ReturnToHandSourceEffect copy() {
        return new ReturnToHandSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            switch (game.getState().getZone(card.getId())) {
                case BATTLEFIELD:
                    Permanent p = game.getPermanent(source.getSourceId());
                    if (p != null) {
                        return p.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                    break;
                case GRAVEYARD:
                    if (!fromBattlefieldOnly) {
                        return card.moveToZone(Zone.HAND, source.getId(), game, true);
                    }

            }
        }
        return false;
    }
}
