/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.predicate.other;

import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class CounterCardPredicate implements Predicate<Card> {

    private final CounterType counter;

    public CounterCardPredicate(CounterType counter) {
        this.counter = counter;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getCounters(game).containsKey(counter);
    }

    @Override
    public String toString() {
        return "CounterType(" + counter.getName() + ')';
    }
}
