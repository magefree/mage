package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwp11
 */
public class WizardsPlayNetwork2011 extends ExpansionSet {

    private static final WizardsPlayNetwork2011 instance = new WizardsPlayNetwork2011();

    public static WizardsPlayNetwork2011 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2011() {
        super("Wizards Play Network 2011", "PW11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Auramancer", 77, Rarity.RARE, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Bloodcrazed Neonate", 83, Rarity.RARE, mage.cards.b.BloodcrazedNeonate.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 84, Rarity.RARE, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Circle of Flame", 78, Rarity.RARE, mage.cards.c.CircleOfFlame.class));
        cards.add(new SetCardInfo("Curse of the Bloody Tome", 80, Rarity.RARE, mage.cards.c.CurseOfTheBloodyTome.class));
        cards.add(new SetCardInfo("Fling", 69, Rarity.RARE, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Master's Call", 64, Rarity.RARE, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Maul Splicer", 72, Rarity.RARE, mage.cards.m.MaulSplicer.class));
        cards.add(new SetCardInfo("Plague Myr", 65, Rarity.RARE, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Shrine of Burning Rage", 73, Rarity.RARE, mage.cards.s.ShrineOfBurningRage.class));
        cards.add(new SetCardInfo("Signal Pest", 66, Rarity.RARE, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 70, Rarity.RARE, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Tormented Soul", 76, Rarity.RARE, mage.cards.t.TormentedSoul.class));
        cards.add(new SetCardInfo("Vault Skirge", 71, Rarity.RARE, mage.cards.v.VaultSkirge.class));
     }
}
