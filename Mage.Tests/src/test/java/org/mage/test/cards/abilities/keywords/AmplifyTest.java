
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AmplifyTest extends CardTestPlayerBase {

    /**
     * Tests if +1/+1 counters are added
     */
    @Test
    public void testAmplifyOneCard() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }
    /**
     * Tests if +1/+1 counters are added
     */
    @Test
    public void testAmplifyTwoCards() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon^Phantasmal Dragon");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 11,11); // 5 + 6 from Amplify
    }
    /**
     * Tests that it works for Clone
     */
    @Test
    public void testAmplifyWithClone() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        addCard(Zone.HAND, playerB, "Clone", 1);
        addCard(Zone.HAND, playerB, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Kilnmouth Dragon");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, "Kilnmouth Dragon");
        setChoice(playerB, "Yes");
        addTarget(playerB, "Phantasmal Dragon");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify

        assertPermanentCount(playerB, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerB, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }

}
