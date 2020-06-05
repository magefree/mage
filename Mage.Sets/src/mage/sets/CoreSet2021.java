package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class CoreSet2021 extends ExpansionSet {

    private static final CoreSet2021 instance = new CoreSet2021();

    public static CoreSet2021 getInstance() {
        return instance;
    }

    private final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private CoreSet2021() {
        super("Core Set 2021", "M21", ExpansionSet.buildDate(2020, 7, 3), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = false; // change when basics are available
        this.numBoosterSpecial = 0;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;

        cards.add(new SetCardInfo("Double Vision", 142, Rarity.RARE, mage.cards.d.DoubleVision.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 27, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class));
    }
}
