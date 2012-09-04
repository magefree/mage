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

package mage.sets.darksteel;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public class DarksteelColossus extends CardImpl<DarksteelColossus> {

    public DarksteelColossus(UUID ownerId) {
        super(ownerId, 109, "Darksteel Colossus", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{11}");
        this.expansionSetCode = "DST";
        this.subtype.add("Golem");
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new IndestructibleAbility());
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new DarksteelColossusEffect(), false));
    }

    public DarksteelColossus(final DarksteelColossus card) {
        super(card);
    }

    @Override
    public DarksteelColossus copy() {
        return new DarksteelColossus(this);
    }

}

class DarksteelColossusEffect extends OneShotEffect<DarksteelColossusEffect> {
    DarksteelColossusEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "reveal {this} and shuffle it into its owner's library instead";
    }

    DarksteelColossusEffect(final DarksteelColossusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card c = game.getCard(source.getSourceId());
        if (c != null) {
            Player player = game.getPlayer(c.getOwnerId());
            if (player != null) {
                Cards cards = new CardsImpl();
                cards.add(c);
                player.revealCards("Blightsteel Colossus", cards, game);
                c.moveToZone(Constants.Zone.LIBRARY, source.getSourceId(), game, true);
                player.shuffleLibrary(game);
                return true;
            }
        }
        return false;
    }

    @Override
    public DarksteelColossusEffect copy() {
        return new DarksteelColossusEffect(this);
    }

}