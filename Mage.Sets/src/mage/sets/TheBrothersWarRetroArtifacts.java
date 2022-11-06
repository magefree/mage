package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBrothersWarRetroArtifacts extends ExpansionSet {

    private static final TheBrothersWarRetroArtifacts instance = new TheBrothersWarRetroArtifacts();

    public static TheBrothersWarRetroArtifacts getInstance() {
        return instance;
    }

    private TheBrothersWarRetroArtifacts() {
        super("The Brothers' War Retro Artifacts", "BRR", ExpansionSet.buildDate(2022, 11, 18), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 63;

        cards.add(new SetCardInfo("Adaptive Automaton", 1, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class));
        cards.add(new SetCardInfo("Aetherflux Reservoir", 2, Rarity.MYTHIC, mage.cards.a.AetherfluxReservoir.class));
        cards.add(new SetCardInfo("Altar of Dementia", 3, Rarity.MYTHIC, mage.cards.a.AltarOfDementia.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 4, Rarity.RARE, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Astral Cornucopia", 5, Rarity.RARE, mage.cards.a.AstralCornucopia.class));
        cards.add(new SetCardInfo("Blackblade Reforged", 6, Rarity.RARE, mage.cards.b.BlackbladeReforged.class));
        cards.add(new SetCardInfo("Bone Saw", 7, Rarity.UNCOMMON, mage.cards.b.BoneSaw.class));
        cards.add(new SetCardInfo("Burnished Hart", 8, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Caged Sun", 9, Rarity.MYTHIC, mage.cards.c.CagedSun.class));
        cards.add(new SetCardInfo("Chromatic Lantern", 10, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Chromatic Star", 11, Rarity.UNCOMMON, mage.cards.c.ChromaticStar.class));
        cards.add(new SetCardInfo("Cloud Key", 12, Rarity.RARE, mage.cards.c.CloudKey.class));
        cards.add(new SetCardInfo("Defense Grid", 13, Rarity.RARE, mage.cards.d.DefenseGrid.class));
        cards.add(new SetCardInfo("Door To Nothingness", 14, Rarity.RARE, mage.cards.d.DoorToNothingness.class));
        cards.add(new SetCardInfo("Elsewhere Flask", 15, Rarity.UNCOMMON, mage.cards.e.ElsewhereFlask.class));
        cards.add(new SetCardInfo("Foundry Inspector", 16, Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class));
        cards.add(new SetCardInfo("Gilded Lotus", 17, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", 18, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Helm of the Host", 19, Rarity.MYTHIC, mage.cards.h.HelmOfTheHost.class));
        cards.add(new SetCardInfo("Howling Mine", 20, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 21, Rarity.UNCOMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Inspiring Statuary", 22, Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Ivory Tower", 23, Rarity.UNCOMMON, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jalum Tome", 24, Rarity.UNCOMMON, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Journeyer's Kite", 25, Rarity.RARE, mage.cards.j.JourneyersKite.class));
        cards.add(new SetCardInfo("Keening Stone", 26, Rarity.RARE, mage.cards.k.KeeningStone.class));
        cards.add(new SetCardInfo("Key to the City", 27, Rarity.RARE, mage.cards.k.KeyToTheCity.class));
        cards.add(new SetCardInfo("Liquimetal Coating", 28, Rarity.UNCOMMON, mage.cards.l.LiquimetalCoating.class));
        cards.add(new SetCardInfo("Lodestone Golem", 29, Rarity.RARE, mage.cards.l.LodestoneGolem.class));
        cards.add(new SetCardInfo("Mazemind Tome", 30, Rarity.RARE, mage.cards.m.MazemindTome.class));
        cards.add(new SetCardInfo("Mesmeric Orb", 31, Rarity.MYTHIC, mage.cards.m.MesmericOrb.class));
        cards.add(new SetCardInfo("Millstone", 32, Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mind's Eye", 33, Rarity.MYTHIC, mage.cards.m.MindsEye.class));
        cards.add(new SetCardInfo("Mishra's Bauble", 34, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class));
        cards.add(new SetCardInfo("Mox Amber", 35, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Mystic Forge", 36, Rarity.MYTHIC, mage.cards.m.MysticForge.class));
        cards.add(new SetCardInfo("Ornithopter", 37, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Perilous Vault", 38, Rarity.MYTHIC, mage.cards.p.PerilousVault.class));
        cards.add(new SetCardInfo("Phyrexian Processor", 39, Rarity.MYTHIC, mage.cards.p.PhyrexianProcessor.class));
        cards.add(new SetCardInfo("Phyrexian Revoker", 40, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class));
        cards.add(new SetCardInfo("Platinum Angel", 41, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Precursor Golem", 42, Rarity.RARE, mage.cards.p.PrecursorGolem.class));
        cards.add(new SetCardInfo("Pristine Talisman", 43, Rarity.UNCOMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 44, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Quicksilver Amulet", 45, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class));
        cards.add(new SetCardInfo("Quietus Spike", 46, Rarity.RARE, mage.cards.q.QuietusSpike.class));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 47, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class));
        cards.add(new SetCardInfo("Runechanter's Pike", 48, Rarity.RARE, mage.cards.r.RunechantersPike.class));
        cards.add(new SetCardInfo("Scrap Trawler", 49, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Sculpting Steel", 50, Rarity.RARE, mage.cards.s.SculptingSteel.class));
        cards.add(new SetCardInfo("Self-Assembler", 51, Rarity.UNCOMMON, mage.cards.s.SelfAssembler.class));
        cards.add(new SetCardInfo("Semblance Anvil", 52, Rarity.RARE, mage.cards.s.SemblanceAnvil.class));
        cards.add(new SetCardInfo("Sigil of Valor", 53, Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 54, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Springleaf Drum", 55, Rarity.UNCOMMON, mage.cards.s.SpringleafDrum.class));
        cards.add(new SetCardInfo("Staff of Domination", 56, Rarity.MYTHIC, mage.cards.s.StaffOfDomination.class));
        cards.add(new SetCardInfo("Sundering Titan", 57, Rarity.MYTHIC, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 58, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Sword of the Meek", 59, Rarity.RARE, mage.cards.s.SwordOfTheMeek.class));
        cards.add(new SetCardInfo("Thorn of Amethyst", 60, Rarity.RARE, mage.cards.t.ThornOfAmethyst.class));
        cards.add(new SetCardInfo("Unwinding Clock", 61, Rarity.RARE, mage.cards.u.UnwindingClock.class));
        cards.add(new SetCardInfo("Well of Lost Dreams", 62, Rarity.RARE, mage.cards.w.WellOfLostDreams.class));
        cards.add(new SetCardInfo("Wurmcoil Engine", 63, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class));
    }
}
