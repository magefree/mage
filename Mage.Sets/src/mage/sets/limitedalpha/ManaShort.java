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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public class ManaShort extends CardImpl {

    public ManaShort(UUID ownerId) {
        super(ownerId, 66, "Mana Short", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "LEA";

        // Tap all lands target player controls and empty his or her mana pool.
        this.getSpellAbility().addEffect(new ManaShortEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public ManaShort(final ManaShort card) {
        super(card);
    }

    @Override
    public ManaShort copy() {
        return new ManaShort(this);
    }
}

class ManaShortEffect extends TapAllTargetPlayerControlsEffect {

    public ManaShortEffect() {
        super(new FilterLandPermanent("lands"));
        staticText = "Tap all lands target player controls and empty his or her mana pool";
    }

    public ManaShortEffect(final ManaShortEffect effect) {
        super(effect);
    }

    @Override
    public ManaShortEffect copy() {
        return new ManaShortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(targetPlayer != null) {
            super.apply(game, source);
            targetPlayer.getManaPool().emptyPool(game);
            return true;
        }
        return false;
    }
}
