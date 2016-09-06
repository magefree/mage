/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.sets;

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */

public class MindVsMight extends ExpansionSet {
    private static final MindVsMight fINSTANCE =  new MindVsMight();

    public static MindVsMight getInstance() {
        return fINSTANCE;
    }

    private MindVsMight() {
        super("Duel Decks: Mind vs. Might", "DDS", "mage.sets.mindvsmight", new GregorianCalendar(2017, 3, 31).getTime(), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = false;
    }
}
