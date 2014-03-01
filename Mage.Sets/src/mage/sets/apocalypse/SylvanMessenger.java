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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SylvanMessenger extends CardImpl<SylvanMessenger> {

    public SylvanMessenger(UUID ownerId) {
        super(ownerId, 87, "Sylvan Messenger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "APC";
        this.subtype.add("Elf");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Sylvan Messenger enters the battlefield, reveal the top four cards of your library. Put all Elf cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SylvanMessengerEffect()));

    }

    public SylvanMessenger(final SylvanMessenger card) {
        super(card);
    }

    @Override
    public SylvanMessenger copy() {
        return new SylvanMessenger(this);
    }
}

class SylvanMessengerEffect extends OneShotEffect<SylvanMessengerEffect> {

    private static final FilterCard filter = new FilterCard("Elf");
    static {
        filter.add(new SubtypePredicate("Elf"));
    }

    public SylvanMessengerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. Put all Elf cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public SylvanMessengerEffect(final SylvanMessengerEffect effect) {
        super(effect);
    }

    @Override
    public SylvanMessengerEffect copy() {
        return new SylvanMessengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cardsToReveal = new CardsImpl(Zone.PICK);
            Cards cardsElf = new CardsImpl(Zone.PICK);
            Cards cards2 = new CardsImpl(Zone.PICK);
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cardsToReveal.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                    if (filter.match(card, game)) {
                        cardsElf.add(card);
                    } else {
                        cards2.add(card);
                    }
                }
            }

            Card sourceCard = game.getCard(source.getSourceId());
            if (!cardsToReveal.isEmpty() && sourceCard != null) {
                player.revealCards(sourceCard.getName(), cardsToReveal, game);
            }

            for (Card card: cardsElf.getCards(game)) {
                player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
            }

            TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            target.setRequired(true);
            while (cards2.size() > 0 && player.choose(Outcome.Detriment, cards2, target, game)) {
                Card card = cards2.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards2.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                }
                target.clearChosen();
            }
        }
        return true;
    }
}
