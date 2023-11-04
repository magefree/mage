
package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.command.emblems.ChandraTorchOfDefianceEmblem;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OjerAxonilDeepestMightTest extends CardTestPlayerBase {

    /**
     * Ojer Axonil, Deepest Might
     * {2}{R}{R}
     * Legendary Creature — God
     * <p>
     * Trample
     * If a red source you control would deal an amount of noncombat damage less than Ojer Axonil’s power to an opponent, that source deals damage equal to Ojer Axonil’s power instead.
     * When Ojer Axonil dies, return it to the battlefield tapped and transformed under its owner’s control.
     * 4/4
     * <p>
     * Temple of Power
     * Land
     * <p>
     * (Transforms from Ojer Axonil, Deepest Might.)
     * {T}: Add {R}.
     * {2}{R}, {T}: Transform Temple of Power. Activate only if red sources you controlled dealt 4 or more noncombat damage this turn and only as a sorcery.
     */
    private static final String ojer = "Ojer Axonil, Deepest Might";
    private static final String temple = "Temple of Power";
    private static final String templeTransformAbility = "{2}{R}, {T}: Transform {this}. Activate only if red sources you controlled dealt 4 or more noncombat damage this turn and only as a sorcery.";

    /**
     * Lightning Bolt
     * {R}
     * Instant
     * <p>
     * Lightning Bolt deals 3 damage to any target.
     */
    private static final String bolt = "Lightning Bolt";
    /**
     * Lava Axe
     * {4}{R}
     * Sorcery
     * <p>
     * Lava Axe deals 5 damage to target player or planeswalker.
     */
    private static final String axe = "Lava Axe";

    @Test
    public void testReplacement_BoltFace() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 4);
    }

    @Test
    public void testReplacement_BoltOwnFace() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3); // only work on opponnent
    }

    @Test
    public void testReplacement_BoltOjer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, bolt, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, ojer);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, ojer, 3); // does not work on creatures
    }

    @Test
    public void testReplacement_CombatDamageNotReplaced() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1);

        attack(1, playerA, "Raging Goblin", playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 1); // does not alter combat damage
    }

    @Test
    public void testReplacement_Hellrider() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        // Whenever a creature you control attacks, Hellrider deals 1 damage to the player or planeswalker it’s attacking.
        addCard(Zone.BATTLEFIELD, playerA, "Hellrider", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 1);

        attack(1, playerA, "Raging Goblin", playerB);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 1 - 4); // Hellrider's trigger is altered.
    }

    @Test
    public void testReplacement_LavaAxeFace() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, axe, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, axe, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 5); // no replacement
    }

    @Test
    public void testReplacement_GiantGrowth_LavaAxeFace() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, axe, 1);
        addCard(Zone.HAND, playerA, "Giant Growth", 1); // +3/+3 until end of turn
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, axe, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", ojer);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - (4 + 3));
    }

    @Test
    public void testTransform() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, axe, 1);
        addCard(Zone.HAND, playerA, "Bathe in Dragonfire", 1); // 4 damage to target creature
        addCard(Zone.BATTLEFIELD, playerA, "Battlefield Forge", 5 + 3); // Using Forge to distinguish the mana ability from the Temple one.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bathe in Dragonfire", ojer, true);
        checkPermanentTapped("temple in play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, temple, true, 1);

        checkPlayableAbility("condition false", 3, PhaseStep.PRECOMBAT_MAIN, playerA, templeTransformAbility, false);
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}. {this} deals 1 damage to you.", 5);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, axe, playerB);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {R}. {this} deals 1 damage to you.", 3);
        checkPlayableAbility("condition true", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, templeTransformAbility, true);
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, templeTransformAbility);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
        assertPermanentCount(playerA, ojer, 1);
        assertTapped(ojer, true);
    }

    @Test
    public void test_watching_Chandra_emblem_damage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ojer, 1);
        addCard(Zone.HAND, playerA, "Doom Blade"); // destroy ojer
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // 3 damage instant
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 3);
        addEmblem(playerA, new ChandraTorchOfDefianceEmblem()); // Whenever you cast a spell, this emblem deals 5 damage to any target.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", ojer);
        addTarget(playerA, playerB); //emblem triggering
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        addTarget(playerA, playerB); //emblem triggering

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 5 - 3 - 5);
    }
}
