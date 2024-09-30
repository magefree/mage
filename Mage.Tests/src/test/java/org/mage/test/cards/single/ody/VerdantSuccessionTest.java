package org.mage.test.cards.single.ody;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.v.VerdantSuccession Verdant Succession}
 *
 * Whenever a green nontoken creature dies, that creature's controller may search their library for a card with the
 * same name as that creature and put it onto the battlefield. If that player does, they shuffle their library.
 *
 * @author jimga150
 */
public class VerdantSuccessionTest extends CardTestPlayerBase {

    private static final String verdantSuccession = "Verdant Succession";

    @Test
    public void singleTest() {
        addCard(Zone.BATTLEFIELD, playerA, verdantSuccession);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise");
        addCard(Zone.LIBRARY, playerA, "Birds of Paradise");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Damnation");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");

        // Choose yes to search
        setChoice(playerA, "Yes");

        // Select cards
        addTarget(playerA, "Birds of Paradise");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Birds of Paradise", 1);

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Birds of Paradise", 1);
    }

    @Test
    public void boardWipeTest() {
        addCard(Zone.BATTLEFIELD, playerA, verdantSuccession);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise");
        addCard(Zone.LIBRARY, playerA, "Birds of Paradise");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.LIBRARY, playerA, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerA, "Ankle Biter");
        addCard(Zone.LIBRARY, playerA, "Ankle Biter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Damnation");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");

        // Order triggers
        setChoice(playerA, "Whenever");
        setChoice(playerA, "Whenever");

        // Choose yes to searches
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Yes");

        // Select cards
        addTarget(playerA, "Birds of Paradise");
        addTarget(playerA, "Arbor Elf");
        addTarget(playerA, "Ankle Biter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Birds of Paradise", 1);
        assertPermanentCount(playerA, "Arbor Elf", 1);
        assertPermanentCount(playerA, "Ankle Biter", 1);

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Birds of Paradise", 1);
        assertGraveyardCount(playerA, "Arbor Elf", 1);
        assertGraveyardCount(playerA, "Ankle Biter", 1);
    }
}
