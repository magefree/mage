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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public class ClutchOfTheUndercity extends CardImpl<ClutchOfTheUndercity> {

    public ClutchOfTheUndercity(UUID ownerId) {
        super(ownerId, 197, "Clutch of the Undercity", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{B}");
        this.expansionSetCode = "RAV";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Return target permanent to its owner's hand. Its controller loses 3 life.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ClutchOfTheUndercityEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Transmute {1}{U}{B}
        this.addAbility(new TransmuteAbility("{1}{U}{B}"));
    }

    public ClutchOfTheUndercity(final ClutchOfTheUndercity card) {
        super(card);
    }

    @Override
    public ClutchOfTheUndercity copy() {
        return new ClutchOfTheUndercity(this);
    }
}

class ClutchOfTheUndercityEffect extends OneShotEffect<ClutchOfTheUndercityEffect> {
    ClutchOfTheUndercityEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Its controller loses 3 life";
    }

    ClutchOfTheUndercityEffect(final ClutchOfTheUndercityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.BATTLEFIELD);
        if (p != null) {
            Player player = game.getPlayer(p.getControllerId());
            if (player != null) {
                player.loseLife(3, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public ClutchOfTheUndercityEffect copy() {
        return new ClutchOfTheUndercityEffect(this);
    }
}
