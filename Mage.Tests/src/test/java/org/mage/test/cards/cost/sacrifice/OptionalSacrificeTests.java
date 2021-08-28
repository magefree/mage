package org.mage.test.cards.cost.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class OptionalSacrificeTests extends CardTestPlayerBase {

    /**
     * Glint Hawk - Flying 2/2 - {W} When Glint Hawk enters the battlefield,
     * sacrifice it unless you return an artifact you control to its owner's
     * hand.
     *
     * Test returning controlled artifact.
     */
    @Test
    public void testGlintHawkChooseArtifact() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Glint Hawk");
        addCard(Zone.BATTLEFIELD, playerA, "Relic of Progenitus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glint Hawk");
        setChoice(playerA, true);
        addTarget(playerA, "Relic of Progenitus");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glint Hawk", 1);
        assertGraveyardCount(playerA, "Glint Hawk", 0);
        assertHandCount(playerA, "Relic of Progenitus", 1);
        assertPermanentCount(playerA, "Relic of Progenitus", 0);
    }

    /**
     * Glint Hawk - Flying 2/2 - {W} When Glint Hawk enters the battlefield,
     * sacrifice it unless you return an artifact you control to its owner's
     * hand.
     *
     * Test opting not to choose an artifact controlled to sacrifice Glint Hawk.
     */
    @Test
    public void testGlintHawkDoNotChooseArtifactControlled() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Glint Hawk");
        addCard(Zone.BATTLEFIELD, playerA, "Relic of Progenitus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glint Hawk");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glint Hawk", 0);
        assertGraveyardCount(playerA, "Glint Hawk", 1);
        assertHandCount(playerA, "Relic of Progenitus", 0);
        assertPermanentCount(playerA, "Relic of Progenitus", 1);
    }

    /**
     * Glint Hawk - Flying 2/2 - {W} When Glint Hawk enters the battlefield,
     * sacrifice it unless you return an artifact you control to its owner's
     * hand.
     *
     * Test no artifacts to target - so Glint Hawk is sacrificed.
     */
    @Test
    public void testGlintHawkNoArtifactControlled() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Glint Hawk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glint Hawk");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glint Hawk", 0);
        assertGraveyardCount(playerA, "Glint Hawk", 1);
    }

    /**
     * Drake Familiar - Flying 2/1 - {1}{U} When Drake Familiar enters the
     * battlefield, sacrifice it unless you return an enchantment to its owner's
     * hand.
     *
     * Test returning own enchantment so not sacrificed.
     */
    @Test
    public void testDrakeFamiliarOwnEnchantment() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Drake Familiar");
        addCard(Zone.BATTLEFIELD, playerA, "Moat");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Familiar");
        setChoice(playerA, true);
        addTarget(playerA, "Moat");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Drake Familiar", 1);
        assertGraveyardCount(playerA, "Drake Familiar", 0);
        assertHandCount(playerA, "Moat", 1);
        assertPermanentCount(playerA, "Moat", 0);
    }

    /**
     * Drake Familiar - Flying 2/1 - {1}{U} When Drake Familiar enters the
     * battlefield, sacrifice it unless you return an enchantment to its owner's
     * hand.
     *
     * Test returning opponent's enchantment so not sacrificed.
     */
    @Test
    public void testDrakeFamiliarOpposingEnchantment() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Drake Familiar");
        // Creatures can’t attack you unless their controller pays {2} for each creature they control that’s attacking you.
        addCard(Zone.BATTLEFIELD, playerB, "Propaganda");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Familiar");
        setChoice(playerA, true);
        addTarget(playerA, "Propaganda");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Drake Familiar", 1);
        assertGraveyardCount(playerA, "Drake Familiar", 0);
        assertHandCount(playerB, "Propaganda", 1);
        assertPermanentCount(playerB, "Propaganda", 0);
    }

    /**
     * Drake Familiar - Flying 2/1 - {1}{U} When Drake Familiar enters the
     * battlefield, sacrifice it unless you return an enchantment to its owner's
     * hand.
     *
     * Test when no enchantments are on battlefield, Drake is sacrificed.
     */
    @Test
    public void testDrakeFamiliarNoEnchantmentControlled() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Drake Familiar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Familiar");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Drake Familiar", 0);
        assertGraveyardCount(playerA, "Drake Familiar", 1);
    }

    /**
     * Drake Familiar - Flying 2/1 - {1}{U} When Drake Familiar enters the
     * battlefield, sacrifice it unless you return an enchantment to its owner's
     * hand.
     *
     * Test when no enchantments are on battlefield, Drake is sacrificed.
     */
    @Test
    public void testDrakeFamiliarDoNotChooseEnchantment() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Drake Familiar");
        addCard(Zone.BATTLEFIELD, playerB, "Propaganda");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Drake Familiar");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Drake Familiar", 0);
        assertGraveyardCount(playerA, "Drake Familiar", 1);
        assertHandCount(playerB, "Propaganda", 0);
        assertPermanentCount(playerB, "Propaganda", 1);
    }

    /**
     As an additional cost to cast Devouring Greed, you may sacrifice any number of Spirits.

     // Target player loses 2 life plus 2 life for each Spirit sacrificed this way. You gain that much life.
     **/
    @Test
    public void testDevouringGreedWithoutSpirits(){
        addCard(Zone.HAND, playerA, "Devouring Greed");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devouring Greed", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertLife(playerB, 18);
        assertLife(playerA, 22);
        assertGraveyardCount(playerA, "Devouring Greed", 1);
    }
}
