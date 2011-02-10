package org.mage.test.clientside.bdd.given;

import org.mage.test.clientside.base.MageAPI;
import org.mage.test.clientside.base.MageBase;
import org.mage.test.clientside.base.exception.CardNotFoundException;
import org.mage.test.clientside.bdd.StepState;

public class A {
    private StepState step;
    public A(StepState step) {
        this.step = step;
    }
    public void card(String cardName) throws Exception {
        StepState current = MageAPI.defineStep(this.step);
        if (current.equals(StepState.GIVEN)) {
            if (!MageBase.getInstance().giveme(cardName)) {
                throw new CardNotFoundException("Couldn't create card: " + cardName);
            }
        } else if (current.equals(StepState.THEN)) {
            if (!MageBase.getInstance().checkIhave(cardName)) {
                throw new CardNotFoundException("Couldn't find requested card in hand: " + cardName);
            }
        }
    }
}
