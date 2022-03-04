package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.constants.SuperType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fireshoes
 */
public final class Dominaria extends ExpansionSet {

    private static final Dominaria instance = new Dominaria();

    public static Dominaria getInstance() {
        return instance;
    }

    private Dominaria() {
        super("Dominaria", "DOM", ExpansionSet.buildDate(2018, 4, 27), SetType.EXPANSION);
        this.blockName = "Dominaria";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 10;
        this.ratioBoosterSpecialRare = 4;
        this.ratioBoosterSpecialMythic = 5;
        this.maxCardNumberInBooster = 269;

        cards.add(new SetCardInfo("Academy Drake", 40, Rarity.COMMON, mage.cards.a.AcademyDrake.class));
        cards.add(new SetCardInfo("Academy Journeymage", 41, Rarity.COMMON, mage.cards.a.AcademyJourneymage.class));
        cards.add(new SetCardInfo("Adamant Will", 2, Rarity.COMMON, mage.cards.a.AdamantWill.class));
        cards.add(new SetCardInfo("Adeliz, the Cinder Wind", 190, Rarity.UNCOMMON, mage.cards.a.AdelizTheCinderWind.class));
        cards.add(new SetCardInfo("Adventurous Impulse", 153, Rarity.COMMON, mage.cards.a.AdventurousImpulse.class));
        cards.add(new SetCardInfo("Aesthir Glider", 209, Rarity.COMMON, mage.cards.a.AesthirGlider.class));
        cards.add(new SetCardInfo("Amaranthine Wall", 210, Rarity.UNCOMMON, mage.cards.a.AmaranthineWall.class));
        cards.add(new SetCardInfo("Ancient Animus", 154, Rarity.COMMON, mage.cards.a.AncientAnimus.class));
        cards.add(new SetCardInfo("Arbor Armament", 155, Rarity.COMMON, mage.cards.a.ArborArmament.class));
        cards.add(new SetCardInfo("Arcane Flight", 43, Rarity.COMMON, mage.cards.a.ArcaneFlight.class));
        cards.add(new SetCardInfo("Artificer's Assistant", 44, Rarity.COMMON, mage.cards.a.ArtificersAssistant.class));
        cards.add(new SetCardInfo("Arvad the Cursed", 191, Rarity.UNCOMMON, mage.cards.a.ArvadTheCursed.class));
        cards.add(new SetCardInfo("Aryel, Knight of Windgrace", 192, Rarity.RARE, mage.cards.a.AryelKnightOfWindgrace.class));
        cards.add(new SetCardInfo("Aven Sentry", 3, Rarity.COMMON, mage.cards.a.AvenSentry.class));
        cards.add(new SetCardInfo("Baird, Steward of Argive", 4, Rarity.UNCOMMON, mage.cards.b.BairdStewardOfArgive.class));
        cards.add(new SetCardInfo("Baloth Gorger", 156, Rarity.COMMON, mage.cards.b.BalothGorger.class));
        cards.add(new SetCardInfo("Befuddle", 45, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Benalish Honor Guard", 5, Rarity.COMMON, mage.cards.b.BenalishHonorGuard.class));
        cards.add(new SetCardInfo("Benalish Marshal", 6, Rarity.RARE, mage.cards.b.BenalishMarshal.class));
        cards.add(new SetCardInfo("Blackblade Reforged", 211, Rarity.RARE, mage.cards.b.BlackbladeReforged.class));
        cards.add(new SetCardInfo("Blessed Light", 7, Rarity.COMMON, mage.cards.b.BlessedLight.class));
        cards.add(new SetCardInfo("Blessing of Belzenlok", 77, Rarity.COMMON, mage.cards.b.BlessingOfBelzenlok.class));
        cards.add(new SetCardInfo("Blink of an Eye", 46, Rarity.COMMON, mage.cards.b.BlinkOfAnEye.class));
        cards.add(new SetCardInfo("Bloodstone Goblin", 115, Rarity.COMMON, mage.cards.b.BloodstoneGoblin.class));
        cards.add(new SetCardInfo("Bloodtallow Candle", 212, Rarity.COMMON, mage.cards.b.BloodtallowCandle.class));
        cards.add(new SetCardInfo("Board the Weatherlight", 8, Rarity.UNCOMMON, mage.cards.b.BoardTheWeatherlight.class));
        cards.add(new SetCardInfo("Broken Bond", 157, Rarity.COMMON, mage.cards.b.BrokenBond.class));
        cards.add(new SetCardInfo("Cabal Evangel", 78, Rarity.COMMON, mage.cards.c.CabalEvangel.class));
        cards.add(new SetCardInfo("Cabal Paladin", 79, Rarity.COMMON, mage.cards.c.CabalPaladin.class));
        cards.add(new SetCardInfo("Cabal Stronghold", 238, Rarity.RARE, mage.cards.c.CabalStronghold.class));
        cards.add(new SetCardInfo("Caligo Skin-Witch", 80, Rarity.COMMON, mage.cards.c.CaligoSkinWitch.class));
        cards.add(new SetCardInfo("Call the Cavalry", 9, Rarity.COMMON, mage.cards.c.CallTheCavalry.class));
        cards.add(new SetCardInfo("Cast Down", 81, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Chainer's Torment", 82, Rarity.UNCOMMON, mage.cards.c.ChainersTorment.class));
        cards.add(new SetCardInfo("Champion of the Flame", 116, Rarity.UNCOMMON, mage.cards.c.ChampionOfTheFlame.class));
        cards.add(new SetCardInfo("Chandra's Outburst", 276, Rarity.RARE, mage.cards.c.ChandrasOutburst.class));
        cards.add(new SetCardInfo("Chandra, Bold Pyromancer", 275, Rarity.MYTHIC, mage.cards.c.ChandraBoldPyromancer.class));
        cards.add(new SetCardInfo("Charge", 10, Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 239, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Cloudreader Sphinx", 47, Rarity.COMMON, mage.cards.c.CloudreaderSphinx.class));
        cards.add(new SetCardInfo("Cold-Water Snapper", 48, Rarity.COMMON, mage.cards.c.ColdWaterSnapper.class));
        cards.add(new SetCardInfo("Curator's Ward", 49, Rarity.UNCOMMON, mage.cards.c.CuratorsWard.class));
        cards.add(new SetCardInfo("Corrosive Ooze", 158, Rarity.COMMON, mage.cards.c.CorrosiveOoze.class));
        cards.add(new SetCardInfo("D'Avenant Trapper", 11, Rarity.COMMON, mage.cards.d.DAvenantTrapper.class));
        cards.add(new SetCardInfo("Damping Sphere", 213, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", 12, Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Darigaaz Reincarnated", 193, Rarity.MYTHIC, mage.cards.d.DarigaazReincarnated.class));
        cards.add(new SetCardInfo("Daring Archaeologist", 13, Rarity.RARE, mage.cards.d.DaringArchaeologist.class));
        cards.add(new SetCardInfo("Dark Bargain", 83, Rarity.COMMON, mage.cards.d.DarkBargain.class));
        cards.add(new SetCardInfo("Dauntless Bodyguard", 14, Rarity.UNCOMMON, mage.cards.d.DauntlessBodyguard.class));
        cards.add(new SetCardInfo("Deathbloom Thallid", 84, Rarity.COMMON, mage.cards.d.DeathbloomThallid.class));
        cards.add(new SetCardInfo("Deep Freeze", 50, Rarity.COMMON, mage.cards.d.DeepFreeze.class));
        cards.add(new SetCardInfo("Demonic Vigor", 85, Rarity.COMMON, mage.cards.d.DemonicVigor.class));
        cards.add(new SetCardInfo("Demonlord Belzenlok", 86, Rarity.MYTHIC, mage.cards.d.DemonlordBelzenlok.class));
        cards.add(new SetCardInfo("Diligent Excavator", 51, Rarity.UNCOMMON, mage.cards.d.DiligentExcavator.class));
        cards.add(new SetCardInfo("Divest", 87, Rarity.COMMON, mage.cards.d.Divest.class));
        cards.add(new SetCardInfo("Divination", 52, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Dread Shade", 88, Rarity.RARE, mage.cards.d.DreadShade.class));
        cards.add(new SetCardInfo("Drudge Sentinel", 89, Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dub", 15, Rarity.COMMON, mage.cards.d.Dub.class));
        cards.add(new SetCardInfo("Elfhame Druid", 159, Rarity.UNCOMMON, mage.cards.e.ElfhameDruid.class));
        cards.add(new SetCardInfo("Eviscerate", 91, Rarity.COMMON, mage.cards.e.Eviscerate.class));
        cards.add(new SetCardInfo("Evra, Halcyon Witness", 16, Rarity.RARE, mage.cards.e.EvraHalcyonWitness.class));
        cards.add(new SetCardInfo("Excavation Elephant", 17, Rarity.COMMON, mage.cards.e.ExcavationElephant.class));
        cards.add(new SetCardInfo("Fall of the Thran", 18, Rarity.RARE, mage.cards.f.FallOfTheThran.class));
        cards.add(new SetCardInfo("Feral Abomination", 92, Rarity.COMMON, mage.cards.f.FeralAbomination.class));
        cards.add(new SetCardInfo("Fervent Strike", 117, Rarity.COMMON, mage.cards.f.FerventStrike.class));
        cards.add(new SetCardInfo("Fiery Intervention", 118, Rarity.COMMON, mage.cards.f.FieryIntervention.class));
        cards.add(new SetCardInfo("Fight with Fire", 119, Rarity.UNCOMMON, mage.cards.f.FightWithFire.class));
        cards.add(new SetCardInfo("Final Parting", 93, Rarity.UNCOMMON, mage.cards.f.FinalParting.class));
        cards.add(new SetCardInfo("Fire Elemental", 120, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Firefist Adept", 121, Rarity.UNCOMMON, mage.cards.f.FirefistAdept.class));
        cards.add(new SetCardInfo("Firesong and Sunspeaker", 280, Rarity.RARE, mage.cards.f.FiresongAndSunspeaker.class));
        cards.add(new SetCardInfo("Forebear's Blade", 214, Rarity.RARE, mage.cards.f.ForebearsBlade.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Rage", 124, Rarity.COMMON, mage.cards.f.FrenziedRage.class));
        cards.add(new SetCardInfo("Fungal Infection", 94, Rarity.COMMON, mage.cards.f.FungalInfection.class));
        cards.add(new SetCardInfo("Fungal Plots", 160, Rarity.UNCOMMON, mage.cards.f.FungalPlots.class));
        cards.add(new SetCardInfo("Gaea's Blessing", 161, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Gaea's Protector", 162, Rarity.COMMON, mage.cards.g.GaeasProtector.class));
        cards.add(new SetCardInfo("Garna, the Bloodflame", 194, Rarity.UNCOMMON, mage.cards.g.GarnaTheBloodflame.class));
        cards.add(new SetCardInfo("Ghitu Chronicler", 125, Rarity.COMMON, mage.cards.g.GhituChronicler.class));
        cards.add(new SetCardInfo("Ghitu Journeymage", 126, Rarity.COMMON, mage.cards.g.GhituJourneymage.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 127, Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Gideon's Reproach", 19, Rarity.COMMON, mage.cards.g.GideonsReproach.class));
        cards.add(new SetCardInfo("Gift of Growth", 163, Rarity.COMMON, mage.cards.g.GiftOfGrowth.class));
        cards.add(new SetCardInfo("Gilded Lotus", 215, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goblin Barrage", 128, Rarity.UNCOMMON, mage.cards.g.GoblinBarrage.class));
        cards.add(new SetCardInfo("Goblin Chainwhirler", 129, Rarity.RARE, mage.cards.g.GoblinChainwhirler.class));
        cards.add(new SetCardInfo("Goblin Warchief", 130, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Grand Warlord Radha", 195, Rarity.RARE, mage.cards.g.GrandWarlordRadha.class));
        cards.add(new SetCardInfo("Grow from the Ashes", 164, Rarity.COMMON, mage.cards.g.GrowFromTheAshes.class));
        cards.add(new SetCardInfo("Grunn, the Lonely King", 165, Rarity.UNCOMMON, mage.cards.g.GrunnTheLonelyKing.class));
        cards.add(new SetCardInfo("Guardians of Koilos", 216, Rarity.COMMON, mage.cards.g.GuardiansOfKoilos.class));
        cards.add(new SetCardInfo("Hallar, the Firefletcher", 196, Rarity.UNCOMMON, mage.cards.h.HallarTheFirefletcher.class));
        cards.add(new SetCardInfo("Haphazard Bombardment", 131, Rarity.RARE, mage.cards.h.HaphazardBombardment.class));
        cards.add(new SetCardInfo("Healing Grace", 20, Rarity.COMMON, mage.cards.h.HealingGrace.class));
        cards.add(new SetCardInfo("Helm of the Host", 217, Rarity.RARE, mage.cards.h.HelmOfTheHost.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 240, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("History of Benalia", 21, Rarity.MYTHIC, mage.cards.h.HistoryOfBenalia.class));
        cards.add(new SetCardInfo("Homarid Explorer", 53, Rarity.COMMON, mage.cards.h.HomaridExplorer.class));
        cards.add(new SetCardInfo("Howling Golem", 218, Rarity.UNCOMMON, mage.cards.h.HowlingGolem.class));
        cards.add(new SetCardInfo("Icy Manipulator", 219, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("In Bolas's Clutches", 54, Rarity.UNCOMMON, mage.cards.i.InBolassClutches.class));
        cards.add(new SetCardInfo("Invoke the Divine", 22, Rarity.COMMON, mage.cards.i.InvokeTheDivine.class));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", 241, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Jaya Ballard", 132, Rarity.MYTHIC, mage.cards.j.JayaBallard.class));
        cards.add(new SetCardInfo("Jaya's Immolating Inferno", 133, Rarity.RARE, mage.cards.j.JayasImmolatingInferno.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Captain", 197, Rarity.MYTHIC, mage.cards.j.JhoiraWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Jhoira's Familiar", 220, Rarity.UNCOMMON, mage.cards.j.JhoirasFamiliar.class));
        cards.add(new SetCardInfo("Jodah, Archmage Eternal", 198, Rarity.RARE, mage.cards.j.JodahArchmageEternal.class));
        cards.add(new SetCardInfo("Josu Vess, Lich Knight", 95, Rarity.RARE, mage.cards.j.JosuVessLichKnight.class));
        cards.add(new SetCardInfo("Jousting Lance", 221, Rarity.COMMON, mage.cards.j.JoustingLance.class));
        cards.add(new SetCardInfo("Juggernaut", 222, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kamahl's Druidic Vow", 166, Rarity.RARE, mage.cards.k.KamahlsDruidicVow.class));
        cards.add(new SetCardInfo("Karn, Scion of Urza", 1, Rarity.MYTHIC, mage.cards.k.KarnScionOfUrza.class));
        cards.add(new SetCardInfo("Karn's Temporal Sundering", 55, Rarity.RARE, mage.cards.k.KarnsTemporalSundering.class));
        cards.add(new SetCardInfo("Karplusan Hound", 277, Rarity.UNCOMMON, mage.cards.k.KarplusanHound.class));
        cards.add(new SetCardInfo("Kazarov, Sengir Pureblood", 96, Rarity.RARE, mage.cards.k.KazarovSengirPureblood.class));
        cards.add(new SetCardInfo("Keldon Overseer", 134, Rarity.COMMON, mage.cards.k.KeldonOverseer.class));
        cards.add(new SetCardInfo("Keldon Raider", 135, Rarity.COMMON, mage.cards.k.KeldonRaider.class));
        cards.add(new SetCardInfo("Keldon Warcaller", 136, Rarity.COMMON, mage.cards.k.KeldonWarcaller.class));
        cards.add(new SetCardInfo("Knight of Grace", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfGrace.class));
        cards.add(new SetCardInfo("Knight of Malice", 97, Rarity.UNCOMMON, mage.cards.k.KnightOfMalice.class));
        cards.add(new SetCardInfo("Knight of New Benalia", 24, Rarity.COMMON, mage.cards.k.KnightOfNewBenalia.class));
        cards.add(new SetCardInfo("Krosan Druid", 167, Rarity.COMMON, mage.cards.k.KrosanDruid.class));
        cards.add(new SetCardInfo("Kwende, Pride of Femeref", 25, Rarity.UNCOMMON, mage.cards.k.KwendePrideOfFemeref.class));
        cards.add(new SetCardInfo("Lich's Mastery", 98, Rarity.RARE, mage.cards.l.LichsMastery.class));
        cards.add(new SetCardInfo("Lingering Phantom", 99, Rarity.UNCOMMON, mage.cards.l.LingeringPhantom.class));
        cards.add(new SetCardInfo("Llanowar Elves", 168, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Llanowar Envoy", 169, Rarity.COMMON, mage.cards.l.LlanowarEnvoy.class));
        cards.add(new SetCardInfo("Llanowar Scout", 170, Rarity.COMMON, mage.cards.l.LlanowarScout.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 26, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class));
        cards.add(new SetCardInfo("Mammoth Spider", 171, Rarity.COMMON, mage.cards.m.MammothSpider.class));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", 172, Rarity.RARE, mage.cards.m.MarwynTheNurturer.class));
        cards.add(new SetCardInfo("Meandering River", 274, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Memorial to Folly", 242, Rarity.UNCOMMON, mage.cards.m.MemorialToFolly.class));
        cards.add(new SetCardInfo("Memorial to Genius", 243, Rarity.UNCOMMON, mage.cards.m.MemorialToGenius.class));
        cards.add(new SetCardInfo("Memorial to Glory", 244, Rarity.UNCOMMON, mage.cards.m.MemorialToGlory.class));
        cards.add(new SetCardInfo("Memorial to Unity", 245, Rarity.UNCOMMON, mage.cards.m.MemorialToUnity.class));
        cards.add(new SetCardInfo("Memorial to War", 246, Rarity.UNCOMMON, mage.cards.m.MemorialToWar.class));
        cards.add(new SetCardInfo("Merfolk Trickster", 56, Rarity.UNCOMMON, mage.cards.m.MerfolkTrickster.class));
        cards.add(new SetCardInfo("Mesa Unicorn", 27, Rarity.COMMON, mage.cards.m.MesaUnicorn.class));
        cards.add(new SetCardInfo("Mishra's Self-Replicator", 223, Rarity.RARE, mage.cards.m.MishrasSelfReplicator.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muldrotha, the Gravetide", 199, Rarity.MYTHIC, mage.cards.m.MuldrothaTheGravetide.class));
        cards.add(new SetCardInfo("Multani, Yavimaya's Avatar", 174, Rarity.MYTHIC, mage.cards.m.MultaniYavimayasAvatar.class));
        cards.add(new SetCardInfo("Mox Amber", 224, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Naban, Dean of Iteration", 58, Rarity.RARE, mage.cards.n.NabanDeanOfIteration.class));
        cards.add(new SetCardInfo("Naru Meha, Master Wizard", 59, Rarity.MYTHIC, mage.cards.n.NaruMehaMasterWizard.class));
        cards.add(new SetCardInfo("Nature's Spiral", 175, Rarity.UNCOMMON, mage.cards.n.NaturesSpiral.class));
        cards.add(new SetCardInfo("Navigator's Compass", 225, Rarity.COMMON, mage.cards.n.NavigatorsCompass.class));
        cards.add(new SetCardInfo("Niambi, Faithful Healer", 272, Rarity.RARE, mage.cards.n.NiambiFaithfulHealer.class));
        cards.add(new SetCardInfo("Oath of Teferi", 200, Rarity.RARE, mage.cards.o.OathOfTeferi.class));
        cards.add(new SetCardInfo("On Serra's Wings", 28, Rarity.UNCOMMON, mage.cards.o.OnSerrasWings.class));
        cards.add(new SetCardInfo("Opt", 60, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Orcish Vandal", 137, Rarity.UNCOMMON, mage.cards.o.OrcishVandal.class));
        cards.add(new SetCardInfo("Pardic Wanderer", 226, Rarity.COMMON, mage.cards.p.PardicWanderer.class));
        cards.add(new SetCardInfo("Pegasus Courser", 29, Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Phyrexian Scriptures", 100, Rarity.MYTHIC, mage.cards.p.PhyrexianScriptures.class));
        cards.add(new SetCardInfo("Pierce the Sky", 176, Rarity.COMMON, mage.cards.p.PierceTheSky.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Powerstone Shard", 227, Rarity.COMMON, mage.cards.p.PowerstoneShard.class));
        cards.add(new SetCardInfo("Precognition Field", 61, Rarity.RARE, mage.cards.p.PrecognitionField.class));
        cards.add(new SetCardInfo("Primevals' Glorious Rebirth", 201, Rarity.RARE, mage.cards.p.PrimevalsGloriousRebirth.class));
        cards.add(new SetCardInfo("Primordial Wurm", 177, Rarity.COMMON, mage.cards.p.PrimordialWurm.class));
        cards.add(new SetCardInfo("Pyromantic Pilgrim", 278, Rarity.COMMON, mage.cards.p.PyromanticPilgrim.class));
        cards.add(new SetCardInfo("Radiating Lightning", 138, Rarity.COMMON, mage.cards.r.RadiatingLightning.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", 202, Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Rampaging Cyclops", 139, Rarity.COMMON, mage.cards.r.RampagingCyclops.class));
        cards.add(new SetCardInfo("Rat Colony", 101, Rarity.COMMON, mage.cards.r.RatColony.class));
        cards.add(new SetCardInfo("Relic Runner", 62, Rarity.COMMON, mage.cards.r.RelicRunner.class));
        cards.add(new SetCardInfo("Rescue", 63, Rarity.COMMON, mage.cards.r.Rescue.class));
        cards.add(new SetCardInfo("Rite of Belzenlok", 102, Rarity.RARE, mage.cards.r.RiteOfBelzenlok.class));
        cards.add(new SetCardInfo("Rona, Disciple of Gix", 203, Rarity.UNCOMMON, mage.cards.r.RonaDiscipleOfGix.class));
        cards.add(new SetCardInfo("Run Amok", 140, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 64, Rarity.UNCOMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Sanctum Spirit", 30, Rarity.UNCOMMON, mage.cards.s.SanctumSpirit.class));
        cards.add(new SetCardInfo("Saproling Migration", 178, Rarity.COMMON, mage.cards.s.SaprolingMigration.class));
        cards.add(new SetCardInfo("Seal Away", 31, Rarity.UNCOMMON, mage.cards.s.SealAway.class));
        cards.add(new SetCardInfo("Seismic Shift", 141, Rarity.COMMON, mage.cards.s.SeismicShift.class));
        cards.add(new SetCardInfo("Sentinel of the Pearl Trident", 65, Rarity.UNCOMMON, mage.cards.s.SentinelOfThePearlTrident.class));
        cards.add(new SetCardInfo("Sergeant-at-Arms", 32, Rarity.COMMON, mage.cards.s.SergeantAtArms.class));
        cards.add(new SetCardInfo("Serra Angel", 33, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra Disciple", 34, Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Settle the Score", 103, Rarity.UNCOMMON, mage.cards.s.SettleTheScore.class));
        cards.add(new SetCardInfo("Shalai, Voice of Plenty", 35, Rarity.RARE, mage.cards.s.ShalaiVoiceOfPlenty.class));
        cards.add(new SetCardInfo("Shanna, Sisay's Legacy", 204, Rarity.UNCOMMON, mage.cards.s.ShannaSisaysLegacy.class));
        cards.add(new SetCardInfo("Shield of the Realm", 228, Rarity.UNCOMMON, mage.cards.s.ShieldOfTheRealm.class));
        cards.add(new SetCardInfo("Shivan Fire", 142, Rarity.COMMON, mage.cards.s.ShivanFire.class));
        cards.add(new SetCardInfo("Short Sword", 229, Rarity.COMMON, mage.cards.s.ShortSword.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 143, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Skirk Prospector", 144, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skittering Surveyor", 230, Rarity.COMMON, mage.cards.s.SkitteringSurveyor.class));
        cards.add(new SetCardInfo("Skizzik", 145, Rarity.UNCOMMON, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Slimefoot, the Stowaway", 205, Rarity.UNCOMMON, mage.cards.s.SlimefootTheStowaway.class));
        cards.add(new SetCardInfo("Slinn Voda, the Rising Deep", 66, Rarity.UNCOMMON, mage.cards.s.SlinnVodaTheRisingDeep.class));
        cards.add(new SetCardInfo("Song of Freyalise", 179, Rarity.UNCOMMON, mage.cards.s.SongOfFreyalise.class));
        cards.add(new SetCardInfo("Sorcerer's Wand", 231, Rarity.UNCOMMON, mage.cards.s.SorcerersWand.class));
        cards.add(new SetCardInfo("Soul Salvage", 104, Rarity.COMMON, mage.cards.s.SoulSalvage.class));
        cards.add(new SetCardInfo("Sparring Construct", 232, Rarity.COMMON, mage.cards.s.SparringConstruct.class));
        cards.add(new SetCardInfo("Spore Swarm", 180, Rarity.UNCOMMON, mage.cards.s.SporeSwarm.class));
        cards.add(new SetCardInfo("Sporecrown Thallid", 181, Rarity.UNCOMMON, mage.cards.s.SporecrownThallid.class));
        cards.add(new SetCardInfo("Squee, the Immortal", 146, Rarity.RARE, mage.cards.s.SqueeTheImmortal.class));
        cards.add(new SetCardInfo("Steel Leaf Champion", 182, Rarity.RARE, mage.cards.s.SteelLeafChampion.class));
        cards.add(new SetCardInfo("Stronghold Confessor", 105, Rarity.COMMON, mage.cards.s.StrongholdConfessor.class));
        cards.add(new SetCardInfo("Sulfur Falls", 247, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Awakening", 183, Rarity.RARE, mage.cards.s.SylvanAwakening.class));
        cards.add(new SetCardInfo("Syncopate", 67, Rarity.COMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 206, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Teferi's Sentinel", 273, Rarity.UNCOMMON, mage.cards.t.TeferisSentinel.class));
        cards.add(new SetCardInfo("Teferi, Hero of Dominaria", 207, Rarity.MYTHIC, mage.cards.t.TeferiHeroOfDominaria.class));
        cards.add(new SetCardInfo("Teferi, Timebender", 270, Rarity.MYTHIC, mage.cards.t.TeferiTimebender.class));
        cards.add(new SetCardInfo("Tempest Djinn", 68, Rarity.RARE, mage.cards.t.TempestDjinn.class));
        cards.add(new SetCardInfo("Temporal Machinations", 271, Rarity.COMMON, mage.cards.t.TemporalMachinations.class));
        cards.add(new SetCardInfo("Territorial Allosaurus", 184, Rarity.RARE, mage.cards.t.TerritorialAllosaurus.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", 36, Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Tetsuko Umezawa, Fugitive", 69, Rarity.UNCOMMON, mage.cards.t.TetsukoUmezawaFugitive.class));
        cards.add(new SetCardInfo("Thallid Omnivore", 106, Rarity.COMMON, mage.cards.t.ThallidOmnivore.class));
        cards.add(new SetCardInfo("Thallid Soothsayer", 107, Rarity.UNCOMMON, mage.cards.t.ThallidSoothsayer.class));
        cards.add(new SetCardInfo("The Antiquities War", 42, Rarity.RARE, mage.cards.t.TheAntiquitiesWar.class));
        cards.add(new SetCardInfo("The Eldest Reborn", 90, Rarity.UNCOMMON, mage.cards.t.TheEldestReborn.class));
        cards.add(new SetCardInfo("The First Eruption", 122, Rarity.RARE, mage.cards.t.TheFirstEruption.class));
        cards.add(new SetCardInfo("The Flame of Keld", 123, Rarity.UNCOMMON, mage.cards.t.TheFlameOfKeld.class));
        cards.add(new SetCardInfo("The Mending of Dominaria", 173, Rarity.RARE, mage.cards.t.TheMendingOfDominaria.class));
        cards.add(new SetCardInfo("The Mirari Conjecture", 57, Rarity.RARE, mage.cards.t.TheMirariConjecture.class));
        cards.add(new SetCardInfo("Thorn Elemental", 185, Rarity.UNCOMMON, mage.cards.t.ThornElemental.class));
        cards.add(new SetCardInfo("Thran Temporal Gateway", 233, Rarity.RARE, mage.cards.t.ThranTemporalGateway.class));
        cards.add(new SetCardInfo("Tiana, Ship's Caretaker", 208, Rarity.UNCOMMON, mage.cards.t.TianaShipsCaretaker.class));
        cards.add(new SetCardInfo("Timber Gorge", 279, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Time of Ice", 70, Rarity.UNCOMMON, mage.cards.t.TimeOfIce.class));
        cards.add(new SetCardInfo("Tolarian Scholar", 71, Rarity.COMMON, mage.cards.t.TolarianScholar.class));
        cards.add(new SetCardInfo("Torgaar, Famine Incarnate", 108, Rarity.RARE, mage.cards.t.TorgaarFamineIncarnate.class));
        cards.add(new SetCardInfo("Tragic Poet", 37, Rarity.COMMON, mage.cards.t.TragicPoet.class));
        cards.add(new SetCardInfo("Traxos, Scourge of Kroog", 234, Rarity.RARE, mage.cards.t.TraxosScourgeOfKroog.class));
        cards.add(new SetCardInfo("Triumph of Gerrard", 38, Rarity.UNCOMMON, mage.cards.t.TriumphOfGerrard.class));
        cards.add(new SetCardInfo("Two-Headed Giant", 147, Rarity.RARE, mage.cards.t.TwoHeadedGiant.class));
        cards.add(new SetCardInfo("Untamed Kavu", 186, Rarity.UNCOMMON, mage.cards.u.UntamedKavu.class));
        cards.add(new SetCardInfo("Unwind", 72, Rarity.COMMON, mage.cards.u.Unwind.class));
        cards.add(new SetCardInfo("Urgoros, the Empty One", 109, Rarity.UNCOMMON, mage.cards.u.UrgorosTheEmptyOne.class));
        cards.add(new SetCardInfo("Urza's Ruinous Blast", 39, Rarity.RARE, mage.cards.u.UrzasRuinousBlast.class));
        cards.add(new SetCardInfo("Urza's Tome", 235, Rarity.UNCOMMON, mage.cards.u.UrzasTome.class));
        cards.add(new SetCardInfo("Valduk, Keeper of the Flame", 148, Rarity.UNCOMMON, mage.cards.v.ValdukKeeperOfTheFlame.class));
        cards.add(new SetCardInfo("Verdant Force", 187, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Verix Bladewing", 149, Rarity.MYTHIC, mage.cards.v.VerixBladewing.class));
        cards.add(new SetCardInfo("Vicious Offering", 110, Rarity.COMMON, mage.cards.v.ViciousOffering.class));
        cards.add(new SetCardInfo("Vodalian Arcanist", 73, Rarity.COMMON, mage.cards.v.VodalianArcanist.class));
        cards.add(new SetCardInfo("Voltaic Servant", 236, Rarity.COMMON, mage.cards.v.VoltaicServant.class));
        cards.add(new SetCardInfo("Warcry Phoenix", 150, Rarity.UNCOMMON, mage.cards.w.WarcryPhoenix.class));
        cards.add(new SetCardInfo("Warlord's Fury", 151, Rarity.COMMON, mage.cards.w.WarlordsFury.class));
        cards.add(new SetCardInfo("Weatherlight", 237, Rarity.MYTHIC, mage.cards.w.Weatherlight.class));
        cards.add(new SetCardInfo("Weight of Memory", 74, Rarity.UNCOMMON, mage.cards.w.WeightOfMemory.class));
        cards.add(new SetCardInfo("Whisper, Blood Liturgist", 111, Rarity.UNCOMMON, mage.cards.w.WhisperBloodLiturgist.class));
        cards.add(new SetCardInfo("Wild Onslaught", 188, Rarity.UNCOMMON, mage.cards.w.WildOnslaught.class));
        cards.add(new SetCardInfo("Windgrace Acolyte", 112, Rarity.COMMON, mage.cards.w.WindgraceAcolyte.class));
        cards.add(new SetCardInfo("Wizard's Lightning", 152, Rarity.UNCOMMON, mage.cards.w.WizardsLightning.class));
        cards.add(new SetCardInfo("Wizard's Retort", 75, Rarity.UNCOMMON, mage.cards.w.WizardsRetort.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 248, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", 113, Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yavimaya Sapherd", 189, Rarity.COMMON, mage.cards.y.YavimayaSapherd.class));
        cards.add(new SetCardInfo("Yawgmoth's Vile Offering", 114, Rarity.RARE, mage.cards.y.YawgmothsVileOffering.class));
        cards.add(new SetCardInfo("Zahid, Djinn of the Lamp", 76, Rarity.RARE, mage.cards.z.ZahidDjinnOfTheLamp.class));
        cards.add(new SetCardInfo("Zhalfirin Void", 249, Rarity.UNCOMMON, mage.cards.z.ZhalfirinVoid.class));
    }

    // for sheet math reasons, four rare legendary creatures are collated as normal rares
    // (a booster containing one of them will always contain an uncommon legend as well)
    private static final List<String> nonSpecialLegends = Arrays.asList("Darigaaz Reincarnated", "Josu Vess, Lich Knight", "Muldrotha, the Gravetide", "Shalai, Voice of Plenty");

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                .setCodes(this.code)
                .rarities(rarity)
                .supertypes(SuperType.LEGENDARY)
                .types(CardType.CREATURE)
                .maxCardNumber(maxCardNumberInBooster)));
        cardInfos.removeIf(cardInfo -> nonSpecialLegends.contains(cardInfo.getName()));
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new DominariaCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/dom.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class DominariaCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "5", "67", "125", "15", "62", "120", "10", "43", "151", "24", "45", "144", "32", "48", "115", "17", "44", "136", "20", "50", "117", "34", "53", "127", "2", "60", "126", "22", "73", "138", "11", "67", "120", "15", "71", "124", "20", "62", "151", "10", "43", "125", "5", "44", "136", "24", "50", "144", "11", "48", "117", "32", "45", "115", "22", "60", "126", "17", "53", "127", "34", "71", "138", "2", "73", "124");
    private final CardRun commonB = new CardRun(true, "79", "153", "87", "164", "77", "163", "104", "171", "89", "169", "80", "162", "112", "155", "83", "167", "92", "169", "101", "170", "105", "176", "87", "164", "77", "153", "104", "158", "89", "171", "79", "162", "80", "163", "83", "170", "105", "167", "101", "155", "112", "176", "92", "158", "87", "170", "79", "153", "104", "164", "77", "169", "112", "162", "83", "167", "89", "171", "92", "163", "80", "155", "105", "176", "101", "158");
    private final CardRun commonC1 = new CardRun(true, "47", "78", "3", "230", "177", "40", "85", "227", "142", "9", "168", "232", "91", "72", "236", "140", "19", "106", "221", "178", "46", "141", "3", "78", "177", "225", "47", "118", "212", "85", "9", "154", "227", "72", "84", "142", "232", "19", "168", "106", "230", "40", "141", "236", "91", "226", "178", "221", "140", "46", "84", "212", "154", "118", "225");
    private final CardRun commonC2 = new CardRun(true, "135", "27", "209", "94", "37", "189", "226", "139", "52", "110", "229", "29", "7", "157", "41", "134", "156", "216", "94", "29", "135", "157", "52", "27", "209", "139", "63", "7", "189", "134", "229", "110", "37", "41", "156", "216", "135", "29", "94", "63", "189", "209", "139", "157", "27", "52", "229", "7", "134", "41", "37", "110", "216", "156", "63");
    private final CardRun uncommonA = new CardRun(true, "235", "119", "179", "145", "99", "38", "219", "175", "243", "33", "116", "210", "70", "160", "152", "81", "23", "235", "186", "242", "14", "130", "210", "54", "159", "121", "90", "38", "219", "180", "245", "23", "145", "179", "65", "186", "116", "82", "75", "121", "175", "99", "14", "119", "235", "70", "159", "130", "81", "54", "243", "160", "33", "82", "152", "210", "65", "180", "145", "90", "75", "242", "159", "99", "245", "116", "219", "70", "175", "119", "81", "65", "243", "186", "82", "38", "130", "235", "75", "160", "33", "121", "70", "152", "179", "90", "23", "145", "180", "54", "159", "116", "14", "65", "242", "179", "99", "38", "119", "210", "245", "180", "152", "23", "54", "243", "175", "90", "14", "130", "219", "242", "160", "121", "82", "75", "245", "186", "81", "33");
    private final CardRun uncommonB = new CardRun(true, "231", "103", "188", "31", "64", "222", "249", "161", "28", "185", "150", "213", "97", "8", "181", "49", "220", "246", "188", "228", "56", "123", "218", "93", "185", "30", "74", "231", "244", "181", "31", "64", "128", "222", "107", "161", "137", "51", "213", "249", "103", "28", "49", "150", "220", "97", "188", "123", "56", "228", "137", "246", "30", "64", "244", "218", "107", "161", "128", "74", "213", "249", "93", "31", "51", "150", "222", "103", "185", "123", "56", "231", "97", "8", "220", "74", "137", "228", "107", "181", "150", "49", "218", "244", "93", "28", "51", "128", "213", "188", "8", "137", "64", "222", "246", "97", "30", "56", "31", "231", "103", "28", "123", "74", "220", "249", "161", "107", "246", "51", "228", "185", "8", "128", "49", "218", "244", "93", "30", "181");
    private final CardRun uncommonLegend = new CardRun(true, "203", "109", "204", "66", "191", "4", "205", "111", "206", "12", "196", "165", "202", "69", "203", "113", "190", "109", "208", "25", "194", "148", "196", "12", "204", "66", "208", "111", "191", "165", "202", "4", "205", "113", "194", "148", "206", "25", "190", "69");
    private final CardRun rare = new CardRun(false, "6", "13", "18", "35", "39", "42", "55", "57", "61", "68", "88", "95", "98", "102", "114", "122", "129", "131", "133", "143", "147", "166", "173", "182", "183", "184", "187", "200", "201", "211", "214", "215", "217", "223", "233", "238", "239", "240", "241", "247", "248", "6", "13", "18", "35", "39", "42", "55", "57", "61", "68", "88", "95", "98", "102", "114", "122", "129", "131", "133", "143", "147", "166", "173", "182", "183", "184", "187", "200", "201", "211", "214", "215", "217", "223", "233", "238", "239", "240", "241", "247", "248", "1", "21", "100", "132", "193", "199", "207", "224", "237");
    private final CardRun rareLegend = new CardRun(false, "16", "36", "58", "76", "96", "108", "146", "172", "192", "195", "198", "234", "16", "36", "58", "76", "96", "108", "146", "172", "192", "195", "198", "234", "26", "59", "86", "149", "174", "197");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure ABL = new BoosterStructure(uncommonA, uncommonB, uncommonLegend, rare);
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB, rareLegend);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB, rareLegend);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.125 A uncommons     (9 / 8)
    // 1.125 B uncommons     (9 / 8)
    // 0.75  uncommon legend (6 / 8)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABL, ABL, ABL, ABL, ABL, ABL,
            AAB, ABB
    );
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
