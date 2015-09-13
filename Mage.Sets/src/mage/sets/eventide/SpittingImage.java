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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SpittingImage extends CardImpl {

    public SpittingImage(UUID ownerId) {
        super(ownerId, 162, "Spitting Image", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{G/U}{G/U}");
        this.expansionSetCode = "EVE";

        // Put a token that's a copy of target creature onto the battlefield.
        this.getSpellAbility().addEffect(new PutTokenOntoBattlefieldCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Retrace (You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)
        this.addAbility(new RetraceAbility(this));

    }

    public SpittingImage(final SpittingImage card) {
        super(card);
    }

    @Override
    public SpittingImage copy() {
        return new SpittingImage(this);
    }
}

class SpittingImageEffect extends OneShotEffect {

    public SpittingImageEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a token that's a copy of target creature onto the battlefield";
    }

    public SpittingImageEffect(final SpittingImageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public SpittingImageEffect copy() {
        return new SpittingImageEffect(this);
    }
}
