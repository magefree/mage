package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class LadyLokiAgentOfChaosTest extends CardTestPlayerBase {

    /**
     * Lady Loki, Agent of Chaos {5}{R}
     * Legendary Creature - God Sorcerer Villain 5/5
     * Whenever you cast your first instant, sorcery, or Villain spell each turn, exile it, then exile cards
     * from the top of your library until you exile a nonland card. Lady Loki deals damage to each opponent
     * equal to the difference between that spell's mana value and that nonland card's mana value.
     * You may cast that card without paying its mana cost.
     */
    private static final String loki = "Lady Loki, Agent of Chaos";

    private static final String bolt = "Lightning Bolt"; // {R} instant
    private static final String divination = "Divination"; // {2}{U} sorcery
    private static final String blaze = "Blaze"; // {X}{R} sorcery
    private static final String assailant = "Armed Assailant"; // {2}{B} Human Villain
    private static final String bears = "Grizzly Bears"; // {1}{G} Bear
    private static final String giant = "Hill Giant"; // {3}{R} 3/3
    private static final String memnite = "Memnite"; // {0} 1/1

    @Test
    public void test_Instant_LowerManaValue_CastFree() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, giant);
        addCard(Zone.LIBRARY, playerA, "Mountain", 2); // on top
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        setChoice(playerA, true); // cast Hill Giant

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Bolt never resolves, 4 - 1 = 3 damage from Lady Loki
        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, bolt, 1);
        assertExileCount(playerA, "Mountain", 2);
        assertPermanentCount(playerA, giant, 1);
    }

    @Test
    public void test_Sorcery_HigherManaValue_DeclineCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, divination);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, divination);
        setChoice(playerA, false); // don't cast Memnite

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 3 - 0 = 3 damage, Divination doesn't draw
        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, 0);
        assertExileCount(playerA, divination, 1);
        assertExileCount(playerA, memnite, 1);
        assertPermanentCount(playerA, memnite, 0);
    }

    @Test
    public void test_ManaValueOfSpellWithX() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, blaze);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blaze, playerB);
        setChoice(playerA, "X=3");
        setChoice(playerA, false); // don't cast Memnite

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // mana value on the stack is 4 (X = 3), not 1
        assertLife(playerB, 20 - 4);
        assertExileCount(playerA, blaze, 1);
    }

    @Test
    public void test_SameManaValue_NoDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        setChoice(playerA, true); // cast the exiled Lightning Bolt
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 1 - 1 = 0 damage from Lady Loki, 3 from the free Bolt
        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, bolt, 1);
        assertGraveyardCount(playerA, bolt, 1);
    }

    @Test
    public void test_VillainCreatureSpell_AfterNonVillainCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.HAND, playerA, assailant);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, memnite);

        // a non-Villain creature spell doesn't count
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, assailant);
        setChoice(playerA, true); // cast Memnite

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertPermanentCount(playerA, assailant, 0);
        assertExileCount(playerA, assailant, 1);
        assertPermanentCount(playerA, memnite, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_SecondSpellEachTurn_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, bolt, 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, memnite);
        addCard(Zone.LIBRARY, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        setChoice(playerA, false); // don't cast Memnite
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // first Bolt exiled (1 - 0 = 1 damage), second Bolt resolves
        assertLife(playerB, 20 - 1 - 3);
        assertExileCount(playerA, bolt, 1);
        assertGraveyardCount(playerA, bolt, 1);
        assertExileCount(playerA, memnite, 1);
        assertLibraryCount(playerA, memnite, 1);
    }

    @Test
    public void test_FirstSpellCastBeforeLokiEntered_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, loki);
        addCard(Zone.HAND, playerA, bolt, 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, loki);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, loki, 1);
        assertLife(playerB, 20 - 3 - 3);
        assertGraveyardCount(playerA, bolt, 2);
        assertLibraryCount(playerA, memnite, 1);
    }

    @Test
    public void test_NoNonlandCard_NoDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, loki);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertExileCount(playerA, bolt, 1);
        assertExileCount(playerA, "Mountain", 2);
        assertLibraryCount(playerA, 0);
    }
}
