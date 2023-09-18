
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
    public void test_Amplify_OneCard() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, true);
        addTarget(playerA, "Kilnmouth Dragon");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }
    /**
     * Tests if +1/+1 counters are added
     */
    @Test
    public void test_Amplify_TwoCards() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.HAND, playerA, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, true);
        addTarget(playerA, "Kilnmouth Dragon^Phantasmal Dragon");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 11,11); // 5 + 6 from Amplify
    }
    /**
     * Tests that it works for Clone
     */
    @Test
    public void test_Amplify_WithClone() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        addCard(Zone.HAND, playerB, "Clone", 1);
        addCard(Zone.HAND, playerB, "Phantasmal Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kilnmouth Dragon");
        setChoice(playerA, true);
        addTarget(playerA, "Kilnmouth Dragon");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true); // use clone
        setChoice(playerB, "Kilnmouth Dragon"); // what clone
        setChoice(playerB, true); // use amplify
        addTarget(playerB, "Phantasmal Dragon"); // reveal

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify

        assertPermanentCount(playerB, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerB, "Kilnmouth Dragon", 8,8); // 5 + 3 from Amplify
    }

    /**
     * Tests if a creature with Amplify is able to select itself if it's put
     * onto the battlefield from hand (without casting).
     * https://github.com/magefree/mage/issues/6776
     */
    @Test
    public void test_Amplify_PutOntoBattlefieldFromHand() {
        // Creature — Dragon - Dragon   5/5  {5}{R}{R}
        // Amplify 3 (As this creature enters the battlefield, put three +1/+1 counters on it for each Dragon card you reveal in your hand.)
        // {T}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to any target
        addCard(Zone.HAND, playerA, "Kilnmouth Dragon", 1);
        //
        // {4}{R}
        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Through the Breach", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Through the Breach");
        setChoice(playerA, "Kilnmouth Dragon");
        //setChoice(playerA, true);  // no reveal request cause no cards to show
        //addTarget(playerA, "Kilnmouth Dragon");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kilnmouth Dragon", 1);
        assertPowerToughness(playerA, "Kilnmouth Dragon", 5,5); // 5 + 0 from Amplify
    }
}
