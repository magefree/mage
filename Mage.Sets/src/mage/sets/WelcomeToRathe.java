package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;


/**
 * @author chesse20
 */
public final class WelcomeToRathe extends ExpansionSet {

    //private static final List<String> unfinished = Arrays.asList();

    private static final WelcomeToRathe instance = new WelcomeToRathe();

    public static WelcomeToRathe getInstance() {
        return instance;
    }

    private WelcomeToRathe() {
        super("Welcome to Rathe", "WTR", ExpansionSet.buildDate(2019, 10, 11), SetType.EXPANSION);
        this.blockName = "Welcome to rathe";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 15; 
        this.numBoosterUncommon = 0; // change to 2 if rares are ever implemented
        this.numBoosterRare = 0;
        this.numBoosterSpecial = 1;
        this.ratioBoosterMythic = 7;
        this.maxCardNumberInBooster = 1;

        cards.add(new SetCardInfo("RagingOnslaught", 1, Rarity.COMMON, mage.cards.r.RagingOnslaughtBlue.class));

        //cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new WelcomeToRatheCollator();
//    }
}
