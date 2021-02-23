package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ClergyOfTheHolyNimbusTest extends CardTestPlayerBase {

    @Test
    public void testBasicRegeneration() {

        // If Clergy of the Holy Nimbus would be destroyed, regenerate it.
        // {1}: Clergy of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Clergy of the Holy Nimbus"); // {W} 1/1
        addCard(Zone.HAND, playerA, "Doom Blade"); // {1}{B} destroy target non-black creature
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Clergy of the Holy Nimbus");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Doom Blade", 1);
        assertPermanentCount(playerB, "Clergy of the Holy Nimbus", 1);
    }

    @Test
    public void testCannotBeRegeneratedSpell() {

        // If Clergy of the Holy Nimbus would be destroyed, regenerate it.
        // {1}: Clergy of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Clergy of the Holy Nimbus"); // {W} 1/1
        addCard(Zone.HAND, playerA, "Wrath of God"); // {2}{W}{W} destroy all creatures, they cannot be regenerated
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wrath of God", 1);
        assertGraveyardCount(playerB, "Clergy of the Holy Nimbus", 1);
    }

    // in game testing works correctly - not sure if the ability is being activated or not here.
    @Test
    public void testOpponentPaysOneToNotAllowRegeneration() {

        // If Clergy of the Holy Nimbus would be destroyed, regenerate it.
        // {1}: Clergy of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Clergy of the Holy Nimbus"); // {W} 1/1
        addCard(Zone.HAND, playerA, "Doom Blade"); // {1}{B} destroy target non-black creature
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Doom Blade", "Clergy of the Holy Nimbus");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Doom Blade", 1);
        assertGraveyardCount(playerB, "Clergy of the Holy Nimbus", 1);
    }
}
