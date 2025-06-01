package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ShadowbaneTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.Shadowbane Shadowbane}  {1}{W}
     * The next time a source of your choice would deal damage to you and/or creatures you control this turn, prevent that damage. If damage from a black source is prevented this way, you gain that much life.
     */
    private static final String shadowbane = "Shadowbane";

    @Test
    public void test_DamageOnCreature_Prevent_SourceNotBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane);
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 0);
        assertLife(playerA, 20); // no life gain
    }

    @Test
    public void test_DamageOnCreature_Prevent_SourceBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Cabal Evangel", 1); // 2/2 black
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane);
        setChoice(playerA, "Cabal Evangel"); // source to prevent from

        attack(2, playerB, "Cabal Evangel", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Cabal Evangel");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 0);
        assertLife(playerA, 20 + 2); // life gain
    }

    @Test
    public void test_DamageOnYou_Prevent_SourceNotBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane);
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    public void test_DamageOnYou_Prevent_SourceBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Cabal Evangel", 1); // 2/2 black
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane);
        setChoice(playerA, "Cabal Evangel"); // source to prevent from

        attack(2, playerB, "Cabal Evangel", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_SpellDamage_Prevent_SourceNotBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane, null, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    public void test_SpellDamage_Prevent_SourceBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.HAND, playerB, "Agonizing Syphon", 1); // 3 damage to any target. gain 3 life
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Agonizing Syphon", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane, null, "Agonizing Syphon");
        setChoice(playerA, "Agonizing Syphon"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 + 3);
    }

    @Test
    public void test_SpellDamage_Prevent_Famine_SourceBlack() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.HAND, playerB, "Famine", 1); // Famine deals 3 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Ageless Guardian"); // 1/4
        addCard(Zone.BATTLEFIELD, playerB, "Dune Beetle"); // 1/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Famine");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane, null, "Famine");
        setChoice(playerA, "Famine"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Ageless Guardian", 0); // prevented
        assertDamageReceived(playerB, "Dune Beetle", 3); // not prevented on opponent's creature
        assertLife(playerB, 20 - 3); // not prevented on opponent
        assertLife(playerA, 20 + 3 * 2); // 2 preventions of 3 damage
    }

    @Test
    public void test_SpellDamage_Prevent_FieryConfluence() {
        addCard(Zone.HAND, playerA, shadowbane, 1);
        addCard(Zone.HAND, playerB, "Fiery Confluence", 3); // three instances of "Fiery Confluence deals 2 damage to each opponent."
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Fiery Confluence");
        setModeChoice(playerB, "2"); // Fiery Confluence deals 2 damage to each opponent
        setModeChoice(playerB, "2"); // Fiery Confluence deals 2 damage to each opponent
        setModeChoice(playerB, "2"); // Fiery Confluence deals 2 damage to each opponent
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, shadowbane, null, "Fiery Confluence");
        setChoice(playerA, "Fiery Confluence"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2 * 2); // only 1 of the 3 damage instance is prevented
    }
}
