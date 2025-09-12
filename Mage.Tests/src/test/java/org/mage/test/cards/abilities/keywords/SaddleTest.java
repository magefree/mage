package org.mage.test.cards.abilities.keywords;

import mage.MageObjectReference;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.watchers.common.SaddledMountWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SaddleTest extends CardTestPlayerBase {

    /**
     * Whenever Quilled Charger attacks while saddled, it gets +1/+2 and gains menace until end of turn.
     * <p>
     * Saddle 2
     */
    private static final String charger = "Quilled Charger";
    private static final String bear = "Grizzly Bears";

    private void assertSaddled(String name, boolean saddled) {
        Permanent permanent = getPermanent(name);
        Assert.assertEquals(
                name + " should " + (saddled ? "" : "not ") + "be saddled",
                saddled, SaddledMountWatcher.hasBeenSaddledThisTurn(new MageObjectReference(permanent.getId(), currentGame), currentGame)
        );
    }

    @Test
    public void testNoSaddle() {
        addCard(Zone.BATTLEFIELD, playerA, charger);

        attack(1, playerA, charger, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(charger, true);
        assertSaddled(charger, false);
        assertAbility(playerA, charger, new MenaceAbility(false), false);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void testSaddle() {
        addCard(Zone.BATTLEFIELD, playerA, charger);
        addCard(Zone.BATTLEFIELD, playerA, bear);

        // turn 1 - saddle and trigger on attack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        setChoice(playerA, bear);
        attack(1, playerA, charger, playerB);
        runCode("on saddle", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            assertTapped(bear, true);
            assertTapped(charger, true);
            assertSaddled(charger, true);
            assertAbility(playerA, charger, new MenaceAbility(false), true);
            assertLife(playerB, 20 - 4 - 1);
        });

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // turn 2 - saddle ends
        assertSaddled(charger, false);
    }

    /**
     * Whenever Rambling Possum attacks while saddled, it gains +1/+2 until end of turn. Then you may return any number
     * of creatures that saddled it this turn to their owner's hand.
     * <p>
     * Saddle 1
     */
    private static final String possum = "Rambling Possum";
    private static final String lion = "Silvercoat Lion";
    private static final String elf = "Arbor Elf";

    @Test
    public void testSaddledThisTurn() {
        addCard(Zone.BATTLEFIELD, playerA, possum);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        setChoice(playerA, bear); // to saddle cost
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        attack(1, playerA, possum, playerB);
        setChoice(playerA, bear); // to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bear, 0);
        assertHandCount(playerA, bear, 1);
        assertTapped(lion, false);
        assertTapped(possum, true);
        assertSaddled(possum, true);
        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void testSaddledThisTurnFail() {
        addCard(Zone.BATTLEFIELD, playerA, possum);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, elf);

        // turn 1 - saddle x2 and trigger on attack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        setChoice(playerA, bear + "^" + lion);
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        attack(1, playerA, possum, playerB);
        setChoice(playerA, elf); // to return (try to choose a wrong creature, so game must not allow to choose it)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        // TODO: test framework must have tools to check targeting (as workaround try to check it by look at test command error)
        try {
            execute();
        } catch (AssertionError e) {
            if (!e.getMessage().contains("Select creatures that saddled it this turn (selected 0)")) {
                Assert.fail("Lion can't be targeted, but catch another error:\n" + e.getMessage());
            }
        }

        assertTapped(bear, true);
        assertTapped(lion, true);
        assertTapped(elf, false);
        assertTapped(possum, true);
        assertSaddled(possum, true);
    }
}
