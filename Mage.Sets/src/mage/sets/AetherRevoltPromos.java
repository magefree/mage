package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/paer
 */
public class AetherRevoltPromos extends ExpansionSet {

    private static final AetherRevoltPromos instance = new AetherRevoltPromos();

    public static AetherRevoltPromos getInstance() {
        return instance;
    }

    private AetherRevoltPromos() {
        super("Aether Revolt Promos", "PAER", ExpansionSet.buildDate(2017, 1, 21), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aethergeode Miner", "4s", Rarity.RARE, mage.cards.a.AethergeodeMiner.class));
        cards.add(new SetCardInfo("Aethersphere Harvester", "142s", Rarity.RARE, mage.cards.a.AethersphereHarvester.class));
        cards.add(new SetCardInfo("Aethertide Whale", "27s", Rarity.RARE, mage.cards.a.AethertideWhale.class));
        cards.add(new SetCardInfo("Aetherwind Basker", "104s", Rarity.MYTHIC, mage.cards.a.AetherwindBasker.class));
        cards.add(new SetCardInfo("Aid from the Cowl", "105s", Rarity.RARE, mage.cards.a.AidFromTheCowl.class));
        cards.add(new SetCardInfo("Ajani Unyielding", "127s", Rarity.MYTHIC, mage.cards.a.AjaniUnyielding.class));
        cards.add(new SetCardInfo("Baral's Expertise", "29s", Rarity.RARE, mage.cards.b.BaralsExpertise.class));
        cards.add(new SetCardInfo("Baral, Chief of Compliance", "28s", Rarity.RARE, mage.cards.b.BaralChiefOfCompliance.class));
        cards.add(new SetCardInfo("Battle at the Bridge", "53s", Rarity.RARE, mage.cards.b.BattleAtTheBridge.class));
        cards.add(new SetCardInfo("Call for Unity", "9s", Rarity.RARE, mage.cards.c.CallForUnity.class));
        cards.add(new SetCardInfo("Consulate Crackdown", "11s", Rarity.RARE, mage.cards.c.ConsulateCrackdown.class));
        cards.add(new SetCardInfo("Dark Intimations", "128s", Rarity.RARE, mage.cards.d.DarkIntimations.class));
        cards.add(new SetCardInfo("Disallow", "31p", Rarity.RARE, mage.cards.d.Disallow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disallow", "31s", Rarity.RARE, mage.cards.d.Disallow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Exquisite Archangel", "18s", Rarity.MYTHIC, mage.cards.e.ExquisiteArchangel.class));
        cards.add(new SetCardInfo("Freejam Regent", "81s", Rarity.RARE, mage.cards.f.FreejamRegent.class));
        cards.add(new SetCardInfo("Glint-Sleeve Siphoner", "62s", Rarity.RARE, mage.cards.g.GlintSleeveSiphoner.class));
        cards.add(new SetCardInfo("Gonti's Aether Heart", "152s", Rarity.MYTHIC, mage.cards.g.GontisAetherHeart.class));
        cards.add(new SetCardInfo("Greenbelt Rampager", "107s", Rarity.RARE, mage.cards.g.GreenbeltRampager.class));
        cards.add(new SetCardInfo("Greenwheel Liberator", "108s", Rarity.RARE, mage.cards.g.GreenwheelLiberator.class));
        cards.add(new SetCardInfo("Heart of Kiran", "153s", Rarity.MYTHIC, mage.cards.h.HeartOfKiran.class));
        cards.add(new SetCardInfo("Herald of Anguish", "64s", Rarity.MYTHIC, mage.cards.h.HeraldOfAnguish.class));
        cards.add(new SetCardInfo("Heroic Intervention", "109p", Rarity.RARE, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroic Intervention", "109s", Rarity.RARE, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hope of Ghirapur", "154s", Rarity.RARE, mage.cards.h.HopeOfGhirapur.class));
        cards.add(new SetCardInfo("Indomitable Creativity", "85s", Rarity.MYTHIC, mage.cards.i.IndomitableCreativity.class));
        cards.add(new SetCardInfo("Inspiring Statuary", "160s", Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Kari Zev's Expertise", "88s", Rarity.RARE, mage.cards.k.KariZevsExpertise.class));
        cards.add(new SetCardInfo("Kari Zev, Skyship Raider", "87s", Rarity.RARE, mage.cards.k.KariZevSkyshipRaider.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", "162s", Rarity.RARE, mage.cards.l.LifecraftersBestiary.class));
        cards.add(new SetCardInfo("Lightning Runner", "90s", Rarity.MYTHIC, mage.cards.l.LightningRunner.class));
        cards.add(new SetCardInfo("Mechanized Production", "38s", Rarity.MYTHIC, mage.cards.m.MechanizedProduction.class));
        cards.add(new SetCardInfo("Merchant's Dockhand", "163s", Rarity.RARE, mage.cards.m.MerchantsDockhand.class));
        cards.add(new SetCardInfo("Metallic Mimic", "164s", Rarity.RARE, mage.cards.m.MetallicMimic.class));
        cards.add(new SetCardInfo("Midnight Entourage", "66s", Rarity.RARE, mage.cards.m.MidnightEntourage.class));
        cards.add(new SetCardInfo("Oath of Ajani", "131s", Rarity.RARE, mage.cards.o.OathOfAjani.class));
        cards.add(new SetCardInfo("Paradox Engine", "169s", Rarity.MYTHIC, mage.cards.p.ParadoxEngine.class));
        cards.add(new SetCardInfo("Peacewalker Colossus", "170s", Rarity.RARE, mage.cards.p.PeacewalkerColossus.class));
        cards.add(new SetCardInfo("Pia's Revolution", "91s", Rarity.RARE, mage.cards.p.PiasRevolution.class));
        cards.add(new SetCardInfo("Planar Bridge", "171s", Rarity.MYTHIC, mage.cards.p.PlanarBridge.class));
        cards.add(new SetCardInfo("Quicksmith Rebel", 93, Rarity.RARE, mage.cards.q.QuicksmithRebel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksmith Rebel", "93s", Rarity.RARE, mage.cards.q.QuicksmithRebel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksmith Spy", "41s", Rarity.RARE, mage.cards.q.QuicksmithSpy.class));
        cards.add(new SetCardInfo("Release the Gremlins", "96s", Rarity.RARE, mage.cards.r.ReleaseTheGremlins.class));
        cards.add(new SetCardInfo("Rishkar's Expertise", "123s", Rarity.RARE, mage.cards.r.RishkarsExpertise.class));
        cards.add(new SetCardInfo("Rishkar, Peema Renegade", "122s", Rarity.RARE, mage.cards.r.RishkarPeemaRenegade.class));
        cards.add(new SetCardInfo("Scrap Trawler", 175, Rarity.RARE, mage.cards.s.ScrapTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrap Trawler", "175s", Rarity.RARE, mage.cards.s.ScrapTrawler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Salvage", "71s", Rarity.RARE, mage.cards.s.SecretSalvage.class));
        cards.add(new SetCardInfo("Solemn Recruit", "22s", Rarity.RARE, mage.cards.s.SolemnRecruit.class));
        cards.add(new SetCardInfo("Spire of Industry", "184s", Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Sram's Expertise", "24s", Rarity.RARE, mage.cards.s.SramsExpertise.class));
        cards.add(new SetCardInfo("Sram, Senior Edificer", "23s", Rarity.RARE, mage.cards.s.SramSeniorEdificer.class));
        cards.add(new SetCardInfo("Tezzeret the Schemer", "137s", Rarity.MYTHIC, mage.cards.t.TezzeretTheSchemer.class));
        cards.add(new SetCardInfo("Trophy Mage", 48, Rarity.UNCOMMON, mage.cards.t.TrophyMage.class));
        cards.add(new SetCardInfo("Walking Ballista", "181s", Rarity.RARE, mage.cards.w.WalkingBallista.class));
        cards.add(new SetCardInfo("Whir of Invention", "49s", Rarity.RARE, mage.cards.w.WhirOfInvention.class));
        cards.add(new SetCardInfo("Yahenni's Expertise", 75, Rarity.RARE, mage.cards.y.YahennisExpertise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yahenni's Expertise", "75s", Rarity.RARE, mage.cards.y.YahennisExpertise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yahenni, Undying Partisan", "74s", Rarity.RARE, mage.cards.y.YahenniUndyingPartisan.class));
     }
}
