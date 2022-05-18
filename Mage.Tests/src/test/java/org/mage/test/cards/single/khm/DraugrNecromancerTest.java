package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DraugrNecromancerTest extends CardTestPlayerBase {

    private static final String necromancer = "Draugr Necromancer";
    private static final String bolt = "Lightning Bolt";
    private static final String bear = "Grizzly Bears";
    private static final String murder = "Murder";
    private static final String pair = "Curious Pair";
    private static final String birgi = "Birgi, God of Storytelling";
    private static final String harnfel = "Harnfel, Horn of Bounty";

    @Test
    public void testExilesWithCounter() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, bear, 1);
        assertCounterOnExiledCardCount(bear, CounterType.ICE, 1);
    }

    @Test
    public void testCastFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, bear, 0);
        assertPermanentCount(playerA, bear, 1);
    }

    @Test
    public void testCastFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, bear, 0);
        assertPermanentCount(playerA, bear, 1);
    }

    @Test
    public void testCastFromExileWithoutSnow() {
        addCard(Zone.HAND, playerA, bolt);

        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        addCard(Zone.BATTLEFIELD, playerB, bear);

        // Kill playerB's Bear to exile it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        // Make sure it can't be cast without the snow land needed for the right colors
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStopAt(1, PhaseStep.END_TURN);

        try { // TODO: The bears are labelled as playable for some reason. Need the try-catch
            execute();
            assertAllCommandsUsed();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Needed error about PlayerA having too many actions, but got:\n" + e.getMessage());
            }
        }

        assertExileCount(playerB, bear, 1);
        assertCounterOnExiledCardCount(bear, CounterType.ICE, 1);
        assertPermanentCount(playerA, bear, 0);
    }

    @Test
    public void testCastAdventureCreatureFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, pair);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, pair);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, pair);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, pair, 0);
        assertPermanentCount(playerA, pair, 1);
    }

    @Test
    public void testCastAdventureSpellFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, pair);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, pair);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, pair, 1);
        assertGraveyardCount(playerB, pair, 0);
        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void testCastAdventureCreatureFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp", 2);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, pair);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, pair);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, pair);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, pair, 0);
        assertPermanentCount(playerA, pair, 1);
    }

    @Test
    public void testCastAdventureSpellFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, pair);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, pair);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Treats to Share");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, pair, 1);
        assertGraveyardCount(playerB, pair, 0);
        assertPermanentCount(playerA, "Food Token", 1);
    }

    @Test
    public void testCastMDFCFrontFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6); // {B} or {R}
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.HAND, playerB, birgi);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, birgi);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, murder, birgi); // {1}{B}{B}

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, birgi); // {2}{R}

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, birgi, 1);
        assertGraveyardCount(playerB, birgi, 0);
        assertExileCount(playerB, birgi, 0);
    }

    @Test
    public void testCastMDFCBackFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 8);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.HAND, playerB, birgi);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, birgi);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, murder, birgi);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, harnfel);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, harnfel, 1);
        assertPermanentCount(playerA, birgi, 0);
        assertGraveyardCount(playerB, birgi, 0);
        assertExileCount(playerB, birgi, 0);
    }

    @Test
    public void testCastMDFCFrontFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp", 6);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.HAND, playerB, birgi);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, birgi);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, murder, birgi);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, birgi);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, birgi, 1);
        assertGraveyardCount(playerB, birgi, 0);
        assertExileCount(playerB, birgi, 0);
    }

    @Test
    public void testCastMDFCBackFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp", 8);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.HAND, playerB, birgi);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, birgi);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, murder, birgi);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, harnfel);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, harnfel, 1);
        assertPermanentCount(playerA, birgi, 0);
        assertGraveyardCount(playerB, birgi, 0);
        assertExileCount(playerB, birgi, 0);
    }
}
