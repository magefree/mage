package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests the {@link mage.abilities.common.DiesOneOrMoreTriggeredAbility} batching.
 *
 * @author Susucr, JayDi85
 */
public class VengefulTownsfolkTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VengefulTownsfolk Vengeful Townsfolk}
     * Creature — Human Citizen
     * Whenever one or more other creatures you control die, put a +1/+1 counter on Vengeful Townsfolk.
     * 3/3
     */
    private static final String townsfolk = "Vengeful Townsfolk";

    // Choose up to one creature. Destroy the rest.
    private static final String duneblast = "Duneblast";

    @Test
    public void testOnTokens() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        addCard(Zone.HAND, playerA, "Raise the Alarm", 1); // 2 1/1 tokens
        addCard(Zone.HAND, playerB, duneblast, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        setChoice(playerB, townsfolk); // do not destroy townsfolk
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, duneblast);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, duneblast, 1);
        assertPermanentCount(playerA, 1 + 2);
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 1, 3 + 1);
    }

    @Test
    public void testOnNonToken() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);
        addCard(Zone.HAND, playerB, duneblast, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        setChoice(playerB, townsfolk); // do not destroy townsfolk
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, duneblast);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, duneblast, 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 3);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 1, 3 + 1);
    }

    @Test
    public void testTwoSeparateDestroy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.HAND, playerB, "Doom Blade", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Doom Blade", 2);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 2, 3 + 2);
    }

    @Test
    public void testControllerMatters() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.HAND, playerB, "Doom Blade", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Doom Blade", 2);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 1, 3 + 1);
    }

    @Test
    public void testDoubleSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerB, "Barter in Blood", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Barter in Blood");
        // sacrificing both, those are moved to graveyard at same time
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerA, "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Barter in Blood", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 1, 3 + 1);
    }

    @Test
    public void testReplacedDieEvent_Single() {
        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        //
        // {T}, Sacrifice up to three permanents: If there were three or more card types among the sacrificed permanents, each opponent loses 3 life, you gain 3 life, and you draw three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Baba Lysaga, Night Witch");
        addCard(Zone.BATTLEFIELD, playerA, "Angel of the God-Pharaoh"); // creature with cycle (will be exiled instead die)
        //
        // If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice up to three");
        setChoice(playerA, "Angel of the God-Pharaoh"); // sac cost
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Angel of the God-Pharaoh", 1); // exiled instead die
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3, 3); // no triggers
    }

    @Test
    public void testReplacedDieEvent_Multiple() {
        addCard(Zone.BATTLEFIELD, playerA, townsfolk);
        //
        // {T}, Sacrifice up to three permanents: If there were three or more card types among the sacrificed permanents, each opponent loses 3 life, you gain 3 life, and you draw three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Baba Lysaga, Night Witch");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 10); // creature
        addCard(Zone.BATTLEFIELD, playerA, "Angel of the God-Pharaoh"); // creature with cycle (will be exiled instead die)
        //
        // If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice up to three");
        setChoice(playerA, "Grizzly Bears^Angel of the God-Pharaoh"); // sac cost
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Angel of the God-Pharaoh", 1); // exiled instead die
        assertPermanentCount(playerA, townsfolk, 1);
        assertPowerToughness(playerA, townsfolk, 3 + 1, 3 + 1);
    }
}
