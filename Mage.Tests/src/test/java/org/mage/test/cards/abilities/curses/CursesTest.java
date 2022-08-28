package org.mage.test.cards.abilities.curses;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CursesTest extends CardTestPlayerBase {

    /*
    Cruel Reality {5}{B}{B}
    Enchantment - Aura Curse
    Enchant player
    At the beginning of enchanted player's upkeep, that player sacrifices a creature or planeswalker. If the player can't, they lose 5 life.
     */
    private final String cReality = "Cruel Reality";

    @Test
    public void testCurseOfBloodletting() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 14);
    }

    @Test
    public void testCurseOfEchoes() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Enchant player
        // Whenever enchanted player casts an instant or sorcery spell, each other player may copy that
        // spell and may choose new targets for the copy they control.
        addCard(Zone.HAND, playerA, "Curse of Echoes");
        // Draw three cards.
        addCard(Zone.HAND, playerB, "Jace's Ingenuity");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Echoes", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Jace's Ingenuity");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
    }

    @Test
    public void testCurseOfExhaustion1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Enchant player
        // Enchanted player can't cast more than one spell each turn.
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        checkPlayableAbility("Can't cast a 2nd spell", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Lightning", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertLife(playerA, 17);
    }

    @Test
    public void testCurseOfExhaustion2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    /**
     * Checks if Copy Enchantment works for player auras
     */
    @Test
    public void testCurseOfExhaustion3() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        // Enchant player
        // Enchanted player can't cast more than one spell each turn.
        addCard(Zone.HAND, playerA, "Curse of Exhaustion");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        addCard(Zone.HAND, playerB, "Copy Enchantment", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Exhaustion", playerB);

        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        // Choices for Copy Enchantment get auto-chosen

        checkPlayableAbility("Can't cast a 2nd spell", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning", false);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Copy Enchantment", 0);
        assertGraveyardCount(playerB, "Copy Enchantment", 0);

        assertPermanentCount(playerA, "Curse of Exhaustion", 1);
        assertPermanentCount(playerB, "Curse of Exhaustion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    // returng curse enchantment from graveyard to battlefield
    @Test
    public void testCurseOfExhaustion4() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        addCard(Zone.GRAVEYARD, playerB, "Curse of Exhaustion", 1);
        addCard(Zone.HAND, playerB, "Obzedat's Aid", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Obzedat's Aid", "Curse of Exhaustion");
        setChoice(playerB, "PlayerA");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        checkPlayableAbility("Can't cast a 2nd spell", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning", false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Obzedat's Aid", 0);
        assertGraveyardCount(playerB, "Obzedat's Aid", 1);
        assertGraveyardCount(playerB, "Curse of Exhaustion", 0);

        assertPermanentCount(playerB, "Curse of Exhaustion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testCurseOfThirst1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testCurseOfThirst2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Curse of Thirst");
        addCard(Zone.HAND, playerA, "Curse of Bloodletting");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Bloodletting", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Curse of Thirst", playerB);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

    @Test
    public void testCurseOfMisfortune1() {
        removeAllCardsFromLibrary(playerA);

        // At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same name as a
        // Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library.
        addCard(Zone.LIBRARY, playerA, "Curse of Misfortunes", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
    }

    @Test
    public void testCurseOfMisfortune2() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curse of Bloodletting", 2);
        addCard(Zone.HAND, playerA, "Curse of Misfortunes");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Curse of Misfortunes", 1);
        assertPermanentCount(playerA, "Curse of Bloodletting", 1);
    }

    @Test
    public void testCurseOfDeathsHold() {
        // Creatures enchanted player controls get -1/-1.
        addCard(Zone.HAND, playerA, "Curse of Death's Hold");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Curse of Death's Hold", 1);

        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
    }

    @Test
    public void testCurseOfDeathsHold2() {
        // Creatures enchanted player controls get -1/-1.
        addCard(Zone.HAND, playerA, "Curse of Death's Hold");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tasigur, the Golden Fang", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.HAND, playerB, "Reclamation Sage");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Reclamation Sage");
        addTarget(playerB, "Curse of Death's Hold");

        // {2}{G/U}{G/U}: Put the top two cards of your library into your graveyard, then return a nonland card of an opponent's choice from your graveyard to your hand.
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{G/U}{G/U}: Mill two cards");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Curse of Death's Hold", playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Reclamation Sage", 1);
        assertPermanentCount(playerA, "Curse of Death's Hold", 1);
        assertGraveyardCount(playerA, 2);

        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
    }

    @Test
    public void cruelRealityHasBothCreatureAndPwChoosePw() {
        String ugin = "Ugin, the Spirit Dragon";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, ugin);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);
        setChoice(playerB, ugin);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, ugin, 1);
        assertPermanentCount(playerB, memnite, 1);
        assertPermanentCount(playerA, cReality, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void cruelRealityHasBothCreatureAndPwChooseCreature() {
        String ugin = "Ugin, the Spirit Dragon";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, ugin);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);
        setChoice(playerB, memnite);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, memnite, 1);
        assertPermanentCount(playerB, ugin, 1);
        assertPermanentCount(playerA, cReality, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void cruelRealityOnlyHasCreatureNoChoiceMade() {
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, memnite, 1);
        assertPermanentCount(playerA, cReality, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void cruelRealityOnlyHasPwNoChoiceMade() {
        String ugin = "Ugin, the Spirit Dragon";

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, ugin);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, ugin, 1);
        assertPermanentCount(playerA, cReality, 1);
        assertLife(playerB, 20);
    }

    @Test
    public void cruelRealityOnlyHasCreatureTryToChooseNotToSac() {
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, memnite);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Missing CHOICE def for turn 2, step UPKEEP, PlayerB")) {
                Assert.fail("Should have had error about needing a target, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    public void cruelRealityNoCreatureOrPwForcesLifeLoss() {
        String gPrison = "Ghostly Prison"; // {2}{W} enchantment - doesnt matter text for this

        addCard(Zone.HAND, playerA, cReality);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerB, gPrison);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cReality, playerB);
        // No choice needed since no valid target available for Cruel Reality's triggered ability

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, gPrison, 1);
        assertPermanentCount(playerA, cReality, 1);
        assertLife(playerB, 15);
    }

    /**
     * Reported bug issue #3326
     * When {Witchbane Orb} triggers when entering the field and there IS a curse attached to you, an error message (I sadly skipped) appears and your turn is reset.
     * This happened to me in a 4-player Commander game with {Curse of the Shallow Graves} on the field.
     */
    @Test
    public void witchbaneOrbDestroysCursesOnETB() {
        /*
        Witchbane Orb {4}
        Artifact
        When Witchbane Orb enters the battlefield, destroy all Curses attached to you.
        You have hexproof.
         */
        String wOrb = "Witchbane Orb";

        /*
        Curse of Shallow Graves {2}{B}
        Enchantment — Aura Curse
        Enchant player
        Whenever a player attacks enchanted player with one or more creatures, that attacking player may create a tapped 2/2 black Zombie creature token.
         */
        String curseSG = "Curse of Shallow Graves";

        addCard(Zone.HAND, playerA, curseSG);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerB, wOrb);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseSG, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, wOrb);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, wOrb, 1);
        assertGraveyardCount(playerA, curseSG, 1);
    }
}
