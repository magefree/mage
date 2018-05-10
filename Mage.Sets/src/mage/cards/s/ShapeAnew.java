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
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author ayratn
 */
public class ShapeAnew extends CardImpl {

    public ShapeAnew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // The controller of target artifact sacrifices it, then reveals cards from the top
        // of their library until he or she reveals an artifact card. That player puts
        // that card onto the battlefield, then shuffles all other cards revealed this way into their library.
        this.getSpellAbility().addEffect(new ShapeAnewEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
    }

    public ShapeAnew(final ShapeAnew card) {
        super(card);
    }

    @Override
    public ShapeAnew copy() {
        return new ShapeAnew(this);
    }

    private static class ShapeAnewEffect extends OneShotEffect {

        public ShapeAnewEffect() {
            super(Outcome.PutCardInPlay);
            staticText = "The controller of target artifact sacrifices it, then reveals cards from the top of their library until he or she reveals an artifact card. That player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library";
        }

        public ShapeAnewEffect(ShapeAnewEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent == null) {
                return false;
            }
            targetPermanent.sacrifice(source.getSourceId(), game);
            Player targetController = game.getPlayer(targetPermanent.getControllerId());
            if (targetController == null) {
                return false;
            }
            Cards revealed = new CardsImpl();
            Card artifactCard = null;
            for (Card card : targetController.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isArtifact()) {
                    artifactCard = card;
                    break;
                }
            }
            targetController.revealCards(source, revealed, game);
            if (artifactCard != null) {
                targetController.moveCards(artifactCard, Zone.BATTLEFIELD, source, game);
            }
            // 1/1/2011: If the first card the player reveals is an artifact card, he or she will still have to shuffle his or her library even though no other cards were revealed this way.
            targetController.shuffleLibrary(source, game);
            return true;
        }

        @Override
        public ShapeAnewEffect copy() {
            return new ShapeAnewEffect(this);
        }
    }
}
