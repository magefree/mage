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
        cards.add(new SetCardInfo("Arcane Flight", 43, Rarity.COMMON, mage.cards.a.ArcaneFlight.class));
        cards.add(new SetCardInfo("Arvad the Cursed", 191, Rarity.UNCOMMON, mage.cards.a.ArvadTheCursed.class));
        cards.add(new SetCardInfo("Aven Sentry", 3, Rarity.COMMON, mage.cards.a.AvenSentry.class));
        cards.add(new SetCardInfo("Baird, Steward of Argive", 4, Rarity.UNCOMMON, mage.cards.b.BairdStewardOfArgive.class));
        cards.add(new SetCardInfo("Baloth Gorger", 156, Rarity.COMMON, mage.cards.b.BalothGorger.class));
        cards.add(new SetCardInfo("Befuddle", 45, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Benalish Marshal", 6, Rarity.RARE, mage.cards.b.BenalishMarshal.class));
        cards.add(new SetCardInfo("Blessed Light", 7, Rarity.UNCOMMON, mage.cards.b.BlessedLight.class));
        cards.add(new SetCardInfo("Cabal Evangel", 78, Rarity.COMMON, mage.cards.c.CabalEvangel.class));
        cards.add(new SetCardInfo("Cabal Stronghold", 238, Rarity.RARE, mage.cards.c.CabalStronghold.class));
        cards.add(new SetCardInfo("Cast Down", 81, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Charge", 11, Rarity.COMMON, mage.cards.c.Charge.class));
        cards.add(new SetCardInfo("Clifftop Retreat", 239, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Damping Sphere", 213, Rarity.UNCOMMON, mage.cards.d.DampingSphere.class));
        cards.add(new SetCardInfo("Divest", 87, Rarity.COMMON, mage.cards.d.Divest.class));
        cards.add(new SetCardInfo("Divination", 52, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Drudge Sentinel", 89, Rarity.COMMON, mage.cards.d.DrudgeSentinel.class));
        cards.add(new SetCardInfo("Dub", 15, Rarity.SPECIAL, mage.cards.d.Dub.class));
        cards.add(new SetCardInfo("Fire Elemental", 120, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Blessing", 161, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Ghitu Lavarunner", 127, Rarity.COMMON, mage.cards.g.GhituLavarunner.class));
        cards.add(new SetCardInfo("Gideon's Reproach", 29, Rarity.COMMON, mage.cards.g.GideonsReproach.class));
        cards.add(new SetCardInfo("Gilded Lotus", 215, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Goblin Chainwhirler", 129, Rarity.RARE, mage.cards.g.GoblinChainwhirler.class));
        cards.add(new SetCardInfo("Goblin Warchief", 130, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Grunn, the Lonely King", 165, Rarity.UNCOMMON, mage.cards.g.GrunnTheLonelyKing.class));
        cards.add(new SetCardInfo("Helm of the Host", 217, Rarity.RARE, mage.cards.h.HelmOfTheHost.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 240, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Homarid Explorer", 53, Rarity.UNCOMMON, mage.cards.h.HomaridExplorer.class));
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
        cards.add(new SetCardInfo("Knight of Grace", 23, Rarity.UNCOMMON, mage.cards.k.KnightOfGrace.class));
        cards.add(new SetCardInfo("Knight of Malice", 97, Rarity.UNCOMMON, mage.cards.k.KnightOfMalice.class));
        cards.add(new SetCardInfo("Llanowar Elves", 168, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 14, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", 172, Rarity.RARE, mage.cards.m.MarwynTheNurturer.class));
        cards.add(new SetCardInfo("Meandering River", 274, Rarity.COMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Memorial To Folly", 242, Rarity.UNCOMMON, mage.cards.m.MemorialToFolly.class));
        cards.add(new SetCardInfo("Memorial To Genius", 243, Rarity.UNCOMMON, mage.cards.m.MemorialToGenius.class));
        cards.add(new SetCardInfo("Memorial To Glory", 244, Rarity.UNCOMMON, mage.cards.m.MemorialToGlory.class));
        cards.add(new SetCardInfo("Memorial To Unity", 245, Rarity.UNCOMMON, mage.cards.m.MemorialToUnity.class));
        cards.add(new SetCardInfo("Memorial To War", 246, Rarity.UNCOMMON, mage.cards.m.MemorialToWar.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Multani, Yavimaya's Avatar", 174, Rarity.MYTHIC, mage.cards.m.MultaniYavimayasAvatar.class));
        cards.add(new SetCardInfo("Mox Amber", 224, Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Naru Meha, Master Wizard", 59, Rarity.MYTHIC, mage.cards.n.NaruMehaMasterWizard.class));
        cards.add(new SetCardInfo("Nature's Spiral", 175, Rarity.UNCOMMON, mage.cards.n.NaturesSpiral.class));
        cards.add(new SetCardInfo("Opt", 60, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primevals' Glorious Rebirth", 201, Rarity.RARE, mage.cards.p.PrimevalsGloriousRebirth.class));
        cards.add(new SetCardInfo("Rescue", 63, Rarity.COMMON, mage.cards.r.Rescue.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 64, Rarity.UNCOMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Seal Away", 31, Rarity.UNCOMMON, mage.cards.s.SealAway.class));
        cards.add(new SetCardInfo("Serra Angel", 33, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Serra Disciple", 34, Rarity.COMMON, mage.cards.s.SerraDisciple.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 143, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Skirk Prospector", 144, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skizzik", 145, Rarity.UNCOMMON, mage.cards.s.Skizzik.class));
        cards.add(new SetCardInfo("Sulfur Falls", 247, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syncopate", 67, Rarity.UNCOMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 206, Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", 36, Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Tetsuko Umezawa, Fugitive", 69, Rarity.UNCOMMON, mage.cards.t.TetsukoUmezawaFugitive.class));
        cards.add(new SetCardInfo("Thorn Elemental", 185, Rarity.UNCOMMON, mage.cards.t.ThornElemental.class));
        cards.add(new SetCardInfo("Timber Gorge", 279, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Tragic Poet", 37, Rarity.COMMON, mage.cards.t.TragicPoet.class));
        cards.add(new SetCardInfo("Untamed Kavu", 186, Rarity.UNCOMMON, mage.cards.u.UntamedKavu.class));
        cards.add(new SetCardInfo("Unwind", 72, Rarity.COMMON, mage.cards.u.Unwind.class));
        cards.add(new SetCardInfo("Urza's Ruinous Blast", 39, Rarity.RARE, mage.cards.u.UrzasRuinousBlast.class));
        cards.add(new SetCardInfo("Verdant Force", 187, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Verix Bladewing", 149, Rarity.MYTHIC, mage.cards.v.VerixBladewing.class));
        cards.add(new SetCardInfo("Wizard's Lightning", 152, Rarity.UNCOMMON, mage.cards.w.WizardsLightning.class));
        cards.add(new SetCardInfo("Wizard's Retort", 75, Rarity.UNCOMMON, mage.cards.w.WizardsRetort.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 248, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));
        cards.add(new SetCardInfo("Yawgmoth's Vile Offering", 114, Rarity.RARE, mage.cards.y.YawgmothsVileOffering.class));
        cards.add(new SetCardInfo("Zhalfirin Void", 249, Rarity.UNCOMMON, mage.cards.z.ZhalfirinVoid.class));
    }
}
