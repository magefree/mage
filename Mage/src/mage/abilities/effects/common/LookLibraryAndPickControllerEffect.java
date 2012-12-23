/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX
 */

public class LookLibraryAndPickControllerEffect extends LookLibraryControllerEffect {

    protected FilterCard filter; // which kind of cards to reveal
    protected DynamicValue numberToPick;
    protected boolean revealPickedCards = true;
    protected Zone targetPickedCards = Zone.HAND; // HAND 
    protected int foundCardsToPick = 0;
    protected boolean optional;

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, boolean putOnTop) {
            this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, putOnTop, true);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, boolean putOnTop, boolean reveal) {
            this(numberOfCards, mayShuffleAfter, numberToPick, pickFilter, Zone.LIBRARY, putOnTop, reveal);
    }

    public LookLibraryAndPickControllerEffect(DynamicValue numberOfCards, boolean mayShuffleAfter, DynamicValue numberToPick, FilterCard pickFilter, Zone targetZoneLookedCards, boolean putOnTop, boolean reveal) {
            super(Outcome.DrawCard, numberOfCards, mayShuffleAfter, targetZoneLookedCards, putOnTop);
            this.numberToPick = numberToPick;
            this.filter = pickFilter;
            this.revealPickedCards = reveal;
    }

    public LookLibraryAndPickControllerEffect(final LookLibraryAndPickControllerEffect effect) {
        super(effect);
        this.numberToPick = effect.numberToPick.copy();
        this.filter = effect.filter.copy();
        this.revealPickedCards = effect.revealPickedCards;
        this.targetPickedCards = effect.targetPickedCards;
    }

    @Override
    public LookLibraryAndPickControllerEffect copy() {
        return new LookLibraryAndPickControllerEffect(this);

    }
    @Override
    protected void cardLooked(Card card, Game game, Ability source) {
        if (numberToPick.calculate(game, source) > 0 && filter.match(card, game)) {
            ++foundCardsToPick;
        }
    }

    @Override
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source, String windowName) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && foundCardsToPick > 0) {
            if (!optional || player.chooseUse(Outcome.DrawCard, "Do you wish to reveal "+filter.getMessage()+" and put it into your hand?", game)) {
                FilterCard pickFilter = filter.copy();
                // Set the pick message
                StringBuilder sb = new StringBuilder(filter.getMessage()).append(" to ");
                if (revealPickedCards) {
                    sb.append("reveal and ");
                }
                sb.append("put into your hand");

                pickFilter.setMessage(sb.toString());
                TargetCard target = new TargetCard(Zone.PICK, pickFilter);
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(targetPickedCards, source.getId(), game, false);
                        if (revealPickedCards) {
                            Cards reveal = new CardsImpl(Zone.OUTSIDE);
                            reveal.add(card);
                            player.revealCards(windowName, reveal, game);
                        }
                    }
                }
            }
        }

    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (numberToPick.calculate(null, null) > 0) {
            if (revealPickedCards) {
                sb.append(". You may reveal a ");
                sb.append(filter.getMessage()).append(" from among them and put it into your ");
            } else {
                sb.append(". Put one of them into your ");
            }
            sb.append(targetPickedCards.toString().toLowerCase());
            if (targetZoneLookedCards == Zone.LIBRARY) {
                sb.append(". Put the rest ");
                if (putOnTop) {
                    sb.append("back ");
                } else {
                    sb.append("on the bottom of your library ");
                }
                sb.append("in any order");
            } else if (targetZoneLookedCards == Zone.GRAVEYARD) {
                sb.append(" and the other into your graveyard");
            }
        }
        // get text frame from super class and inject action text
        return setText(mode, sb.toString());
    }    

}
