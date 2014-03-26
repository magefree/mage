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
 * @author LevelX2
 */

public class ElvesVsGoblins extends ExpansionSet {
    private static final ElvesVsGoblins fINSTANCE =  new ElvesVsGoblins();

    public static ElvesVsGoblins getInstance() {
        return fINSTANCE;
    }

    private ElvesVsGoblins() {
        super("Duel Decks: Elves vs. Goblins", "EVG", "mage.sets.elvesvsgoblins", new GregorianCalendar(2007, 11, 16).getTime(), SetType.REPRINT);
        this.hasBasicLands = false;
    }
}