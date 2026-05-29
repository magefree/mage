package org.mage.test.cards.abilities.keywords;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class OffspringTest extends CardTestPlayerBase {

    private static final String vinelasher = "Iridescent Vinelasher";
    private static final String bandit = "Prosperous Bandit";
    private static final String lion = "Silvercoat Lion";
    private static final String genericGrantSource = "Generic Offspring Grant Source";

    // CR 702.175a/b: Offspring is an additional cost and creates a linked ETB trigger; multiple instances are paid separately.
    // CR 607.2i, 607.5: linked abilities remain linked per instance, including abilities gained from other effects.
    // Keep these mechanic regressions generic; actual Zinnia coverage stays in ZinniaValleysVoiceTest.

    private void addGenericOffspringGrantSource(String name) {
        // Zinnia is currently the only printed card in the test card pool that grants Offspring to creature spells.
        // This helper isolates the generic granted-Offspring engine path and lets these tests cover multiple
        // independent grant sources without mixing in Zinnia-specific copy/legend/creature behavior.
        FilterNonlandCard creatureSpells = new FilterNonlandCard("creature spells");
        creatureSpells.add(CardType.CREATURE.getPredicate());
        addCustomCardWithAbility(
                name,
                playerA,
                new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new GainAbilityControlledSpellsEffect(new OffspringAbility("{2}"), creatureSpells)
                ),
                null,
                CardType.ENCHANTMENT,
                "",
                Zone.BATTLEFIELD
        );
    }

    private Permanent getCreature(String name, boolean isToken) {
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(playerA.getId(), currentGame)) {
            if (name.equals(permanent.getName()) && (permanent instanceof PermanentToken) == isToken) {
                return permanent;
            }
        }
        return null;
    }

    private void checkOffspring(String name, int power, int toughness, boolean paid) {
        assertPermanentCount(playerA, name, paid ? 2 : 1);
        assertTokenCount(playerA, name, paid ? 1 : 0);

        Permanent original = getCreature(name, false);
        Assert.assertEquals(
                "Original creature should have power " + power,
                power, original.getPower().getValue()
        );
        Assert.assertEquals(
                "Original creature should have toughness " + toughness,
                toughness, original.getToughness().getValue()
        );
        if (!paid) {
            return;
        }
        Permanent token = getCreature(name, true);
        Assert.assertEquals(
                "Token creature should have power 1",
                1, token.getPower().getValue()
        );
        Assert.assertEquals(
                "Token creature should have toughness 1",
                1, token.getToughness().getValue()
        );
    }

    @Test
    public void testNoPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, vinelasher);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        checkOffspring(vinelasher, 1, 2, false);
    }

    @Test
    public void testPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, vinelasher);

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        checkOffspring(vinelasher, 1, 2, true);
    }

    @Test
    public void testHumilityInResponseNoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, vinelasher);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Vedalken Orrery");
        addCard(Zone.HAND, playerB, "Humility");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Humility", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vinelasher, 1);
        assertTokenCount(playerA, vinelasher, 0);
        assertPowerToughness(playerA, vinelasher, 1, 1);
    }

    @Test
    public void testHumilityInResponseNoCopyWithPrintedAndGrantedOffspring() {
        // Use a generic noncreature grant source so this stays a mechanic regression, not a Zinnia card test.
        addGenericOffspringGrantSource(genericGrantSource);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, vinelasher);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Vedalken Orrery");
        addCard(Zone.HAND, playerB, "Humility");

        setChoice(playerA, true);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vinelasher);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Humility", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vinelasher, 1);
        assertTokenCount(playerA, vinelasher, 0);
        assertPowerToughness(playerA, vinelasher, 1, 1);
    }

    @Test
    public void testTwoGrantedOffspringAbilitiesOnePayment() {
        addGenericOffspringGrantSource("Generic Offspring Grant Source A");
        addGenericOffspringGrantSource("Generic Offspring Grant Source B");

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, lion);

        setChoice(playerA, true);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 2);
        assertTokenCount(playerA, lion, 1);
    }

    @Test
    public void testGrantedOffspringSourceRemovedBeforeEtbNoCopy() {
        addGenericOffspringGrantSource(genericGrantSource);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, lion);
        addCard(Zone.HAND, playerA, "Disenchant");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lion);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disenchant", genericGrantSource);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, lion, 1);
        assertTokenCount(playerA, lion, 0);
    }

    @Test
    public void testPrintedAndGrantedOffspringOnePayment() {
        addGenericOffspringGrantSource(genericGrantSource);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, bandit);

        setChoice(playerA, true);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bandit, 2);
        assertTokenCount(playerA, bandit, 1);
    }

    @Test
    public void testPrintedAndGrantedOffspringTwoPayments() {
        addGenericOffspringGrantSource(genericGrantSource);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, bandit);

        setChoice(playerA, true);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, bandit); // stack both offspring triggers (2 triggers -> 1 choice)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bandit, 3);
        assertTokenCount(playerA, bandit, 2);
    }

    @Test
    public void testCopyingSpellMustKeepOffspringStatus() {
        addCard(Zone.HAND, playerA, bandit, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Double Major", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, true); // pay offspring once

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", bandit, bandit, StackClause.WHILE_ON_STACK);
        checkStackSize("before copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // bandit + double major
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + copy

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bandit, 4);
        assertTokenCount(playerA, bandit, 3);
    }

    @Test
    public void testCopyingEtbTriggerMustKeepOffspringStatus() {
        addCard(Zone.HAND, playerA, bandit, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, true); // pay offspring once
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);

        // bandit resolved, its offspring trigger is on the stack; copy that trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.");

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bandit, 3);
        assertTokenCount(playerA, bandit, 2);
    }

    @Test
    public void testPrintedAndGrantedOffspringRollbackClearsOldPayments() {
        addGenericOffspringGrantSource(genericGrantSource);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, bandit);

        // first line: pay both offspring costs, then roll back to the start of the turn
        setChoice(playerA, true);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);
        setChoice(playerA, bandit);

        rollbackTurns(1, PhaseStep.BEGIN_COMBAT, playerA, 0);
        rollbackAfterActionsStart();

        // after rollback, only the printed offspring payment should be remembered
        setChoice(playerA, true);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bandit);

        rollbackAfterActionsEnd();

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bandit, 2);
        assertTokenCount(playerA, bandit, 1);
    }

}
