package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class CathedralMembraneTest extends CardTestPlayerBase {

    // see issue #13774

    private static final String cathedralMembrane = "Cathedral Membrane"; // 0/6
    // Defender
    // When this creature dies during combat, it deals 6 damage to each creature it blocked this combat.

    private static final String wurm = "Autochthon Wurm"; // 9/14 convoke trample
    private static final String gigantosaurus = "Gigantosaurus"; // 10/10

    private static final String moraug = "Moraug, Fury of Akoum"; // 6/6
    // Each creature you control gets +1/+0 for each time it has attacked this turn.
    // Landfall — Whenever a land you control enters, if it's your main phase,
    // there's an additional combat phase after this phase. At the beginning of that combat, untap all creatures you control.

    private static final String recovery = "Miraculous Recovery"; // 4W instant
    // Return target creature card from your graveyard to the battlefield. Put a +1/+1 counter on it.

    @Test
    public void testMembraneTrigger() {
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, gigantosaurus);
        addCard(Zone.BATTLEFIELD, playerA, moraug);
        addCard(Zone.HAND, playerB, cathedralMembrane);
        addCard(Zone.HAND, playerB, recovery);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, cathedralMembrane);
        setChoice(playerB, true); // pay life

        attack(3, playerA, wurm, playerB);

        block(3, playerB, cathedralMembrane, wurm);
        setChoiceAmount(playerA, 6); // assign trample damage

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTapped(wurm, true);
        assertTapped(gigantosaurus, false);
        assertTapped(moraug, false);
        assertGraveyardCount(playerB, cathedralMembrane, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 4);
        assertDamageReceived(playerA, wurm, 6);
        assertDamageReceived(playerA, gigantosaurus, 0);
        assertDamageReceived(playerA, moraug, 0);
        
    }

    @Test
    public void testMembraneTriggerAgain() {
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, gigantosaurus);
        addCard(Zone.BATTLEFIELD, playerA, moraug);
        addCard(Zone.HAND, playerB, cathedralMembrane);
        addCard(Zone.HAND, playerB, recovery);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, cathedralMembrane);
        setChoice(playerB, true); // pay life

        attack(3, playerA, wurm, playerB);

        block(3, playerB, cathedralMembrane, wurm);
        setChoiceAmount(playerA, 6); // assign trample damage

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute(); // separate execute needed to separate commands from second combat phase

        playLand(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mountain");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, recovery, cathedralMembrane);

        attack(3, playerA, wurm, playerB);
        attack(3, playerA, gigantosaurus, playerB);

        block(3, playerB, cathedralMembrane, gigantosaurus);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTapped(wurm, true);
        assertTapped(gigantosaurus, true);
        assertTapped(moraug, false);
        assertGraveyardCount(playerB, cathedralMembrane, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 4 - 11);
        assertDamageReceived(playerA, wurm, 6);
        assertDamageReceived(playerA, gigantosaurus, 1 + 6);
        assertDamageReceived(playerA, moraug, 0);

    }

}
