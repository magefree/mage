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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class ArmoredSkaab extends CardImpl<ArmoredSkaab> {

    public ArmoredSkaab(UUID ownerId) {
        super(ownerId, 43, "Armored Skaab", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Armored Skaab enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArmoredSkaabEffect()));
    }

    public ArmoredSkaab(final ArmoredSkaab card) {
        super(card);
    }

    @Override
    public ArmoredSkaab copy() {
        return new ArmoredSkaab(this);
    }
}

class ArmoredSkaabEffect extends OneShotEffect<ArmoredSkaabEffect> {

    public ArmoredSkaabEffect() {
        super(Outcome.Discard);
        this.staticText = "put the top four cards of your library into your graveyard";
    }

    public ArmoredSkaabEffect(final ArmoredSkaabEffect effect) {
        super(effect);
    }

    @Override
    public ArmoredSkaabEffect copy() {
        return new ArmoredSkaabEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int cardsCount = Math.min(4, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
