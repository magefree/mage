package mage.sets;

import mage.ObjectColor;
import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class Turing extends ExpansionSet {

    private static final Turing instance = new Turing();

    public static Turing getInstance() {
        return instance;
    }

    private Turing() {
        super("Turing", "TNG", ExpansionSet.buildDate(2022, 2, 19), SetType.EXPANSION);
        this.blockName = "Turing";
        this.hasBoosters = false;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 0;
        this.numBoosterUncommon = 0;
        this.numBoosterRare = 0;
        this.ratioBoosterMythic = 0;
	cards.add(new SetCardInfo("Rotlung Reanimator Aetherborn-White-Sliver", 1, Rarity.COMMON, mage.cards.r.RotlungReanimatorASW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Basilisk-Green-Elf", 2, Rarity.COMMON, mage.cards.r.RotlungReanimatorBEG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Cephalid-White-Sliver", 3, Rarity.COMMON, mage.cards.r.RotlungReanimatorCSW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Demon-Green-Aetherborn", 4, Rarity.COMMON, mage.cards.r.RotlungReanimatorDAG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Elf-White-Demon", 5, Rarity.COMMON, mage.cards.r.RotlungReanimatorEDW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Faerie-Green-Harpy", 6, Rarity.COMMON, mage.cards.r.RotlungReanimatorFHG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Giant-Green-Juggernaut", 7, Rarity.COMMON, mage.cards.r.RotlungReanimatorGJG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Harpy-White-Faerie", 8, Rarity.COMMON, mage.cards.r.RotlungReanimatorHFW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Illusion-Green-Faerie", 9, Rarity.COMMON, mage.cards.r.RotlungReanimatorIFG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Juggernaut-White-Illusion", 10, Rarity.COMMON, mage.cards.r.RotlungReanimatorJIW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Noggle-Green-Orc", 11, Rarity.COMMON, mage.cards.r.RotlungReanimatorNOG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Orc-White-Pegasus", 12, Rarity.COMMON, mage.cards.r.RotlungReanimatorOPW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Rhino-Blue-Assassin", 13, Rarity.COMMON, mage.cards.r.RotlungReanimatorRAB.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Sliver-Green-Cephalid", 14, Rarity.COMMON, mage.cards.r.RotlungReanimatorSCG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Aetherborn-Green-Cephalid", 15, Rarity.COMMON, mage.cards.r.RotlungReanimatorACG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Basilisk-Green-Cephalid", 16, Rarity.COMMON, mage.cards.r.RotlungReanimatorBCG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Cephalid-White-Basilisk", 17, Rarity.COMMON, mage.cards.r.RotlungReanimatorCBW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Demon-Green-Elf", 18, Rarity.COMMON, mage.cards.r.RotlungReanimatorDEG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Elf-White-Aetherborn", 19, Rarity.COMMON, mage.cards.r.RotlungReanimatorEAW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Giant-Green-Harpy", 20, Rarity.COMMON, mage.cards.r.RotlungReanimatorGHG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Harpy-White-Giant", 21, Rarity.COMMON, mage.cards.r.RotlungReanimatorHGW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Illusion-Green-Juggernaut", 22, Rarity.COMMON, mage.cards.r.RotlungReanimatorIJG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Juggernaut-White-Giant", 23, Rarity.COMMON, mage.cards.r.RotlungReanimatorJGW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Leviathan-Green-Juggernaut", 24, Rarity.COMMON, mage.cards.r.RotlungReanimatorLJG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Myr-Green-Orc", 25, Rarity.COMMON, mage.cards.r.RotlungReanimatorMOG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Noggle-Green-Orc", 26, Rarity.COMMON, mage.cards.r.RotlungReanimatorNOG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Orc-White-Noggle", 27, Rarity.COMMON, mage.cards.r.RotlungReanimatorONW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Pegasus-Green-Sliver", 28, Rarity.COMMON, mage.cards.r.RotlungReanimatorPSG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Sliver-White-Myr", 29, Rarity.COMMON, mage.cards.r.RotlungReanimatorSMW.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Lhurgoyf-Black-Cephalid", 30, Rarity.COMMON, mage.cards.r.RotlungReanimatorLCB.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Lhurgoyf-Green-Lhurgoyf", 31, Rarity.COMMON, mage.cards.r.RotlungReanimatorLLG.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Rat-Black-Cephalid", 32, Rarity.COMMON, mage.cards.r.RotlungReanimatorRCB.class));
	cards.add(new SetCardInfo("Rotlung Reanimator Rat-White-Rat", 33, Rarity.COMMON, mage.cards.r.RotlungReanimatorRRW.class));

	cards.add(new SetCardInfo("Xathrid Necromancer Kavu-White-Leviathan", 34, Rarity.COMMON, mage.cards.x.XathridNecromancerKLW.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Leviathan-White-Illusion", 35, Rarity.COMMON, mage.cards.x.XathridNecromancerLIW.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Myr-White-Basilisk", 36, Rarity.COMMON, mage.cards.x.XathridNecromancerMBW.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Pegasus-Green-Rhino", 37, Rarity.COMMON, mage.cards.x.XathridNecromancerPRG.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Faerie-Green-Kavu", 38, Rarity.COMMON, mage.cards.x.XathridNecromancerFKG.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Kavu-Green-Faerie", 39, Rarity.COMMON, mage.cards.x.XathridNecromancerKFG.class));
	cards.add(new SetCardInfo("Xathrid Necromancer Rhino-White-Sliver", 40, Rarity.COMMON, mage.cards.x.XathridNecromancerRSW.class));

	cards.add(new SetCardInfo("Fungus Sliver Incarnation", 41, Rarity.COMMON, mage.cards.f.FungusSliverI.class));
	cards.add(new SetCardInfo("Dread of Night Black", 42, Rarity.COMMON, mage.cards.d.DreadOfNightB.class));
	cards.add(new SetCardInfo("Blazing Archon AssemblyWorker", 43, Rarity.COMMON, mage.cards.b.BlazingArchonAssemblyWorker.class));
	cards.add(new SetCardInfo("Vigor AssemblyWorker", 44, Rarity.COMMON, mage.cards.v.VigorAssemblyWorker.class));
    }
}
