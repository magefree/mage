package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Fallout extends ExpansionSet {

    private static final Fallout instance = new Fallout();

    public static Fallout getInstance() {
        return instance;
    }

    private Fallout() {
        super("Fallout", "PIP", ExpansionSet.buildDate(2024, 3, 8), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Agent Frank Horrigan", 89, Rarity.RARE, mage.cards.a.AgentFrankHorrigan.class));
        cards.add(new SetCardInfo("Almost Perfect", 90, Rarity.RARE, mage.cards.a.AlmostPerfect.class));
        cards.add(new SetCardInfo("Alpha Deathclaw", 91, Rarity.RARE, mage.cards.a.AlphaDeathclaw.class));
        cards.add(new SetCardInfo("Arcane Signet", 224, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Ash Barrens", 253, Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Blasphemous Act", 188, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Command Tower", 787, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Crucible of Worlds", 357, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Desolate Mire", 146, Rarity.RARE, mage.cards.d.DesolateMire.class));
        cards.add(new SetCardInfo("Dr. Madison Li", 3, Rarity.MYTHIC, mage.cards.d.DrMadisonLi.class));
        cards.add(new SetCardInfo("Evolving Wilds", 263, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Ferrous Lake", 148, Rarity.RARE, mage.cards.f.FerrousLake.class));
        cards.add(new SetCardInfo("Forest", 853, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gary Clone", 16, Rarity.UNCOMMON, mage.cards.g.GaryClone.class));
        cards.add(new SetCardInfo("Guardian Project", 199, Rarity.RARE, mage.cards.g.GuardianProject.class));
        cards.add(new SetCardInfo("Hancock, Ghoulish Mayor", 45, Rarity.RARE, mage.cards.h.HancockGhoulishMayor.class));
        cards.add(new SetCardInfo("Heroic Intervention", 202, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Intelligence Bobblehead", 134, Rarity.UNCOMMON, mage.cards.i.IntelligenceBobblehead.class));
        cards.add(new SetCardInfo("Island", 847, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liberty Prime, Recharged", 5, Rarity.MYTHIC, mage.cards.l.LibertyPrimeRecharged.class));
        cards.add(new SetCardInfo("Mantle of the Ancients", 165, Rarity.RARE, mage.cards.m.MantleOfTheAncients.class));
        cards.add(new SetCardInfo("Mechanized Production", 178, Rarity.MYTHIC, mage.cards.m.MechanizedProduction.class));
        cards.add(new SetCardInfo("Mountain", 851, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mr. House, President and CEO", 7, Rarity.MYTHIC, mage.cards.m.MrHousePresidentAndCEO.class));
        cards.add(new SetCardInfo("Nuka-Cola Vending Machine", 137, Rarity.UNCOMMON, mage.cards.n.NukaColaVendingMachine.class));
        cards.add(new SetCardInfo("Open the Vaults", 168, Rarity.RARE, mage.cards.o.OpenTheVaults.class));
        cards.add(new SetCardInfo("Overflowing Basin", 152, Rarity.RARE, mage.cards.o.OverflowingBasin.class));
        cards.add(new SetCardInfo("Path of Ancestry", 279, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Plains", 845, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Powder Ganger", 65, Rarity.RARE, mage.cards.p.PowderGanger.class));
        cards.add(new SetCardInfo("Puresteel Paladin", 170, Rarity.RARE, mage.cards.p.PuresteelPaladin.class));
        cards.add(new SetCardInfo("Radstorm", 37, Rarity.RARE, mage.cards.r.Radstorm.class));
        cards.add(new SetCardInfo("Ravages of War", 882, Rarity.MYTHIC, mage.cards.r.RavagesOfWar.class));
        cards.add(new SetCardInfo("Ruinous Ultimatum", 220, Rarity.RARE, mage.cards.r.RuinousUltimatum.class));
        cards.add(new SetCardInfo("Sol Ring", 239, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 240, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sunscorched Divide", 153, Rarity.RARE, mage.cards.s.SunscorchedDivide.class));
        cards.add(new SetCardInfo("Swamp", 849, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tarmogoyf", 349, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 313, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("V.A.T.S.", 50, Rarity.RARE, mage.cards.v.VATS.class));
        cards.add(new SetCardInfo("Vandalblast", 355, Rarity.UNCOMMON, mage.cards.v.Vandalblast.class));
        cards.add(new SetCardInfo("Vault 101: Birthday Party", 28, Rarity.RARE, mage.cards.v.Vault101BirthdayParty.class));
        cards.add(new SetCardInfo("War Room", 1068, Rarity.RARE, mage.cards.w.WarRoom.class));
        cards.add(new SetCardInfo("Wasteland", 361, Rarity.RARE, mage.cards.w.Wasteland.class));
    }
}
