package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunctionCommander extends ExpansionSet {

    private static final OutlawsOfThunderJunctionCommander instance = new OutlawsOfThunderJunctionCommander();

    public static OutlawsOfThunderJunctionCommander getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunctionCommander() {
        super("Outlaws of Thunder Junction Commander", "OTC", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angel of Indemnity", 45, Rarity.RARE, mage.cards.a.AngelOfIndemnity.class));
        cards.add(new SetCardInfo("Angelic Sell-Sword", 10, Rarity.RARE, mage.cards.a.AngelicSellSword.class));
        cards.add(new SetCardInfo("Back in Town", 18, Rarity.RARE, mage.cards.b.BackInTown.class));
        cards.add(new SetCardInfo("Charred Graverobber", 19, Rarity.RARE, mage.cards.c.CharredGraverobber.class));
        cards.add(new SetCardInfo("Elemental Eruption", 27, Rarity.RARE, mage.cards.e.ElementalEruption.class));
        cards.add(new SetCardInfo("Embrace the Unknown", 64, Rarity.RARE, mage.cards.e.EmbraceTheUnknown.class));
        cards.add(new SetCardInfo("Eris, Roar of the Storm", 5, Rarity.MYTHIC, mage.cards.e.ErisRoarOfTheStorm.class));
        cards.add(new SetCardInfo("Kirri, Talented Sprout", 43, Rarity.MYTHIC, mage.cards.k.KirriTalentedSprout.class));
        cards.add(new SetCardInfo("Leyline Dowser", 39, Rarity.RARE, mage.cards.l.LeylineDowser.class));
        cards.add(new SetCardInfo("Rumbleweed", 68, Rarity.RARE, mage.cards.r.Rumbleweed.class));
        cards.add(new SetCardInfo("Stella Lee, Wild Card", 3, Rarity.MYTHIC, mage.cards.s.StellaLeeWildCard.class));
        cards.add(new SetCardInfo("Vengeful Regrowth", 71, Rarity.RARE, mage.cards.v.VengefulRegrowth.class));
        cards.add(new SetCardInfo("Yuma, Proud Protector", 4, Rarity.MYTHIC, mage.cards.y.YumaProudProtector.class));
    }
}
