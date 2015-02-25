package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class UndyingTest extends CardTestPlayerBase {

    /**
     * Tests boost weren't be applied second time when creature back to battlefield
     */
    @Test
    public void testWithBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Geralf's Messenger");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Last Gasp");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Geralf's Messenger");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Geralf's Messenger", 1);
        assertPowerToughness(playerA, "Geralf's Messenger", 4, 3);
    }

    /**
     * Tests boost weren't be applied second time when creature back to battlefield
     */
    @Test
    public void testWithMassBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Strangleroot Geist");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Cower in Fear");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cower in Fear");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Strangleroot Geist", 1);
        // dies then returned with +1/+1 counter (and boost doesn't work anymore)
        assertPowerToughness(playerA, "Strangleroot Geist", 3, 2);
    }

    /**
     * Tests "Target creature gains undying until end of turn"
     */
    @Test
    public void testUndyingEvil() {
        // Elite Vanguard
        // Creature — Human Soldier 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Last Gasp
        // Instant, 1B
        // Target creature gets -3/-3 until end of turn.
        addCard(Zone.HAND, playerA, "Last Gasp");
        // Undying Evil
        // Target creature gains undying until end of turn. 
        // When it dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Undying Evil");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Last Gasp", "Elite Vanguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Undying Evil", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 2);
    }


    /**
     * Tests "Threads of Disloyalty enchanting Strangleroot Geist: after geist died it returns to the bf under opponent's control."
     */
    @Test
    public void testUndyingControlledReturnsToOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        // Strangleroot Geist  2/1
        // Haste
        // Undying
        // (When it dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.HAND, playerA, "Strangleroot Geist");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        // Threads of Disloyalty {1}{U}{U}
        // Enchant creature with converted mana cost 2 or less
        // You control enchanted creature.
        addCard(Zone.HAND, playerB, "Threads of Disloyalty");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Strangleroot Geist");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Threads of Disloyalty", "Strangleroot Geist");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Strangleroot Geist");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Threads of Disloyalty", 1);        
        assertGraveyardCount(playerA, "Lightning Bolt",1);
        assertPermanentCount(playerB, "Strangleroot Geist", 0);
        assertPermanentCount(playerA, "Strangleroot Geist", 1);
        assertPowerToughness(playerA, "Strangleroot Geist", 3, 2);
    }


}
