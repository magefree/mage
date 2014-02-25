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

public class GarrukVsLiliana extends ExpansionSet {
    private static final GarrukVsLiliana fINSTANCE =  new GarrukVsLiliana();

    public static GarrukVsLiliana getInstance() {
        return fINSTANCE;
    }

    private GarrukVsLiliana() {
        super("Duel Decks: Garruk vs. Liliana", "DDD", "mage.sets.garrukvsliliana", new GregorianCalendar(2009, 10, 30).getTime(), SetType.REPRINT);
    }
}