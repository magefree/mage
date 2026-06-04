package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroes extends ExpansionSet {

    private static final MarvelSuperHeroes instance = new MarvelSuperHeroes();

    public static MarvelSuperHeroes getInstance() {
        return instance;
    }

    private MarvelSuperHeroes() {
        super("Marvel Super Heroes", "MSH", ExpansionSet.buildDate(2026, 6, 26), SetType.EXPANSION);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abomination, Terrifying Titan", 198, Rarity.UNCOMMON, mage.cards.a.AbominationTerrifyingTitan.class));
        cards.add(new SetCardInfo("Agent Phil Coulson", 4, Rarity.RARE, mage.cards.a.AgentPhilCoulson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Agent Phil Coulson", 402, Rarity.RARE, mage.cards.a.AgentPhilCoulson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arc Reactor", 243, Rarity.RARE, mage.cards.a.ArcReactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arc Reactor", 310, Rarity.RARE, mage.cards.a.ArcReactor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Armor Wars", 203, Rarity.RARE, mage.cards.a.ArmorWars.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Armor Wars", 305, Rarity.RARE, mage.cards.a.ArmorWars.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Attuma, Atlantean Warlord", 47, Rarity.UNCOMMON, mage.cards.a.AttumaAtlanteanWarlord.class));
        cards.add(new SetCardInfo("Avengers Assemble!", 297, Rarity.MYTHIC, mage.cards.a.AvengersAssemble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avengers Assemble!", 341, Rarity.MYTHIC, mage.cards.a.AvengersAssemble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avengers Assemble!", 6, Rarity.MYTHIC, mage.cards.a.AvengersAssemble.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baron Helmut Zemo", 87, Rarity.RARE, mage.cards.b.BaronHelmutZemo.class));
        cards.add(new SetCardInfo("Baxter Building", 261, Rarity.UNCOMMON, mage.cards.b.BaxterBuilding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baxter Building", 453, Rarity.UNCOMMON, mage.cards.b.BaxterBuilding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Black Widow, Double Agent", 208, Rarity.UNCOMMON, mage.cards.b.BlackWidowDoubleAgent.class));
        cards.add(new SetCardInfo("Black Panther, Vanguard", 207, Rarity.UNCOMMON, mage.cards.b.BlackPantherVanguard.class));
        cards.add(new SetCardInfo("Bruce Banner", 390, Rarity.MYTHIC, mage.cards.b.BruceBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bruce Banner", 49, Rarity.MYTHIC, mage.cards.b.BruceBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America's Shield", 244, Rarity.RARE, mage.cards.c.CaptainAmericasShield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America's Shield", 317, Rarity.RARE, mage.cards.c.CaptainAmericasShield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 387, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 9, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Marvel, Earth's Protector", 11, Rarity.MYTHIC, mage.cards.c.CaptainMarvelEarthsProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Marvel, Earth's Protector", 342, Rarity.MYTHIC, mage.cards.c.CaptainMarvelEarthsProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Marvel, Earth's Protector", 354, Rarity.MYTHIC, mage.cards.c.CaptainMarvelEarthsProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Colleen Wing, Street Samurai", 13, Rarity.UNCOMMON, mage.cards.c.ColleenWingStreetSamurai.class));
        cards.add(new SetCardInfo("Construct a Cosmic Cube", 406, Rarity.RARE, mage.cards.c.ConstructACosmicCube.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Construct a Cosmic Cube", 90, Rarity.RARE, mage.cards.c.ConstructACosmicCube.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crossbones, Malicious Mercenary", 91, Rarity.UNCOMMON, mage.cards.c.CrossbonesMaliciousMercenary.class));
        cards.add(new SetCardInfo("Dark Deed", 93, Rarity.UNCOMMON, mage.cards.d.DarkDeed.class));
        cards.add(new SetCardInfo("Doctor Doom", 394, Rarity.MYTHIC, mage.cards.d.DoctorDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doctor Doom", 95, Rarity.MYTHIC, mage.cards.d.DoctorDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Reigns Supreme", 96, Rarity.RARE, mage.cards.d.DoomReignsSupreme.class));
        cards.add(new SetCardInfo("Elektra, Daughter of the Hand", 326, Rarity.RARE, mage.cards.e.ElektraDaughterOfTheHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elektra, Daughter of the Hand", 366, Rarity.RARE, mage.cards.e.ElektraDaughterOfTheHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elektra, Daughter of the Hand", 395, Rarity.RARE, mage.cards.e.ElektraDaughterOfTheHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elektra, Daughter of the Hand", 97, Rarity.RARE, mage.cards.e.ElektraDaughterOfTheHand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 285, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 295, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 296, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Human Torch, Johnny Storm", 136, Rarity.UNCOMMON, mage.cards.h.HumanTorchJohnnyStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Human Torch, Johnny Storm", 321, Rarity.UNCOMMON, mage.cards.h.HumanTorchJohnnyStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invisible Woman, Sue Storm", 17, Rarity.UNCOMMON, mage.cards.i.InvisibleWomanSueStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invisible Woman, Sue Storm", 323, Rarity.UNCOMMON, mage.cards.i.InvisibleWomanSueStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Man, Master of Machines", 216, Rarity.UNCOMMON, mage.cards.i.IronManMasterOfMachines.class));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 289, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 290, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kang, Temporal Tyrant", 217, Rarity.UNCOMMON, mage.cards.k.KangTemporalTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kang, Temporal Tyrant", 450, Rarity.UNCOMMON, mage.cards.k.KangTemporalTyrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Madame Hydra", 221, Rarity.UNCOMMON, mage.cards.m.MadameHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Madame Hydra", 318, Rarity.UNCOMMON, mage.cards.m.MadameHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Madame Hydra", 451, Rarity.UNCOMMON, mage.cards.m.MadameHydra.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mister Fantastic, Reed Richards", 320, Rarity.UNCOMMON, mage.cards.m.MisterFantasticReedRichards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mister Fantastic, Reed Richards", 445, Rarity.UNCOMMON, mage.cards.m.MisterFantasticReedRichards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mister Fantastic, Reed Richards", 66, Rarity.UNCOMMON, mage.cards.m.MisterFantasticReedRichards.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("M.O.D.O.K.", 106, Rarity.MYTHIC, mage.cards.m.MODOK.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("M.O.D.O.K.", 408, Rarity.MYTHIC, mage.cards.m.MODOK.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moon Girl and Devil Dinosaur", 223, Rarity.RARE, mage.cards.m.MoonGirlAndDevilDinosaur.class));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 293, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 294, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Namor the Sub-Mariner", 391, Rarity.MYTHIC, mage.cards.n.NamorTheSubMariner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Namor the Sub-Mariner", 69, Rarity.MYTHIC, mage.cards.n.NamorTheSubMariner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver, Brash Blur", 148, Rarity.RARE, mage.cards.q.QuicksilverBrashBlur.class));
        cards.add(new SetCardInfo("Ronin, Shadow Stalker", 112, Rarity.UNCOMMON, mage.cards.r.RoninShadowStalker.class));
        cards.add(new SetCardInfo("S.H.I.E.L.D. Flying Car", 404, Rarity.RARE, mage.cards.s.SHIELDFlyingCar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("S.H.I.E.L.D. Flying Car", 74, Rarity.RARE, mage.cards.s.SHIELDFlyingCar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Super-Skrull", 115, Rarity.RARE, mage.cards.s.SuperSkrull.class));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 292, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Coming of Galactus", 212, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Coming of Galactus", 307, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Masters of Evil", 105, Rarity.UNCOMMON, mage.cards.t.TheMastersOfEvil.class));
        cards.add(new SetCardInfo("The Mind Stone", 21, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Mind Stone", 385, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Mind Stone", 386, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Scarlet Witch", 151, Rarity.RARE, mage.cards.t.TheScarletWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Scarlet Witch", 368, Rarity.RARE, mage.cards.t.TheScarletWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Scarlet Witch", 431, Rarity.RARE, mage.cards.t.TheScarletWitch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Sentry, Golden Guardian", 35, Rarity.RARE, mage.cards.t.TheSentryGoldenGuardian.class));
        cards.add(new SetCardInfo("The Ten Rings", 251, Rarity.MYTHIC, mage.cards.t.TheTenRings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Ten Rings", 313, Rarity.MYTHIC, mage.cards.t.TheTenRings.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Thing, Ben Grimm", 190, Rarity.UNCOMMON, mage.cards.t.TheThingBenGrimm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Thing, Ben Grimm", 322, Rarity.UNCOMMON, mage.cards.t.TheThingBenGrimm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Unbeatable Squirrel Girl", 193, Rarity.RARE, mage.cards.t.TheUnbeatableSquirrelGirl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Unbeatable Squirrel Girl", 371, Rarity.RARE, mage.cards.t.TheUnbeatableSquirrelGirl.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Vision", 255, Rarity.RARE, mage.cards.t.TheVision.class));
        cards.add(new SetCardInfo("Thor Odinson", 234, Rarity.UNCOMMON, mage.cards.t.ThorOdinson.class));
        cards.add(new SetCardInfo("Thunderbolts Conspiracy", 117, Rarity.RARE, mage.cards.t.ThunderboltsConspiracy.class));
        cards.add(new SetCardInfo("Tigra, Feline Fury", 191, Rarity.UNCOMMON, mage.cards.t.TigraFelineFury.class));
        cards.add(new SetCardInfo("Wolverine, Fierce Fighter", 240, Rarity.RARE, mage.cards.w.WolverineFierceFighter.class));
        cards.add(new SetCardInfo("World War Hulk", 197, Rarity.RARE, mage.cards.w.WorldWarHulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("World War Hulk", 304, Rarity.RARE, mage.cards.w.WorldWarHulk.class, NON_FULL_USE_VARIOUS));
    }
}
