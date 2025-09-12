package org.mage.test.cards.single.dsk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class UnidentifiedHovershipTest extends CardTestPlayerBase {

    /*
    Unidentified Hovership
    {1}{W}{W}
    Artifact - Vehicle
    Flying
    When this Vehicle enters, exile up to one target creature with toughness 5 or less.
    When this Vehicle leaves the battlefield, the exiled card's owner manifests dread.
    Crew 1
    2/2
    */
    private static final String unidentifiedHovership = "Unidentified Hovership";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Naturalize
    {1}{G}
    Instant
    Destroy target artifact or enchantment.
    */
    private static final String naturalize = "Naturalize";

    @Test
    public void testUnidentifiedHovership() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, unidentifiedHovership);
        addCard(Zone.HAND, playerB, naturalize);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, unidentifiedHovership);
        addTarget(playerA, bearCub);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, naturalize, unidentifiedHovership);
        setChoice(playerB, "Mountain");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 2 + 1); // forests + face down
        assertGraveyardCount(playerA, unidentifiedHovership, 1);
        assertGraveyardCount(playerB, 1 + 1); // naturalize + manifest
        assertExileCount(playerB, bearCub, 1);
    }
}