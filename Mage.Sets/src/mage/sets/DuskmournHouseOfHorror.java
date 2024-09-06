package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DuskmournHouseOfHorror extends ExpansionSet {

    private static final DuskmournHouseOfHorror instance = new DuskmournHouseOfHorror();

    public static DuskmournHouseOfHorror getInstance() {
        return instance;
    }

    private DuskmournHouseOfHorror() {
        super("Duskmourn: House of Horror", "DSK", ExpansionSet.buildDate(2024, 9, 27), SetType.EXPANSION);
        this.blockName = "Duskmourn: House of Horror"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Abandoned Campground", 255, Rarity.COMMON, mage.cards.a.AbandonedCampground.class));
        cards.add(new SetCardInfo("Altanak, the Thrice-Called", 166, Rarity.UNCOMMON, mage.cards.a.AltanakTheThriceCalled.class));
        cards.add(new SetCardInfo("Anthropede", 167, Rarity.COMMON, mage.cards.a.Anthropede.class));
        cards.add(new SetCardInfo("Attack-in-the-Box", 242, Rarity.UNCOMMON, mage.cards.a.AttackInTheBox.class));
        cards.add(new SetCardInfo("Balustrade Wurm", 168, Rarity.RARE, mage.cards.b.BalustradeWurm.class));
        cards.add(new SetCardInfo("Blazemire Verge", 256, Rarity.RARE, mage.cards.b.BlazemireVerge.class));
        cards.add(new SetCardInfo("Bleeding Woods", 257, Rarity.COMMON, mage.cards.b.BleedingWoods.class));
        cards.add(new SetCardInfo("Chainsaw", 128, Rarity.RARE, mage.cards.c.Chainsaw.class));
        cards.add(new SetCardInfo("Clockwork Percussionist", 130, Rarity.COMMON, mage.cards.c.ClockworkPercussionist.class));
        cards.add(new SetCardInfo("Cult Healer", 2, Rarity.COMMON, mage.cards.c.CultHealer.class));
        cards.add(new SetCardInfo("Cursed Recording", 131, Rarity.RARE, mage.cards.c.CursedRecording.class));
        cards.add(new SetCardInfo("Cursed Windbreaker", 47, Rarity.UNCOMMON, mage.cards.c.CursedWindbreaker.class));
        cards.add(new SetCardInfo("Cynical Loner", 89, Rarity.UNCOMMON, mage.cards.c.CynicalLoner.class));
        cards.add(new SetCardInfo("Dashing Bloodsucker", 90, Rarity.UNCOMMON, mage.cards.d.DashingBloodsucker.class));
        cards.add(new SetCardInfo("Demonic Counsel", 92, Rarity.RARE, mage.cards.d.DemonicCounsel.class));
        cards.add(new SetCardInfo("Disturbing Mirth", 212, Rarity.UNCOMMON, mage.cards.d.DisturbingMirth.class));
        cards.add(new SetCardInfo("Doomsday Excruciator", 94, Rarity.RARE, mage.cards.d.DoomsdayExcruciator.class));
        cards.add(new SetCardInfo("Drag to the Roots", 213, Rarity.UNCOMMON, mage.cards.d.DragToTheRoots.class));
        cards.add(new SetCardInfo("Enduring Curiosity", 51, Rarity.RARE, mage.cards.e.EnduringCuriosity.class));
        cards.add(new SetCardInfo("Enduring Innocence", 6, Rarity.RARE, mage.cards.e.EnduringInnocence.class));
        cards.add(new SetCardInfo("Enduring Tenacity", 95, Rarity.RARE, mage.cards.e.EnduringTenacity.class));
        cards.add(new SetCardInfo("Etched Cornfield", 258, Rarity.COMMON, mage.cards.e.EtchedCornfield.class));
        cards.add(new SetCardInfo("Ethereal Armor", 7, Rarity.UNCOMMON, mage.cards.e.EtherealArmor.class));
        cards.add(new SetCardInfo("Fear of Failed Tests", 55, Rarity.UNCOMMON, mage.cards.f.FearOfFailedTests.class));
        cards.add(new SetCardInfo("Fear of Immobility", 10, Rarity.COMMON, mage.cards.f.FearOfImmobility.class));
        cards.add(new SetCardInfo("Fear of Impostors", 57, Rarity.UNCOMMON, mage.cards.f.FearOfImpostors.class));
        cards.add(new SetCardInfo("Fear of Infinity", 214, Rarity.UNCOMMON, mage.cards.f.FearOfInfinity.class));
        cards.add(new SetCardInfo("Fear of Isolation", 58, Rarity.UNCOMMON, mage.cards.f.FearOfIsolation.class));
        cards.add(new SetCardInfo("Fear of Lost Teeth", 97, Rarity.COMMON, mage.cards.f.FearOfLostTeeth.class));
        cards.add(new SetCardInfo("Fear of Missing Out", 136, Rarity.RARE, mage.cards.f.FearOfMissingOut.class));
        cards.add(new SetCardInfo("Fear of the Dark", 98, Rarity.COMMON, mage.cards.f.FearOfTheDark.class));
        cards.add(new SetCardInfo("Floodfarm Verge", 259, Rarity.RARE, mage.cards.f.FloodfarmVerge.class));
        cards.add(new SetCardInfo("Floodpits Drowner", 59, Rarity.UNCOMMON, mage.cards.f.FloodpitsDrowner.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Glimmer Seeker", 14, Rarity.UNCOMMON, mage.cards.g.GlimmerSeeker.class));
        cards.add(new SetCardInfo("Gloomlake Verge", 260, Rarity.RARE, mage.cards.g.GloomlakeVerge.class));
        cards.add(new SetCardInfo("Grasping Longneck", 180, Rarity.COMMON, mage.cards.g.GraspingLongneck.class));
        cards.add(new SetCardInfo("Hushwood Verge", 261, Rarity.RARE, mage.cards.h.HushwoodVerge.class));
        cards.add(new SetCardInfo("Infernal Phantom", 141, Rarity.UNCOMMON, mage.cards.i.InfernalPhantom.class));
        cards.add(new SetCardInfo("Insidious Fungus", 186, Rarity.UNCOMMON, mage.cards.i.InsidiousFungus.class));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Lakeside Shack", 262, Rarity.COMMON, mage.cards.l.LakesideShack.class));
        cards.add(new SetCardInfo("Leyline of Hope", 18, Rarity.RARE, mage.cards.l.LeylineOfHope.class));
        cards.add(new SetCardInfo("Leyline of Mutation", 188, Rarity.RARE, mage.cards.l.LeylineOfMutation.class));
        cards.add(new SetCardInfo("Leyline of Resonance", 143, Rarity.RARE, mage.cards.l.LeylineOfResonance.class));
        cards.add(new SetCardInfo("Leyline of the Void", 106, Rarity.RARE, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Murky Sewer", 263, Rarity.COMMON, mage.cards.m.MurkySewer.class));
        cards.add(new SetCardInfo("Neglected Manor", 264, Rarity.COMMON, mage.cards.n.NeglectedManor.class));
        cards.add(new SetCardInfo("Overlord of the Boilerbilges", 146, Rarity.MYTHIC, mage.cards.o.OverlordOfTheBoilerbilges.class));
        cards.add(new SetCardInfo("Overlord of the Floodpits", 68, Rarity.MYTHIC, mage.cards.o.OverlordOfTheFloodpits.class));
        cards.add(new SetCardInfo("Overlord of the Hauntwoods", 194, Rarity.MYTHIC, mage.cards.o.OverlordOfTheHauntwoods.class));
        cards.add(new SetCardInfo("Overlord of the Mistmoors", 23, Rarity.MYTHIC, mage.cards.o.OverlordOfTheMistmoors.class));
        cards.add(new SetCardInfo("Patched Plaything", 24, Rarity.UNCOMMON, mage.cards.p.PatchedPlaything.class));
        cards.add(new SetCardInfo("Patchwork Beastie", 195, Rarity.UNCOMMON, mage.cards.p.PatchworkBeastie.class));
        cards.add(new SetCardInfo("Peculiar Lighthouse", 265, Rarity.COMMON, mage.cards.p.PeculiarLighthouse.class));
        cards.add(new SetCardInfo("Peer Past the Veil", 226, Rarity.RARE, mage.cards.p.PeerPastTheVeil.class));
        cards.add(new SetCardInfo("Piranha Fly", 70, Rarity.COMMON, mage.cards.p.PiranhaFly.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Popular Egotist", 114, Rarity.UNCOMMON, mage.cards.p.PopularEgotist.class));
        cards.add(new SetCardInfo("Pyroclasm", 149, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Raucous Carnival", 266, Rarity.COMMON, mage.cards.r.RaucousCarnival.class));
        cards.add(new SetCardInfo("Razorkin Needlehead", 153, Rarity.RARE, mage.cards.r.RazorkinNeedlehead.class));
        cards.add(new SetCardInfo("Razortrap Gorge", 267, Rarity.COMMON, mage.cards.r.RazortrapGorge.class));
        cards.add(new SetCardInfo("Savior of the Small", 27, Rarity.UNCOMMON, mage.cards.s.SaviorOfTheSmall.class));
        cards.add(new SetCardInfo("Scrabbling Skullcrab", 71, Rarity.UNCOMMON, mage.cards.s.ScrabblingSkullcrab.class));
        cards.add(new SetCardInfo("Screaming Nemesis", 157, Rarity.MYTHIC, mage.cards.s.ScreamingNemesis.class));
        cards.add(new SetCardInfo("Shardmage's Rescue", 29, Rarity.UNCOMMON, mage.cards.s.ShardmagesRescue.class));
        cards.add(new SetCardInfo("Shepherding Spirits", 31, Rarity.COMMON, mage.cards.s.ShepherdingSpirits.class));
        cards.add(new SetCardInfo("Shrewd Storyteller", 232, Rarity.UNCOMMON, mage.cards.s.ShrewdStoryteller.class));
        cards.add(new SetCardInfo("Silent Hallcreeper", 72, Rarity.RARE, mage.cards.s.SilentHallcreeper.class));
        cards.add(new SetCardInfo("Slavering Branchsnapper", 198, Rarity.COMMON, mage.cards.s.SlaveringBranchsnapper.class));
        cards.add(new SetCardInfo("Spineseeker Centipede", 199, Rarity.COMMON, mage.cards.s.SpineseekerCentipede.class));
        cards.add(new SetCardInfo("Split Up", 32, Rarity.RARE, mage.cards.s.SplitUp.class));
        cards.add(new SetCardInfo("Stalked Researcher", 73, Rarity.COMMON, mage.cards.s.StalkedResearcher.class));
        cards.add(new SetCardInfo("Stay Hidden, Stay Silent", 74, Rarity.UNCOMMON, mage.cards.s.StayHiddenStaySilent.class));
        cards.add(new SetCardInfo("Strangled Cemetery", 268, Rarity.COMMON, mage.cards.s.StrangledCemetery.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Terramorphic Expanse", 269, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("The Jolly Balloon Man", 219, Rarity.RARE, mage.cards.t.TheJollyBalloonMan.class));
        cards.add(new SetCardInfo("The Swarmweaver", 236, Rarity.RARE, mage.cards.t.TheSwarmweaver.class));
        cards.add(new SetCardInfo("The Wandering Rescuer", 41, Rarity.MYTHIC, mage.cards.t.TheWanderingRescuer.class));
        cards.add(new SetCardInfo("Thornspire Verge", 270, Rarity.RARE, mage.cards.t.ThornspireVerge.class));
        cards.add(new SetCardInfo("Threats Around Every Corner", 200, Rarity.UNCOMMON, mage.cards.t.ThreatsAroundEveryCorner.class));
        cards.add(new SetCardInfo("Toby, Beastie Befriender", 35, Rarity.RARE, mage.cards.t.TobyBeastieBefriender.class));
        cards.add(new SetCardInfo("Trapped in the Screen", 36, Rarity.COMMON, mage.cards.t.TrappedInTheScreen.class));
        cards.add(new SetCardInfo("Tunnel Surveyor", 76, Rarity.COMMON, mage.cards.t.TunnelSurveyor.class));
        cards.add(new SetCardInfo("Turn Inside Out", 160, Rarity.COMMON, mage.cards.t.TurnInsideOut.class));
        cards.add(new SetCardInfo("Twitching Doll", 201, Rarity.RARE, mage.cards.t.TwitchingDoll.class));
        cards.add(new SetCardInfo("Tyvar, the Pummeler", 202, Rarity.MYTHIC, mage.cards.t.TyvarThePummeler.class));
        cards.add(new SetCardInfo("Untimely Malfunction", 161, Rarity.UNCOMMON, mage.cards.u.UntimelyMalfunction.class));
        cards.add(new SetCardInfo("Unwanted Remake", 39, Rarity.UNCOMMON, mage.cards.u.UnwantedRemake.class));
        cards.add(new SetCardInfo("Unwilling Vessel", 81, Rarity.UNCOMMON, mage.cards.u.UnwillingVessel.class));
        cards.add(new SetCardInfo("Valgavoth's Faithful", 121, Rarity.UNCOMMON, mage.cards.v.ValgavothsFaithful.class));
        cards.add(new SetCardInfo("Valgavoth's Lair", 271, Rarity.RARE, mage.cards.v.ValgavothsLair.class));
        cards.add(new SetCardInfo("Veteran Survivor", 40, Rarity.UNCOMMON, mage.cards.v.VeteranSurvivor.class));
        cards.add(new SetCardInfo("Vicious Clown", 163, Rarity.COMMON, mage.cards.v.ViciousClown.class));
        cards.add(new SetCardInfo("Victor, Valgavoth's Seneschal", 238, Rarity.RARE, mage.cards.v.VictorValgavothsSeneschal.class));
        cards.add(new SetCardInfo("Winter, Misanthropic Guide", 240, Rarity.RARE, mage.cards.w.WinterMisanthropicGuide.class));
        cards.add(new SetCardInfo("Zimone, All-Questioning", 241, Rarity.RARE, mage.cards.z.ZimoneAllQuestioning.class));

        cards.removeIf(setCardInfo -> setCardInfo.getName().startsWith("Overlord"));
    }
}
