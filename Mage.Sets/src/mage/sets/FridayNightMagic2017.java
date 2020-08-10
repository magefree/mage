package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f17
 */
public class FridayNightMagic2017 extends ExpansionSet {

    private static final FridayNightMagic2017 instance = new FridayNightMagic2017();

    public static FridayNightMagic2017 getInstance() {
        return instance;
    }

    private FridayNightMagic2017() {
        super("Friday Night Magic 2017", "F17", ExpansionSet.buildDate(2017, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aether Hub", 6, Rarity.RARE, mage.cards.a.AetherHub.class));
        cards.add(new SetCardInfo("Fatal Push", 8, Rarity.RARE, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fortune's Favor", 2, Rarity.RARE, mage.cards.f.FortunesFavor.class));
        cards.add(new SetCardInfo("Incendiary Flow", 3, Rarity.RARE, mage.cards.i.IncendiaryFlow.class));
        cards.add(new SetCardInfo("Noose Constrictor", 1, Rarity.RARE, mage.cards.n.NooseConstrictor.class));
        cards.add(new SetCardInfo("Renegade Rallier", 9, Rarity.RARE, mage.cards.r.RenegadeRallier.class));
        cards.add(new SetCardInfo("Reverse Engineer", 7, Rarity.RARE, mage.cards.r.ReverseEngineer.class));
        cards.add(new SetCardInfo("Servo Exhibition", 4, Rarity.RARE, mage.cards.s.ServoExhibition.class));
        cards.add(new SetCardInfo("Unlicensed Disintegration", 5, Rarity.RARE, mage.cards.u.UnlicensedDisintegration.class));
     }
}
