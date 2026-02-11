package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

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
        this.maxCardNumberInBooster = 116; //small set

        cards.add(new SetCardInfo("Abrade", 45, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Alchemists Gift", 31, Rarity.COMMON, mage.cards.a.AlchemistsGift.class));
        cards.add(new SetCardInfo("Big Buggy", 32, Rarity.COMMON, mage.cards.b.BigBuggy.class));
        cards.add(new SetCardInfo("Big Doggy", 46, Rarity.COMMON, mage.cards.b.BigDoggy.class));
        cards.add(new SetCardInfo("Big Fishy", 17, Rarity.COMMON, mage.cards.b.BigFishy.class));
        cards.add(new SetCardInfo("Big Ghost", 3, Rarity.COMMON, mage.cards.b.BigGhost.class));
        cards.add(new SetCardInfo("Big Kitty", 59, Rarity.COMMON, mage.cards.b.BigKitty.class));
        cards.add(new SetCardInfo("Big Lizard", 33, Rarity.COMMON, mage.cards.b.BigLizard.class));
        cards.add(new SetCardInfo("Big Otter", 47, Rarity.COMMON, mage.cards.b.BigOtter.class));
        cards.add(new SetCardInfo("Big Penguin", 4, Rarity.COMMON, mage.cards.b.BigPenguin.class));
        cards.add(new SetCardInfo("Big Rhino", 60, Rarity.COMMON, mage.cards.b.BigRhino.class));
        cards.add(new SetCardInfo("Big Snail", 18, Rarity.COMMON, mage.cards.b.BigSnail.class));
        cards.add(new SetCardInfo("Black Elemental Blast", 34, Rarity.COMMON, mage.cards.b.BlackElementalBlast.class));
        cards.add(new SetCardInfo("Blasphemous Act", 48, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 19, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Chain To Memory", 20, Rarity.COMMON, mage.cards.c.ChainToMemory.class));
        cards.add(new SetCardInfo("Circle of Protection White", 5, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Claws of Coercesion", 21, Rarity.COMMON, mage.cards.c.ClawsOfCoercesion.class));
        cards.add(new SetCardInfo("Claws of Fear", 35, Rarity.COMMON, mage.cards.c.ClawsOfFear.class));
        cards.add(new SetCardInfo("Claws of Power", 49, Rarity.COMMON, mage.cards.c.ClawsOfPower.class));
        cards.add(new SetCardInfo("Claws of Sight", 6, Rarity.COMMON, mage.cards.c.ClawsOfSight.class));
        cards.add(new SetCardInfo("Claws of Venom", 62, Rarity.COMMON, mage.cards.c.ClawsOfVenom.class));
        cards.add(new SetCardInfo("Weakstones's Subjugation", 22, Rarity.UNCOMMON, mage.cards.w.WeakstonesSubjugation.class));
        cards.add(new SetCardInfo("Critter of Action", 73, Rarity.RARE, mage.cards.c.CritterOfAction.class));
        cards.add(new SetCardInfo("Critter of Bravery", 74, Rarity.RARE, mage.cards.c.CritterOfBravery.class));
        cards.add(new SetCardInfo("Critter of Courage", 75, Rarity.RARE, mage.cards.c.CritterOfCourage.class));
        cards.add(new SetCardInfo("Critter of Daring", 76, Rarity.RARE, mage.cards.c.CritterOfDaring.class));
        cards.add(new SetCardInfo("Critter of Esteem", 77, Rarity.RARE, mage.cards.c.CritterOfEsteem.class));
        cards.add(new SetCardInfo("Critter of Fame", 78, Rarity.RARE, mage.cards.c.CritterOfFame.class));
        cards.add(new SetCardInfo("Critter of Guilt", 79, Rarity.RARE, mage.cards.c.CritterOfGuilt.class));
        cards.add(new SetCardInfo("Critter of Heart", 80, Rarity.RARE, mage.cards.c.CritterOfHeart.class));
        cards.add(new SetCardInfo("Critter of Intent", 81, Rarity.RARE, mage.cards.c.CritterOfIntent.class));
        cards.add(new SetCardInfo("Critter of Jest", 82, Rarity.RARE, mage.cards.c.CritterOfJest.class));
        cards.add(new SetCardInfo("Introduction to Annihilation", 1, Rarity.COMMON, mage.cards.i.IntroductionToAnnihilation.class));
        cards.add(new SetCardInfo("Danger Buggy", 36, Rarity.COMMON, mage.cards.d.DangerBuggy.class));
        cards.add(new SetCardInfo("Danger Doggy", 50, Rarity.COMMON, mage.cards.d.DangerDoggy.class));
        cards.add(new SetCardInfo("Danger Fishy", 23, Rarity.COMMON, mage.cards.d.DangerFishy.class));
        cards.add(new SetCardInfo("Danger Ghost", 7, Rarity.COMMON, mage.cards.d.DangerGhost.class));
        cards.add(new SetCardInfo("Danger Kitty", 63, Rarity.COMMON, mage.cards.d.DangerKitty.class));
        cards.add(new SetCardInfo("Danger Lizard", 37, Rarity.COMMON, mage.cards.d.DangerLizard.class));
        cards.add(new SetCardInfo("Danger Otter", 51, Rarity.COMMON, mage.cards.d.DangerOtter.class));
        cards.add(new SetCardInfo("Danger Penguin", 8, Rarity.COMMON, mage.cards.d.DangerPenguin.class));
        cards.add(new SetCardInfo("Danger Rhino", 64, Rarity.COMMON, mage.cards.d.DangerRhino.class));
        cards.add(new SetCardInfo("Danger Snail", 24, Rarity.COMMON, mage.cards.d.DangerSnail.class));
        cards.add(new SetCardInfo("Daring Escape", 52, Rarity.COMMON, mage.cards.d.DaringEscape.class));
        cards.add(new SetCardInfo("Divine Reckoning", 9, Rarity.RARE, mage.cards.d.DivineReckoning.class));
        cards.add(new SetCardInfo("Drop of Honey", 65, Rarity.RARE, mage.cards.d.DropOfHoney.class));
        cards.add(new SetCardInfo("Fatal Push", 38, Rarity.UNCOMMON, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Final Badger", 103, Rarity.UNCOMMON, mage.cards.f.FinalBadger.class));
        cards.add(new SetCardInfo("First Badger", 104, Rarity.COMMON, mage.cards.f.FirstBadger.class));
        cards.add(new SetCardInfo("Giant Growth", 66, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gods Willing", 10, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Green Elemental Blast", 67, Rarity.COMMON, mage.cards.g.GreenElementalBlast.class));
        cards.add(new SetCardInfo("Indatha Triome", 107, Rarity.UNCOMMON, mage.cards.i.IndathaTriome.class));
        cards.add(new SetCardInfo("Jetmir's Garden", 108, Rarity.UNCOMMON, mage.cards.j.JetmirsGarden.class));
        cards.add(new SetCardInfo("Ketria Triome", 109, Rarity.UNCOMMON, mage.cards.k.KetriaTriome.class));
        cards.add(new SetCardInfo("Little Buggy",68, Rarity.COMMON, mage.cards.l.LittleBuggy.class));
        cards.add(new SetCardInfo("Little Doggy",11, Rarity.COMMON, mage.cards.l.LittleDoggy.class));
        cards.add(new SetCardInfo("Little Fishy",69, Rarity.COMMON, mage.cards.l.LittleFishy.class));
        cards.add(new SetCardInfo("Little Ghost",39, Rarity.COMMON, mage.cards.l.LittleGhost.class));
        cards.add(new SetCardInfo("Little Kitty",12, Rarity.COMMON, mage.cards.l.LittleKitty.class));
        cards.add(new SetCardInfo("Little Lizard",53, Rarity.COMMON, mage.cards.l.LittleLizard.class));
        cards.add(new SetCardInfo("Little Otter",25, Rarity.COMMON, mage.cards.l.LittleOtter.class));
        cards.add(new SetCardInfo("Little Penguin",26, Rarity.COMMON, mage.cards.l.LittlePenguin.class));
        cards.add(new SetCardInfo("Little Rhino",54, Rarity.COMMON, mage.cards.l.LittleRhino.class));
        cards.add(new SetCardInfo("Little Snail",40, Rarity.COMMON, mage.cards.l.LittleSnail.class));
        cards.add(new SetCardInfo("Lord Buggy",83, Rarity.UNCOMMON, mage.cards.l.LordBuggy.class));
        cards.add(new SetCardInfo("Lord Doggy",84, Rarity.UNCOMMON, mage.cards.l.LordDoggy.class));
        cards.add(new SetCardInfo("Lord Fishy",85, Rarity.UNCOMMON, mage.cards.l.LordFishy.class));
        cards.add(new SetCardInfo("Lord Ghost",86, Rarity.UNCOMMON, mage.cards.l.LordGhost.class));
        cards.add(new SetCardInfo("Lord Kitty",87, Rarity.UNCOMMON, mage.cards.l.LordKitty.class));
        cards.add(new SetCardInfo("Lord Lizard",88, Rarity.UNCOMMON, mage.cards.l.LordLizard.class));
        cards.add(new SetCardInfo("Lord Otter",89, Rarity.UNCOMMON, mage.cards.l.LordOtter.class));
        cards.add(new SetCardInfo("Lord Penguin",90, Rarity.UNCOMMON, mage.cards.l.LordPenguin.class));
        cards.add(new SetCardInfo("Lord Rhino",91, Rarity.UNCOMMON, mage.cards.l.LordRhino.class));
        cards.add(new SetCardInfo("Lord Snail",92, Rarity.UNCOMMON, mage.cards.l.LordSnail.class));
        cards.add(new SetCardInfo("Meditative Buggy",93, Rarity.RARE, mage.cards.m.MeditativeBuggy.class));
        cards.add(new SetCardInfo("Meditative Doggy",94, Rarity.RARE, mage.cards.m.MeditativeFishy.class));
        cards.add(new SetCardInfo("Meditative Fishy",95, Rarity.RARE, mage.cards.m.MeditativeFishy.class));
        cards.add(new SetCardInfo("Meditative Ghost",96, Rarity.RARE, mage.cards.m.MeditativeGhost.class));
        cards.add(new SetCardInfo("Meditative Kitty",97, Rarity.RARE, mage.cards.m.MeditativeKitty.class));
        cards.add(new SetCardInfo("Meditative Lizard",98, Rarity.RARE, mage.cards.m.MeditativeLizard.class));
        cards.add(new SetCardInfo("Meditative Otter",99, Rarity.RARE, mage.cards.m.MeditativeOtter.class));
        cards.add(new SetCardInfo("Meditative Penguin",100, Rarity.RARE, mage.cards.m.MeditativePenguin.class));
        cards.add(new SetCardInfo("Meditative Rhino",101, Rarity.RARE, mage.cards.m.MeditativeRhino.class));
        cards.add(new SetCardInfo("Meditative Snail",102, Rarity.RARE, mage.cards.m.MeditativeSnail.class));
        cards.add(new SetCardInfo("Ominous Parcel",105, Rarity.COMMON, mage.cards.o.OminousParcel.class));
        cards.add(new SetCardInfo("Raffine's Tower",110, Rarity.UNCOMMON, mage.cards.r.RaffinesTower.class));
        cards.add(new SetCardInfo("Raugrin Triome",111, Rarity.UNCOMMON, mage.cards.r.RaugrinTriome.class));
        cards.add(new SetCardInfo("Red Elemental Blast",55, Rarity.COMMON, mage.cards.r.RedElementalBlast.class));
        cards.add(new SetCardInfo("Ritual of Soot",41, Rarity.RARE, mage.cards.r.RitualOfSoot.class));
        cards.add(new SetCardInfo("Savai Triome",112, Rarity.UNCOMMON, mage.cards.s.SavaiTriome.class));
        cards.add(new SetCardInfo("Sparas Headquarters",113, Rarity.UNCOMMON, mage.cards.s.SparasHeadquarters.class));
        cards.add(new SetCardInfo("Planar Disruption",13, Rarity.UNCOMMON, mage.cards.p.PlanarDisruption.class));
        cards.add(new SetCardInfo("Springjaw Trap",106, Rarity.COMMON, mage.cards.s.SpringjawTrap.class));
        cards.add(new SetCardInfo("Suprise Drain",42, Rarity.COMMON, mage.cards.s.SurpriseDrain.class));
        cards.add(new SetCardInfo("Suprise Endurance",14, Rarity.COMMON, mage.cards.s.SurpriseEndurance.class));
        cards.add(new SetCardInfo("Suprise Sear",56, Rarity.COMMON, mage.cards.s.SurpriseSear.class));
        cards.add(new SetCardInfo("Suprise Strength",70, Rarity.COMMON, mage.cards.s.SurpriseStrength.class));
        cards.add(new SetCardInfo("Suprise Weakness",28, Rarity.COMMON, mage.cards.s.SurpriseWeakness.class));
        cards.add(new SetCardInfo("The Massive Critter",2, Rarity.RARE, mage.cards.t.TheMassiveCritter.class));
        cards.add(new SetCardInfo("The Phasing of Zhalfir",27, Rarity.RARE, mage.cards.t.ThePhasingOfZhalfir.class));
        cards.add(new SetCardInfo("Tough Buggy",71, Rarity.COMMON, mage.cards.t.ToughBuggy.class));
        cards.add(new SetCardInfo("Tough Doggy",15, Rarity.COMMON, mage.cards.t.ToughDoggy.class));
        cards.add(new SetCardInfo("Tough Fishy",72, Rarity.COMMON, mage.cards.t.ToughFishy.class));
        cards.add(new SetCardInfo("Tough Ghost",43, Rarity.COMMON, mage.cards.t.ToughGhost.class));
        cards.add(new SetCardInfo("Tough Kitty",16, Rarity.COMMON, mage.cards.t.ToughKitty.class));
        cards.add(new SetCardInfo("Tough Lizard",57, Rarity.COMMON, mage.cards.t.ToughLizard.class));
        cards.add(new SetCardInfo("Tough Otter",29, Rarity.COMMON, mage.cards.t.ToughOtter.class));
        cards.add(new SetCardInfo("Tough Penguin",30, Rarity.COMMON, mage.cards.t.ToughPenguin.class));
        cards.add(new SetCardInfo("Tough Rhino",58, Rarity.COMMON, mage.cards.t.ToughRhino.class));
        cards.add(new SetCardInfo("Tough Snail",44, Rarity.COMMON, mage.cards.t.ToughSnail.class));
        cards.add(new SetCardInfo("Xander's Lounge",114, Rarity.UNCOMMON, mage.cards.x.XandersLounge.class));
        cards.add(new SetCardInfo("Zagoth Triome",115, Rarity.UNCOMMON, mage.cards.z.ZagothTriome.class));
        cards.add(new SetCardInfo("Ziatora's Proving Ground",116, Rarity.UNCOMMON, mage.cards.z.ZiatorasProvingGround.class));
        
    }

   /// @Override
   // public BoosterCollator createCollator() {
 //       return new StreetsOfNewCapennaCollator();
   // }
}