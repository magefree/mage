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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class SuddenReclamation extends CardImpl {

    public SuddenReclamation(UUID ownerId) {
        super(ownerId, 139, "Sudden Reclamation", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "FRF";

        // Put the top four cards of your library into your graveyard, then return a creature card and a land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new PutTopCardOfLibraryIntoGraveControllerEffect(4));
        this.getSpellAbility().addEffect(new SuddenReclamationEffect());
    }

    public SuddenReclamation(final SuddenReclamation card) {
        super(card);
    }

    @Override
    public SuddenReclamation copy() {
        return new SuddenReclamation(this);
    }
}

class SuddenReclamationEffect extends OneShotEffect {

    public SuddenReclamationEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then return a creature card and a land card from your graveyard to your hand";
    }

    public SuddenReclamationEffect(final SuddenReclamationEffect effect) {
        super(effect);
    }

    @Override
    public SuddenReclamationEffect copy() {
        return new SuddenReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"));
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            target = new TargetCardInYourGraveyard(new FilterLandCard("land card from your graveyard"));
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            controller.moveCards(cardsToHand, null, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
