/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.counters.common;

import mage.counters.Counter;

/**
 *
 * @author Plopman
 */
public class DepletionCounter extends Counter<DepletionCounter> {

    public DepletionCounter() {
        super("Depletion");
        this.count = 1;
    }

    public DepletionCounter(int amount) {
        super("Depletion");
        this.count = amount;
    }
}