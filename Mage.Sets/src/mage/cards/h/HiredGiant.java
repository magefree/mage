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
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000 & L_J
 */
public class HiredGiant extends CardImpl {

    public HiredGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Hired Giant enters the battlefield, each other player may search their library for a land card and put that card onto the battlefield. Then each player who searched their library this way shuffles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HiredGiantEffect()));
    }

    public HiredGiant(final HiredGiant card) {
        super(card);
    }

    @Override
    public HiredGiant copy() {
        return new HiredGiant(this);
    }
}

class HiredGiantEffect extends OneShotEffect {

    HiredGiantEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may search their library for a land card and put that card onto the battlefield. Then each player who searched their library this way shuffles it";
    }

    HiredGiantEffect(final HiredGiantEffect effect) {
        super(effect);
    }

    @Override
    public HiredGiantEffect copy() {
        return new HiredGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Player> playersThatSearched = new HashSet<>(1);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (playerId != controller.getId()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && player.chooseUse(Outcome.PutCreatureInPlay, "Search your library for a land card and put it onto the battlefield?", source, game)) {
                        TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
                        if (player.searchLibrary(target, game)) {
                            Card targetCard = player.getLibrary().getCard(target.getFirstTarget(), game);
                            if (targetCard != null) {
                                player.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
                                playersThatSearched.add(player);
                            }
                        }
                    }
                }
            }
            for (Player player : playersThatSearched) {
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
