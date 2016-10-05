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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class ThievesAuction extends CardImpl {

    public ThievesAuction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}{R}");

        // Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it onto the battlefield tapped under his or her control. Repeat this process until all cards exiled this way have been chosen.
        this.getSpellAbility().addEffect(new ThievesAuctionEffect());
    }

    public ThievesAuction(final ThievesAuction card) {
        super(card);
    }

    @Override
    public ThievesAuction copy() {
        return new ThievesAuction(this);
    }
}

class ThievesAuctionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanents");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    ThievesAuctionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it onto the battlefield tapped under his or her control. Repeat this process until all cards exiled this way have been chosen";
    }

    ThievesAuctionEffect(final ThievesAuctionEffect effect) {
        super(effect);
    }

    @Override
    public ThievesAuctionEffect copy() {
        return new ThievesAuctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Exile all nontoken permanents.
            Cards exiledCards = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                exiledCards.add(permanent);
                controller.moveCardsToExile(permanent, source, game, true, CardUtil.getCardExileZoneId(game, source.getSourceId()), "Thieves' Auction");
            }
            // Starting with you, each player
            PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
            Player player = playerList.getCurrent(game);
            while (!exiledCards.isEmpty() && !game.hasEnded()) {
                if (player != null && player.canRespond()) {
                    // chooses one of the exiled cards
                    TargetCard target = new TargetCardInExile(new FilterCard());
                    if (player.choose(Outcome.PutCardInPlay, exiledCards, target, game)) {
                        // and puts it onto the battlefield tapped under his or her control.
                        Card chosenCard = exiledCards.get(target.getFirstTarget(), game);
                        player.moveCards(chosenCard, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        exiledCards.remove(chosenCard);
                    }
                }
                // Repeat this process until all cards exiled this way have been chosen.
                player = playerList.getNext(game);
            }
            return true;
        }
        return false;
    }
}
