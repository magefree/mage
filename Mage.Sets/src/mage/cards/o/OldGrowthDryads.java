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
package mage.cards.o;

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
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public class OldGrowthDryads extends CardImpl {

    public OldGrowthDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Old-Growth Dryads enters the battlefield, each opponent may search his or her library for a basic land card, put it onto the battlefield tapped, then shuffle his or her library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OldGrowthDryadsEffect()));
    }

    public OldGrowthDryads(final OldGrowthDryads card) {
        super(card);
    }

    @Override
    public OldGrowthDryads copy() {
        return new OldGrowthDryads(this);
    }
}

class OldGrowthDryadsEffect extends OneShotEffect {

    OldGrowthDryadsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may search his or her library for a basic land card, put it onto the battlefield tapped, then shuffle his or her library";
    }

    OldGrowthDryadsEffect(final OldGrowthDryadsEffect effect) {
        super(effect);
    }

    @Override
    public OldGrowthDryadsEffect copy() {
        return new OldGrowthDryadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Player> playersThatSearched = new HashSet<>(1);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.chooseUse(Outcome.PutLandInPlay, "Search your library for a basic land card and put it onto the battlefield tapped?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
                if (opponent.searchLibrary(target, game)) {
                    Card targetCard = opponent.getLibrary().getCard(target.getFirstTarget(), game);
                    if (targetCard != null) {
                        opponent.moveCards(targetCard, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        playersThatSearched.add(opponent);
                    }
                }
            }
        }
        for (Player opponent : playersThatSearched) {
            opponent.shuffleLibrary(source, game);
        }
        return true;
    }
}
