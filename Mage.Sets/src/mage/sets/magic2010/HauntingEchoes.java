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

package mage.sets.magic2010;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HauntingEchoes extends CardImpl<HauntingEchoes> {

    public HauntingEchoes(UUID ownerId) {
        super(ownerId, 98, "Haunting Echoes", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "M10";
        this.color.setBlack(true);
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new HauntingEchoesEffect());
    }

    public HauntingEchoes(final HauntingEchoes card) {
        super(card);
    }

    @Override
    public HauntingEchoes copy() {
        return new HauntingEchoes(this);
    }
}

class HauntingEchoesEffect extends OneShotEffect<HauntingEchoesEffect> {

    private static final FilterBasicLandCard filter = new FilterBasicLandCard();

    public HauntingEchoesEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all cards from target player's graveyard other than basic land cards. For each card exiled this way, search that player's library for all cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public HauntingEchoesEffect(final HauntingEchoesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            for (Card card: targetPlayer.getGraveyard().getCards(game)) {
                if (!filter.match(card, game)) {
                    card.moveToExile(null, "", source.getId(), game);

                    FilterCard filterCard = new FilterCard("cards named " + card.getName());
                    filterCard.add(new NamePredicate(card.getName()));
                    int count = targetPlayer.getLibrary().count(filterCard, game);
                    TargetCardInLibrary target = new TargetCardInLibrary(count, count, filterCard);
                    target.setRequired(true);

                    player.searchLibrary(target, game, targetPlayer.getId());
                    List<UUID> targets = target.getTargets();
                    for(UUID cardId : targets){
                        Card libraryCard = game.getCard(cardId);
                        if (libraryCard != null) {
                            libraryCard.moveToExile(null, "", source.getId(), game);
                        }
                    }
                }
            }
            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public HauntingEchoesEffect copy() {
        return new HauntingEchoesEffect(this);
    }

}