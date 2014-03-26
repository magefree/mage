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

public class JaceVsChandra extends ExpansionSet {
    private static final JaceVsChandra fINSTANCE =  new JaceVsChandra();

    public static JaceVsChandra getInstance() {
        return fINSTANCE;
    }

    private JaceVsChandra() {
        super("Duel Decks: Jace vs. Chandra", "DD2", "mage.sets.jacevschandra", new GregorianCalendar(2008, 11, 07).getTime(), SetType.REPRINT);
        this.hasBasicLands = false;
    }
}
