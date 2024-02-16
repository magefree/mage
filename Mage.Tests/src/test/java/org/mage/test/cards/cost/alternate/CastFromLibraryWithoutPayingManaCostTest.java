package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Quercitron
 */
public class CastFromLibraryWithoutPayingManaCostTest extends CardTestPlayerBase {

    /**
     * Test for issue https://github.com/magefree/mage/issues/5189
     *
     * I've cast Utter End via Sunforger, then later on I've shuffled Utter End from my GY back into my library
     * via Elixir of Immortality. I cast Utter End again via Sunforger, but this time I don't get prompted to select
     * a target - it keeps the old target, a permanent that's already in exile, and thus the recast Utter End fizzles.
     */
    @Test
    public void testCastCardFromLibraryTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Storm Crow");
        // {R}{W}, Unattach Sunforger: Search your library for a red or white instant card with
        // converted mana cost 4 or less and cast that card without paying its mana cost. Then shuffle your library.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Sunforger");
        // {2}, {T}: You gain 5 life. Shuffle Elixir of Immortality and your graveyard into their owner's library.
        addCard(Zone.BATTLEFIELD, playerA, "Elixir of Immortality");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        // Exile target nonland permanent.
        addCard(Zone.LIBRARY, playerA, "Utter End");

        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre");
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        // Equip Sunforger to Storm Crow.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Storm Crow");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Unattach Sunforger to cast Utter End from library targeting Gray Ogre.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}{W}, Unattach");
        addTarget(playerA, "Utter End");
        addTarget(playerA, "Gray Ogre");

        // Sacrifice Elixir of Immortality to shuffle Utter End from graveyard to library.
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{2}, {T}: You gain 5 life");

        // Equip Sunforger to Storm Crow again.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Storm Crow");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        // Unattach Sunforger to cast Utter End from library targeting Hill Giant.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}{W}, Unattach");
        addTarget(playerA, "Utter End");
        addTarget(playerA, "Hill Giant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Check that Elixir of Immortality was sacrificed.
        assertPermanentCount(playerA, "Elixir of Immortality", 0);

        // Check that Gray Ogre was exiled.
        assertPermanentCount(playerB, "Gray Ogre", 0);
        // Check that Hill Giant was exiled.
        assertPermanentCount(playerB, "Hill Giant", 0);

        // Check that Utter End is in the graveyard.
        assertGraveyardCount(playerA, "Utter End", 1);
    }

    @Test
    public void testCastCardFromHandAndThenFromLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, "Storm Crow");
        // {R}{W}, Unattach Sunforger: Search your library for a red or white instant card with
        // converted mana cost 4 or less and cast that card without paying its mana cost. Then shuffle your library.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Sunforger");
        // {2}, {T}: You gain 5 life. Shuffle Elixir of Immortality and your graveyard into their owner's library.
        addCard(Zone.BATTLEFIELD, playerA, "Elixir of Immortality");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        // Instant
        // Exile target nonland permanent.
        addCard(Zone.HAND, playerA, "Utter End");

        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre");
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");

        // Cast Utter End from hand targeting Gray Ogre.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Utter End", "Gray Ogre");

        // Sacrifice Elixir of Immortality to shuffle Utter End from graveyard to library.
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{2}, {T}: You gain 5 life");

        // Equip Sunforger to Storm Crow.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Storm Crow");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        // Unattach Sunforger to cast Utter End from library targeting Hill Giant.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}{W}, Unattach");
        addTarget(playerA, "Utter End");
        addTarget(playerA, "Hill Giant");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Check that Elixir of Immortality was sacrificed.
        assertPermanentCount(playerA, "Elixir of Immortality", 0);

        // Check that Gray Ogre was exiled.
        assertPermanentCount(playerB, "Gray Ogre", 0);
        // Check that Hill Giant was exiled.
        assertPermanentCount(playerB, "Hill Giant", 0);

        // Check that Utter End is in the graveyard.
        assertGraveyardCount(playerA, "Utter End", 1);
    }
}
