package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class AbandonedSarcophagusTest extends CardTestPlayerBase {

    /**
     * You may cast non-land card with cycling from your graveyard
     */
    @Test
    public void castNonLandFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerA, "Astral Drift"); // {2}{W} Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Ash Barrens"); // Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Astral Drift");
        // Can't play lands with this ability
        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Ash Barrens", false);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Astral Drift", 1);
    }

    /**
     * You can only cast the card from the graveyard, you CANNOT cycle it
     */
    @Test
    public void cantCycleFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.GRAVEYARD, playerA, "Astral Drift"); // {2}{W} Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Ash Barrens"); // Land

        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling", false);
        checkPlayableAbility("before play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basic landcycling", false);

        setStrictChooseMode(true);
        execute();

    }

    /**
     * When a card with cycling is cycled it still goes to the graveyard
     */
    @Test
    public void cycledCardGoesToGraveyard() {
        addCard(Zone.LIBRARY, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Astral Drift"); // {2}{W} Enchantment
        addCard(Zone.HAND, playerA, "Ash Barrens"); // Land

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basic landcycling");
        addTarget(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, 2);
        assertGraveyardCount(playerA, 2);
    }

    /**
     * When a card goes to the graveyard and it WAS NOT cycled, it gets exiled
     */
    @Test
    public void nonCycledCardGoesToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Abandoned Sarcophagus");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Astral Drift"); // {2}{W} Enchantment
        addCard(Zone.HAND, playerA, "Ash Barrens"); // Land
        addCard(Zone.HAND, playerA, "Beast Within", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Astral Drift", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ash Barrens");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beast Within", "Astral Drift");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beast Within", "Ash Barrens");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 2);
    }
}
