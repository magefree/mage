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
        cards.add(new SetCardInfo("Arvad the Cursed", 191, Rarity.UNCOMMON, mage.cards.a.ArvadTheCursed.class));
        cards.add(new SetCardInfo("Baird, Steward of Argive", 4, Rarity.UNCOMMON, mage.cards.b.BairdStewardOfArgive.class));
        cards.add(new SetCardInfo("Benalish Marshal", 10, Rarity.UNCOMMON, mage.cards.b.BenalishMarshal.class));
        cards.add(new SetCardInfo("Blessed Light", 31, Rarity.UNCOMMON, mage.cards.b.BlessedLight.class));
        cards.add(new SetCardInfo("Cabal Evangel", 78, Rarity.COMMON, mage.cards.c.CabalEvangel.class));
        cards.add(new SetCardInfo("Cabal Stronghold", 238, Rarity.RARE, mage.cards.c.CabalStronghold.class));
        cards.add(new SetCardInfo("Cast Down", 81, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Charge", 11, Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 239, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Damping Sphere", 213, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class));
        cards.add(new SetCardInfo("Divest", 87, Rarity.COMMON, mage.cards.d.Divest.class));
        cards.add(new SetCardInfo("Drudge Sentinel", 89, Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dub", 999, Rarity.SPECIAL, mage.cards.d.Dub.class)); //TODO: Update once full spoiler
        cards.add(new SetCardInfo("Fire Elemental", 120, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 127, Rarity.COMMON, mage.cards.g.GhituLavarunner.class)); //TODO: Update once full spoiler
        cards.add(new SetCardInfo("Gilded Lotus", 215, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goblin Warchief", 130, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Grunn, the Lonely King", 165, Rarity.UNCOMMON, mage.cards.g.GrunnTheLonelyKing.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 240, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Homarid Explorer", 53, Rarity.UNCOMMON, mage.cards.h.HomaridExplorer.class));
        cards.add(new SetCardInfo("Icy Manipulator", 219, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Invoke the Divine", 13, Rarity.COMMON, mage.cards.i.InvokeTheDivine.class));
        cards.add(new SetCardInfo("Isolated Chapel", 241, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Jaya's Immolating Inferno", 133, Rarity.RARE, mage.cards.j.JayasImmolatingInferno.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Captain", 200, Rarity.MYTHIC, mage.cards.j.JhoiraWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Jodah, Archmage Eternal", 198, Rarity.RARE, mage.cards.j.JodahArchmageEternal.class));
        cards.add(new SetCardInfo("Josu Vess, Lich Knight",95, Rarity.RARE, mage.cards.j.JosuVessLichKnight.class));
        cards.add(new SetCardInfo("Juggernaut", 222, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kamahl's Druidic Vow", 166, Rarity.RARE, mage.cards.k.KamahlsDruidicVow.class));
        cards.add(new SetCardInfo("Karn, Scion of Urza", 554, Rarity.MYTHIC, mage.cards.k.KarnScionOfUrza.class));
        cards.add(new SetCardInfo("Karn's Temporal Sundering", 55, Rarity.RARE, mage.cards.k.KarnsTemporalSundering.class));
        cards.add(new SetCardInfo("Knight of Grace", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfGrace.class));
        cards.add(new SetCardInfo("Knight of Malice", 52, Rarity.UNCOMMON, mage.cards.k.KnightOfMalice.class));
        cards.add(new SetCardInfo("Llanowar Elves", 168, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 14, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", 172, Rarity.RARE, mage.cards.m.MarwynTheNurturer.class));
        cards.add(new SetCardInfo("Meandering River", 274, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Naru Meha, Master Wizard", 59, Rarity.MYTHIC, mage.cards.n.NaruMehaMasterWizard.class));
        cards.add(new SetCardInfo("Opt", 60, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Primevals' Glorious Rebirth", 201, Rarity.RARE, mage.cards.p.PrimevalsGloriousRebirth.class));
        cards.add(new SetCardInfo("Seal Away", 31, Rarity.UNCOMMON, mage.cards.s.SealAway.class));
        cards.add(new SetCardInfo("Serra Angel", 33, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra Disciple", 34, Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Skizzik", 35, Rarity.RARE, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Sulfur Falls", 247, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Syncopate", 67, Rarity.UNCOMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 206, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", 36, Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Tetsuko Umezawa, Fugitive", 69, Rarity.UNCOMMON, mage.cards.t.TetsukoUmezawaFugitive.class));
        cards.add(new SetCardInfo("Thorn Elemental", 185, Rarity.UNCOMMON, mage.cards.t.ThornElemental.class));
        cards.add(new SetCardInfo("Unwind", 72, Rarity.COMMON, mage.cards.u.Unwind.class));
        cards.add(new SetCardInfo("Urza's Ruinous Blast", 39, Rarity.RARE, mage.cards.u.UrzasRuinousBlast.class));
        cards.add(new SetCardInfo("Verix Bladewing", 149, Rarity.MYTHIC, mage.cards.v.VerixBladewing.class));
        cards.add(new SetCardInfo("Wizard's Lightning", 152, Rarity.UNCOMMON, mage.cards.w.WizardsLightning.class));
        cards.add(new SetCardInfo("Wizard's Retort", 75, Rarity.UNCOMMON, mage.cards.w.WizardsRetort.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 248, Rarity.RARE, mage.cards.w.WoodlandCemetery.class)); //TODO: Check number once full spoiler rolls out
        cards.add(new SetCardInfo("Yawgmoth's Vile Offering", 114, Rarity.RARE, mage.cards.y.YawgmothsVileOffering.class));
        cards.add(new SetCardInfo("Zhalfirin Void", 249, Rarity.UNCOMMON, mage.cards.z.ZhalfirinVoid.class));
    }
}
