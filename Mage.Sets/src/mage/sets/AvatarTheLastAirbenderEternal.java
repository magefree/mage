package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AvatarTheLastAirbenderEternal extends ExpansionSet {

    private static final AvatarTheLastAirbenderEternal instance = new AvatarTheLastAirbenderEternal();

    public static AvatarTheLastAirbenderEternal getInstance() {
        return instance;
    }

    private AvatarTheLastAirbenderEternal() {
        super("Avatar: The Last Airbender Eternal", "TLE", ExpansionSet.buildDate(2025, 11, 21), SetType.SUPPLEMENTAL);
        this.blockName = "Avatar: The Last Airbender"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aang's Defense", 211, Rarity.COMMON, mage.cards.a.AangsDefense.class));
        cards.add(new SetCardInfo("Aang, Air Nomad", 210, Rarity.RARE, mage.cards.a.AangAirNomad.class));
        cards.add(new SetCardInfo("Aardvark Sloth", 212, Rarity.COMMON, mage.cards.a.AardvarkSloth.class));
        cards.add(new SetCardInfo("Allied Teamwork", 213, Rarity.RARE, mage.cards.a.AlliedTeamwork.class));
        cards.add(new SetCardInfo("Appa, Aang's Companion", 214, Rarity.UNCOMMON, mage.cards.a.AppaAangsCompanion.class));
        cards.add(new SetCardInfo("Bumi, Eclectic Earthbender", 248, Rarity.RARE, mage.cards.b.BumiEclecticEarthbender.class));
        cards.add(new SetCardInfo("Force of Negation", 13, Rarity.MYTHIC, mage.cards.f.ForceOfNegation.class));
        cards.add(new SetCardInfo("Katara, Waterbending Master", 93, Rarity.MYTHIC, mage.cards.k.KataraWaterbendingMaster.class));
        cards.add(new SetCardInfo("The Great Henge", 41, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
    }
}
