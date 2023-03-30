package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachine extends ExpansionSet {

    private static final MarchOfTheMachine instance = new MarchOfTheMachine();

    public static MarchOfTheMachine getInstance() {
        return instance;
    }

    private MarchOfTheMachine() {
        super("March of the Machine", "MOM", ExpansionSet.buildDate(2023, 4, 21), SetType.EXPANSION);
        this.blockName = "March of the Machine";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Alabaster Host Sanctifier", 4, Rarity.COMMON, mage.cards.a.AlabasterHostSanctifier.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 267, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossoming Sands", 268, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Chandra, Hope's Beacon", 134, Rarity.MYTHIC, mage.cards.c.ChandraHopesBeacon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Hope's Beacon", 321, Rarity.MYTHIC, mage.cards.c.ChandraHopesBeacon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dismal Backwater", 269, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Faerie Mastermind", 58, Rarity.RARE, mage.cards.f.FaerieMastermind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faerie Mastermind", 352, Rarity.RARE, mage.cards.f.FaerieMastermind.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod, the Radiant Dawn", 17, Rarity.RARE, mage.cards.h.HeliodTheRadiantDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod, the Radiant Dawn", 293, Rarity.RARE, mage.cards.h.HeliodTheRadiantDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod, the Warped Eclipse", 17, Rarity.RARE, mage.cards.h.HeliodTheWarpedEclipse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heliod, the Warped Eclipse", 293, Rarity.RARE, mage.cards.h.HeliodTheWarpedEclipse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fairgrounds Trumpeter", 335, Rarity.COMMON, mage.cards.f.FairgroundsTrumpeter.class));
        cards.add(new SetCardInfo("Forest", 281, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Interdisciplinary Mascot", 326, Rarity.RARE, mage.cards.i.InterdisciplinaryMascot.class));
        cards.add(new SetCardInfo("Island", 278, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 270, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Monastery Mentor", 28, Rarity.MYTHIC, mage.cards.m.MonasteryMentor.class));
        cards.add(new SetCardInfo("Mountain", 280, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 68, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Plains", 277, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rugged Highlands", 271, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Ruins Recluse", 336, Rarity.UNCOMMON, mage.cards.r.RuinsRecluse.class));
        cards.add(new SetCardInfo("Scoured Barrens", 272, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Stoke the Flames", 166, Rarity.UNCOMMON, mage.cards.s.StokeTheFlames.class));
        cards.add(new SetCardInfo("Swamp", 279, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 273, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Thornwood Falls", 274, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tranquil Cove", 275, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Transcendent Message", 83, Rarity.RARE, mage.cards.t.TranscendentMessage.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 276, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Yargle and Multani", 256, Rarity.RARE, mage.cards.y.YargleAndMultani.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yargle and Multani", 317, Rarity.RARE, mage.cards.y.YargleAndMultani.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zephyr Winder", 328, Rarity.COMMON, mage.cards.z.ZephyrWinder.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new MarchOfTheMachineCollator();
//    }
}
