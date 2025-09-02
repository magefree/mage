package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderMan extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Eddie Brock", "Gwen Stacy", "Miles Morales", "Norman Osborn", "Peter Parker");
    private static final MarvelsSpiderMan instance = new MarvelsSpiderMan();

    public static MarvelsSpiderMan getInstance() {
        return instance;
    }

    private MarvelsSpiderMan() {
        super("Marvel's Spider-Man", "SPM", ExpansionSet.buildDate(2025, 9, 26), SetType.EXPANSION);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Agent Venom", 255, Rarity.RARE, mage.cards.a.AgentVenom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agent Venom", 49, Rarity.RARE, mage.cards.a.AgentVenom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angry Rabble", 75, Rarity.COMMON, mage.cards.a.AngryRabble.class));
        cards.add(new SetCardInfo("Anti-Venom, Horrifying Healer", 1, Rarity.MYTHIC, mage.cards.a.AntiVenomHorrifyingHealer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anti-Venom, Horrifying Healer", 244, Rarity.MYTHIC, mage.cards.a.AntiVenomHorrifyingHealer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aunt May", 3, Rarity.UNCOMMON, mage.cards.a.AuntMay.class));
        cards.add(new SetCardInfo("Beetle, Legacy Criminal", 26, Rarity.COMMON, mage.cards.b.BeetleLegacyCriminal.class));
        cards.add(new SetCardInfo("Behold the Sinister Six!", 221, Rarity.MYTHIC, mage.cards.b.BeholdTheSinisterSix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Behold the Sinister Six!", 51, Rarity.MYTHIC, mage.cards.b.BeholdTheSinisterSix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carnage, Crimson Chaos", 125, Rarity.RARE, mage.cards.c.CarnageCrimsonChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carnage, Crimson Chaos", 227, Rarity.RARE, mage.cards.c.CarnageCrimsonChaos.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City Pigeon", 4, Rarity.COMMON, mage.cards.c.CityPigeon.class));
        cards.add(new SetCardInfo("Costume Closet", 5, Rarity.UNCOMMON, mage.cards.c.CostumeCloset.class));
        cards.add(new SetCardInfo("Daily Bugle Building", 179, Rarity.UNCOMMON, mage.cards.d.DailyBugleBuilding.class));
        cards.add(new SetCardInfo("Daily Bugle Reporters", 6, Rarity.COMMON, mage.cards.d.DailyBugleReporters.class));
        cards.add(new SetCardInfo("Doc Ock's Henchmen", 30, Rarity.COMMON, mage.cards.d.DocOcksHenchmen.class));
        cards.add(new SetCardInfo("Doc Ock, Sinister Scientist", 29, Rarity.COMMON, mage.cards.d.DocOckSinisterScientist.class));
        cards.add(new SetCardInfo("Doctor Octopus, Master Planner", 128, Rarity.MYTHIC, mage.cards.d.DoctorOctopusMasterPlanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doctor Octopus, Master Planner", 228, Rarity.MYTHIC, mage.cards.d.DoctorOctopusMasterPlanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eddie Brock", 224, Rarity.MYTHIC, mage.cards.e.EddieBrock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eddie Brock", 233, Rarity.MYTHIC, mage.cards.e.EddieBrock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eddie Brock", 55, Rarity.MYTHIC, mage.cards.e.EddieBrock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eerie Gravestone", 163, Rarity.COMMON, mage.cards.e.EerieGravestone.class));
        cards.add(new SetCardInfo("Electro's Bolt", 77, Rarity.COMMON, mage.cards.e.ElectrosBolt.class));
        cards.add(new SetCardInfo("Electro, Assaulting Battery", 260, Rarity.RARE, mage.cards.e.ElectroAssaultingBattery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Electro, Assaulting Battery", 76, Rarity.RARE, mage.cards.e.ElectroAssaultingBattery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ezekiel Sims, Spider-Totem", 100, Rarity.UNCOMMON, mage.cards.e.EzekielSimsSpiderTotem.class));
        cards.add(new SetCardInfo("Flash Thompson, Spider-Fan", 7, Rarity.UNCOMMON, mage.cards.f.FlashThompsonSpiderFan.class));
        cards.add(new SetCardInfo("Flying Octobot", 31, Rarity.UNCOMMON, mage.cards.f.FlyingOctobot.class));
        cards.add(new SetCardInfo("Forest", 193, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Green Goblin, Revenant", 130, Rarity.UNCOMMON, mage.cards.g.GreenGoblinRevenant.class));
        cards.add(new SetCardInfo("Grow Extra Arms", 101, Rarity.COMMON, mage.cards.g.GrowExtraArms.class));
        cards.add(new SetCardInfo("Guy in the Chair", 102, Rarity.COMMON, mage.cards.g.GuyInTheChair.class));
        cards.add(new SetCardInfo("Gwen Stacy", 202, Rarity.MYTHIC, mage.cards.g.GwenStacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwen Stacy", 209, Rarity.MYTHIC, mage.cards.g.GwenStacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwen Stacy", 78, Rarity.MYTHIC, mage.cards.g.GwenStacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwenom, Remorseless", 256, Rarity.MYTHIC, mage.cards.g.GwenomRemorseless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwenom, Remorseless", 286, Rarity.MYTHIC, mage.cards.g.GwenomRemorseless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwenom, Remorseless", 56, Rarity.MYTHIC, mage.cards.g.GwenomRemorseless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Impostor Syndrome", 251, Rarity.MYTHIC, mage.cards.i.ImpostorSyndrome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Impostor Syndrome", 34, Rarity.MYTHIC, mage.cards.i.ImpostorSyndrome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Spider, Stark Upgrade", 166, Rarity.RARE, mage.cards.i.IronSpiderStarkUpgrade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Spider, Stark Upgrade", 279, Rarity.RARE, mage.cards.i.IronSpiderStarkUpgrade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 190, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 195, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("J. Jonah Jameson", 261, Rarity.RARE, mage.cards.j.JJonahJameson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("J. Jonah Jameson", 81, Rarity.RARE, mage.cards.j.JJonahJameson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kapow!", 103, Rarity.COMMON, mage.cards.k.Kapow.class));
        cards.add(new SetCardInfo("Kraven the Hunter", 133, Rarity.RARE, mage.cards.k.KravenTheHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraven the Hunter", 273, Rarity.RARE, mage.cards.k.KravenTheHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraven's Cats", 104, Rarity.COMMON, mage.cards.k.KravensCats.class));
        cards.add(new SetCardInfo("Kraven's Last Hunt", 105, Rarity.RARE, mage.cards.k.KravensLastHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kraven's Last Hunt", 226, Rarity.RARE, mage.cards.k.KravensLastHunt.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lurking Lizards", 107, Rarity.COMMON, mage.cards.l.LurkingLizards.class));
        cards.add(new SetCardInfo("Masked Meower", 82, Rarity.COMMON, mage.cards.m.MaskedMeower.class));
        cards.add(new SetCardInfo("Mechanical Mobster", 168, Rarity.COMMON, mage.cards.m.MechanicalMobster.class));
        cards.add(new SetCardInfo("Merciless Enforcers", 58, Rarity.COMMON, mage.cards.m.MercilessEnforcers.class));
        cards.add(new SetCardInfo("Miles Morales", 108, Rarity.MYTHIC, mage.cards.m.MilesMorales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Miles Morales", 200, Rarity.MYTHIC, mage.cards.m.MilesMorales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Miles Morales", 211, Rarity.MYTHIC, mage.cards.m.MilesMorales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Miles Morales", 234, Rarity.MYTHIC, mage.cards.m.MilesMorales.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 192, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiversal Passage", 180, Rarity.RARE, mage.cards.m.MultiversalPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multiversal Passage", 206, Rarity.RARE, mage.cards.m.MultiversalPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mysterio, Master of Illusion", 253, Rarity.RARE, mage.cards.m.MysterioMasterOfIllusion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mysterio, Master of Illusion", 37, Rarity.RARE, mage.cards.m.MysterioMasterOfIllusion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Norman Osborn", 220, Rarity.MYTHIC, mage.cards.n.NormanOsborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Norman Osborn", 39, Rarity.MYTHIC, mage.cards.n.NormanOsborn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ominous Asylum", 181, Rarity.COMMON, mage.cards.o.OminousAsylum.class));
        cards.add(new SetCardInfo("Origin of Spider-Man", 218, Rarity.RARE, mage.cards.o.OriginOfSpiderMan.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Origin of Spider-Man", 9, Rarity.RARE, mage.cards.o.OriginOfSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oscorp Research Team", 40, Rarity.COMMON, mage.cards.o.OscorpResearchTeam.class));
        cards.add(new SetCardInfo("Peter Parker", 10, Rarity.MYTHIC, mage.cards.p.PeterParker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peter Parker", 208, Rarity.MYTHIC, mage.cards.p.PeterParker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peter Parker", 232, Rarity.MYTHIC, mage.cards.p.PeterParker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peter Parker's Camera", 171, Rarity.RARE, mage.cards.p.PeterParkersCamera.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peter Parker's Camera", 280, Rarity.RARE, mage.cards.p.PeterParkersCamera.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pictures of Spider-Man", 109, Rarity.UNCOMMON, mage.cards.p.PicturesOfSpiderMan.class));
        cards.add(new SetCardInfo("Plains", 189, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 194, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prowler, Clawed Thief", 138, Rarity.UNCOMMON, mage.cards.p.ProwlerClawedThief.class));
        cards.add(new SetCardInfo("Radioactive Spider", 111, Rarity.RARE, mage.cards.r.RadioactiveSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radioactive Spider", 212, Rarity.RARE, mage.cards.r.RadioactiveSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radioactive Spider", 285, Rarity.RARE, mage.cards.r.RadioactiveSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Goblinoids", 85, Rarity.UNCOMMON, mage.cards.r.RagingGoblinoids.class));
        cards.add(new SetCardInfo("Rent Is Due", 11, Rarity.RARE, mage.cards.r.RentIsDue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rent Is Due", 247, Rarity.RARE, mage.cards.r.RentIsDue.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rhino, Barreling Brute", 140, Rarity.UNCOMMON, mage.cards.r.RhinoBarrelingBrute.class));
        cards.add(new SetCardInfo("Risky Research", 62, Rarity.COMMON, mage.cards.r.RiskyResearch.class));
        cards.add(new SetCardInfo("Romantic Rendezvous", 86, Rarity.COMMON, mage.cards.r.RomanticRendezvous.class));
        cards.add(new SetCardInfo("Sandman, Shifting Scoundrel", 112, Rarity.RARE, mage.cards.s.SandmanShiftingScoundrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandman, Shifting Scoundrel", 266, Rarity.RARE, mage.cards.s.SandmanShiftingScoundrel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scorpion's Sting", 65, Rarity.COMMON, mage.cards.s.ScorpionsSting.class));
        cards.add(new SetCardInfo("Scorpion, Seething Striker", 64, Rarity.UNCOMMON, mage.cards.s.ScorpionSeethingStriker.class));
        cards.add(new SetCardInfo("Scout the City", 113, Rarity.COMMON, mage.cards.s.ScoutTheCity.class));
        cards.add(new SetCardInfo("Selfless Police Captain", 12, Rarity.COMMON, mage.cards.s.SelflessPoliceCaptain.class));
        cards.add(new SetCardInfo("Shock", 88, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Shocker, Unshakable", 89, Rarity.UNCOMMON, mage.cards.s.ShockerUnshakable.class));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 14, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 235, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 236, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 237, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 238, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 239, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 240, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 241, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectacular Tactics", 15, Rarity.COMMON, mage.cards.s.SpectacularTactics.class));
        cards.add(new SetCardInfo("Spider-Bot", 173, Rarity.COMMON, mage.cards.s.SpiderBot.class));
        cards.add(new SetCardInfo("Spider-Byte, Web Warden", 44, Rarity.UNCOMMON, mage.cards.s.SpiderByteWebWarden.class));
        cards.add(new SetCardInfo("Spider-Gwen, Free Spirit", 90, Rarity.COMMON, mage.cards.s.SpiderGwenFreeSpirit.class));
        cards.add(new SetCardInfo("Spider-Ham, Peter Porker", 114, Rarity.RARE, mage.cards.s.SpiderHamPeterPorker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Ham, Peter Porker", 201, Rarity.RARE, mage.cards.s.SpiderHamPeterPorker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Islanders", 91, Rarity.COMMON, mage.cards.s.SpiderIslanders.class));
        cards.add(new SetCardInfo("Spider-Man 2099", 150, Rarity.RARE, mage.cards.s.SpiderMan2099.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Man 2099", 205, Rarity.RARE, mage.cards.s.SpiderMan2099.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Man 2099", 216, Rarity.RARE, mage.cards.s.SpiderMan2099.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Man India", 151, Rarity.UNCOMMON, mage.cards.s.SpiderManIndia.class));
        cards.add(new SetCardInfo("Spider-Man Noir", 204, Rarity.UNCOMMON, mage.cards.s.SpiderManNoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Man Noir", 67, Rarity.UNCOMMON, mage.cards.s.SpiderManNoir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Man, Brooklyn Visionary", 115, Rarity.COMMON, mage.cards.s.SpiderManBrooklynVisionary.class));
        cards.add(new SetCardInfo("Spider-Man, Web-Slinger", 16, Rarity.COMMON, mage.cards.s.SpiderManWebSlinger.class));
        cards.add(new SetCardInfo("Spider-Punk", 207, Rarity.RARE, mage.cards.s.SpiderPunk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Punk", 210, Rarity.RARE, mage.cards.s.SpiderPunk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Punk", 92, Rarity.RARE, mage.cards.s.SpiderPunk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spider-Rex, Daring Dino", 116, Rarity.COMMON, mage.cards.s.SpiderRexDaringDino.class));
        cards.add(new SetCardInfo("Spider-Suit", 176, Rarity.UNCOMMON, mage.cards.s.SpiderSuit.class));
        cards.add(new SetCardInfo("Spiders-Man, Heroic Horde", 117, Rarity.UNCOMMON, mage.cards.s.SpidersManHeroicHorde.class));
        cards.add(new SetCardInfo("Starling, Aerial Ally", 18, Rarity.COMMON, mage.cards.s.StarlingAerialAlly.class));
        cards.add(new SetCardInfo("Steel Wrecking Ball", 177, Rarity.COMMON, mage.cards.s.SteelWreckingBall.class));
        cards.add(new SetCardInfo("Stegron the Dinosaur Man", 95, Rarity.COMMON, mage.cards.s.StegronTheDinosaurMan.class));
        cards.add(new SetCardInfo("Swamp", 191, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 196, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiote Spider-Man", 156, Rarity.RARE, mage.cards.s.SymbioteSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiote Spider-Man", 217, Rarity.RARE, mage.cards.s.SymbioteSpiderMan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taxi Driver", 97, Rarity.COMMON, mage.cards.t.TaxiDriver.class));
        cards.add(new SetCardInfo("The Clone Saga", 219, Rarity.RARE, mage.cards.t.TheCloneSaga.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Clone Saga", 28, Rarity.RARE, mage.cards.t.TheCloneSaga.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thwip!", 20, Rarity.COMMON, mage.cards.t.Thwip.class));
        cards.add(new SetCardInfo("Tombstone, Career Criminal", 70, Rarity.UNCOMMON, mage.cards.t.TombstoneCareerCriminal.class));
        cards.add(new SetCardInfo("Ultimate Green Goblin", 157, Rarity.RARE, mage.cards.u.UltimateGreenGoblin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ultimate Green Goblin", 276, Rarity.RARE, mage.cards.u.UltimateGreenGoblin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unstable Experiment", 47, Rarity.COMMON, mage.cards.u.UnstableExperiment.class));
        cards.add(new SetCardInfo("Venom's Hunger", 73, Rarity.COMMON, mage.cards.v.VenomsHunger.class));
        cards.add(new SetCardInfo("Venom, Evil Unleashed", 71, Rarity.COMMON, mage.cards.v.VenomEvilUnleashed.class));
        cards.add(new SetCardInfo("Vulture, Scheming Scavenger", 158, Rarity.UNCOMMON, mage.cards.v.VultureSchemingScavenger.class));
        cards.add(new SetCardInfo("Web Up", 21, Rarity.COMMON, mage.cards.w.WebUp.class));
        cards.add(new SetCardInfo("Web of Life and Destiny", 122, Rarity.MYTHIC, mage.cards.w.WebOfLifeAndDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Web of Life and Destiny", 268, Rarity.MYTHIC, mage.cards.w.WebOfLifeAndDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Web-Warriors", 159, Rarity.UNCOMMON, mage.cards.w.WebWarriors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Web-Warriors", 203, Rarity.UNCOMMON, mage.cards.w.WebWarriors.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whoosh!", 48, Rarity.COMMON, mage.cards.w.Whoosh.class));
        cards.add(new SetCardInfo("Wild Pack Squad", 23, Rarity.COMMON, mage.cards.w.WildPackSquad.class));
        cards.add(new SetCardInfo("With Great Power...", 24, Rarity.RARE, mage.cards.w.WithGreatPower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("With Great Power...", 248, Rarity.RARE, mage.cards.w.WithGreatPower.class, NON_FULL_USE_VARIOUS));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
