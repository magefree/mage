package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ConcurrentModificationException;

// goal is to test the concurrentmodification exception related to continuous effects
public class WarpworldTest extends CardTestPlayerBase {

    private final String innkeeper = "Prosperous Innkeeper";
    private final String tracker = "Tireless Tracker";
    private final String goose = "Gilded Goose";
    private final String galazeth_prismari = "Galazeth Prismari";
    private final String wood_elves = "Wood Elves";
    private final String nesting_dragon = "Nesting Dragon";
    private final String primeval_titan = "Primeval Titan";
    private final String eternal_witness = "Eternal Witness";
    private final String westvale_abbey = "Westvale Abbey";
    private final String cradle = "Gaea's Cradle";
    private final String castle_garenbrig = "Castle Garenbrig";
    private final String nyxbloom_ancient = "Nyxbloom Ancient";
    private final String huntmaster = "Huntmaster of the Fells";
    private final String xenagos = "Xenagos, the Reveler";
    private final String thromok = "Thromok the Insatiable";
    private final String world_tree = "The World Tree";
    private final String nylea = "Nylea, Keen-Eyed";
    private final String kogla = "Kogla, the Titan Ape";
    private final String slime = "Acidic Slime";
    private final String druid = "Druid Class";

    @Test
    public void massWarpWorld(){
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, innkeeper);
        addCard(Zone.BATTLEFIELD, playerA, tracker);
        addCard(Zone.BATTLEFIELD, playerA, goose);
        addCard(Zone.BATTLEFIELD, playerA, galazeth_prismari);
        addCard(Zone.BATTLEFIELD, playerA, wood_elves);
        addCard(Zone.BATTLEFIELD, playerA, nesting_dragon);
        addCard(Zone.BATTLEFIELD, playerA, primeval_titan);
        addCard(Zone.BATTLEFIELD, playerA, eternal_witness);
        addCard(Zone.BATTLEFIELD, playerA, westvale_abbey);
        addCard(Zone.BATTLEFIELD, playerA, cradle);
        addCard(Zone.BATTLEFIELD, playerA, castle_garenbrig);
        addCard(Zone.BATTLEFIELD, playerA, nyxbloom_ancient);
        addCard(Zone.BATTLEFIELD, playerA, huntmaster);
        addCard(Zone.BATTLEFIELD, playerA, xenagos);
        addCard(Zone.BATTLEFIELD, playerA, thromok);
        addCard(Zone.BATTLEFIELD, playerA, world_tree);
        addCard(Zone.BATTLEFIELD, playerA, nylea);
        addCard(Zone.BATTLEFIELD, playerA, kogla);
        addCard(Zone.BATTLEFIELD, playerA, slime);
        addCard(Zone.BATTLEFIELD, playerA, druid);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.HAND, playerA, "Warp World");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Warp World");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
        }
        catch (ConcurrentModificationException concurrentModificationException){
            concurrentModificationException.printStackTrace();
            Assert.fail("Concurrent Modification Exception raised");
        }

    }
}
