/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class IceAge extends ExpansionSet {

    private static final IceAge instance = new IceAge();

    public static IceAge getInstance() {
        return instance;
    }

    private IceAge() {
        super("Ice Age", "ICE", ExpansionSet.buildDate(1995, 5, 1), SetType.EXPANSION);
        this.blockName = "Ice Age";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abyssal Specter", 1, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class));
        cards.add(new SetCardInfo("Adarkar Sentinel", 281, Rarity.UNCOMMON, mage.cards.a.AdarkarSentinel.class));
        cards.add(new SetCardInfo("Adarkar Wastes", 326, Rarity.RARE, mage.cards.a.AdarkarWastes.class));
        cards.add(new SetCardInfo("Aegis of the Meek", 282, Rarity.RARE, mage.cards.a.AegisOfTheMeek.class));
        cards.add(new SetCardInfo("Altar of Bone", 359, Rarity.RARE, mage.cards.a.AltarOfBone.class));
        cards.add(new SetCardInfo("Anarchy", 170, Rarity.UNCOMMON, mage.cards.a.Anarchy.class));
        cards.add(new SetCardInfo("Arenson's Aura", 227, Rarity.COMMON, mage.cards.a.ArensonsAura.class));
        cards.add(new SetCardInfo("Armor of Faith", 228, Rarity.COMMON, mage.cards.a.ArmorOfFaith.class));
        cards.add(new SetCardInfo("Arnjlot's Ascent", 57, Rarity.COMMON, mage.cards.a.ArnjlotsAscent.class));
        cards.add(new SetCardInfo("Aurochs", 113, Rarity.COMMON, mage.cards.a.Aurochs.class));
        cards.add(new SetCardInfo("Balduvian Barbarians", 172, Rarity.COMMON, mage.cards.b.BalduvianBarbarians.class));
        cards.add(new SetCardInfo("Balduvian Bears", 114, Rarity.COMMON, mage.cards.b.BalduvianBears.class));
        cards.add(new SetCardInfo("Barbed Sextant", 287, Rarity.COMMON, mage.cards.b.BarbedSextant.class));
        cards.add(new SetCardInfo("Battle Frenzy", 175, Rarity.COMMON, mage.cards.b.BattleFrenzy.class));
        cards.add(new SetCardInfo("Binding Grasp", 60, Rarity.UNCOMMON, mage.cards.b.BindingGrasp.class));
        cards.add(new SetCardInfo("Black Scarab", 230, Rarity.UNCOMMON, mage.cards.b.BlackScarab.class));
        cards.add(new SetCardInfo("Blessed Wine", 231, Rarity.COMMON, mage.cards.b.BlessedWine.class));
        cards.add(new SetCardInfo("Blinking Spirit", 232, Rarity.RARE, mage.cards.b.BlinkingSpirit.class));
        cards.add(new SetCardInfo("Blue Scarab", 233, Rarity.UNCOMMON, mage.cards.b.BlueScarab.class));
        cards.add(new SetCardInfo("Brainstorm", 61, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brushland", 327, Rarity.RARE, mage.cards.b.Brushland.class));
        cards.add(new SetCardInfo("Burnt Offering", 4, Rarity.COMMON, mage.cards.b.BurntOffering.class));
        cards.add(new SetCardInfo("Caribou Range", 235, Rarity.RARE, mage.cards.c.CaribouRange.class));
        cards.add(new SetCardInfo("Celestial Sword", 289, Rarity.RARE, mage.cards.c.CelestialSword.class));
        cards.add(new SetCardInfo("Centaur Archer", 360, Rarity.UNCOMMON, mage.cards.c.CentaurArcher.class));
        cards.add(new SetCardInfo("Chub Toad", 117, Rarity.COMMON, mage.cards.c.ChubToad.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", 236, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Blue", 237, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlue.class));
        cards.add(new SetCardInfo("Circle of Protection: Green", 238, Rarity.COMMON, mage.cards.c.CircleOfProtectionGreen.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 239, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Circle of Protection: White", 240, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Clairvoyance", 63, Rarity.COMMON, mage.cards.c.Clairvoyance.class));
        cards.add(new SetCardInfo("Cold Snap", 241, Rarity.UNCOMMON, mage.cards.c.ColdSnap.class));
        cards.add(new SetCardInfo("Conquer", 180, Rarity.UNCOMMON, mage.cards.c.Conquer.class));
        cards.add(new SetCardInfo("Counterspell", 64, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crown of the Ages", 290, Rarity.RARE, mage.cards.c.CrownOfTheAges.class));
        cards.add(new SetCardInfo("Curse of Marit Lage", 181, Rarity.RARE, mage.cards.c.CurseOfMaritLage.class));
        cards.add(new SetCardInfo("Dance of the Dead", 6, Rarity.UNCOMMON, mage.cards.d.DanceOfTheDead.class));
        cards.add(new SetCardInfo("Dark Banishing", 7, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Dark Ritual", 8, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Death Ward", 243, Rarity.COMMON, mage.cards.d.DeathWard.class));
        cards.add(new SetCardInfo("Deflection", 65, Rarity.RARE, mage.cards.d.Deflection.class));
        cards.add(new SetCardInfo("Demonic Consultation", 9, Rarity.UNCOMMON, mage.cards.d.DemonicConsultation.class));
        cards.add(new SetCardInfo("Despotic Scepter", 291, Rarity.RARE, mage.cards.d.DespoticScepter.class));
        cards.add(new SetCardInfo("Disenchant", 244, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dwarven Armory", 182, Rarity.RARE, mage.cards.d.DwarvenArmory.class));
        cards.add(new SetCardInfo("Elder Druid", 120, Rarity.RARE, mage.cards.e.ElderDruid.class));
        cards.add(new SetCardInfo("Elemental Augury", 364, Rarity.RARE, mage.cards.e.ElementalAugury.class));
        cards.add(new SetCardInfo("Enduring Renewal", 247, Rarity.RARE, mage.cards.e.EnduringRenewal.class));
        cards.add(new SetCardInfo("Enervate", 67, Rarity.COMMON, mage.cards.e.Enervate.class));
        cards.add(new SetCardInfo("Errantry", 183, Rarity.COMMON, mage.cards.e.Errantry.class));
        cards.add(new SetCardInfo("Fanatical Fever", 122, Rarity.UNCOMMON, mage.cards.f.FanaticalFever.class));
        cards.add(new SetCardInfo("Fear", 12, Rarity.COMMON, mage.cards.f.Fear.class));
        cards.add(new SetCardInfo("Fiery Justice", 366, Rarity.RARE, mage.cards.f.FieryJustice.class));
        cards.add(new SetCardInfo("Fire Covenant", 367, Rarity.UNCOMMON, mage.cards.f.FireCovenant.class));
        cards.add(new SetCardInfo("Flame Spirit", 184, Rarity.UNCOMMON, mage.cards.f.FlameSpirit.class));
        cards.add(new SetCardInfo("Flare", 185, Rarity.COMMON, mage.cards.f.Flare.class));
        cards.add(new SetCardInfo("Flow of Maggots", 13, Rarity.RARE, mage.cards.f.FlowOfMaggots.class));
        cards.add(new SetCardInfo("Folk of the Pines", 123, Rarity.COMMON, mage.cards.f.FolkOfThePines.class));
        cards.add(new SetCardInfo("Forbidden Lore", 124, Rarity.RARE, mage.cards.f.ForbiddenLore.class));
        cards.add(new SetCardInfo("Force Void", 70, Rarity.UNCOMMON, mage.cards.f.ForceVoid.class));
        cards.add(new SetCardInfo("Forest", 328, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 329, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 330, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forgotten Lore", 125, Rarity.UNCOMMON, mage.cards.f.ForgottenLore.class));
        cards.add(new SetCardInfo("Foul Familiar", 14, Rarity.COMMON, mage.cards.f.FoulFamiliar.class));
        cards.add(new SetCardInfo("Fumarole", 369, Rarity.UNCOMMON, mage.cards.f.Fumarole.class));
        cards.add(new SetCardInfo("Fyndhorn Bow", 293, Rarity.UNCOMMON, mage.cards.f.FyndhornBow.class));
        cards.add(new SetCardInfo("Fyndhorn Brownie", 130, Rarity.COMMON, mage.cards.f.FyndhornBrownie.class));
        cards.add(new SetCardInfo("Fyndhorn Elder", 131, Rarity.UNCOMMON, mage.cards.f.FyndhornElder.class));
        cards.add(new SetCardInfo("Fyndhorn Elves", 132, Rarity.COMMON, mage.cards.f.FyndhornElves.class));
        cards.add(new SetCardInfo("Game of Chaos", 186, Rarity.RARE, mage.cards.g.GameOfChaos.class));
        cards.add(new SetCardInfo("Gangrenous Zombies", 15, Rarity.COMMON, mage.cards.g.GangrenousZombies.class));
        cards.add(new SetCardInfo("Giant Growth", 134, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glacial Chasm", 331, Rarity.UNCOMMON, mage.cards.g.GlacialChasm.class));
        cards.add(new SetCardInfo("Glacial Crevasses", 187, Rarity.RARE, mage.cards.g.GlacialCrevasses.class));
        cards.add(new SetCardInfo("Glacial Wall", 71, Rarity.UNCOMMON, mage.cards.g.GlacialWall.class));
        cards.add(new SetCardInfo("Goblin Mutant", 188, Rarity.UNCOMMON, mage.cards.g.GoblinMutant.class));
        cards.add(new SetCardInfo("Goblin Snowman", 191, Rarity.UNCOMMON, mage.cards.g.GoblinSnowman.class));
        cards.add(new SetCardInfo("Gorilla Pack", 135, Rarity.COMMON, mage.cards.g.GorillaPack.class));
        cards.add(new SetCardInfo("Gravebind", 17, Rarity.RARE, mage.cards.g.Gravebind.class));
        cards.add(new SetCardInfo("Green Scarab", 252, Rarity.UNCOMMON, mage.cards.g.GreenScarab.class));
        cards.add(new SetCardInfo("Hallowed Ground", 253, Rarity.UNCOMMON, mage.cards.h.HallowedGround.class));
        cards.add(new SetCardInfo("Heal", 254, Rarity.COMMON, mage.cards.h.Heal.class));
        cards.add(new SetCardInfo("Hecatomb", 18, Rarity.RARE, mage.cards.h.Hecatomb.class));
        cards.add(new SetCardInfo("Hematite Talisman", 295, Rarity.UNCOMMON, mage.cards.h.HematiteTalisman.class));
        cards.add(new SetCardInfo("Hoar Shade", 19, Rarity.COMMON, mage.cards.h.HoarShade.class));
        cards.add(new SetCardInfo("Hot Springs", 136, Rarity.RARE, mage.cards.h.HotSprings.class));
        cards.add(new SetCardInfo("Howl from Beyond", 20, Rarity.COMMON, mage.cards.h.HowlFromBeyond.class));
        cards.add(new SetCardInfo("Hurricane", 137, Rarity.UNCOMMON, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hyalopterous Lemure", 21, Rarity.UNCOMMON, mage.cards.h.HyalopterousLemure.class));
        cards.add(new SetCardInfo("Hydroblast", 72, Rarity.COMMON, mage.cards.h.Hydroblast.class));
        cards.add(new SetCardInfo("Hymn of Rebirth", 373, Rarity.UNCOMMON, mage.cards.h.HymnOfRebirth.class));
        cards.add(new SetCardInfo("Icequake", 22, Rarity.UNCOMMON, mage.cards.i.Icequake.class));
        cards.add(new SetCardInfo("Icy Manipulator", 297, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Icy Prison", 74, Rarity.RARE, mage.cards.i.IcyPrison.class));
        cards.add(new SetCardInfo("Illusionary Forces", 75, Rarity.COMMON, mage.cards.i.IllusionaryForces.class));
        cards.add(new SetCardInfo("Illusionary Wall", 78, Rarity.COMMON, mage.cards.i.IllusionaryWall.class));
        cards.add(new SetCardInfo("Illusions of Grandeur", 79, Rarity.RARE, mage.cards.i.IllusionsOfGrandeur.class));
        cards.add(new SetCardInfo("Imposing Visage", 193, Rarity.COMMON, mage.cards.i.ImposingVisage.class));
        cards.add(new SetCardInfo("Incinerate", 194, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Infernal Darkness", 23, Rarity.RARE, mage.cards.i.InfernalDarkness.class));
        cards.add(new SetCardInfo("Infuse", 80, Rarity.COMMON, mage.cards.i.Infuse.class));
        cards.add(new SetCardInfo("Island", 334, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 335, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 336, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Jester's Cap", 299, Rarity.RARE, mage.cards.j.JestersCap.class));
        cards.add(new SetCardInfo("Jester's Mask", 300, Rarity.RARE, mage.cards.j.JestersMask.class));
        cards.add(new SetCardInfo("Jeweled Amulet", 301, Rarity.UNCOMMON, mage.cards.j.JeweledAmulet.class));
        cards.add(new SetCardInfo("Johtull Wurm", 138, Rarity.UNCOMMON, mage.cards.j.JohtullWurm.class));
        cards.add(new SetCardInfo("Jokulhaups", 195, Rarity.RARE, mage.cards.j.Jokulhaups.class));
        cards.add(new SetCardInfo("Juniper Order Druid", 139, Rarity.COMMON, mage.cards.j.JuniperOrderDruid.class));
        cards.add(new SetCardInfo("Justice", 256, Rarity.UNCOMMON, mage.cards.j.Justice.class));
        cards.add(new SetCardInfo("Karplusan Forest", 337, Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Karplusan Yeti", 197, Rarity.RARE, mage.cards.k.KarplusanYeti.class));
        cards.add(new SetCardInfo("Kjeldoran Dead", 25, Rarity.COMMON, mage.cards.k.KjeldoranDead.class));
        cards.add(new SetCardInfo("Kjeldoran Royal Guard", 262, Rarity.RARE, mage.cards.k.KjeldoranRoyalGuard.class));
        cards.add(new SetCardInfo("Knight of Stromgald", 26, Rarity.UNCOMMON, mage.cards.k.KnightOfStromgald.class));
        cards.add(new SetCardInfo("Krovikan Fetish", 28, Rarity.COMMON, mage.cards.k.KrovikanFetish.class));
        cards.add(new SetCardInfo("Krovikan Sorcerer", 81, Rarity.COMMON, mage.cards.k.KrovikanSorcerer.class));
        cards.add(new SetCardInfo("Land Cap", 338, Rarity.RARE, mage.cards.l.LandCap.class));
        cards.add(new SetCardInfo("Lapis Lazuli Talisman", 302, Rarity.UNCOMMON, mage.cards.l.LapisLazuliTalisman.class));
        cards.add(new SetCardInfo("Lava Tubes", 339, Rarity.RARE, mage.cards.l.LavaTubes.class));
        cards.add(new SetCardInfo("Legions of Lim-Dul", 30, Rarity.COMMON, mage.cards.l.LegionsOfLimDul.class));
        cards.add(new SetCardInfo("Leshrac's Rite", 31, Rarity.UNCOMMON, mage.cards.l.LeshracsRite.class));
        cards.add(new SetCardInfo("Lhurgoyf", 140, Rarity.RARE, mage.cards.l.Lhurgoyf.class));
        cards.add(new SetCardInfo("Lightning Blow", 266, Rarity.RARE, mage.cards.l.LightningBlow.class));
        cards.add(new SetCardInfo("Lure", 141, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Magus of the Unseen", 82, Rarity.RARE, mage.cards.m.MagusOfTheUnseen.class));
        cards.add(new SetCardInfo("Malachite Talisman", 303, Rarity.UNCOMMON, mage.cards.m.MalachiteTalisman.class));
        cards.add(new SetCardInfo("Marton Stromgald", 199, Rarity.RARE, mage.cards.m.MartonStromgald.class));
        cards.add(new SetCardInfo("Merieke Ri Berit", 375, Rarity.RARE, mage.cards.m.MeriekeRiBerit.class));
        cards.add(new SetCardInfo("Mesmeric Trance", 83, Rarity.RARE, mage.cards.m.MesmericTrance.class));
        cards.add(new SetCardInfo("Mind Ravel", 35, Rarity.COMMON, mage.cards.m.MindRavel.class));
        cards.add(new SetCardInfo("Mind Warp", 36, Rarity.UNCOMMON, mage.cards.m.MindWarp.class));
        cards.add(new SetCardInfo("Mole Worms", 40, Rarity.UNCOMMON, mage.cards.m.MoleWorms.class));
        cards.add(new SetCardInfo("Moor Fiend", 41, Rarity.COMMON, mage.cards.m.MoorFiend.class));
        cards.add(new SetCardInfo("Mountain", 340, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 341, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 342, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain Goat", 203, Rarity.COMMON, mage.cards.m.MountainGoat.class));
        cards.add(new SetCardInfo("Mudslide", 204, Rarity.RARE, mage.cards.m.Mudslide.class));
        cards.add(new SetCardInfo("Mystic Might", 86, Rarity.RARE, mage.cards.m.MysticMight.class));
        cards.add(new SetCardInfo("Mystic Remora", 87, Rarity.COMMON, mage.cards.m.MysticRemora.class));
        cards.add(new SetCardInfo("Nacre Talisman", 304, Rarity.UNCOMMON, mage.cards.n.NacreTalisman.class));
        cards.add(new SetCardInfo("Naked Singularity", 305, Rarity.RARE, mage.cards.n.NakedSingularity.class));
        cards.add(new SetCardInfo("Nature's Lore", 143, Rarity.UNCOMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Necropotence", 42, Rarity.RARE, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Onyx Talisman", 306, Rarity.UNCOMMON, mage.cards.o.OnyxTalisman.class));
        cards.add(new SetCardInfo("Orcish Cannoneers", 205, Rarity.UNCOMMON, mage.cards.o.OrcishCannoneers.class));
        cards.add(new SetCardInfo("Orcish Healer", 208, Rarity.UNCOMMON, mage.cards.o.OrcishHealer.class));
        cards.add(new SetCardInfo("Orcish Librarian", 209, Rarity.RARE, mage.cards.o.OrcishLibrarian.class));
        cards.add(new SetCardInfo("Orcish Lumberjack", 210, Rarity.COMMON, mage.cards.o.OrcishLumberjack.class));
        cards.add(new SetCardInfo("Order of the Sacred Torch", 269, Rarity.RARE, mage.cards.o.OrderOfTheSacredTorch.class));
        cards.add(new SetCardInfo("Order of the White Shield", 270, Rarity.UNCOMMON, mage.cards.o.OrderOfTheWhiteShield.class));
        cards.add(new SetCardInfo("Pale Bears", 144, Rarity.RARE, mage.cards.p.PaleBears.class));
        cards.add(new SetCardInfo("Panic", 212, Rarity.COMMON, mage.cards.p.Panic.class));
        cards.add(new SetCardInfo("Pentagram of the Ages", 307, Rarity.RARE, mage.cards.p.PentagramOfTheAges.class));
        cards.add(new SetCardInfo("Pestilence Rats", 45, Rarity.COMMON, mage.cards.p.PestilenceRats.class));
        cards.add(new SetCardInfo("Pit Trap", 308, Rarity.UNCOMMON, mage.cards.p.PitTrap.class));
        cards.add(new SetCardInfo("Plains", 343, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 344, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 345, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Polar Kraken", 89, Rarity.RARE, mage.cards.p.PolarKraken.class));
        cards.add(new SetCardInfo("Portent", 90, Rarity.COMMON, mage.cards.p.Portent.class));
        cards.add(new SetCardInfo("Power Sink", 91, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Pox", 46, Rarity.RARE, mage.cards.p.Pox.class));
        cards.add(new SetCardInfo("Pygmy Allosaurus", 145, Rarity.RARE, mage.cards.p.PygmyAllosaurus.class));
        cards.add(new SetCardInfo("Pyknite", 146, Rarity.COMMON, mage.cards.p.Pyknite.class));
        cards.add(new SetCardInfo("Pyroblast", 213, Rarity.COMMON, mage.cards.p.Pyroblast.class));
        cards.add(new SetCardInfo("Pyroclasm", 214, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Rally", 272, Rarity.COMMON, mage.cards.r.Rally.class));
        cards.add(new SetCardInfo("Ray of Command", 92, Rarity.COMMON, mage.cards.r.RayOfCommand.class));
        cards.add(new SetCardInfo("Ray of Erasure", 93, Rarity.COMMON, mage.cards.r.RayOfErasure.class));
        cards.add(new SetCardInfo("Red Scarab", 273, Rarity.UNCOMMON, mage.cards.r.RedScarab.class));
        cards.add(new SetCardInfo("Regeneration", 147, Rarity.COMMON, mage.cards.r.Regeneration.class));
        cards.add(new SetCardInfo("Rime Dryad", 148, Rarity.COMMON, mage.cards.r.RimeDryad.class));
        cards.add(new SetCardInfo("River Delta", 346, Rarity.RARE, mage.cards.r.RiverDelta.class));
        cards.add(new SetCardInfo("Sabretooth Tiger", 215, Rarity.COMMON, mage.cards.s.SabretoothTiger.class));
        cards.add(new SetCardInfo("Scaled Wurm", 150, Rarity.COMMON, mage.cards.s.ScaledWurm.class));
        cards.add(new SetCardInfo("Sea Spirit", 95, Rarity.UNCOMMON, mage.cards.s.SeaSpirit.class));
        cards.add(new SetCardInfo("Seizures", 47, Rarity.COMMON, mage.cards.s.Seizures.class));
        cards.add(new SetCardInfo("Shambling Strider", 151, Rarity.COMMON, mage.cards.s.ShamblingStrider.class));
        cards.add(new SetCardInfo("Shatter", 216, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shield of the Ages", 310, Rarity.UNCOMMON, mage.cards.s.ShieldOfTheAges.class));
        cards.add(new SetCardInfo("Sibilant Spirit", 97, Rarity.RARE, mage.cards.s.SibilantSpirit.class));
        cards.add(new SetCardInfo("Silver Erne", 98, Rarity.UNCOMMON, mage.cards.s.SilverErne.class));
        cards.add(new SetCardInfo("Skeleton Ship", 379, Rarity.RARE, mage.cards.s.SkeletonShip.class));
        cards.add(new SetCardInfo("Skull Catapult", 311, Rarity.UNCOMMON, mage.cards.s.SkullCatapult.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 347, Rarity.COMMON, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 348, Rarity.COMMON, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 349, Rarity.COMMON, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 350, Rarity.COMMON, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 351, Rarity.COMMON, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Snow Hound", 277, Rarity.UNCOMMON, mage.cards.s.SnowHound.class));
        cards.add(new SetCardInfo("Soldevi Machinist", 102, Rarity.UNCOMMON, mage.cards.s.SoldeviMachinist.class));
        cards.add(new SetCardInfo("Soldevi Simulacrum", 314, Rarity.UNCOMMON, mage.cards.s.SoldeviSimulacrum.class));
        cards.add(new SetCardInfo("Songs of the Damned", 48, Rarity.COMMON, mage.cards.s.SongsOfTheDamned.class));
        cards.add(new SetCardInfo("Soul Barrier", 103, Rarity.UNCOMMON, mage.cards.s.SoulBarrier.class));
        cards.add(new SetCardInfo("Soul Burn", 361, Rarity.COMMON, mage.cards.s.SoulBurn.class));
        cards.add(new SetCardInfo("Soul Kiss", 50, Rarity.COMMON, mage.cards.s.SoulKiss.class));
        cards.add(new SetCardInfo("Spoils of Evil", 51, Rarity.RARE, mage.cards.s.SpoilsOfEvil.class));
        cards.add(new SetCardInfo("Stampede", 153, Rarity.RARE, mage.cards.s.Stampede.class));
        cards.add(new SetCardInfo("Stonehands", 219, Rarity.COMMON, mage.cards.s.Stonehands.class));
        cards.add(new SetCardInfo("Stone Rain", 217, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stone Spirit", 218, Rarity.UNCOMMON, mage.cards.s.StoneSpirit.class));
        cards.add(new SetCardInfo("Stormbind", 382, Rarity.RARE, mage.cards.s.Stormbind.class));
        cards.add(new SetCardInfo("Storm Spirit", 381, Rarity.RARE, mage.cards.s.StormSpirit.class));
        cards.add(new SetCardInfo("Stromgald Cabal", 54, Rarity.RARE, mage.cards.s.StromgaldCabal.class));
        cards.add(new SetCardInfo("Stunted Growth", 154, Rarity.RARE, mage.cards.s.StuntedGrowth.class));
        cards.add(new SetCardInfo("Sulfurous Springs", 352, Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Sunstone", 316, Rarity.UNCOMMON, mage.cards.s.Sunstone.class));
        cards.add(new SetCardInfo("Swamp", 353, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 354, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 355, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swords to Plowshares", 278, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tarpan", 155, Rarity.COMMON, mage.cards.t.Tarpan.class));
        cards.add(new SetCardInfo("Thermokarst", 156, Rarity.UNCOMMON, mage.cards.t.Thermokarst.class));
        cards.add(new SetCardInfo("Thoughtleech", 157, Rarity.UNCOMMON, mage.cards.t.Thoughtleech.class));
        cards.add(new SetCardInfo("Thunder Wall", 104, Rarity.UNCOMMON, mage.cards.t.ThunderWall.class));
        cards.add(new SetCardInfo("Timberline Ridge", 356, Rarity.RARE, mage.cards.t.TimberlineRidge.class));
        cards.add(new SetCardInfo("Time Bomb", 317, Rarity.RARE, mage.cards.t.TimeBomb.class));
        cards.add(new SetCardInfo("Tinder Wall", 158, Rarity.COMMON, mage.cards.t.TinderWall.class));
        cards.add(new SetCardInfo("Tor Giant", 220, Rarity.COMMON, mage.cards.t.TorGiant.class));
        cards.add(new SetCardInfo("Touch of Death", 55, Rarity.COMMON, mage.cards.t.TouchOfDeath.class));
        cards.add(new SetCardInfo("Underground River", 357, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Updraft", 105, Rarity.UNCOMMON, mage.cards.u.Updraft.class));
        cards.add(new SetCardInfo("Urza's Bauble", 318, Rarity.UNCOMMON, mage.cards.u.UrzasBauble.class));
        cards.add(new SetCardInfo("Veldt", 358, Rarity.RARE, mage.cards.v.Veldt.class));
        cards.add(new SetCardInfo("Vertigo", 222, Rarity.UNCOMMON, mage.cards.v.Vertigo.class));
        cards.add(new SetCardInfo("Walking Wall", 321, Rarity.UNCOMMON, mage.cards.w.WalkingWall.class));
        cards.add(new SetCardInfo("Wall of Lava", 223, Rarity.UNCOMMON, mage.cards.w.WallOfLava.class));
        cards.add(new SetCardInfo("Wall of Pine Needles", 162, Rarity.UNCOMMON, mage.cards.w.WallOfPineNeedles.class));
        cards.add(new SetCardInfo("War Chariot", 323, Rarity.UNCOMMON, mage.cards.w.WarChariot.class));
        cards.add(new SetCardInfo("Warning", 279, Rarity.COMMON, mage.cards.w.Warning.class));
        cards.add(new SetCardInfo("Whiteout", 163, Rarity.UNCOMMON, mage.cards.w.Whiteout.class));
        cards.add(new SetCardInfo("White Scarab", 280, Rarity.UNCOMMON, mage.cards.w.WhiteScarab.class));
        cards.add(new SetCardInfo("Wild Growth", 165, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Wind Spirit", 106, Rarity.UNCOMMON, mage.cards.w.WindSpirit.class));
        cards.add(new SetCardInfo("Wings of Aesthir", 383, Rarity.UNCOMMON, mage.cards.w.WingsOfAesthir.class));
        cards.add(new SetCardInfo("Word of Blasting", 224, Rarity.UNCOMMON, mage.cards.w.WordOfBlasting.class));
        cards.add(new SetCardInfo("Wrath of Marit Lage", 109, Rarity.RARE, mage.cards.w.WrathOfMaritLage.class));
        cards.add(new SetCardInfo("Yavimaya Gnats", 168, Rarity.UNCOMMON, mage.cards.y.YavimayaGnats.class));
        cards.add(new SetCardInfo("Zuran Enchanter", 110, Rarity.COMMON, mage.cards.z.ZuranEnchanter.class));
        cards.add(new SetCardInfo("Zuran Orb", 325, Rarity.UNCOMMON, mage.cards.z.ZuranOrb.class));
        cards.add(new SetCardInfo("Zuran Spellcaster", 111, Rarity.COMMON, mage.cards.z.ZuranSpellcaster.class));
        cards.add(new SetCardInfo("Zur's Weirding", 112, Rarity.RARE, mage.cards.z.ZursWeirding.class));
    }
}
