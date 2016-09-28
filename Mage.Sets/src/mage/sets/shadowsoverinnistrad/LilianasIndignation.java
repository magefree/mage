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
package mage.sets.shadowsoverinnistrad;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class LilianasIndignation extends CardImpl {

    public LilianasIndignation(UUID ownerId) {
        super(ownerId, 120, "Liliana's Indignation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{B}");
        this.expansionSetCode = "SOI";

        // Put the top X cards of your library into your graveyard. Target player loses 2 life for each creature card put into your graveyard this way.
        this.getSpellAbility().addEffect(new LilianasIndignationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public LilianasIndignation(final LilianasIndignation card) {
        super(card);
    }

    @Override
    public LilianasIndignation copy() {
        return new LilianasIndignation(this);
    }
}

class LilianasIndignationEffect extends OneShotEffect {

    public LilianasIndignationEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Put the top X cards of your library into your graveyard. Target player loses 2 life for each creature card put into your graveyard this way";
    }

    public LilianasIndignationEffect(final LilianasIndignationEffect effect) {
        super(effect);
    }

    @Override
    public LilianasIndignationEffect copy() {
        return new LilianasIndignationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int x = source.getManaCostsToPay().getX();
            if (x > 0) {
                Cards cardsToGraveyard = new CardsImpl();
                cardsToGraveyard.addAll(controller.getLibrary().getTopCards(game, x));
                if (!cardsToGraveyard.isEmpty()) {
                    Set<Card> movedCards = controller.moveCardsToGraveyardWithInfo(cardsToGraveyard.getCards(game), source, game, Zone.LIBRARY);
                    Cards cardsMoved = new CardsImpl();
                    cardsMoved.addAll(movedCards);
                    int creatures = cardsMoved.count(new FilterCreatureCard(), game);
                    if (creatures > 0) {
                        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                        if (targetPlayer != null) {
                            targetPlayer.loseLife(creatures * 2, game, false);
                        }

                    }
                }
            }
            return true;
        }
        return false;
    }
}
