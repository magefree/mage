package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class RodOfAbsorptionTest extends CardTestPlayerBase {
    private static final String rod = "Rod of Absorption";

    // Whenever a player casts an instant or sorcery spell,
    // exile it instead of putting it into a graveyard as it resolves.
    /**
     * Owner and non-owner of Rod cast instant/sorcery. They both resolve, and should be exiled.
     */
    @Test
    public void testExilingResolvedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, rod, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Taste of Blood", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Taste of Blood", playerA);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);

        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
    }

    /**
     * Instant & Sorcery that are countered should NOT be exiled.
     */
    @Test
    public void testNotExilingCounteredSpell() {
        addCard(Zone.BATTLEFIELD, playerA, rod, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.HAND, playerA, "Counterspell", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Taste of Blood", 1);
        addCard(Zone.HAND, playerB, "Arcane Denial", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Arcane Denial", "Shock", "Shock");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Taste of Blood", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Taste of Blood", "Taste of Blood");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // The damage spells don't get exiled by the rod, but the counter spells do.
        assertExileCount(playerA, 1);
        assertExileCount(playerA, "Counterspell", 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerB, "Arcane Denial", 1);

        // The damage spells should be in the graveyard.
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Taste of Blood", 1);
    }

    /**
     * Instant & Sorcery are cast but do not resolve.
     */
    @Test
    public void testNotExilingUnresolvedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, rod, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.HAND, playerA, "Arcane Denial", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        // 1. Cast Shock
        // 2. Cast Counterspell  -> Shock
        // 3. Cast Arcane Denial -> Shock
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Shock", "Shock");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Denial", "Shock", "Counterspell");

        // Shock:         Graveyard since it was countered
        // Counterspell:  Graveyard since it did not resolve
        // Arcane Denial: Exile since it resolved

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // The damage spells don't get exiled by the rod, but the counter spells do.
        assertExileCount(playerA, 1);
        assertExileCount(playerA, "Arcane Denial", 1);
        assertExileCount(playerB, 0);

        // The damage spells should be in the graveyard.
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Shock", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Counterspell", 1);
    }

    /**
     * Instant/sorcery that does NOT go to the graveyard. (e.g. Flashback)
     */
    // TODO

    /**
     * A spell causes an instant/sorcery to not go to the graveyard.
     */
    // TODO

    /**
     * If there are multiple Rods on the field, the spell's controller picks which one exiles it.
     */
    // TODO: add check here for the specifc exile zone, add in after Share the Spoils is merged in
    // TODO
}
