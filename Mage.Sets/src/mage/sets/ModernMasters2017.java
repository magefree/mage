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
public class ModernMasters2017 extends ExpansionSet {

    private static final ModernMasters2017 fINSTANCE = new ModernMasters2017();

    public static ModernMasters2017 getInstance() {
        return fINSTANCE;
    }

    private ModernMasters2017() {
        super("Modern Masters 2017", "MM3", ExpansionSet.buildDate(2017, 3, 17), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abrupt Decay", 146, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Abyssal Specter", 59, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 228, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Arid Mesa", 229, Rarity.RARE, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Augur of Bolas", 30, Rarity.COMMON, mage.cards.a.AugurOfBolas.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 230, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Azorius Signet", 215, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Basilisk Collar", 216, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Blade Splicer", 3, Rarity.RARE, mage.cards.b.BladeSplicer.class));
        cards.add(new SetCardInfo("Blood Moon", 90, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Bone Splinters", 60, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Bonfire of the Damned", 91, Rarity.MYTHIC, mage.cards.b.BonfireOfTheDamned.class));
        cards.add(new SetCardInfo("Boros Guildgate", 231, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Boros Signet", 217, Rarity.UNCOMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Burning-Tree Emissary", 207, Rarity.COMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Cavern of Souls", 232, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Compulsive Research", 31, Rarity.UNCOMMON, mage.cards.c.CompulsiveResearch.class));
        cards.add(new SetCardInfo("Craterhoof Behemoth", 122, Rarity.MYTHIC, mage.cards.c.CraterhoofBehemoth.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 158, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 233, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Damnation", 63, Rarity.RARE, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Death's Shadow", 64, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Deadeye Navigator", 34, Rarity.RARE, mage.cards.d.DeadeyeNavigator.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 234, Rarity.COMMON, mage.cards.d.DimirGuildgate.class));
        cards.add(new SetCardInfo("Dimir Signet", 219, Rarity.UNCOMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Domri Rade", 161, Rarity.MYTHIC, mage.cards.d.DomriRade.class));
        cards.add(new SetCardInfo("Entreat the Angels", 4, Rarity.MYTHIC, mage.cards.e.EntreatTheAngels.class));
        cards.add(new SetCardInfo("Extractor Demon", 69, Rarity.RARE, mage.cards.e.ExtractorDemon.class));
        cards.add(new SetCardInfo("Falkenrath Aristocrat", 163, Rarity.RARE, mage.cards.f.FalkenrathAristocrat.class));
        cards.add(new SetCardInfo("Fiery Justice", 164, Rarity.RARE, mage.cards.f.FieryJustice.class));
        cards.add(new SetCardInfo("Flickerwisp", 6, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 40, Rarity.RARE, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Gnawing Zombie", 71, Rarity.COMMON, mage.cards.g.GnawingZombie.class));
        cards.add(new SetCardInfo("Goblin Assault", 95, Rarity.UNCOMMON, mage.cards.g.GoblinAssault.class));
        cards.add(new SetCardInfo("Goblin Guide", 96, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 235, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Golgari Signet", 220, Rarity.UNCOMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Grafdigger's Cage", 221, Rarity.RARE, mage.cards.g.GrafdiggersCage.class));
        cards.add(new SetCardInfo("Griselbrand", 72, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 236, Rarity.COMMON, mage.cards.g.GruulGuildgate.class));
        cards.add(new SetCardInfo("Gruul Signet", 222, Rarity.UNCOMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Harmonize", 128, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 75, Rarity.UNCOMMON, mage.cards.i.InquisitionOfKozilek.class));
        cards.add(new SetCardInfo("Intangible Virtue", 9, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Izzet Charm", 171, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Izzet Guildgate", 237, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class));
        cards.add(new SetCardInfo("Izzet Signet", 223, Rarity.UNCOMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Jungle Shrine", 238, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 76, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Lingering Souls", 12, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Linvala, Keeper of Silence", 13, Rarity.MYTHIC, mage.cards.l.LinvalaKeeperOfSilence.class));
        cards.add(new SetCardInfo("Magma Jet", 100, Rarity.COMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Marsh Flats", 239, Rarity.RARE, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Master Splicer", 15, Rarity.UNCOMMON, mage.cards.m.MasterSplicer.class));
        cards.add(new SetCardInfo("Might of Old Krosa", 130, Rarity.UNCOMMON, mage.cards.m.MightOfOldKrosa.class));
        cards.add(new SetCardInfo("Mist Raven", 43, Rarity.COMMON, mage.cards.m.MistRaven.class));
        cards.add(new SetCardInfo("Misty Rainforest", 240, Rarity.RARE, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Molten Rain", 103, Rarity.UNCOMMON, mage.cards.m.MoltenRain.class));
        cards.add(new SetCardInfo("Momentary Blink", 16, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Mortician Beetle", 78, Rarity.COMMON, mage.cards.m.MorticianBeetle.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Dracogenius", 175, Rarity.RARE, mage.cards.n.NivMizzetDracogenius.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 241, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Orzhov Signet", 224, Rarity.UNCOMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Past in Flames", 105, Rarity.MYTHIC, mage.cards.p.PastInFlames.class));
        cards.add(new SetCardInfo("Path to Exile", 17, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Phantasmal Image", 46, Rarity.RARE, mage.cards.p.PhantasmalImage.class));
        cards.add(new SetCardInfo("Primal Command", 132, Rarity.RARE, mage.cards.p.PrimalCommand.class));
        cards.add(new SetCardInfo("Pyromancer Ascension", 108, Rarity.RARE, mage.cards.p.PyromancerAscension.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 242, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class));
        cards.add(new SetCardInfo("Rakdos Signet", 225, Rarity.UNCOMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Ranger of Eos", 19, Rarity.RARE, mage.cards.r.RangerOfEos.class));
        cards.add(new SetCardInfo("Restoration Angel", 20, Rarity.RARE, mage.cards.r.RestorationAngel.class));
        cards.add(new SetCardInfo("Rhox War Monk", 180, Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Savage Lands", 243, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scalding Tarn", 244, Rarity.RARE, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 134, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Seance", 22, Rarity.RARE, mage.cards.s.Seance.class));
        cards.add(new SetCardInfo("Seaside Citadel", 245, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Sedraxis Specter", 181, Rarity.UNCOMMON, mage.cards.s.SedraxisSpecter.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 246, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Selesnya Signet", 226, Rarity.UNCOMMON, mage.cards.s.SelesnyaSignet.class));
        cards.add(new SetCardInfo("Sensor Splicer", 23, Rarity.COMMON, mage.cards.s.SensorSplicer.class));
        cards.add(new SetCardInfo("Serum Visions", 49, Rarity.UNCOMMON, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Simic Guildgate", 248, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Simic Signet", 227, Rarity.UNCOMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 50, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Sphinx's Revelation", 187, Rarity.MYTHIC, mage.cards.s.SphinxsRevelation.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 189, Rarity.UNCOMMON, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Stoic Angel", 190, Rarity.RARE, mage.cards.s.StoicAngel.class));
        cards.add(new SetCardInfo("Stony Silence", 25, Rarity.RARE, mage.cards.s.StonySilence.class));
        cards.add(new SetCardInfo("Tarmogoyf", 141, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Temporal Mastery", 54, Rarity.MYTHIC, mage.cards.t.TemporalMastery.class));
        cards.add(new SetCardInfo("Terminate", 194, Rarity.UNCOMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terminus", 26, Rarity.RARE, mage.cards.t.Terminus.class));
        cards.add(new SetCardInfo("Thragtusk", 143, Rarity.RARE, mage.cards.t.Thragtusk.class));
        cards.add(new SetCardInfo("Vital Splicer", 145, Rarity.UNCOMMON, mage.cards.v.VitalSplicer.class));
        cards.add(new SetCardInfo("Tower Gargoyle", 196, Rarity.UNCOMMON, mage.cards.t.TowerGargoyle.class));
        cards.add(new SetCardInfo("Vanish into Memory", 199, Rarity.UNCOMMON, mage.cards.v.VanishIntoMemory.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 55, Rarity.RARE, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 249, Rarity.RARE, mage.cards.v.VerdantCatacombs.class));
        cards.add(new SetCardInfo("Vithian Stinger", 115, Rarity.UNCOMMON, mage.cards.v.VithianStinger.class));
        cards.add(new SetCardInfo("Voice of Resurgence", 200, Rarity.MYTHIC, mage.cards.v.VoiceOfResurgence.class));
        cards.add(new SetCardInfo("Wing Splicer", 57, Rarity.UNCOMMON, mage.cards.w.WingSplicer.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 203, Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 204, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class));
    }

}
