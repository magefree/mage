package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */
public class GeodeGolemTest extends CardTestCommanderDuelBase {

    @Test
    public void test_Normal() {
        // Whenever Geode Golem deals combat damage to a player, you may
        // cast your commander from the command zone without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Geode Golem");
        //
        addCard(Zone.COMMAND, playerA, "Grizzly Bears"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        //
        addCustomEffect_TargetDamage(playerA, 2);

        checkCommandCardCount("before 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // turn 1 - first cast

        // attack and cast first time (free)
        attack(1, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Grizzly Bears"); // commander choice
        waitStackResolved(1, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Grizzly Bears", 1);
        checkPermanentTapped("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Forest", true, 0);
        //
        // remove to command zone (0x tax)
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 2", "Grizzly Bears");
        setChoice(playerA, true); // move to command zone

        // turn 3 - second cast (1x tax)

        attack(3, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Grizzly Bears"); // commander choice
        waitStackResolved(3, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Grizzly Bears", 1);
        checkPermanentTapped("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Forest", true, 2); // 1x tax
        //
        // remove to command zone
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 2", "Grizzly Bears");
        setChoice(playerA, true); // move to command zone

        // turn 5 - third cast (2x tax)

        attack(5, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Grizzly Bears"); // commander choice
        waitStackResolved(5, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Grizzly Bears", 1);
        checkPermanentTapped("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Forest", true, 2 * 2); // 2x tax

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_MDF_SingleSide() {
        // Whenever Geode Golem deals combat damage to a player, you may
        // cast your commander from the command zone without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Geode Golem");
        //
        // Akoum Warrior {5}{R} - creature 4/5
        // Akoum Teeth - land
        addCard(Zone.COMMAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        //
        addCustomEffect_TargetDamage(playerA, 5);

        checkCommandCardCount("before 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // turn 1 - first cast

        // attack and cast first time (free)
        attack(1, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Akoum Warrior"); // commander choice
        waitStackResolved(1, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Akoum Warrior", 1);
        checkPermanentTapped("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 0);
        //
        // remove to command zone (0x tax)
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 5", "Akoum Warrior");
        setChoice(playerA, true); // move to command zone

        // turn 3 - second cast (1x tax)

        attack(3, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Akoum Warrior"); // commander choice
        waitStackResolved(3, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Akoum Warrior", 1);
        checkPermanentTapped("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 2); // 1x tax
        //
        // remove to command zone
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 5", "Akoum Warrior");
        setChoice(playerA, true); // move to command zone

        // turn 5 - third cast (2x tax)

        attack(5, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Akoum Warrior"); // commander choice
        waitStackResolved(5, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Akoum Warrior", 1);
        checkPermanentTapped("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 2 * 2); // 2x tax

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Akoum Warrior", 1);
    }

    @Test
    public void test_MDF_BothSides() {
        // Whenever Geode Golem deals combat damage to a player, you may
        // cast your commander from the command zone without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Geode Golem");
        //
        // Birgi, God of Storytelling {2}{R} - creature 3/3
        // Harnfel, Horn of Bounty {4}{R} - artifact
        addCard(Zone.COMMAND, playerA, "Birgi, God of Storytelling");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        //
        addCustomEffect_TargetDamage(playerA, 3);
        addCustomEffect_DestroyTarget(playerA);

        checkCommandCardCount("before 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birgi, God of Storytelling", 1);

        // turn 1 - first cast, LEFT side

        // attack and cast first time (free)
        attack(1, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Birgi, God of Storytelling"); // commander choice
        setChoice(playerA, "Cast Birgi, God of Storytelling"); // spell choice
        waitStackResolved(1, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Birgi, God of Storytelling", 1);
        checkPermanentTapped("after 1", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 0);
        //
        // remove to command zone (0x tax)
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 3", "Birgi, God of Storytelling");
        setChoice(playerA, true); // move to command zone

        // turn 3 - second cast, LEFT side (1x tax)

        attack(3, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Birgi, God of Storytelling"); // commander choice
        setChoice(playerA, "Cast Birgi, God of Storytelling"); // spell choice
        waitStackResolved(3, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Birgi, God of Storytelling", 1);
        checkPermanentTapped("after 2", 3, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 2); // 1x tax
        //
        // remove to command zone
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "target damage 3", "Birgi, God of Storytelling");
        setChoice(playerA, true); // move to command zone

        // turn 5 - third cast, RIGHT side (2x tax)

        attack(5, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Birgi, God of Storytelling"); // commander choice
        setChoice(playerA, "Cast Harnfel, Horn of Bounty"); // spell choice
        waitStackResolved(5, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Harnfel, Horn of Bounty", 1);
        checkPermanentTapped("after 3", 5, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 2 * 2); // 2x tax
        //
        // remove to command zone
        activateAbility(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Harnfel, Horn of Bounty");
        setChoice(playerA, true); // move to command zone

        // turn 7 - fourth cast, RIGHT side (3x tax)

        attack(7, playerA, "Geode Golem");
        setChoice(playerA, true); // cast commander
        setChoice(playerA, "Birgi, God of Storytelling"); // commander choice
        setChoice(playerA, "Cast Harnfel, Horn of Bounty"); // spell choice
        waitStackResolved(7, PhaseStep.COMBAT_DAMAGE);
        checkPermanentCount("after 4", 7, PhaseStep.COMBAT_DAMAGE, playerA, "Harnfel, Horn of Bounty", 1);
        checkPermanentTapped("after 4", 7, PhaseStep.COMBAT_DAMAGE, playerA, "Mountain", true, 2 * 3); // 3x tax

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Harnfel, Horn of Bounty", 1);
    }
}
