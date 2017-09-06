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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class FracturedIdentity extends CardImpl {

    public FracturedIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}");

        // Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it.
        this.getSpellAbility().addEffect(new FracturedIdentityEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public FracturedIdentity(final FracturedIdentity card) {
        super(card);
    }

    @Override
    public FracturedIdentity copy() {
        return new FracturedIdentity(this);
    }
}

class FracturedIdentityEffect extends OneShotEffect {

    public FracturedIdentityEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it";
    }

    public FracturedIdentityEffect(final FracturedIdentityEffect effect) {
        super(effect);
    }

    @Override
    public FracturedIdentityEffect copy() {
        return new FracturedIdentityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        permanent.moveToExile(null, null, source.getSourceId(), game);
        UUID controllerId = permanent.getControllerId();
        for (UUID opponentId : game.getOpponents(controllerId)) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(opponentId, null, false);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
        }
        return true;
    }
}
