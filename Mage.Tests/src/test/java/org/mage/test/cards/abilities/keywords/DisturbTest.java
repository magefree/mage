package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.stack.Spell;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DisturbTest extends CardTestPlayerBase {

    /**
     * Relevant ruling:
     *      - When you cast a spell using a card's disturb ability, the card is put onto the stack with its
     *        back face up. The resulting spell has all the characteristics of that face.
     *      - The mana value of a spell cast using disturb is determined by the mana cost on the
     *        front face of the card, no matter what the total cost to cast the spell was.
     */
    @Test
    public void test_SpellAttributesOnStack() {
        // Disturb {1}{U}
        // Hook-Haunt Drifter
        addCard(Zone.GRAVEYARD, playerA, "Baithook Angler", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", true);

        // cast with disturb
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb");
        checkStackObject("on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", 1);
        runCode("check stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // Stack must contain another card side, so spell/card characteristics must be diff from main side (only mana value is same)
            Spell spell = (Spell) game.getStack().getFirst();
            Assert.assertEquals("Hook-Haunt Drifter", spell.getName());
            Assert.assertEquals(1, spell.getCardType(game).size());
            Assert.assertEquals(CardType.CREATURE, spell.getCardType(game).get(0));
            Assert.assertEquals(1, spell.getSubtype(game).size());
            Assert.assertEquals(SubType.SPIRIT, spell.getSubtype(game).get(0));
            Assert.assertEquals(1, spell.getPower().getValue());
            Assert.assertEquals(2, spell.getToughness().getValue());
            Assert.assertEquals("U", spell.getColor(game).toString());

            Assert.assertEquals(2, spell.getManaValue()); // {1}{U}

            Assert.assertEquals("{1}{U}", spell.getSpellAbility().getManaCosts().getText());
        });

        // must be transformed
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 1);
        checkPT("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 1, 2);
        checkSubType("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", SubType.SPIRIT, true);

        // must be exiled on die
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Hook-Haunt Drifter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 0);
        checkExileCount("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baithook Angler", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * Relevant ruling:
     *      To determine the total cost of a spell, start with the mana cost or alternative cost
     *      (such as a disturb cost) you're paying, add any cost increases, then apply any cost
     *      reductions. The mana value of a spell cast using disturb is determined by the mana cost on
     *      the front face of the card, no matter what the total cost to cast the spell was. (This is
     *      a special rule that applies only to transforming double faced-cards, including ones with
     *      disturb.)
     */
    @Test
    public void test_CostModification_CanPlay() {
        // rules:


        // Disturb {1}{U}
        // Hook-Haunt Drifter
        addCard(Zone.GRAVEYARD, playerA, "Baithook Angler", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCustomEffect_SpellCostModification(playerA, -1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", true);

        // cast with disturb
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb");
        runCode("check stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Spell spell = (Spell) game.getStack().getFirst();
            Assert.assertEquals("mana value must be from main side", 2, spell.getManaValue());
            Assert.assertEquals("mana cost to pay must be modified", "{U}", spell.getSpellAbility().getManaCostsToPay().getText());
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * Check that a cost modification effect that increases the cost of an spell cast properly effects Disturb.
     */
    @Test
    public void test_CostModification_CanNotPlay() {
        // Disturb {1}{U}
        // Hook-Haunt Drifter
        addCard(Zone.GRAVEYARD, playerA, "Baithook Angler", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCustomEffect_SpellCostModification(playerA, 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * Relevant rule:
     *      If you copy a permanent spell cast this way (perhaps with a card like Double Major), the copy becomes
     *      a token that's a copy of the card's back face, even though it isn't itself a double-faced card.
     */
    @Test
    public void test_CopySpell() {
        // Disturb {1}{U}
        // Hook-Haunt Drifter
        addCard(Zone.GRAVEYARD, playerA, "Baithook Angler", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major", 1); // {G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", true);

        // cast with disturb
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb");
        // prepare copy of spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Hook-Haunt Drifter", "Hook-Haunt Drifter");
        checkStackSize("before copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkStackSize("after copy spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * Relevant ruling:
     *      The back face of each card with disturb has an ability that instructs its controller to exile
     *      if it would be put into a graveyard from anywhere. This includes going to the graveyard from the
     *      stack, so if the spell is countered after you cast it using the disturb ability, it will
     *      be put into exile.
     */
    @Test
    public void test_Counter() {
        // Disturb {1}{U}
        // Hook-Haunt Drifter
        addCard(Zone.GRAVEYARD, playerA, "Baithook Angler", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Counter target spell.
        addCard(Zone.HAND, playerA, "Counterspell", 1); // {U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb", true);

        // cast with disturb
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hook-Haunt Drifter with Disturb");
        // counter it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell", "Hook-Haunt Drifter", "Hook-Haunt Drifter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPermanentCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hook-Haunt Drifter", 0);
        checkExileCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Baithook Angler", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/8572
     * Casting an aura using its disturb ability causes it to be immediately exiled.
     */
    @Test
    public void testDisturbAura() {
        String mirrorhallMimic = "Mirrorhall Mimic";
        String ghastlyMimictry = "Ghastly Mimicry";
        String lightningBolt = "Lightning Bolt";

        addCard(Zone.GRAVEYARD, playerA, mirrorhallMimic);
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, lightningBolt);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + ghastlyMimictry + " with Disturb");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, ghastlyMimictry, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, "Alloy Myr");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
        assertExileCount(mirrorhallMimic, 1);
    }
}
