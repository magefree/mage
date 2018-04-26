package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Angel of Jubilation Other nonblack creatures you control get +1/+1. Players
 * can't pay life or sacrifice creatures to cast spells or activate abilities
 *
 * @author noxx
 */
public class AngelOfJubilationTest extends CardTestPlayerBase {

    /**
     * Tests boosting other non black creatures
     */
    @Test
    public void testBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Angel of Jubilation", 3, 3);
        assertPowerToughness(playerA, "Devout Chaplain", 3, 3);
        assertPowerToughness(playerA, "Corpse Traders", 3, 3);
    }

    /**
     * Tests boost disappeared on leaving battlefield
     */
    @Test
    public void testNoBoostOnBattlefieldLeave() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Angel of Jubilation");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Angel of Jubilation", 0);
        assertPowerToughness(playerA, "Devout Chaplain", 2, 2);
        assertPowerToughness(playerA, "Corpse Traders", 3, 3);
    }

    @Test
    public void testOpponentCantSacrificeCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Corpse Traders");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice a creature: {this} gets +2/+2 until end of turn.");
        playerB.addChoice("Corpse Traders");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, "Nantuko Husk", 2, 2);
        assertPermanentCount(playerB, "Corpse Traders", 1);
    }

    @Test
    public void testOpponentCanSacrificeNonCreaturePermanents() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerB, "Barrin, Master Wizard");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Food Chain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Sacrifice a permanent you control: Return target creature to its owner's hand.");
        playerB.addChoice("Food Chain");
        playerA.addTarget("Angel of Jubilation");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Jubilation", 0);
        assertPermanentCount(playerB, "Food Chain", 0);
    }

    @Test
    public void testOpponentCantSacrificeCreaturesAsPartOfPermanentsOptions() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerB, "Barrin, Master Wizard");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, Sacrifice a permanent you control: Return target creature to its owner's hand.");
        playerB.addChoice("Nantuko Husk");
        playerA.addTarget("Angel of Jubilation");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Jubilation", 1);
        assertPermanentCount(playerB, "Nantuko Husk", 1);
    }

    @Test
    public void testOpponentCantSacrificeAll() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerB, "Nantuko Husk");
        addCard(Zone.BATTLEFIELD, playerB, "Corpse Traders");
        addCard(Zone.HAND, playerB, "Soulblast");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Soulblast", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Nantuko Husk", 1);
        assertPermanentCount(playerB, "Corpse Traders", 1);
    }

    @Test
    public void testOpponentCantSacrificeCreatureSource() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerB, "Children of Korlis");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice {this}: You gain life equal to the life you've lost this turn.");
        playerB.addChoice("Skirk Prospector");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Children of Korlis", 1);
    }

    @Test
    public void testOpponentCanSacrificeAllLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerB, "Tomb of Urami");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{B}{B}, {T}, Sacrifice all lands you control: Create a legendary 5/5 black Demon Spirit creature token with flying named Urami.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Swamp", 0);
    }

    @Test
    public void testOpponentCanSacrificeNonCreatureSource() {
        addCard(Zone.BATTLEFIELD, playerA, "Angel of Jubilation");
        addCard(Zone.BATTLEFIELD, playerA, "Tundra");
        addCard(Zone.BATTLEFIELD, playerB, "Wasteland");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice {this}: Destroy target nonbasic land.");
        playerB.addTarget("Tundra");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Tundra", 0);
        assertPermanentCount(playerB, "Wasteland", 0);
    }

}
