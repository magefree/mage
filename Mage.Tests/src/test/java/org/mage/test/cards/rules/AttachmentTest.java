package org.mage.test.cards.rules;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 */
public class AttachmentTest extends CardTestPlayerBase {

    // {T}: Attach target Aura or Equipment you control to target creature you control. Activate only as a sorcery.
    private static final String codsworth = "Codsworth, Handy Helper";

    /**
     * Tests that a permanent that becomes non-attachable (i.e., non-aura, non-equipment, non-fortification)
     * due to loss of card type before resolution of an ability is not still attached.
     */
    @Test
    public void testAttachNonAttachable() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.BATTLEFIELD, playerA, "Lion Sash"); // Reconfigurable equipment creature
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Orrery"); // You may cast spells as though they had flash
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Darksteel Mutation"); // Enchanted creature loses equipment type

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Lion Sash");
        addTarget(playerA, codsworth);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Mutation", "Lion Sash");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Lion Sash", codsworth, false);
        assertGraveyardCount(playerA, "Darksteel Mutation", 0);
        assertType("Lion Sash", CardType.CREATURE, true);
    }

    /**
     * Tests that protection prevents attachment. Attachment should remain attached to whatever it was attached to.
     */
    @Test
    public void testProtectionPreventsAttachment() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.BATTLEFIELD, playerA, "Candlestick"); // Blue equipment
        addCard(Zone.HAND, playerA, "Agoraphobia"); // Blue aura
        addCard(Zone.BATTLEFIELD, playerA, "Bloated Toad"); // Protection from blue
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", codsworth);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agoraphobia", codsworth);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Candlestick");
        addTarget(playerA, "Bloated Toad");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Agoraphobia");
        addTarget(playerA, "Bloated Toad");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Candlestick", codsworth, true);
        assertAttachedTo(playerA, "Agoraphobia", codsworth, true);
    }

    /**
     * Tests that attachments fall off a permanent that gains the appropriate protection.
     */
    @Test
    public void testProtectionShedsAttachments() {
        setStrictChooseMode(true);

        // {R}: {this} gains protection from red until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Keeper of Kookus");
        addCard(Zone.BATTLEFIELD, playerA, "Jackhammer"); // Red equipment
        addCard(Zone.HAND, playerA, "Agility"); // Red aura
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Keeper of Kookus");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Agility", "Keeper of Kookus");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Agility", 1);
        assertPermanentCount(playerA, "Jackhammer", 1);
        assertAttachedTo(playerA, "Jackhammer", "Keeper of Kookus", false);
    }

    /**
     * Tests that attachments can only attach to legal targets. Specifically,
     * that an "Enchant land" aura cannot be attached to a nonland creature.
     */
    @Test
    public void testDeniedMoveIllegalAuraTarget() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Wild Growth"); // Enchant land aura

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Wild Growth");
        addTarget(playerA, codsworth);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wild Growth", 0);
        assertAttachedTo(playerA, "Wild Growth", "Forest", true);
    }

    /**
     * Tests that an equipment cannot be moved to a noncreature permanent.
     * Equipment should remain attached to whatever it was attached to.
     */
    @Test
    public void testDeniedMoveIllegalEquipmentTarget() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter"); // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Bonebreaker Giant"); // Arbitrary creature
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Orrery"); // You may cast spells as though they had flash
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "One with the Stars"); // Enchanted permanent loses creature type

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", codsworth);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Bonesplitter");
        addTarget(playerA, "Bonebreaker Giant");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One with the Stars", "Bonebreaker Giant");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAttachedTo(playerA, "Bonesplitter", codsworth, true);
        assertGraveyardCount(playerA, 0);
    }
}
