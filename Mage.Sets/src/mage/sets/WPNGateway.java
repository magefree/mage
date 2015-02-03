/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import java.util.GregorianCalendar;
import mage.constants.SetType;
import mage.cards.ExpansionSet;

/**
 *
 * @author fireshoes
 */
public class WPNGateway extends ExpansionSet {
    
    private static final WPNGateway fINSTANCE =  new WPNGateway();

    public static WPNGateway getInstance() {
        return fINSTANCE;
    }

    private WPNGateway() {
        super("WPN Gateway", "GRC", "mage.sets.wpngateway", new GregorianCalendar(2011, 6, 17).getTime(), SetType.REPRINT);
        this.hasBoosters = false;
        this.hasBasicLands = false;
    }
}
