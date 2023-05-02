package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachineTheAftermath extends ExpansionSet {

    private static final MarchOfTheMachineTheAftermath instance = new MarchOfTheMachineTheAftermath();

    public static MarchOfTheMachineTheAftermath getInstance() {
        return instance;
    }

    private MarchOfTheMachineTheAftermath() {
        super("March of the Machine: The Aftermath", "MAT", ExpansionSet.buildDate(2023, 5, 12), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "March of the Machine";
        this.hasBasicLands = false;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Jolrael, Voice of Zhalfir", 33, Rarity.RARE, mage.cards.j.JolraelVoiceOfZhalfir.class));
        cards.add(new SetCardInfo("The Kenriths' Royal Funeral", 34, Rarity.RARE, mage.cards.t.TheKenrithsRoyalFuneral.class));
    }
}
