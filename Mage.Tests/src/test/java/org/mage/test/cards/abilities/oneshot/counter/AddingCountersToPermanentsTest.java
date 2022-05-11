package org.mage.test.cards.abilities.oneshot.counter;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class AddingCountersToPermanentsTest extends CardTestPlayerBase {

    @Test
    public void testBlackSunsZenith() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Witch's Familiar", 1);

        // Put X -1/-1 counters on each creature. Shuffle Black Sun's Zenith into its owner's library.
        addCard(Zone.HAND, playerA, "Black Sun's Zenith", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Witch's Familiar", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Sun's Zenith");
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertPermanentCount(playerA, "Witch's Familiar", 1);
        assertPowerToughness(playerA, "Witch's Familiar", 0, 1);

        assertPermanentCount(playerB, "Witch's Familiar", 1);
        assertPowerToughness(playerB, "Witch's Familiar", 0, 1);

    }

    /**
     * Fairgrounds Trumpeter does not get a counter at the end of turn when
     * Woodland Wanderer enters the battlefield
     */
    @Test
    public void testFairgroundsTrumpeter() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // At the beginning of each end step, if a +1/+1 counter was placed on a permanent under your control this turn, put a +1/+1 counter on Fairgrounds Trumpeter.
        addCard(Zone.HAND, playerA, "Fairgrounds Trumpeter", 1); // Creature 2/2  {2}{G}
        // Vigilance, trample
        // <i>Converge</i> &mdash; Woodland Wanderer enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        addCard(Zone.HAND, playerA, "Woodland Wanderer", 1); // Creature 2/2 {3}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Woodland Wanderer");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fairgrounds Trumpeter");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Woodland Wanderer", 3, 3);
        assertPowerToughness(playerA, "Fairgrounds Trumpeter", 3, 3);

    }

    @Test
    public void testSoulstingerNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        // When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger.
        addCard(Zone.HAND, playerA, "Soulstinger", 1); // Creature 4/5  {3}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        // Turn to Slag deals 5 damage to target creature. Destroy all Equipment attached to that creature.
        addCard(Zone.HAND, playerB, "Turn to Slag", 1); // Sorcery  {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soulstinger");
        addTarget(playerA, "Soulstinger");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Slag", "Soulstinger");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Soulstinger", 1);

        assertGraveyardCount(playerB, "Turn to Slag", 1);

        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);

    }

    /**
     * Soulstinger died and gave a -1/-1 counter to an opponent's creature.
     * Soulstinger had no -1/-1 counters on it, but the opponent's creature did,
     * so maybe checking quantity of counters on the wrong creature?
     */
    @Test
    public void testSoulstinger() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        // When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger.
        addCard(Zone.HAND, playerA, "Soulstinger", 1); // Creature 4/5  {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        // Turn to Slag deals 5 damage to target creature. Destroy all Equipment attached to that creature.
        addCard(Zone.HAND, playerB, "Turn to Slag", 1); // Sorcery  {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soulstinger");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Slag", "Soulstinger");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Soulstinger", 1);

        assertGraveyardCount(playerB, "Turn to Slag", 1);

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

    }

    /**
     * Considering rule 121.6 Persist is not working with Hapatra, Vizier of
     * Poisons and Obelisk Spider. Hapatra and Obelisk Spider do not trigger
     * their second abilities when Kitchen Finks returns with -1/-1 counter.
     */
    @Test
    public void HapatraVizierOfPoisons() {
        // Whenever Hapatra, Vizier of Poisons deals combat damage to a player, you may put a -1/-1 counter on target creature.
        // Whenever you put one or more -1/-1 counters on a creature, create a 1/1 green Snake creature token with deathtouch.
        addCard(Zone.BATTLEFIELD, playerA, "Hapatra, Vizier of Poisons", 1);
        //
        // When Kitchen Finks enters the battlefield, you gain 2 life.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerA, "Kitchen Finks", 1); // Creature 3/2
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        // kill finks and return it to battlefield with hapata's trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Kitchen Finks");
        setChoice(playerA, "When "); // two triggers (gain life + snake)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPowerToughness(playerA, "Kitchen Finks", 2, 1);
        assertPermanentCount(playerA, "Snake Token", 1);
    }
}
