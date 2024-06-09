package org.mage.test.cards.single.ori;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThopterSpyNetworkTest extends CardTestPlayerBase {

    /**
     * Thopter Spy Network
     * {2}{U}{U}
     * Enchantment
     *
     * At the beginning of your upkeep, if you control an artifact, create a 1/1 colorless Thopter artifact creature token with flying.
     * Whenever one or more artifact creatures you control deal combat damage to a player, draw a card.
     */
    private static final String network = "Thopter Spy Network";

    private static final String memnite = "Memnite"; // 1/1 Artifact
    private static final String ornithopter = "Ornithopter"; // 0/2 Artifact Flying

    private static final String squire = "Squire"; // 1/2

    @Test
    public void Simple() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void NotArtifactNoTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerA, squire);

        attack(1, playerA, squire);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void TwoAttackOneTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerA, memnite, 2);

        attack(1, playerA, memnite);
        attack(1, playerA, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void BlockedNoTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerA, memnite, 1);
        addCard(Zone.BATTLEFIELD, playerB, ornithopter, 1);

        attack(1, playerA, memnite);
        block(1, playerB, ornithopter, memnite);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerB, 20);
    }

    @Test
    public void BeingDamageNoTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        attack(2, playerB, memnite);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void BlockedDamageNoTrigger() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, network);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(2, playerB, memnite);
        block(2, playerA, memnite, memnite);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerA, 20);
    }
}
