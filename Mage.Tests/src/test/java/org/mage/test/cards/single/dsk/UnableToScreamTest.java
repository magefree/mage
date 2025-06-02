package org.mage.test.cards.single.dsk;

import mage.abilities.Ability;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.constants.*;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author markort147
 */
public class UnableToScreamTest extends CardTestPlayerBase {

    /*
     * Unable to Scream {U}
     * Enchantment - Aura
     * Enchant creature
     * Enchanted creature loses all abilities and is a Toy artifact creature with base power and toughness 0/2 in addition to its other types.
     * As long as enchanted creature is face down, it can't be turned face up.
     */
    public static final String UNABLE_TO_SCREAM = "Unable to Scream";

    /*
     * Appetite for the Unnatural {2}{G}
     * Instant
     * Destroy target artifact or enchantment. You gain 2 life.
     */
    public static final String APPETITE_FOR_THE_UNNATURAL = "Appetite for the Unnatural";


    // Cast a spell that would turn the creature face up, but Unable to Scream negates the effect.
    // After removing Unable to Scream, cast the spell again and the effect applies correctly.
    @Test
    public void preventFromTurningFaceUpTest() {
        /*
         * Akroma, Angel of Fury {5}{R}{R}{R}
         * Creature - Legendary Angel
         * 6/6
         * Akroma, Angel of Fury can't be countered.
         * Flying
         * Trample
         * Protection from white and from blue.
         * {R}: Akroma, Angel of Fury gets +1/+0 until end of turn.
         * Morph {3}{R}{R}{R}
         */
        final String akromaAngelOfFury = "Akroma, Angel of Fury";

        /*
         * Break Open {1}{R}
         * Instant
         * Turn target face-down creature an opponent controls face up.
         */
        final String breakOpen = "Break Open";

        setStrictChooseMode(true);

        addCard(Zone.HAND, playerB, akromaAngelOfFury);
        addCard(Zone.HAND, playerB, APPETITE_FOR_THE_UNNATURAL);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.HAND, playerA, breakOpen, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // prepare the face down creature
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, akromaAngelOfFury + " using Morph");
        // attach Unable to Scream to it
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), true);
        // cast a spell that would turn the creature face up
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, breakOpen, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        // the creature is still face down
        checkPermanentCount("Face down creature is on the battlefield", 3, PhaseStep.BEGIN_COMBAT, playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        checkPermanentCount("Akroma is not on the battlefield", 3, PhaseStep.BEGIN_COMBAT, playerB, akromaAngelOfFury, 0);

        // destroy Unable to Scream
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, APPETITE_FOR_THE_UNNATURAL, UNABLE_TO_SCREAM);
        // cast a spell that would turn the creature face up
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, breakOpen, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        // now the creature is turned face up
        checkPermanentCount("Face down creature is not on the battlefield", 5, PhaseStep.BEGIN_COMBAT, playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        checkPermanentCount("Akroma is on the battlefield", 5, PhaseStep.BEGIN_COMBAT, playerB, akromaAngelOfFury, 1);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    // Unable to Scream changes type, subtype and base stats of a creature.
    // After removing Unable to Scream, the creature restores its type, subtype, and base stats.
    @Test
    public void becomesEffectTest() {
        /*
         * Llanowar Elves {G}
         * Creature - Elf Druid
         * 1/1
         * {T}: Add {G}.
         */
        final String llanowarElves = "Llanowar Elves";

        setStrictChooseMode(true);

        addCard(Zone.HAND, playerB, APPETITE_FOR_THE_UNNATURAL);
        addCard(Zone.BATTLEFIELD, playerB, llanowarElves);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // attach Unable to Scream to the creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, llanowarElves);

        // the creature is an artifact creature, toy elf druid with base stats 0/2
        runCode("The creature is an artifact creature, toy elf druid with base stats 0/2", 1, PhaseStep.BEGIN_COMBAT, playerB,
                (info, player, game) -> {
                    Card elves = game.getBattlefield().getAllActivePermanents().stream().filter(p -> p.getName().equals(llanowarElves)).findAny().orElse(null);
                    Assert.assertNotNull("The creature must be on the battlefield", elves);
                    Assert.assertEquals("The creature must have base power 0", 0, elves.getPower().getValue());
                    Assert.assertEquals("The creature must have base toughness 2", 2, elves.getToughness().getValue());
                    Assert.assertTrue("The creature must have lost its abilities", elves.getAbilities(game).isEmpty());
                    Assert.assertEquals("The creature must be an artifact creature", new HashSet<>(Arrays.asList(CardType.ARTIFACT, CardType.CREATURE)), new HashSet<>(elves.getCardType(game)));
                    Assert.assertEquals("The creature must be a Toy Elf Druid", new HashSet<>(Arrays.asList(SubType.TOY, SubType.ELF, SubType.DRUID)), new HashSet<>(elves.getSubtype(game)));
                }
        );

        // destroy Unable to Scream
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, APPETITE_FOR_THE_UNNATURAL, UNABLE_TO_SCREAM);

        // the creature is a creature, elf druid with base stats 1/1
        runCode("The creature is creature, toy elf druid with base stats 1/1", 2, PhaseStep.BEGIN_COMBAT, playerB,
                (info, player, game) -> {
                    Card elves = game.getBattlefield().getAllActivePermanents().stream().filter(p -> p.getName().equals(llanowarElves)).findAny().orElse(null);
                    Assert.assertNotNull("The creature must be on the battlefield", elves);
                    Assert.assertEquals("The creature must have base power 1", 1, elves.getPower().getValue());
                    Assert.assertEquals("The creature must have base toughness 1", 1, elves.getToughness().getValue());
                    Assert.assertTrue("The creature must have restored its abilities", elves.getAbilities(game).stream().map(Ability::getClass).collect(Collectors.toList()).contains(GreenManaAbility.class));
                    Assert.assertEquals("The creature must be just a creature", Collections.singleton(CardType.CREATURE), new HashSet<>(elves.getCardType(game)));
                    Assert.assertEquals("The creature must be an Elf Druid", new HashSet<>(Arrays.asList(SubType.ELF, SubType.DRUID)), new HashSet<>(elves.getSubtype(game)));
                }
        );

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
