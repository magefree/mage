package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SerpentsSoulJarTest extends CardTestPlayerBase {

    /**
     * The permission comes from a resolved ability, so it lasts after the Jar leaves, but it still allows only one
     * creature spell. It once allowed one per card: each cast was counted against the Jar's new object, so the
     * permission from the old one was never used up.
     */
    @Test
    public void testOneCastAfterJarLeaves() {
        // Whenever an Elf you control dies, exile it.
        // {T}, Pay 2 life: Until end of turn, you may cast a creature spell from among cards exiled with this artifact.
        addCard(Zone.BATTLEFIELD, playerA, "Serpent's Soul-Jar");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 6);
        addCard(Zone.HAND, playerA, "Pyroclasm"); // 2 damage to each creature
        addCard(Zone.HAND, playerA, "Shatter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm", true);
        setChoice(playerA, "Whenever an Elf you control dies"); // trigger order
        checkExileCount("both Elves exiled with the Jar", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 2 life");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatter", "Serpent's Soul-Jar", "{T}, Pay 2 life");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", true);
        checkPlayableAbility("permission used up", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Llanowar Elves", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Serpent's Soul-Jar", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertExileCount(playerA, "Llanowar Elves", 1);
        assertLife(playerA, 18);
    }
}
