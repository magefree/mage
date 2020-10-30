package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pkld
 */
public class KaladeshPromos extends ExpansionSet {

    private static final KaladeshPromos instance = new KaladeshPromos();

    public static KaladeshPromos getInstance() {
        return instance;
    }

    private KaladeshPromos() {
        super("Kaladesh Promos", "PKLD", ExpansionSet.buildDate(2016, 9, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aetherflux Reservoir", "192s", Rarity.RARE, mage.cards.a.AetherfluxReservoir.class));
        cards.add(new SetCardInfo("Aethersquall Ancient", "39s", Rarity.RARE, mage.cards.a.AethersquallAncient.class));
        cards.add(new SetCardInfo("Aetherstorm Roc", "3s", Rarity.RARE, mage.cards.a.AetherstormRoc.class));
        cards.add(new SetCardInfo("Aetherworks Marvel", "193s", Rarity.MYTHIC, mage.cards.a.AetherworksMarvel.class));
        cards.add(new SetCardInfo("Angel of Invention", "4s", Rarity.MYTHIC, mage.cards.a.AngelOfInvention.class));
        cards.add(new SetCardInfo("Animation Module", "194s", Rarity.RARE, mage.cards.a.AnimationModule.class));
        cards.add(new SetCardInfo("Architect of the Untamed", "143s", Rarity.RARE, mage.cards.a.ArchitectOfTheUntamed.class));
        cards.add(new SetCardInfo("Authority of the Consuls", "5s", Rarity.RARE, mage.cards.a.AuthorityOfTheConsuls.class));
        cards.add(new SetCardInfo("Blooming Marsh", "243s", Rarity.RARE, mage.cards.b.BloomingMarsh.class));
        cards.add(new SetCardInfo("Bomat Courier", "199s", Rarity.RARE, mage.cards.b.BomatCourier.class));
        cards.add(new SetCardInfo("Botanical Sanctum", "244s", Rarity.RARE, mage.cards.b.BotanicalSanctum.class));
        cards.add(new SetCardInfo("Bristling Hydra", "147s", Rarity.RARE, mage.cards.b.BristlingHydra.class));
        cards.add(new SetCardInfo("Captured by the Consulate", "8s", Rarity.RARE, mage.cards.c.CapturedByTheConsulate.class));
        cards.add(new SetCardInfo("Cataclysmic Gearhulk", "9s", Rarity.MYTHIC, mage.cards.c.CataclysmicGearhulk.class));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", "110s", Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class));
        cards.add(new SetCardInfo("Chief of the Foundry", 200, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheFoundry.class));
        cards.add(new SetCardInfo("Combustible Gearhulk", "112p", Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Combustible Gearhulk", "112s", Rarity.MYTHIC, mage.cards.c.CombustibleGearhulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Concealed Courtyard", "245s", Rarity.RARE, mage.cards.c.ConcealedCourtyard.class));
        cards.add(new SetCardInfo("Confiscation Coup", "41s", Rarity.RARE, mage.cards.c.ConfiscationCoup.class));
        cards.add(new SetCardInfo("Cultivator of Blades", 151, Rarity.RARE, mage.cards.c.CultivatorOfBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivator of Blades", "151s", Rarity.RARE, mage.cards.c.CultivatorOfBlades.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivator's Caravan", "203s", Rarity.RARE, mage.cards.c.CultivatorsCaravan.class));
        cards.add(new SetCardInfo("Deadlock Trap", "204s", Rarity.RARE, mage.cards.d.DeadlockTrap.class));
        cards.add(new SetCardInfo("Demon of Dark Schemes", "73s", Rarity.MYTHIC, mage.cards.d.DemonOfDarkSchemes.class));
        cards.add(new SetCardInfo("Depala, Pilot Exemplar", "178s", Rarity.RARE, mage.cards.d.DepalaPilotExemplar.class));
        cards.add(new SetCardInfo("Dovin Baan", "179s", Rarity.MYTHIC, mage.cards.d.DovinBaan.class));
        cards.add(new SetCardInfo("Dubious Challenge", "152s", Rarity.RARE, mage.cards.d.DubiousChallenge.class));
        cards.add(new SetCardInfo("Dynavolt Tower", "208s", Rarity.RARE, mage.cards.d.DynavoltTower.class));
        cards.add(new SetCardInfo("Electrostatic Pummeler", "210s", Rarity.RARE, mage.cards.e.ElectrostaticPummeler.class));
        cards.add(new SetCardInfo("Eliminate the Competition", "78s", Rarity.RARE, mage.cards.e.EliminateTheCompetition.class));
        cards.add(new SetCardInfo("Essence Extraction", 80, Rarity.UNCOMMON, mage.cards.e.EssenceExtraction.class));
        cards.add(new SetCardInfo("Fateful Showdown", "114s", Rarity.RARE, mage.cards.f.FatefulShowdown.class));
        cards.add(new SetCardInfo("Fleetwheel Cruiser", "214s", Rarity.RARE, mage.cards.f.FleetwheelCruiser.class));
        cards.add(new SetCardInfo("Fumigate", "15s", Rarity.RARE, mage.cards.f.Fumigate.class));
        cards.add(new SetCardInfo("Ghirapur Orrery", "216s", Rarity.RARE, mage.cards.g.GhirapurOrrery.class));
        cards.add(new SetCardInfo("Gonti, Lord of Luxury", "84s", Rarity.RARE, mage.cards.g.GontiLordOfLuxury.class));
        cards.add(new SetCardInfo("Insidious Will", "52s", Rarity.RARE, mage.cards.i.InsidiousWill.class));
        cards.add(new SetCardInfo("Inspiring Vantage", "246s", Rarity.RARE, mage.cards.i.InspiringVantage.class));
        cards.add(new SetCardInfo("Inventors' Fair", "247s", Rarity.RARE, mage.cards.i.InventorsFair.class));
        cards.add(new SetCardInfo("Kambal, Consul of Allocation", "183s", Rarity.RARE, mage.cards.k.KambalConsulOfAllocation.class));
        cards.add(new SetCardInfo("Key to the City", "220s", Rarity.RARE, mage.cards.k.KeyToTheCity.class));
        cards.add(new SetCardInfo("Lathnu Hellion", "121s", Rarity.RARE, mage.cards.l.LathnuHellion.class));
        cards.add(new SetCardInfo("Lost Legacy", "88s", Rarity.RARE, mage.cards.l.LostLegacy.class));
        cards.add(new SetCardInfo("Madcap Experiment", "122s", Rarity.RARE, mage.cards.m.MadcapExperiment.class));
        cards.add(new SetCardInfo("Marionette Master", "90s", Rarity.RARE, mage.cards.m.MarionetteMaster.class));
        cards.add(new SetCardInfo("Master Trinketeer", "21s", Rarity.RARE, mage.cards.m.MasterTrinketeer.class));
        cards.add(new SetCardInfo("Metallurgic Summonings", "56s", Rarity.MYTHIC, mage.cards.m.MetallurgicSummonings.class));
        cards.add(new SetCardInfo("Metalwork Colossus", "222s", Rarity.RARE, mage.cards.m.MetalworkColossus.class));
        cards.add(new SetCardInfo("Midnight Oil", "92s", Rarity.RARE, mage.cards.m.MidnightOil.class));
        cards.add(new SetCardInfo("Multiform Wonder", "223s", Rarity.RARE, mage.cards.m.MultiformWonder.class));
        cards.add(new SetCardInfo("Nissa, Vital Force", "163s", Rarity.MYTHIC, mage.cards.n.NissaVitalForce.class));
        cards.add(new SetCardInfo("Noxious Gearhulk", "96p", Rarity.MYTHIC, mage.cards.n.NoxiousGearhulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Noxious Gearhulk", "96s", Rarity.MYTHIC, mage.cards.n.NoxiousGearhulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oviya Pashiri, Sage Lifecrafter", "165s", Rarity.RARE, mage.cards.o.OviyaPashiriSageLifecrafter.class));
        cards.add(new SetCardInfo("Padeem, Consul of Innovation", "59s", Rarity.RARE, mage.cards.p.PadeemConsulOfInnovation.class));
        cards.add(new SetCardInfo("Panharmonicon", "226s", Rarity.RARE, mage.cards.p.Panharmonicon.class));
        cards.add(new SetCardInfo("Paradoxical Outcome", "60s", Rarity.RARE, mage.cards.p.ParadoxicalOutcome.class));
        cards.add(new SetCardInfo("Pia Nalaar", "124s", Rarity.RARE, mage.cards.p.PiaNalaar.class));
        cards.add(new SetCardInfo("Rashmi, Eternities Crafter", "184s", Rarity.MYTHIC, mage.cards.r.RashmiEternitiesCrafter.class));
        cards.add(new SetCardInfo("Saheeli Rai", "186s", Rarity.MYTHIC, mage.cards.s.SaheeliRai.class));
        cards.add(new SetCardInfo("Saheeli's Artistry", 62, Rarity.RARE, mage.cards.s.SaheelisArtistry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saheeli's Artistry", "62s", Rarity.RARE, mage.cards.s.SaheelisArtistry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrapheap Scrounger", "231s", Rarity.RARE, mage.cards.s.ScrapheapScrounger.class));
        cards.add(new SetCardInfo("Skyship Stalker", 130, Rarity.RARE, mage.cards.s.SkyshipStalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyship Stalker", "130s", Rarity.RARE, mage.cards.s.SkyshipStalker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skysovereign, Consul Flagship", "234s", Rarity.MYTHIC, mage.cards.s.SkysovereignConsulFlagship.class));
        cards.add(new SetCardInfo("Smuggler's Copter", "235s", Rarity.RARE, mage.cards.s.SmugglersCopter.class));
        cards.add(new SetCardInfo("Spirebluff Canal", "249s", Rarity.RARE, mage.cards.s.SpirebluffCanal.class));
        cards.add(new SetCardInfo("Syndicate Trafficker", "101s", Rarity.RARE, mage.cards.s.SyndicateTrafficker.class));
        cards.add(new SetCardInfo("Territorial Gorger", "136s", Rarity.RARE, mage.cards.t.TerritorialGorger.class));
        cards.add(new SetCardInfo("Toolcraft Exemplar", "32s", Rarity.RARE, mage.cards.t.ToolcraftExemplar.class));
        cards.add(new SetCardInfo("Torrential Gearhulk", "67s", Rarity.MYTHIC, mage.cards.t.TorrentialGearhulk.class));
        cards.add(new SetCardInfo("Verdurous Gearhulk", "172s", Rarity.MYTHIC, mage.cards.v.VerdurousGearhulk.class));
        cards.add(new SetCardInfo("Wildest Dreams", "174s", Rarity.RARE, mage.cards.w.WildestDreams.class));
     }
}
