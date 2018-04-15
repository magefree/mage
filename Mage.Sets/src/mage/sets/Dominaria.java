/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class Dominaria extends ExpansionSet {

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
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Academy Drake", 40, Rarity.UNCOMMON, mage.cards.a.AcademyDrake.class));
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
        cards.add(new SetCardInfo("Aven Sentry", 3, Rarity.COMMON, mage.cards.a.AvenSentry.class));
        cards.add(new SetCardInfo("Baird, Steward of Argive", 4, Rarity.UNCOMMON, mage.cards.b.BairdStewardOfArgive.class));
        cards.add(new SetCardInfo("Baloth Gorger", 156, Rarity.COMMON, mage.cards.b.BalothGorger.class));
        cards.add(new SetCardInfo("Befuddle", 45, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Benalish Honor Guard", 5, Rarity.COMMON, mage.cards.b.BenalishHonorGuard.class));
        cards.add(new SetCardInfo("Benalish Marshal", 6, Rarity.RARE, mage.cards.b.BenalishMarshal.class));
        cards.add(new SetCardInfo("Blackblade Reforged", 211, Rarity.RARE, mage.cards.b.BlackbladeReforged.class));
        cards.add(new SetCardInfo("Blessed Light", 7, Rarity.UNCOMMON, mage.cards.b.BlessedLight.class));
        cards.add(new SetCardInfo("Blessing of Belzenlok", 77, Rarity.COMMON, mage.cards.b.BlessingOfBelzenlok.class));
        cards.add(new SetCardInfo("Blink of an Eye", 46, Rarity.COMMON, mage.cards.b.BlinkOfAnEye.class));
        cards.add(new SetCardInfo("Bloodtallow Candle", 212, Rarity.COMMON, mage.cards.b.BloodtallowCandle.class));
        cards.add(new SetCardInfo("Board the Weatherlight", 8, Rarity.UNCOMMON, mage.cards.b.BoardTheWeatherlight.class));
        cards.add(new SetCardInfo("Broken Bond", 157, Rarity.COMMON, mage.cards.b.BrokenBond.class));
        cards.add(new SetCardInfo("Cabal Evangel", 78, Rarity.COMMON, mage.cards.c.CabalEvangel.class));
        cards.add(new SetCardInfo("Cabal Paladin", 79, Rarity.COMMON, mage.cards.c.CabalPaladin.class));
        cards.add(new SetCardInfo("Cabal Stronghold", 238, Rarity.RARE, mage.cards.c.CabalStronghold.class));
        cards.add(new SetCardInfo("Caligo Skin-Witch", 80, Rarity.COMMON, mage.cards.c.CaligoSkinWitch.class));
        cards.add(new SetCardInfo("Call the Cavalry", 9, Rarity.COMMON, mage.cards.c.CallTheCavalry.class));
        cards.add(new SetCardInfo("Cast Down", 81, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Champion of the Flame", 116, Rarity.UNCOMMON, mage.cards.c.ChampionOfTheFlame.class));
        cards.add(new SetCardInfo("Charge", 10, Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 239, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Cloudreader Sphinx", 47, Rarity.COMMON, mage.cards.c.CloudreaderSphinx.class));
        cards.add(new SetCardInfo("Cold-Water Snapper", 48, Rarity.COMMON, mage.cards.c.ColdWaterSnapper.class));
        cards.add(new SetCardInfo("D'Avenant Trapper", 11, Rarity.COMMON, mage.cards.d.DAvenantTrapper.class));
        cards.add(new SetCardInfo("Damping Sphere", 213, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", 12, Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Daring Archaeologist", 13, Rarity.RARE, mage.cards.d.DaringArchaeologist.class));
        cards.add(new SetCardInfo("Deathbloom Thallid", 84, Rarity.COMMON, mage.cards.d.DeathbloomThallid.class));
        cards.add(new SetCardInfo("Demonic Vigor", 85, Rarity.COMMON, mage.cards.d.DemonicVigor.class));
        cards.add(new SetCardInfo("Diligent Excavator", 51, Rarity.UNCOMMON, mage.cards.d.DiligentExcavator.class));
        cards.add(new SetCardInfo("Divest", 87, Rarity.COMMON, mage.cards.d.Divest.class));
        cards.add(new SetCardInfo("Divination", 52, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Dread Shade", 88, Rarity.RARE, mage.cards.d.DreadShade.class));
        cards.add(new SetCardInfo("Drudge Sentinel", 89, Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dub", 15, Rarity.SPECIAL, mage.cards.d.Dub.class));
        cards.add(new SetCardInfo("Eviscerate", 91, Rarity.COMMON, mage.cards.e.Eviscerate.class));
        cards.add(new SetCardInfo("Excavation Elephant", 17, Rarity.COMMON, mage.cards.e.ExcavationElephant.class));
        cards.add(new SetCardInfo("Fall of the Thran", 18, Rarity.RARE, mage.cards.f.FallOfTheThran.class));
        cards.add(new SetCardInfo("Feral Abomination", 92, Rarity.COMMON, mage.cards.f.FeralAbomination.class));
        cards.add(new SetCardInfo("Fervent Strike", 117, Rarity.COMMON, mage.cards.f.FerventStrike.class));
        cards.add(new SetCardInfo("Fiery Intervention", 118, Rarity.COMMON, mage.cards.f.FieryIntervention.class));
        cards.add(new SetCardInfo("Fire Elemental", 120, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Firefist Adept", 121, Rarity.UNCOMMON, mage.cards.f.FirefistAdept.class));
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
        cards.add(new SetCardInfo("Ghitu Chronicler", 125, Rarity.COMMON, mage.cards.g.GhituChronicler.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 127, Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Gideon's Reproach", 19, Rarity.COMMON, mage.cards.g.GideonsReproach.class));
        cards.add(new SetCardInfo("Gilded Lotus", 215, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goblin Chainwhirler", 129, Rarity.RARE, mage.cards.g.GoblinChainwhirler.class));
        cards.add(new SetCardInfo("Goblin Warchief", 130, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Grunn, the Lonely King", 165, Rarity.UNCOMMON, mage.cards.g.GrunnTheLonelyKing.class));
        cards.add(new SetCardInfo("Guardians of Koilos", 216, Rarity.COMMON, mage.cards.g.GuardiansOfKoilos.class));
        cards.add(new SetCardInfo("Helm of the Host", 217, Rarity.RARE, mage.cards.h.HelmOfTheHost.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 240, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("History of Benalia", 21, Rarity.MYTHIC, mage.cards.h.HistoryOfBenalia.class));
        cards.add(new SetCardInfo("Homarid Explorer", 53, Rarity.UNCOMMON, mage.cards.h.HomaridExplorer.class));
        cards.add(new SetCardInfo("Howling Golem", 218, Rarity.UNCOMMON, mage.cards.h.HowlingGolem.class));
        cards.add(new SetCardInfo("Icy Manipulator", 219, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Invoke the Divine", 22, Rarity.COMMON, mage.cards.i.InvokeTheDivine.class));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", 241, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
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
        cards.add(new SetCardInfo("Keldon Overseer", 134, Rarity.COMMON, mage.cards.k.KeldonOverseer.class));
        cards.add(new SetCardInfo("Keldon Raider", 135, Rarity.COMMON, mage.cards.k.KeldonRaider.class));
        cards.add(new SetCardInfo("Keldon Warcaller", 136, Rarity.COMMON, mage.cards.k.KeldonWarcaller.class));
        cards.add(new SetCardInfo("Knight of Grace", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfGrace.class));
        cards.add(new SetCardInfo("Knight of Malice", 97, Rarity.UNCOMMON, mage.cards.k.KnightOfMalice.class));
        cards.add(new SetCardInfo("Knight of New Benalia", 24, Rarity.COMMON, mage.cards.k.KnightOfNewBenalia.class));
        cards.add(new SetCardInfo("Kwende, Pride of Femeref", 25, Rarity.UNCOMMON, mage.cards.k.KwendePrideOfFemeref.class));
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
        cards.add(new SetCardInfo("Mesa Unicorn", 27, Rarity.COMMON, mage.cards.m.MesaUnicorn.class));
        cards.add(new SetCardInfo("Mishra's Self-Replicator", 223, Rarity.RARE, mage.cards.m.MishrasSelfReplicator.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multani, Yavimaya's Avatar", 174, Rarity.MYTHIC, mage.cards.m.MultaniYavimayasAvatar.class));
        cards.add(new SetCardInfo("Mox Amber", 224, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Naban, Dean of Iteration", 58, Rarity.RARE, mage.cards.n.NabanDeanOfIteration.class));
        cards.add(new SetCardInfo("Naru Meha, Master Wizard", 59, Rarity.MYTHIC, mage.cards.n.NaruMehaMasterWizard.class));
        cards.add(new SetCardInfo("Nature's Spiral", 175, Rarity.UNCOMMON, mage.cards.n.NaturesSpiral.class));
        cards.add(new SetCardInfo("Niambi, Faithful Healer", 272, Rarity.RARE, mage.cards.n.NiambiFaithfulHealer.class));
        cards.add(new SetCardInfo("Opt", 60, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Pardic Wanderer", 226, Rarity.COMMON, mage.cards.p.PardicWanderer.class));
        cards.add(new SetCardInfo("Pegasus Courser", 29, Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Pierce the Sky", 176, Rarity.COMMON, mage.cards.p.PierceTheSky.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Powerstone Shard", 227, Rarity.COMMON, mage.cards.p.PowerstoneShard.class));
        cards.add(new SetCardInfo("Primevals' Glorious Rebirth", 201, Rarity.RARE, mage.cards.p.PrimevalsGloriousRebirth.class));
        cards.add(new SetCardInfo("Primordial Wurm", 177, Rarity.COMMON, mage.cards.p.PrimordialWurm.class));
        cards.add(new SetCardInfo("Pyromantic Pilgrim", 278, Rarity.COMMON, mage.cards.p.PyromanticPilgrim.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", 202, Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Rescue", 63, Rarity.COMMON, mage.cards.r.Rescue.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 64, Rarity.UNCOMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Saproling Migration", 178, Rarity.COMMON, mage.cards.s.SaprolingMigration.class));
        cards.add(new SetCardInfo("Seal Away", 31, Rarity.UNCOMMON, mage.cards.s.SealAway.class));
        cards.add(new SetCardInfo("Serra Angel", 33, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra Disciple", 34, Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Shalai, Voice of Plenty", 35, Rarity.RARE, mage.cards.s.ShalaiVoiceOfPlenty.class));
        cards.add(new SetCardInfo("Short Sword", 229, Rarity.COMMON, mage.cards.s.ShortSword.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 143, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Skirk Prospector", 144, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skittering Surveyor", 230, Rarity.COMMON, mage.cards.s.SkitteringSurveyor.class));
        cards.add(new SetCardInfo("Skizzik", 145, Rarity.UNCOMMON, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Soul Salvage", 104, Rarity.COMMON, mage.cards.s.SoulSalvage.class));
        cards.add(new SetCardInfo("Sparring Construct", 232, Rarity.COMMON, mage.cards.s.SparringConstruct.class));
        cards.add(new SetCardInfo("Spore Swarm", 180, Rarity.UNCOMMON, mage.cards.s.SporeSwarm.class));
        cards.add(new SetCardInfo("Sporecrown Thallid", 181, Rarity.UNCOMMON, mage.cards.s.SporecrownThallid.class));
        cards.add(new SetCardInfo("Squee, the Immortal", 146, Rarity.RARE, mage.cards.s.SqueeTheImmortal.class));
        cards.add(new SetCardInfo("Steel Leaf Champion", 182, Rarity.RARE, mage.cards.s.SteelLeafChampion.class));
        cards.add(new SetCardInfo("Sulfur Falls", 247, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syncopate", 67, Rarity.UNCOMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 206, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Teferi's Sentinel", 273, Rarity.UNCOMMON, mage.cards.t.TeferisSentinel.class));
        cards.add(new SetCardInfo("Teferi, Hero of Dominaria", 207, Rarity.MYTHIC, mage.cards.t.TeferiHeroOfDominaria.class));
        cards.add(new SetCardInfo("Teferi, Timebender", 270, Rarity.MYTHIC, mage.cards.t.TeferiTimebender.class));
        cards.add(new SetCardInfo("Tempest Djinn", 68, Rarity.RARE, mage.cards.t.TempestDjinn.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", 36, Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Tetsuko Umezawa, Fugitive", 69, Rarity.UNCOMMON, mage.cards.t.TetsukoUmezawaFugitive.class));
        cards.add(new SetCardInfo("Thallid Soothsayer", 107, Rarity.UNCOMMON, mage.cards.t.ThallidSoothsayer.class));
        cards.add(new SetCardInfo("The Flame of Keld", 123, Rarity.UNCOMMON, mage.cards.t.TheFlameOfKeld.class));
        cards.add(new SetCardInfo("The Mirari Conjecture", 57, Rarity.RARE, mage.cards.t.TheMirariConjecture.class));
        cards.add(new SetCardInfo("Thorn Elemental", 185, Rarity.UNCOMMON, mage.cards.t.ThornElemental.class));
        cards.add(new SetCardInfo("Thran Temporal Gateway", 233, Rarity.RARE, mage.cards.t.ThranTemporalGateway.class));
        cards.add(new SetCardInfo("Timber Gorge", 279, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Tolarian Scholar", 71, Rarity.COMMON, mage.cards.t.TolarianScholar.class));
        cards.add(new SetCardInfo("Tragic Poet", 37, Rarity.COMMON, mage.cards.t.TragicPoet.class));
        cards.add(new SetCardInfo("Traxos, Scourge of Kroog", 234, Rarity.RARE, mage.cards.t.TraxosScourgeOfKroog.class));
        cards.add(new SetCardInfo("Triumph of Gerrard", 38, Rarity.UNCOMMON, mage.cards.t.TriumphOfGerrard.class));
        cards.add(new SetCardInfo("Untamed Kavu", 186, Rarity.UNCOMMON, mage.cards.u.UntamedKavu.class));
        cards.add(new SetCardInfo("Unwind", 72, Rarity.COMMON, mage.cards.u.Unwind.class));
        cards.add(new SetCardInfo("Urza's Ruinous Blast", 39, Rarity.RARE, mage.cards.u.UrzasRuinousBlast.class));
        cards.add(new SetCardInfo("Verdant Force", 187, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Verix Bladewing", 149, Rarity.MYTHIC, mage.cards.v.VerixBladewing.class));
        cards.add(new SetCardInfo("Voltaic Servant", 236, Rarity.COMMON, mage.cards.v.VoltaicServant.class));
        cards.add(new SetCardInfo("Warlord's Fury", 151, Rarity.COMMON, mage.cards.w.WarlordsFury.class));
        cards.add(new SetCardInfo("Weatherlight", 237, Rarity.MYTHIC, mage.cards.w.Weatherlight.class));
        cards.add(new SetCardInfo("Wizard's Lightning", 152, Rarity.UNCOMMON, mage.cards.w.WizardsLightning.class));
        cards.add(new SetCardInfo("Wizard's Retort", 75, Rarity.UNCOMMON, mage.cards.w.WizardsRetort.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 248, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", 113, Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yavimaya Sapherd", 189, Rarity.COMMON, mage.cards.y.YavimayaSapherd.class));
        cards.add(new SetCardInfo("Yawgmoth's Vile Offering", 114, Rarity.RARE, mage.cards.y.YawgmothsVileOffering.class));
        cards.add(new SetCardInfo("Zhalfirin Void", 249, Rarity.UNCOMMON, mage.cards.z.ZhalfirinVoid.class));
    }
}
