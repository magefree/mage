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
package mage.sets.magic2012;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class SphinxOfUthuun extends CardImpl<SphinxOfUthuun> {

    public SphinxOfUthuun(UUID ownerId) {
        super(ownerId, 76, "Sphinx of Uthuun", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "M12";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SphinxOfUthuunEffect()));
    }

    public SphinxOfUthuun(final SphinxOfUthuun card) {
        super(card);
    }

    @Override
    public SphinxOfUthuun copy() {
        return new SphinxOfUthuun(this);
    }
}

class SphinxOfUthuunEffect extends OneShotEffect<SphinxOfUthuunEffect> {

    public SphinxOfUthuunEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    public SphinxOfUthuunEffect(final SphinxOfUthuunEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfUthuunEffect copy() {
        return new SphinxOfUthuunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.revealCards("Sphinx of Uthuun", cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());

            TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<Card>();
            Cards pile1CardsIds = new CardsImpl();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        pile1.add(card);
                        pile1CardsIds.add(card.getId());
                    }
                }
            }
            List<Card> pile2 = new ArrayList<Card>();
            Cards pile2CardsIds = new CardsImpl();
            for (UUID cardId :cards) {
                Card card = game.getCard(cardId);
                if (card != null && !pile1.contains(card)) {
                    pile2.add(card);
                    pile2CardsIds.add(card.getId());
                }
            }
            boolean choice = player.choosePile(Outcome.DestroyPermanent, "Choose a pile to put into hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder("Sphinx of Uthuun: Pile 1, going to ").append(pile1Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (": ");
            int i = 0;
            for (UUID cardUuid : pile1CardsIds) {
                i++;
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    sb.append(card.getName());
                    if (i < pile1CardsIds.size()) {
                        sb.append(", ");
                    }
                    card.moveToZone(pile1Zone, source.getId(), game, false);
                }
            }
            game.informPlayers(sb.toString());

            sb = new StringBuilder("Sphinx of Uthuun: Pile 2, going to ").append(pile2Zone.equals(Zone.HAND)?"Hand":"Graveyard").append (":");
            i = 0;
            for (UUID cardUuid : pile2CardsIds) {
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    i++;
                    sb.append(" ").append(card.getName());
                    if (i < pile2CardsIds.size()) {
                        sb.append(", ");
                    }
                    card.moveToZone(pile2Zone, source.getId(), game, false);
                }
            }
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
