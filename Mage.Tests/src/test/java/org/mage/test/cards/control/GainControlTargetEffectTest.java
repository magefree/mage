/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.control;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GainControlTargetEffectTest extends CardTestPlayerBase {

    /**
     * Checks if control has changed and the controlled creature has Haste
     *
     */
    @Test
    public void testPermanentControlEffect() {
        addCard(Zone.HAND, playerA, "Smelt-Ward Gatekeepers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Boros Guildgate", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Smelt-Ward Gatekeepers");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // under control
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertAbility(playerA, "Silvercoat Lion", HasteAbility.getInstance(), true);
    }

    /**
     * I gained control of my opponent's Glen Elendra Archmage with Vedalken
     * Shackles. After I sacrificed it to counter a spell, it Persisted back to
     * my battlefield, but it should return under its owner's control. Maybe a
     * Persist problem, but I am thinking Vedalken Shackles doesn't realize that
     * it is a different object when it returns from the graveyard instead.
     */
    @Test
    public void testGainControlOfCreatureWithPersistEffect() {
        // {2},{T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Lightning Strike deals 3 damage to target creature or player.
        addCard(Zone.HAND, playerB, "Lightning Strike", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // Flying
        // {U}, Sacrifice Glen Elendra Archmage: Counter target noncreature spell.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerB, "Glen Elendra Archmage");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as {this} remains tapped.", "Glen Elendra Archmage");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Strike", playerA);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U},Sacrifice {this}: Counter target noncreature spell.", "Lightning Strike");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Strike", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // under control of the owner after persist triggered
        assertPermanentCount(playerA, "Glen Elendra Archmage", 0);
        assertPermanentCount(playerB, "Glen Elendra Archmage", 1);

    }

    /**
     * The shackles can maintain control of Mutavault indefinitely, even when
     * it's not a creature.
     *
     */
    @Test
    public void testKeepControlOfMutavault() {
        // {2},{T}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // {T}: Add {1} to your mana pool.
        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        addCard(Zone.BATTLEFIELD, playerB, "Mutavault", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}: Until end of turn {this} becomes");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2},{T}: Gain control", "Mutavault");

        setChoice(playerA, "No"); // Don't untap the Shackles
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // under control of Shackles even if it's no longer a creature
        assertPermanentCount(playerB, "Mutavault", 0);
        assertPermanentCount(playerA, "Mutavault", 1);

    }
}
