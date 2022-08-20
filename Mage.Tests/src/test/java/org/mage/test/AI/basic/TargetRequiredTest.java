package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TargetRequiredTest extends CardTestPlayerBase {

    /*
        Redcap must sacrifice target land -- it's required target, but AI don't known about that
        (target can be copied as new target in effect's code)
     */

    @Test
    public void test_chooseBadTargetOnSacrifice_WithTargets_User() {
        // Redcap Melee deals 4 damage to target creature or planeswalker. If a nonred permanent is dealt damage this way, you sacrifice a land.
        addCard(Zone.HAND, playerA, "Redcap Melee", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Redcap Melee", "Silvercoat Lion");
        addTarget(playerA, "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Redcap Melee", 1);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Mountain", 3 - 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_chooseBadTargetOnSacrifice_WithTargets_AI() {
        // Redcap Melee deals 4 damage to target creature or planeswalker. If a nonred permanent is dealt damage this way, you sacrifice a land.
        addCard(Zone.HAND, playerA, "Redcap Melee", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Redcap Melee", "Silvercoat Lion");
        //addTarget(playerA, "Mountain"); AI must select targets

        //setStrictChooseMode(true); AI must select targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Redcap Melee", 1);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Mountain", 3 - 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_chooseBadTargetOnSacrifice_WithoutTargets_User() {
        // Redcap Melee deals 4 damage to target creature or planeswalker. If a nonred permanent is dealt damage this way, you sacrifice a land.
        addCard(Zone.HAND, playerA, "Redcap Melee", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Atarka Monument", 1); // {T}: Add {R} or {G}.
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Redcap Melee", "Silvercoat Lion");
        //addTarget(playerA, "Mountain"); no lands to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Redcap Melee", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_chooseBadTargetOnSacrifice_WithoutTargets_AI() {
        // Redcap Melee deals 4 damage to target creature or planeswalker. If a nonred permanent is dealt damage this way, you sacrifice a land.
        addCard(Zone.HAND, playerA, "Redcap Melee", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Atarka Monument", 1); // {T}: Add {R} or {G}.
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Redcap Melee", "Silvercoat Lion");
        //addTarget(playerA, "Mountain"); no lands to sacrifice

        //setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Redcap Melee", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }
}
