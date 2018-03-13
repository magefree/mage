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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Scott-Crawford
 */
public class MisfortunesGain extends CardImpl {

    public MisfortunesGain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Destroy target creature. Its owner gains 4 life.
        this.getSpellAbility().addEffect(new MisfortunesGainEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MisfortunesGain(final MisfortunesGain card) {
        super(card);
    }

    @Override
    public MisfortunesGain copy() {
        return new MisfortunesGain(this);
    }
}

class MisfortunesGainEffect extends OneShotEffect {

    public MisfortunesGainEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its owner gains 4 life";
    }

    public MisfortunesGainEffect(final MisfortunesGainEffect effect) {
        super(effect);
    }

    @Override
    public MisfortunesGainEffect copy() {
        return new MisfortunesGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && target != null) {
            target.destroy(source.getSourceId(), game, false);
            Player owner = game.getPlayer(target.getOwnerId());
            if (owner != null) {
                owner.gainLife(4, game);
            }
            return true;
        }
        return false;
    }
}
