package org.mage.test.cards.rules;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
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

    /**
     * Tests that an enchant ability prohibition not pertaining to card type is respected when trying to move an aura.
     * In this case, an "Enchant creature you don't control" aura shouldn't be movable to a controlled creature.
     */
    @Test
    public void testDeniedMoveIllegalAuraTargetNotCardType() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Captured by the Consulate"); // Enchant creature you don't control
        addCard(Zone.BATTLEFIELD, playerB, "Pia Nalaar"); // Arbitrary creature

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}.", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Captured by the Consulate", "Pia Nalaar");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Captured by the Consulate");
        addTarget(playerA, codsworth);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 0);
        assertAttachedTo(playerB, "Captured by the Consulate", "Pia Nalaar", true);
    }

    /**
     * Tests that an aura that attaches to a player (specifically an opponent in this case) cannot attach to a permanent.
     */
    @Test
    public void testDeniedMoveIllegalPlayerAuraTarget() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.HAND, playerA, "Psychic Possession"); // Enchant opponent
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}.", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Possession", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Psychic Possession");
        addTarget(playerA, codsworth);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Psychic Possession", 0); // TODO: Currently, the aura goes to graveyard. Must fix
        assertAttachedTo(playerA, "Psychic Possession", codsworth, false);
    }

    /**
     * Tests that an aura that attaches to a card in the graveyard cannot attach to a permanent.
     */
    @Test
    public void testDeniedMoveIllegalCardAuraTarget() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, codsworth);
        addCard(Zone.HAND, playerA, "Spellweaver Volute"); // Enchant instant card in graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.GRAVEYARD, playerA, "Counterspell"); // Arbitrary instant

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}.", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spellweaver Volute", "Counterspell");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Attach", "Spellweaver Volute");
        addTarget(playerA, codsworth);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Spellweaver Volute", 0); // TODO: Currently goes to graveyard. Must fix
        assertAttachedTo(playerA, "Spellweaver Volute", codsworth, false);
    }

    /**
     * Tests that when a player gains protection from an aura, it falls off (is moved to the graveyard).
     */
    @Test
    public void testCurseFallsOffFromGainedProtection() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Curse of Opulence"); // Arbitrary curse (enchant player)
        addCard(Zone.HAND, playerB, "Runed Halo"); // You have protection from the chosen card name.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Opulence", playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Runed Halo");
        setChoice(playerB, "Curse of Opulence");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Curse of Opulence", 1);
    }

    /**
     * Tests that when an aura is cast and the target becomes illegal (in this
     * case, due to a change in card type), the aura goes to the graveyard.
     */
    @Test
    public void testCastAuraIllegalTarget() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Arcane Flight"); // Arbitrary "enchant creature" aura
        addCard(Zone.HAND, playerA, "One with the Stars"); // Enchanted permanent loses creature type
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // Arbitrary creature
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Orrery"); // You may cast spells as though they had flash
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Flight", "Runeclaw Bear");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One with the Stars", "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Arcane Flight", 1);
    }

    /**
     * Tests that when an aura tries to move from a player's hand without being cast, and
     * it has no legal objects to attach to, it instead remains in the player's hand.
     */
    @Test
    public void testAuraMoveFromHandWithNoAttachableObject() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Show and Tell"); // Puts an aura from hand onto the battlefield without casting
        addCard(Zone.HAND, playerA, "Aether Tunnel"); // Enchant creature
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Show and Tell");
        setChoice(playerA, true); // yes, put from hand to battle
        addTarget(playerA, "Aether Tunnel");
        setChoice(playerB, false); // no, do not put

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Aether Tunnel", 0);
        assertHandCount(playerA, "Aether Tunnel", 1);
    }

    /**
     * Tests that when an aura tries to move from a player's graveyard without being cast, and
     * it has no legal objects to attach to, it instead remains in the player's graveyard.
     */
    @Test
    public void testAuraMoveFromGraveyardWithNoAttachableObject() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Replenish"); // Put all enchantments from graveyard onto battlefield
        addCard(Zone.GRAVEYARD, playerA, "Divine Favor"); // Enchant creature, gain 3 life on ETB
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Divine Favor", 1);
        assertLife(playerA, 20);
    }

    /**
     * Tests that when an aura tries to move from exile without being cast, and
     * it has no legal objects to attach to, it instead remains in exile.
     */
    @Test
    public void testAuraMoveFromExileWithNoAttachableObject() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Sudden Disappearance"); // Exile nonland permanents, return at end of turn
        addCard(Zone.HAND, playerA, "Spiritual Visit"); // Create a 1/1 token
        addCard(Zone.HAND, playerA, "Divine Favor"); // Enchant creature, gain 3 life on ETB
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 9);


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spiritual Visit");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divine Favor", "Spirit Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sudden Disappearance", playerA);
        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertGraveyardCount(playerA, "Divine Favor", 0);
        assertExileCount(playerA, "Divine Favor", 1);
        assertLife(playerA, 23);
    }

    /**
     * Tests that Dream Leash can correctly attach to untapped permanents when it enters through non-casting means (e.g., via Show and Tell)
     */
    @Ignore
    @Test
    public void testDreamLeashNoUntappedPermanents() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        // Enchant permanent
        // You can't choose an untapped permanent as Dream Leash's target as you cast Dream Leash.
        // You control enchanted permanent.
        addCard(Zone.HAND, playerA, "Dream Leash");
        addCard(Zone.HAND, playerA, "Show and Tell"); // Puts a permanent directly into play without casting

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Show and Tell");
        setChoice(playerA, true, 2); // 1. Cast without paying cost? 2. Put permanent into play?
        setChoice(playerB, false); // Put permanent into play?
        addTarget(playerA, "Dream Leash"); // Show and Tell's target
        setChoice(playerA, "Omniscience"); // Dream Leash's choice

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dream Leash", 0);
        assertAttachedTo(playerA, "Dream Leash", "Omniscience", true);
        assertHandCount(playerA, "Dream Leash", 0);
    }
}
