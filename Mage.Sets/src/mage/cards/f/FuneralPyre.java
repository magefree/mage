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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class FuneralPyre extends CardImpl {

    public FuneralPyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Exile target card from a graveyard. Its owner puts a 1/1 white Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addEffect(new FuneralPyreEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        
    }

    public FuneralPyre(final FuneralPyre card) {
        super(card);
    }

    @Override
    public FuneralPyre copy() {
        return new FuneralPyre(this);
    }
}

class FuneralPyreEffect extends OneShotEffect {

    public FuneralPyreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target card from a graveyard. Its owner puts a 1/1 white Spirit creature token with flying onto the battlefield";
    }

    public FuneralPyreEffect(final FuneralPyreEffect effect) {
        super(effect);
    }

    @Override
    public FuneralPyreEffect copy() {
        return new FuneralPyreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card exiledCard = game.getCard(source.getTargets().getFirstTarget());
        if (exiledCard != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            if (exiledCard.moveToExile(exileId, "Funeral Pyre", source.getSourceId(), game)) {
                Player owner = game.getPlayer(exiledCard.getOwnerId());
                if (owner != null) {
                    Token token = new SpiritWhiteToken();
                    return token.putOntoBattlefield(1, game, source.getSourceId(), owner.getId());
                }
            }
        }
        return false;
    }
}
