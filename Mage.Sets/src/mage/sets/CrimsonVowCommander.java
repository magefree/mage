package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CrimsonVowCommander extends ExpansionSet {

    private static final CrimsonVowCommander instance = new CrimsonVowCommander();

    public static CrimsonVowCommander getInstance() {
        return instance;
    }

    private CrimsonVowCommander() {
        super("Crimson Vow Commander", "VOC", ExpansionSet.buildDate(2021, 11, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Craving", 117, Rarity.UNCOMMON, mage.cards.a.AncientCraving.class));
        cards.add(new SetCardInfo("Angel of Flight Alabaster", 77, Rarity.RARE, mage.cards.a.AngelOfFlightAlabaster.class));
        cards.add(new SetCardInfo("Anje's Ravager", 141, Rarity.RARE, mage.cards.a.AnjesRavager.class));
        cards.add(new SetCardInfo("Anowon, the Ruin Sage", 118, Rarity.RARE, mage.cards.a.AnowonTheRuinSage.class));
        cards.add(new SetCardInfo("Arcane Denial", 102, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Arcane Signet", 159, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Avacyn's Judgment", 142, Rarity.RARE, mage.cards.a.AvacynsJudgment.class));
        cards.add(new SetCardInfo("Azorius Chancery", 171, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Azorius Locket", 160, Rarity.COMMON, mage.cards.a.AzoriusLocket.class));
        cards.add(new SetCardInfo("Azorius Signet", 161, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Benevolent Offering", 78, Rarity.RARE, mage.cards.b.BenevolentOffering.class));
        cards.add(new SetCardInfo("Blasphemous Act", 143, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blood Artist", 119, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Bloodline Necromancer", 120, Rarity.UNCOMMON, mage.cards.b.BloodlineNecromancer.class));
        cards.add(new SetCardInfo("Bloodlord of Vaasgoth", 121, Rarity.MYTHIC, mage.cards.b.BloodlordOfVaasgoth.class));
        cards.add(new SetCardInfo("Bloodsworn Steward", 144, Rarity.RARE, mage.cards.b.BloodswornSteward.class));
        cards.add(new SetCardInfo("Bloodtracker", 122, Rarity.RARE, mage.cards.b.Bloodtracker.class));
        cards.add(new SetCardInfo("Boreas Charger", 79, Rarity.RARE, mage.cards.b.BoreasCharger.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 123, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Bygone Bishop", 80, Rarity.RARE, mage.cards.b.BygoneBishop.class));
        cards.add(new SetCardInfo("Champion of Dusk", 124, Rarity.RARE, mage.cards.c.ChampionOfDusk.class));
        cards.add(new SetCardInfo("Charcoal Diamond", 162, Rarity.COMMON, mage.cards.c.CharcoalDiamond.class));
        cards.add(new SetCardInfo("Command Tower", 172, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 163, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Cordial Vampire", 125, Rarity.RARE, mage.cards.c.CordialVampire.class));
        cards.add(new SetCardInfo("Crimson Honor Guard", 145, Rarity.RARE, mage.cards.c.CrimsonHonorGuard.class));
        cards.add(new SetCardInfo("Crossway Troublemakers", 17, Rarity.RARE, mage.cards.c.CrosswayTroublemakers.class));
        cards.add(new SetCardInfo("Crush Contraband", 81, Rarity.UNCOMMON, mage.cards.c.CrushContraband.class));
        cards.add(new SetCardInfo("Custodi Soulbinders", 82, Rarity.RARE, mage.cards.c.CustodiSoulbinders.class));
        cards.add(new SetCardInfo("Custodi Squire", 83, Rarity.COMMON, mage.cards.c.CustodiSquire.class));
        cards.add(new SetCardInfo("Damnable Pact", 126, Rarity.RARE, mage.cards.d.DamnablePact.class));
        cards.add(new SetCardInfo("Dark Impostor", 127, Rarity.RARE, mage.cards.d.DarkImpostor.class));
        cards.add(new SetCardInfo("Darksteel Mutation", 84, Rarity.UNCOMMON, mage.cards.d.DarksteelMutation.class));
        cards.add(new SetCardInfo("Distant Melody", 103, Rarity.COMMON, mage.cards.d.DistantMelody.class));
        cards.add(new SetCardInfo("Dovin, Grand Arbiter", 153, Rarity.MYTHIC, mage.cards.d.DovinGrandArbiter.class));
        cards.add(new SetCardInfo("Drogskol Captain", 154, Rarity.UNCOMMON, mage.cards.d.DrogskolCaptain.class));
        cards.add(new SetCardInfo("Ethereal Investigator", 12, Rarity.RARE, mage.cards.e.EtherealInvestigator.class));
        cards.add(new SetCardInfo("Exotic Orchard", 173, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Falkenrath Gorger", 146, Rarity.RARE, mage.cards.f.FalkenrathGorger.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 128, Rarity.UNCOMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Feed the Swarm", 129, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fell the Mighty", 85, Rarity.RARE, mage.cards.f.FellTheMighty.class));
        cards.add(new SetCardInfo("Field of Souls", 86, Rarity.UNCOMMON, mage.cards.f.FieldOfSouls.class));
        cards.add(new SetCardInfo("Fire Diamond", 164, Rarity.COMMON, mage.cards.f.FireDiamond.class));
        cards.add(new SetCardInfo("Flood of Tears", 104, Rarity.RARE, mage.cards.f.FloodOfTears.class));
        cards.add(new SetCardInfo("Foreboding Ruins", 174, Rarity.RARE, mage.cards.f.ForebodingRuins.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 155, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Ghostly Pilferer", 105, Rarity.RARE, mage.cards.g.GhostlyPilferer.class));
        cards.add(new SetCardInfo("Ghostly Prison", 87, Rarity.UNCOMMON, mage.cards.g.GhostlyPrison.class));
        cards.add(new SetCardInfo("Hallowed Spiritkeeper", 88, Rarity.RARE, mage.cards.h.HallowedSpiritkeeper.class));
        cards.add(new SetCardInfo("Hanged Executioner", 89, Rarity.RARE, mage.cards.h.HangedExecutioner.class));
        cards.add(new SetCardInfo("Haunted Library", 6, Rarity.RARE, mage.cards.h.HauntedLibrary.class));
        cards.add(new SetCardInfo("Hollowhenge Overlord", 36, Rarity.RARE, mage.cards.h.HollowhengeOverlord.class));
        cards.add(new SetCardInfo("Imprisoned in the Moon", 106, Rarity.RARE, mage.cards.i.ImprisonedInTheMoon.class));
        cards.add(new SetCardInfo("Indulgent Aristocrat", 130, Rarity.UNCOMMON, mage.cards.i.IndulgentAristocrat.class));
        cards.add(new SetCardInfo("Kamber, the Plunderer", 19, Rarity.RARE, mage.cards.k.KamberThePlunderer.class));
        cards.add(new SetCardInfo("Kami of the Crescent Moon", 107, Rarity.RARE, mage.cards.k.KamiOfTheCrescentMoon.class));
        cards.add(new SetCardInfo("Karmic Guide", 90, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Kirtar's Wrath", 91, Rarity.RARE, mage.cards.k.KirtarsWrath.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", 92, Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Malakir Bloodwitch", 131, Rarity.RARE, mage.cards.m.MalakirBloodwitch.class));
        cards.add(new SetCardInfo("Marble Diamond", 165, Rarity.COMMON, mage.cards.m.MarbleDiamond.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 93, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Midnight Clock", 108, Rarity.RARE, mage.cards.m.MidnightClock.class));
        cards.add(new SetCardInfo("Mirror Entity", 94, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Mob Rule", 147, Rarity.RARE, mage.cards.m.MobRule.class));
        cards.add(new SetCardInfo("Molten Echoes", 148, Rarity.RARE, mage.cards.m.MoltenEchoes.class));
        cards.add(new SetCardInfo("Moorland Haunt", 175, Rarity.RARE, mage.cards.m.MoorlandHaunt.class));
        cards.add(new SetCardInfo("Myriad Landscape", 176, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Nebelgast Herald", 109, Rarity.UNCOMMON, mage.cards.n.NebelgastHerald.class));
        cards.add(new SetCardInfo("Necropolis Regent", 132, Rarity.MYTHIC, mage.cards.n.NecropolisRegent.class));
        cards.add(new SetCardInfo("Night's Whisper", 133, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Nirkana Revenant", 134, Rarity.MYTHIC, mage.cards.n.NirkanaRevenant.class));
        cards.add(new SetCardInfo("Occult Epiphany", 14, Rarity.RARE, mage.cards.o.OccultEpiphany.class));
        cards.add(new SetCardInfo("Oyobi, Who Split the Heavens", 95, Rarity.RARE, mage.cards.o.OyobiWhoSplitTheHeavens.class));
        cards.add(new SetCardInfo("Path of Ancestry", 177, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Patron of the Vein", 135, Rarity.RARE, mage.cards.p.PatronOfTheVein.class));
        cards.add(new SetCardInfo("Port Town", 178, Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prairie Stream", 179, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Promise of Bunrei", 96, Rarity.RARE, mage.cards.p.PromiseOfBunrei.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 180, Rarity.UNCOMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rakdos Charm", 156, Rarity.UNCOMMON, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Rakdos Signet", 166, Rarity.UNCOMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rakish Heir", 149, Rarity.UNCOMMON, mage.cards.r.RakishHeir.class));
        cards.add(new SetCardInfo("Rattlechains", 110, Rarity.RARE, mage.cards.r.Rattlechains.class));
        cards.add(new SetCardInfo("Reconnaissance Mission", 111, Rarity.UNCOMMON, mage.cards.r.ReconnaissanceMission.class));
        cards.add(new SetCardInfo("Remorseful Cleric", 97, Rarity.RARE, mage.cards.r.RemorsefulCleric.class));
        cards.add(new SetCardInfo("Sanctum Seeker", 136, Rarity.RARE, mage.cards.s.SanctumSeeker.class));
        cards.add(new SetCardInfo("Shacklegeist", 112, Rarity.RARE, mage.cards.s.Shacklegeist.class));
        cards.add(new SetCardInfo("Shadowblood Ridge", 181, Rarity.RARE, mage.cards.s.ShadowbloodRidge.class));
        cards.add(new SetCardInfo("Sire of the Storm", 113, Rarity.UNCOMMON, mage.cards.s.SireOfTheStorm.class));
        cards.add(new SetCardInfo("Sky Diamond", 167, Rarity.COMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Skycloud Expanse", 182, Rarity.RARE, mage.cards.s.SkycloudExpanse.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 183, Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Sol Ring", 168, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Spectral Sailor", 114, Rarity.UNCOMMON, mage.cards.s.SpectralSailor.class));
        cards.add(new SetCardInfo("Spectral Shepherd", 98, Rarity.UNCOMMON, mage.cards.s.SpectralShepherd.class));
        cards.add(new SetCardInfo("Stensia Masquerade", 150, Rarity.UNCOMMON, mage.cards.s.StensiaMasquerade.class));
        cards.add(new SetCardInfo("Stromkirk Captain", 157, Rarity.UNCOMMON, mage.cards.s.StromkirkCaptain.class));
        cards.add(new SetCardInfo("Stromkirk Condemned", 137, Rarity.RARE, mage.cards.s.StromkirkCondemned.class));
        cards.add(new SetCardInfo("Stromkirk Occultist", 151, Rarity.RARE, mage.cards.s.StromkirkOccultist.class));
        cards.add(new SetCardInfo("Supreme Phantom", 115, Rarity.RARE, mage.cards.s.SupremePhantom.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 169, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 99, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tainted Peak", 184, Rarity.UNCOMMON, mage.cards.t.TaintedPeak.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 185, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Malice", 186, Rarity.RARE, mage.cards.t.TempleOfMalice.class));
        cards.add(new SetCardInfo("Temple of the False God", 187, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Timin, Youthful Geist", 16, Rarity.RARE, mage.cards.t.TiminYouthfulGeist.class));
        cards.add(new SetCardInfo("Twilight Drover", 100, Rarity.RARE, mage.cards.t.TwilightDrover.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 188, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Underworld Connections", 138, Rarity.RARE, mage.cards.u.UnderworldConnections.class));
        cards.add(new SetCardInfo("Unstable Obelisk", 170, Rarity.UNCOMMON, mage.cards.u.UnstableObelisk.class));
        cards.add(new SetCardInfo("Urge to Feed", 139, Rarity.UNCOMMON, mage.cards.u.UrgeToFeed.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 140, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vampiric Dragon", 158, Rarity.RARE, mage.cards.v.VampiricDragon.class));
        cards.add(new SetCardInfo("Vandalblast", 152, Rarity.UNCOMMON, mage.cards.v.Vandalblast.class));
        cards.add(new SetCardInfo("Verity Circle", 116, Rarity.RARE, mage.cards.v.VerityCircle.class));
        cards.add(new SetCardInfo("Windborn Muse", 101, Rarity.RARE, mage.cards.w.WindbornMuse.class));
    }
}
