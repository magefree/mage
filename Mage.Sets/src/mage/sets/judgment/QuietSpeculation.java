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
package mage.sets.judgment;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class QuietSpeculation extends CardImpl<QuietSpeculation> {

    private static final FilterCard filterCard = new FilterCard("cards with flashback");

    static {
        filterCard.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public QuietSpeculation(UUID ownerId) {
        super(ownerId, 49, "Quiet Speculation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "JUD";

        this.color.setBlue(true);

        // Search target player's library for up to three cards with flashback and put them into that player's graveyard. Then the player shuffles his or her library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filterCard);
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveEffect(target));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public QuietSpeculation(final QuietSpeculation card) {
        super(card);
    }

    @Override
    public QuietSpeculation copy() {
        return new QuietSpeculation(this);
    }
}

class SearchLibraryPutInGraveEffect extends SearchEffect<SearchLibraryPutInGraveEffect> {

    public SearchLibraryPutInGraveEffect(TargetCardInLibrary target) {
        super(target, Constants.Outcome.Neutral);
        staticText = "Search target player's library for up to three cards with flashback and put them into that player's graveyard. Then the player shuffles his or her library.";
    }

    public SearchLibraryPutInGraveEffect(final SearchLibraryPutInGraveEffect effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveEffect copy() {
        return new SearchLibraryPutInGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null){
                        card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                        cards.add(card);
                    }
                }
                if (cards.size() > 0) {
                    player.revealCards("Quiet Speculation", cards, game);
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

}
