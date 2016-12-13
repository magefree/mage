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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class SeedsOfInnocence extends CardImpl {

    public SeedsOfInnocence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Destroy all artifacts. They can't be regenerated. The controller of each of those artifacts gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new SeedsOfInnocenceEffect());

    }

    public SeedsOfInnocence(final SeedsOfInnocence card) {
        super(card);
    }

    @Override
    public SeedsOfInnocence copy() {
        return new SeedsOfInnocence(this);
    }
}

class SeedsOfInnocenceEffect extends OneShotEffect {

    public SeedsOfInnocenceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all artifacts. They can't be regenerated. The controller of each of those artifacts gains life equal to its converted mana cost";
    }

    public SeedsOfInnocenceEffect(final SeedsOfInnocenceEffect effect) {
        super(effect);
    }

    @Override
    public SeedsOfInnocenceEffect copy() {
        return new SeedsOfInnocenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent artifact : game.getState().getBattlefield().getActivePermanents(new FilterArtifactPermanent(), controller.getId(), game)) {
                Player artifactController = game.getPlayer(artifact.getControllerId());
                int cmc = artifact.getConvertedManaCost();
                if (artifact.destroy(source.getSourceId(), game, true)) {
                    artifactController.gainLife(cmc, game);
                }
            }
            return true;
        }
        return false;
    }
}
