/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.continuous;

import mage.abilities.keyword.SwampwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LoosingAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void GivingSwampwalkFromGraveyard() {

        // Swampwalk
        // As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk.
        addCard(Zone.GRAVEYARD, playerB, "Filth"); // Creature
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), true);
    }

    /**
     * The Card in the graveyard should have no Swampwalk if Yixlid Jailer
     * effect was added later
     */
    @Test
    public void testYixlidJailerRemovesAbilities() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Swampwalk
        // As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk.
        addCard(Zone.GRAVEYARD, playerB, "Filth");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertAbility(playerB, "Silvercoat Lion", new SwampwalkAbility(), false);
    }

    // https://github.com/magefree/mage/issues/1147
    @Test
    public void testYixlidJailerAbilitiesComeBack() {
        // Cards in graveyards lose all abilities.
        addCard(Zone.HAND, playerA, "Yixlid Jailer"); // Creature 2/1 - {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // Gravecrawler can’t block.
        // You may cast Gravecrawler from your graveyard as long as you control a Zombie.
        addCard(Zone.GRAVEYARD, playerB, "Gravecrawler");
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse"); // Creature 2/2 - Zombie
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yixlid Jailer");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Yixlid Jailer");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gravecrawler");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Yixlid Jailer", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertPermanentCount(playerB, "Gravecrawler", 1);
    }
}
