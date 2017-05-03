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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PongifyApeToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public class Pongify extends CardImpl {

    public Pongify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Destroy target creature. It can't be regenerated. That creature's controller creates a 3/3 green Ape creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new PongifyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Pongify(final Pongify card) {
        super(card);
    }

    @Override
    public Pongify copy() {
        return new Pongify(this);
    }
}

class PongifyEffect extends OneShotEffect {

    public PongifyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "That creature's controller creates a 3/3 green Ape creature token";
    }

    public PongifyEffect(final PongifyEffect effect) {
        super(effect);
    }

    @Override
    public PongifyEffect copy() {
        return new PongifyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
            if (permanent != null) {
                UUID controllerId = permanent.getControllerId();
                if (controllerId != null) {
                    new PongifyApeToken().putOntoBattlefield(1, game, source.getSourceId(), controllerId);
                    return true;
                }
            }
        }
        return false;
    }
}
