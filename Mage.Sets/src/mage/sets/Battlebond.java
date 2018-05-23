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
 * @author TheElk801
 */
public class Battlebond extends ExpansionSet {

    private static final Battlebond instance = new Battlebond();

    public static Battlebond getInstance() {
        return instance;
    }

    private Battlebond() {
        super("Battlebond", "BBD", ExpansionSet.buildDate(2018, 6, 8), SetType.SUPPLEMENTAL);
        this.blockName = "Battlebond";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Archfiend of Despair", 44, Rarity.MYTHIC, mage.cards.a.ArchfiendOfDespair.class));
        cards.add(new SetCardInfo("Arena Rector", 23, Rarity.MYTHIC, mage.cards.a.ArenaRector.class));
        cards.add(new SetCardInfo("Auger Spree", 218, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Boldwyr Intimidator", 169, Rarity.UNCOMMON, mage.cards.b.BoldwyrIntimidator.class));
        cards.add(new SetCardInfo("Bountiful Promenade", 81, Rarity.RARE, mage.cards.b.BountifulPromenade.class));
        cards.add(new SetCardInfo("Centaur Healer", 219, Rarity.COMMON, mage.cards.c.CentaurHealer.class));
        cards.add(new SetCardInfo("Chain Lightning", 171, Rarity.UNCOMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Chakram Retriever", 15, Rarity.UNCOMMON, mage.cards.c.ChakramRetriever.class));
        cards.add(new SetCardInfo("Chakram Slinger", 16, Rarity.UNCOMMON, mage.cards.c.ChakramSlinger.class));
        cards.add(new SetCardInfo("Daggerdrome Imp", 140, Rarity.COMMON, mage.cards.d.DaggerdromeImp.class));
        cards.add(new SetCardInfo("Diabolic Intent", 141, Rarity.RARE, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 142, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Doomed Traveler", 91, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Doubling Season", 195, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Eager Construct", 234, Rarity.COMMON, mage.cards.e.EagerConstruct.class));
        cards.add(new SetCardInfo("Expedition Raptor", 92, Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Fertile Ground", 198, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Greater Good", 201, Rarity.RARE, mage.cards.g.GreaterGood.class));
        cards.add(new SetCardInfo("Impetuous Protege", 19, Rarity.UNCOMMON, mage.cards.i.ImpetuousProtege.class));
        cards.add(new SetCardInfo("Impulse", 119, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Inner Demon", 48, Rarity.UNCOMMON, mage.cards.i.InnerDemon.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Khorvath Brightflame", 9, Rarity.RARE, mage.cards.k.KhorvathBrightflame.class));
        cards.add(new SetCardInfo("Kor Spiritdancer", 93, Rarity.RARE, mage.cards.k.KorSpiritdancer.class));
        cards.add(new SetCardInfo("Kraken Hatchling", 121, Rarity.COMMON, mage.cards.k.KrakenHatchling.class));
        cards.add(new SetCardInfo("Krav, the Unredeemed", 4, Rarity.RARE, mage.cards.k.KravTheUnredeemed.class));
        cards.add(new SetCardInfo("Land Tax", 94, Rarity.MYTHIC, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Last One Standing", 76, Rarity.RARE, mage.cards.l.LastOneStanding.class));
        cards.add(new SetCardInfo("Ley Weaver", 21, Rarity.UNCOMMON, mage.cards.l.LeyWeaver.class));
        cards.add(new SetCardInfo("Lore Weaver", 22, Rarity.UNCOMMON, mage.cards.l.LoreWeaver.class));
        cards.add(new SetCardInfo("Luxury Suite", 82, Rarity.RARE, mage.cards.l.LuxurySuite.class));
        cards.add(new SetCardInfo("Mindblade Render", 49, Rarity.RARE, mage.cards.m.MindbladeRender.class));
        cards.add(new SetCardInfo("Morphic Pool", 83, Rarity.RARE, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nirkana Revenant", 150, Rarity.MYTHIC, mage.cards.n.NirkanaRevenant.class));
        cards.add(new SetCardInfo("Peregrine Drake", 128, Rarity.UNCOMMON, mage.cards.p.PeregrineDrake.class));
        cards.add(new SetCardInfo("Pir's Whim", 73, Rarity.RARE, mage.cards.p.PirsWhim.class));
        cards.add(new SetCardInfo("Pir, Imaginative Rascal", 11, Rarity.RARE, mage.cards.p.PirImaginativeRascal.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Proud Mentor", 20, Rarity.UNCOMMON, mage.cards.p.ProudMentor.class));
        cards.add(new SetCardInfo("Regna, the Redeemer", 3, Rarity.RARE, mage.cards.r.RegnaTheRedeemer.class));
        cards.add(new SetCardInfo("Rowan Kenrith", 2, Rarity.MYTHIC, mage.cards.r.RowanKenrith.class));
        cards.add(new SetCardInfo("Rushblade Commander", 77, Rarity.UNCOMMON, mage.cards.r.RushbladeCommander.class));
        cards.add(new SetCardInfo("Saltwater Stalwart", 39, Rarity.COMMON, mage.cards.s.SaltwaterStalwart.class));
        cards.add(new SetCardInfo("Sea of Clouds", 84, Rarity.RARE, mage.cards.s.SeaOfClouds.class));
        cards.add(new SetCardInfo("Seedborn Muse", 212, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", 105, Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Sickle Dancer", 50, Rarity.COMMON, mage.cards.s.SickleDancer.class));
        cards.add(new SetCardInfo("Soaring Show-Off", 40, Rarity.COMMON, mage.cards.s.SoaringShowOff.class));
        cards.add(new SetCardInfo("Soulblade Corrupter", 17, Rarity.UNCOMMON, mage.cards.s.SoulbladeCorrupter.class));
        cards.add(new SetCardInfo("Soulblade Renewer", 18, Rarity.UNCOMMON, mage.cards.s.SoulbladeRenewer.class));
        cards.add(new SetCardInfo("Sower of Temptation", 131, Rarity.RARE, mage.cards.s.SowerOfTemptation.class));
        cards.add(new SetCardInfo("Spellseeker", 41, Rarity.RARE, mage.cards.s.Spellseeker.class));
        cards.add(new SetCardInfo("Spire Garden", 85, Rarity.RARE, mage.cards.s.SpireGarden.class));
        cards.add(new SetCardInfo("Stunning Reversal", 51, Rarity.MYTHIC, mage.cards.s.StunningReversal.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 110, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvia Brightspear", 10, Rarity.RARE, mage.cards.s.SylviaBrightspear.class));
        cards.add(new SetCardInfo("Thrasher Brute", 52, Rarity.UNCOMMON, mage.cards.t.ThrasherBrute.class));
        cards.add(new SetCardInfo("Tidespout Tyrant", 134, Rarity.RARE, mage.cards.t.TidespoutTyrant.class));
        cards.add(new SetCardInfo("Toothy, Imaginary Friend", 12, Rarity.RARE, mage.cards.t.ToothyImaginaryFriend.class));
        cards.add(new SetCardInfo("True-Name Nemesis", 136, Rarity.MYTHIC, mage.cards.t.TrueNameNemesis.class));
        cards.add(new SetCardInfo("Veteran Explorer", 214, Rarity.UNCOMMON, mage.cards.v.VeteranExplorer.class));
        cards.add(new SetCardInfo("Vigor", 215, Rarity.RARE, mage.cards.v.Vigor.class));
        cards.add(new SetCardInfo("Virtus the Veiled", 7, Rarity.RARE, mage.cards.v.VirtusTheVeiled.class));
        cards.add(new SetCardInfo("Will Kenrith", 1, Rarity.MYTHIC, mage.cards.w.WillKenrith.class));
    }
}
