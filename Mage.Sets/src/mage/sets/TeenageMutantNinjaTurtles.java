package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TeenageMutantNinjaTurtles extends ExpansionSet {

    private static final TeenageMutantNinjaTurtles instance = new TeenageMutantNinjaTurtles();

    public static TeenageMutantNinjaTurtles getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtles() {
        super("Teenage Mutant Ninja Turtles", "TMT", ExpansionSet.buildDate(2026, 3, 6), SetType.EXPANSION);
        this.blockName = "Teenage Mutant Ninja Turtles"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Agent Bishop, Man in Black", 2, Rarity.RARE, mage.cards.a.AgentBishopManInBlack.class));
        cards.add(new SetCardInfo("April O'Neil, Hacktivist", 29, Rarity.RARE, mage.cards.a.AprilONeilHacktivist.class));
        cards.add(new SetCardInfo("Armaggon, Future Shark", 58, Rarity.RARE, mage.cards.a.ArmaggonFutureShark.class));
        cards.add(new SetCardInfo("Bebop & Rocksteady", 140, Rarity.RARE, mage.cards.b.BebopAndRocksteady.class));
        cards.add(new SetCardInfo("Broadcast Takeover", 86, Rarity.MYTHIC, mage.cards.b.BroadcastTakeover.class));
        cards.add(new SetCardInfo("Casey Jones, Jury-Rig Justiciar", 87, Rarity.UNCOMMON, mage.cards.c.CaseyJonesJuryRigJusticiar.class));
        cards.add(new SetCardInfo("Casey Jones, Vigilante", 88, Rarity.RARE, mage.cards.c.CaseyJonesVigilante.class));
        cards.add(new SetCardInfo("Chrome Dome", 172, Rarity.RARE, mage.cards.c.ChromeDome.class));
        cards.add(new SetCardInfo("Cool but Rude", 89, Rarity.RARE, mage.cards.c.CoolButRude.class));
        cards.add(new SetCardInfo("Dark Leo & Shredder", 142, Rarity.MYTHIC, mage.cards.d.DarkLeoAndShredder.class));
        cards.add(new SetCardInfo("Does Machines", 34, Rarity.RARE, mage.cards.d.DoesMachines.class));
        cards.add(new SetCardInfo("Don & Leo, Problem Solvers", 143, Rarity.RARE, mage.cards.d.DonAndLeoProblemSolvers.class));
        cards.add(new SetCardInfo("Don & Raph, Hard Science", 144, Rarity.RARE, mage.cards.d.DonAndRaphHardScience.class));
        cards.add(new SetCardInfo("Donatello, Gadget Master", 35, Rarity.RARE, mage.cards.d.DonatelloGadgetMaster.class));
        cards.add(new SetCardInfo("Donatello, Mutant Mechanic", 36, Rarity.MYTHIC, mage.cards.d.DonatelloMutantMechanic.class));
        cards.add(new SetCardInfo("Forest", 257, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 314, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Groundchuck & Dirtbag", 115, Rarity.RARE, mage.cards.g.GroundchuckAndDirtbag.class));
        cards.add(new SetCardInfo("Improvised Arsenal", 92, Rarity.RARE, mage.cards.i.ImprovisedArsenal.class));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 311, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kitsune's Technique", 42, Rarity.RARE, mage.cards.k.KitsunesTechnique.class));
        cards.add(new SetCardInfo("Kitsune, Dragon's Daughter", 41, Rarity.RARE, mage.cards.k.KitsuneDragonsDaughter.class));
        cards.add(new SetCardInfo("Krang & Shredder", 153, Rarity.RARE, mage.cards.k.KrangAndShredder.class));
        cards.add(new SetCardInfo("Krang, Master Mind", 43, Rarity.RARE, mage.cards.k.KrangMasterMind.class));
        cards.add(new SetCardInfo("Krang, Utrom Warlord", 175, Rarity.MYTHIC, mage.cards.k.KrangUtromWarlord.class));
        cards.add(new SetCardInfo("Leader's Talent", 13, Rarity.RARE, mage.cards.l.LeadersTalent.class));
        cards.add(new SetCardInfo("Leatherhead, Swamp Stalker", 117, Rarity.RARE, mage.cards.l.LeatherheadSwampStalker.class));
        cards.add(new SetCardInfo("Leonardo's Technique", 18, Rarity.RARE, mage.cards.l.LeonardosTechnique.class));
        cards.add(new SetCardInfo("Leonardo, Cutting Edge", 15, Rarity.RARE, mage.cards.l.LeonardoCuttingEdge.class));
        cards.add(new SetCardInfo("Leonardo, Sewer Samurai", 17, Rarity.MYTHIC, mage.cards.l.LeonardoSewerSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leonardo, Sewer Samurai", 301, Rarity.MYTHIC, mage.cards.l.LeonardoSewerSamurai.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Madame Null, Power Broker", 66, Rarity.RARE, mage.cards.m.MadameNullPowerBroker.class));
        cards.add(new SetCardInfo("Michelangelo's Technique", 122, Rarity.RARE, mage.cards.m.MichelangelosTechnique.class));
        cards.add(new SetCardInfo("Michelangelo, Improviser", 119, Rarity.MYTHIC, mage.cards.m.MichelangeloImproviser.class));
        cards.add(new SetCardInfo("Michelangelo, Weirdness to 11", 121, Rarity.RARE, mage.cards.m.MichelangeloWeirdnessTo11.class));
        cards.add(new SetCardInfo("Mikey & Leo, Chaos & Order", 158, Rarity.RARE, mage.cards.m.MikeyAndLeoChaosAndOrder.class));
        cards.add(new SetCardInfo("Mondo Gecko", 46, Rarity.MYTHIC, mage.cards.m.MondoGecko.class));
        cards.add(new SetCardInfo("Mountain", 256, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 313, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mutagen Man, Living Ooze", 124, Rarity.RARE, mage.cards.m.MutagenManLivingOoze.class));
        cards.add(new SetCardInfo("North Wind Avatar", 162, Rarity.MYTHIC, mage.cards.n.NorthWindAvatar.class));
        cards.add(new SetCardInfo("Northampton Farm", 188, Rarity.RARE, mage.cards.n.NorthamptonFarm.class));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 310, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Prehistoric Pet", 22, Rarity.RARE, mage.cards.p.PrehistoricPet.class));
        cards.add(new SetCardInfo("Raph & Leo, Sibling Rivals", 166, Rarity.RARE, mage.cards.r.RaphAndLeoSiblingRivals.class));
        cards.add(new SetCardInfo("Raph & Mikey, Troublemakers", 167, Rarity.RARE, mage.cards.r.RaphAndMikeyTroublemakers.class));
        cards.add(new SetCardInfo("Raphael's Technique", 105, Rarity.RARE, mage.cards.r.RaphaelsTechnique.class));
        cards.add(new SetCardInfo("Raphael, Ninja Destroyer", 102, Rarity.MYTHIC, mage.cards.r.RaphaelNinjaDestroyer.class));
        cards.add(new SetCardInfo("Raphael, the Nightwatcher", 103, Rarity.RARE, mage.cards.r.RaphaelTheNightwatcher.class));
        cards.add(new SetCardInfo("Rat King, Verminister", 71, Rarity.RARE, mage.cards.r.RatKingVerminister.class));
        cards.add(new SetCardInfo("Ravenous Robots", 106, Rarity.RARE, mage.cards.r.RavenousRobots.class));
        cards.add(new SetCardInfo("Renet, Temporal Apprentice", 50, Rarity.RARE, mage.cards.r.RenetTemporalApprentice.class));
        cards.add(new SetCardInfo("Sally Pride, Lioness Leader", 24, Rarity.RARE, mage.cards.s.SallyPrideLionessLeader.class));
        cards.add(new SetCardInfo("Savanti Romero, Time's Exile", 72, Rarity.RARE, mage.cards.s.SavantiRomeroTimesExile.class));
        cards.add(new SetCardInfo("Shark Shredder, Killer Clone", 73, Rarity.RARE, mage.cards.s.SharkShredderKillerClone.class));
        cards.add(new SetCardInfo("Slash, Reptile Rampager", 108, Rarity.RARE, mage.cards.s.SlashReptileRampager.class));
        cards.add(new SetCardInfo("South Wind Avatar", 78, Rarity.RARE, mage.cards.s.SouthWindAvatar.class));
        cards.add(new SetCardInfo("Splinter's Technique", 80, Rarity.RARE, mage.cards.s.SplintersTechnique.class));
        cards.add(new SetCardInfo("Splinter, Radical Rat", 169, Rarity.RARE, mage.cards.s.SplinterRadicalRat.class));
        cards.add(new SetCardInfo("Super Shredder", 83, Rarity.MYTHIC, mage.cards.s.SuperShredder.class));
        cards.add(new SetCardInfo("Swamp", 255, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 312, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Technodrome", 179, Rarity.MYTHIC, mage.cards.t.Technodrome.class));
        cards.add(new SetCardInfo("The Cloning of Shredder", 60, Rarity.MYTHIC, mage.cards.t.TheCloningOfShredder.class));
        cards.add(new SetCardInfo("The Last Ronin", 154, Rarity.MYTHIC, mage.cards.t.TheLastRonin.class));
        cards.add(new SetCardInfo("The Ooze", 177, Rarity.RARE, mage.cards.t.TheOoze.class));
        cards.add(new SetCardInfo("Tokka & Rahzar, Terrible Twos", 171, Rarity.RARE, mage.cards.t.TokkaAndRahzarTerribleTwos.class));
        cards.add(new SetCardInfo("Transdimensional Bovine", 134, Rarity.RARE, mage.cards.t.TransdimensionalBovine.class));
        cards.add(new SetCardInfo("Triceraton Commander", 25, Rarity.MYTHIC, mage.cards.t.TriceratonCommander.class));
        cards.add(new SetCardInfo("Turncoat Kunoichi", 26, Rarity.RARE, mage.cards.t.TurncoatKunoichi.class));
        cards.add(new SetCardInfo("Turtle Power!", 135, Rarity.RARE, mage.cards.t.TurtlePower.class));
        cards.add(new SetCardInfo("Turtle Van", 181, Rarity.RARE, mage.cards.t.TurtleVan.class));
        cards.add(new SetCardInfo("Weather Maker", 182, Rarity.RARE, mage.cards.w.WeatherMaker.class));
    }
}
