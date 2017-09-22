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
import mage.cards.a.AbbeyMatron;
import mage.cards.a.AlibansTower;
import mage.cards.c.CemeteryGate;
import mage.cards.d.DrySpell;
import mage.cards.d.DwarvenTrader;
import mage.cards.f.FeastOfTheUnicorn;
import mage.cards.f.FolkOfAnHavva;
import mage.cards.m.MesaFalcon;
import mage.cards.r.ReefPirates;
import mage.cards.s.SengirBats;
import mage.cards.t.Torture;
import mage.cards.w.WillowFaerie;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Homelands extends ExpansionSet {

    private static final Homelands instance = new Homelands();

    public static Homelands getInstance() {
        return instance;
    }

    private Homelands() {
        super("Homelands", "HML", ExpansionSet.buildDate(1995, 9, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Aether Storm", 26, Rarity.UNCOMMON, mage.cards.a.AetherStorm.class));
        cards.add(new SetCardInfo("Abbey Gargoyles", 101, Rarity.UNCOMMON, mage.cards.a.AbbeyGargoyles.class));
        cards.add(new SetCardInfo("Abbey Matron", 102, Rarity.COMMON, AbbeyMatron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abbey Matron", 103, Rarity.COMMON, AbbeyMatron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aliban's Tower", 76, Rarity.COMMON, AlibansTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aliban's Tower", 77, Rarity.COMMON, AlibansTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambush", 78, Rarity.COMMON, mage.cards.a.Ambush.class));
        cards.add(new SetCardInfo("Ambush Party", 79, Rarity.COMMON, mage.cards.a.AmbushParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambush Party", 80, Rarity.COMMON, mage.cards.a.AmbushParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("An-Zerrin Ruins", 87, Rarity.RARE, mage.cards.a.AnZerrinRuins.class));
        cards.add(new SetCardInfo("Anaba Ancestor", 81, Rarity.RARE, mage.cards.a.AnabaAncestor.class));
        cards.add(new SetCardInfo("Anaba Bodyguard", 82, Rarity.COMMON, mage.cards.a.AnabaBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Bodyguard", 83, Rarity.COMMON, mage.cards.a.AnabaBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Shaman", 84, Rarity.COMMON, mage.cards.a.AnabaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Shaman", 85, Rarity.COMMON, mage.cards.a.AnabaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Spirit Crafter", 86, Rarity.RARE, mage.cards.a.AnabaSpiritCrafter.class));
        cards.add(new SetCardInfo("An-Havva Constable", 51, Rarity.RARE, mage.cards.a.AnHavvaConstable.class));
        cards.add(new SetCardInfo("An-Havva Inn", 52, Rarity.UNCOMMON, mage.cards.a.AnHavvaInn.class));
        cards.add(new SetCardInfo("An-Havva Township", 136, Rarity.UNCOMMON, mage.cards.a.AnHavvaTownship.class));
        cards.add(new SetCardInfo("An-Zerrin Ruins", 87, Rarity.RARE, mage.cards.a.AnZerrinRuins.class));
        cards.add(new SetCardInfo("Aysen Abbey", 137, Rarity.UNCOMMON, mage.cards.a.AysenAbbey.class));
        cards.add(new SetCardInfo("Aysen Bureaucrats", 104, Rarity.COMMON, mage.cards.a.AysenBureaucrats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aysen Bureaucrats", 105, Rarity.COMMON, mage.cards.a.AysenBureaucrats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aysen Crusader", 106, Rarity.RARE, mage.cards.a.AysenCrusader.class));
        cards.add(new SetCardInfo("Aysen Highway", 107, Rarity.RARE, mage.cards.a.AysenHighway.class));
        cards.add(new SetCardInfo("Baki's Curse", 27, Rarity.RARE, mage.cards.b.BakisCurse.class));
        cards.add(new SetCardInfo("Baron Sengir", 1, Rarity.RARE, mage.cards.b.BaronSengir.class));
        cards.add(new SetCardInfo("Black Carriage", 2, Rarity.RARE, mage.cards.b.BlackCarriage.class));
        cards.add(new SetCardInfo("Carapace", 55, Rarity.COMMON, mage.cards.c.Carapace.class));
        cards.add(new SetCardInfo("Castle Sengir", 138, Rarity.UNCOMMON, mage.cards.c.CastleSengir.class));
        cards.add(new SetCardInfo("Cemetery Gate", 4, Rarity.COMMON, CemeteryGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Gate", 5, Rarity.COMMON, CemeteryGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chain Stasis", 28, Rarity.RARE, mage.cards.c.ChainStasis.class));
        cards.add(new SetCardInfo("Chandler", 88, Rarity.COMMON, mage.cards.c.Chandler.class));
        cards.add(new SetCardInfo("Clockwork Gnomes", 127, Rarity.COMMON, mage.cards.c.ClockworkGnomes.class));
        cards.add(new SetCardInfo("Coral Reef", 29, Rarity.COMMON, mage.cards.c.CoralReef.class));
        cards.add(new SetCardInfo("Dark Maze", 31, Rarity.COMMON, mage.cards.d.DarkMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daughter of Autumn", 56, Rarity.RARE, mage.cards.d.DaughterOfAutumn.class));
        cards.add(new SetCardInfo("Death Speakers", 109, Rarity.UNCOMMON, mage.cards.d.DeathSpeakers.class));
        cards.add(new SetCardInfo("Didgeridoo", 130, Rarity.RARE, mage.cards.d.Didgeridoo.class));
        cards.add(new SetCardInfo("Drudge Spell", 6, Rarity.UNCOMMON, mage.cards.d.DrudgeSpell.class));
        cards.add(new SetCardInfo("Dry Spell", 7, Rarity.COMMON, DrySpell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dry Spell", 8, Rarity.COMMON, DrySpell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Pony", 89, Rarity.RARE, mage.cards.d.DwarvenPony.class));
        cards.add(new SetCardInfo("Dwarven Trader", 91, Rarity.COMMON, DwarvenTrader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Trader", 92, Rarity.COMMON, DwarvenTrader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ebony Rhino", 131, Rarity.COMMON, mage.cards.e.EbonyRhino.class));
        cards.add(new SetCardInfo("Eron the Relentless", 93, Rarity.UNCOMMON, mage.cards.e.EronTheRelentless.class));
        cards.add(new SetCardInfo("Evaporate", 94, Rarity.UNCOMMON, mage.cards.e.Evaporate.class));
        cards.add(new SetCardInfo("Faerie Noble", 57, Rarity.RARE, mage.cards.f.FaerieNoble.class));
        cards.add(new SetCardInfo("Feast of the Unicorn", 9, Rarity.COMMON, FeastOfTheUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feast of the Unicorn", 10, Rarity.COMMON, FeastOfTheUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feroz's Ban", 132, Rarity.RARE, mage.cards.f.FerozsBan.class));
        cards.add(new SetCardInfo("Folk of An-Havva", 58, Rarity.COMMON, FolkOfAnHavva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Folk of An-Havva", 59, Rarity.COMMON, FolkOfAnHavva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forget", 32, Rarity.RARE, mage.cards.f.Forget.class));
        cards.add(new SetCardInfo("Ghost Hounds", 12, Rarity.UNCOMMON, mage.cards.g.GhostHounds.class));
        cards.add(new SetCardInfo("Grandmother Sengir", 13, Rarity.RARE, mage.cards.g.GrandmotherSengir.class));
        cards.add(new SetCardInfo("Headstone", 15, Rarity.COMMON, mage.cards.h.Headstone.class));
        cards.add(new SetCardInfo("Hungry Mist", 60, Rarity.COMMON, mage.cards.h.HungryMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hungry Mist", 61, Rarity.COMMON, mage.cards.h.HungryMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ihsan's Shade", 16, Rarity.UNCOMMON, mage.cards.i.IhsansShade.class));
        cards.add(new SetCardInfo("Irini Sengir", 17, Rarity.UNCOMMON, mage.cards.i.IriniSengir.class));
        cards.add(new SetCardInfo("Jinx", 36, Rarity.COMMON, mage.cards.j.Jinx.class));
        cards.add(new SetCardInfo("Joven", 97, Rarity.COMMON, mage.cards.j.Joven.class));
        cards.add(new SetCardInfo("Koskun Falls", 18, Rarity.RARE, mage.cards.k.KoskunFalls.class));
        cards.add(new SetCardInfo("Koskun Keep", 139, Rarity.UNCOMMON, mage.cards.k.KoskunKeep.class));
        cards.add(new SetCardInfo("Leaping Lizard", 63, Rarity.COMMON, mage.cards.l.LeapingLizard.class));
        cards.add(new SetCardInfo("Leeches", 111, Rarity.RARE, mage.cards.l.Leeches.class));
        cards.add(new SetCardInfo("Marjhan", 39, Rarity.RARE, mage.cards.m.Marjhan.class));
        cards.add(new SetCardInfo("Memory Lapse", 40, Rarity.COMMON, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Lapse", 41, Rarity.COMMON, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merchant Scroll", 42, Rarity.COMMON, mage.cards.m.MerchantScroll.class));
        cards.add(new SetCardInfo("Mesa Falcon", 112, Rarity.COMMON, MesaFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesa Falcon", 113, Rarity.COMMON, MesaFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Decree", 43, Rarity.RARE, mage.cards.m.MysticDecree.class));
        cards.add(new SetCardInfo("Narwhal", 44, Rarity.RARE, mage.cards.n.Narwhal.class));
        cards.add(new SetCardInfo("Primal Order", 65, Rarity.RARE, mage.cards.p.PrimalOrder.class));
        cards.add(new SetCardInfo("Reef Pirates", 45, Rarity.COMMON, ReefPirates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reef Pirates", 46, Rarity.COMMON, ReefPirates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renewal", 66, Rarity.COMMON, mage.cards.r.Renewal.class));
        cards.add(new SetCardInfo("Reveka, Wizard Savant", 47, Rarity.RARE, mage.cards.r.RevekaWizardSavant.class));
        cards.add(new SetCardInfo("Roots", 68, Rarity.UNCOMMON, mage.cards.r.Roots.class));
        cards.add(new SetCardInfo("Root Spider", 67, Rarity.UNCOMMON, mage.cards.r.RootSpider.class));
        cards.add(new SetCardInfo("Roterothopter", 134, Rarity.COMMON, mage.cards.r.Roterothopter.class));
        cards.add(new SetCardInfo("Sea Sprite", 48, Rarity.UNCOMMON, mage.cards.s.SeaSprite.class));
        cards.add(new SetCardInfo("Sengir Autocrat", 19, Rarity.UNCOMMON, mage.cards.s.SengirAutocrat.class));
        cards.add(new SetCardInfo("Sengir Bats", 20, Rarity.COMMON, SengirBats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sengir Bats", 21, Rarity.COMMON, SengirBats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Aviary", 118, Rarity.RARE, mage.cards.s.SerraAviary.class));
        cards.add(new SetCardInfo("Serrated Arrows", 135, Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Shrink", 71, Rarity.COMMON, mage.cards.s.Shrink.class));
        cards.add(new SetCardInfo("Spectral Bears", 72, Rarity.UNCOMMON, mage.cards.s.SpectralBears.class));
        cards.add(new SetCardInfo("Torture", 23, Rarity.COMMON, Torture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torture", 24, Rarity.COMMON, Torture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Veldrane of Sengir", 25, Rarity.RARE, mage.cards.v.VeldraneOfSengir.class));
        cards.add(new SetCardInfo("Wall of Kelp", 50, Rarity.RARE, mage.cards.w.WallOfKelp.class));
        cards.add(new SetCardInfo("Willow Faerie", 73, Rarity.COMMON, WillowFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Faerie", 74, Rarity.COMMON, WillowFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Priestess", 75, Rarity.RARE, mage.cards.w.WillowPriestess.class));
        cards.add(new SetCardInfo("Winter Sky", 100, Rarity.RARE, mage.cards.w.WinterSky.class));
        cards.add(new SetCardInfo("Wizards' School", 140, Rarity.UNCOMMON, mage.cards.w.WizardsSchool.class));
    }
}
