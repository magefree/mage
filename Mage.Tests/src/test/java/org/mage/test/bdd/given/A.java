package org.mage.test.bdd.given;

import org.mage.test.base.MageBase;
import org.mage.test.bdd.StepController;
import org.mage.test.bdd.StepState;

public class A {
    private StepState step;
    public A(StepState step) {
        this.step = step;
    }
    public void card(String cardName) throws Exception {
        MageBase.getInstance().giveme(cardName);
        Thread.sleep(4000);
        if (!MageBase.getInstance().checkIhave(cardName)) {
            throw new IllegalStateException("Couldn't find a card in hand: " + cardName);
        }
        StepController.currentState = this.step;
    }
}
