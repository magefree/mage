package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class NeonDynastyCommander extends ExpansionSet {

    private static final NeonDynastyCommander instance = new NeonDynastyCommander();

    public static NeonDynastyCommander getInstance() {
        return instance;
    }

    private NeonDynastyCommander() {
        super("Neon Dynasty Commander", "NEC", ExpansionSet.buildDate(2022, 2, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Access Denied", 11, Rarity.RARE, mage.cards.a.AccessDenied.class));
        cards.add(new SetCardInfo("Aerial Surveyor", 5, Rarity.RARE, mage.cards.a.AerialSurveyor.class));
        cards.add(new SetCardInfo("Chishiro, the Shattered Blade", 1, Rarity.MYTHIC, mage.cards.c.ChishiroTheShatteredBlade.class));
        cards.add(new SetCardInfo("Kotori, Pilot Prodigy", 2, Rarity.MYTHIC, mage.cards.k.KotoriPilotProdigy.class));
        cards.add(new SetCardInfo("Myojin of Blooming Dawn", 31, Rarity.RARE, mage.cards.m.MyojinOfBloomingDawn.class));
        cards.add(new SetCardInfo("Myojin of Roaring Blades", 36, Rarity.RARE, mage.cards.m.MyojinOfRoaringBlades.class));
        cards.add(new SetCardInfo("Myojin of Towering Might", 38, Rarity.RARE, mage.cards.m.MyojinOfToweringMight.class));
        cards.add(new SetCardInfo("Organic Extinction", 8, Rarity.RARE, mage.cards.o.OrganicExtinction.class));
        cards.add(new SetCardInfo("Release to Memory", 9, Rarity.RARE, mage.cards.r.ReleaseToMemory.class));
        cards.add(new SetCardInfo("Shorikai, Genesis Engine", 4, Rarity.MYTHIC, mage.cards.s.ShorikaiGenesisEngine.class));
        cards.add(new SetCardInfo("Universal Surveillance", 17, Rarity.RARE, mage.cards.u.UniversalSurveillance.class));
        cards.add(new SetCardInfo("Yoshimaru, Ever Faithful", 32, Rarity.MYTHIC, mage.cards.y.YoshimaruEverFaithful.class));
    }
}
