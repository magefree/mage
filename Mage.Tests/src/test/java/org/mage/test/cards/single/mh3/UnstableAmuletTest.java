package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class UnstableAmuletTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.UnstableAmulet Unstable Amulet} {1}{R}
     * Artifact
     * When Unstable Amulet enters the battlefield, you get {E}{E} (two energy counters).
     * Whenever you cast a spell from anywhere other than your hand, Unstable Amulet deals 1 damage to each opponent.
     * {T}, Pay {E}{E}: Exile the top card of your library. You may play it until you exile another card with Unstable Amulet.
     */
    private static final String amulet = "Unstable Amulet";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 2);
        addCard(Zone.HAND, playerA, amulet);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amulet, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay {E}{E}");

        checkPlayableAbility("No mana to cast Bears", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkExileCount("Bears in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        checkPlayableAbility("Can play Bears", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1); // 1 damage from Amulet trigger
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_ExileAnother_EndPreviousPlay() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, amulet);
        addCard(Zone.HAND, playerA, "Tune the Narrative"); // Draw a card. You get {E}{E}
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.LIBRARY, playerA, "Plains", 2); // draw for turn 3 + Tune the Narrative
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amulet, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay {E}{E}");

        checkPlayableAbility("1: No mana to cast Grizzly Bears", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkExileCount("1: Bears in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        checkPlayableAbility("2: Can play Grizzly Bears", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Tune the Narrative", true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay {E}{E}");
        checkExileCount("3: Grizzly Bears in exile", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("3: Balduvian Bears in exile", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkPlayableAbility("3: Can no longer play Grizzly Bears", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("3: Can play Balduvian Bears", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1); // 1 damage from Amulet trigger
        assertExileCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_Split() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2);
        addCard(Zone.HAND, playerA, amulet);
        addCard(Zone.LIBRARY, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amulet, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay {E}{E}");

        checkPlayableAbility("No mana to cast Fire", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Fire", false);
        checkPlayableAbility("No mana to cast Ice", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ice", false);
        checkExileCount("Fire // Ice in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fire // Ice", 1);

        checkPlayableAbility("Can play Fire", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire", true);
        checkPlayableAbility("Can play Ice", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ice", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", amulet);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 1); // 1 damage from Amulet trigger
        assertHandCount(playerA, 2); // 1 from turn 3 draw step, 1 from Ice
        assertTapped(amulet, true);
        assertTappedCount("Volcanic Island", true, 2);
    }

    @Test
    public void test_Adventure() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 2);
        addCard(Zone.HAND, playerA, amulet);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, amulet, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay {E}{E}");

        checkPlayableAbility("1: No mana to cast Curious Pair", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("1: No mana to cast Treats to Share", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Treats to Share", false);
        checkExileCount("1: Curious Pair in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Curious Pair", 1);

        checkPlayableAbility("2: Can play Curious Pair", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", true);
        checkPlayableAbility("2: Can play Treats to Share", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        checkPlayableAbility("3: No mana to cast Curious Pair", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", false);
        checkPlayableAbility("3: Can't cast Treats to Share (on an adventure)", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);
        checkExileCount("3: Curious Pair in exile (on an adventure)", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Curious Pair", 1);

        checkPlayableAbility("4: Can play Curious Pair", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Curious Pair", true);
        checkPlayableAbility("4: Can't cast Treats to Share (on an adventure)", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Treats to Share", false);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Curious Pair");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 2); // 1 damage per Amulet trigger
        assertTappedCount("Taiga", true, 2);
        assertPermanentCount(playerA, "Food Token", 1);
        assertPermanentCount(playerA, "Curious Pair", 1);
    }
}
