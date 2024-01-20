package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class MurdersAtKarlovManor extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Aurelia's Vindicator", "Branch of Vitu-Ghazi", "Culvert Ambusher", "Defenestrated Phantom", "Dog Walker", "Essence of Antiquity", "Expose the Culprit", "Faerie Snoop", "Fugitive Codebreaker", "Gadget Technician", "Granite Witness", "Museum Nightwatch", "Nervous Gardener", "Nightdrinker Moroii", "Pyrotechnic Performer", "Shady Informant", "Undercover Crocodelf", "Unyielding Gatekeeper");

    private static final MurdersAtKarlovManor instance = new MurdersAtKarlovManor();

    public static MurdersAtKarlovManor getInstance() {
        return instance;
    }

    private MurdersAtKarlovManor() {
        super("Murders at Karlov Manor", "MKM", ExpansionSet.buildDate(2024, 2, 9), SetType.EXPANSION);
        this.blockName = "Murders at Karlov Manor"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Alquist Proft, Master Sleuth", 185, Rarity.MYTHIC, mage.cards.a.AlquistProftMasterSleuth.class));
        cards.add(new SetCardInfo("Assassin's Trophy", 187, Rarity.RARE, mage.cards.a.AssassinsTrophy.class));
        cards.add(new SetCardInfo("Aurelia, the Law Above", 188, Rarity.RARE, mage.cards.a.AureliaTheLawAbove.class));
        cards.add(new SetCardInfo("Auspicious Arrival", 5, Rarity.COMMON, mage.cards.a.AuspiciousArrival.class));
        cards.add(new SetCardInfo("Benthic Criminologists", 40, Rarity.COMMON, mage.cards.b.BenthicCriminologists.class));
        cards.add(new SetCardInfo("Branch of Vitu-Ghazi", 258, Rarity.UNCOMMON, mage.cards.b.BranchOfVituGhazi.class));
        cards.add(new SetCardInfo("Burden of Proof", 42, Rarity.UNCOMMON, mage.cards.b.BurdenOfProof.class));
        cards.add(new SetCardInfo("Commercial District", 259, Rarity.RARE, mage.cards.c.CommercialDistrict.class));
        cards.add(new SetCardInfo("Crime Novelist", 121, Rarity.UNCOMMON, mage.cards.c.CrimeNovelist.class));
        cards.add(new SetCardInfo("Culvert Ambusher", 158, Rarity.UNCOMMON, mage.cards.c.CulvertAmbusher.class));
        cards.add(new SetCardInfo("Curious Cadaver", 194, Rarity.UNCOMMON, mage.cards.c.CuriousCadaver.class));
        cards.add(new SetCardInfo("Deduce", 52, Rarity.COMMON, mage.cards.d.Deduce.class));
        cards.add(new SetCardInfo("Defenestrated Phantom", 11, Rarity.COMMON, mage.cards.d.DefenestratedPhantom.class));
        cards.add(new SetCardInfo("Demand Answers", 122, Rarity.COMMON, mage.cards.d.DemandAnswers.class));
        cards.add(new SetCardInfo("Dog Walker", 197, Rarity.COMMON, mage.cards.d.DogWalker.class));
        cards.add(new SetCardInfo("Doppelgang", 198, Rarity.RARE, mage.cards.d.Doppelgang.class));
        cards.add(new SetCardInfo("Drag the Canal", 199, Rarity.RARE, mage.cards.d.DragTheCanal.class));
        cards.add(new SetCardInfo("Elegant Parlor", 260, Rarity.RARE, mage.cards.e.ElegantParlor.class));
        cards.add(new SetCardInfo("Escape Tunnel", 261, Rarity.COMMON, mage.cards.e.EscapeTunnel.class));
        cards.add(new SetCardInfo("Essence of Antiquity", 15, Rarity.UNCOMMON, mage.cards.e.EssenceOfAntiquity.class));
        cards.add(new SetCardInfo("Faerie Snoop", 203, Rarity.COMMON, mage.cards.f.FaerieSnoop.class));
        cards.add(new SetCardInfo("Fanatical Strength", 159, Rarity.COMMON, mage.cards.f.FanaticalStrength.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gadget Technician", 204, Rarity.COMMON, mage.cards.g.GadgetTechnician.class));
        cards.add(new SetCardInfo("Galvanize", 128, Rarity.COMMON, mage.cards.g.Galvanize.class));
        cards.add(new SetCardInfo("Gearbane Orangutan", 129, Rarity.COMMON, mage.cards.g.GearbaneOrangutan.class));
        cards.add(new SetCardInfo("Gleaming Geardrake", 205, Rarity.UNCOMMON, mage.cards.g.GleamingGeardrake.class));
        cards.add(new SetCardInfo("Granite Witness", 206, Rarity.COMMON, mage.cards.g.GraniteWitness.class));
        cards.add(new SetCardInfo("Harried Dronesmith", 131, Rarity.UNCOMMON, mage.cards.h.HarriedDronesmith.class));
        cards.add(new SetCardInfo("Hedge Maze", 262, Rarity.RARE, mage.cards.h.HedgeMaze.class));
        cards.add(new SetCardInfo("Homicide Investigator", 86, Rarity.RARE, mage.cards.h.HomicideInvestigator.class));
        cards.add(new SetCardInfo("Hotshot Investigators", 60, Rarity.COMMON, mage.cards.h.HotshotInvestigators.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jaded Analyst", 62, Rarity.COMMON, mage.cards.j.JadedAnalyst.class));
        cards.add(new SetCardInfo("Knife", 134, Rarity.UNCOMMON, mage.cards.k.Knife.class));
        cards.add(new SetCardInfo("Kraul Whipcracker", 213, Rarity.UNCOMMON, mage.cards.k.KraulWhipcracker.class));
        cards.add(new SetCardInfo("Krenko, Baron of Tin Street", 135, Rarity.RARE, mage.cards.k.KrenkoBaronOfTinStreet.class));
        cards.add(new SetCardInfo("Kylox, Visionary Inventor", 214, Rarity.RARE, mage.cards.k.KyloxVisionaryInventor.class));
        cards.add(new SetCardInfo("Lead Pipe", 90, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Lightning Helix", 218, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Long Goodbye", 92, Rarity.UNCOMMON, mage.cards.l.LongGoodbye.class));
        cards.add(new SetCardInfo("Loxodon Eavesdropper", 168, Rarity.COMMON, mage.cards.l.LoxodonEavesdropper.class));
        cards.add(new SetCardInfo("Lush Portico", 263, Rarity.RARE, mage.cards.l.LushPortico.class));
        cards.add(new SetCardInfo("Macabre Reconstruction", 93, Rarity.COMMON, mage.cards.m.MacabreReconstruction.class));
        cards.add(new SetCardInfo("Magnifying Glass", 255, Rarity.COMMON, mage.cards.m.MagnifyingGlass.class));
        cards.add(new SetCardInfo("Massacre Girl, Known Killer", 94, Rarity.MYTHIC, mage.cards.m.MassacreGirlKnownKiller.class));
        cards.add(new SetCardInfo("Meddling Youths", 219, Rarity.UNCOMMON, mage.cards.m.MeddlingYouths.class));
        cards.add(new SetCardInfo("Meticulous Archive", 264, Rarity.RARE, mage.cards.m.MeticulousArchive.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Museum Nightwatch", 25, Rarity.COMMON, mage.cards.m.MuseumNightwatch.class));
        cards.add(new SetCardInfo("Nervous Gardener", 169, Rarity.COMMON, mage.cards.n.NervousGardener.class));
        cards.add(new SetCardInfo("Nightdrinker Moroii", 96, Rarity.UNCOMMON, mage.cards.n.NightdrinkerMoroii.class));
        cards.add(new SetCardInfo("No More Lies", 221, Rarity.UNCOMMON, mage.cards.n.NoMoreLies.class));
        cards.add(new SetCardInfo("No Witnesses", 27, Rarity.RARE, mage.cards.n.NoWitnesses.class));
        cards.add(new SetCardInfo("Not on My Watch", 28, Rarity.UNCOMMON, mage.cards.n.NotOnMyWatch.class));
        cards.add(new SetCardInfo("Novice Inspector", 29, Rarity.COMMON, mage.cards.n.NoviceInspector.class));
        cards.add(new SetCardInfo("Out Cold", 66, Rarity.COMMON, mage.cards.o.OutCold.class));
        cards.add(new SetCardInfo("Persuasive Interrogators", 98, Rarity.UNCOMMON, mage.cards.p.PersuasiveInterrogators.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Private Eye", 223, Rarity.UNCOMMON, mage.cards.p.PrivateEye.class));
        cards.add(new SetCardInfo("Public Thoroughfare", 265, Rarity.COMMON, mage.cards.p.PublicThoroughfare.class));
        cards.add(new SetCardInfo("Raucous Theater", 266, Rarity.RARE, mage.cards.r.RaucousTheater.class));
        cards.add(new SetCardInfo("Sanitation Automaton", 256, Rarity.COMMON, mage.cards.s.SanitationAutomaton.class));
        cards.add(new SetCardInfo("Scene of the Crime", 267, Rarity.UNCOMMON, mage.cards.s.SceneOfTheCrime.class));
        cards.add(new SetCardInfo("Shadowy Backstreet", 268, Rarity.RARE, mage.cards.s.ShadowyBackstreet.class));
        cards.add(new SetCardInfo("Shady Informant", 231, Rarity.COMMON, mage.cards.s.ShadyInformant.class));
        cards.add(new SetCardInfo("Shock", 144, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Steamcore Scholar", 71, Rarity.RARE, mage.cards.s.SteamcoreScholar.class));
        cards.add(new SetCardInfo("Sumala Sentry", 233, Rarity.UNCOMMON, mage.cards.s.SumalaSentry.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Thundering Falls", 269, Rarity.RARE, mage.cards.t.ThunderingFalls.class));
        cards.add(new SetCardInfo("Topiary Panther", 179, Rarity.COMMON, mage.cards.t.TopiaryPanther.class));
        cards.add(new SetCardInfo("Trostani, Three Whispers", 238, Rarity.MYTHIC, mage.cards.t.TrostaniThreeWhispers.class));
        cards.add(new SetCardInfo("Undercity Eliminator", 108, Rarity.UNCOMMON, mage.cards.u.UndercityEliminator.class));
        cards.add(new SetCardInfo("Undercity Sewers", 270, Rarity.RARE, mage.cards.u.UndercitySewers.class));
        cards.add(new SetCardInfo("Undercover Crocodelf", 239, Rarity.COMMON, mage.cards.u.UndercoverCrocodelf.class));
        cards.add(new SetCardInfo("Underground Mortuary", 271, Rarity.RARE, mage.cards.u.UndergroundMortuary.class));
        cards.add(new SetCardInfo("Vein Ripper", 110, Rarity.MYTHIC, mage.cards.v.VeinRipper.class));
        cards.add(new SetCardInfo("Voja, Jaws of the Conclave", 432, Rarity.MYTHIC, mage.cards.v.VojaJawsOfTheConclave.class));
        cards.add(new SetCardInfo("Warleader's Call", 242, Rarity.RARE, mage.cards.w.WarleadersCall.class));
        cards.add(new SetCardInfo("Wojek Investigator", 36, Rarity.RARE, mage.cards.w.WojekInvestigator.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }
}
