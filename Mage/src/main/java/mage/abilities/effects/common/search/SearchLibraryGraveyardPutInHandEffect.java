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
package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Styxo
 */
public class SearchLibraryGraveyardPutInHandEffect extends OneShotEffect {

    private FilterCard filter;
    private boolean forceToSearchBoth;

    public SearchLibraryGraveyardPutInHandEffect(FilterCard filter) {
        this(filter, false);
    }

    public SearchLibraryGraveyardPutInHandEffect(FilterCard filter, boolean forceToSearchBoth) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.forceToSearchBoth = forceToSearchBoth;
        staticText = "Search your library and" + (forceToSearchBoth ? "" : "/or ") + " graveyard for a card named " + filter.getMessage() + ", reveal it, and put it into your hand. Then shuffle your library";
    }

    public SearchLibraryGraveyardPutInHandEffect(final SearchLibraryGraveyardPutInHandEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.forceToSearchBoth = effect.forceToSearchBoth;

    }

    @Override
    public SearchLibraryGraveyardPutInHandEffect copy() {
        return new SearchLibraryGraveyardPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Card cardFound = null;
        if (controller != null && sourceObject != null) {
            if (forceToSearchBoth || controller.chooseUse(outcome, "Search your library for a card named " + filter.getMessage() + '?', source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                target.clearChosen();
                if (controller.searchLibrary(target, game)) {
                    if (target.getTargets().size() > 0) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
                controller.shuffleLibrary(source, game);
            }

            if (cardFound == null && controller.chooseUse(outcome, "Search your graveyard for a card named " + filter.getMessage() + '?', source, game)) {
                TargetCard target = new TargetCard(0, 1, Zone.GRAVEYARD, filter);
                target.clearChosen();
                if (controller.choose(outcome, controller.getGraveyard(), target, game)) {
                    if (target.getTargets().size() > 0) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
            }

            if (cardFound != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
                controller.moveCards(cardFound, Zone.HAND, source, game);
            }

            return true;
        }

        return false;
    }

}
