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
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public class DarkBargain extends CardImpl {

    public DarkBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Look at the top three cards of your library. Put two of them into your hand and the rest into your graveyard.
        // Dark Bargain deals 2 damage to you.
        this.getSpellAbility().addEffect(new DarkBargainEffect());
        this.getSpellAbility().addEffect(new DamageControllerEffect(2));
    }

    public DarkBargain(final DarkBargain card) {
        super(card);
    }

    @Override
    public DarkBargain copy() {
        return new DarkBargain(this);
    }
}

class DarkBargainEffect extends OneShotEffect {

    public DarkBargainEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top three cards of your library. Put two of them into your hand and the rest into your graveyard";
    }

    public DarkBargainEffect(final DarkBargainEffect effect) {
        super(effect);
    }

    @Override
    public DarkBargainEffect copy() {
        return new DarkBargainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceOject = source.getSourceObject(game);
        if (player != null && sourceOject != null) {
            Cards cards = new CardsImpl();
            int cardsCount = Math.min(3, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            if (!cards.isEmpty()) {
                Cards cardsToHand = new CardsImpl();
                player.lookAtCards(sourceOject.getIdName(), cards, game);
                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.LIBRARY, new FilterCard("two cards to put in your hand"));
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = cards.get(targetId, game);
                        if (card != null) {
                            cardsToHand.add(card);
                            cards.remove(card);
                        }
                    }
                }
                player.moveCards(cardsToHand, Zone.HAND, source, game);
                player.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
