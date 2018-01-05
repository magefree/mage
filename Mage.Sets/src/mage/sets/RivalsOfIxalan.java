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

        cards.add(new SetCardInfo("Angrath's Ambusher", 202, Rarity.UNCOMMON, mage.cards.a.AngrathsAmbusher.class));
        cards.add(new SetCardInfo("Angrath's Fury", 204, Rarity.RARE, mage.cards.a.AngrathsFury.class));
        cards.add(new SetCardInfo("Angrath, Minotaur Pirate", 201, Rarity.MYTHIC, mage.cards.a.AngrathMinotaurPirate.class));
        cards.add(new SetCardInfo("Atzal, Cave of Eternity", 160, Rarity.RARE, mage.cards.a.AtzalCaveOfEternity.class));
        cards.add(new SetCardInfo("Brass's Bounty", 94, Rarity.RARE, mage.cards.b.BrasssBounty.class));
        cards.add(new SetCardInfo("Buffling End", 1, Rarity.UNCOMMON, mage.cards.m.BafflingEnd.class));
        cards.add(new SetCardInfo("Captain's Hook", 177, Rarity.RARE, mage.cards.c.CaptainsHook.class));
        cards.add(new SetCardInfo("Cinder Barrens", 205, Rarity.RARE, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Deeproot Elite", 127, Rarity.RARE, mage.cards.d.DeeprootElite.class));
        cards.add(new SetCardInfo("Dire Fleet Daredevil", 99, Rarity.RARE, mage.cards.d.DireFleetDaredevil.class));
        cards.add(new SetCardInfo("Dire Fleet Neckbreaker", 156, Rarity.UNCOMMON, mage.cards.d.DireFleetNeckbreaker.class));
        cards.add(new SetCardInfo("Dire Fleet Poisoner", 68, Rarity.RARE, mage.cards.d.DireFleetPoisoner.class));
        cards.add(new SetCardInfo("Dusk Charger", 69, Rarity.COMMON, mage.cards.d.DuskCharger.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 70, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Elenda, the Dusk Rose", 157, Rarity.MYTHIC, mage.cards.e.ElendaTheDuskRose.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 100, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Evolving Wilds", 186, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 101, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Forerunner of the Coalition", 72, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheCoalition.class));
        cards.add(new SetCardInfo("Forerunner of the Empire", 102, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheEmpire.class));
        cards.add(new SetCardInfo("Forerunner of the Heralds", 129, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheHeralds.class));
        cards.add(new SetCardInfo("Forerunner of the Legion", 9, Rarity.UNCOMMON, mage.cards.f.ForerunnerOfTheLegion.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 130, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Glorious Destiny", 18, Rarity.RARE, mage.cards.g.GloriousDestiny.class));
        cards.add(new SetCardInfo("Hadana's Climb", 158, Rarity.RARE, mage.cards.h.HadanasClimb.class));
        cards.add(new SetCardInfo("Jadelight Ranger", 136, Rarity.RARE, mage.cards.j.JadelightRanger.class));
        cards.add(new SetCardInfo("Journey to Eternity", 160, Rarity.RARE, mage.cards.j.JourneyToEternity.class));
        cards.add(new SetCardInfo("Jungle Creeper", 161, Rarity.UNCOMMON, mage.cards.j.JungleCreeper.class));
        cards.add(new SetCardInfo("Kumena's Awakening", 42, Rarity.RARE, mage.cards.k.KumenasAwakening.class));
        cards.add(new SetCardInfo("Kumena, Tyrant of Orazca", 162, Rarity.MYTHIC, mage.cards.k.KumenaTyrantOfOrazca.class));
        cards.add(new SetCardInfo("Legion Lieutenant", 163, Rarity.UNCOMMON, mage.cards.l.LegionLieutenant.class));
        cards.add(new SetCardInfo("Merfolk Mistbinder", 164, Rarity.UNCOMMON, mage.cards.m.MerfolkMistbinder.class));
        cards.add(new SetCardInfo("Nezahal, Primal Tide", 45, Rarity.MYTHIC, mage.cards.n.NezahalPrimalTide.class));
        cards.add(new SetCardInfo("Paladin of Atonement", 16, Rarity.RARE, mage.cards.p.PaladinOfAtonement.class));
        cards.add(new SetCardInfo("Path to Discovery", 142, Rarity.RARE, mage.cards.p.PathToDiscovery.class));
        cards.add(new SetCardInfo("Pirate's Pillage", 109, Rarity.UNCOMMON, mage.cards.p.PiratesPillage.class));
        cards.add(new SetCardInfo("Polyraptor", 144, Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Seafloor Oracle", 51, Rarity.RARE, mage.cards.s.SeafloorOracle.class));
        cards.add(new SetCardInfo("Secrets of the Golden City", 52, Rarity.COMMON, mage.cards.s.SecretsOfTheGoldenCity.class));
        cards.add(new SetCardInfo("Silent Gravestone", 182, Rarity.RARE, mage.cards.s.SilentGravestone.class));
        cards.add(new SetCardInfo("Silvergill Adept", 53, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Skymarcher Aspirant", 21, Rarity.UNCOMMON, mage.cards.s.SkymarcherAspirant.class));
        cards.add(new SetCardInfo("Sphinx's Decree", 24, Rarity.RARE, mage.cards.s.SphinxsDecree.class));
        cards.add(new SetCardInfo("Storm Fleet Sprinter", 172, Rarity.UNCOMMON, mage.cards.s.StormFleetSprinter.class));
        cards.add(new SetCardInfo("Storm the Vault", 173, Rarity.RARE, mage.cards.s.StormTheVault.class));
        cards.add(new SetCardInfo("Swab Goblin", 203, Rarity.COMMON, mage.cards.s.SwabGoblin.class));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", 86, Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class));
        cards.add(new SetCardInfo("The Immortal Sun", 180, Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class));
        cards.add(new SetCardInfo("Vampire Champion", 198, Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vault of Catlacan", 173, Rarity.RARE, mage.cards.v.VaultOfCatlacan.class));
        cards.add(new SetCardInfo("Vicious Cagemaw", 29, Rarity.MYTHIC, mage.cards.v.ViciousCagemaw.class));
        cards.add(new SetCardInfo("Vona's Hunger", 90, Rarity.RARE, mage.cards.v.VonasHunger.class));
        cards.add(new SetCardInfo("Vraska's Conquistador", 199, Rarity.UNCOMMON, mage.cards.v.VraskasConquistador.class));
        cards.add(new SetCardInfo("Vraska's Scorn", 200, Rarity.RARE, mage.cards.v.VraskasScorn.class));
        cards.add(new SetCardInfo("Vraska, Scheming Gorgon", 197, Rarity.MYTHIC, mage.cards.v.VraskaSchemingGorgon.class));
        cards.add(new SetCardInfo("Warkite Marauder", 60, Rarity.RARE, mage.cards.w.WarkiteMarauder.class));
        cards.add(new SetCardInfo("Wayward Swordtooth", 150, Rarity.RARE, mage.cards.w.WaywardSwordtooth.class));
        cards.add(new SetCardInfo("Winged Temple of Orazca", 158, Rarity.RARE, mage.cards.w.WingedTempleOfOrazca.class));
        cards.add(new SetCardInfo("World Shaper", 151, Rarity.RARE, mage.cards.w.WorldShaper.class));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", 30, Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class));
    }
}
