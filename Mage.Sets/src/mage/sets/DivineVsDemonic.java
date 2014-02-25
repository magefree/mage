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

public class DivineVsDemonic extends ExpansionSet {
    private static final DivineVsDemonic fINSTANCE =  new DivineVsDemonic();

    public static DivineVsDemonic getInstance() {
        return fINSTANCE;
    }

    private DivineVsDemonic() {
        super("Duel Decks: Divine vs. Demonic", "DDC", "mage.sets.divinevsdemonic", new GregorianCalendar(2009, 04, 10).getTime(), SetType.REPRINT);
    }
}