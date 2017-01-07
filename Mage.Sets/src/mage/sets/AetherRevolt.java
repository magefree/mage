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

import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

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
        cards.add(new SetCardInfo("Aerial Modification", 1, Rarity.UNCOMMON, mage.cards.a.AerialModification.class));
        cards.add(new SetCardInfo("Aeronaut Admiral", 2, Rarity.UNCOMMON, mage.cards.a.AeronautAdmiral.class));
        cards.add(new SetCardInfo("Aether Chaser", 76, Rarity.COMMON, mage.cards.a.AetherChaser.class));
        cards.add(new SetCardInfo("Aether Herder", 102, Rarity.COMMON, mage.cards.a.AetherHerder.class));
        cards.add(new SetCardInfo("Aether Inspector", 3, Rarity.COMMON, mage.cards.a.AetherInspector.class));
        cards.add(new SetCardInfo("Aether Poisoner", 51, Rarity.COMMON, mage.cards.a.AetherPoisoner.class));
        cards.add(new SetCardInfo("Aether Swooper", 26, Rarity.COMMON, mage.cards.a.AetherSwooper.class));
        cards.add(new SetCardInfo("Aethergeode Miner", 4, Rarity.RARE, mage.cards.a.AethergeodeMiner.class));
        cards.add(new SetCardInfo("Aethersphere Harvester", 142, Rarity.RARE, mage.cards.a.AethersphereHarvester.class));
        cards.add(new SetCardInfo("Aetherstream Leopard", 103, Rarity.COMMON, mage.cards.a.AetherstreamLeopard.class));
        cards.add(new SetCardInfo("Aethertide Whale", 27, Rarity.RARE, mage.cards.a.AethertideWhale.class));
        cards.add(new SetCardInfo("Aetherwind Basker", 104, Rarity.MYTHIC, mage.cards.a.AetherwindBasker.class));
        cards.add(new SetCardInfo("Aid from the Cowl", 105, Rarity.RARE, mage.cards.a.AidFromTheCowl.class));
        cards.add(new SetCardInfo("Airdrop Aeronauts", 5, Rarity.UNCOMMON, mage.cards.a.AirdropAeronauts.class));
        cards.add(new SetCardInfo("Ajani Unyielding", 127, Rarity.MYTHIC, mage.cards.a.AjaniUnyielding.class));
        cards.add(new SetCardInfo("Ajani's Aid", 188, Rarity.RARE, mage.cards.a.AjanisAid.class));
        cards.add(new SetCardInfo("Ajani's Comrade", 187, Rarity.UNCOMMON, mage.cards.a.AjanisComrade.class));
        cards.add(new SetCardInfo("Ajani, Valiant Protector", 185, Rarity.MYTHIC, mage.cards.a.AjaniValiantProtector.class));
        cards.add(new SetCardInfo("Alley Evasion", 6, Rarity.COMMON, mage.cards.a.AlleyEvasion.class));
        cards.add(new SetCardInfo("Alley Strangler", 52, Rarity.COMMON, mage.cards.a.AlleyStrangler.class));
        cards.add(new SetCardInfo("Audacious Infiltrator", 7, Rarity.COMMON, mage.cards.a.AudaciousInfiltrator.class));
        cards.add(new SetCardInfo("Augmenting Automaton", 143, Rarity.COMMON, mage.cards.a.AugmentingAutomaton.class));
        cards.add(new SetCardInfo("Baral's Expertise", 29, Rarity.RARE, mage.cards.b.BaralsExpertise.class));
        cards.add(new SetCardInfo("Baral, Chief of Compliance", 28, Rarity.RARE, mage.cards.b.BaralChiefOfCompliance.class));
        cards.add(new SetCardInfo("Barricade Breaker", 144, Rarity.UNCOMMON, mage.cards.b.BarricadeBreaker.class));
        cards.add(new SetCardInfo("Bastion Enforcer", 8, Rarity.COMMON, mage.cards.b.BastionEnforcer.class));
        cards.add(new SetCardInfo("Battle at the Bridge", 53, Rarity.RARE, mage.cards.b.BattleAtTheBridge.class));
        cards.add(new SetCardInfo("Call for Unity", 9, Rarity.RARE, mage.cards.c.CallForUnity.class));
        cards.add(new SetCardInfo("Caught in the Brights", 10, Rarity.COMMON, mage.cards.c.CaughtInTheBrights.class));
        cards.add(new SetCardInfo("Consulate Crackdown", 11, Rarity.RARE, mage.cards.c.ConsulateCrackdown.class));
        cards.add(new SetCardInfo("Consulate Dreadnought", 146, Rarity.UNCOMMON, mage.cards.c.ConsulateDreadnought.class));
        cards.add(new SetCardInfo("Consulate Turret", 147, Rarity.COMMON, mage.cards.c.ConsulateTurret.class));
        cards.add(new SetCardInfo("Conviction", 12, Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Countless Gears Renegade", 13, Rarity.COMMON, mage.cards.c.CountlessGearsRenegade.class));
        cards.add(new SetCardInfo("Cruel Finality", 54, Rarity.COMMON, mage.cards.c.CruelFinality.class));
        cards.add(new SetCardInfo("Daring Demolition", 55, Rarity.COMMON, mage.cards.d.DaringDemolition.class));
        cards.add(new SetCardInfo("Dark Intimations", 128, Rarity.RARE, mage.cards.d.DarkIntimations.class));
        cards.add(new SetCardInfo("Dawnfeather Eagle", 14, Rarity.COMMON, mage.cards.d.DawnfeatherEagle.class));
        cards.add(new SetCardInfo("Deadeye Harpooner", 15, Rarity.UNCOMMON, mage.cards.d.DeadeyeHarpooner.class));
        cards.add(new SetCardInfo("Decommission", 16, Rarity.COMMON, mage.cards.d.Decommission.class));
        cards.add(new SetCardInfo("Defiant Salvager", 56, Rarity.COMMON, mage.cards.d.DefiantSalvager.class));
        cards.add(new SetCardInfo("Deft Dismissal", 17, Rarity.UNCOMMON, mage.cards.d.DeftDismissal.class));
        cards.add(new SetCardInfo("Disallow", 31, Rarity.RARE, mage.cards.d.Disallow.class));
        cards.add(new SetCardInfo("Druid of the Cowl", 106, Rarity.COMMON, mage.cards.d.DruidOfTheCowl.class));
        cards.add(new SetCardInfo("Efficient Construction", 33, Rarity.UNCOMMON, mage.cards.e.EfficientConstruction.class));
        cards.add(new SetCardInfo("Exquisite Archangel", 18, Rarity.MYTHIC, mage.cards.e.ExquisiteArchangel.class));
        cards.add(new SetCardInfo("Fatal Push", 57, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Felidar Guardian", 19, Rarity.UNCOMMON, mage.cards.f.FelidarGuardian.class));
        cards.add(new SetCardInfo("Fen Hauler", 58, Rarity.COMMON, mage.cards.f.FenHauler.class));
        cards.add(new SetCardInfo("Filigree Crawler", 150, Rarity.COMMON, mage.cards.f.FiligreeCrawler.class));
        cards.add(new SetCardInfo("Foundry Assembler", 151, Rarity.COMMON, mage.cards.f.FoundryAssembler.class));
        cards.add(new SetCardInfo("Foundry Hornet", 59, Rarity.UNCOMMON, mage.cards.f.FoundryHornet.class));
        cards.add(new SetCardInfo("Fourth Bridge Prowler", 60, Rarity.COMMON, mage.cards.f.FourthBridgeProwler.class));
        cards.add(new SetCardInfo("Freejam Regent", 81, Rarity.RARE, mage.cards.f.FreejamRegent.class));
        cards.add(new SetCardInfo("Ghirapur Osprey", 20, Rarity.COMMON, mage.cards.g.GhirapurOsprey.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", 61, Rarity.UNCOMMON, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Glint-Sleeve Siphoner", 62, Rarity.RARE, mage.cards.g.GlintSleeveSiphoner.class));
        cards.add(new SetCardInfo("Gonti's Aether Heart", 152, Rarity.MYTHIC, mage.cards.g.GontisAetherHeart.class));
        cards.add(new SetCardInfo("Gonti's Machinations", 63, Rarity.UNCOMMON, mage.cards.g.GontisMachinations.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", 107, Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwheel Liberator", 108, Rarity.RARE, mage.cards.g.GreenwheelLiberator.class));
        cards.add(new SetCardInfo("Heart of Kiran", 153, Rarity.MYTHIC, mage.cards.h.HeartOfKiran.class));
        cards.add(new SetCardInfo("Herald of Anguish", 64, Rarity.MYTHIC, mage.cards.h.HeraldOfAnguish.class));
        cards.add(new SetCardInfo("Heroic Intervention", 109, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Hidden Herbalists", 110, Rarity.UNCOMMON, mage.cards.h.HiddenHerbalists.class));
        cards.add(new SetCardInfo("Hidden Stockpile", 129, Rarity.UNCOMMON, mage.cards.h.HiddenStockpile.class));
        cards.add(new SetCardInfo("Highspire Infusion", 111, Rarity.COMMON, mage.cards.h.HighspireInfusion.class));
        cards.add(new SetCardInfo("Hungry Flames", 84, Rarity.UNCOMMON, mage.cards.h.HungryFlames.class));
        cards.add(new SetCardInfo("Implement of Combustion", 155, Rarity.COMMON, mage.cards.i.ImplementOfCombustion.class));
        cards.add(new SetCardInfo("Implement of Examination", 156, Rarity.COMMON, mage.cards.i.ImplementOfExamination.class));
        cards.add(new SetCardInfo("Implement of Ferocity", 157, Rarity.COMMON, mage.cards.i.ImplementOfFerocity.class));
        cards.add(new SetCardInfo("Implement of Improvement", 158, Rarity.COMMON, mage.cards.i.ImplementOfImprovement.class));
        cards.add(new SetCardInfo("Implement of Malice", 159, Rarity.COMMON, mage.cards.i.ImplementOfMalice.class));
        cards.add(new SetCardInfo("Inspiring Roar", 186, Rarity.COMMON, mage.cards.i.InspiringRoar.class));
        cards.add(new SetCardInfo("Inspiring Statuary", 160, Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Ironclad Revolutionary", 65, Rarity.UNCOMMON, mage.cards.i.IroncladRevolutionary.class));
        cards.add(new SetCardInfo("Irontread Crusher", 161, Rarity.COMMON, mage.cards.i.IrontreadCrusher.class));
        cards.add(new SetCardInfo("Kari Zev's Expertise", 88, Rarity.RARE, mage.cards.k.KariZevsExpertise.class));
        cards.add(new SetCardInfo("Kari Zev, Skyship Raider", 87, Rarity.RARE, mage.cards.k.KariZevSkyshipRaider.class));
        cards.add(new SetCardInfo("Lathnu Sailback", 89, Rarity.COMMON, mage.cards.l.LathnuSailback.class));
        cards.add(new SetCardInfo("Lifecraft Awakening", 112, Rarity.UNCOMMON, mage.cards.l.LifecraftAwakening.class));
        cards.add(new SetCardInfo("Lifecraft Cavalry", 113, Rarity.COMMON, mage.cards.l.LifecraftCavalry.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 162, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Lifecrafter's Gift", 114, Rarity.UNCOMMON, mage.cards.l.LifecraftersGift.class));
        cards.add(new SetCardInfo("Lightning Runner", 90, Rarity.MYTHIC, mage.cards.l.LightningRunner.class));
        cards.add(new SetCardInfo("Maulfist Revolutionary", 115, Rarity.UNCOMMON, mage.cards.m.MaulfistRevolutionary.class));
        cards.add(new SetCardInfo("Maverick Thopterist", 130, Rarity.UNCOMMON, mage.cards.m.MaverickThopterist.class));
        cards.add(new SetCardInfo("Metallic Mimic", 164, Rarity.RARE, mage.cards.m.MetallicMimic.class));
        cards.add(new SetCardInfo("Metallic Rebuke", 39, Rarity.COMMON, mage.cards.m.MetallicRebuke.class));
        cards.add(new SetCardInfo("Midnight Entourage", 66, Rarity.RARE, mage.cards.m.MidnightEntourage.class));
        cards.add(new SetCardInfo("Mobile Garrison", 165, Rarity.COMMON, mage.cards.m.MobileGarrison.class));
        cards.add(new SetCardInfo("Monstrous Onslaught", 116, Rarity.UNCOMMON, mage.cards.m.MonstrousOnslaught.class));
        cards.add(new SetCardInfo("Narnam Renegade", 117, Rarity.UNCOMMON, mage.cards.n.NarnamRenegade.class));
        cards.add(new SetCardInfo("Natural Obsolescence", 118, Rarity.COMMON, mage.cards.n.NaturalObsolescence.class));
        cards.add(new SetCardInfo("Negate", 40, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Night Market Aeronaut", 67, Rarity.COMMON, mage.cards.n.NightMarketAeronaut.class));
        cards.add(new SetCardInfo("Oath of Ajani", 131, Rarity.RARE, mage.cards.o.OathOfAjani.class));
        cards.add(new SetCardInfo("Ornithopter", 167, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Outland Boar", 132, Rarity.UNCOMMON, mage.cards.o.OutlandBoar.class));
        cards.add(new SetCardInfo("Pacification Array", 168, Rarity.UNCOMMON, mage.cards.p.PacificationArray.class));
        cards.add(new SetCardInfo("Paradox Engine", 169, Rarity.MYTHIC, mage.cards.p.ParadoxEngine.class));
        cards.add(new SetCardInfo("Peacewalker Colossus", 170, Rarity.RARE, mage.cards.p.PeacewalkerColossus.class));
        cards.add(new SetCardInfo("Peema Aether-Seer", 119, Rarity.UNCOMMON, mage.cards.p.PeemaAetherSeer.class));
        cards.add(new SetCardInfo("Pendulum of Patterns", 192, Rarity.COMMON, mage.cards.p.PendulumOfPatterns.class));
        cards.add(new SetCardInfo("Perilous Predicament", 68, Rarity.UNCOMMON, mage.cards.p.PerilousPredicament.class));
        cards.add(new SetCardInfo("Pia's Revolution", 91, Rarity.RARE, mage.cards.p.PiasRevolution.class));
        cards.add(new SetCardInfo("Planar Bridge", 171, Rarity.MYTHIC, mage.cards.p.PlanarBridge.class));
        cards.add(new SetCardInfo("Precise Strike", 92, Rarity.COMMON, mage.cards.p.PreciseStrike.class));
        cards.add(new SetCardInfo("Prey Upon", 120, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prizefighter Construct", 172, Rarity.COMMON, mage.cards.p.PrizefighterConstruct.class));
        cards.add(new SetCardInfo("Quicksmith Rebel", 93, Rarity.RARE, mage.cards.q.QuicksmithRebel.class));
        cards.add(new SetCardInfo("Quicksmith Spy", 41, Rarity.RARE, mage.cards.q.QuicksmithSpy.class));
        cards.add(new SetCardInfo("Ravenous Intruder", 94, Rarity.UNCOMMON, mage.cards.r.RavenousIntruder.class));
        cards.add(new SetCardInfo("Release the Gremlins", 96, Rarity.RARE, mage.cards.r.ReleaseTheGremlins.class));
        cards.add(new SetCardInfo("Renegade Map", 173, Rarity.COMMON, mage.cards.r.RenegadeMap.class));
        cards.add(new SetCardInfo("Renegade Rallier", 133, Rarity.UNCOMMON, mage.cards.r.RenegadeRallier.class));
        cards.add(new SetCardInfo("Renegade Wheelsmith", 134, Rarity.UNCOMMON, mage.cards.r.RenegadeWheelsmith.class));
        cards.add(new SetCardInfo("Renegade's Getaway", 69, Rarity.COMMON, mage.cards.r.RenegadesGetaway.class));
        cards.add(new SetCardInfo("Resourceful Return", 70, Rarity.COMMON, mage.cards.r.ResourcefulReturn.class));
        cards.add(new SetCardInfo("Restoration Specialist", 21, Rarity.UNCOMMON, mage.cards.r.RestorationSpecialist.class));
        cards.add(new SetCardInfo("Reverse Engineer", 42, Rarity.UNCOMMON, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Ridgescale Tusker", 121, Rarity.UNCOMMON, mage.cards.r.RidgescaleTusker.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", 123, Rarity.RARE, mage.cards.r.RishkarsExpertise.class));
        cards.add(new SetCardInfo("Rishkar, Peema Renegade", 122, Rarity.RARE, mage.cards.r.RishkarPeemaRenegade.class));
        cards.add(new SetCardInfo("Rogue Refiner", 135, Rarity.UNCOMMON, mage.cards.r.RogueRefiner.class));
        cards.add(new SetCardInfo("Scrap Trawler", 175, Rarity.RARE, mage.cards.s.ScrapTrawler.class));
        cards.add(new SetCardInfo("Scrounging Bandar", 124, Rarity.COMMON, mage.cards.s.ScroungingBandar.class));
        cards.add(new SetCardInfo("Secret Salvage", 71, Rarity.RARE, mage.cards.s.SecretSalvage.class));
        cards.add(new SetCardInfo("Shipwreck Moray", 45, Rarity.COMMON, mage.cards.s.ShipwreckMoray.class));
        cards.add(new SetCardInfo("Shock", 98, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Siege Modification", 99, Rarity.UNCOMMON, mage.cards.s.SiegeModification.class));
        cards.add(new SetCardInfo("Silkweaver Elite", 125, Rarity.COMMON, mage.cards.s.SilkweaverElite.class));
        cards.add(new SetCardInfo("Skyship Plunderer", 46, Rarity.UNCOMMON, mage.cards.s.SkyshipPlunderer.class));
        cards.add(new SetCardInfo("Sly Requisitioner", 72, Rarity.UNCOMMON, mage.cards.s.SlyRequisitioner.class));
        cards.add(new SetCardInfo("Solemn Recruit", 22, Rarity.RARE, mage.cards.s.SolemnRecruit.class));
        cards.add(new SetCardInfo("Spire Patrol", 136, Rarity.UNCOMMON, mage.cards.s.SpirePatrol.class));
        cards.add(new SetCardInfo("Spire of Industry", 184, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Sram's Expertise", 24, Rarity.RARE, mage.cards.s.SramsExpertise.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", 23, Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 194, Rarity.COMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sweatworks Brawler", 100, Rarity.COMMON, mage.cards.s.SweatworksBrawler.class));
        cards.add(new SetCardInfo("Tezzeret the Schemer", 137, Rarity.MYTHIC, mage.cards.t.TezzeretTheSchemer.class));
        cards.add(new SetCardInfo("Tezzeret's Betrayal", 191, Rarity.RARE, mage.cards.t.TezzeretsBetrayal.class));
        cards.add(new SetCardInfo("Tezzeret's Touch", 138, Rarity.UNCOMMON, mage.cards.t.TezzeretsTouch.class));
        cards.add(new SetCardInfo("Tezzeret, Master of Metal", 190, Rarity.MYTHIC, mage.cards.t.TezzeretMasterOfMetal.class));
        cards.add(new SetCardInfo("Thopter Arrest", 25, Rarity.UNCOMMON, mage.cards.t.ThopterArrest.class));
        cards.add(new SetCardInfo("Tranquil Expanse", 189, Rarity.COMMON, mage.cards.t.TranquilExpanse.class));
        cards.add(new SetCardInfo("Treasure Keeper", 177, Rarity.UNCOMMON, mage.cards.t.TreasureKeeper.class));
        cards.add(new SetCardInfo("Trophy Mage", 48, Rarity.UNCOMMON, mage.cards.t.TrophyMage.class));
        cards.add(new SetCardInfo("Unbridled Growth", 126, Rarity.COMMON, mage.cards.u.UnbridledGrowth.class));
        cards.add(new SetCardInfo("Universal Solvent", 178, Rarity.COMMON, mage.cards.u.UniversalSolvent.class));
        cards.add(new SetCardInfo("Untethered Express", 179, Rarity.UNCOMMON, mage.cards.u.UntetheredExpress.class));
        cards.add(new SetCardInfo("Vengeful Rebel", 73, Rarity.UNCOMMON, mage.cards.v.VengefulRebel.class));
        cards.add(new SetCardInfo("Weldfast Engineer", 139, Rarity.UNCOMMON, mage.cards.w.WeldfastEngineer.class));
        cards.add(new SetCardInfo("Whir of Invention", 49, Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Winding Constrictor", 140, Rarity.UNCOMMON, mage.cards.w.WindingConstrictor.class));
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
