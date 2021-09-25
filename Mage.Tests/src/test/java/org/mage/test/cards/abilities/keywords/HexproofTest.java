package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author LevelX2, JayDi85
 */
public class HexproofTest extends CardTestPlayerBaseWithAIHelps {

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testOneTargetOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        //
        // Target creature you control gets +1/+1 and gains hexproof until end of turn.
        addCard(Zone.HAND, playerA, "Ranger's Guile"); // {G}
        //
        // Return up to two target creatures to their owners’ hands.
        addCard(Zone.HAND, playerB, "Into the Void"); //{3}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
    }

    /**
     * Tests one target gets hexproof
     */
    @Test
    public void testTwoTargetsOneGainingHexproof() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elder of Laurels");
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf");
        addCard(Zone.HAND, playerA, "Ranger's Guile");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Into the Void");

        // Return up to two target creatures to their owners' hands.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Into the Void", "Elder of Laurels^Arbor Elf");
        // Target creature you control gets +1/+1 and gains hexproof until end of turn. (It can't be the target of spells or abilities your opponents control.)
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Ranger's Guile", "Elder of Laurels");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // because of hexproof the Elder should be onto the battlefield
        assertPermanentCount(playerA, "Elder of Laurels", 1);
        assertPowerToughness(playerA, "Elder of Laurels", 3, 4);
        assertAbility(playerA, "Elder of Laurels", HexproofAbility.getInstance(), true);
        assertPermanentCount(playerA, "Arbor Elf", 0);
    }

    /**
     * Tests hexproof from a color with opponent's spells
     */
    @Test
    public void testHexproofFromColorOpponentSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Murder", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Knight of Grace");
        addCard(Zone.BATTLEFIELD, playerB, "Knight of Malice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Grace");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Malice");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Knight of Grace", 1);
        assertPermanentCount(playerB, "Knight of Malice", 0);
    }

    /**
     * Tests hexproof from a color with controller's spells
     */
    @Test
    public void testHexproofFromColorOwnSpells() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.HAND, playerA, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Knight of Grace");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Knight of Grace", 0);
    }

    @Test
    public void test_Human_CanTargetValid() {
        // +1: Target player discards a card.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana Vess", 1);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Matter Reshaper", 1);
        addCard(Zone.HAND, playerB, "Mountain", 1);
        //
        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of Sanctity", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, playerA);
        setChoice(playerA, "Swamp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_Human_CantTargetInvalid() {
        // +1: Target player discards a card.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana Vess", 1);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Matter Reshaper", 1);
        addCard(Zone.HAND, playerB, "Mountain", 1);
        //
        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of Sanctity", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        addTarget(playerA, playerB);

        try {
            setStrictChooseMode(true);
            setStopAt(1, PhaseStep.END_TURN);
            execute();
            assertAllCommandsUsed();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("setup good targets")) {
                Assert.fail("must thow error about bad targets, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    public void test_AI_MustTargetOnlyValid() {
        // +1: Target player discards a card.
        addCard(Zone.BATTLEFIELD, playerA, "Liliana Vess", 1);
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Matter Reshaper", 1);
        addCard(Zone.HAND, playerB, "Mountain", 1);
        //
        // You have hexproof. (You can't be the target of spells or abilities your opponents control.)
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of Sanctity", 1);

        // ai must not use +1 on itself (due bad score) and must not use on opponent (due hexproof)
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // no discarded cards
        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
    }

    @Test
    public void test_RulesModificationForPlayers() {
        // This turn and next turn, creatures can't attack, and players and permanents can't be the targets
        // of spells or activated abilities.
        addCard(Zone.HAND, playerA, "Peace Talks", 1); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);

        // activate restriction
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Peace Talks");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // playable doesn't check illegal targets, so it will be active
        // ai can cast on turn 3 only
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkLife("after 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 20);
        aiPlayStep(2, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkLife("after 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, 20);
        aiPlayStep(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkLife("after 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
