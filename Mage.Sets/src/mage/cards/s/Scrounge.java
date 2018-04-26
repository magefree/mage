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
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox & L_J
 */
public class Scrounge extends CardImpl {

    public Scrounge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent chooses an artifact card in their graveyard. Put that card onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ScroungeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Scrounge(final Scrounge card) {
        super(card);
    }

    @Override
    public Scrounge copy() {
        return new Scrounge(this);
    }
}

class ScroungeEffect extends OneShotEffect {

    public ScroungeEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent chooses an artifact card in their graveyard. Put that card onto the battlefield under your control";
    }

    public ScroungeEffect(final ScroungeEffect effect) {
        super(effect);
    }

    @Override
    public ScroungeEffect copy() {
        return new ScroungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && opponent != null) {
            FilterArtifactCard filter = new FilterArtifactCard();
            filter.add(new OwnerIdPredicate(opponent.getId()));
            TargetCardInGraveyard chosenCard = new TargetCardInGraveyard(filter);
            chosenCard.setNotTarget(true);
            if (chosenCard.canChoose(opponent.getId(), game)) {
                opponent.chooseTarget(Outcome.ReturnToHand, chosenCard, source, game);
                Card card = game.getCard(chosenCard.getFirstTarget());
                if (card != null) {
                    game.informPlayers ("Scrounge: " + opponent.getLogName() + " has chosen " + card.getLogName());
                    Cards cardsToMove = new CardsImpl();
                    cardsToMove.add(card);
                    controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
