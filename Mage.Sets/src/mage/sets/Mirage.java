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

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Mirage extends ExpansionSet {

    private static final Mirage instance = new Mirage();

    public static Mirage getInstance() {
        return instance;
    }

    private Mirage() {
        super("Mirage", "MIR", ExpansionSet.buildDate(1996, 8, 21), SetType.EXPANSION);
        this.blockName = "Mirage";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abyssal Hunter", 1, Rarity.RARE, mage.cards.a.AbyssalHunter.class));
        cards.add(new SetCardInfo("Afiya Grove", 103, Rarity.RARE, mage.cards.a.AfiyaGrove.class));
        cards.add(new SetCardInfo("Afterlife", 205, Rarity.UNCOMMON, mage.cards.a.Afterlife.class));
        cards.add(new SetCardInfo("Agility", 154, Rarity.COMMON, mage.cards.a.Agility.class));
        cards.add(new SetCardInfo("Alarum", 206, Rarity.COMMON, mage.cards.a.Alarum.class));
        cards.add(new SetCardInfo("Aleatory", 155, Rarity.UNCOMMON, mage.cards.a.Aleatory.class));
        cards.add(new SetCardInfo("Amber Prison", 257, Rarity.RARE, mage.cards.a.AmberPrison.class));
        cards.add(new SetCardInfo("Amulet of Unmaking", 258, Rarity.RARE, mage.cards.a.AmuletOfUnmaking.class));
        cards.add(new SetCardInfo("Ancestral Memories", 52, Rarity.RARE, mage.cards.a.AncestralMemories.class));
        cards.add(new SetCardInfo("Armorer Guildmage", 156, Rarity.COMMON, mage.cards.a.ArmorerGuildmage.class));
        cards.add(new SetCardInfo("Armor of Thorns", 104, Rarity.COMMON, mage.cards.a.ArmorOfThorns.class));
        cards.add(new SetCardInfo("Ashen Powder", 2, Rarity.RARE, mage.cards.a.AshenPowder.class));
        cards.add(new SetCardInfo("Asmira, Holy Avenger", 316, Rarity.RARE, mage.cards.a.AsmiraHolyAvenger.class));
        cards.add(new SetCardInfo("Auspicious Ancestor", 207, Rarity.RARE, mage.cards.a.AuspiciousAncestor.class));
        cards.add(new SetCardInfo("Azimaet Drake", 53, Rarity.COMMON, mage.cards.a.AzimaetDrake.class));
        cards.add(new SetCardInfo("Bad River", 289, Rarity.UNCOMMON, mage.cards.b.BadRiver.class));
        cards.add(new SetCardInfo("Barbed-Back Wurm", 3, Rarity.UNCOMMON, mage.cards.b.BarbedBackWurm.class));
        cards.add(new SetCardInfo("Bay Falcon", 54, Rarity.COMMON, mage.cards.b.BayFalcon.class));
        cards.add(new SetCardInfo("Benthic Djinn", 317, Rarity.RARE, mage.cards.b.BenthicDjinn.class));
        cards.add(new SetCardInfo("Binding Agony", 4, Rarity.COMMON, mage.cards.b.BindingAgony.class));
        cards.add(new SetCardInfo("Blighted Shaman", 5, Rarity.UNCOMMON, mage.cards.b.BlightedShaman.class));
        cards.add(new SetCardInfo("Blinding Light", 209, Rarity.UNCOMMON, mage.cards.b.BlindingLight.class));
        cards.add(new SetCardInfo("Blistering Barrier", 159, Rarity.COMMON, mage.cards.b.BlisteringBarrier.class));
        cards.add(new SetCardInfo("Bone Harvest", 6, Rarity.COMMON, mage.cards.b.BoneHarvest.class));
        cards.add(new SetCardInfo("Boomerang", 56, Rarity.COMMON, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Breathstealer", 7, Rarity.COMMON, mage.cards.b.Breathstealer.class));
        cards.add(new SetCardInfo("Brushwagg", 106, Rarity.RARE, mage.cards.b.Brushwagg.class));
        cards.add(new SetCardInfo("Burning Shield Askari", 162, Rarity.COMMON, mage.cards.b.BurningShieldAskari.class));
        cards.add(new SetCardInfo("Cadaverous Bloom", 318, Rarity.RARE, mage.cards.c.CadaverousBloom.class));
        cards.add(new SetCardInfo("Cadaverous Knight", 8, Rarity.COMMON, mage.cards.c.CadaverousKnight.class));
        cards.add(new SetCardInfo("Canopy Dragon", 107, Rarity.RARE, mage.cards.c.CanopyDragon.class));
        cards.add(new SetCardInfo("Carrion", 9, Rarity.RARE, mage.cards.c.Carrion.class));
        cards.add(new SetCardInfo("Celestial Dawn", 210, Rarity.RARE, mage.cards.c.CelestialDawn.class));
        cards.add(new SetCardInfo("Cerulean Wyvern", 57, Rarity.UNCOMMON, mage.cards.c.CeruleanWyvern.class));
        cards.add(new SetCardInfo("Chaos Charm", 163, Rarity.COMMON, mage.cards.c.ChaosCharm.class));
        cards.add(new SetCardInfo("Chaosphere", 164, Rarity.RARE, mage.cards.c.Chaosphere.class));
        cards.add(new SetCardInfo("Charcoal Diamond", 261, Rarity.UNCOMMON, mage.cards.c.CharcoalDiamond.class));
        cards.add(new SetCardInfo("Choking Sands", 11, Rarity.COMMON, mage.cards.c.ChokingSands.class));
        cards.add(new SetCardInfo("Cinder Cloud", 165, Rarity.UNCOMMON, mage.cards.c.CinderCloud.class));
        cards.add(new SetCardInfo("Civic Guildmage", 211, Rarity.COMMON, mage.cards.c.CivicGuildmage.class));
        cards.add(new SetCardInfo("Cloak of Invisibility", 58, Rarity.COMMON, mage.cards.c.CloakOfInvisibility.class));
        cards.add(new SetCardInfo("Consuming Ferocity", 166, Rarity.UNCOMMON, mage.cards.c.ConsumingFerocity.class));
        cards.add(new SetCardInfo("Coral Fighters", 59, Rarity.UNCOMMON, mage.cards.c.CoralFighters.class));
        cards.add(new SetCardInfo("Crash of Rhinos", 108, Rarity.COMMON, mage.cards.c.CrashOfRhinos.class));
        cards.add(new SetCardInfo("Crimson Hellkite", 167, Rarity.RARE, mage.cards.c.CrimsonHellkite.class));
        cards.add(new SetCardInfo("Crypt Cobra", 12, Rarity.UNCOMMON, mage.cards.c.CryptCobra.class));
        cards.add(new SetCardInfo("Crystal Golem", 263, Rarity.UNCOMMON, mage.cards.c.CrystalGolem.class));
        cards.add(new SetCardInfo("Crystal Vein", 290, Rarity.UNCOMMON, mage.cards.c.CrystalVein.class));
        cards.add(new SetCardInfo("Cursed Totem", 264, Rarity.RARE, mage.cards.c.CursedTotem.class));
        cards.add(new SetCardInfo("Daring Apprentice", 60, Rarity.RARE, mage.cards.d.DaringApprentice.class));
        cards.add(new SetCardInfo("Dark Banishing", 13, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Dark Ritual", 14, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Dirtwater Wraith", 15, Rarity.COMMON, mage.cards.d.DirtwaterWraith.class));
        cards.add(new SetCardInfo("Disempower", 213, Rarity.COMMON, mage.cards.d.Disempower.class));
        cards.add(new SetCardInfo("Disenchant", 214, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dissipate", 61, Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Divine Offering", 215, Rarity.COMMON, mage.cards.d.DivineOffering.class));
        cards.add(new SetCardInfo("Drain Life", 16, Rarity.COMMON, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Dread Specter", 17, Rarity.UNCOMMON, mage.cards.d.DreadSpecter.class));
        cards.add(new SetCardInfo("Dream Cache", 62, Rarity.COMMON, mage.cards.d.DreamCache.class));
        cards.add(new SetCardInfo("Dwarven Miner", 169, Rarity.UNCOMMON, mage.cards.d.DwarvenMiner.class));
        cards.add(new SetCardInfo("Dwarven Nomad", 170, Rarity.COMMON, mage.cards.d.DwarvenNomad.class));
        cards.add(new SetCardInfo("Early Harvest", 111, Rarity.RARE, mage.cards.e.EarlyHarvest.class));
        cards.add(new SetCardInfo("Ebony Charm", 18, Rarity.COMMON, mage.cards.e.EbonyCharm.class));
        cards.add(new SetCardInfo("Ekundu Griffin", 217, Rarity.COMMON, mage.cards.e.EkunduGriffin.class));
        cards.add(new SetCardInfo("Elixir of Vitality", 265, Rarity.UNCOMMON, mage.cards.e.ElixirOfVitality.class));
        cards.add(new SetCardInfo("Enfeeblement", 19, Rarity.COMMON, mage.cards.e.Enfeeblement.class));
        cards.add(new SetCardInfo("Enlightened Tutor", 218, Rarity.UNCOMMON, mage.cards.e.EnlightenedTutor.class));
        cards.add(new SetCardInfo("Ersatz Gnomes", 266, Rarity.UNCOMMON, mage.cards.e.ErsatzGnomes.class));
        cards.add(new SetCardInfo("Ethereal Champion", 219, Rarity.RARE, mage.cards.e.EtherealChampion.class));
        cards.add(new SetCardInfo("Fallow Earth", 112, Rarity.UNCOMMON, mage.cards.f.FallowEarth.class));
        cards.add(new SetCardInfo("Femeref Archers", 113, Rarity.UNCOMMON, mage.cards.f.FemerefArchers.class));
        cards.add(new SetCardInfo("Femeref Healer", 221, Rarity.COMMON, mage.cards.f.FemerefHealer.class));
        cards.add(new SetCardInfo("Femeref Knight", 222, Rarity.COMMON, mage.cards.f.FemerefKnight.class));
        cards.add(new SetCardInfo("Femeref Scouts", 223, Rarity.COMMON, mage.cards.f.FemerefScouts.class));
        cards.add(new SetCardInfo("Feral Shadow", 20, Rarity.COMMON, mage.cards.f.FeralShadow.class));
        cards.add(new SetCardInfo("Fetid Horror", 21, Rarity.COMMON, mage.cards.f.FetidHorror.class));
        cards.add(new SetCardInfo("Final Fortune", 173, Rarity.RARE, mage.cards.f.FinalFortune.class));
        cards.add(new SetCardInfo("Firebreathing", 174, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Fire Diamond", 267, Rarity.UNCOMMON, mage.cards.f.FireDiamond.class));
        cards.add(new SetCardInfo("Flare", 176, Rarity.COMMON, mage.cards.f.Flare.class));
        cards.add(new SetCardInfo("Flash", 66, Rarity.RARE, mage.cards.f.Flash.class));
        cards.add(new SetCardInfo("Flood Plain", 291, Rarity.UNCOMMON, mage.cards.f.FloodPlain.class));
        cards.add(new SetCardInfo("Fog", 114, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Foratog", 115, Rarity.UNCOMMON, mage.cards.f.Foratog.class));
        cards.add(new SetCardInfo("Forbidden Crypt", 22, Rarity.RARE, mage.cards.f.ForbiddenCrypt.class));
        cards.add(new SetCardInfo("Forest", 292, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 293, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 294, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 295, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Wastes", 23, Rarity.RARE, mage.cards.f.ForsakenWastes.class));
        cards.add(new SetCardInfo("Frenetic Efreet", 324, Rarity.RARE, mage.cards.f.FreneticEfreet.class));
        cards.add(new SetCardInfo("Giant Mantis", 116, Rarity.COMMON, mage.cards.g.GiantMantis.class));
        cards.add(new SetCardInfo("Goblin Elite Infantry", 177, Rarity.COMMON, mage.cards.g.GoblinEliteInfantry.class));
        cards.add(new SetCardInfo("Goblin Scouts", 178, Rarity.UNCOMMON, mage.cards.g.GoblinScouts.class));
        cards.add(new SetCardInfo("Goblin Soothsayer", 179, Rarity.COMMON, mage.cards.g.GoblinSoothsayer.class));
        cards.add(new SetCardInfo("Goblin Tinkerer", 180, Rarity.COMMON, mage.cards.g.GoblinTinkerer.class));
        cards.add(new SetCardInfo("Granger Guildmage", 118, Rarity.COMMON, mage.cards.g.GrangerGuildmage.class));
        cards.add(new SetCardInfo("Grasslands", 296, Rarity.UNCOMMON, mage.cards.g.Grasslands.class));
        cards.add(new SetCardInfo("Gravebane Zombie", 25, Rarity.COMMON, mage.cards.g.GravebaneZombie.class));
        cards.add(new SetCardInfo("Grave Servitude", 24, Rarity.COMMON, mage.cards.g.GraveServitude.class));
        cards.add(new SetCardInfo("Grinning Totem", 268, Rarity.RARE, mage.cards.g.GrinningTotem.class));
        cards.add(new SetCardInfo("Hall of Gemstone", 119, Rarity.RARE, mage.cards.h.HallOfGemstone.class));
        cards.add(new SetCardInfo("Hammer of Bogardan", 181, Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Harbinger of Night", 26, Rarity.RARE, mage.cards.h.HarbingerOfNight.class));
        cards.add(new SetCardInfo("Harmattan Efreet", 69, Rarity.UNCOMMON, mage.cards.h.HarmattanEfreet.class));
        cards.add(new SetCardInfo("Hazerider Drake", 328, Rarity.UNCOMMON, mage.cards.h.HazeriderDrake.class));
        cards.add(new SetCardInfo("Healing Salve", 224, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Hivis of the Scale", 182, Rarity.RARE, mage.cards.h.HivisOfTheScale.class));
        cards.add(new SetCardInfo("Horrible Hordes", 269, Rarity.UNCOMMON, mage.cards.h.HorribleHordes.class));
        cards.add(new SetCardInfo("Igneous Golem", 270, Rarity.UNCOMMON, mage.cards.i.IgneousGolem.class));
        cards.add(new SetCardInfo("Illicit Auction", 183, Rarity.RARE, mage.cards.i.IllicitAuction.class));
        cards.add(new SetCardInfo("Illumination", 225, Rarity.UNCOMMON, mage.cards.i.Illumination.class));
        cards.add(new SetCardInfo("Incinerate", 184, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Infernal Contract", 27, Rarity.RARE, mage.cards.i.InfernalContract.class));
        cards.add(new SetCardInfo("Iron Tusk Elephant", 226, Rarity.UNCOMMON, mage.cards.i.IronTuskElephant.class));
        cards.add(new SetCardInfo("Island", 297, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 298, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 299, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 300, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivory Charm", 227, Rarity.COMMON, mage.cards.i.IvoryCharm.class));
        cards.add(new SetCardInfo("Jolrael's Centaur", 120, Rarity.COMMON, mage.cards.j.JolraelsCentaur.class));
        cards.add(new SetCardInfo("Jolt", 70, Rarity.COMMON, mage.cards.j.Jolt.class));
        cards.add(new SetCardInfo("Jungle Patrol", 121, Rarity.RARE, mage.cards.j.JunglePatrol.class));
        cards.add(new SetCardInfo("Jungle Troll", 329, Rarity.UNCOMMON, mage.cards.j.JungleTroll.class));
        cards.add(new SetCardInfo("Jungle Wurm", 122, Rarity.COMMON, mage.cards.j.JungleWurm.class));
        cards.add(new SetCardInfo("Kaervek's Torch", 185, Rarity.COMMON, mage.cards.k.KaerveksTorch.class));
        cards.add(new SetCardInfo("Karoo Meerkat", 123, Rarity.UNCOMMON, mage.cards.k.KarooMeerkat.class));
        cards.add(new SetCardInfo("Kukemssa Pirates", 71, Rarity.RARE, mage.cards.k.KukemssaPirates.class));
        cards.add(new SetCardInfo("Kukemssa Serpent", 72, Rarity.COMMON, mage.cards.k.KukemssaSerpent.class));
        cards.add(new SetCardInfo("Lead Golem", 271, Rarity.UNCOMMON, mage.cards.l.LeadGolem.class));
        cards.add(new SetCardInfo("Lightning Reflexes", 186, Rarity.COMMON, mage.cards.l.LightningReflexes.class));
        cards.add(new SetCardInfo("Lion's Eye Diamond", 272, Rarity.RARE, mage.cards.l.LionsEyeDiamond.class));
        cards.add(new SetCardInfo("Locust Swarm", 124, Rarity.UNCOMMON, mage.cards.l.LocustSwarm.class));
        cards.add(new SetCardInfo("Mana Prism", 273, Rarity.UNCOMMON, mage.cards.m.ManaPrism.class));
        cards.add(new SetCardInfo("Mangara's Tome", 274, Rarity.RARE, mage.cards.m.MangarasTome.class));
        cards.add(new SetCardInfo("Marble Diamond", 275, Rarity.UNCOMMON, mage.cards.m.MarbleDiamond.class));
        cards.add(new SetCardInfo("Maro", 126, Rarity.RARE, mage.cards.m.Maro.class));
        cards.add(new SetCardInfo("Melesse Spirit", 231, Rarity.UNCOMMON, mage.cards.m.MelesseSpirit.class));
        cards.add(new SetCardInfo("Memory Lapse", 74, Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Merfolk Raiders", 75, Rarity.COMMON, mage.cards.m.MerfolkRaiders.class));
        cards.add(new SetCardInfo("Merfolk Seer", 76, Rarity.COMMON, mage.cards.m.MerfolkSeer.class));
        cards.add(new SetCardInfo("Mind Harness", 78, Rarity.UNCOMMON, mage.cards.m.MindHarness.class));
        cards.add(new SetCardInfo("Mire Shade", 29, Rarity.UNCOMMON, mage.cards.m.MireShade.class));
        cards.add(new SetCardInfo("Mist Dragon", 79, Rarity.RARE, mage.cards.m.MistDragon.class));
        cards.add(new SetCardInfo("Moss Diamond", 277, Rarity.UNCOMMON, mage.cards.m.MossDiamond.class));
        cards.add(new SetCardInfo("Mountain", 301, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 302, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 303, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 304, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain Valley", 305, Rarity.UNCOMMON, mage.cards.m.MountainValley.class));
        cards.add(new SetCardInfo("Mtenda Griffin", 232, Rarity.UNCOMMON, mage.cards.m.MtendaGriffin.class));
        cards.add(new SetCardInfo("Mtenda Herder", 233, Rarity.COMMON, mage.cards.m.MtendaHerder.class));
        cards.add(new SetCardInfo("Mystical Tutor", 80, Rarity.UNCOMMON, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Natural Balance", 129, Rarity.RARE, mage.cards.n.NaturalBalance.class));
        cards.add(new SetCardInfo("Nettletooth Djinn", 130, Rarity.UNCOMMON, mage.cards.n.NettletoothDjinn.class));
        cards.add(new SetCardInfo("Nocturnal Raid", 30, Rarity.UNCOMMON, mage.cards.n.NocturnalRaid.class));
        cards.add(new SetCardInfo("Pacifism", 236, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Painful Memories", 31, Rarity.UNCOMMON, mage.cards.p.PainfulMemories.class));
        cards.add(new SetCardInfo("Patagia Golem", 278, Rarity.UNCOMMON, mage.cards.p.PatagiaGolem.class));
        cards.add(new SetCardInfo("Paupers' Cage", 279, Rarity.RARE, mage.cards.p.PaupersCage.class));
        cards.add(new SetCardInfo("Pearl Dragon", 237, Rarity.RARE, mage.cards.p.PearlDragon.class));
        cards.add(new SetCardInfo("Phyrexian Dreadnought", 280, Rarity.RARE, mage.cards.p.PhyrexianDreadnought.class));
        cards.add(new SetCardInfo("Phyrexian Purge", 333, Rarity.RARE, mage.cards.p.PhyrexianPurge.class));
        cards.add(new SetCardInfo("Phyrexian Tribute", 32, Rarity.RARE, mage.cards.p.PhyrexianTribute.class));
        cards.add(new SetCardInfo("Phyrexian Vault", 281, Rarity.UNCOMMON, mage.cards.p.PhyrexianVault.class));
        cards.add(new SetCardInfo("Plains", 306, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 307, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 308, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 309, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Political Trickery", 81, Rarity.RARE, mage.cards.p.PoliticalTrickery.class));
        cards.add(new SetCardInfo("Polymorph", 82, Rarity.RARE, mage.cards.p.Polymorph.class));
        cards.add(new SetCardInfo("Power Sink", 83, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Psychic Transfer", 85, Rarity.RARE, mage.cards.p.PsychicTransfer.class));
        cards.add(new SetCardInfo("Quirion Elves", 132, Rarity.COMMON, mage.cards.q.QuirionElves.class));
        cards.add(new SetCardInfo("Radiant Essence", 336, Rarity.UNCOMMON, mage.cards.r.RadiantEssence.class));
        cards.add(new SetCardInfo("Raging Spirit", 188, Rarity.COMMON, mage.cards.r.RagingSpirit.class));
        cards.add(new SetCardInfo("Rampant Growth", 133, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rashida Scalebane", 239, Rarity.RARE, mage.cards.r.RashidaScalebane.class));
        cards.add(new SetCardInfo("Ray of Command", 86, Rarity.COMMON, mage.cards.r.RayOfCommand.class));
        cards.add(new SetCardInfo("Reality Ripple", 87, Rarity.COMMON, mage.cards.r.RealityRipple.class));
        cards.add(new SetCardInfo("Reckless Embermage", 189, Rarity.RARE, mage.cards.r.RecklessEmbermage.class));
        cards.add(new SetCardInfo("Regeneration", 134, Rarity.COMMON, mage.cards.r.Regeneration.class));
        cards.add(new SetCardInfo("Reparations", 338, Rarity.RARE, mage.cards.r.Reparations.class));
        cards.add(new SetCardInfo("Restless Dead", 36, Rarity.COMMON, mage.cards.r.RestlessDead.class));
        cards.add(new SetCardInfo("Ritual of Steel", 240, Rarity.COMMON, mage.cards.r.RitualOfSteel.class));
        cards.add(new SetCardInfo("Rock Basilisk", 339, Rarity.RARE, mage.cards.r.RockBasilisk.class));
        cards.add(new SetCardInfo("Rocky Tar Pit", 310, Rarity.UNCOMMON, mage.cards.r.RockyTarPit.class));
        cards.add(new SetCardInfo("Sabertooth Cobra", 136, Rarity.COMMON, mage.cards.s.SabertoothCobra.class));
        cards.add(new SetCardInfo("Sacred Mesa", 241, Rarity.RARE, mage.cards.s.SacredMesa.class));
        cards.add(new SetCardInfo("Sandbar Crocodile", 88, Rarity.COMMON, mage.cards.s.SandbarCrocodile.class));
        cards.add(new SetCardInfo("Sandstorm", 137, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Sapphire Charm", 89, Rarity.COMMON, mage.cards.s.SapphireCharm.class));
        cards.add(new SetCardInfo("Savage Twister", 340, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Searing Spear Askari", 191, Rarity.COMMON, mage.cards.s.SearingSpearAskari.class));
        cards.add(new SetCardInfo("Sea Scryer", 90, Rarity.COMMON, mage.cards.s.SeaScryer.class));
        cards.add(new SetCardInfo("Seedling Charm", 138, Rarity.COMMON, mage.cards.s.SeedlingCharm.class));
        cards.add(new SetCardInfo("Seeds of Innocence", 139, Rarity.RARE, mage.cards.s.SeedsOfInnocence.class));
        cards.add(new SetCardInfo("Serene Heart", 140, Rarity.COMMON, mage.cards.s.SereneHeart.class));
        cards.add(new SetCardInfo("Sewer Rats", 37, Rarity.COMMON, mage.cards.s.SewerRats.class));
        cards.add(new SetCardInfo("Shadow Guildmage", 38, Rarity.COMMON, mage.cards.s.ShadowGuildmage.class));
        cards.add(new SetCardInfo("Shallow Grave", 39, Rarity.RARE, mage.cards.s.ShallowGrave.class));
        cards.add(new SetCardInfo("Shaper Guildmage", 91, Rarity.COMMON, mage.cards.s.ShaperGuildmage.class));
        cards.add(new SetCardInfo("Shauku's Minion", 343, Rarity.UNCOMMON, mage.cards.s.ShaukusMinion.class));
        cards.add(new SetCardInfo("Shauku, Endbringer", 40, Rarity.RARE, mage.cards.s.ShaukuEndbringer.class));
        cards.add(new SetCardInfo("Shimmer", 92, Rarity.RARE, mage.cards.s.Shimmer.class));
        cards.add(new SetCardInfo("Sidar Jabari", 243, Rarity.RARE, mage.cards.s.SidarJabari.class));
        cards.add(new SetCardInfo("Skulking Ghost", 41, Rarity.COMMON, mage.cards.s.SkulkingGhost.class));
        cards.add(new SetCardInfo("Sky Diamond", 284, Rarity.UNCOMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Soar", 93, Rarity.COMMON, mage.cards.s.Soar.class));
        cards.add(new SetCardInfo("Soul Rend", 42, Rarity.UNCOMMON, mage.cards.s.SoulRend.class));
        cards.add(new SetCardInfo("Spirit of the Night", 44, Rarity.RARE, mage.cards.s.SpiritOfTheNight.class));
        cards.add(new SetCardInfo("Spitting Earth", 193, Rarity.COMMON, mage.cards.s.SpittingEarth.class));
        cards.add(new SetCardInfo("Stalking Tiger", 141, Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Stone Rain", 194, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stupor", 45, Rarity.UNCOMMON, mage.cards.s.Stupor.class));
        cards.add(new SetCardInfo("Subterranean Spirit", 195, Rarity.RARE, mage.cards.s.SubterraneanSpirit.class));
        cards.add(new SetCardInfo("Sunweb", 246, Rarity.RARE, mage.cards.s.Sunweb.class));
        cards.add(new SetCardInfo("Suq'Ata Firewalker", 94, Rarity.UNCOMMON, mage.cards.s.SuqAtaFirewalker.class));
        cards.add(new SetCardInfo("Swamp", 311, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 312, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 313, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 314, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Talruum Minotaur", 196, Rarity.COMMON, mage.cards.t.TalruumMinotaur.class));
        cards.add(new SetCardInfo("Taniwha", 95, Rarity.RARE, mage.cards.t.Taniwha.class));
        cards.add(new SetCardInfo("Teeka's Dragon", 285, Rarity.RARE, mage.cards.t.TeekasDragon.class));
        cards.add(new SetCardInfo("Teferi's Curse", 96, Rarity.COMMON, mage.cards.t.TeferisCurse.class));
        cards.add(new SetCardInfo("Teferi's Drake", 97, Rarity.COMMON, mage.cards.t.TeferisDrake.class));
        cards.add(new SetCardInfo("Teferi's Isle", 315, Rarity.RARE, mage.cards.t.TeferisIsle.class));
        cards.add(new SetCardInfo("Telim'Tor's Darts", 286, Rarity.UNCOMMON, mage.cards.t.TelimTorsDarts.class));
        cards.add(new SetCardInfo("Thirst", 99, Rarity.COMMON, mage.cards.t.Thirst.class));
        cards.add(new SetCardInfo("Tidal Wave", 100, Rarity.UNCOMMON, mage.cards.t.TidalWave.class));
        cards.add(new SetCardInfo("Tranquil Domain", 143, Rarity.COMMON, mage.cards.t.TranquilDomain.class));
        cards.add(new SetCardInfo("Uktabi Wildcats", 146, Rarity.RARE, mage.cards.u.UktabiWildcats.class));
        cards.add(new SetCardInfo("Unfulfilled Desires", 345, Rarity.RARE, mage.cards.u.UnfulfilledDesires.class));
        cards.add(new SetCardInfo("Unseen Walker", 147, Rarity.UNCOMMON, mage.cards.u.UnseenWalker.class));
        cards.add(new SetCardInfo("Unyaro Bee Sting", 148, Rarity.UNCOMMON, mage.cards.u.UnyaroBeeSting.class));
        cards.add(new SetCardInfo("Unyaro Griffin", 248, Rarity.UNCOMMON, mage.cards.u.UnyaroGriffin.class));
        cards.add(new SetCardInfo("Vaporous Djinn", 101, Rarity.UNCOMMON, mage.cards.v.VaporousDjinn.class));
        cards.add(new SetCardInfo("Ventifact Bottle", 288, Rarity.RARE, mage.cards.v.VentifactBottle.class));
        cards.add(new SetCardInfo("Viashino Warrior", 200, Rarity.COMMON, mage.cards.v.ViashinoWarrior.class));
        cards.add(new SetCardInfo("Vigilant Martyr", 249, Rarity.UNCOMMON, mage.cards.v.VigilantMartyr.class));
        cards.add(new SetCardInfo("Village Elder", 149, Rarity.COMMON, mage.cards.v.VillageElder.class));
        cards.add(new SetCardInfo("Vitalizing Cascade", 346, Rarity.UNCOMMON, mage.cards.v.VitalizingCascade.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 201, Rarity.RARE, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 202, Rarity.UNCOMMON, mage.cards.v.VolcanicGeyser.class));
        cards.add(new SetCardInfo("Waiting in the Weeds", 150, Rarity.RARE, mage.cards.w.WaitingInTheWeeds.class));
        cards.add(new SetCardInfo("Wall of Roots", 151, Rarity.COMMON, mage.cards.w.WallOfRoots.class));
        cards.add(new SetCardInfo("Ward of Lights", 251, Rarity.COMMON, mage.cards.w.WardOfLights.class));
        cards.add(new SetCardInfo("Wild Elephant", 152, Rarity.COMMON, mage.cards.w.WildElephant.class));
        cards.add(new SetCardInfo("Wildfire Emissary", 203, Rarity.UNCOMMON, mage.cards.w.WildfireEmissary.class));
        cards.add(new SetCardInfo("Windreaper Falcon", 349, Rarity.UNCOMMON, mage.cards.w.WindreaperFalcon.class));
        cards.add(new SetCardInfo("Withering Boon", 50, Rarity.UNCOMMON, mage.cards.w.WitheringBoon.class));
        cards.add(new SetCardInfo("Worldly Tutor", 153, Rarity.UNCOMMON, mage.cards.w.WorldlyTutor.class));
        cards.add(new SetCardInfo("Zebra Unicorn", 350, Rarity.UNCOMMON, mage.cards.z.ZebraUnicorn.class));
        cards.add(new SetCardInfo("Zhalfirin Commander", 253, Rarity.UNCOMMON, mage.cards.z.ZhalfirinCommander.class));
        cards.add(new SetCardInfo("Zhalfirin Knight", 254, Rarity.COMMON, mage.cards.z.ZhalfirinKnight.class));
        cards.add(new SetCardInfo("Zirilan of the Claw", 204, Rarity.RARE, mage.cards.z.ZirilanOfTheClaw.class));
        cards.add(new SetCardInfo("Zuberi, Golden Feather", 255, Rarity.RARE, mage.cards.z.ZuberiGoldenFeather.class));
    }
}
