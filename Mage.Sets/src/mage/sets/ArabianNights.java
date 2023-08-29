package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author North
 */
public final class ArabianNights extends ExpansionSet {

    private static final ArabianNights instance = new ArabianNights();

    public static ArabianNights getInstance() {
        return instance;
    }

    private ArabianNights() {
        super("Arabian Nights", "ARN", ExpansionSet.buildDate(1993, 11, 1), SetType.EXPANSION);
        this.hasBasicLands = true;
        this.hasBoosters = true; // note: paper boosters had only 8 cards
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Abu Ja'far", 1, Rarity.UNCOMMON, mage.cards.a.AbuJafar.class));
        cards.add(new SetCardInfo("Aladdin", 34, Rarity.RARE, mage.cards.a.Aladdin.class));
        cards.add(new SetCardInfo("Aladdin's Lamp", 56, Rarity.RARE, mage.cards.a.AladdinsLamp.class));
        cards.add(new SetCardInfo("Aladdin's Ring", 57, Rarity.RARE, mage.cards.a.AladdinsRing.class));
        cards.add(new SetCardInfo("Ali Baba", 35, Rarity.UNCOMMON, mage.cards.a.AliBaba.class));
        cards.add(new SetCardInfo("Ali from Cairo", 36, Rarity.RARE, mage.cards.a.AliFromCairo.class));
        cards.add(new SetCardInfo("Army of Allah", "2+", Rarity.COMMON, mage.cards.a.ArmyOfAllah.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Army of Allah", 2, Rarity.COMMON, mage.cards.a.ArmyOfAllah.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bazaar of Baghdad", 70, Rarity.UNCOMMON, mage.cards.b.BazaarOfBaghdad.class));
        cards.add(new SetCardInfo("Bird Maiden", "37+", Rarity.COMMON, mage.cards.b.BirdMaiden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bird Maiden", 37, Rarity.COMMON, mage.cards.b.BirdMaiden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bottle of Suleiman", 58, Rarity.RARE, mage.cards.b.BottleOfSuleiman.class));
        cards.add(new SetCardInfo("Brass Man", 59, Rarity.UNCOMMON, mage.cards.b.BrassMan.class));
        cards.add(new SetCardInfo("Camel", 3, Rarity.COMMON, mage.cards.c.Camel.class));
        cards.add(new SetCardInfo("City in a Bottle", 60, Rarity.RARE, mage.cards.c.CityInABottle.class));
        cards.add(new SetCardInfo("City of Brass", 71, Rarity.UNCOMMON, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Cuombajj Witches", 23, Rarity.COMMON, mage.cards.c.CuombajjWitches.class));
        cards.add(new SetCardInfo("Cyclone", 45, Rarity.UNCOMMON, mage.cards.c.Cyclone.class));
        cards.add(new SetCardInfo("Dancing Scimitar", 61, Rarity.RARE, mage.cards.d.DancingScimitar.class));
        cards.add(new SetCardInfo("Dandan", 12, Rarity.COMMON, mage.cards.d.Dandan.class));
        cards.add(new SetCardInfo("Desert Nomads", 38, Rarity.COMMON, mage.cards.d.DesertNomads.class));
        cards.add(new SetCardInfo("Desert Twister", 46, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Desert", 72, Rarity.COMMON, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Diamond Valley", 73, Rarity.RARE, mage.cards.d.DiamondValley.class));
        cards.add(new SetCardInfo("Drop of Honey", 47, Rarity.RARE, mage.cards.d.DropOfHoney.class));
        cards.add(new SetCardInfo("Ebony Horse", 62, Rarity.RARE, mage.cards.e.EbonyHorse.class));
        cards.add(new SetCardInfo("El-Hajjaj", 24, Rarity.RARE, mage.cards.e.ElHajjaj.class));
        cards.add(new SetCardInfo("Elephant Graveyard", 74, Rarity.RARE, mage.cards.e.ElephantGraveyard.class));
        cards.add(new SetCardInfo("Erg Raiders", "25+", Rarity.COMMON, mage.cards.e.ErgRaiders.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erg Raiders", 25, Rarity.COMMON, mage.cards.e.ErgRaiders.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erhnam Djinn", 48, Rarity.RARE, mage.cards.e.ErhnamDjinn.class));
        cards.add(new SetCardInfo("Eye for an Eye", 4, Rarity.UNCOMMON, mage.cards.e.EyeForAnEye.class));
        cards.add(new SetCardInfo("Fishliver Oil", "13+", Rarity.COMMON, mage.cards.f.FishliverOil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fishliver Oil", 13, Rarity.COMMON, mage.cards.f.FishliverOil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flying Carpet", 63, Rarity.UNCOMMON, mage.cards.f.FlyingCarpet.class));
        cards.add(new SetCardInfo("Flying Men", 14, Rarity.COMMON, mage.cards.f.FlyingMen.class));
        cards.add(new SetCardInfo("Ghazban Ogre", 49, Rarity.COMMON, mage.cards.g.GhazbanOgre.class));
        cards.add(new SetCardInfo("Giant Tortoise", "15+", Rarity.COMMON, mage.cards.g.GiantTortoise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Tortoise", 15, Rarity.COMMON, mage.cards.g.GiantTortoise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guardian Beast", 26, Rarity.RARE, mage.cards.g.GuardianBeast.class));
        cards.add(new SetCardInfo("Hasran Ogress", "27+", Rarity.COMMON, mage.cards.h.HasranOgress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hasran Ogress", 27, Rarity.COMMON, mage.cards.h.HasranOgress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hurr Jackal", 39, Rarity.COMMON, mage.cards.h.HurrJackal.class));
        cards.add(new SetCardInfo("Ifh-Biff Efreet", 50, Rarity.RARE, mage.cards.i.IfhBiffEfreet.class));
        cards.add(new SetCardInfo("Island Fish Jasconius", 16, Rarity.RARE, mage.cards.i.IslandFishJasconius.class));
        cards.add(new SetCardInfo("Island of Wak-Wak", 75, Rarity.RARE, mage.cards.i.IslandOfWakWak.class));
        cards.add(new SetCardInfo("Jandor's Ring", 64, Rarity.RARE, mage.cards.j.JandorsRing.class));
        cards.add(new SetCardInfo("Jandor's Saddlebags", 65, Rarity.RARE, mage.cards.j.JandorsSaddlebags.class));
        cards.add(new SetCardInfo("Jihad", 5, Rarity.RARE, mage.cards.j.Jihad.class));
        cards.add(new SetCardInfo("Junun Efreet", 28, Rarity.RARE, mage.cards.j.JununEfreet.class));
        cards.add(new SetCardInfo("Juzam Djinn", 29, Rarity.RARE, mage.cards.j.JuzamDjinn.class));
        cards.add(new SetCardInfo("Khabal Ghoul", 30, Rarity.UNCOMMON, mage.cards.k.KhabalGhoul.class));
        cards.add(new SetCardInfo("King Suleiman", 6, Rarity.RARE, mage.cards.k.KingSuleiman.class));
        cards.add(new SetCardInfo("Kird Ape", 40, Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Library of Alexandria", 76, Rarity.UNCOMMON, mage.cards.l.LibraryOfAlexandria.class));
        cards.add(new SetCardInfo("Magnetic Mountain", 41, Rarity.UNCOMMON, mage.cards.m.MagneticMountain.class));
        cards.add(new SetCardInfo("Merchant Ship", 17, Rarity.UNCOMMON, mage.cards.m.MerchantShip.class));
        cards.add(new SetCardInfo("Metamorphosis", 51, Rarity.COMMON, mage.cards.m.Metamorphosis.class));
        cards.add(new SetCardInfo("Mijae Djinn", 42, Rarity.RARE, mage.cards.m.MijaeDjinn.class));
        cards.add(new SetCardInfo("Moorish Cavalry", "7+", Rarity.COMMON, mage.cards.m.MoorishCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moorish Cavalry", 7, Rarity.COMMON, mage.cards.m.MoorishCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 77, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Nafs Asp", "52+", Rarity.COMMON, mage.cards.n.NafsAsp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nafs Asp", 52, Rarity.COMMON, mage.cards.n.NafsAsp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oasis", 78, Rarity.UNCOMMON, mage.cards.o.Oasis.class));
        cards.add(new SetCardInfo("Old Man of the Sea", 18, Rarity.RARE, mage.cards.o.OldManOfTheSea.class));
        cards.add(new SetCardInfo("Oubliette", "31+", Rarity.COMMON, mage.cards.o.Oubliette.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oubliette", 31, Rarity.COMMON, mage.cards.o.Oubliette.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piety", "8+", Rarity.COMMON, mage.cards.p.Piety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Piety", 8, Rarity.COMMON, mage.cards.p.Piety.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyramids", 67, Rarity.RARE, mage.cards.p.Pyramids.class));
        cards.add(new SetCardInfo("Repentant Blacksmith", 9, Rarity.RARE, mage.cards.r.RepentantBlacksmith.class));
        cards.add(new SetCardInfo("Ring of Ma'ruf", 68, Rarity.RARE, mage.cards.r.RingOfMaruf.class));
        cards.add(new SetCardInfo("Rukh Egg", "43+", Rarity.COMMON, mage.cards.r.RukhEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rukh Egg", 43, Rarity.COMMON, mage.cards.r.RukhEgg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandals of Abdallah", 69, Rarity.UNCOMMON, mage.cards.s.SandalsOfAbdallah.class));
        cards.add(new SetCardInfo("Sandstorm", 53, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Serendib Djinn", 19, Rarity.RARE, mage.cards.s.SerendibDjinn.class));
        cards.add(new SetCardInfo("Serendib Efreet", 20, Rarity.RARE, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Sindbad", 21, Rarity.UNCOMMON, mage.cards.s.Sindbad.class));
        cards.add(new SetCardInfo("Singing Tree", 54, Rarity.RARE, mage.cards.s.SingingTree.class));
        cards.add(new SetCardInfo("Sorceress Queen", 32, Rarity.UNCOMMON, mage.cards.s.SorceressQueen.class));
        cards.add(new SetCardInfo("Stone-Throwing Devils", "33+", Rarity.COMMON, mage.cards.s.StoneThrowingDevils.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stone-Throwing Devils", 33, Rarity.COMMON, mage.cards.s.StoneThrowingDevils.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unstable Mutation", 22, Rarity.COMMON, mage.cards.u.UnstableMutation.class));
        cards.add(new SetCardInfo("War Elephant", "11+", Rarity.COMMON, mage.cards.w.WarElephant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("War Elephant", 11, Rarity.COMMON, mage.cards.w.WarElephant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wyluli Wolf", "55+", Rarity.COMMON, mage.cards.w.WyluliWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wyluli Wolf", 55, Rarity.COMMON, mage.cards.w.WyluliWolf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ydwen Efreet", 44, Rarity.RARE, mage.cards.y.YdwenEfreet.class));
    }
}
