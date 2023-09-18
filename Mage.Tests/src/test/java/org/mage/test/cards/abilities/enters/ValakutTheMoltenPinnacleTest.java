package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ValakutTheMoltenPinnacleTest extends CardTestPlayerBase {

    /**
     * Valakut, the Molten Pinnacle Land Valakut, the Molten Pinnacle enters the
     * battlefield tapped. Whenever a Mountain enters the battlefield under your
     * control, if you control at least five other Mountains, you may have
     * Valakut, the Molten Pinnacle deal 3 damage to any target. {T}: Add {R}.
     */
    @Test
    public void onlyFourMountainsNoDamage() {

        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertPermanentCount(playerA, "Mountain", 5);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void fiveMountainsDamageToPlayerB() {

        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain"); // 3 damage because already 5 Mountains on battlefield

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    // Scapeshift {2}{G}{G}
    // Sorcery
    // Sacrifice any number of lands. Search your library for that many land cards, put them onto the battlefield tapped, then shuffle your library.
    @Test
    public void sixEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 6);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18

    }

    @Test
    public void sixAndValakutEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest^Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 7);
        assertPermanentCount(playerA, "Mountain", 6);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18

    }

    // using some shock lands instead of only mountains
    @Test
    public void sixWithShocklandsAndValakutEnterWithScapeshiftDamageToPlayerB() {

        addCard(Zone.LIBRARY, playerA, "Mountain", 3);
        addCard(Zone.LIBRARY, playerA, "Stomping Ground", 3);
        addCard(Zone.LIBRARY, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Scapeshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scapeshift");
        addTarget(playerA, "Forest^Forest^Forest^Forest^Forest^Forest^Forest");
        setChoice(playerA, false); // Stomping Ground can be tapped
        setChoice(playerA, false); // Stomping Ground can be tapped
        setChoice(playerA, false); // Stomping Ground can be tapped
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Valakut, the Molten Pinnacle", 1);
        assertGraveyardCount(playerA, "Forest", 7);
        assertPermanentCount(playerA, "Mountain", 3);
        assertPermanentCount(playerA, "Stomping Ground", 3);

        assertLife(playerA, 20);
        assertLife(playerB, 2); // 6 * 3 damage = 18

    }

    /**
     * Some lands aren't triggering Valakut, the Molten Pinnacle with Prismatic
     * Omen and 6+ lands in play. So far I've noticed that Misty Rainforest and
     * basic Island did not trigger Valakut, but an additional copy of Valakut
     * did.
     */
    @Test
    public void withPrismaticOmen() {
        // Valakut, the Molten Pinnacle enters the battlefield tapped.
        // Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains,
        // you may have Valakut, the Molten Pinnacle deal 3 damage to any target.
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Valakut, the Molten Pinnacle");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.HAND, playerA, "Forest", 1);

        // Lands you control are every basic land type in addition to their other types.
        addCard(Zone.BATTLEFIELD, playerA, "Prismatic Omen");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

}
