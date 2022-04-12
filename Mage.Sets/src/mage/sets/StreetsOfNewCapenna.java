package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class StreetsOfNewCapenna extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Disciplined Duelist", "Elspeth Resplendent", "Falco Spara, Pactweaver", "Jaxis, the Troublemaker");

    private static final StreetsOfNewCapenna instance = new StreetsOfNewCapenna();

    public static StreetsOfNewCapenna getInstance() {
        return instance;
    }

    private StreetsOfNewCapenna() {
        super("Streets of New Capenna", "SNC", ExpansionSet.buildDate(2022, 4, 29), SetType.EXPANSION);
        this.blockName = "Streets of New Capenna";
        this.hasBoosters = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("A Little Chat", 47, Rarity.UNCOMMON, mage.cards.a.ALittleChat.class));
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 51, Rarity.UNCOMMON, mage.cards.a.AnOfferYouCantRefuse.class));
        cards.add(new SetCardInfo("Arc Spitter", 233, Rarity.UNCOMMON, mage.cards.a.ArcSpitter.class));
        cards.add(new SetCardInfo("Ballroom Brawlers", 3, Rarity.UNCOMMON, mage.cards.b.BallroomBrawlers.class));
        cards.add(new SetCardInfo("Black Market Tycoon", 167, Rarity.RARE, mage.cards.b.BlackMarketTycoon.class));
        cards.add(new SetCardInfo("Botanical Plaza", 247, Rarity.COMMON, mage.cards.b.BotanicalPlaza.class));
        cards.add(new SetCardInfo("Brokers Ascendancy", 170, Rarity.RARE, mage.cards.b.BrokersAscendancy.class));
        cards.add(new SetCardInfo("Brokers Charm", 171, Rarity.UNCOMMON, mage.cards.b.BrokersCharm.class));
        cards.add(new SetCardInfo("Cabaretti Charm", 173, Rarity.UNCOMMON, mage.cards.c.CabarettiCharm.class));
        cards.add(new SetCardInfo("Ceremonial Groundbreaker", 175, Rarity.UNCOMMON, mage.cards.c.CeremonialGroundbreaker.class));
        cards.add(new SetCardInfo("Chrome Cat", 236, Rarity.COMMON, mage.cards.c.ChromeCat.class));
        cards.add(new SetCardInfo("Cormela, Glamour Thief", 177, Rarity.UNCOMMON, mage.cards.c.CormelaGlamourThief.class));
        cards.add(new SetCardInfo("Courier's Briefcase", 142, Rarity.UNCOMMON, mage.cards.c.CouriersBriefcase.class));
        cards.add(new SetCardInfo("Cut Your Losses", 38, Rarity.RARE, mage.cards.c.CutYourLosses.class));
        cards.add(new SetCardInfo("Cut of the Profits", 72, Rarity.RARE, mage.cards.c.CutOfTheProfits.class));
        cards.add(new SetCardInfo("Depopulate", 10, Rarity.RARE, mage.cards.d.Depopulate.class));
        cards.add(new SetCardInfo("Devilish Valet", 105, Rarity.RARE, mage.cards.d.DevilishValet.class));
        cards.add(new SetCardInfo("Dig Up the Body", 76, Rarity.COMMON, mage.cards.d.DigUpTheBody.class));
        cards.add(new SetCardInfo("Disciplined Duelist", 182, Rarity.UNCOMMON, mage.cards.d.DisciplinedDuelist.class));
        cards.add(new SetCardInfo("Echo Inspector", 40, Rarity.COMMON, mage.cards.e.EchoInspector.class));
        cards.add(new SetCardInfo("Errant, Street Artist", 41, Rarity.RARE, mage.cards.e.ErrantStreetArtist.class));
        cards.add(new SetCardInfo("Evolving Door", 144, Rarity.RARE, mage.cards.e.EvolvingDoor.class));
        cards.add(new SetCardInfo("Exotic Pets", 185, Rarity.UNCOMMON, mage.cards.e.ExoticPets.class));
        cards.add(new SetCardInfo("Faerie Vandal", 44, Rarity.UNCOMMON, mage.cards.f.FaerieVandal.class));
        cards.add(new SetCardInfo("Fight Rigging", 145, Rarity.RARE, mage.cards.f.FightRigging.class));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gala Greeters", 148, Rarity.RARE, mage.cards.g.GalaGreeters.class));
        cards.add(new SetCardInfo("Getaway Car", 237, Rarity.RARE, mage.cards.g.GetawayCar.class));
        cards.add(new SetCardInfo("Graveyard Shift", 81, Rarity.UNCOMMON, mage.cards.g.GraveyardShift.class));
        cards.add(new SetCardInfo("Grisly Sigil", 82, Rarity.UNCOMMON, mage.cards.g.GrislySigil.class));
        cards.add(new SetCardInfo("Halo Fountain", 15, Rarity.MYTHIC, mage.cards.h.HaloFountain.class));
        cards.add(new SetCardInfo("Hypnotic Grifter", 45, Rarity.UNCOMMON, mage.cards.h.HypnoticGrifter.class));
        cards.add(new SetCardInfo("Illicit Shipment", 83, Rarity.UNCOMMON, mage.cards.i.IllicitShipment.class));
        cards.add(new SetCardInfo("Illuminator Virtuoso", 17, Rarity.UNCOMMON, mage.cards.i.IlluminatorVirtuoso.class));
        cards.add(new SetCardInfo("Incriminate", 84, Rarity.COMMON, mage.cards.i.Incriminate.class));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaxis, the Troublemaker", 112, Rarity.RARE, mage.cards.j.JaxisTheTroublemaker.class));
        cards.add(new SetCardInfo("Jetmir's Garden", 250, Rarity.RARE, mage.cards.j.JetmirsGarden.class));
        cards.add(new SetCardInfo("Jetmir, Nexus of Revels", 193, Rarity.MYTHIC, mage.cards.j.JetmirNexusOfRevels.class));
        cards.add(new SetCardInfo("Join the Maestros", 85, Rarity.COMMON, mage.cards.j.JoinTheMaestros.class));
        cards.add(new SetCardInfo("Ledger Shredder", 46, Rarity.RARE, mage.cards.l.LedgerShredder.class));
        cards.add(new SetCardInfo("Light 'Em Up", 113, Rarity.COMMON, mage.cards.l.LightEmUp.class));
        cards.add(new SetCardInfo("Lord Xander, the Collector", 197, Rarity.MYTHIC, mage.cards.l.LordXanderTheCollector.class));
        cards.add(new SetCardInfo("Luxurious Libation", 152, Rarity.UNCOMMON, mage.cards.l.LuxuriousLibation.class));
        cards.add(new SetCardInfo("Maestros Charm", 199, Rarity.UNCOMMON, mage.cards.m.MaestrosCharm.class));
        cards.add(new SetCardInfo("Maestros Diabolist", 200, Rarity.RARE, mage.cards.m.MaestrosDiabolist.class));
        cards.add(new SetCardInfo("Maestros Initiate", 86, Rarity.COMMON, mage.cards.m.MaestrosInitiate.class));
        cards.add(new SetCardInfo("Maestros Theater", 251, Rarity.COMMON, mage.cards.m.MaestrosTheater.class));
        cards.add(new SetCardInfo("Mage's Attendant", 21, Rarity.UNCOMMON, mage.cards.m.MagesAttendant.class));
        cards.add(new SetCardInfo("Make Disappear", 49, Rarity.COMMON, mage.cards.m.MakeDisappear.class));
        cards.add(new SetCardInfo("Mountain", 268, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder", 88, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nimble Larcenist", 205, Rarity.UNCOMMON, mage.cards.n.NimbleLarcenist.class));
        cards.add(new SetCardInfo("Obscura Charm", 208, Rarity.UNCOMMON, mage.cards.o.ObscuraCharm.class));
        cards.add(new SetCardInfo("Obscura Interceptor", 209, Rarity.RARE, mage.cards.o.ObscuraInterceptor.class));
        cards.add(new SetCardInfo("Out of the Way", 52, Rarity.UNCOMMON, mage.cards.o.OutOfTheWay.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Professional Face-Breaker", 116, Rarity.RARE, mage.cards.p.ProfessionalFaceBreaker.class));
        cards.add(new SetCardInfo("Psychic Pickpocket", 54, Rarity.UNCOMMON, mage.cards.p.PsychicPickpocket.class));
        cards.add(new SetCardInfo("Pyre-Sledge Arsonist", 118, Rarity.UNCOMMON, mage.cards.p.PyreSledgeArsonist.class));
        cards.add(new SetCardInfo("Queza, Augur of Agonies", 212, Rarity.UNCOMMON, mage.cards.q.QuezaAugurOfAgonies.class));
        cards.add(new SetCardInfo("Racers' Ring", 253, Rarity.COMMON, mage.cards.r.RacersRing.class));
        cards.add(new SetCardInfo("Raffine's Informant", 26, Rarity.COMMON, mage.cards.r.RaffinesInformant.class));
        cards.add(new SetCardInfo("Raffine's Tower", 254, Rarity.RARE, mage.cards.r.RaffinesTower.class));
        cards.add(new SetCardInfo("Raffine, Scheming Seer", 213, Rarity.MYTHIC, mage.cards.r.RaffineSchemingSeer.class));
        cards.add(new SetCardInfo("Revel Ruiner", 91, Rarity.COMMON, mage.cards.r.RevelRuiner.class));
        cards.add(new SetCardInfo("Riveteers Charm", 217, Rarity.UNCOMMON, mage.cards.r.RiveteersCharm.class));
        cards.add(new SetCardInfo("Rob the Archives", 122, Rarity.UNCOMMON, mage.cards.r.RobTheArchives.class));
        cards.add(new SetCardInfo("Rooftop Nuisance", 57, Rarity.COMMON, mage.cards.r.RooftopNuisance.class));
        cards.add(new SetCardInfo("Rumor Gatherer", 29, Rarity.UNCOMMON, mage.cards.r.RumorGatherer.class));
        cards.add(new SetCardInfo("Skybridge Towers", 256, Rarity.COMMON, mage.cards.s.SkybridgeTowers.class));
        cards.add(new SetCardInfo("Snooping Newsie", 222, Rarity.COMMON, mage.cards.s.SnoopingNewsie.class));
        cards.add(new SetCardInfo("Spara's Headquarters", 257, Rarity.RARE, mage.cards.s.SparasHeadquarters.class));
        cards.add(new SetCardInfo("Strangle", 125, Rarity.COMMON, mage.cards.s.Strangle.class));
        cards.add(new SetCardInfo("Suspicious Bookcase", 245, Rarity.UNCOMMON, mage.cards.s.SuspiciousBookcase.class));
        cards.add(new SetCardInfo("Swamp", 266, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tavern Swindler", 96, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Topiary Stomper", 160, Rarity.RARE, mage.cards.t.TopiaryStomper.class));
        cards.add(new SetCardInfo("Tramway Station", 258, Rarity.COMMON, mage.cards.t.TramwayStation.class));
        cards.add(new SetCardInfo("Unleash the Inferno", 229, Rarity.RARE, mage.cards.u.UnleashTheInferno.class));
        cards.add(new SetCardInfo("Unlucky Witness", 128, Rarity.UNCOMMON, mage.cards.u.UnluckyWitness.class));
        cards.add(new SetCardInfo("Urabrask, Heretic Praetor", 129, Rarity.MYTHIC, mage.cards.u.UrabraskHereticPraetor.class));
        cards.add(new SetCardInfo("Vampire Scrivener", 98, Rarity.UNCOMMON, mage.cards.v.VampireScrivener.class));
        cards.add(new SetCardInfo("Vivien on the Hunt", 162, Rarity.MYTHIC, mage.cards.v.VivienOnTheHunt.class));
        cards.add(new SetCardInfo("Waterfront District", 259, Rarity.COMMON, mage.cards.w.WaterfrontDistrict.class));
        cards.add(new SetCardInfo("Witness Protection", 66, Rarity.COMMON, mage.cards.w.WitnessProtection.class));
        cards.add(new SetCardInfo("Xander's Lounge", 260, Rarity.RARE, mage.cards.x.XandersLounge.class));
        cards.add(new SetCardInfo("Ziatora's Proving Ground", 261, Rarity.RARE, mage.cards.z.ZiatorasProvingGround.class));
        cards.add(new SetCardInfo("Ziatora, the Incinerator", 231, Rarity.MYTHIC, mage.cards.z.ZiatoraTheIncinerator.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when shield counters are implemented
    }
}
