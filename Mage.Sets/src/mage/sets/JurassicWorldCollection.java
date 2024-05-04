
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class JurassicWorldCollection extends ExpansionSet {

    private static final JurassicWorldCollection instance = new JurassicWorldCollection();

    public static JurassicWorldCollection getInstance() {
        return instance;
    }

    private JurassicWorldCollection() {
        super("Jurassic World Collection", "REX", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Blue, Loyal Raptor", 8, Rarity.RARE, mage.cards.b.BlueLoyalRaptor.class));
        cards.add(new SetCardInfo("Command Tower", 26, Rarity.COMMON, mage.cards.c.CommandTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Command Tower", "26b", Rarity.COMMON, mage.cards.c.CommandTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compy Swarm", 9, Rarity.RARE, mage.cards.c.CompySwarm.class));
        cards.add(new SetCardInfo("Cresting Mosasaurus", 2, Rarity.RARE, mage.cards.c.CrestingMosasaurus.class));
        cards.add(new SetCardInfo("Dino DNA", 20, Rarity.RARE, mage.cards.d.DinoDNA.class));
        cards.add(new SetCardInfo("Don't Move", 1, Rarity.RARE, mage.cards.d.DontMove.class));
        cards.add(new SetCardInfo("Ellie and Alan, Paleontologists", 10, Rarity.RARE, mage.cards.e.EllieAndAlanPaleontologists.class));
        cards.add(new SetCardInfo("Forest", 25, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", "25b", Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Grim Giganotosaurus", 11, Rarity.RARE, mage.cards.g.GrimGiganotosaurus.class));
        cards.add(new SetCardInfo("Henry Wu, InGen Geneticist", 12, Rarity.RARE, mage.cards.h.HenryWuInGenGeneticist.class));
        cards.add(new SetCardInfo("Hunting Velociraptor", 4, Rarity.RARE, mage.cards.h.HuntingVelociraptor.class));
        cards.add(new SetCardInfo("Ian Malcolm, Chaotician", 13, Rarity.RARE, mage.cards.i.IanMalcolmChaotician.class));
        cards.add(new SetCardInfo("Indoraptor, the Perfect Hybrid", 15, Rarity.RARE, mage.cards.i.IndoraptorThePerfectHybrid.class));
        cards.add(new SetCardInfo("Island", 22, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", "22b", Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jurassic Park", 7, Rarity.RARE, mage.cards.j.JurassicPark.class));
        cards.add(new SetCardInfo("Life Finds a Way", 5, Rarity.RARE, mage.cards.l.LifeFindsAWay.class));
        cards.add(new SetCardInfo("Mountain", 24, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "24b", Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Owen Grady, Raptor Trainer", 16, Rarity.RARE, mage.cards.o.OwenGradyRaptorTrainer.class));
        cards.add(new SetCardInfo("Permission Denied", 17, Rarity.RARE, mage.cards.p.PermissionDenied.class));
        cards.add(new SetCardInfo("Plains", 21, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", "21b", Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ravenous Tyrannosaurus", 18, Rarity.RARE, mage.cards.r.RavenousTyrannosaurus.class));
        cards.add(new SetCardInfo("Savage Order", 6, Rarity.RARE, mage.cards.s.SavageOrder.class));
        cards.add(new SetCardInfo("Spitting Dilophosaurus", 3, Rarity.RARE, mage.cards.s.SpittingDilophosaurus.class));
        cards.add(new SetCardInfo("Swamp", 23, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "23b", Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swooping Pteranodon", 19, Rarity.RARE, mage.cards.s.SwoopingPteranodon.class));
        cards.add(new SetCardInfo("Welcome to . . .", 7, Rarity.RARE, mage.cards.w.WelcomeTo.class));
    }
}
