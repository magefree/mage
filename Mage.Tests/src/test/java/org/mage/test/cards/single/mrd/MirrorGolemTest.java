package org.mage.test.cards.single.mrd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MirrorGolemTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MirrorGolem Mirror Golem} {6}
     * Artifact Creature — Golem
     * Imprint — When this creature enters, you may exile target card from a graveyard.
     * This creature has protection from each of the exiled card's card types.
     * 3/4
     */
    private static final String golem = "Mirror Golem";

    @Test
    public void test_ProtectionSorcery() {
        addCard(Zone.HAND, playerA, golem);
        addCard(Zone.HAND, playerA, "Pyroclasm");
        addCard(Zone.GRAVEYARD, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, golem);
        addTarget(playerA, "Divination");
        setChoice(playerA, true); // yes to "you may exile"

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, "Divination", 1);
        assertGraveyardCount(playerA, "Pyroclasm", 1);
        assertDamageReceived(playerA, golem, 0);
    }

    @Test
    public void test_TokenCopy() {
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.HAND, playerA, "Relm's Sketching");
        addCard(Zone.HAND, playerA, "Storm's Wrath");
        addCard(Zone.GRAVEYARD, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Relm's Sketching", golem);
        addTarget(playerA, "Divination");
        setChoice(playerA, true); // yes to "you may exile"

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm's Wrath");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, "Divination", 1);
        assertGraveyardCount(playerA, "Storm's Wrath", 1);
        assertPermanentCount(playerA, golem, 1);
        assertGraveyardCount(playerA, golem, 1);
        assertDamageReceived(playerA, golem, 0);
    }
}
