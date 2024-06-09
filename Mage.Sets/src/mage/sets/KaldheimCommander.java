package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class KaldheimCommander extends ExpansionSet {

    private static final KaldheimCommander instance = new KaldheimCommander();

    public static KaldheimCommander getInstance() {
        return instance;
    }

    private KaldheimCommander() {
        super("Kaldheim Commander", "KHC", ExpansionSet.buildDate(2021, 2, 5), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abomination of Llanowar", 81, Rarity.UNCOMMON, mage.cards.a.AbominationOfLlanowar.class));
        cards.add(new SetCardInfo("Ambition's Cost", 47, Rarity.UNCOMMON, mage.cards.a.AmbitionsCost.class));
        cards.add(new SetCardInfo("Angel of Finality", 17, Rarity.RARE, mage.cards.a.AngelOfFinality.class));
        cards.add(new SetCardInfo("Angel of Serenity", 18, Rarity.MYTHIC, mage.cards.a.AngelOfSerenity.class));
        cards.add(new SetCardInfo("Arcane Artisan", 36, Rarity.MYTHIC, mage.cards.a.ArcaneArtisan.class));
        cards.add(new SetCardInfo("Arcane Signet", 96, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Azorius Chancery", 106, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 107, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Azorius Signet", 97, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Banishing Light", 19, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Beast Whisperer", 54, Rarity.RARE, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Bounty of Skemfar", 12, Rarity.RARE, mage.cards.b.BountyOfSkemfar.class));
        cards.add(new SetCardInfo("Brago, King Eternal", 82, Rarity.RARE, mage.cards.b.BragoKingEternal.class));
        cards.add(new SetCardInfo("Burnished Hart", 98, Rarity.UNCOMMON, mage.cards.b.BurnishedHart.class));
        cards.add(new SetCardInfo("Casualties of War", 83, Rarity.RARE, mage.cards.c.CasualtiesOfWar.class));
        cards.add(new SetCardInfo("Cleansing Nova", 20, Rarity.RARE, mage.cards.c.CleansingNova.class));
        cards.add(new SetCardInfo("Cloudblazer", 84, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Cloudgoat Ranger", 21, Rarity.UNCOMMON, mage.cards.c.CloudgoatRanger.class));
        cards.add(new SetCardInfo("Command Tower", 108, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 99, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Cosmic Intervention", 3, Rarity.RARE, mage.cards.c.CosmicIntervention.class));
        cards.add(new SetCardInfo("Crown of Skemfar", 13, Rarity.RARE, mage.cards.c.CrownOfSkemfar.class));
        cards.add(new SetCardInfo("Cryptic Caves", 109, Rarity.UNCOMMON, mage.cards.c.CrypticCaves.class));
        cards.add(new SetCardInfo("Cultivator of Blades", 55, Rarity.RARE, mage.cards.c.CultivatorOfBlades.class));
        cards.add(new SetCardInfo("Curse of the Swine", 37, Rarity.RARE, mage.cards.c.CurseOfTheSwine.class));
        cards.add(new SetCardInfo("Day of the Dragons", 38, Rarity.RARE, mage.cards.d.DayOfTheDragons.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 56, Rarity.RARE, mage.cards.d.DwynenGiltLeafDaen.class));
        cards.add(new SetCardInfo("Eerie Interlude", 22, Rarity.RARE, mage.cards.e.EerieInterlude.class));
        cards.add(new SetCardInfo("Elderfang Venom", 15, Rarity.RARE, mage.cards.e.ElderfangVenom.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 57, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Mystic", 58, Rarity.COMMON, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Elvish Promenade", 59, Rarity.UNCOMMON, mage.cards.e.ElvishPromenade.class));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 60, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 85, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("End-Raze Forerunners", 61, Rarity.RARE, mage.cards.e.EndRazeForerunners.class));
        cards.add(new SetCardInfo("Ethereal Valkyrie", 16, Rarity.RARE, mage.cards.e.EtherealValkyrie.class));
        cards.add(new SetCardInfo("Evangel of Heliod", 23, Rarity.UNCOMMON, mage.cards.e.EvangelOfHeliod.class));
        cards.add(new SetCardInfo("Eyeblight Cullers", 48, Rarity.COMMON, mage.cards.e.EyeblightCullers.class));
        cards.add(new SetCardInfo("Eyeblight Massacre", 49, Rarity.UNCOMMON, mage.cards.e.EyeblightMassacre.class));
        cards.add(new SetCardInfo("Farhaven Elf", 62, Rarity.COMMON, mage.cards.f.FarhavenElf.class));
        cards.add(new SetCardInfo("Flickerwisp", 24, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Foul Orchard", 110, Rarity.UNCOMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Geist-Honored Monk", 25, Rarity.RARE, mage.cards.g.GeistHonoredMonk.class));
        cards.add(new SetCardInfo("Ghostly Flicker", 39, Rarity.COMMON, mage.cards.g.GhostlyFlicker.class));
        cards.add(new SetCardInfo("Ghostly Prison", 26, Rarity.UNCOMMON, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Goldnight Commander", 27, Rarity.UNCOMMON, mage.cards.g.GoldnightCommander.class));
        cards.add(new SetCardInfo("Golgari Findbroker", 86, Rarity.UNCOMMON, mage.cards.g.GolgariFindbroker.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 111, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 112, Rarity.UNCOMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Harvest Season", 63, Rarity.RARE, mage.cards.h.HarvestSeason.class));
        cards.add(new SetCardInfo("Hero of Bretagard", 4, Rarity.RARE, mage.cards.h.HeroOfBretagard.class));
        cards.add(new SetCardInfo("Imperious Perfect", 64, Rarity.RARE, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Inspired Sphinx", 40, Rarity.MYTHIC, mage.cards.i.InspiredSphinx.class));
        cards.add(new SetCardInfo("Jagged-Scar Archers", 65, Rarity.UNCOMMON, mage.cards.j.JaggedScarArchers.class));
        cards.add(new SetCardInfo("Jungle Hollow", 113, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kor Cartographer", 28, Rarity.COMMON, mage.cards.k.KorCartographer.class));
        cards.add(new SetCardInfo("Lathril, Blade of the Elves", 1, Rarity.MYTHIC, mage.cards.l.LathrilBladeOfTheElves.class));
        cards.add(new SetCardInfo("Llanowar Tribe", 66, Rarity.UNCOMMON, mage.cards.l.LlanowarTribe.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 67, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Lys Alana Scarblade", 50, Rarity.UNCOMMON, mage.cards.l.LysAlanaScarblade.class));
        cards.add(new SetCardInfo("Marble Diamond", 100, Rarity.COMMON, mage.cards.m.MarbleDiamond.class));
        cards.add(new SetCardInfo("Marshal's Anthem", 29, Rarity.RARE, mage.cards.m.MarshalsAnthem.class));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", 68, Rarity.RARE, mage.cards.m.MarwynTheNurturer.class));
        cards.add(new SetCardInfo("Masked Admirers", 69, Rarity.RARE, mage.cards.m.MaskedAdmirers.class));
        cards.add(new SetCardInfo("Meandering River", 114, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Meteor Golem", 101, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Miara, Thorn of the Glade", 51, Rarity.UNCOMMON, mage.cards.m.MiaraThornOfTheGlade.class));
        cards.add(new SetCardInfo("Migratory Route", 87, Rarity.UNCOMMON, mage.cards.m.MigratoryRoute.class));
        cards.add(new SetCardInfo("Mind Stone", 102, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mist Raven", 41, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 88, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Moldervine Reclamation", 89, Rarity.UNCOMMON, mage.cards.m.MoldervineReclamation.class));
        cards.add(new SetCardInfo("Momentary Blink", 30, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Mulldrifter", 42, Rarity.UNCOMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Myriad Landscape", 115, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Nullmage Shepherd", 70, Rarity.UNCOMMON, mage.cards.n.NullmageShepherd.class));
        cards.add(new SetCardInfo("Numa, Joraga Chieftain", 71, Rarity.UNCOMMON, mage.cards.n.NumaJoragaChieftain.class));
        cards.add(new SetCardInfo("Opal Palace", 116, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Pact of the Serpent", 9, Rarity.RARE, mage.cards.p.PactOfTheSerpent.class));
        cards.add(new SetCardInfo("Path of Ancestry", 117, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Poison-Tip Archer", 90, Rarity.UNCOMMON, mage.cards.p.PoisonTipArcher.class));
        cards.add(new SetCardInfo("Pride of the Perfect", 52, Rarity.UNCOMMON, mage.cards.p.PrideOfThePerfect.class));
        cards.add(new SetCardInfo("Prowess of the Fair", 53, Rarity.UNCOMMON, mage.cards.p.ProwessOfTheFair.class));
        cards.add(new SetCardInfo("Putrefy", 91, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Ranar the Ever-Watchful", 2, Rarity.MYTHIC, mage.cards.r.RanarTheEverWatchful.class));
        cards.add(new SetCardInfo("Reclamation Sage", 72, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Restoration Angel", 31, Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Return to Dust", 32, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Rhys the Exiled", 73, Rarity.RARE, mage.cards.r.RhysTheExiled.class));
        cards.add(new SetCardInfo("Ruthless Winnower", 10, Rarity.RARE, mage.cards.r.RuthlessWinnower.class));
        cards.add(new SetCardInfo("Sage of the Beyond", 6, Rarity.RARE, mage.cards.s.SageOfTheBeyond.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 43, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Sejiri Refuge", 118, Rarity.UNCOMMON, mage.cards.s.SejiriRefuge.class));
        cards.add(new SetCardInfo("Serpent's Soul-Jar", 11, Rarity.RARE, mage.cards.s.SerpentsSoulJar.class));
        cards.add(new SetCardInfo("Shaman of the Pack", 92, Rarity.UNCOMMON, mage.cards.s.ShamanOfThePack.class));
        cards.add(new SetCardInfo("Sky Diamond", 103, Rarity.COMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Sol Ring", 104, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Soulherder", 93, Rarity.UNCOMMON, mage.cards.s.Soulherder.class));
        cards.add(new SetCardInfo("Spectral Deluge", 7, Rarity.RARE, mage.cards.s.SpectralDeluge.class));
        cards.add(new SetCardInfo("Springbloom Druid", 74, Rarity.COMMON, mage.cards.s.SpringbloomDruid.class));
        cards.add(new SetCardInfo("Stoic Farmer", 5, Rarity.RARE, mage.cards.s.StoicFarmer.class));
        cards.add(new SetCardInfo("Storm Herd", 33, Rarity.RARE, mage.cards.s.StormHerd.class));
        cards.add(new SetCardInfo("Sun Titan", 34, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 105, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Sylvan Messenger", 75, Rarity.UNCOMMON, mage.cards.s.SylvanMessenger.class));
        cards.add(new SetCardInfo("Synthetic Destiny", 44, Rarity.RARE, mage.cards.s.SyntheticDestiny.class));
        cards.add(new SetCardInfo("Tales of the Ancestors", 8, Rarity.RARE, mage.cards.t.TalesOfTheAncestors.class));
        cards.add(new SetCardInfo("Thunderclap Wyvern", 94, Rarity.UNCOMMON, mage.cards.t.ThunderclapWyvern.class));
        cards.add(new SetCardInfo("Timberwatch Elf", 76, Rarity.COMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Tranquil Cove", 119, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Twinblade Assassins", 95, Rarity.UNCOMMON, mage.cards.t.TwinbladeAssassins.class));
        cards.add(new SetCardInfo("Voice of Many", 77, Rarity.UNCOMMON, mage.cards.v.VoiceOfMany.class));
        cards.add(new SetCardInfo("Voice of the Woods", 78, Rarity.RARE, mage.cards.v.VoiceOfTheWoods.class));
        cards.add(new SetCardInfo("Wall of Omens", 35, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Whirler Rogue", 45, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Windfall", 46, Rarity.UNCOMMON, mage.cards.w.Windfall.class));
        cards.add(new SetCardInfo("Wirewood Channeler", 79, Rarity.UNCOMMON, mage.cards.w.WirewoodChanneler.class));
        cards.add(new SetCardInfo("Wolverine Riders", 14, Rarity.RARE, mage.cards.w.WolverineRiders.class));
        cards.add(new SetCardInfo("Wood Elves", 80, Rarity.COMMON, mage.cards.w.WoodElves.class));
    }
}
