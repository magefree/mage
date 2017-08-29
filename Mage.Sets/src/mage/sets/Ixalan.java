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
        cards.add(new SetCardInfo("Dragonskull Summit", 252, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 253, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Glacial Fortress", 255, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Rootbound Crag", 256, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 257, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", 213, Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class));
        cards.add(new SetCardInfo("Vraska's Contempt", 129, Rarity.RARE, mage.cards.v.VraskasContempt.class));
        cards.add(new SetCardInfo("Waker of the Wilds", 215, Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 130, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
    }
}
