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
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class RevealCardsFromLibraryUntilEffect extends OneShotEffect {

    private FilterCard filter;
    private Zone zoneToPutRest;
    private Zone zoneToPutCard;
    private boolean shuffleRestInto;
    private boolean anyOrder;

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest) {
        this(filter, zoneToPutCard, zoneToPutRest, false, false);
    }

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest, boolean shuffleRestInto) {
        this(filter, zoneToPutCard, zoneToPutRest, shuffleRestInto, false);
    }

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest, boolean shuffleRestInto, boolean anyOrder) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.zoneToPutCard = zoneToPutCard;
        this.zoneToPutRest = zoneToPutRest;
        this.shuffleRestInto = shuffleRestInto;
        this.anyOrder = anyOrder;
        setText();
    }

    public RevealCardsFromLibraryUntilEffect(final RevealCardsFromLibraryUntilEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.zoneToPutCard = effect.zoneToPutCard;
        this.zoneToPutRest = effect.zoneToPutRest;
        this.shuffleRestInto = effect.shuffleRestInto;
        this.anyOrder = effect.anyOrder;
        setText();
    }

    @Override
    public RevealCardsFromLibraryUntilEffect copy() {
        return new RevealCardsFromLibraryUntilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && controller.getLibrary().size() > 0) {
            Cards cards = new CardsImpl();
            Library library = controller.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            } while (library.size() > 0 && card != null && !filter.match(card, game));
            // reveal cards
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (filter.match(card, game)) {
                    // put card in correct zone
                    controller.moveCards(card, zoneToPutCard, source, game);
                    // remove it from revealed card list
                    cards.remove(card);
                }
                // Put the rest in correct zone
                switch (zoneToPutRest) {
                    case LIBRARY: {
                        if (cards.size() > 0) {
                            if (shuffleRestInto) {
                                library.addAll(cards.getCards(game), game);
                            } else {
                                controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
                            }
                        }
                        break;
                    }
                    default:
                        if (cards.size() > 0) {
                            controller.moveCards(cards, zoneToPutRest, source, game);
                        }
                }
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("reveal cards from the top of your library until you reveal a " + filter.getMessage() + ". Put that card ");

        switch (zoneToPutCard) {
            case HAND: {
                sb.append("into your hand ");
                break;
            }
            case BATTLEFIELD: {
                sb.append("onto the battlefield");
                break;
            }
        }

        switch (zoneToPutRest) {
            case GRAVEYARD: {
                sb.append(" and put all other cards revealed this way into your graveyard.");
                break;
            }
            case LIBRARY: {
                if (shuffleRestInto) {
                    sb.append(", then shuffles the rest into his or her library.");
                } else {
                    sb.append(" and the rest on the bottom of your library in ");
                    if (anyOrder) {
                        sb.append("any");
                    } else {
                        sb.append("random");

                    }
                    sb.append(" order.");
                }
                break;
            }
            case EXILED: {
                sb.append(" and exile all other cards revealed this way.");
                break;
            }
        }
        staticText = sb.toString();
    }
}
