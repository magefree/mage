/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterCard extends FilterObject<Card> {

    private static final long serialVersionUID = 1L;
    protected List<ObjectPlayerPredicate<ObjectPlayer<Card>>> extraPredicates = new ArrayList<>();

    public FilterCard() {
        super("card");
    }

    public FilterCard(String name) {
        super(name);
    }

    public FilterCard(FilterCard filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    //20130711 708.6c
    /* If anything performs a comparison involving multiple characteristics or 
     * values of one or more split cards in any zone other than the stack or 
     * involving multiple characteristics or values of one or more fused split 
     * spells, each characteristic or value is compared separately. If each of 
     * the individual comparisons would return a “yes” answer, the whole 
     * comparison returns a “yes” answer. The individual comparisons may involve
     * different halves of the same split card.
     */
    
    @Override
    public boolean match(Card card, Game game) {
        if(card.isSplitCard()){
            return super.match(((SplitCard)card).getLeftHalfCard(), game) ||
                    super.match(((SplitCard)card).getRightHalfCard(), game);
        }
        else{
           return super.match(card, game); 
        }
    }
    public boolean match(Card card, UUID playerId, Game game) {
        if (!this.match(card, game)) {
            return false;
        }
        
        return Predicates.and(extraPredicates).apply(new ObjectPlayer<>(card, playerId), game);
    }

    public boolean match(Card card, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(card, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer<>(card, sourceId, playerId), game);
    }

    public void add(ObjectPlayerPredicate predicate) {
        extraPredicates.add(predicate);
    }

    public Set<Card> filter(Set<Card> cards, Game game) {
        Set<Card> filtered = new HashSet<>();
        for (Card card : cards) {
            if (match(card, game)) {
                filtered.add(card);
            }
        }
        return filtered;
    }

    @Override
    public FilterCard copy() {
        return new FilterCard(this);
    }
}
