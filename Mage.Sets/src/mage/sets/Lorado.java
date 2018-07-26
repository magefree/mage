package mage.sets;


import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author EikePace
 */
public final class Lorado extends ExpansionSet {

    private static final Lorado instance = new Lorado();

    public static Lorado getInstance() {
        return instance;
    }

    private Lorado() {
        super("Lorado", "LDO", ExpansionSet.buildDate(2018, 4, 16), SetType.CUSTOM_SET);
        this.blockName = "Lorado";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Angelic Regulator", 1, Rarity.RARE, mage.cards.a.AngelicRegulator.class));
        cards.add(new SetCardInfo("Arrest", 2, Rarity.UNCOMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Back in the Saddle", 3, Rarity.UNCOMMON, mage.cards.b.BackInTheSaddle.class));
        cards.add(new SetCardInfo("Cattle Drive", 4, Rarity.COMMON, mage.cards.c.CattleDrive.class));
        cards.add(new SetCardInfo("Circle the Wagons", 5, Rarity.COMMON, mage.cards.c.CircleTheWagons.class));
        cards.add(new SetCardInfo("Drag Behind", 6, Rarity.RARE, mage.cards.d.DragBehind.class));
        cards.add(new SetCardInfo("Empty Night", 7, Rarity.RARE, mage.cards.e.EmptyNight.class));
        cards.add(new SetCardInfo("Farajo Peacemaker", 8, Rarity.MYTHIC, mage.cards.f.FarajoPeacemaker.class));
        cards.add(new SetCardInfo("Farajo's Blessing", 9, Rarity.UNCOMMON, mage.cards.f.FarajosBlessing.class));
        cards.add(new SetCardInfo("Farajo's Shield", 10, Rarity.COMMON, mage.cards.f.FarajosShield.class));
        cards.add(new SetCardInfo("Feather Swine", 11, Rarity.COMMON, mage.cards.f.FeatherSwine.class));
        cards.add(new SetCardInfo("Firstfeather Elders", 12, Rarity.COMMON, mage.cards.f.FirstfeatherElders.class));
        cards.add(new SetCardInfo("Fort Redemption Ranger", 13, Rarity.MYTHIC, mage.cards.f.FortRedemptionRanger.class));
        cards.add(new SetCardInfo("Goat Herder", 14, Rarity.COMMON, mage.cards.g.GoatHerder.class));
        cards.add(new SetCardInfo("Grit", 15, Rarity.UNCOMMON, mage.cards.g.Grit.class));
        cards.add(new SetCardInfo("Guardian of the Dead", 15, Rarity.UNCOMMON, mage.cards.g.GuardianOfTheDead.class));
        cards.add(new SetCardInfo("Guthram Deputy", 17, Rarity.UNCOMMON, mage.cards.g.GuthramDeputy.class));
        cards.add(new SetCardInfo("High Noon", 18, Rarity.RARE, mage.cards.h.HighNoon.class));
        cards.add(new SetCardInfo("Hogtie", 19, Rarity.COMMON, mage.cards.h.Hogtie.class));
        cards.add(new SetCardInfo("Into the Sunset", 20, Rarity.COMMON, mage.cards.i.IntoTheSunset.class));
        cards.add(new SetCardInfo("Kill Shot", 21, Rarity.COMMON, mage.cards.k.KillShot.class));
        cards.add(new SetCardInfo("Manifestation of Destiny", 21, Rarity.RARE, mage.cards.m.ManifestationOfDestiny.class));
    }
}