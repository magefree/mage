package org.mage.test.cards.split;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CastSplitCardsWithFlashbackTest extends CardTestPlayerBase {

    @Test
    public void test_Flashback_Simple() {
        // {1}{U}
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // add flashback
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Lightning Bolt");

        // cast as flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_Flashback_SplitLeft() {
        // {1}{U}
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.GRAVEYARD, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // add flashback
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Wear // Tear");

        // cast as flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}", "Bident of Thassa");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertPermanentCount(playerB, "Bow of Nylea", 1);
        assertExileCount(playerA, "Wear // Tear", 1);
    }

    @Test
    public void test_Flashback_SplitLeft_ZCCChanged() {
        // {1}{U}
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.HAND, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 2); // for first Wear cast
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // play card as normal to change ZCC for split cards (simulate GUI session - ZCC's was bugged and card's parts was able to have different ZCC)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear", "Bow of Nylea");

        // add flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Wear // Tear");

        // cast as flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{R}", "Bident of Thassa");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertGraveyardCount(playerB, "Bow of Nylea", 1);
        assertExileCount(playerA, "Wear // Tear", 1);
    }

    @Test
    public void test_Flashback_SplitRight() {
        // {1}{U}
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
        addCard(Zone.HAND, playerA, "Snapcaster Mage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.GRAVEYARD, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // add flashback
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Wear // Tear");

        // cast as flashback
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {W}", "Bident of Thassa");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertPermanentCount(playerB, "Bow of Nylea", 1);
        assertExileCount(playerA, "Wear // Tear", 1);
    }
}
