package org.mage.test.cards.single.unf;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class NameStickerGoblinTest extends CardTestPlayerBase {

    /**
     * "Name Sticker" Goblin {2}{R}
     * When this creature enters the battlefield from anywhere other than a graveyard or exile, if it’s on the battlefield and you control 9 or fewer creatures named “Name Sticker” Goblin, roll a 20-sided die.
     * 1-6 | Add {R}{R}{R}{R}.
     * 7-14 | Add {R}{R}{R}{R}{R}.
     * 15-20 | Add {R}{R}{R}{R}{R}{R}.
     */
    private final static String nsgoblin = "\"Name Sticker\" Goblin";

    @Test
    public void testBasicETB() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, nsgoblin);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",3);

        setDieRollResult(playerA, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nsgoblin, true);
        checkManaPool("Mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
    @Test
    public void testGraveyardETB() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, nsgoblin);
        addCard(Zone.HAND, playerA, "Unearth");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        //No dice roll since it's from graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth", nsgoblin, true);
        checkManaPool("No Mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
    @Test
    public void testExileETB() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nsgoblin);
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        //No dice roll since it's from exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", nsgoblin, true);
        checkManaPool("No mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
    @Test
    public void testNineETB() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nsgoblin, 8);
        addCard(Zone.HAND, playerA, nsgoblin, 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain",3);

        setDieRollResult(playerA, 15);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nsgoblin, true);
        checkManaPool("Mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 6);

        //No dice roll since count > 9
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nsgoblin, true);
        checkManaPool("No extra mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nsgoblin, true);
        checkManaPool("No extra mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 0);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
