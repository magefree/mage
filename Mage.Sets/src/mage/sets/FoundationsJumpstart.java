package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FoundationsJumpstart extends ExpansionSet {

    private static final FoundationsJumpstart instance = new FoundationsJumpstart();

    public static FoundationsJumpstart getInstance() {
        return instance;
    }

    private FoundationsJumpstart() {
        super("Foundations Jumpstart", "J25", ExpansionSet.buildDate(2024, 11, 15), SetType.EXPANSION);
        this.blockName = "Foundations"; // for sorting in GUI
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Brimaz, King of Oreskos", 58, Rarity.MYTHIC, mage.cards.b.BrimazKingOfOreskos.class));
        cards.add(new SetCardInfo("Dawnwing Marshal", 1, Rarity.UNCOMMON, mage.cards.d.DawnwingMarshal.class));
        cards.add(new SetCardInfo("Dionus, Elvish Archdruid", 52, Rarity.UNCOMMON, mage.cards.d.DionusElvishArchdruid.class));
        cards.add(new SetCardInfo("Faithful Pikemaster", 3, Rarity.COMMON, mage.cards.f.FaithfulPikemaster.class));
        cards.add(new SetCardInfo("Shroofus Sproutsire", 54, Rarity.RARE, mage.cards.s.ShroofusSproutsire.class));
        cards.add(new SetCardInfo("Vilis, Broker of Blood", 70, Rarity.RARE, mage.cards.v.VilisBrokerOfBlood.class));
    }
}
