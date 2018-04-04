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
package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.WaylayToken;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author TheElk801
 */
public class Waylay extends CardImpl {

    public Waylay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Create three 2/2 white Knight creature tokens. Exile them at the beginning of the next cleanup step.
        this.getSpellAbility().addEffect(new WaylayEffect());
    }

    public Waylay(final Waylay card) {
        super(card);
    }

    @Override
    public Waylay copy() {
        return new Waylay(this);
    }
}

class WaylayEffect extends OneShotEffect {

    public WaylayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create three 2/2 white Knight creature tokens. Exile them at the beginning of the next cleanup step.";
    }

    public WaylayEffect(final WaylayEffect effect) {
        super(effect);
    }

    @Override
    public WaylayEffect copy() {
        return new WaylayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new WaylayToken();
        token.putOntoBattlefield(3, game, source.getSourceId(), source.getControllerId());
        List<Permanent> toExile = new ArrayList<>();
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                toExile.add(tokenPermanent);
            }
        }
        Effect effect = new ExileTargetEffect();
        effect.setTargetPointer(new FixedTargets(toExile, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(effect), source);
        return true;
    }
}
