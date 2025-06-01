package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TriumphantChompTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TriumphantChomp Triumphant Chomp} {R}
     * Sorcery
     * Triumphant Chomp deals damage to target creature equal to 2 or the greatest power among Dinosaurs you control, whichever is greater.
     */
    private static final String chomp = "Triumphant Chomp";

    @Test
    public void test_chomp_nodinosaur() {
        addCard(Zone.HAND, playerA, chomp);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerA, "Enormous Baloth");
        addCard(Zone.BATTLEFIELD, playerB, "Colossadactyl");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chomp, "Colossadactyl");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerB, "Colossadactyl", 2);
    }

    @Test
    public void test_chomp_dinosaur() {
        addCard(Zone.HAND, playerA, chomp);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerA, "Bellowing Aegisaur"); // 3/5 dino
        addCard(Zone.BATTLEFIELD, playerA, "Raging Regisaur"); // 4/4 dino
        addCard(Zone.BATTLEFIELD, playerB, "Colossadactyl");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chomp, "Colossadactyl");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerB, "Colossadactyl", 4);
    }
}
