/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class Ixalan extends ExpansionSet {

    private static final Ixalan instance = new Ixalan();

    public static Ixalan getInstance() {
        return instance;
    }

    private Ixalan() {
        super("Ixalan", "XLN", ExpansionSet.buildDate(2017, 9, 29), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Verdant Sun's Avatar", 410, Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class));
        cards.add(new SetCardInfo("Vraska's Contempt", 210, Rarity.RARE, mage.cards.v.VraskasContempt.class));
        cards.add(new SetCardInfo("Waker of the Wilds", 415, Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 220, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
    }
}
