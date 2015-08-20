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
public class FishliverOil2 extends mage.sets.arabiannights.FishliverOil1 {
    
   public FishliverOil2(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 18;
        this.expansionSetCode = "ARN";
    }

    public FishliverOil2(final FishliverOil2 card) {
        super(card);
    }

    @Override
    public FishliverOil2 copy() {
        return new FishliverOil2(this);
    }
}

