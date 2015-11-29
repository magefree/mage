/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.arabiannights;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class StoneThrowingDevils2 extends mage.sets.arabiannights.StoneThrowingDevils1 {
    
   public StoneThrowingDevils2(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 15;
        this.expansionSetCode = "ARN";
    }

    public StoneThrowingDevils2(final StoneThrowingDevils2 card) {
        super(card);
    }

    @Override
    public StoneThrowingDevils2 copy() {
        return new StoneThrowingDevils2(this);
    }
}

