package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author chesse20
 */
public final class WelcomeToRathe extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Arcane Proxy", "Autonomous Assembler", "Blitz Automaton", "Boulderbranch Golem", "Combat Thresher", "Cradle Clearcutter", "Depth Charge Colossus", "Fallaji Dragon Engine", "Goring Warplow", "Hulking Metamorph", "Iron-Craw Crusher", "Phyrexian Fleshgorger", "Rootwire Amalgam", "Rust Goliath", "Skitterbeam Battalion", "Spotter Thopter", "Steel Seraph", "Woodcaller Automaton");

    private static final WelcomeToRathe instance = new WelcomeToRathe();

    public static WelcomeToRathe getInstance() {
        return instance;
    }

    private WelcomeToRathe() {
        super("Welcome to Rathe", "BRO", ExpansionSet.buildDate(2022, 11, 18), SetType.EXPANSION);
        this.blockName = "The Brothers' War";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 15; 
        this.numBoosterUncommon = 0; // change to 2 if rares are ever implemented
        this.numBoosterRare = 0;
        this.numBoosterSpecial = 1;
        this.ratioBoosterMythic = 7;
        this.maxCardNumberInBooster = 1;

        cards.add(new SetCardInfo("Aeronaut Cavalry", 1, Rarity.COMMON, mage.cards.a.AeronautCavalry.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }

    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        // Boosters have one card from BRR, odds are 2/3 for uncommon, 4/15 for rare, 1/15 for mythic
        Rarity rarity;
        int rarityKey = RandomUtil.nextInt(15);
        if (rarityKey == 14) {
            rarity = Rarity.MYTHIC;
        } else if (rarityKey >= 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.UNCOMMON;
        }
        addToBooster(booster, TheBrothersWarRetroArtifacts.getInstance().getCardsByRarity(rarity));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new TheBrothersWarCollator();
//    }
}
