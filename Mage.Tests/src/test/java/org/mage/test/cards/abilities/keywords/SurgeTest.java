
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SurgeTest extends CardTestPlayerBase {

    /**
     *
     */
    @Test
    public void testWithoutSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, put an 8/8 blue Octopus creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Crush of Tentacles"); // {4}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crush of Tentacles");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Crush of Tentacles", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, 6);
    }

    @Test
    public void testWithSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, put an 8/8 blue Octopus creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Crush of Tentacles"); // {4}{U}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Create a token that's a copy of target creature you control.
        // Flashback {5}{U}{U}(You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerB, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cackling Counterpart", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crush of Tentacles with surge");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Cackling Counterpart", 1);
        assertGraveyardCount(playerA, "Crush of Tentacles", 1);
        assertPermanentCount(playerA, "Octopus Token", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, 7);

        assertLife(playerB, 17);
    }

    @Test
    public void testTyrantOfValakut() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        // Surge {3}{R}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        // Flying
        // When Tyrant of Valakut enters the battlefield, if its surge cost was paid, it deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Tyrant of Valakut"); // {5}{R}{R}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tyrant of Valakut");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Tyrant of Valakut", 1);

        assertLife(playerB, 14);
    }

    @Test
    public void testContainmentMembrane() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Enchant creature
        // Enchanted creature doesn't untap during its controller's untap step.
        // Surge {U} (You may cast a spell for its surge cost if you or a teammate have cast another spell in the same turn.)
        addCard(Zone.HAND, playerA, "Containment Membrane"); // {2}{U}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Containment Membrane", "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Containment Membrane", 1);

        assertTapped("Silvercoat Lion", true);

    }

}
