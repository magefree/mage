package org.mage.test.cards.single.dsk;

import mage.abilities.mana.GreenManaAbility;
import mage.constants.*;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author markort147
 */
public class UnableToScreamTest extends CardTestPlayerBase {

    public static final String UNABLE_TO_SCREAM = "Unable to Scream";

    @Test
    public void cannotTurnFaceUpTest() {
        String akroma = "Akroma, Angel of Fury";
        String breakOpen = "Break Open";

        addCard(Zone.HAND, playerB, akroma);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);

        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.HAND, playerA, breakOpen, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, akroma + " using Morph");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, breakOpen, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        assertPowerToughness(playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0, 2);
    }

    @Test
    public void turnFaceUpAfterRemovingAuraTest() {
        String akroma = "Akroma, Angel of Fury";
        String breakOpen = "Break Open";
        String removal = "Appetite for the Unnatural";

        addCard(Zone.HAND, playerB, akroma);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.HAND, playerB, removal);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.HAND, playerA, breakOpen, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, akroma + " using Morph");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, removal, UNABLE_TO_SCREAM);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, breakOpen, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 0);
        assertPermanentCount(playerB, akroma, 1);
    }


    @Test
    public void becomesToyTest() {
        String elves = "Llanowar Elves";

        addCard(Zone.BATTLEFIELD, playerB, elves);
        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, elves);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, elves, 1);
        assertPermanentCount(playerA, UNABLE_TO_SCREAM, 1);
        assertPowerToughness(playerB, elves, 0, 2);
        assertAbility(playerB, elves, new GreenManaAbility(), false);
        List<CardType> types = Arrays.asList(CardType.CREATURE, CardType.ARTIFACT);
        List<SubType> subTypes = Arrays.asList(SubType.TOY, SubType.ELF, SubType.DRUID);
        types.forEach(t ->
                subTypes.forEach(st ->
                        assertType(elves, t, st)
                )
        );
    }

    @Test
    public void removeAuraTest() {
        String elves = "Llanowar Elves";
        String removal = "Appetite for the Unnatural";

        addCard(Zone.BATTLEFIELD, playerB, elves);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);
        addCard(Zone.HAND, playerB, removal);
        addCard(Zone.HAND, playerA, UNABLE_TO_SCREAM);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, UNABLE_TO_SCREAM, elves);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, removal, UNABLE_TO_SCREAM);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, elves, 1);
        assertPermanentCount(playerA, UNABLE_TO_SCREAM, 0);

        assertPowerToughness(playerB, elves, 1, 1);
        assertType(elves, CardType.CREATURE, true);
        assertType(elves, CardType.ARTIFACT, false);
        assertSubtype(elves, SubType.ELF);
        assertSubtype(elves, SubType.DRUID);
        assertNotSubtype(elves, SubType.TOY);
        assertAbility(playerB, elves, new GreenManaAbility(), true);
    }
}
