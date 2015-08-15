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
package mage.sets.masterseditioniv;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public class Crumble extends CardImpl {

    public Crumble(UUID ownerId) {
        super(ownerId, 147, "Crumble", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ME4";

        // Destroy target artifact. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        
        // That artifact's controller gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new CrumbleEffect());
    }

    public Crumble(final Crumble card) {
        super(card);
    }

    @Override
    public Crumble copy() {
        return new Crumble(this);
    }
}

class CrumbleEffect extends OneShotEffect {

    public CrumbleEffect() {
        super(Outcome.GainLife);
        staticText = "That artifact's controller gains life equal to its converted mana cost";
    }

    public CrumbleEffect(final CrumbleEffect effect) {
        super(effect);
    }

    @Override
    public CrumbleEffect copy() {
        return new CrumbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            int cost = permanent.getManaCost().convertedManaCost();
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.gainLife(cost, game);
            }
            return true;
        }
        return false;
    }
}
