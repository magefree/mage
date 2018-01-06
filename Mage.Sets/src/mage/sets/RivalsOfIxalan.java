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
import mage.cards.o.OrazcaFrillback;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class RivalsOfIxalan extends ExpansionSet {

    private static final RivalsOfIxalan instance = new RivalsOfIxalan();

    public static RivalsOfIxalan getInstance() {
        return instance;
    }

    private RivalsOfIxalan() {
        super("Rivals of Ixalan", "RIX", ExpansionSet.buildDate(2018, 1, 19), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.parentSet = Ixalan.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Admiral's Order", 31, Rarity.RARE, mage.cards.a.AdmiralsOrder.class));
        cards.add(new SetCardInfo("Aggressive Urge", 122, Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Angrath's Ambusher", 202, Rarity.UNCOMMON, mage.cards.a.AngrathsAmbusher.class));
        cards.add(new SetCardInfo("Angrath's Fury", 204, Rarity.RARE, mage.cards.a.AngrathsFury.class));
        cards.add(new SetCardInfo("Angrath, Minotaur Pirate", 201, Rarity.MYTHIC, mage.cards.a.AngrathMinotaurPirate.class));
        cards.add(new SetCardInfo("Arterial Flow", 62, Rarity.UNCOMMON, mage.cards.a.ArterialFlow.class));
        cards.add(new SetCardInfo("Atzal, Cave of Eternity", 160, Rarity.RARE, mage.cards.a.AtzalCaveOfEternity.class));
        cards.add(new SetCardInfo("Atzocan Seer", 153, Rarity.UNCOMMON, mage.cards.a.AtzocanSeer.class));
        cards.add(new SetCardInfo("Baffling End", 1, Rarity.UNCOMMON, mage.cards.b.BafflingEnd.class));
        cards.add(new SetCardInfo("Bombard", 93, Rarity.COMMON, mage.cards.b.Bombard.class));
        cards.add(new SetCardInfo("Brass's Bounty", 94, Rarity.RARE, mage.cards.b.BrasssBounty.class));
        cards.add(new SetCardInfo("Captain's Hook", 177, Rarity.RARE, mage.cards.c.CaptainsHook.class));
        cards.add(new SetCardInfo("Champion of Dusk", 64, Rarity.RARE, mage.cards.c.ChampionOfDusk.class));
        cards.add(new SetCardInfo("Cinder Barrens", 205, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 125, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Deeproot Elite", 127, Rarity.RARE, mage.cards.d.DeeprootElite.class));
        cards.add(new SetCardInfo("Dire Fleet Daredevil", 99, Rarity.RARE, mage.cards.d.DireFleetDaredevil.class));
        cards.add(new SetCardInfo("Dire Fleet Neckbreaker", 156, Rarity.UNCOMMON, mage.cards.d.DireFleetNeckbreaker.class));
        cards.add(new SetCardInfo("Dire Fleet Poisoner", 68, Rarity.RARE, mage.cards.d.DireFleetPoisoner.class));
        cards.add(new SetCardInfo("Divine Verdict", 5, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Dusk Charger", 69, Rarity.COMMON, mage.cards.d.DuskCharger.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 70, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Elenda, the Dusk Rose", 157, Rarity.MYTHIC, mage.cards.e.ElendaTheDuskRose.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 100, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Evolving Wilds", 186, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Famished Paladin", 8, Rarity.UNCOMMON, mage.cards.f.FamishedPaladin.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 101, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Flood of Recollection", 38, Rarity.UNCOMMON, mage.cards.f.FloodOfRecollection.class));
        cards.add(new SetCardInfo("Forerunner of the Coalition", 72, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheCoalition.class));
        cards.add(new SetCardInfo("Forerunner of the Empire", 102, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheEmpire.class));
        cards.add(new SetCardInfo("Forerunner of the Heralds", 129, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheHeralds.class));
        cards.add(new SetCardInfo("Forerunner of the Legion", 9, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheLegion.class));
        cards.add(new SetCardInfo("Forest", 196, Rarity.COMMON, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Forsaken Sanctuary", 187, Rarity.UNCOMMON, mage.cards.f.ForsakenSanctuary.class));
        cards.add(new SetCardInfo("Foul Orchard", 188, Rarity.UNCOMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 130, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Radiant Destiny", 18, Rarity.RARE, mage.cards.r.RadiantDestiny.class));
        cards.add(new SetCardInfo("Hadana's Climb", 158, Rarity.RARE, mage.cards.h.HadanasClimb.class));
        cards.add(new SetCardInfo("Highland Lake", 189, Rarity.UNCOMMON, mage.cards.h.HighlandLake.class));
        cards.add(new SetCardInfo("Hunt the Weak", 133, Rarity.COMMON, mage.cards.h.HuntTheWeak.class));
        cards.add(new SetCardInfo("Impale", 76, Rarity.COMMON, mage.cards.i.Impale.class));
        cards.add(new SetCardInfo("Island", 193, Rarity.COMMON, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Jade Bearer", 134, Rarity.COMMON, mage.cards.j.JadeBearer.class));
        cards.add(new SetCardInfo("Jadecraft Artisan", 135, Rarity.COMMON, mage.cards.j.JadecraftArtisan.class));
        cards.add(new SetCardInfo("Jadelight Ranger", 136, Rarity.RARE, mage.cards.j.JadelightRanger.class));
        cards.add(new SetCardInfo("Jungle Creeper", 161, Rarity.UNCOMMON, mage.cards.j.JungleCreeper.class));
        cards.add(new SetCardInfo("Jungleborn Pioneer", 137, Rarity.COMMON, mage.cards.j.JunglebornPioneer.class));
        cards.add(new SetCardInfo("Journey to Eternity", 160, Rarity.RARE, mage.cards.j.JourneyToEternity.class));
        cards.add(new SetCardInfo("Kitesail Corsair", 41, Rarity.COMMON, mage.cards.k.KitesailCorsair.class));
        cards.add(new SetCardInfo("Knight of the Stampede", 138, Rarity.COMMON, mage.cards.k.KnightOfTheStampede.class));
        cards.add(new SetCardInfo("Kumena's Awakening", 42, Rarity.RARE, mage.cards.k.KumenasAwakening.class));
        cards.add(new SetCardInfo("Kumena, Tyrant of Orazca", 162, Rarity.MYTHIC, mage.cards.k.KumenaTyrantOfOrazca.class));
        cards.add(new SetCardInfo("Legion Conquistador", 11, Rarity.COMMON, mage.cards.l.LegionConquistador.class));
        cards.add(new SetCardInfo("Legion Lieutenant", 163, Rarity.UNCOMMON, mage.cards.l.LegionLieutenant.class));
        cards.add(new SetCardInfo("Luminous Bonds", 12, Rarity.COMMON, mage.cards.l.LuminousBonds.class));
        cards.add(new SetCardInfo("Merfolk Mistbinder", 164, Rarity.UNCOMMON, mage.cards.m.MerfolkMistbinder.class));
        cards.add(new SetCardInfo("Mountain", 195, Rarity.COMMON, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Naturalize", 139, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Negate", 44, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nezahal, Primal Tide", 45, Rarity.RARE, mage.cards.n.NezahalPrimalTide.class));
        cards.add(new SetCardInfo("Orazca Frillback", 140, Rarity.COMMON, OrazcaFrillback.class));
        cards.add(new SetCardInfo("Overgrown Armasaur", 141, Rarity.COMMON, mage.cards.o.OvergrownArmasaur.class));
        cards.add(new SetCardInfo("Paladin of Atonement", 16, Rarity.RARE, mage.cards.p.PaladinOfAtonement.class));
        cards.add(new SetCardInfo("Path of Discovery", 142, Rarity.RARE, mage.cards.p.PathOfDiscovery.class));
        cards.add(new SetCardInfo("Pirate's Pillage", 109, Rarity.UNCOMMON, mage.cards.p.PiratesPillage.class));
        cards.add(new SetCardInfo("Plains", 192, Rarity.COMMON, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Plummet", 143, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Polyraptor", 144, Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Raptor Companion", 19, Rarity.COMMON, mage.cards.r.RaptorCompanion.class));
        cards.add(new SetCardInfo("Recover", 84, Rarity.COMMON, mage.cards.r.Recover.class));
        cards.add(new SetCardInfo("Sailor of Means", 49, Rarity.COMMON, mage.cards.s.SailorOfMeans.class));
        cards.add(new SetCardInfo("Seafloor Oracle", 51, Rarity.RARE, mage.cards.s.SeafloorOracle.class));
        cards.add(new SetCardInfo("Secrets of the Golden City", 52, Rarity.COMMON, mage.cards.s.SecretsOfTheGoldenCity.class));
        cards.add(new SetCardInfo("Shake the Foundations", 113, Rarity.UNCOMMON, mage.cards.s.ShakeTheFoundations.class));
        cards.add(new SetCardInfo("Shatter", 114, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Silent Gravestone", 182, Rarity.RARE, mage.cards.s.SilentGravestone.class));
        cards.add(new SetCardInfo("Silvergill Adept", 53, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Skymarcher Aspirant", 21, Rarity.UNCOMMON, mage.cards.s.SkymarcherAspirant.class));
        cards.add(new SetCardInfo("Sphinx's Decree", 24, Rarity.RARE, mage.cards.s.SphinxsDecree.class));
        cards.add(new SetCardInfo("Stone Quarry", 190, Rarity.UNCOMMON, mage.cards.s.StoneQuarry.class));
        cards.add(new SetCardInfo("Storm Fleet Sprinter", 172, Rarity.UNCOMMON, mage.cards.s.StormFleetSprinter.class));
        cards.add(new SetCardInfo("Storm the Vault", 173, Rarity.RARE, mage.cards.s.StormTheVault.class));
        cards.add(new SetCardInfo("Strength of the Pack", 145, Rarity.UNCOMMON, mage.cards.s.StrengthOfThePack.class));
        cards.add(new SetCardInfo("Strider Harness", 183, Rarity.COMMON, mage.cards.s.StriderHarness.class));
        cards.add(new SetCardInfo("Swab Goblin", 203, Rarity.COMMON, mage.cards.s.SwabGoblin.class));
        cards.add(new SetCardInfo("Swamp", 194, Rarity.COMMON, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", 86, Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class));
        cards.add(new SetCardInfo("The Immortal Sun", 180, Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 148, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 184, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Trapjaw Tyrant", 29, Rarity.MYTHIC, mage.cards.t.TrapjawTyrant.class));
        cards.add(new SetCardInfo("Vampire Champion", 198, Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vault of Catlacan", 173, Rarity.RARE, mage.cards.v.VaultOfCatlacan.class));
        cards.add(new SetCardInfo("Vona's Hunger", 90, Rarity.RARE, mage.cards.v.VonasHunger.class));
        cards.add(new SetCardInfo("Vraska's Conquistador", 199, Rarity.UNCOMMON, mage.cards.v.VraskasConquistador.class));
        cards.add(new SetCardInfo("Vraska's Scorn", 200, Rarity.RARE, mage.cards.v.VraskasScorn.class));
        cards.add(new SetCardInfo("Vraska, Scheming Gorgon", 197, Rarity.MYTHIC, mage.cards.v.VraskaSchemingGorgon.class));
        cards.add(new SetCardInfo("Warkite Marauder", 60, Rarity.RARE, mage.cards.w.WarkiteMarauder.class));
        cards.add(new SetCardInfo("Waterknot", 61, Rarity.COMMON, mage.cards.w.Waterknot.class));
        cards.add(new SetCardInfo("Wayward Swordtooth", 150, Rarity.RARE, mage.cards.w.WaywardSwordtooth.class));
        cards.add(new SetCardInfo("Winged Temple of Orazca", 158, Rarity.RARE, mage.cards.w.WingedTempleOfOrazca.class));
        cards.add(new SetCardInfo("Woodland Stream", 191, Rarity.UNCOMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("World Shaper", 151, Rarity.RARE, mage.cards.w.WorldShaper.class));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", 30, Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class));
    }
}
