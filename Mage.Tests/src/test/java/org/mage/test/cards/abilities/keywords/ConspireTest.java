package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class ConspireTest extends CardTestPlayerBase {

    /**
     * 702.77. Conspire 702.77a Conspire is a keyword that represents two
     * abilities. The first is a static ability that functions while the spell
     * with conspire is on the stack. The second is a triggered ability that
     * functions while the spell with conspire is on the stack. “Conspire” means
     * “As an additional cost to cast this spell, you may tap two untapped
     * creatures you control that each share a color with it” and “When you cast
     * this spell, if its conspire cost was paid, copy it. If the spell has any
     * targets, you may choose new targets for the copy.” Paying a spell's
     * conspire cost follows the rules for paying additional costs in rules
     * 601.2b and 601.2e–g.
     *
     * 702.77b If a spell has multiple instances of conspire, each is paid
     * separately and triggers based on its own payment, not any other instance
     * of conspire
     *
     */
    /**
     * Burn Trail Sorcery, 3R (4) Burn Trail deals 3 damage to target creature
     * or player.
     * <p>
     * Conspire (As you cast this spell, you may tap two untapped creatures you
     * control that share a color with it. When you do, copy it and you may
     * choose a new target for the copy.)
     */
    @Test
    public void testConspire() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Burn Trail");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertTapped("Goblin Roughrider", true);
        assertTapped("Raging Goblin", true);

    }

    @Test
    public void testConspireNotUsed() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.HAND, playerA, "Burn Trail");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertTapped("Goblin Roughrider", false);
        assertTapped("Raging Goblin", false);

    }

    @Test
    public void testWortTheRaidmother() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        // prepare goblins
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother");// {4}{R/G}{R/G}

        // cast with conspire
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // use conspire
        setChoice(playerA, "Goblin Warrior");
        setChoice(playerA, "Goblin Warrior");
        setChoice(playerA, false); // keep targets

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3 - 3);

    }

    @Test
    public void testWortTheRaidmotherTwoSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Shock");

        // prepare goblins
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother");// {4}{R/G}{R/G}

        // cast with conspire
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // use conspire
        setChoice(playerA, "Goblin Warrior");
        setChoice(playerA, "Goblin Warrior");
        setChoice(playerA, false); // keep targets
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Shock", playerB);
        setChoice(playerA, false); // don't use conspire

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Raging Goblin", false);
        assertLife(playerB, 20 - 3 - 3 - 2);

    }

    @Test
    public void testWortTheRaidmotherWithConspireSpellOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Burn Trail");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother"); // {4}{R/G}{R/G}

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, true); // use Conspire from Burn Trail itself
        setChoice(playerA, false); // don't use Conspire gained from Wort, the Raidmother

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertLife(playerB, 20 - 3 - 3);
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Burn Trail", 1);
    }

    @Test
    public void testWortTheRaidmotherWithConspireSpellTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Burn Trail");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother"); // {4}{R/G}{R/G}

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Burn Trail", playerB);
        setChoice(playerA, true); // use Conspire from Burn Trail itself
        setChoice(playerA, true); // use Conspire gained from Wort, the Raidmother

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertLife(playerB, 20 - 3 - 3 - 3);
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Burn Trail", 1);

    }

    @Test
    public void testWortTheRaidmotherWithSakashimaOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Sakashima the Impostor");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother"); // {4}{R/G}{R/G}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sakashima the Impostor"); // {2}{U}{U}
        setChoice(playerA, "Wort, the Raidmother");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // use Conspire gained from Wort, the Raidmother
        setChoice(playerA, false); // don't use Conspire gained from Sakashima the Imposter

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertPermanentCount(playerA, "Sakashima the Impostor", 1);
        assertLife(playerB, 20 - 3 - 3);
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void testWortTheRaidmotherWithSakashimaTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 11);
        // When Wort, the Raidmother enters the battlefield, put two 1/1 red and green Goblin Warrior creature tokens onto the battlefield.
        // Each red or green instant or sorcery spell you cast has conspire.
        // (As you cast the spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.)
        addCard(Zone.HAND, playerA, "Wort, the Raidmother");
        addCard(Zone.HAND, playerA, "Sakashima the Impostor");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wort, the Raidmother"); // {4}{R/G}{R/G}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sakashima the Impostor"); // {2}{U}{U}
        setChoice(playerA, "Wort, the Raidmother");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true); // use Conspire gained from Wort, the Raidmother
        setChoice(playerA, true); // use Conspire gained from Sakashima the Imposter

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Wort, the Raidmother", 1);
        assertPermanentCount(playerA, "Sakashima the Impostor", 1);
        assertLife(playerB, 20 - 3 - 3 - 3);
        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void testConspire_User() {
        // Burn Trail deals 3 damage to any target.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it.
        // When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Burn Trail", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Assailant", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail");
        addTarget(playerA, playerB);
        setChoice(playerA, true); // use conspire
        setChoice(playerA, "Goblin Assailant^Goblin Assailant");
        setChoice(playerA, false); // don't change target 1

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertLife(playerB, 20 - 3 - 3);
        assertTapped("Goblin Assailant", true);
    }

    @Test
    public void testConspire_AI_can() {
        // Burn Trail deals 3 damage to any target.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it.
        // When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Burn Trail", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Assailant", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail");
        addTarget(playerA, playerB);
        //setChoice(playerA, true); // use conspire - AI must choose
        //setChoice(playerA, "Goblin Assailant^Goblin Assailant"); - AI must choose
        //setChoice(playerA, false); // don't change target 1 - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertLife(playerB, 20 - 3 - 3);
        assertTapped("Goblin Assailant", true);
    }

    @Test
    public void testConspire_AI_cannot() {
        // Burn Trail deals 3 damage to any target.
        // Conspire (As you cast this spell, you may tap two untapped creatures you control that share a color with it.
        // When you do, copy it and you may choose a new target for the copy.)
        addCard(Zone.HAND, playerA, "Burn Trail", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Assailant", 2 - 1); // AI can't pay additional cost, must use simple mode

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burn Trail");
        addTarget(playerA, playerB);
        //setChoice(playerA, true); // use conspire - AI must choose
        //setChoice(playerA, "Goblin Assailant^Goblin Assailant"); - AI must choose
        //setChoice(playerA, false); // don't change target 1 - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Burn Trail", 1);
        assertLife(playerB, 20 - 3); // simple cast
        assertTapped("Goblin Assailant", false);
    }
}
