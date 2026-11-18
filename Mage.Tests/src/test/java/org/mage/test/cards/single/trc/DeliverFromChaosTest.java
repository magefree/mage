package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class DeliverFromChaosTest extends CardTestPlayerBase {

    @Test
    public void test_ExileCreature() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); // {2}{R}, mana value 3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Gray Ogre"); // exile the 3 drop

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Gray Ogre", 0);
        assertExileCount(playerB, 1);
        assertPermanentCount(playerA, "Borg Token", 3); // mana value of the exiled creature
        assertPowerToughness(playerA, "Borg Token", 1, 1);
        assertType("Borg Token", CardType.ARTIFACT, SubType.BORG);
        assertType("Borg Token", CardType.CREATURE, true);
    }

    @Test
    public void test_ExileArtifactWithZeroManaValue() {
        // Exile target artifact or creature. Create X 1/1 colorless Borg artifact creature tokens,
        // where X is that permanent's mana value.
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lotus Petal"); // {0}, mana value 0

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Lotus Petal"); // exile the 0 drop

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Lotus Petal", 0);
        assertPermanentCount(playerA, "Borg Token", 0); // no tokens for mana value 0
    }

    @Test
    public void test_ExileToken() {
        // a token has no mana cost, so its mana value is 0 and no Borg tokens are created
        addCard(Zone.HAND, playerA, "Dragon Fodder"); // {1}{R}, creates two 1/1 red Goblin creature tokens
        addCard(Zone.HAND, playerA, "Deliver from Chaos"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Fodder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deliver from Chaos", "Goblin Token"); // exile own token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 1); // one of the two goblins was exiled
        assertPermanentCount(playerA, "Borg Token", 0); // no tokens for mana value 0
    }
}
