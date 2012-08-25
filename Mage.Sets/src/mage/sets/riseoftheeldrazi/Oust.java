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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class Oust extends CardImpl<Oust> {

    public Oust(UUID ownerId) {
        super(ownerId, 40, "Oust", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{W}");
        this.expansionSetCode = "ROE";

        this.color.setWhite(true);

        // Put target creature into its owner's library second from the top. Its controller gains 3 life.
        this.getSpellAbility().addEffect(new OustEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Oust(final Oust card) {
        super(card);
    }

    @Override
    public Oust copy() {
        return new Oust(this);
    }
}

class OustEffect extends OneShotEffect<OustEffect> {

    public OustEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target creature into its owner's library second from the top. Its controller gains 3 life";
    }

    public OustEffect(final OustEffect effect) {
        super(effect);
    }

    @Override
    public OustEffect copy() {
        return new OustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            Player controller = game.getPlayer(permanent.getControllerId());
            if (owner == null || controller == null) {
                return false;
            }

            Card card = null;
            if (owner.getLibrary().size() > 0) {
                card = owner.getLibrary().removeFromTop(game);
            }

            permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            if (card != null) {
                owner.getLibrary().putOnTop(card, game);
            }
            controller.gainLife(3, game);

            return true;
        }
        return false;
    }
}
