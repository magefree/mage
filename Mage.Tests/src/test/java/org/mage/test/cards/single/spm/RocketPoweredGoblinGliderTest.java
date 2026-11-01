package org.mage.test.cards.single.spm;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class RocketPoweredGoblinGliderTest extends CardTestPlayerBase {

    /*
    Rocket-Powered Goblin Glider
    {3}
    Artifact - Equipment
    When this Equipment enters, if it was cast from your graveyard, attach it to target creature you control.
    Equipped creature gets +2/+0 and has flying and haste.
    Equip {2}
    Mayhem {2}
    */
    private static final String glider = "Rocket-Powered Goblin Glider";

    /*
    Putrid Imp
    {B}
    Creature - Zombie Imp
    Discard a card: Putrid Imp gains flying until end of turn.
    Threshold - As long as seven or more cards are in your graveyard, Putrid Imp gets +1/+1 and can't block.
    1/1
    */
    private static final String imp = "Putrid Imp";

    private static final String bears = "Grizzly Bears";

    @Test
    public void testCastWithMayhemAttaches() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.HAND, playerA, glider);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, glider);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, glider + " with Mayhem");
        addTarget(playerA, bears);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, glider, bears, true);
        assertPowerToughness(playerA, bears, 2 + 2, 2);
        assertAbility(playerA, bears, FlyingAbility.getInstance(), true);
    }

    @Test
    public void testCastFromHandDoesNotAttach() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.HAND, playerA, glider);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glider);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, glider, 1);
        assertAttachedTo(playerA, glider, bears, false);
        assertPowerToughness(playerA, bears, 2, 2);
    }
}
