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

import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public class HourOfDevastation extends ExpansionSet {

    private static final HourOfDevastation instance = new HourOfDevastation();

    public static HourOfDevastation getInstance() {
        return instance;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private HourOfDevastation() {
        super("Hour of Devastation", "HOU", ExpansionSet.buildDate(2017, 7, 14), SetType.EXPANSION);
        this.blockName = "Amonkhet";
        this.parentSet = Amonkhet.getInstance();
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialLand = 144;

        cards.add(new SetCardInfo("Abrade", 83, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Accursed Horde", 56, Rarity.UNCOMMON, mage.cards.a.AccursedHorde.class));
        cards.add(new SetCardInfo("Adorned Pouncer", 2, Rarity.RARE, mage.cards.a.AdornedPouncer.class));
        cards.add(new SetCardInfo("Angel of the God-Pharaoh", 4, Rarity.UNCOMMON, mage.cards.a.AngelOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Bontu's Last Reckoning", 60, Rarity.RARE, mage.cards.b.BontusLastReckoning.class));
        cards.add(new SetCardInfo("Carrion Screecher", 61, Rarity.COMMON, mage.cards.c.CarrionScreecher.class));
        cards.add(new SetCardInfo("Chandra's Defeat", 86, Rarity.UNCOMMON, mage.cards.c.ChandrasDefeat.class));
        cards.add(new SetCardInfo("Cinder Barrens", 209, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Defiant Khenra", 89, Rarity.COMMON, mage.cards.d.DefiantKhenra.class));
        cards.add(new SetCardInfo("Desert of the Fervent", 170, Rarity.COMMON, mage.cards.d.DesertOfTheFervent.class));
        cards.add(new SetCardInfo("Desert of the Glorified", 171, Rarity.COMMON, mage.cards.d.DesertOfTheGlorified.class));
        cards.add(new SetCardInfo("Desert of the Indomitable", 172, Rarity.COMMON, mage.cards.d.DesertOfTheIndomitable.class));
        cards.add(new SetCardInfo("Desert of the Mindful", 173, Rarity.COMMON, mage.cards.d.DesertOfTheMindful.class));
        cards.add(new SetCardInfo("Desert of the True", 174, Rarity.COMMON, mage.cards.d.DesertOfTheTrue.class));
        cards.add(new SetCardInfo("Doomfall", 62, Rarity.UNCOMMON, mage.cards.d.Doomfall.class));
        cards.add(new SetCardInfo("Dreamstealer", 63, Rarity.RARE, mage.cards.d.Dreamstealer.class));
        cards.add(new SetCardInfo("Hour of Glory", 65, Rarity.RARE, mage.cards.h.HourOfGlory.class));
        cards.add(new SetCardInfo("Hour of Revelation", 15, Rarity.RARE, mage.cards.h.HourOfRevelation.class));
        cards.add(new SetCardInfo("Inferno Jet", 99, Rarity.UNCOMMON, mage.cards.i.InfernoJet.class));
        cards.add(new SetCardInfo("Khenra Eternal", 66, Rarity.COMMON, mage.cards.k.KhenraEternal.class));
        cards.add(new SetCardInfo("Khenra Scrapper", 100, Rarity.COMMON, mage.cards.k.KhenraScrapper.class));
        cards.add(new SetCardInfo("Liliana's Defeat", 68, Rarity.UNCOMMON, mage.cards.l.LilianasDefeat.class));
        cards.add(new SetCardInfo("Marauding Boneslasher", 70, Rarity.COMMON, mage.cards.m.MaraudingBoneslasher.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", 140, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Open Fire", 105, Rarity.COMMON, mage.cards.o.OpenFire.class));
        cards.add(new SetCardInfo("Proven Combatant", 42, Rarity.COMMON, mage.cards.p.ProvenCombatant.class));
        cards.add(new SetCardInfo("Ramunap Excavator", 129, Rarity.RARE, mage.cards.r.RamunapExcavator.class));
        cards.add(new SetCardInfo("Razaketh, the Foulblooded", 73, Rarity.MYTHIC, mage.cards.r.RazakethTheFoulblooded.class));
        cards.add(new SetCardInfo("Samut, the Tested", 144, Rarity.MYTHIC, mage.cards.s.SamutTheTested.class));
        cards.add(new SetCardInfo("Sinuous Striker", 45, Rarity.UNCOMMON, mage.cards.s.SinuousStriker.class));
        cards.add(new SetCardInfo("Solemnity", 22, Rarity.RARE, mage.cards.s.Solemnity.class));
        cards.add(new SetCardInfo("Steadfast Sentinel", 24, Rarity.COMMON, mage.cards.s.SteadfastSentinel.class));
        cards.add(new SetCardInfo("Supreme Will", 49, Rarity.UNCOMMON, mage.cards.s.SupremeWill.class));
        cards.add(new SetCardInfo("The Scorpion God", 146, Rarity.MYTHIC, mage.cards.t.TheScorpionGod.class));
        cards.add(new SetCardInfo("Torment of Hailfire", 77, Rarity.RARE, mage.cards.t.TormentOfHailfire.class));
        cards.add(new SetCardInfo("Torment of Scarabs", 78, Rarity.UNCOMMON, mage.cards.t.TormentOfScarabs.class));
        cards.add(new SetCardInfo("Torment of Venom", 79, Rarity.COMMON, mage.cards.t.TormentOfVenom.class));
        cards.add(new SetCardInfo("Unesh, Criosphinx Sovereign", 52, Rarity.MYTHIC, mage.cards.u.UneshCriosphinxSovereign.class));
        cards.add(new SetCardInfo("Visage of Bolas", 208, Rarity.RARE, mage.cards.v.VisageOfBolas.class));
        cards.add(new SetCardInfo("Vizier of the True", 28, Rarity.UNCOMMON, mage.cards.v.VizierOfTheTrue.class));
        cards.add(new SetCardInfo("Wasp of the Bitter End", 206, Rarity.UNCOMMON, mage.cards.w.WaspOfTheBitterEnd.class));
        cards.add(new SetCardInfo("Wildfire Eternal", 109, Rarity.RARE, mage.cards.w.WildfireEternal.class));
        cards.add(new SetCardInfo("Woodland Stream", 204, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("Zealot of the God-Pharaoh", 207, Rarity.COMMON, mage.cards.z.ZealotOfTheGodPharaoh.class));
    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("MPS-AKH");
            criteria.minCardNumber(31);
            criteria.maxCardNumber(54);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
