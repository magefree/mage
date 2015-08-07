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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author LokiX, BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutInHandEffect extends SearchEffect {

    private boolean revealCards = false;
    private boolean forceShuffle;
    private String rulePrefix;

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target) {
        this(target, false, true);
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards) {
        this(target, revealCards, true);
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards, boolean forceShuffle) {
        this(target, revealCards, forceShuffle, "search your library for ");
    }

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target, boolean revealCards, boolean forceShuffle, String rulePrefix) {
        super(target, Outcome.DrawCard);
        this.revealCards = revealCards;
        this.forceShuffle = forceShuffle;
        this.rulePrefix = rulePrefix;
        setText();
    }

    public SearchLibraryPutInHandEffect(final SearchLibraryPutInHandEffect effect) {
        super(effect);
        this.revealCards = effect.revealCards;
        this.forceShuffle = effect.forceShuffle;
        this.rulePrefix = effect.rulePrefix;
    }

    @Override
    public SearchLibraryPutInHandEffect copy() {
        return new SearchLibraryPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        target.clearChosen();
        if (controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.moveCards(cards, null, Zone.HAND, source, game);
                if (revealCards) {
                    String name = "Reveal";
                    Card sourceCard = game.getCard(source.getSourceId());
                    if (sourceCard != null) {
                        name = sourceCard.getIdName();
                    }
                    controller.revealCards(name, cards, game);
                }
            }
            controller.shuffleLibrary(game);
            return true;
        }
        if (forceShuffle) {
            controller.shuffleLibrary(game);
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(rulePrefix);
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" ");
            sb.append(target.getTargetName()).append(revealCards ? ", reveal them, " : "").append(" and put them into your hand");
        } else {
            sb.append("a ").append(target.getTargetName()).append(revealCards ? ", reveal it, " : "").append(" and put that card into your hand");
        }
        if (forceShuffle) {
            sb.append(". Then shuffle your library");
        } else {
            sb.append(". If you do, shuffle your library");
        }
        staticText = sb.toString();
    }

}
