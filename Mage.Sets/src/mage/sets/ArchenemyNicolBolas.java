
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public final class ArchenemyNicolBolas extends ExpansionSet {

    private static final ArchenemyNicolBolas instance = new ArchenemyNicolBolas();

    public static ArchenemyNicolBolas getInstance() {
        return instance;
    }

    private ArchenemyNicolBolas() {
        super("Archenemy: Nicol Bolas", "E01", ExpansionSet.buildDate(2017, 6, 16), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Aegis Angel", 1, Rarity.RARE, mage.cards.a.AegisAngel.class));
        cards.add(new SetCardInfo("Aerial Responder", 2, Rarity.UNCOMMON, mage.cards.a.AerialResponder.class));
        cards.add(new SetCardInfo("Anointer of Champions", 3, Rarity.UNCOMMON, mage.cards.a.AnointerOfChampions.class));
        cards.add(new SetCardInfo("Archfiend of Depravity", 31, Rarity.RARE, mage.cards.a.ArchfiendOfDepravity.class));
        cards.add(new SetCardInfo("Avatar of Fury", 39, Rarity.RARE, mage.cards.a.AvatarOfFury.class));
        cards.add(new SetCardInfo("Baleful Strix", 80, Rarity.UNCOMMON, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 40, Rarity.COMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Blood Ogre", 41, Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Blood Tyrant", 81, Rarity.RARE, mage.cards.b.BloodTyrant.class));
        cards.add(new SetCardInfo("Chandra, Pyromaster", 42, Rarity.MYTHIC, mage.cards.c.ChandraPyromaster.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 43, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chandra's Phoenix", 44, Rarity.RARE, mage.cards.c.ChandrasPhoenix.class));
        cards.add(new SetCardInfo("Compulsive Research", 23, Rarity.COMMON, mage.cards.c.CompulsiveResearch.class));
        cards.add(new SetCardInfo("Coordinated Assault", 45, Rarity.UNCOMMON, mage.cards.c.CoordinatedAssault.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 82, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 92, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Cultivate", 62, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Deathbringer Regent", 32, Rarity.RARE, mage.cards.d.DeathbringerRegent.class));
        cards.add(new SetCardInfo("Doom Blade", 33, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Doomed Traveler", 4, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 93, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Dreadbore", 83, Rarity.RARE, mage.cards.d.Dreadbore.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 94, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 46, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Excoriate", 5, Rarity.COMMON, mage.cards.e.Excoriate.class));
        cards.add(new SetCardInfo("Expedition Raptor", 6, Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Explore", 63, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Extract from Darkness", 84, Rarity.UNCOMMON, mage.cards.e.ExtractFromDarkness.class));
        cards.add(new SetCardInfo("Fertilid", 64, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Fencing Ace", 7, Rarity.UNCOMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Fiendslayer Paladin", 8, Rarity.RARE, mage.cards.f.FiendslayerPaladin.class));
        cards.add(new SetCardInfo("Fiery Fall", 47, Rarity.COMMON, mage.cards.f.FieryFall.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 48, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Flickerwisp", 9, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Forest", 101, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 106, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Ancient", 65, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Gideon Jura", 10, Rarity.MYTHIC, mage.cards.g.GideonJura.class));
        cards.add(new SetCardInfo("Gideon's Lawkeeper", 11, Rarity.COMMON, mage.cards.g.GideonsLawkeeper.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 49, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Grand Abolisher", 12, Rarity.RARE, mage.cards.g.GrandAbolisher.class));
        cards.add(new SetCardInfo("Grasp of the Hieromancer", 13, Rarity.COMMON, mage.cards.g.GraspOfTheHieromancer.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 50, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Grixis Panorama", 95, Rarity.COMMON, mage.cards.g.GrixisPanorama.class));
        cards.add(new SetCardInfo("Guttersnipe", 51, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Hammerhand", 52, Rarity.COMMON, mage.cards.h.Hammerhand.class));
        cards.add(new SetCardInfo("Harvester of Souls", 34, Rarity.RARE, mage.cards.h.HarvesterOfSouls.class));
        cards.add(new SetCardInfo("Hunter's Prowess", 66, Rarity.RARE, mage.cards.h.HuntersProwess.class));
        cards.add(new SetCardInfo("Icefall Regent", 24, Rarity.RARE, mage.cards.i.IcefallRegent.class));
        cards.add(new SetCardInfo("Inferno Titan", 53, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));
        cards.add(new SetCardInfo("Ior Ruin Expedition", 25, Rarity.COMMON, mage.cards.i.IorRuinExpedition.class));
        cards.add(new SetCardInfo("Island", 103, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 98, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Khalni Heart Expedition", 67, Rarity.COMMON, mage.cards.k.KhalniHeartExpedition.class));
        cards.add(new SetCardInfo("Lightning Bolt", 54, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lightwielder Paladin", 14, Rarity.RARE, mage.cards.l.LightwielderPaladin.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 15, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Moment of Heroism", 16, Rarity.COMMON, mage.cards.m.MomentOfHeroism.class));
        cards.add(new SetCardInfo("Mountain", 100, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 105, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nicol Bolas, Planeswalker", 85, Rarity.MYTHIC, mage.cards.n.NicolBolasPlaneswalker.class));
        cards.add(new SetCardInfo("Nightscape Familiar", 35, Rarity.COMMON, mage.cards.n.NightscapeFamiliar.class));
        cards.add(new SetCardInfo("Nissa, Worldwaker", 68, Rarity.MYTHIC, mage.cards.n.NissaWorldwaker.class));
        cards.add(new SetCardInfo("Obelisk of Grixis", 88, Rarity.COMMON, mage.cards.o.ObeliskOfGrixis.class));
        cards.add(new SetCardInfo("Obsidian Fireheart", 55, Rarity.MYTHIC, mage.cards.o.ObsidianFireheart.class));
        cards.add(new SetCardInfo("Odric, Master Tactician", 17, Rarity.RARE, mage.cards.o.OdricMasterTactician.class));
        cards.add(new SetCardInfo("Oran-Rief Hydra", 69, Rarity.RARE, mage.cards.o.OranRiefHydra.class));
        cards.add(new SetCardInfo("Overseer of the Damned", 36, Rarity.RARE, mage.cards.o.OverseerOfTheDamned.class));
        cards.add(new SetCardInfo("Plains", 102, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 97, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Precinct Captain", 18, Rarity.RARE, mage.cards.p.PrecinctCaptain.class));
        cards.add(new SetCardInfo("Press the Advantage", 70, Rarity.UNCOMMON, mage.cards.p.PressTheAdvantage.class));
        cards.add(new SetCardInfo("Prognostic Sphinx", 26, Rarity.RARE, mage.cards.p.PrognosticSphinx.class));
        cards.add(new SetCardInfo("Rampaging Baloths", 71, Rarity.MYTHIC, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Reckless Scholar", 27, Rarity.UNCOMMON, mage.cards.r.RecklessScholar.class));
        cards.add(new SetCardInfo("Reckless Spite", 37, Rarity.UNCOMMON, mage.cards.r.RecklessSpite.class));
        cards.add(new SetCardInfo("Relief Captain", 19, Rarity.UNCOMMON, mage.cards.r.ReliefCaptain.class));
        cards.add(new SetCardInfo("Retreat to Kazandu", 72, Rarity.UNCOMMON, mage.cards.r.RetreatToKazandu.class));
        cards.add(new SetCardInfo("Scute Mob", 73, Rarity.RARE, mage.cards.s.ScuteMob.class));
        cards.add(new SetCardInfo("Searing Spear", 56, Rarity.COMMON, mage.cards.s.SearingSpear.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", 20, Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Skarrgan Firebird", 57, Rarity.UNCOMMON, mage.cards.s.SkarrganFirebird.class));
        cards.add(new SetCardInfo("Slave of Bolas", 86, Rarity.UNCOMMON, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Smoldering Spires", 96, Rarity.COMMON, mage.cards.s.SmolderingSpires.class));
        cards.add(new SetCardInfo("Soul Ransom", 87, Rarity.UNCOMMON, mage.cards.s.SoulRansom.class));
        cards.add(new SetCardInfo("Sphinx of Jwar Isle", 28, Rarity.RARE, mage.cards.s.SphinxOfJwarIsle.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 58, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Sudden Demise", 59, Rarity.RARE, mage.cards.s.SuddenDemise.class));
        cards.add(new SetCardInfo("Sun Titan", 21, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Swamp", 104, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 99, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of the Animist", 89, Rarity.RARE, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("Sylvan Bounty", 74, Rarity.COMMON, mage.cards.s.SylvanBounty.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 90, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Indulgence", 91, Rarity.UNCOMMON, mage.cards.t.TalismanOfIndulgence.class));
        cards.add(new SetCardInfo("Thragtusk", 75, Rarity.RARE, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Torchling", 60, Rarity.RARE, mage.cards.t.Torchling.class));
        cards.add(new SetCardInfo("Turntimber Basilisk", 76, Rarity.UNCOMMON, mage.cards.t.TurntimberBasilisk.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 38, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vastwood Zendikon", 77, Rarity.COMMON, mage.cards.v.VastwoodZendikon.class));
        cards.add(new SetCardInfo("Vines of the Recluse", 78, Rarity.COMMON, mage.cards.v.VinesOfTheRecluse.class));
        cards.add(new SetCardInfo("Vision Skeins", 29, Rarity.COMMON, mage.cards.v.VisionSkeins.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 61, Rarity.UNCOMMON, mage.cards.v.VolcanicGeyser.class));
        cards.add(new SetCardInfo("Windrider Eel", 30, Rarity.COMMON, mage.cards.w.WindriderEel.class));
        cards.add(new SetCardInfo("Woodborn Behemoth", 79, Rarity.UNCOMMON, mage.cards.w.WoodbornBehemoth.class));
        cards.add(new SetCardInfo("Youthful Knight", 22, Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
    }

}
