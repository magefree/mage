package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pxln
 */
public class IxalanPromos extends ExpansionSet {

    private static final IxalanPromos instance = new IxalanPromos();

    public static IxalanPromos getInstance() {
        return instance;
    }

    private IxalanPromos() {
        super("Ixalan Promos", "PXLN", ExpansionSet.buildDate(2017, 9, 29), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Adanto, the First Fort", "22s", Rarity.RARE, mage.cards.a.AdantoTheFirstFort.class));
        cards.add(new SetCardInfo("Admiral Beckett Brass", "217s", Rarity.MYTHIC, mage.cards.a.AdmiralBeckettBrass.class));
        cards.add(new SetCardInfo("Angrath's Marauders", "132s", Rarity.RARE, mage.cards.a.AngrathsMarauders.class));
        cards.add(new SetCardInfo("Arcane Adaptation", "46p", Rarity.RARE, mage.cards.a.ArcaneAdaptation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Adaptation", "46s", Rarity.RARE, mage.cards.a.ArcaneAdaptation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arguel's Blood Fast", "90s", Rarity.RARE, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Ashes of the Abhorrent", "2p", Rarity.RARE, mage.cards.a.AshesOfTheAbhorrent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashes of the Abhorrent", "2s", Rarity.RARE, mage.cards.a.AshesOfTheAbhorrent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Axis of Mortality", "3s", Rarity.MYTHIC, mage.cards.a.AxisOfMortality.class));
        cards.add(new SetCardInfo("Azcanta, the Sunken Ruin", "74s", Rarity.RARE, mage.cards.a.AzcantaTheSunkenRuin.class));
        cards.add(new SetCardInfo("Bishop of Rebirth", 5, Rarity.RARE, mage.cards.b.BishopOfRebirth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bishop of Rebirth", "5s", Rarity.RARE, mage.cards.b.BishopOfRebirth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodcrazed Paladin", "93s", Rarity.RARE, mage.cards.b.BloodcrazedPaladin.class));
        cards.add(new SetCardInfo("Boneyard Parley", "94s", Rarity.MYTHIC, mage.cards.b.BoneyardParley.class));
        cards.add(new SetCardInfo("Burning Sun's Avatar", 135, Rarity.RARE, mage.cards.b.BurningSunsAvatar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burning Sun's Avatar", "135s", Rarity.RARE, mage.cards.b.BurningSunsAvatar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Lannery Storm", "136p", Rarity.RARE, mage.cards.c.CaptainLanneryStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Lannery Storm", "136s", Rarity.RARE, mage.cards.c.CaptainLanneryStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captivating Crew", "137p", Rarity.RARE, mage.cards.c.CaptivatingCrew.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captivating Crew", "137s", Rarity.RARE, mage.cards.c.CaptivatingCrew.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carnage Tyrant", "179p", Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carnage Tyrant", "179s", Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conqueror's Foothold", "234s", Rarity.RARE, mage.cards.c.ConquerorsFoothold.class));
        cards.add(new SetCardInfo("Conqueror's Galleon", "234s", Rarity.RARE, mage.cards.c.ConquerorsGalleon.class));
        cards.add(new SetCardInfo("Daring Saboteur", "49s", Rarity.RARE, mage.cards.d.DaringSaboteur.class));
        cards.add(new SetCardInfo("Deadeye Tracker", "99p", Rarity.RARE, mage.cards.d.DeadeyeTracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deadeye Tracker", "99s", Rarity.RARE, mage.cards.d.DeadeyeTracker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathgorge Scavenger", "184p", Rarity.RARE, mage.cards.d.DeathgorgeScavenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathgorge Scavenger", "184s", Rarity.RARE, mage.cards.d.DeathgorgeScavenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deeproot Champion", 185, Rarity.RARE, mage.cards.d.DeeprootChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deeproot Champion", "185p", Rarity.RARE, mage.cards.d.DeeprootChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deeproot Champion", "185s", Rarity.RARE, mage.cards.d.DeeprootChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Fleet Ravager", "104s", Rarity.MYTHIC, mage.cards.d.DireFleetRavager.class));
        cards.add(new SetCardInfo("Dowsing Dagger", "235s", Rarity.RARE, mage.cards.d.DowsingDagger.class));
        cards.add(new SetCardInfo("Dragonskull Summit", "252p", Rarity.RARE, mage.cards.d.DragonskullSummit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragonskull Summit", "252s", Rarity.RARE, mage.cards.d.DragonskullSummit.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreamcaller Siren", "54s", Rarity.RARE, mage.cards.d.DreamcallerSiren.class));
        cards.add(new SetCardInfo("Drowned Catacomb", "253p", Rarity.RARE, mage.cards.d.DrownedCatacomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drowned Catacomb", "253s", Rarity.RARE, mage.cards.d.DrownedCatacomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Emperor's Vanguard", "189s", Rarity.RARE, mage.cards.e.EmperorsVanguard.class));
        cards.add(new SetCardInfo("Entrancing Melody", "55p", Rarity.RARE, mage.cards.e.EntrancingMelody.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Entrancing Melody", "55s", Rarity.RARE, mage.cards.e.EntrancingMelody.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fathom Fleet Captain", "106p", Rarity.RARE, mage.cards.f.FathomFleetCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fathom Fleet Captain", "106s", Rarity.RARE, mage.cards.f.FathomFleetCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fell Flagship", "238s", Rarity.RARE, mage.cards.f.FellFlagship.class));
        cards.add(new SetCardInfo("Fleet Swallower", "57s", Rarity.RARE, mage.cards.f.FleetSwallower.class));
        cards.add(new SetCardInfo("Gishath, Sun's Avatar", "222s", Rarity.MYTHIC, mage.cards.g.GishathSunsAvatar.class));
        cards.add(new SetCardInfo("Glacial Fortress", "255p", Rarity.RARE, mage.cards.g.GlacialFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glacial Fortress", "255s", Rarity.RARE, mage.cards.g.GlacialFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goring Ceratops", "13s", Rarity.RARE, mage.cards.g.GoringCeratops.class));
        cards.add(new SetCardInfo("Growing Rites of Itlimoc", "191s", Rarity.RARE, mage.cards.g.GrowingRitesOfItlimoc.class));
        cards.add(new SetCardInfo("Herald of Secret Streams", "59p", Rarity.RARE, mage.cards.h.HeraldOfSecretStreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herald of Secret Streams", "59s", Rarity.RARE, mage.cards.h.HeraldOfSecretStreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hostage Taker", "223s", Rarity.RARE, mage.cards.h.HostageTaker.class));
        cards.add(new SetCardInfo("Huatli, Warrior Poet", "224s", Rarity.MYTHIC, mage.cards.h.HuatliWarriorPoet.class));
        cards.add(new SetCardInfo("Itlimoc, Cradle of the Sun", "191s", Rarity.RARE, mage.cards.i.ItlimocCradleOfTheSun.class));
        cards.add(new SetCardInfo("Jace, Cunning Castaway", "60s", Rarity.MYTHIC, mage.cards.j.JaceCunningCastaway.class));
        cards.add(new SetCardInfo("Kinjalli's Sunwing", "19p", Rarity.RARE, mage.cards.k.KinjallisSunwing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kinjalli's Sunwing", "19s", Rarity.RARE, mage.cards.k.KinjallisSunwing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kopala, Warden of Waves", "61p", Rarity.RARE, mage.cards.k.KopalaWardenOfWaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kopala, Warden of Waves", "61s", Rarity.RARE, mage.cards.k.KopalaWardenOfWaves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legion's Landing", "22s", Rarity.RARE, mage.cards.l.LegionsLanding.class));
        cards.add(new SetCardInfo("Lost Vale", "235s", Rarity.RARE, mage.cards.l.LostVale.class));
        cards.add(new SetCardInfo("Mavren Fein, Dusk Apostle", "24p", Rarity.RARE, mage.cards.m.MavrenFeinDuskApostle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mavren Fein, Dusk Apostle", "24s", Rarity.RARE, mage.cards.m.MavrenFeinDuskApostle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old-Growth Dryads", "199s", Rarity.RARE, mage.cards.o.OldGrowthDryads.class));
        cards.add(new SetCardInfo("Overflowing Insight", "66s", Rarity.MYTHIC, mage.cards.o.OverflowingInsight.class));
        cards.add(new SetCardInfo("Priest of the Wakening Sun", "27s", Rarity.RARE, mage.cards.p.PriestOfTheWakeningSun.class));
        cards.add(new SetCardInfo("Primal Amulet", "243s", Rarity.RARE, mage.cards.p.PrimalAmulet.class));
        cards.add(new SetCardInfo("Primal Wellspring", "243s", Rarity.RARE, mage.cards.p.PrimalWellspring.class));
        cards.add(new SetCardInfo("Rampaging Ferocidon", "154s", Rarity.RARE, mage.cards.r.RampagingFerocidon.class));
        cards.add(new SetCardInfo("Regisaur Alpha", "227p", Rarity.RARE, mage.cards.r.RegisaurAlpha.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Regisaur Alpha", "227s", Rarity.RARE, mage.cards.r.RegisaurAlpha.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Repeating Barrage", "156s", Rarity.RARE, mage.cards.r.RepeatingBarrage.class));
        cards.add(new SetCardInfo("Revel in Riches", "117p", Rarity.RARE, mage.cards.r.RevelInRiches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Revel in Riches", "117s", Rarity.RARE, mage.cards.r.RevelInRiches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ripjaw Raptor", "203p", Rarity.RARE, mage.cards.r.RipjawRaptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ripjaw Raptor", "203s", Rarity.RARE, mage.cards.r.RipjawRaptor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("River's Rebuke", "71p", Rarity.RARE, mage.cards.r.RiversRebuke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("River's Rebuke", "71s", Rarity.RARE, mage.cards.r.RiversRebuke.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rootbound Crag", "256p", Rarity.RARE, mage.cards.r.RootboundCrag.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rootbound Crag", "256s", Rarity.RARE, mage.cards.r.RootboundCrag.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rowdy Crew", "159s", Rarity.MYTHIC, mage.cards.r.RowdyCrew.class));
        cards.add(new SetCardInfo("Ruin Raider", "118p", Rarity.RARE, mage.cards.r.RuinRaider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruin Raider", "118s", Rarity.RARE, mage.cards.r.RuinRaider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum Seeker", "120p", Rarity.RARE, mage.cards.s.SanctumSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum Seeker", "120s", Rarity.RARE, mage.cards.s.SanctumSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanguine Sacrament", "33s", Rarity.RARE, mage.cards.s.SanguineSacrament.class));
        cards.add(new SetCardInfo("Search for Azcanta", "74s", Rarity.RARE, mage.cards.s.SearchForAzcanta.class));
        cards.add(new SetCardInfo("Settle the Wreckage", "34p", Rarity.RARE, mage.cards.s.SettleTheWreckage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Settle the Wreckage", "34s", Rarity.RARE, mage.cards.s.SettleTheWreckage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadowed Caravel", "246s", Rarity.RARE, mage.cards.s.ShadowedCaravel.class));
        cards.add(new SetCardInfo("Shapers' Sanctuary", "206p", Rarity.RARE, mage.cards.s.ShapersSanctuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shapers' Sanctuary", "206s", Rarity.RARE, mage.cards.s.ShapersSanctuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorcerous Spyglass", "248p", Rarity.RARE, mage.cards.s.SorcerousSpyglass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sorcerous Spyglass", "248s", Rarity.RARE, mage.cards.s.SorcerousSpyglass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spell Swindle", "82p", Rarity.RARE, mage.cards.s.SpellSwindle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spell Swindle", "82s", Rarity.RARE, mage.cards.s.SpellSwindle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spires of Orazca", "249s", Rarity.RARE, mage.cards.s.SpiresOfOrazca.class));
        cards.add(new SetCardInfo("Spitfire Bastion", "173s", Rarity.RARE, mage.cards.s.SpitfireBastion.class));
        cards.add(new SetCardInfo("Star of Extinction", "161p", Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Star of Extinction", "161s", Rarity.MYTHIC, mage.cards.s.StarOfExtinction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunbird's Invocation", "165p", Rarity.RARE, mage.cards.s.SunbirdsInvocation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunbird's Invocation", "165s", Rarity.RARE, mage.cards.s.SunbirdsInvocation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunpetal Grove", "257p", Rarity.RARE, mage.cards.s.SunpetalGrove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sunpetal Grove", "257s", Rarity.RARE, mage.cards.s.SunpetalGrove.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword-Point Diplomacy", "126p", Rarity.RARE, mage.cards.s.SwordPointDiplomacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword-Point Diplomacy", "126s", Rarity.RARE, mage.cards.s.SwordPointDiplomacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Aclazotz", "90s", Rarity.RARE, mage.cards.t.TempleOfAclazotz.class));
        cards.add(new SetCardInfo("Thaumatic Compass", "249s", Rarity.RARE, mage.cards.t.ThaumaticCompass.class));
        cards.add(new SetCardInfo("Tilonalli's Skinshifter", "170s", Rarity.RARE, mage.cards.t.TilonallisSkinshifter.class));
        cards.add(new SetCardInfo("Tishana, Voice of Thunder", "230s", Rarity.MYTHIC, mage.cards.t.TishanaVoiceOfThunder.class));
        cards.add(new SetCardInfo("Tocatli Honor Guard", "42p", Rarity.RARE, mage.cards.t.TocatliHonorGuard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tocatli Honor Guard", "42s", Rarity.RARE, mage.cards.t.TocatliHonorGuard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Treasure Cove", "250s", Rarity.RARE, mage.cards.t.TreasureCove.class));
        cards.add(new SetCardInfo("Treasure Map", "250s", Rarity.RARE, mage.cards.t.TreasureMap.class));
        cards.add(new SetCardInfo("Unclaimed Territory", 258, Rarity.UNCOMMON, mage.cards.u.UnclaimedTerritory.class));
        cards.add(new SetCardInfo("Vance's Blasting Cannons", "173s", Rarity.RARE, mage.cards.v.VancesBlastingCannons.class));
        cards.add(new SetCardInfo("Vanquisher's Banner", "251p", Rarity.RARE, mage.cards.v.VanquishersBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vanquisher's Banner", "251s", Rarity.RARE, mage.cards.v.VanquishersBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", "213p", Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Verdant Sun's Avatar", "213s", Rarity.RARE, mage.cards.v.VerdantSunsAvatar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vona, Butcher of Magan", "231s", Rarity.MYTHIC, mage.cards.v.VonaButcherOfMagan.class));
        cards.add(new SetCardInfo("Vraska's Contempt", "129p", Rarity.RARE, mage.cards.v.VraskasContempt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska's Contempt", "129s", Rarity.RARE, mage.cards.v.VraskasContempt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska, Relic Seeker", "232p", Rarity.MYTHIC, mage.cards.v.VraskaRelicSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vraska, Relic Seeker", "232s", Rarity.MYTHIC, mage.cards.v.VraskaRelicSeeker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wakening Sun's Avatar", "44s", Rarity.MYTHIC, mage.cards.w.WakeningSunsAvatar.class));
        cards.add(new SetCardInfo("Waker of the Wilds", "215s", Rarity.RARE, mage.cards.w.WakerOfTheWilds.class));
        cards.add(new SetCardInfo("Walk the Plank", 130, Rarity.UNCOMMON, mage.cards.w.WalkThePlank.class));
     }
}
