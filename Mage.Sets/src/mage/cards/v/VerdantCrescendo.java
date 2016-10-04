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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class VerdantCrescendo extends CardImpl {

    public VerdantCrescendo(UUID ownerId) {
        super(ownerId, 273, "Verdant Crescendo", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}");
        this.expansionSetCode = "KLD";

        // Search your library for a basic land card and put it onto the battlefield tapped.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterBasicLandCard()), true, false));

        // Search your library and graveyard for a card named Nissa, Nature's Artisan, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new VerdantCrescendoEffect());
    }

    public VerdantCrescendo(final VerdantCrescendo card) {
        super(card);
    }

    @Override
    public VerdantCrescendo copy() {
        return new VerdantCrescendo(this);
    }
}

class VerdantCrescendoEffect extends OneShotEffect {

    public VerdantCrescendoEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library and graveyard for a card named Nissa, Nature's Artisan, reveal it, and put it into your hand. Then shuffle your library";
    }

    public VerdantCrescendoEffect(final VerdantCrescendoEffect effect) {
        super(effect);
    }

    @Override
    public VerdantCrescendoEffect copy() {
        return new VerdantCrescendoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            //Search your library and graveyard
            Cards allCards = new CardsImpl();
            allCards.addAll(controller.getLibrary().getCardList());
            allCards.addAll(controller.getGraveyard());
            FilterCard filter = new FilterCard("a card named Nissa, Nature's Artisan");
            filter.add(new NamePredicate("Nissa, Nature's Artisan"));
            TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
            if (controller.choose(outcome, allCards, target, game)) {
                Card cardFound = game.getCard(target.getFirstTarget());
                if (cardFound != null) {
                    controller.revealCards(sourceObject.getIdName(), allCards, game);
                    controller.moveCards(cardFound, Zone.HAND, source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
