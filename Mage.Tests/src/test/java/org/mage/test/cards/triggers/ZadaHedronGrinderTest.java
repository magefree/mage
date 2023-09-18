package org.mage.test.cards.triggers;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ZadaHedronGrinderTest extends CardTestPlayerBase {

    /**
     * Playing Zada edh, strive cards such as Rouse the Mob do not copy when
     * targeting only Zada.
     */
    @Test
    public void testWithStriveSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // Strive — Rouse the Mob costs {2}{R} more to cast for each target beyond the first.
        // Any number of target creatures each get +2/+0 and gain trample until end of turn.
        addCard(Zone.HAND, playerA, "Rouse the Mob", 1); // Instant - {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rouse the Mob", "Zada, Hedron Grinder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Rouse the Mob", 1);

        assertPowerToughness(playerA, "Zada, Hedron Grinder", 5, 3);
        assertAbility(playerA, "Zada, Hedron Grinder", TrampleAbility.getInstance(), true);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 2);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
    }

    @Test
    public void testTargetsByTestPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for
        // each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        // Put a +1/+1 counter on target creature. That creature gains reach until end of turn.
        addCard(Zone.HAND, playerA, "Arbor Armament", 1); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // cast
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // skip stack order
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Armament", "Zada, Hedron Grinder");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Arbor Armament", 1);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 3 + 1, 3 + 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 1, 2 + 1);
        assertPowerToughness(playerA, "Balduvian Bears", 2 + 1, 2 + 1);
    }

    @Test
    public void testTargetsByAI() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Whenever you cast an instant or sorcery spell that targets only Zada, Hedron Grinder, copy that spell for
        // each other creature you control that the spell could target. Each copy targets a different one of those creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Zada, Hedron Grinder", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        // Put a +1/+1 counter on target creature. That creature gains reach until end of turn.
        addCard(Zone.HAND, playerA, "Arbor Armament", 1); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Armament", "Zada, Hedron Grinder");
        //addTarget(playerA, "Balduvian Bears^Silvercoat Lion");

        //setStrictChooseMode(true); // no strict mode for AI
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Arbor Armament", 1);
        assertPowerToughness(playerA, "Zada, Hedron Grinder", 3 + 1, 3 + 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 1, 2 + 1);
        assertPowerToughness(playerA, "Balduvian Bears", 2 + 1, 2 + 1);
    }

}
