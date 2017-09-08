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
        cards.add(new SetCardInfo("Admiral Beckett Brass", 217, Rarity.MYTHIC, mage.cards.a.AdmiralBeckettBrass.class));
        cards.add(new SetCardInfo("Angrath's Marauders", 132, Rarity.RARE, mage.cards.a.AngrathsMarauders.class));
        cards.add(new SetCardInfo("Arguel's Blood Fast", 90, Rarity.RARE, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Ashes of the Abhorrent", 2, Rarity.RARE, mage.cards.a.AshesOfTheAbhorrent.class));
        cards.add(new SetCardInfo("Bishop of Rebirth", 5, Rarity.RARE, mage.cards.b.BishopOfRebirth.class));
        cards.add(new SetCardInfo("Belligerent Brontodon", 218, Rarity.UNCOMMON, mage.cards.b.BelligerentBrontodon.class));
        cards.add(new SetCardInfo("Bellowing Aegisaur", 4, Rarity.UNCOMMON, mage.cards.b.BellowingAegisaur.class));
        cards.add(new SetCardInfo("Bloodcrazed Paladin", 93, Rarity.RARE, mage.cards.b.BloodcrazedPaladin.class));
        cards.add(new SetCardInfo("Boneyard Parley", 94, Rarity.MYTHIC, mage.cards.b.BoneyardParley.class));
        cards.add(new SetCardInfo("Burning Sun's Avatar", 135, Rarity.RARE, mage.cards.b.BurningSunsAvatar.class));
        cards.add(new SetCardInfo("Call to the Feast", 219, Rarity.UNCOMMON, mage.cards.c.CallToTheFeast.class));
        cards.add(new SetCardInfo("Captain Lannery Storm", 136, Rarity.RARE, mage.cards.c.CaptainLanneryStorm.class));
        cards.add(new SetCardInfo("Carnage Tyrant", 179, Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class));
        cards.add(new SetCardInfo("Castaway's Despair", 281, Rarity.COMMON, mage.cards.c.CastawaysDespair.class));
        cards.add(new SetCardInfo("Commune with Dinosaurs", 181, Rarity.COMMON, mage.cards.c.CommuneWithDinosaurs.class));
        cards.add(new SetCardInfo("Conqueror's Foothold", 234, Rarity.RARE, mage.cards.c.ConquerorsFoothold.class));
        cards.add(new SetCardInfo("Conqueror's Galleon", 234, Rarity.RARE, mage.cards.c.ConquerorsGalleon.class));
        cards.add(new SetCardInfo("Daring Saboteur", 49, Rarity.RARE, mage.cards.d.DaringSaboteur.class));
        cards.add(new SetCardInfo("Deadeye Plunderers", 220, Rarity.UNCOMMON, mage.cards.d.DeadeyePlunderers.class));
        cards.add(new SetCardInfo("Deadeye Quartermaster", 50, Rarity.UNCOMMON, mage.cards.d.DeadeyeQuartermaster.class));
        cards.add(new SetCardInfo("Deadeye Tormentor", 98, Rarity.COMMON, mage.cards.d.DeadeyeTormentor.class));
        cards.add(new SetCardInfo("Deadeye Tracker", 99, Rarity.RARE, mage.cards.d.DeadeyeTracker.class));
        cards.add(new SetCardInfo("Deathless Ancient", 100, Rarity.UNCOMMON, mage.cards.d.DeathlessAncient.class));
        cards.add(new SetCardInfo("Deeproot Champion", 185, Rarity.RARE, mage.cards.d.DeeprootChampion.class));
        cards.add(new SetCardInfo("Deeproot Waters", 51, Rarity.UNCOMMON, mage.cards.d.DeeprootWaters.class));
        cards.add(new SetCardInfo("Dire Fleet Captain", 221, Rarity.UNCOMMON, mage.cards.d.DireFleetCaptain.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 252, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dreamcaller Siren", 54, Rarity.RARE, mage.cards.d.DreamcallerSiren.class));
        cards.add(new SetCardInfo("Drover of the Mighty", 167, Rarity.UNCOMMON, mage.cards.d.DroverOfTheMighty.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 253, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Duress", 105, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Dusk Legion Dreadnought", 236, Rarity.UNCOMMON, mage.cards.d.DuskLegionDreadnought.class));
        cards.add(new SetCardInfo("Duskborne Skymarcher", 9, Rarity.UNCOMMON, mage.cards.d.DuskborneSkymarcher.class));
        cards.add(new SetCardInfo("Emperor's Vanguard", 189, Rarity.RARE, mage.cards.e.EmperorsVanguard.class));
        cards.add(new SetCardInfo("Entrancing Melody", 55, Rarity.RARE, mage.cards.e.EntrancingMelody.class));
        cards.add(new SetCardInfo("Fathom Fleet Captain", 106, Rarity.RARE, mage.cards.f.FathomFleetCaptain.class));
        cards.add(new SetCardInfo("Favorable Winds", 56, Rarity.UNCOMMON, mage.cards.f.FavorableWinds.class));
        cards.add(new SetCardInfo("Fell Flagship", 238, Rarity.RARE, mage.cards.f.FellFlagship.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", 222, Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Glacial Fortress", 255, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Goring Ceratops", 13, Rarity.RARE, mage.cards.g.GoringCeratops.class));
        cards.add(new SetCardInfo("Grasping Current", 282, Rarity.RARE, mage.cards.g.GraspingCurrent.class));
        cards.add(new SetCardInfo("Grazing Whiptail", 190, Rarity.COMMON, mage.cards.g.GrazingWhiptail.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", 191, Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Headwater Sentries", 58, Rarity.COMMON, mage.cards.h.HeadwaterSentries.class));
        cards.add(new SetCardInfo("Herald of Secret Streams", 59, Rarity.RARE, mage.cards.h.HeraldOfSecretStreams.class));
        cards.add(new SetCardInfo("Hostage Taker", 223, Rarity.RARE, mage.cards.h.HostageTaker.class));
        cards.add(new SetCardInfo("Huatli's Snubhorn", 286, Rarity.COMMON, mage.cards.h.HuatlisSnubhorn.class));
        cards.add(new SetCardInfo("Huatli's Spurring", 287, Rarity.UNCOMMON, mage.cards.h.HuatlisSpurring.class));
        cards.add(new SetCardInfo("Huatli, Dinosaur Knight", 285, Rarity.MYTHIC, mage.cards.h.HuatliDinosaurKnight.class));
        cards.add(new SetCardInfo("Huatli, Warrior Poet", 224, Rarity.MYTHIC, mage.cards.h.HuatliWarriorPoet.class));
        cards.add(new SetCardInfo("Itlimoc, Cradle of the Sun", 191, Rarity.RARE, mage.cards.i.ItlimocCradleOfTheSun.class));
        cards.add(new SetCardInfo("Ixalan's Binding", 17, Rarity.UNCOMMON, mage.cards.i.IxalansBinding.class));
        cards.add(new SetCardInfo("Jace's Sentinel", 283, Rarity.UNCOMMON, mage.cards.j.JacesSentinel.class));
        cards.add(new SetCardInfo("Jace, Cunning Castaway", 60, Rarity.MYTHIC, mage.cards.j.JaceCunningCastaway.class));
        cards.add(new SetCardInfo("Jace, Ingenious Mind-Mage", 280, Rarity.MYTHIC, mage.cards.j.JaceIngeniousMindMage.class));
        cards.add(new SetCardInfo("Kinjalli's Sunwing", 19, Rarity.RARE, mage.cards.k.KinjallisSunwing.class));
        cards.add(new SetCardInfo("Kitesail Freebooter", 110, Rarity.UNCOMMON, mage.cards.k.KitesailFreebooter.class));
        cards.add(new SetCardInfo("Kopala, Warden of Waves", 61, Rarity.RARE, mage.cards.k.KopalaWardenOfWaves.class));
        cards.add(new SetCardInfo("Kumena's Speaker", 196, Rarity.UNCOMMON, mage.cards.k.KumenasSpeaker.class));
        cards.add(new SetCardInfo("Lightning Strike", 149, Rarity.UNCOMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Lookout's Dispersal", 62, Rarity.UNCOMMON, mage.cards.l.LookoutsDispersal.class));
        cards.add(new SetCardInfo("Lurking Chupacabra", 111, Rarity.UNCOMMON, mage.cards.l.LurkingChupacabra.class));
        cards.add(new SetCardInfo("Marauding Looter", 225, Rarity.UNCOMMON, mage.cards.m.MaraudingLooter.class));
        cards.add(new SetCardInfo("Mavren Fein, Dusk Apostle", 24, Rarity.RARE, mage.cards.m.MavrenFeinDuskApostle.class));
        cards.add(new SetCardInfo("Merfolk Branchwalker", 197, Rarity.UNCOMMON, mage.cards.m.MerfolkBranchwalker.class));
        cards.add(new SetCardInfo("Old-Growth Dryads", 199, Rarity.RARE, mage.cards.o.OldGrowthDryads.class));
        cards.add(new SetCardInfo("Opt", 65, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Otepec Huntmaster", 153, Rarity.UNCOMMON, mage.cards.o.OtepecHuntmaster.class));
        cards.add(new SetCardInfo("Overflowing Insight", 64, Rarity.MYTHIC, mage.cards.o.OverflowingInsight.class));
        cards.add(new SetCardInfo("Pillar of Origins", 241, Rarity.UNCOMMON, mage.cards.p.PillarOfOrigins.class));
        cards.add(new SetCardInfo("Pirate's Cutlass", 242, Rarity.COMMON, mage.cards.p.PiratesCutlass.class));
        cards.add(new SetCardInfo("Priest of the Wakening Sun", 27, Rarity.RARE, mage.cards.p.PriestOfTheWakeningSun.class));
        cards.add(new SetCardInfo("Primal Amulet", 243, Rarity.RARE, mage.cards.p.PrimalAmulet.class));
        cards.add(new SetCardInfo("Primal Wellspring", 243, Rarity.RARE, mage.cards.p.PrimalWellspring.class));
        cards.add(new SetCardInfo("Prosperous Pirates", 69, Rarity.COMMON, mage.cards.p.ProsperousPirates.class));
        cards.add(new SetCardInfo("Queen's Bay Soldier", 115, Rarity.COMMON, mage.cards.q.QueensBaySoldier.class));
        cards.add(new SetCardInfo("Raging Swordtooth", 226, Rarity.UNCOMMON, mage.cards.r.RagingSwordtooth.class));
        cards.add(new SetCardInfo("Ranging Raptors", 201, Rarity.UNCOMMON, mage.cards.r.RangingRaptors.class));
        cards.add(new SetCardInfo("Raptor Hatchling", 149, Rarity.UNCOMMON, mage.cards.r.RaptorHatchling.class));
        cards.add(new SetCardInfo("Ravenous Daggertooth", 202, Rarity.COMMON, mage.cards.r.RavenousDaggertooth.class));
        cards.add(new SetCardInfo("Regisaur Alpha", 227, Rarity.RARE, mage.cards.r.RegisaurAlpha.class));
        cards.add(new SetCardInfo("Revel in Riches", 117, Rarity.RARE, mage.cards.r.RevelInRiches.class));
        cards.add(new SetCardInfo("Ripjaw Raptor", 203, Rarity.RARE, mage.cards.r.RipjawRaptor.class));
        cards.add(new SetCardInfo("River's Rebuke", 71, Rarity.RARE, mage.cards.r.RiversRebuke.class));
        cards.add(new SetCardInfo("Rootbound Crag", 256, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Rowdy Crew", 159, Rarity.MYTHIC, mage.cards.r.RowdyCrew.class));
        cards.add(new SetCardInfo("Ruin Raider", 118, Rarity.RARE, mage.cards.r.RuinRaider.class));
        cards.add(new SetCardInfo("Sanctum Seeker", 120, Rarity.RARE, mage.cards.s.SanctumSeeker.class));
        cards.add(new SetCardInfo("Sanguine Sacrament", 33, Rarity.RARE, mage.cards.s.SanguineSacrament.class));
        cards.add(new SetCardInfo("Savage Stomp", 205, Rarity.UNCOMMON, mage.cards.s.SavageStomp.class));
        cards.add(new SetCardInfo("Sentinel Totem", 245, Rarity.UNCOMMON, mage.cards.s.SentinelTotem.class));
        cards.add(new SetCardInfo("Settle the Wreckage", 34, Rarity.RARE, mage.cards.s.SettleTheWreckage.class));
        cards.add(new SetCardInfo("Shapers of Nature", 228, Rarity.UNCOMMON, mage.cards.s.ShapersOfNature.class));
        cards.add(new SetCardInfo("Shapers' Sanctuary", 206, Rarity.RARE, mage.cards.s.ShapersSanctuary.class));
        cards.add(new SetCardInfo("Siren Stormtamer", 79, Rarity.UNCOMMON, mage.cards.s.SirenStormtamer.class));
        cards.add(new SetCardInfo("Sleek Schooner", 247, Rarity.UNCOMMON, mage.cards.s.SleekSchooner.class));
        cards.add(new SetCardInfo("Slice in Twain", 207, Rarity.UNCOMMON, mage.cards.s.SliceinTwain.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 248, Rarity.RARE, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Spires of Orazca", 249, Rarity.RARE, mage.cards.s.SpiresOfOrazca.class));
        cards.add(new SetCardInfo("Spell Pierce", 81, Rarity.RARE, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Star of Extinction", 161, Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class));
        cards.add(new SetCardInfo("Stone Quarry", 289, Rarity.COMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Storm Fleet Aerialist", 63, Rarity.UNCOMMON, mage.cards.s.StormFleetAerialist.class));
        cards.add(new SetCardInfo("Storm Fleet Arsonist", 162, Rarity.UNCOMMON, mage.cards.s.StormFleetArsonist.class));
        cards.add(new SetCardInfo("Sun-Blessed Mount", 288, Rarity.RARE, mage.cards.s.SunBlessedMount.class));
        cards.add(new SetCardInfo("Sun-Crowned Hunters", 164, Rarity.COMMON, mage.cards.s.SunCrownedHunters.class));
        cards.add(new SetCardInfo("Sunbird's Invocation", 165, Rarity.RARE, mage.cards.s.SunbirdsInvocation.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 257, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Temple of Aclazotz", 90, Rarity.RARE, mage.cards.t.TempleOfAclazotz.class));
        cards.add(new SetCardInfo("Thaumatic Compass", 249, Rarity.RARE, mage.cards.t.ThaumaticCompass.class));
        cards.add(new SetCardInfo("Thundering Spineback", 210, Rarity.UNCOMMON, mage.cards.t.ThunderingSpineback.class));
        cards.add(new SetCardInfo("Tilonalli's Skinshifter", 170, Rarity.RARE, mage.cards.t.TilonallisSkinshifter.class));
        cards.add(new SetCardInfo("Tishana's Wayfinder", 211, Rarity.COMMON, mage.cards.t.TishanasWayfinder.class));
        cards.add(new SetCardInfo("Tishana, Voice of Thunder", 230, Rarity.MYTHIC, mage.cards.t.TishanaVoiceOfThunder.class));
        cards.add(new SetCardInfo("Tocatli Honor Guard", 42, Rarity.RARE, mage.cards.t.TocatliHonorGuard.class));
        cards.add(new SetCardInfo("Treasure Cove", 1250, Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", 250, Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 258, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Unfriendly Fire", 172, Rarity.COMMON, mage.cards.u.UnfriendlyFire.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", 251, Rarity.RARE, mage.cards.v.VanquishersBanner.class));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", 213, Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class));
        cards.add(new SetCardInfo("Vona, Butcher of Magan", 231, Rarity.MYTHIC, mage.cards.v.VonaButcherOfMagan.class));
        cards.add(new SetCardInfo("Vraska's Contempt", 129, Rarity.RARE, mage.cards.v.VraskasContempt.class));
        cards.add(new SetCardInfo("Vraska, Relic Seeker", 232, Rarity.MYTHIC, mage.cards.v.VraskaRelicSeeker.class));
        cards.add(new SetCardInfo("Wakening Sun's Avatar", 44, Rarity.MYTHIC, mage.cards.w.WakeningSunsAvatar.class));
        cards.add(new SetCardInfo("Waker of the Wilds", 215, Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 130, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
        cards.add(new SetCardInfo("Wanted Scoundrels", 131, Rarity.UNCOMMON, mage.cards.w.WantedScoundrels.class));
        cards.add(new SetCardInfo("Wildgrowth Walker", 216, Rarity.UNCOMMON, mage.cards.w.WildgrowthWalker.class));
        cards.add(new SetCardInfo("Wily Goblin", 174, Rarity.UNCOMMON, mage.cards.w.WilyGoblin.class));
        cards.add(new SetCardInfo("Woodland Stream", 284, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
    }
}
