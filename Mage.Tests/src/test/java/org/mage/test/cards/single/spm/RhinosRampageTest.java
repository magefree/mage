package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class RhinosRampageTest extends CardTestPlayerBase {

    /*
    Rhino's Rampage
    {R/G}
    Sorcery
    Target creature you control gets +1/+0 until end of turn. It fights target creature an opponent controls.
    When excess damage is dealt to the creature an opponent controls this way, destroy up to one target noncreature artifact with mana value 3 or less.
    */
    private static final String rhinosRampage = "Rhino's Rampage";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard
    
    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";

    /*
    Tormod's Crypt
    {0}
    Artifact
    {T}, Sacrifice Tormod's Crypt: Exile all cards from target player's graveyard.
    */
    private static final String tormodsCrypt = "Tormod's Crypt";

    @Test
    public void testRhinosRampageExcess() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhinosRampage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerB, tormodsCrypt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhinosRampage);
        addTarget(playerA, bearCub);
        addTarget(playerA, fugitiveWizard);
        addTarget(playerA, tormodsCrypt);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, fugitiveWizard, 1);
        assertGraveyardCount(playerB, tormodsCrypt, 1);
    }

    @Test
    public void testRhinosRampageNoExcess() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rhinosRampage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, tormodsCrypt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rhinosRampage);
        addTarget(playerA, fugitiveWizard);
        addTarget(playerA, bearCub);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, fugitiveWizard, 1);
        assertGraveyardCount(playerB, bearCub, 1);
        assertPermanentCount(playerB, tormodsCrypt, 1);
    }
}