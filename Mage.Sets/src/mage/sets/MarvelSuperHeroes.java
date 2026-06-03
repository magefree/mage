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
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Attuma, Atlantean Warlord", 47, Rarity.UNCOMMON, mage.cards.a.AttumaAtlanteanWarlord.class));
        cards.add(new SetCardInfo("Baron Helmut Zemo", 87, Rarity.RARE, mage.cards.b.BaronHelmutZemo.class));
        cards.add(new SetCardInfo("Bruce Banner", 390, Rarity.MYTHIC, mage.cards.b.BruceBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bruce Banner", 49, Rarity.MYTHIC, mage.cards.b.BruceBanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 387, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 9, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doctor Doom", 394, Rarity.MYTHIC, mage.cards.d.DoctorDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doctor Doom", 95, Rarity.MYTHIC, mage.cards.d.DoctorDoom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Reigns Supreme", 96, Rarity.RARE, mage.cards.d.DoomReignsSupreme.class));
        cards.add(new SetCardInfo("Iron Man, Master of Machines", 216, Rarity.UNCOMMON, mage.cards.i.IronManMasterOfMachines.class));
        cards.add(new SetCardInfo("Moon Girl and Devil Dinosaur", 223, Rarity.RARE, mage.cards.m.MoonGirlAndDevilDinosaur.class));
        cards.add(new SetCardInfo("Namor the Sub-Mariner", 391, Rarity.MYTHIC, mage.cards.n.NamorTheSubMariner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Namor the Sub-Mariner", 69, Rarity.MYTHIC, mage.cards.n.NamorTheSubMariner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quicksilver, Brash Blur", 148, Rarity.RARE, mage.cards.q.QuicksilverBrashBlur.class));
        cards.add(new SetCardInfo("Ronin, Shadow Stalker", 112, Rarity.UNCOMMON, mage.cards.r.RoninShadowStalker.class));
        cards.add(new SetCardInfo("Super-Skrull", 115, Rarity.RARE, mage.cards.s.SuperSkrull.class));
        cards.add(new SetCardInfo("The Coming of Galactus", 212, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Coming of Galactus", 307, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Mind Stone", 21, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Mind Stone", 385, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Mind Stone", 386, Rarity.MYTHIC, mage.cards.t.TheMindStone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Sentry, Golden Guardian", 35, Rarity.RARE, mage.cards.t.TheSentryGoldenGuardian.class));
        cards.add(new SetCardInfo("The Vision", 255, Rarity.RARE, mage.cards.t.TheVision.class));
        cards.add(new SetCardInfo("Thor Odinson", 234, Rarity.UNCOMMON, mage.cards.t.ThorOdinson.class));
        cards.add(new SetCardInfo("Thunderbolts Conspiracy", 117, Rarity.RARE, mage.cards.t.ThunderboltsConspiracy.class));
        cards.add(new SetCardInfo("Tigra, Feline Fury", 191, Rarity.UNCOMMON, mage.cards.t.TigraFelineFury.class));
        cards.add(new SetCardInfo("Wolverine, Fierce Fighter", 240, Rarity.RARE, mage.cards.w.WolverineFierceFighter.class));
        cards.add(new SetCardInfo("World War Hulk", 197, Rarity.RARE, mage.cards.w.WorldWarHulk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("World War Hulk", 304, Rarity.RARE, mage.cards.w.WorldWarHulk.class, NON_FULL_USE_VARIOUS));
    }
}
