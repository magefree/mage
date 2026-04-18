package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MagarOfTheMagicStringsTest extends CardTestPlayerBase {

    @Test
    public void testTriggered() {
        addCard(Zone.BATTLEFIELD, playerA, "Magar of the Magic Strings");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}{R}:");
        addTarget(playerA, "Lightning Bolt");

        attack(3, playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand());
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 1);
        assertLife(playerB, 14);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 3, 3);
    }

    @Test
    public void testExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Magar of the Magic Strings");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Fell");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}{R}:");
        addTarget(playerA, "Lightning Bolt");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fell");
        addTarget(playerB, EmptyNames.FACE_DOWN_CARD.getTestCommand());

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 0);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertExileCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void testXCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Magar of the Magic Strings");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Fireball");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}{R}:");
        addTarget(playerA, "Fireball");

        attack(3, playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand());
        setChoice(playerA, true);
        addTarget(playerA, playerB);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 1);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Fireball", 0);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 3, 3);
    }

    @Test
    public void testModal() {
        addCard(Zone.BATTLEFIELD, playerA, "Magar of the Magic Strings");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Ivory Charm");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}{R}:");
        addTarget(playerA, "Ivory Charm");

        attack(3, playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand());
        setChoice(playerA, true);
        setModeChoice(playerA, "2");
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 1);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Ivory Charm", 0);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 3, 3);
        assertTapped("Balduvian Bears", true);
    }

    @Test
    public void testClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Magar of the Magic Strings");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Clone");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}{R}:");
        addTarget(playerA, "Lightning Bolt");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, EmptyNames.FACE_DOWN_CARD.getTestCommand());

        attack(4, playerB, EmptyNames.FACE_DOWN_CARD.getTestCommand());

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 1);
        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 1);
        assertLife(playerA, 17);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertPowerToughness(playerB, EmptyNames.FACE_DOWN_CARD.getTestCommand(), 3, 3);
    }

}
