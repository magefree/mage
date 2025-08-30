package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class TheSoulStoneTest extends CardTestPlayerBase {

    /*
    The Soul Stone
    {1}{B}
    Legendary Artifact - Infinity Stone
    Indestructible
    {T}: Add {B}.
    {6}{B}, {T}, Exile a creature you control: Harness The Soul Stone.
    ∞ -- At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.
    */
    private static final String theSoulStone = "The Soul Stone";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Teferi's Time Twist
    {1}{U}
    Instant
    Exile target permanent you control. Return that card to the battlefield under its owner's control at the beginning of the next end step. If it enters the battlefield as a creature, it enters with an additional +1/+1 counter on it.
    */
    private static final String teferisTimeTwist = "Teferi's Time Twist";

    @Test
    public void testTheSoulStone() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, theSoulStone);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{B}, {T}");
        setChoice(playerA, bearCub);
        addTarget(playerA, bearCub);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bearCub, 1);
        assertExileCount(playerA, bearCub, 1);
    }

    @Test
    public void testBlinkSoulStone() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, theSoulStone);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.HAND, playerA, teferisTimeTwist);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}{B}, {T}");
        setChoice(playerA, bearCub);

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, teferisTimeTwist, theSoulStone);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, bearCub, 1);
    }
}