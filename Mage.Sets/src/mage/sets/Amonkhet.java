/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fireshoes
 */
public class Amonkhet extends ExpansionSet {

    private static final Amonkhet instance = new Amonkhet();

    public static Amonkhet getInstance() {
        return instance;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private Amonkhet() {
        super("Amonkhet", "AKH", ExpansionSet.buildDate(2017, 4, 28), SetType.EXPANSION);
        this.blockName = "Amonkhet";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 269;
        this.ratioBoosterSpecialLand = 144;

        cards.add(new SetCardInfo("Ahn-Crop Crasher", 117, Rarity.UNCOMMON, mage.cards.a.AhnCropCrasher.class));
        cards.add(new SetCardInfo("Approach of the Second Sun", 4, Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class));
        cards.add(new SetCardInfo("Ancient Crab", 40, Rarity.COMMON, mage.cards.a.AncientCrab.class));
        cards.add(new SetCardInfo("Angel of Sanctions", 1, Rarity.MYTHIC, mage.cards.a.AngelOfSanctions.class));
        cards.add(new SetCardInfo("Angler Drake", 41, Rarity.UNCOMMON, mage.cards.a.AnglerDrake.class));
        cards.add(new SetCardInfo("Anointer Priest", 3, Rarity.COMMON, mage.cards.a.AnointerPriest.class));
        cards.add(new SetCardInfo("Archfiend of Ifnir", 78, Rarity.RARE, mage.cards.a.ArchfiendOfIfnir.class));
        cards.add(new SetCardInfo("Aven Mindcensor", 5, Rarity.RARE, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Bontu's Monument", 225, Rarity.UNCOMMON, mage.cards.b.BontusMonument.class));
        cards.add(new SetCardInfo("Canyon Slough", 239, Rarity.RARE, mage.cards.c.CanyonSlough.class));
        cards.add(new SetCardInfo("Cartouche of Solidarity", 7, Rarity.COMMON, mage.cards.c.CartoucheOfSolidarity.class));
        cards.add(new SetCardInfo("Cast Out", 8, Rarity.UNCOMMON, mage.cards.c.CastOut.class));
        cards.add(new SetCardInfo("Channeler Initiate", 160, Rarity.RARE, mage.cards.c.ChannelerInitiate.class));
        cards.add(new SetCardInfo("Colossapede", 161, Rarity.COMMON, mage.cards.c.Colossapede.class));
        cards.add(new SetCardInfo("Combat Celebrant", 125, Rarity.MYTHIC, mage.cards.c.CombatCelebrant.class));
        cards.add(new SetCardInfo("Consuming Fervor", 126, Rarity.UNCOMMON, mage.cards.c.ConsumingFervor.class));
        cards.add(new SetCardInfo("Crocodile of the Crossing", 162, Rarity.UNCOMMON, mage.cards.c.CrocodileOfTheCrossing.class));
        cards.add(new SetCardInfo("Cursed Minotaur", 85, Rarity.COMMON, mage.cards.c.CursedMinotaur.class));
        cards.add(new SetCardInfo("Cut // Ribbons", 223, Rarity.RARE, mage.cards.c.CutRibbons.class));
        cards.add(new SetCardInfo("Decision Paralysis", 50, Rarity.COMMON, mage.cards.d.DecisionParalysis.class));
        cards.add(new SetCardInfo("Destined // Lead", 217, Rarity.UNCOMMON, mage.cards.d.DestinedLead.class));
        cards.add(new SetCardInfo("Dissenter's Deliverance", 164, Rarity.COMMON, mage.cards.d.DissentersDeliverance.class));
        cards.add(new SetCardInfo("Djeru's Resolve", 11, Rarity.COMMON, mage.cards.d.DjerusResolve.class));
        cards.add(new SetCardInfo("Drake Haven", 51, Rarity.RARE, mage.cards.d.DrakeHaven.class));
        cards.add(new SetCardInfo("Dune Beetle", 89, Rarity.COMMON, mage.cards.d.DuneBeetle.class));
        cards.add(new SetCardInfo("Dusk // Dawn", 210, Rarity.RARE, mage.cards.d.DuskDawn.class));
        cards.add(new SetCardInfo("Essence Scatter", 52, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Exemplar of Strength", 165, Rarity.UNCOMMON, mage.cards.e.ExemplarOfStrength.class));
        cards.add(new SetCardInfo("Fetid Pools", 243, Rarity.RARE, mage.cards.f.FetidPools.class));
        cards.add(new SetCardInfo("Flameblade Adept", 131, Rarity.UNCOMMON, mage.cards.f.FlamebladeAdept.class));
        cards.add(new SetCardInfo("Fling", 132, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Forest", 254, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Foul Orchard", 279, Rarity.COMMON, mage.cards.f.FoulOrchard.class));
        cards.add(new SetCardInfo("Giant Spider", 166, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gideon of the Trials", 14, Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class));
        cards.add(new SetCardInfo("Gideon, Martial Paragon", 270, Rarity.MYTHIC, mage.cards.g.GideonMartialParagon.class));
        cards.add(new SetCardInfo("Graceful Cat", 273, Rarity.COMMON, mage.cards.g.GracefulCat.class));
        cards.add(new SetCardInfo("Gravedigger", 93, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Hazoret the Fervent", 136, Rarity.MYTHIC, mage.cards.h.HazoretTheFervent.class));
        cards.add(new SetCardInfo("Hazoret's Monument", 229, Rarity.UNCOMMON, mage.cards.h.HazoretsMonument.class));
        cards.add(new SetCardInfo("Hyena Pack", 139, Rarity.COMMON, mage.cards.h.HyenaPack.class));
        cards.add(new SetCardInfo("Impeccable Timing", 18, Rarity.COMMON, mage.cards.i.ImpeccableTiming.class));
        cards.add(new SetCardInfo("In Oketra's Name", 19, Rarity.COMMON, mage.cards.i.InOketrasName.class));
        cards.add(new SetCardInfo("Insult // Injury", 213, Rarity.RARE, mage.cards.i.InsultInjury.class));
        cards.add(new SetCardInfo("Irrigated Farmland", 245, Rarity.RARE, mage.cards.i.IrrigatedFarmland.class));
        cards.add(new SetCardInfo("Island", 251, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 258, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 259, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 260, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Kefnet the Mindful", 59, Rarity.MYTHIC, mage.cards.k.KefnetTheMindful.class));
        cards.add(new SetCardInfo("Kefnet's Monument", 231, Rarity.UNCOMMON, mage.cards.k.KefnetsMonument.class));
        cards.add(new SetCardInfo("Lay Bare the Heart", 96, Rarity.UNCOMMON, mage.cards.l.LayBareTheHeart.class));
        cards.add(new SetCardInfo("Liliana, Death Wielder", 274, Rarity.MYTHIC, mage.cards.l.LilianaDeathWielder.class));
        cards.add(new SetCardInfo("Limits of Solidarity", 140, Rarity.UNCOMMON, mage.cards.l.LimitsOfSolidarity.class));
        cards.add(new SetCardInfo("Magma Spray", 141, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Miasma Mummy", 100, Rarity.COMMON, mage.cards.m.MiasmaMummy.class));
        cards.add(new SetCardInfo("Mighty Leap", 20, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mountain", 253, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 266, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mouth // Feed", 214, Rarity.RARE, mage.cards.m.MouthFeed.class));
        cards.add(new SetCardInfo("Nimble-Blade Khenra", 145, Rarity.COMMON, mage.cards.n.NimbleBladeKhenra.class));
        cards.add(new SetCardInfo("Oketra's Monument", 233, Rarity.UNCOMMON, mage.cards.o.OketrasMonument.class));
        cards.add(new SetCardInfo("Onward // Victory", 218, Rarity.UNCOMMON, mage.cards.o.OnwardVictory.class));
        cards.add(new SetCardInfo("Oracle's Vault", 234, Rarity.RARE, mage.cards.o.OraclesVault.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 255, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 256, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 257, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Pouncing Cheetah", 179, Rarity.COMMON, mage.cards.p.PouncingCheetah.class));
        cards.add(new SetCardInfo("Prepared // Fight", 220, Rarity.RARE, mage.cards.p.PreparedFight.class));
        cards.add(new SetCardInfo("Prowling Serpopard", 180, Rarity.RARE, mage.cards.p.ProwlingSerpopard.class));
        cards.add(new SetCardInfo("Rags // Riches", 222, Rarity.RARE, mage.cards.r.RagsRiches.class));
        cards.add(new SetCardInfo("Renewed Faith", 25, Rarity.UNCOMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Rhonas's Monument", 236, Rarity.UNCOMMON, mage.cards.r.RhonassMonument.class));
        cards.add(new SetCardInfo("Sacred Cat", 27, Rarity.COMMON, mage.cards.s.SacredCat.class));
        cards.add(new SetCardInfo("Scarab Feast", 106, Rarity.COMMON, mage.cards.s.ScarabFeast.class));
        cards.add(new SetCardInfo("Scattered Groves", 247, Rarity.RARE, mage.cards.s.ScatteredGroves.class));
        cards.add(new SetCardInfo("Scribe of the Mindful", 68, Rarity.COMMON, mage.cards.s.ScribeOfTheMindful.class));
        cards.add(new SetCardInfo("Sheltered Thicket", 248, Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Sixth Sense", 187, Rarity.UNCOMMON, mage.cards.s.SixthSense.class));
        cards.add(new SetCardInfo("Spidery Grasp", 188, Rarity.COMMON, mage.cards.s.SpideryGrasp.class));
        cards.add(new SetCardInfo("Splendid Agony", 109, Rarity.COMMON, mage.cards.s.SplendidAgony.class));
        cards.add(new SetCardInfo("Supply Caravan", 30, Rarity.COMMON, mage.cards.s.SupplyCaravan.class));
        cards.add(new SetCardInfo("Swamp", 252, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 263, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Tattered Mummy", 278, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Temmet, Vizier of Naktamun", 207, Rarity.RARE, mage.cards.t.TemmetVizierOfNaktamun.class));
        cards.add(new SetCardInfo("Trial of Knowledge", 73, Rarity.UNCOMMON, mage.cards.t.TrialOfKnowledge.class));
        cards.add(new SetCardInfo("Trueheart Duelist", 35, Rarity.UNCOMMON, mage.cards.t.TrueheartDuelist.class));
        cards.add(new SetCardInfo("Unwavering Initiate", 36, Rarity.COMMON, mage.cards.u.UnwaveringInitiate.class));
        cards.add(new SetCardInfo("Winged Shepherd", 39, Rarity.COMMON, mage.cards.w.WingedShepherd.class));
    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("MPS-AKH");
            criteria.minCardNumber(1);
            criteria.maxCardNumber(30);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
