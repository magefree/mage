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
package mage.sets.zendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class MerfolkWayfinder extends CardImpl {

    public MerfolkWayfinder(UUID ownerId) {
        super(ownerId, 56, "Merfolk Wayfinder", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Merfolk");
        this.subtype.add("Scout");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MerfolkWayfinderEffect()));
    }

    public MerfolkWayfinder(final MerfolkWayfinder card) {
        super(card);
    }

    @Override
    public MerfolkWayfinder copy() {
        return new MerfolkWayfinder(this);
    }
}

class MerfolkWayfinderEffect extends OneShotEffect {

    public MerfolkWayfinderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top three cards of your library. Put all Island cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public MerfolkWayfinderEffect(final MerfolkWayfinderEffect effect) {
        super(effect);
    }

    @Override
    public MerfolkWayfinderEffect copy() {
        return new MerfolkWayfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        Cards cardsToReveal = new CardsImpl();
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cardsToReveal.add(card);
                if (card.hasSubtype("Island", game)) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                } else {
                    cards.add(card);
                }
            }
        }
        player.revealCards("Merfolk Wayfinder", cardsToReveal, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
