package org.mage.test.cards.single.vow;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class CemeteryIlluminatorTest extends CardTestPlayerBase {

    /*
     Cemetery Illuminator {1}{U}{U}
     Creature — Spirit
     Flying
     Whenever Cemetery Illuminator enters the battlefield or attacks, exile a card from a graveyard.
     You may look at the top card of your library any time.
     Once each turn, you may cast a spell from the top of your library if it shares a card type with a card exiled with Cemetery Illuminator.
     */
    private static final String ci = "Cemetery Illuminator";

    private static final String unicorn = "Lonesome Unicorn"; // 4W 3/3 with adventure 2W 2/2 token
    private static final String rider = "Rider in Need";
    private static final String knight = "Knight Token";
    private static final String sorcery = "Sign in Blood";
    private static final String creature = "Walking Corpse";

    @Test
    public void testCreature() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, unicorn);
        addCard(Zone.GRAVEYARD, playerA, creature);
        addCard(Zone.HAND, playerA, ci);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ci);
        setChoice(playerA, creature); // to exile on ETB

        checkPlayableAbility("creature", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + unicorn, true);
        checkPlayableAbility("sorcery", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + rider, false);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, unicorn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, creature, 1);
        assertPermanentCount(playerA, ci, 1);
        assertPermanentCount(playerA, unicorn, 1);
    }

    @Test
    public void testSorcery() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, unicorn);
        addCard(Zone.GRAVEYARD, playerA, sorcery);
        addCard(Zone.HAND, playerA, ci);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ci);
        setChoice(playerA, sorcery); // to exile on ETB

        checkPlayableAbility("creature", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + unicorn, false);
        checkPlayableAbility("sorcery", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + rider, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, rider);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, sorcery, 1);
        assertPermanentCount(playerA, ci, 1);
        assertPermanentCount(playerA, knight, 1);
    }

    @Test
    public void testMorph() {
        String whetwheel = "Whetwheel"; // Artifact
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, whetwheel);
        addCard(Zone.GRAVEYARD, playerA, creature);
        addCard(Zone.HAND, playerA, ci);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ci);
        setChoice(playerA, creature); // to exile on ETB

        // test framework can't pick this up, but it works correctly
        // checkPlayableAbility("artifact", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + whetwheel, false);
        checkPlayableAbility("creature", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + whetwheel + " using Morph", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, whetwheel + " using Morph");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, creature, 1);
        assertPermanentCount(playerA, ci, 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPermanentCount(playerA, whetwheel, 0);
    }

}
