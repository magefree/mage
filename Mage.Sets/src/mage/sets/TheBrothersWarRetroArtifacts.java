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

        cards.add(new SetCardInfo("Adaptive Automaton", 1, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Adaptive Automaton", 64, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aetherflux Reservoir", 2, Rarity.MYTHIC, mage.cards.a.AetherfluxReservoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aetherflux Reservoir", 65, Rarity.MYTHIC, mage.cards.a.AetherfluxReservoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Altar of Dementia", 3, Rarity.MYTHIC, mage.cards.a.AltarOfDementia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Altar of Dementia", 66, Rarity.MYTHIC, mage.cards.a.AltarOfDementia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashnod's Altar", 4, Rarity.RARE, mage.cards.a.AshnodsAltar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashnod's Altar", 67, Rarity.RARE, mage.cards.a.AshnodsAltar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Astral Cornucopia", 5, Rarity.RARE, mage.cards.a.AstralCornucopia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Astral Cornucopia", 68, Rarity.RARE, mage.cards.a.AstralCornucopia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blackblade Reforged", 6, Rarity.RARE, mage.cards.b.BlackbladeReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blackblade Reforged", 69, Rarity.RARE, mage.cards.b.BlackbladeReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Saw", 7, Rarity.UNCOMMON, mage.cards.b.BoneSaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Saw", 70, Rarity.UNCOMMON, mage.cards.b.BoneSaw.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burnished Hart", 71, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burnished Hart", 8, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Caged Sun", 72, Rarity.MYTHIC, mage.cards.c.CagedSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Caged Sun", 9, Rarity.MYTHIC, mage.cards.c.CagedSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Lantern", 10, Rarity.RARE, mage.cards.c.ChromaticLantern.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Lantern", 73, Rarity.RARE, mage.cards.c.ChromaticLantern.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Star", 11, Rarity.UNCOMMON, mage.cards.c.ChromaticStar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Star", 74, Rarity.UNCOMMON, mage.cards.c.ChromaticStar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud Key", 12, Rarity.RARE, mage.cards.c.CloudKey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud Key", 75, Rarity.RARE, mage.cards.c.CloudKey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defense Grid", 13, Rarity.RARE, mage.cards.d.DefenseGrid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defense Grid", 76, Rarity.RARE, mage.cards.d.DefenseGrid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Door to Nothingness", 14, Rarity.RARE, mage.cards.d.DoorToNothingness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Door to Nothingness", 77, Rarity.RARE, mage.cards.d.DoorToNothingness.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elsewhere Flask", 15, Rarity.UNCOMMON, mage.cards.e.ElsewhereFlask.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elsewhere Flask", 78, Rarity.UNCOMMON, mage.cards.e.ElsewhereFlask.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foundry Inspector", 16, Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foundry Inspector", 79, Rarity.UNCOMMON, mage.cards.f.FoundryInspector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gilded Lotus", 17, Rarity.RARE, mage.cards.g.GildedLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gilded Lotus", 80, Rarity.RARE, mage.cards.g.GildedLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Charbelcher", 18, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Charbelcher", 81, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of the Host", 19, Rarity.MYTHIC, mage.cards.h.HelmOfTheHost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of the Host", 82, Rarity.MYTHIC, mage.cards.h.HelmOfTheHost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howling Mine", 20, Rarity.RARE, mage.cards.h.HowlingMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Howling Mine", 83, Rarity.RARE, mage.cards.h.HowlingMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ichor Wellspring", 21, Rarity.UNCOMMON, mage.cards.i.IchorWellspring.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ichor Wellspring", 84, Rarity.UNCOMMON, mage.cards.i.IchorWellspring.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Statuary", 22, Rarity.RARE, mage.cards.i.InspiringStatuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Statuary", 85, Rarity.RARE, mage.cards.i.InspiringStatuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivory Tower", 23, Rarity.UNCOMMON, mage.cards.i.IvoryTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivory Tower", 86, Rarity.UNCOMMON, mage.cards.i.IvoryTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jalum Tome", 24, Rarity.UNCOMMON, mage.cards.j.JalumTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jalum Tome", 87, Rarity.UNCOMMON, mage.cards.j.JalumTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Journeyer's Kite", 25, Rarity.RARE, mage.cards.j.JourneyersKite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Journeyer's Kite", 88, Rarity.RARE, mage.cards.j.JourneyersKite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keening Stone", 26, Rarity.RARE, mage.cards.k.KeeningStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keening Stone", 89, Rarity.RARE, mage.cards.k.KeeningStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Key to the City", 27, Rarity.RARE, mage.cards.k.KeyToTheCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Key to the City", 90, Rarity.RARE, mage.cards.k.KeyToTheCity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liquimetal Coating", 28, Rarity.UNCOMMON, mage.cards.l.LiquimetalCoating.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liquimetal Coating", 91, Rarity.UNCOMMON, mage.cards.l.LiquimetalCoating.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lodestone Golem", 29, Rarity.RARE, mage.cards.l.LodestoneGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lodestone Golem", 92, Rarity.RARE, mage.cards.l.LodestoneGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mazemind Tome", 30, Rarity.RARE, mage.cards.m.MazemindTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mazemind Tome", 93, Rarity.RARE, mage.cards.m.MazemindTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesmeric Orb", 31, Rarity.MYTHIC, mage.cards.m.MesmericOrb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesmeric Orb", 94, Rarity.MYTHIC, mage.cards.m.MesmericOrb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Millstone", 32, Rarity.UNCOMMON, mage.cards.m.Millstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Millstone", 95, Rarity.UNCOMMON, mage.cards.m.Millstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind's Eye", 33, Rarity.MYTHIC, mage.cards.m.MindsEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind's Eye", 96, Rarity.MYTHIC, mage.cards.m.MindsEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Bauble", 34, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Bauble", 97, Rarity.UNCOMMON, mage.cards.m.MishrasBauble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Amber", 35, Rarity.MYTHIC, mage.cards.m.MoxAmber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Amber", 98, Rarity.MYTHIC, mage.cards.m.MoxAmber.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Forge", 36, Rarity.MYTHIC, mage.cards.m.MysticForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Forge", 99, Rarity.MYTHIC, mage.cards.m.MysticForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornithopter", 100, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ornithopter", 37, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perilous Vault", 101, Rarity.MYTHIC, mage.cards.p.PerilousVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perilous Vault", 38, Rarity.MYTHIC, mage.cards.p.PerilousVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Processor", 102, Rarity.MYTHIC, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Processor", 39, Rarity.MYTHIC, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Revoker", 103, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Revoker", 40, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Platinum Angel", 104, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Platinum Angel", 41, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Precursor Golem", 105, Rarity.RARE, mage.cards.p.PrecursorGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Precursor Golem", 42, Rarity.RARE, mage.cards.p.PrecursorGolem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pristine Talisman", 106, Rarity.UNCOMMON, mage.cards.p.PristineTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pristine Talisman", 43, Rarity.UNCOMMON, mage.cards.p.PristineTalisman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Psychosis Crawler", 107, Rarity.RARE, mage.cards.p.PsychosisCrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Psychosis Crawler", 44, Rarity.RARE, mage.cards.p.PsychosisCrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver Amulet", 108, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver Amulet", 45, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quietus Spike", 109, Rarity.RARE, mage.cards.q.QuietusSpike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quietus Spike", 46, Rarity.RARE, mage.cards.q.QuietusSpike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 110, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ramos, Dragon Engine", 47, Rarity.MYTHIC, mage.cards.r.RamosDragonEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runechanter's Pike", 111, Rarity.RARE, mage.cards.r.RunechantersPike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runechanter's Pike", 48, Rarity.RARE, mage.cards.r.RunechantersPike.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrap Trawler", 112, Rarity.RARE, mage.cards.s.ScrapTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrap Trawler", 49, Rarity.RARE, mage.cards.s.ScrapTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sculpting Steel", 113, Rarity.RARE, mage.cards.s.SculptingSteel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sculpting Steel", 50, Rarity.RARE, mage.cards.s.SculptingSteel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Self-Assembler", 114, Rarity.UNCOMMON, mage.cards.s.SelfAssembler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Self-Assembler", 51, Rarity.UNCOMMON, mage.cards.s.SelfAssembler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Semblance Anvil", 115, Rarity.RARE, mage.cards.s.SemblanceAnvil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Semblance Anvil", 52, Rarity.RARE, mage.cards.s.SemblanceAnvil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigil of Valor", 116, Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sigil of Valor", 53, Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 117, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 54, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Springleaf Drum", 118, Rarity.UNCOMMON, mage.cards.s.SpringleafDrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Springleaf Drum", 55, Rarity.UNCOMMON, mage.cards.s.SpringleafDrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Staff of Domination", 119, Rarity.MYTHIC, mage.cards.s.StaffOfDomination.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Staff of Domination", 56, Rarity.MYTHIC, mage.cards.s.StaffOfDomination.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundering Titan", 120, Rarity.MYTHIC, mage.cards.s.SunderingTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sundering Titan", 57, Rarity.MYTHIC, mage.cards.s.SunderingTitan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftfoot Boots", 121, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swiftfoot Boots", 58, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of the Meek", 122, Rarity.RARE, mage.cards.s.SwordOfTheMeek.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of the Meek", 59, Rarity.RARE, mage.cards.s.SwordOfTheMeek.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorn of Amethyst", 123, Rarity.RARE, mage.cards.t.ThornOfAmethyst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorn of Amethyst", 60, Rarity.RARE, mage.cards.t.ThornOfAmethyst.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unwinding Clock", 124, Rarity.RARE, mage.cards.u.UnwindingClock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unwinding Clock", 61, Rarity.RARE, mage.cards.u.UnwindingClock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Well of Lost Dreams", 125, Rarity.RARE, mage.cards.w.WellOfLostDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Well of Lost Dreams", 62, Rarity.RARE, mage.cards.w.WellOfLostDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wurmcoil Engine", 126, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wurmcoil Engine", 63, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class, NON_FULL_USE_VARIOUS));
    }
}
