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

package mage.sets.scarsofmirrodin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class TurnToSlag extends CardImpl<TurnToSlag> {

    public TurnToSlag (UUID ownerId) {
        super(ownerId, 106, "Turn to Slag", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "SOM";
		this.color.setRed(true);
        this.getSpellAbility().addEffect(new TurnToSlagEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TurnToSlag (final TurnToSlag card) {
        super(card);
    }

    @Override
    public TurnToSlag copy() {
        return new TurnToSlag(this);
    }
}

class TurnToSlagEffect extends OneShotEffect<TurnToSlagEffect> {

    public TurnToSlagEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Turn to Slag deals 5 damage to target creature. Destroy all Equipment attached to that creature";
    }

    public TurnToSlagEffect(final TurnToSlagEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            List<Permanent> attachments = new ArrayList<Permanent>();
            for (UUID uuid : target.getAttachments()) {
				Permanent attached = game.getBattlefield().getPermanent(uuid);
				if (attached.getSubtype().contains("Equipment")) {
                    attachments.add(attached);
				}
			}
            for (Permanent p : attachments) {
                p.destroy(source.getId(), game, false);
            }
            target.damage(5, source.getId(), game, false, false);
            return true;
        }
        return false;
    }

    @Override
    public TurnToSlagEffect copy() {
        return new TurnToSlagEffect(this);
    }

}