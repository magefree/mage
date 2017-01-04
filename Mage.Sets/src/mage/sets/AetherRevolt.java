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
public class AetherRevolt extends ExpansionSet {

    private static final AetherRevolt fINSTANCE = new AetherRevolt();

    public static AetherRevolt getInstance() {
        return fINSTANCE;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private AetherRevolt() {
        super("Aether Revolt", "AER", ExpansionSet.buildDate(2017, 1, 20), SetType.EXPANSION);
        this.blockName = "Kaladesh";
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 184;
        this.ratioBoosterSpecialLand = 144;
        this.parentSet = Kaladesh.getInstance();
        cards.add(new SetCardInfo("Aegis Automaton", 141, Rarity.COMMON, mage.cards.a.AegisAutomaton.class));
        cards.add(new SetCardInfo("Aeronaut Admiral", 2, Rarity.UNCOMMON, mage.cards.a.AeronautAdmiral.class));
        cards.add(new SetCardInfo("Aethergeode Miner", 4, Rarity.RARE, mage.cards.a.AethergeodeMiner.class));
        cards.add(new SetCardInfo("Aid from the Cowl", 105, Rarity.RARE, mage.cards.a.AidFromTheCowl.class));
        cards.add(new SetCardInfo("Ajani Unyielding", 127, Rarity.MYTHIC, mage.cards.a.AjaniUnyielding.class));
        cards.add(new SetCardInfo("Ajani, Valiant Protector", 185, Rarity.MYTHIC, mage.cards.a.AjaniValiantProtector.class));
        cards.add(new SetCardInfo("Battle at the Bridge", 53, Rarity.RARE, mage.cards.b.BattleAtTheBridge.class));
        cards.add(new SetCardInfo("Consulate Crackdown", 11, Rarity.RARE, mage.cards.c.ConsulateCrackdown.class));
        cards.add(new SetCardInfo("Consulate Dreadnought", 146, Rarity.UNCOMMON, mage.cards.c.ConsulateDreadnought.class));
        cards.add(new SetCardInfo("Countless Gears Renegade", 13, Rarity.COMMON, mage.cards.c.CountlessGearsRenegade.class));
        cards.add(new SetCardInfo("Dark Intimations", 128, Rarity.RARE, mage.cards.d.DarkIntimations.class));
        cards.add(new SetCardInfo("Decommission", 16, Rarity.COMMON, mage.cards.d.Decommission.class));
        cards.add(new SetCardInfo("Disallow", 31, Rarity.RARE, mage.cards.d.Disallow.class));
        cards.add(new SetCardInfo("Fatal Push", 57, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Foundry Assembler", 151, Rarity.COMMON, mage.cards.f.FoundryAssembler.class));
        cards.add(new SetCardInfo("Freejam Regent", 81, Rarity.RARE, mage.cards.f.FreejamRegent.class));
        cards.add(new SetCardInfo("Glint-Sleeve Siphoner", 62, Rarity.RARE, mage.cards.g.GlintSleeveSiphoner.class));
        cards.add(new SetCardInfo("Gonti's Aether Heart", 152, Rarity.MYTHIC, mage.cards.g.GontisAetherHeart.class));
        cards.add(new SetCardInfo("Greenbelt Druid", 106, Rarity.COMMON, mage.cards.g.GreenbeltDruid.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", 107, Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwheel Liberator", 108, Rarity.RARE, mage.cards.g.GreenwheelLiberator.class));
        cards.add(new SetCardInfo("Heart of Kiran", 153, Rarity.MYTHIC, mage.cards.h.HeartOfKiran.class));
        cards.add(new SetCardInfo("Hungry Flames", 84, Rarity.UNCOMMON, mage.cards.h.HungryFlames.class));
        cards.add(new SetCardInfo("Industrial Tower", 184, Rarity.RARE, mage.cards.i.IndustrialTower.class));
        cards.add(new SetCardInfo("Night Market Aeronaut", 67, Rarity.COMMON, mage.cards.n.NightMarketAeronaut.class));
        cards.add(new SetCardInfo("Oath of Ajani", 131, Rarity.RARE, mage.cards.o.OathOfAjani.class));
        cards.add(new SetCardInfo("Ornithopter", 167, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Pacification Array", 168, Rarity.UNCOMMON, mage.cards.p.PacificationArray.class));
        cards.add(new SetCardInfo("Paradox Engine", 169, Rarity.MYTHIC, mage.cards.p.ParadoxEngine.class));
        cards.add(new SetCardInfo("Peacekeeper Colossus", 170, Rarity.RARE, mage.cards.p.PeacekeeperColossus.class));
        cards.add(new SetCardInfo("Pia's Revolution", 91, Rarity.RARE, mage.cards.p.PiasRevolution.class));
        cards.add(new SetCardInfo("Planar Bridge", 171, Rarity.MYTHIC, mage.cards.p.PlanarBridge.class));
        cards.add(new SetCardInfo("Quicksmith Rebel", 93, Rarity.RARE, mage.cards.q.QuicksmithRebel.class));
        cards.add(new SetCardInfo("Quicksmith Spy", 41, Rarity.RARE, mage.cards.q.QuicksmithSpy.class));
        cards.add(new SetCardInfo("Renegade Map", 173, Rarity.COMMON, mage.cards.r.RenegadeMap.class));
        cards.add(new SetCardInfo("Renegade Rallier", 133, Rarity.UNCOMMON, mage.cards.r.RenegadeRallier.class));
        cards.add(new SetCardInfo("Reverse Engineer", 42, Rarity.UNCOMMON, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", 123, Rarity.RARE, mage.cards.r.RishkarsExpertise.class));
        cards.add(new SetCardInfo("Rogue Refiner", 135, Rarity.UNCOMMON, mage.cards.r.RogueRefiner.class));
        cards.add(new SetCardInfo("Scrap Trawler", 175, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Shipwreck Moray", 45, Rarity.COMMON, mage.cards.s.ShipwreckMoray.class));
        cards.add(new SetCardInfo("Shock", 98, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Siege Modification", 99, Rarity.UNCOMMON, mage.cards.s.SiegeModification.class));
        cards.add(new SetCardInfo("Silkweaver Elite", 125, Rarity.COMMON, mage.cards.s.SilkweaverElite.class));
        cards.add(new SetCardInfo("Sram's Expertise", 24, Rarity.RARE, mage.cards.s.SramsExpertise.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 23, Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Sweatworks Brawler", 100, Rarity.COMMON, mage.cards.s.SweatworksBrawler.class));
        cards.add(new SetCardInfo("Tezzeret the Schemer", 137, Rarity.MYTHIC, mage.cards.t.TezzeretTheSchemer.class));
        cards.add(new SetCardInfo("Tezzeret's Touch", 138, Rarity.UNCOMMON, mage.cards.t.TezzeretsTouch.class));
        cards.add(new SetCardInfo("Tezzeret, Master of Metal", 190, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfMetal.class));
        cards.add(new SetCardInfo("Trophy Mage", 48, Rarity.UNCOMMON, mage.cards.t.TrophyMage.class));
        cards.add(new SetCardInfo("Untethered Express", 179, Rarity.UNCOMMON, mage.cards.u.UntetheredExpress.class));
        cards.add(new SetCardInfo("Whir of Invention", 49, Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Yahenni's Expertise", 75, Rarity.RARE, mage.cards.y.YahennisExpertise.class));
        cards.add(new SetCardInfo("Yahenni, Undying Partisan", 74, Rarity.RARE, mage.cards.y.YahenniUndyingPartisan.class));
    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("MPS");
            criteria.minCardNumber(31);
            criteria.maxCardNumber(54);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
