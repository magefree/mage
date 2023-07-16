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
        super("Critters and Magic", "CAMCS", ExpansionSet.buildDate(2023, 7, 16), SetType.CUSTOM_SET); //update the release date when its done?
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
        cards.add(new SetCardInfo("Black Elemental Blast", 32, Rarity.COMMON, mage.cards.b.BlackElementalBlast.class));
        cards.add(new SetCardInfo("Blasphemous Act", 45, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 18, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Chain To Memory", 19, Rarity.COMMON, mage.cards.c.ChainToMemory.class));
        cards.add(new SetCardInfo("Circle of Protection White", 5, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Claws of Coercesion", 20, Rarity.COMMON, mage.cards.c.ClawsOfCoercesion.class));
        cards.add(new SetCardInfo("Claws of Fear", 33, Rarity.COMMON, mage.cards.c.ClawsOfFear.class));
        cards.add(new SetCardInfo("Claws of Power", 46, Rarity.COMMON, mage.cards.c.ClawsOfPower.class));
        cards.add(new SetCardInfo("Claws of Sight", 6, Rarity.COMMON, mage.cards.c.ClawsOfSight.class));
        cards.add(new SetCardInfo("Claws of Venom", 58, Rarity.COMMON, mage.cards.c.ClawsOfVenom.class));
        cards.add(new SetCardInfo("Weakstones's Subjugation", 21, Rarity.UNCOMMON, mage.cards.w.WeakstonesSubjugation.class));
        cards.add(new SetCardInfo("Critter of Action", 68, Rarity.RARE, mage.cards.c.CritterOfAction.class));
        cards.add(new SetCardInfo("Critter of Bravery", 69, Rarity.RARE, mage.cards.c.CritterOfBravery.class));
        cards.add(new SetCardInfo("Critter of Courage", 70, Rarity.RARE, mage.cards.c.CritterOfCourage.class));
        cards.add(new SetCardInfo("Critter of Daring", 71, Rarity.RARE, mage.cards.c.CritterOfDaring.class));
        cards.add(new SetCardInfo("Critter of Esteem", 72, Rarity.RARE, mage.cards.c.CritterOfEsteem.class));
        cards.add(new SetCardInfo("Critter of Fame", 73, Rarity.RARE, mage.cards.c.CritterOfFame.class));
        cards.add(new SetCardInfo("Critter of Guilt", 74, Rarity.RARE, mage.cards.c.CritterOfGuilt.class));
        cards.add(new SetCardInfo("Critter of Heart", 75, Rarity.RARE, mage.cards.c.CritterOfHeart.class));
        cards.add(new SetCardInfo("Critter of Intent", 76, Rarity.RARE, mage.cards.c.CritterOfIntent.class));
        cards.add(new SetCardInfo("Critter of Jest", 77, Rarity.RARE, mage.cards.c.CritterOfJest.class));
        cards.add(new SetCardInfo("Introduction to Annihilation", 1, Rarity.COMMON, mage.cards.i.IntroductionToAnnihilation.class));
        cards.add(new SetCardInfo("Danger Buggy", 34, Rarity.COMMON, mage.cards.d.DangerBuggy.class));
        cards.add(new SetCardInfo("Danger Doggy", 47, Rarity.COMMON, mage.cards.d.DangerDoggy.class));
        cards.add(new SetCardInfo("Danger Fishy", 22, Rarity.COMMON, mage.cards.d.DangerFishy.class));
        cards.add(new SetCardInfo("Danger Ghost", 7, Rarity.COMMON, mage.cards.d.DangerGhost.class));
        cards.add(new SetCardInfo("Danger Kitty", 59, Rarity.COMMON, mage.cards.d.DangerKitty.class));
        cards.add(new SetCardInfo("Danger Lizard", 35, Rarity.COMMON, mage.cards.d.DangerLizard.class));
        cards.add(new SetCardInfo("Danger Otter", 48, Rarity.COMMON, mage.cards.d.DangerOtter.class));
        cards.add(new SetCardInfo("Danger Penguin", 8, Rarity.COMMON, mage.cards.d.DangerPenguin.class));
        cards.add(new SetCardInfo("Danger Rhino", 60, Rarity.COMMON, mage.cards.d.DangerRhino.class));
        cards.add(new SetCardInfo("Danger Snail", 23, Rarity.COMMON, mage.cards.d.DangerSnail.class));
        cards.add(new SetCardInfo("Daring Escape", 49, Rarity.COMMON, mage.cards.d.DaringEscape.class));
        cards.add(new SetCardInfo("Divine Reckoning", 9, Rarity.RARE, mage.cards.d.DivineReckoning.class));
        cards.add(new SetCardInfo("Drop of Honey", 61, Rarity.RARE, mage.cards.d.DropOfHoney.class));
        cards.add(new SetCardInfo("Fatal Push", 36, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Final Badger", 98, Rarity.UNCOMMON, mage.cards.f.FinalBadger.class));
        cards.add(new SetCardInfo("First Badger", 99, Rarity.COMMON, mage.cards.f.FirstBadger.class));
        cards.add(new SetCardInfo("Giant Growth", 62, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gods Willing", 10, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Green Elemental Blast", 63, Rarity.COMMON, mage.cards.g.GreenElementalBlast.class));
        cards.add(new SetCardInfo("Indatha Triome", 100, Rarity.UNCOMMON, mage.cards.i.IndathaTriome.class));
        cards.add(new SetCardInfo("Jetmir's Garden", 101, Rarity.UNCOMMON, mage.cards.j.JetmirsGarden.class));
        cards.add(new SetCardInfo("Ketria Triome", 102, Rarity.UNCOMMON, mage.cards.k.KetriaTriome.class));
        cards.add(new SetCardInfo("Little Buggy",64, Rarity.COMMON, mage.cards.l.LittleBuggy.class));
        cards.add(new SetCardInfo("Little Doggy",11, Rarity.COMMON, mage.cards.l.LittleDoggy.class));
        cards.add(new SetCardInfo("Little Fishy",65, Rarity.COMMON, mage.cards.l.LittleFishy.class));
        cards.add(new SetCardInfo("Little Ghost",37, Rarity.COMMON, mage.cards.l.LittleGhost.class));
        cards.add(new SetCardInfo("Little Kitty",12, Rarity.COMMON, mage.cards.l.LittleKitty.class));
        cards.add(new SetCardInfo("Little Lizard",50, Rarity.COMMON, mage.cards.l.LittleLizard.class));
        cards.add(new SetCardInfo("Little Otter",24, Rarity.COMMON, mage.cards.l.LittleOtter.class));
        cards.add(new SetCardInfo("Little Penguin",25, Rarity.COMMON, mage.cards.l.LittlePenguin.class));
        cards.add(new SetCardInfo("Little Rhino",51, Rarity.COMMON, mage.cards.l.LittleRhino.class));
        cards.add(new SetCardInfo("Little Otter",38, Rarity.COMMON, mage.cards.l.LittleSnail.class));


















        
    }

   /// @Override
   // public BoosterCollator createCollator() {
 //       return new StreetsOfNewCapennaCollator();
   // }
}