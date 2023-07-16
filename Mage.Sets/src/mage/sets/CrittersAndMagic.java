package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChesseTheWasp
 */
public final class CrittersAndMagic extends ExpansionSet {

    private static final CrittersAndMagic instance = new CrittersAndMagic();

    public static CrittersAndMagic getInstance() {
        return instance;
    }

    private CrittersAndMagic() {
        super("Critters and Magic (Custom Set)", "CAMCS", ExpansionSet.buildDate(2022, 4, 29), SetType.EXPANSION);
        this.blockName = "Critters and Magic";
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0; //no mythics
        this.maxCardNumberInBooster = 109; //small set

        cards.add(new SetCardInfo("Abrade", 42, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Alchemists Gift", 29, Rarity.COMMON, mage.cards.a.AlchemistsGift.class));
        cards.add(new SetCardInfo("Big Buggy", 30, Rarity.COMMON, mage.cards.b.BigBuggy.class));
        cards.add(new SetCardInfo("Big Doggy", 43, Rarity.COMMON, mage.cards.b.BigDoggy.class));
        cards.add(new SetCardInfo("Big Snail", 16, Rarity.COMMON, mage.cards.b.BigSnail.class));
        cards.add(new SetCardInfo("Big Ghost", 3, Rarity.COMMON, mage.cards.b.BigGhost.class));
        cards.add(new SetCardInfo("Big Kitty", 55, Rarity.COMMON, mage.cards.b.BigKitty.class));
        cards.add(new SetCardInfo("Big Lizard", 31, Rarity.COMMON, mage.cards.b.BigLizard.class));
        cards.add(new SetCardInfo("Big Otter", 44, Rarity.COMMON, mage.cards.b.BigOtter.class));
        cards.add(new SetCardInfo("Big Penguin", 4, Rarity.COMMON, mage.cards.b.BigPenguin.class));
        cards.add(new SetCardInfo("Big Rhino", 56, Rarity.COMMON, mage.cards.b.BigRhino.class));
        cards.add(new SetCardInfo("Big Snail", 17, Rarity.COMMON, mage.cards.b.BigSnail.class));
        cards.add(new SetCardInfo("Black Elemental Blast", 32, Rarity.COMMON, mage.cards.a.AlchemistsGift.class));
        
    }

   /// @Override
   // public BoosterCollator createCollator() {
 //       return new StreetsOfNewCapennaCollator();
   // }
}