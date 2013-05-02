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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 *
 * @author LevelX2
 */
public class RestoreThePeace extends CardImpl<RestoreThePeace> {

    public RestoreThePeace(UUID ownerId) {
        super(ownerId, 97, "Restore the Peace", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W}{U}");
        this.expansionSetCode = "DGM";

        this.color.setBlue(true);
        this.color.setWhite(true);

        // Return each creature that dealt damage this turn to its owner's hand.
        this.getSpellAbility().addEffect(new RestoreThePeaceEffect());
        this.addWatcher(new SourceDidDamageWatcher());

    }

    public RestoreThePeace(final RestoreThePeace card) {
        super(card);
    }

    @Override
    public RestoreThePeace copy() {
        return new RestoreThePeace(this);
    }
}

class RestoreThePeaceEffect extends OneShotEffect<RestoreThePeaceEffect> {
    
    public RestoreThePeaceEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return each creature that dealt damage this turn to its owner's hand";
    }
    
    public RestoreThePeaceEffect(final RestoreThePeaceEffect effect) {
        super(effect);
    }
    
    @Override
    public RestoreThePeaceEffect copy() {
        return new RestoreThePeaceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get("SourceDidDamageWatcher");
        if (watcher != null) {
            for (UUID permId :watcher.damageSources) {
                Permanent perm = game.getPermanent(permId);
                if (perm != null) {
                    perm.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                }
            }
            return true;
        }        
        return false;
    }
}
