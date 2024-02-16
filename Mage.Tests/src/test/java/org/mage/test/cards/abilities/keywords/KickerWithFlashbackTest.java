package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class KickerWithFlashbackTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/5389
    // Burst Lightning {R}
    // Kicker {4} (You may pay an additional {4} as you cast this spell.)
    // Burst Lightning deals 2 damage to any target. If this spell was kicked, it deals 4 damage to that permanent or player instead.
    // Snapcaster Mage {1}{U}
    // Flash
    // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn.
    // The flashback cost is equal to its mana cost. (You may cast that card from your graveyard for its flashback cost. Then exile it.)
    @Test
    public void test_SimpleKicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Burst Lightning");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burst Lightning", playerB);
        setChoice(playerA, true); // use kicker

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_SimpleFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Snapcaster Mage");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);

        // add flashback to bolt
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Lightning Bolt");

        // cast bolt by flashback
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Flashback");
        addTarget(playerA, playerB);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_KickerWithFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Snapcaster Mage");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 4);
        addCard(Zone.GRAVEYARD, playerA, "Burst Lightning", 1);

        // add flashback to burst
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Burst Lightning");

        // cast burst by flashback
        // showAvailableAbilities("after", 1, PhaseStep.BEGIN_COMBAT, playerA);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Flashback");
        setChoice(playerA, true); // use kicker
        addTarget(playerA, playerB);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

}
