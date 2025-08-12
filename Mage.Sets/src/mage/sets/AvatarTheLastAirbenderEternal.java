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

        cards.add(new SetCardInfo("Katara, Waterbending Master", 93, Rarity.MYTHIC, mage.cards.k.KataraWaterbendingMaster.class));
    }
}
