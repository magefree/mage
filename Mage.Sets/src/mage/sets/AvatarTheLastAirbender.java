package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AvatarTheLastAirbender extends ExpansionSet {

    private static final AvatarTheLastAirbender instance = new AvatarTheLastAirbender();

    public static AvatarTheLastAirbender getInstance() {
        return instance;
    }

    private AvatarTheLastAirbender() {
        super("Avatar: The Last Airbender", "TLA", ExpansionSet.buildDate(2025, 11, 21), SetType.EXPANSION);
        this.blockName = "Avatar: The Last Airbender"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = false; // temporary
    }
}
