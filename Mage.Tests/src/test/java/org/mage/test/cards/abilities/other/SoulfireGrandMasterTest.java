package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class SoulfireGrandMasterTest extends CardTestPlayerBase {

    /**
     * Soulfire Grand Master Creature - Human Monk 2/2, 1W (2) Lifelink Instant
     * and sorcery spells you control have lifelink. {2}{U/R}{U/R}: The next
     * time you cast an instant or sorcery spell from your hand this turn, put
     * that card into your hand instead of into your graveyard as it resolves.
     */
    @Test
    public void testSpellsGainLifelink() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertHandCount(playerA, "Lightning Bolt", 0);
        assertLife(playerB, 17);
        assertLife(playerA, 23);

    }

    @Test
    public void testSpellsReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Lifelink
        // Instant and sorcery spells you control have lifelink.
        // {2}{U/R}{U/R}: The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of your graveyard as it resolves.
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U/R}{U/R}:");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertHandCount(playerA, "Lightning Bolt", 1);
        assertLife(playerA, 23);
        assertLife(playerB, 17);

    }

    /**
     * Test with Searing Blood If the delayed triggered ability triggers, it has
     * to give life from lifelink because the source is still Searing Blood
     */
    @Test
    public void testSearingBlood1() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Searing Blood {R}{R}
        // Searing Blood deals 2 damage to target creature. When that creature dies this turn, Searing Blood deals 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Searing Blood");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Blood", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulfire Grand Master", 1);
        assertGraveyardCount(playerA, "Searing Blood", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerB, 17); // -3 by Searing blood because Silvercoat Lion dies
        assertLife(playerA, 25); // +2 from damage to Silvercoat Lion + 3 from damage to Player B

    }

    /**
     * Test with Searing Blood If the delayed triggered ability triggers, it has
     * to give life from lifelink because the source is still Searing Blood
     */
    @Test
    public void testSearinBlood2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Searing Blood {R}{R}
        // Searing Blood deals 2 damage to target creature. When that creature dies this turn, Searing Blood deals 3 damage to that creature's controller.
        addCard(Zone.HAND, playerA, "Searing Blood");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Blood", "Pillarfield Ox");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Pillarfield Ox");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soulfire Grand Master", 1);
        assertGraveyardCount(playerA, "Searing Blood", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerB, 17);
        assertLife(playerA, 28); // +2 from damage to Silvercoat Lion + 3 from Lighning Bolt + 3 from damage to Player B from Searing Blood

    }

    /**
     * Test copied instant spell gives also life
     */
    @Test
    public void test_CopiesMustHaveGainedLifelink() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        //
        // Instant and sorcery spells you control have lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);
        //
        // {2}{U}{R}: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Nivix Guildmage", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}{R}:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // 2x bolts

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulfire Grand Master", 1);
        assertPermanentCount(playerA, "Nivix Guildmage", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertLife(playerB, 14);
        assertLife(playerA, 26);
    }

    /**
     * Test damage of activated ability of a permanent does not gain lifelink
     */
    @Test
    public void testActivatedAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);
        // {3}, {T}: Rod of Ruin deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Rod of Ruin", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulfire Grand Master", 1);
        assertPermanentCount(playerA, "Rod of Ruin", 1);

        assertLife(playerB, 19);
        assertLife(playerA, 20);

    }

    /**
     * Test that if Soulfire Grand Master has left the battlefield spell has no
     * longer lifelink
     */
    @Test
    public void testSoulfireLeft() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Soulfire Grand Master");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Soulfire Grand Master", 1);

        assertLife(playerB, 17);
        assertLife(playerA, 20);

    }

    /**
     * I activated the ability of Soulfire grand master, it resolved, then i
     * cast Stoke the Flames on Whisperwood Elemental, my opponenet sacrificed
     * the elemental, so stoke didnt resolve, but i still got the life from
     * lifelink.
     */
    @Test
    public void testSoulfireStokeTheFlames() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        addCard(Zone.HAND, playerA, "Stoke the Flames");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Whisperwood Elemental", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U/R}{U/R}:");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Stoke the Flames", "Whisperwood Elemental");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Sacrifice {this}", null, "{this} deals 4 damage");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Stoke the Flames", 1); // no legal target left so the spell is countered and goes to graveyard
        assertGraveyardCount(playerB, "Whisperwood Elemental", 1);

        assertLife(playerB, 20);
        assertLife(playerA, 20);

    }

    /**
     * Check if second ability resolved, the next spell that is counterer won't
     * go to hand back because it did not resolve
     */
    @Test
    public void testSoulfireCounteredSpellDontGoesBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        addCard(Zone.HAND, playerA, "Stoke the Flames");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Whisperwood Elemental", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U/R}{U/R}:");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Stoke the Flames", "Whisperwood Elemental");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Counterspell", "Stoke the Flames");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertGraveyardCount(playerA, "Stoke the Flames", 1); // no legal target left so the spell is countered and goes to graveyard

        assertLife(playerB, 20);
        assertLife(playerA, 20);

    }

    /**
     * With a Soulfire Grand Master in play, Deflecting Palm doesn't gain the
     * caster life. It should as it has lifelink, and it's Deflecting Palm (an
     * instant) dealing damage. I was playing against a human in Standard
     * Constructed.
     */
    @Test
    public void testWithDeflectingPalm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Instant -{R}{W}
        // The next time a source of your choice would deal damage to you this turn, prevent that damage.
        // If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        addCard(Zone.HAND, playerA, "Deflecting Palm");
        addCard(Zone.BATTLEFIELD, playerA, "Soulfire Grand Master", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deflecting Palm", null, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Deflecting Palm", 1);

        assertLife(playerB, 17);
        assertLife(playerA, 23); // damage is prevented + lifelink + 3

    }
}
