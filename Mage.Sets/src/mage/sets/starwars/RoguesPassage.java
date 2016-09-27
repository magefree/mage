/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.starwars;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class RoguesPassage extends mage.sets.returntoravnica.RoguesPassage {

    public RoguesPassage(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "248";
        this.expansionSetCode = "SWS";
    }

    public RoguesPassage(final RoguesPassage card) {
        super(card);
    }

    @Override
    public RoguesPassage copy() {
        return new RoguesPassage(this);
    }
}