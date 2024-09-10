package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ReleaseToTheWindTest extends CardTestPlayerBase {

    @Test
    public void test_Exile_PermanentCard() {
        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkPlayableAbility("after exile - non owner can't play 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        // owner can play
        checkPlayableAbility("after exile - non owner can't play 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("after exile - owner can play", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_Exile_ModalDoubleFacedCard() {
        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile mdf creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // you can cast mdf, but can't play a land
        checkPlayableAbility("after exile - can play mdf creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("after exile - can't play mdf land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", false);

        // cast mdf again for free
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Akoum Warrior", 1);
    }
}
