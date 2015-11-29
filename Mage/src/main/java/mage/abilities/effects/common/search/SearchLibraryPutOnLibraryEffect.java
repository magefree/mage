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
import mage.abilities.effects.SearchEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutOnLibraryEffect extends SearchEffect {

    private boolean reveal;
    private boolean forceShuffle;

    public SearchLibraryPutOnLibraryEffect(TargetCardInLibrary target) {
        this(target, false, true);
        setText();
    }

    public SearchLibraryPutOnLibraryEffect(TargetCardInLibrary target, boolean reveal, boolean forceShuffle) {
        super(target, Outcome.DrawCard);
        this.reveal = reveal;
        this.forceShuffle = forceShuffle;
        setText();
    }

    public SearchLibraryPutOnLibraryEffect(final SearchLibraryPutOnLibraryEffect effect) {
        super(effect);
        this.reveal = effect.reveal;
        this.forceShuffle = effect.forceShuffle;
    }

    @Override
    public SearchLibraryPutOnLibraryEffect copy() {
        return new SearchLibraryPutOnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        if (controller.searchLibrary(target, game)) {
            Cards foundCards = new CardsImpl(target.getTargets());
            if (reveal && !foundCards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), foundCards, game);
            }
            if (forceShuffle) {
                controller.shuffleLibrary(game);
            }
            controller.putCardsOnTopOfLibrary(foundCards, game, source, reveal);
            return true;
        }
        // shuffle
        if (forceShuffle) {
            controller.shuffleLibrary(game);
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("search your library for a ").append(target.getTargetName());
        if (reveal) {
            sb.append(" and reveal that card. Shuffle");
        } else {
            sb.append(", then shuffle");
        }
        sb.append(" your library and put that card on top of it");
        staticText = sb.toString();
    }

}
