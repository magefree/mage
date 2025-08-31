package org.mage.test.cards.continuous;

import mage.ObjectColor;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Collections;

public class BecomesCreatureEffectTest extends CardTestPlayerBase {
    /*
    Ambush Commander
    {3}{G}{G}
    Creature — Elf
    Forests you control are 1/1 green Elf creatures that are still lands.
    {1}{G}, Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
    2/2
     */
    String ambushCommander = "Ambush Commander";
    /*
    Dryad Arbor
    Land Creature — Forest Dryad
    1/1
     */
    String dryadArbor = "Dryad Arbor";
    /*
    Frogify
    {1}{U}
    Enchantment — Aura
    Enchant creature
    Enchanted creature loses all abilities and is a blue Frog creature with base power and toughness 1/1.
    (It loses all other card types and creature types.)
     */
    String frogify = "Frogify";
    @Test
    public void testBecomesCreatureAllEffect() {
        FilterPermanent filter = new FilterPermanent();
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.ELF.getPredicate());
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));

        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, dryadArbor);
        addCard(Zone.HAND, playerA, ambushCommander);

        runCode("Check forests are not 1/1 Elves", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            int numElves = game.getBattlefield().getActivePermanents(filter, player.getId(), game).size();
            Assert.assertEquals("No 1/1 elves should be present", 0, numElves);
        });
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ambushCommander);
        runCode("Check forests are 1/1 Elves", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            int numElves = game.getBattlefield().getActivePermanents(filter, player.getId(), game).size();
            // 5 forests + dryad arbor
            Assert.assertEquals("There should be 6 1/1 elves present", 6, numElves);
        });

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testBecomesCreatureAttachedEffect() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, dryadArbor);
        addCard(Zone.HAND, playerA, frogify);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, frogify, dryadArbor);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbilities(playerA, dryadArbor, Collections.emptyList());
        assertPowerToughness(playerA, dryadArbor, 1, 1);
        assertType(dryadArbor, CardType.CREATURE, SubType.FROG);
        assertNotSubtype(dryadArbor, SubType.DRYAD);
        assertNotType(dryadArbor, CardType.LAND);
        assertColor(playerA, dryadArbor, ObjectColor.BLUE, true);
        assertColor(playerA, dryadArbor, ObjectColor.GREEN, false);
    }
}
