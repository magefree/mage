package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class CoreSet2020 extends ExpansionSet {

    private static final CoreSet2020 instance = new CoreSet2020();

    public static CoreSet2020 getInstance() {
        return instance;
    }

    List<CardInfo> savedSpecialCommon = new ArrayList<>();
    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private CoreSet2020() {
        super("Core Set 2020", "M20", ExpansionSet.buildDate(2019, 7, 12), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterSpecial = 0;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;

        // Core 2020 boosters have a 5/12 chance of basic land being replaced
        // with the common taplands, which DO NOT appear in the common slot.
        this.ratioBoosterSpecialLand = 12;
        this.ratioBoosterSpecialLandNumerator = 5;

        cards.add(new SetCardInfo("Aerial Assault", 1, Rarity.COMMON, mage.cards.a.AerialAssault.class));
        cards.add(new SetCardInfo("Aether Gust", 42, Rarity.UNCOMMON, mage.cards.a.AetherGust.class));
        cards.add(new SetCardInfo("Agent of Treachery", 43, Rarity.RARE, mage.cards.a.AgentOfTreachery.class));
        cards.add(new SetCardInfo("Ajani, Inspiring Leader", 282, Rarity.MYTHIC, mage.cards.a.AjaniInspiringLeader.class));
        cards.add(new SetCardInfo("Ajani, Strength of the Pride", 2, Rarity.MYTHIC, mage.cards.a.AjaniStrengthOfThePride.class));
        cards.add(new SetCardInfo("Angel of Vitality", 4, Rarity.UNCOMMON, mage.cards.a.AngelOfVitality.class));
        cards.add(new SetCardInfo("Atemsis, All-Seeing", 46, Rarity.RARE, mage.cards.a.AtemsisAllSeeing.class));
        cards.add(new SetCardInfo("Barkhide Troll", 165, Rarity.UNCOMMON, mage.cards.b.BarkhideTroll.class));
        cards.add(new SetCardInfo("Bishop of Wings", 8, Rarity.RARE, mage.cards.b.BishopOfWings.class));
        cards.add(new SetCardInfo("Blightbeetle", 87, Rarity.UNCOMMON, mage.cards.b.Blightbeetle.class));
        cards.add(new SetCardInfo("Blood Burglar", 88, Rarity.COMMON, mage.cards.b.BloodBurglar.class));
        cards.add(new SetCardInfo("Blood for Bones", 89, Rarity.UNCOMMON, mage.cards.b.BloodForBones.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 242, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Bloodsoaked Altar", 90, Rarity.UNCOMMON, mage.cards.b.BloodsoakedAltar.class));
        cards.add(new SetCardInfo("Bloodthirsty Aerialist", 91, Rarity.UNCOMMON, mage.cards.b.BloodthirstyAerialist.class));
        cards.add(new SetCardInfo("Blossoming Sands", 243, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bone Splinters", 92, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Boneclad Necromancer", 93, Rarity.COMMON, mage.cards.b.BonecladNecromancer.class));
        cards.add(new SetCardInfo("Captivating Gyre", 51, Rarity.UNCOMMON, mage.cards.c.CaptivatingGyre.class));
        cards.add(new SetCardInfo("Cavalier of Dawn", 10, Rarity.MYTHIC, mage.cards.c.CavalierOfDawn.class));
        cards.add(new SetCardInfo("Cavalier of Flame", 125, Rarity.MYTHIC, mage.cards.c.CavalierOfFlame.class));
        cards.add(new SetCardInfo("Cavalier of Night", 94, Rarity.MYTHIC, mage.cards.c.CavalierOfNight.class));
        cards.add(new SetCardInfo("Cavalier of Thorns", 167, Rarity.MYTHIC, mage.cards.c.CavalierOfThorns.class));
        cards.add(new SetCardInfo("Cerulean Drake", 53, Rarity.UNCOMMON, mage.cards.c.CeruleanDrake.class));
        cards.add(new SetCardInfo("Chandra's Embercat", 129, Rarity.COMMON, mage.cards.c.ChandrasEmbercat.class));
        cards.add(new SetCardInfo("Chandra's Regulator", 131, Rarity.RARE, mage.cards.c.ChandrasRegulator.class));
        cards.add(new SetCardInfo("Chandra's Spitfire", 132, Rarity.UNCOMMON, mage.cards.c.ChandrasSpitfire.class));
        cards.add(new SetCardInfo("Chandra, Acolyte of Flame", 126, Rarity.RARE, mage.cards.c.ChandraAcolyteOfFlame.class));
        cards.add(new SetCardInfo("Chandra, Awakened Inferno", 127, Rarity.MYTHIC, mage.cards.c.ChandraAwakenedInferno.class));
        cards.add(new SetCardInfo("Chandra, Flame's Fury", 294, Rarity.COMMON, mage.cards.c.ChandraFlamesFury.class));
        cards.add(new SetCardInfo("Chandra, Novice Pyromancer", 128, Rarity.UNCOMMON, mage.cards.c.ChandraNovicePyromancer.class));
        cards.add(new SetCardInfo("Cloudkin Seer", 54, Rarity.COMMON, mage.cards.c.CloudkinSeer.class));
        cards.add(new SetCardInfo("Convolute", 55, Rarity.COMMON, mage.cards.c.Convolute.class));
        cards.add(new SetCardInfo("Corpse Knight", 206, Rarity.UNCOMMON, mage.cards.c.CorpseKnight.class));
        cards.add(new SetCardInfo("Creeping Trailblazer", 207, Rarity.UNCOMMON, mage.cards.c.CreepingTrailblazer.class));
        cards.add(new SetCardInfo("Cryptic Caves", 244, Rarity.UNCOMMON, mage.cards.c.CrypticCaves.class));
        cards.add(new SetCardInfo("Destructive Digger", 134, Rarity.COMMON, mage.cards.d.DestructiveDigger.class));
        cards.add(new SetCardInfo("Devout Decree", 13, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Diamond Knight", 224, Rarity.UNCOMMON, mage.cards.d.DiamondKnight.class));
        cards.add(new SetCardInfo("Disenchant", 14, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disfigure", 95, Rarity.UNCOMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Dismal Backwater", 245, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Diviner's Lockbox", 225, Rarity.UNCOMMON, mage.cards.d.DivinersLockbox.class));
        cards.add(new SetCardInfo("Dragon Mage", 135, Rarity.UNCOMMON, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Drawn from Dreams", 56, Rarity.RARE, mage.cards.d.DrawnFromDreams.class));
        cards.add(new SetCardInfo("Dread Presence", 96, Rarity.RARE, mage.cards.d.DreadPresence.class));
        cards.add(new SetCardInfo("Dungeon Geists", 57, Rarity.RARE, mage.cards.d.DungeonGeists.class));
        cards.add(new SetCardInfo("Elvish Reclaimer", 169, Rarity.RARE, mage.cards.e.ElvishReclaimer.class));
        cards.add(new SetCardInfo("Ember Hauler", 137, Rarity.UNCOMMON, mage.cards.e.EmberHauler.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 208, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("Eternal Isolation", 15, Rarity.UNCOMMON, mage.cards.e.EternalIsolation.class));
        cards.add(new SetCardInfo("Evolving Wilds", 246, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Ferocious Pup", 171, Rarity.COMMON, mage.cards.f.FerociousPup.class));
        cards.add(new SetCardInfo("Field of the Dead", 247, Rarity.RARE, mage.cards.f.FieldOfTheDead.class));
        cards.add(new SetCardInfo("Flame Sweep", 139, Rarity.UNCOMMON, mage.cards.f.FlameSweep.class));
        cards.add(new SetCardInfo("Flood of Tears", 59, Rarity.RARE, mage.cards.f.FloodOfTears.class));
        cards.add(new SetCardInfo("Frost Lynx", 62, Rarity.COMMON, mage.cards.f.FrostLynx.class));
        cards.add(new SetCardInfo("Fry", 140, Rarity.UNCOMMON, mage.cards.f.Fry.class));
        cards.add(new SetCardInfo("Goblin Bird-Grabber", 142, Rarity.COMMON, mage.cards.g.GoblinBirdGrabber.class));
        cards.add(new SetCardInfo("Goblin Ringleader", 143, Rarity.UNCOMMON, mage.cards.g.GoblinRingleader.class));
        cards.add(new SetCardInfo("Goblin Smuggler", 144, Rarity.COMMON, mage.cards.g.GoblinSmuggler.class));
        cards.add(new SetCardInfo("Golos, Tireless Pilgrim", 226, Rarity.RARE, mage.cards.g.GolosTirelessPilgrim.class));
        cards.add(new SetCardInfo("Growth Cycle", 175, Rarity.COMMON, mage.cards.g.GrowthCycle.class));
        cards.add(new SetCardInfo("Hanged Executioner", 22, Rarity.RARE, mage.cards.h.HangedExecutioner.class));
        cards.add(new SetCardInfo("Hard Cover", 63, Rarity.UNCOMMON, mage.cards.h.HardCover.class));
        cards.add(new SetCardInfo("Heart-Piercer Bow", 228, Rarity.COMMON, mage.cards.h.HeartPiercerBow.class));
        cards.add(new SetCardInfo("Herald of the Sun", 23, Rarity.UNCOMMON, mage.cards.h.HeraldOfTheSun.class));
        cards.add(new SetCardInfo("Icon of Ancestry", 229, Rarity.RARE, mage.cards.i.IconOfAncestry.class));
        cards.add(new SetCardInfo("Infuriate", 145, Rarity.COMMON, mage.cards.i.Infuriate.class));
        cards.add(new SetCardInfo("Ironroot Warlord", 209, Rarity.UNCOMMON, mage.cards.i.IronrootWarlord.class));
        cards.add(new SetCardInfo("Jungle Hollow", 248, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaalia, Zenith Seeker", 210, Rarity.MYTHIC, mage.cards.k.KaaliaZenithSeeker.class));
        cards.add(new SetCardInfo("Kethis, the Hidden Hand", 211, Rarity.MYTHIC, mage.cards.k.KethisTheHiddenHand.class));
        cards.add(new SetCardInfo("Knight of the Ebon Legion", 105, Rarity.RARE, mage.cards.k.KnightOfTheEbonLegion.class));
        cards.add(new SetCardInfo("Kykar, Wind's Fury", 212, Rarity.MYTHIC, mage.cards.k.KykarWindsFury.class));
        cards.add(new SetCardInfo("Lavakin Brawler", 147, Rarity.COMMON, mage.cards.l.LavakinBrawler.class));
        cards.add(new SetCardInfo("Leafkin Druid", 178, Rarity.COMMON, mage.cards.l.LeafkinDruid.class));
        cards.add(new SetCardInfo("Legion's End", 106, Rarity.RARE, mage.cards.l.LegionsEnd.class));
        cards.add(new SetCardInfo("Leyline of Abundance", 179, Rarity.RARE, mage.cards.l.LeylineOfAbundance.class));
        cards.add(new SetCardInfo("Leyline of Anticipation", 64, Rarity.RARE, mage.cards.l.LeylineOfAnticipation.class));
        cards.add(new SetCardInfo("Leyline of Sanctity", 26, Rarity.RARE, mage.cards.l.LeylineOfSanctity.class));
        cards.add(new SetCardInfo("Leyline of the Void", 107, Rarity.RARE, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Loaming Shaman", 180, Rarity.UNCOMMON, mage.cards.l.LoamingShaman.class));
        cards.add(new SetCardInfo("Lotus Field", 249, Rarity.RARE, mage.cards.l.LotusField.class));
        cards.add(new SetCardInfo("Loxodon Lifechanter", 27, Rarity.RARE, mage.cards.l.LoxodonLifechanter.class));
        cards.add(new SetCardInfo("Loyal Pegasus", 28, Rarity.UNCOMMON, mage.cards.l.LoyalPegasus.class));
        cards.add(new SetCardInfo("Manifold Key", 230, Rarity.UNCOMMON, mage.cards.m.ManifoldKey.class));
        cards.add(new SetCardInfo("Marauding Raptor", 150, Rarity.RARE, mage.cards.m.MaraudingRaptor.class));
        cards.add(new SetCardInfo("Mask of Immolation", 151, Rarity.UNCOMMON, mage.cards.m.MaskOfImmolation.class));
        cards.add(new SetCardInfo("Masterful Replication", 65, Rarity.RARE, mage.cards.m.MasterfulReplication.class));
        cards.add(new SetCardInfo("Moat Piranhas", 67, Rarity.COMMON, mage.cards.m.MoatPiranhas.class));
        cards.add(new SetCardInfo("Moldervine Reclamation", 214, Rarity.UNCOMMON, mage.cards.m.MoldervineReclamation.class));
        cards.add(new SetCardInfo("Mu Yanling, Celestial Wind", 286, Rarity.MYTHIC, mage.cards.m.MuYanlingCelestialWind.class));
        cards.add(new SetCardInfo("Mu Yanling, Sky Dancer", 68, Rarity.MYTHIC, mage.cards.m.MuYanlingSkyDancer.class));
        cards.add(new SetCardInfo("Murder", 109, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Negate", 69, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nightpack Ambusher", 185, Rarity.RARE, mage.cards.n.NightpackAmbusher.class));
        cards.add(new SetCardInfo("Noxious Grasp", 110, Rarity.UNCOMMON, mage.cards.n.NoxiousGrasp.class));
        cards.add(new SetCardInfo("Octoprophet", 70, Rarity.COMMON, mage.cards.o.Octoprophet.class));
        cards.add(new SetCardInfo("Ogre Siegebreaker", 215, Rarity.UNCOMMON, mage.cards.o.OgreSiegebreaker.class));
        cards.add(new SetCardInfo("Omnath, Locus of the Roil", 216, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfTheRoil.class));
        cards.add(new SetCardInfo("Pacifism", 32, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Planar Cleansing", 33, Rarity.RARE, mage.cards.p.PlanarCleansing.class));
        cards.add(new SetCardInfo("Portal of Sanctuary", 71, Rarity.UNCOMMON, mage.cards.p.PortalOfSanctuary.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 189, Rarity.UNCOMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Raise the Alarm", 34, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Rapacious Dragon", 153, Rarity.UNCOMMON, mage.cards.r.RapaciousDragon.class));
        cards.add(new SetCardInfo("Reckless Air Strike", 154, Rarity.COMMON, mage.cards.r.RecklessAirStrike.class));
        cards.add(new SetCardInfo("Renowned Weaponsmith", 72, Rarity.UNCOMMON, mage.cards.r.RenownedWeaponsmith.class));
        cards.add(new SetCardInfo("Retributive Wand", 236, Rarity.UNCOMMON, mage.cards.r.RetributiveWand.class));
        cards.add(new SetCardInfo("Risen Reef", 217, Rarity.UNCOMMON, mage.cards.r.RisenReef.class));
        cards.add(new SetCardInfo("Rotting Regisaur", 111, Rarity.RARE, mage.cards.r.RottingRegisaur.class));
        cards.add(new SetCardInfo("Rugged Highlands", 250, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Rule of Law", 35, Rarity.UNCOMMON, mage.cards.r.RuleOfLaw.class));
        cards.add(new SetCardInfo("Scheming Symmetry", 113, Rarity.RARE, mage.cards.s.SchemingSymmetry.class));
        cards.add(new SetCardInfo("Scholar of the Ages", 74, Rarity.UNCOMMON, mage.cards.s.ScholarOfTheAges.class));
        cards.add(new SetCardInfo("Scoured Barrens", 251, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Scuttlemutt", 238, Rarity.UNCOMMON, mage.cards.s.Scuttlemutt.class));
        cards.add(new SetCardInfo("Season of Growth", 191, Rarity.UNCOMMON, mage.cards.s.SeasonOfGrowth.class));
        cards.add(new SetCardInfo("Sephara, Sky's Blade", 36, Rarity.RARE, mage.cards.s.SepharaSkysBlade.class));
        cards.add(new SetCardInfo("Shared Summons", 193, Rarity.RARE, mage.cards.s.SharedSummons.class));
        cards.add(new SetCardInfo("Shifting Ceratops", 194, Rarity.RARE, mage.cards.s.ShiftingCeratops.class));
        cards.add(new SetCardInfo("Silverback Shaman", 195, Rarity.COMMON, mage.cards.s.SilverbackShaman.class));
        cards.add(new SetCardInfo("Sorin, Imperious Bloodlord", 115, Rarity.MYTHIC, mage.cards.s.SorinImperiousBloodlord.class));
        cards.add(new SetCardInfo("Sorin, Vampire Lord", 290, Rarity.MYTHIC, mage.cards.s.SorinVampireLord.class));
        cards.add(new SetCardInfo("Spectral Sailor", 76, Rarity.UNCOMMON, mage.cards.s.SpectralSailor.class));
        cards.add(new SetCardInfo("Squad Captain", 38, Rarity.COMMON, mage.cards.s.SquadCaptain.class));
        cards.add(new SetCardInfo("Starfield Mystic", 39, Rarity.RARE, mage.cards.s.StarfieldMystic.class));
        cards.add(new SetCardInfo("Steel Overseer", 239, Rarity.RARE, mage.cards.s.SteelOverseer.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 252, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 253, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Malady", 254, Rarity.RARE, mage.cards.t.TempleOfMalady.class));
        cards.add(new SetCardInfo("Temple of Mystery", 255, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Silence", 256, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 257, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Thicket Crasher", 196, Rarity.COMMON, mage.cards.t.ThicketCrasher.class));
        cards.add(new SetCardInfo("Thornwood Falls", 258, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thought Distortion", 117, Rarity.UNCOMMON, mage.cards.t.ThoughtDistortion.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 197, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Tomebound Lich", 219, Rarity.UNCOMMON, mage.cards.t.TomeboundLich.class));
        cards.add(new SetCardInfo("Tranquil Cove", 259, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Uncaged Fury", 163, Rarity.UNCOMMON, mage.cards.u.UncagedFury.class));
        cards.add(new SetCardInfo("Unchained Berserker", 164, Rarity.UNCOMMON, mage.cards.u.UnchainedBerserker.class));
        cards.add(new SetCardInfo("Unsummon", 78, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vampire of the Dire Moon", 120, Rarity.UNCOMMON, mage.cards.v.VampireOfTheDireMoon.class));
        cards.add(new SetCardInfo("Vial of Dragonfire", 241, Rarity.COMMON, mage.cards.v.VialOfDragonfire.class));
        cards.add(new SetCardInfo("Vilis, Broker of Blood", 122, Rarity.RARE, mage.cards.v.VilisBrokerOfBlood.class));
        cards.add(new SetCardInfo("Vivien, Arkbow Ranger", 199, Rarity.MYTHIC, mage.cards.v.VivienArkbowRanger.class));
        cards.add(new SetCardInfo("Vivien, Nature's Avenger", 298, Rarity.MYTHIC, mage.cards.v.VivienNaturesAvenger.class));
        cards.add(new SetCardInfo("Voracious Hydra", 200, Rarity.RARE, mage.cards.v.VoraciousHydra.class));
        cards.add(new SetCardInfo("Wakeroot Elemental", 202, Rarity.RARE, mage.cards.w.WakerootElemental.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 260, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Winged Words", 80, Rarity.COMMON, mage.cards.w.WingedWords.class));
        cards.add(new SetCardInfo("Wolfkin Bond", 203, Rarity.COMMON, mage.cards.w.WolfkinBond.class));
        cards.add(new SetCardInfo("Wolfrider's Saddle", 204, Rarity.UNCOMMON, mage.cards.w.WolfridersSaddle.class));
        cards.add(new SetCardInfo("Yarok's Fenlurker", 123, Rarity.UNCOMMON, mage.cards.y.YaroksFenlurker.class));
        cards.add(new SetCardInfo("Yarok, the Desecrated", 220, Rarity.MYTHIC, mage.cards.y.YarokTheDesecrated.class));
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        // Common cards retrievement of Core Set 2020 boosters - prevent the retrievement of the common lands
        if (rarity == Rarity.COMMON) {
            List<CardInfo> savedCardsInfos = savedCards.get(rarity);
            if (savedCardsInfos == null) {
                CardCriteria criteria = new CardCriteria();
                criteria.rarities(Rarity.COMMON);
                criteria.setCodes(this.code).notTypes(CardType.LAND);
                savedCardsInfos = CardRepository.instance.findCards(criteria);
                if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                    savedCardsInfos.removeIf(next -> next.getCardNumberAsInt() > maxCardNumberInBooster && rarity != Rarity.LAND);
                }
                savedCards.put(rarity, savedCardsInfos);
            }
            // Return a copy of the saved cards information, as not to let modify the original.
            return new ArrayList<>(savedCardsInfos);
        } else {
            return super.getCardsByRarity(rarity);
        }
    }

    @Override
    // the common taplands replacing the basic land
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes(this.code);
            criteria.rarities(Rarity.COMMON);
            criteria.types(CardType.LAND);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
