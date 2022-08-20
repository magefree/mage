package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class LilianaDefiantNecromancerTest extends CardTestPlayerBase {

    // Reported bug: -X allowing returning creatures with higher CMC than counters removed
    @Test
    public void testMinusAbilityShouldNotReturnHigherCmcCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant", 1); // {3}{R} 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer,
        // then return her to the battlefield transformed under her owner's control.
        // If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Liliana, Heretical Healer");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana, Heretical Healer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Hill Giant");

        // Transformed into Liliana, Defiant Necromancer with (3) loyalty to start
        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-X:", "Hill Giant");
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have thrown error about cannot attack, but got:\n" + e.getMessage());
            }
        }

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Liliana, Heretical Healer", 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount("Liliana, Defiant Necromancer", CounterType.LOYALTY, 3); // No balid target with X=1 so no counter is removed
        assertPermanentCount(playerA, "Hill Giant", 0);
        assertGraveyardCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void testMinusAbilityShouldNotReturnLegendaryCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Alesha, Who Smiles at Death", 1); // {2}{R} 3/2 Legendary first strike
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Liliana, Heretical Healer");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana, Heretical Healer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt");
        addTarget(playerB, "Alesha, Who Smiles at Death");

        // Transformed into Liliana, Defiant Necromancer with (3) loyalty to start
        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        checkPlayableAbility("Can't -X", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-X:", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Liliana, Heretical Healer", 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertPermanentCount(playerA, "Alesha, Who Smiles at Death", 0);
        assertGraveyardCount(playerA, "Alesha, Who Smiles at Death", 1);
        // because target could not be chosen, the counters were never removed?
        assertCounterCount("Liliana, Defiant Necromancer", CounterType.LOYALTY, 3);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
    }

    @Test
    public void testMinusAbilityShouldReturnNonLegendaryCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Bronze Sable", 1); // {2} 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Lifelink
        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Liliana, Heretical Healer");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana, Heretical Healer");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt");
        addTarget(playerB, "Bronze Sable");

        // Transformed into Liliana, Defiant Necromancer with (3) loyalty to start
        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-X:");
        setChoice(playerA, "X=2");
        // Bronze Sable is auto-chosen since only option

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Liliana, Heretical Healer", 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertPermanentCount(playerA, "Bronze Sable", 1);
        assertGraveyardCount(playerA, "Bronze Sable", 0);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertCounterCount("Liliana, Defiant Necromancer", CounterType.LOYALTY, 1);
    }

}
