package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BasrisLieutenantTest extends CardTestPlayerBase {

    private static final String basrisLieutenant = "Basri's Lieutenant";

    @Test
    public void counterOnLieutenant(){
        // When Basri's Lieutenant enters the battlefield, put a +1/+1 counter on target creature you control.
        // Whenever Basri's Lieutenant or another creature you control dies, if it had a +1/+1 counter on it, create a 2/2 white Knight creature token with vigilance.

        addCard(Zone.HAND, playerA, basrisLieutenant);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Wrath of God");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, basrisLieutenant);
        addTarget(playerA, basrisLieutenant);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void counterOnOtherCreature(){
        // When Basri's Lieutenant enters the battlefield, put a +1/+1 counter on target creature you control.
        // Whenever Basri's Lieutenant or another creature you control dies, if it had a +1/+1 counter on it, create a 2/2 white Knight creature token with vigilance.

        addCard(Zone.HAND, playerA, basrisLieutenant);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Wrath of God");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, basrisLieutenant);
        addTarget(playerA, "Savannah Lions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void creatureWithoutCounterDies(){
        // When Basri's Lieutenant enters the battlefield, put a +1/+1 counter on target creature you control.
        // Whenever Basri's Lieutenant or another creature you control dies, if it had a +1/+1 counter on it, create a 2/2 white Knight creature token with vigilance.

        addCard(Zone.HAND, playerA, basrisLieutenant);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        // Destroy target nonartifact, nonblack creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terror");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, basrisLieutenant);
        addTarget(playerA, "Savannah Lions");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terror", basrisLieutenant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Knight Token", 0);
    }
}
