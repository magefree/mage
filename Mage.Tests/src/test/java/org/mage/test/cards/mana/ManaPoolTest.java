package org.mage.test.cards.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.abilities.mana.builder.common.SimpleActivatedAbilityManaBuilder;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.TargetSpell;
import mage.target.common.TargetAnyTarget;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ManaPoolTest extends CardTestPlayerBase {

    @Test
    public void test_OneMana_OneSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_MultipleMana_OneSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Precision Bolt"); // {2}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Precision Bolt", playerB);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_OneSpell() {
        // +1: Add {R}{R}{R}. Spend this mana only to cast instant or sorcery spells.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard");
        addCard(Zone.HAND, playerA, "Precision Bolt"); // {2}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Add {R}{R}{R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Precision Bolt", playerB);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_MultipleSpells() {
        // +1: Add {R}{R}{R}. Spend this mana only to cast instant or sorcery spells.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2); // {R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Add {R}{R}{R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 3);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3 * 2);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_MultipleMana_OneXSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Volcanic Geyser"); // {X}{R}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=2");

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 2);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_MultipleMana_MultipleXSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4 * 2);
        addCard(Zone.HAND, playerA, "Volcanic Geyser", 2); // {X}{R}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        //
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4 * 2);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=2");

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 2 * 2);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_OneXSpell() {
        addCustomCardWithAbility("add 10", playerA, new SimpleActivatedAbility(Zone.ALL,
                new AddConditionalManaEffect(Mana.RedMana(10), new InstantOrSorcerySpellManaBuilder()),
                new ManaCostsImpl<>("")));
        addCard(Zone.HAND, playerA, "Volcanic Geyser"); // {X}{R}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Add {R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10);

        // use for spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10 - 3);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_MultipleXSpell() {
        addCustomCardWithAbility("add 10", playerA, new SimpleActivatedAbility(Zone.ALL,
                new AddConditionalManaEffect(Mana.RedMana(10), new InstantOrSorcerySpellManaBuilder()),
                new ManaCostsImpl<>("")));
        addCard(Zone.HAND, playerA, "Volcanic Geyser", 2); // {X}{R}{R}

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Add {R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10);

        // use for spell 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10 - 3);

        // use for spell 2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Volcanic Geyser", playerB);
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10 - 3 * 2);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 1 * 2);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_MultipleMana_OneXAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetAnyTarget());
        addCustomCardWithAbility("damage X", playerA, ability);

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4);

        // use for ability
        // showAvailableAbilities("before ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}:", playerB);
        setChoice(playerA, "X=3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4 - 3);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_OneXAbility() {
        addCustomCardWithAbility("add 10", playerA, new SimpleActivatedAbility(Zone.ALL,
                new AddConditionalManaEffect(Mana.RedMana(10), new SimpleActivatedAbilityManaBuilder()),
                new ManaCostsImpl<>("")));
        //
        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetAnyTarget());
        addCustomCardWithAbility("damage X", playerA, ability);

        // make mana
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Add {R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10);

        // use for ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}:", playerB);
        setChoice(playerA, "X=3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10 - 3);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_MultipleMana_OneXPart() {
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 3 + 1);
        //
        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>(""));
        ability.addTarget(new TargetAnyTarget());
        addCustomCardWithAbility("damage X", playerA, ability);
        //
        // {X}: Counter target spell
        ability = new SimpleActivatedAbility(Zone.ALL, new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetSpell());
        addCustomCardWithAbility("counter until pay X", playerB, ability);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        // make mana for spell
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // make mana for pay X to prevent
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}"); // one must be saved in pool
        checkManaPool("mana prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4);

        // counter by X=3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{X}: Counter");
        setChoice(playerB, "X=3");
        addTarget(playerB, "Lightning Bolt");
        // pay to prevent
        setChoice(playerA, true); // pay 3 to prevent counter

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 4 - 3);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ConditionalMana_OneXPart() {
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCustomCardWithAbility("add 10", playerA, new SimpleManaAbility(Zone.ALL,
                new AddConditionalManaEffect(Mana.RedMana(10), new SimpleActivatedAbilityManaBuilder()),
                new ManaCostsImpl<>("")));
        //
        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>(""));
        ability.addTarget(new TargetAnyTarget());
        addCustomCardWithAbility("damage X", playerA, ability);
        //
        // {X}: Counter target spell
        ability = new SimpleActivatedAbility(Zone.ALL, new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetSpell());
        addCustomCardWithAbility("counter until pay X", playerB, ability);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        // make mana for spell
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        checkManaPool("mana spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);
        // cast spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // make mana for pay X to prevent
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Add {R}");
        checkManaPool("mana prevent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10);

        // counter by X=3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{X}: Counter");
        setChoice(playerB, "X=3");
        addTarget(playerB, "Lightning Bolt");
        // pay to prevent
        setChoice(playerA, true); // pay 3 to prevent counter

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkManaPool("mana after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 10 + 1 - 1 - 3);

        checkLife("after", 1, PhaseStep.END_TURN, playerB, 20 - 3);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }
}
