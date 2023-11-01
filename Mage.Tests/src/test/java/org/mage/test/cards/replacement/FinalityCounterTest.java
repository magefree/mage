package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class FinalityCounterTest extends CardTestPlayerBase {

    private static final String viper = "Soulcoil Viper";
    private static final String corpse = "Walking Corpse";

    @Test
    public void testCounterAdded() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, viper);
        addCard(Zone.GRAVEYARD, playerA, corpse);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B},", corpse);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, viper, 0);
        assertGraveyardCount(playerA, viper, 1);
        assertGraveyardCount(playerA, corpse, 0);
        assertCounterCount(playerA, corpse, CounterType.FINALITY, 1);
    }

    private static final String murder = "Murder";

    @Test
    public void testCounterApplies() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 3);
        addCard(Zone.BATTLEFIELD, playerA, viper);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, corpse);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B},", corpse);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, corpse);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, viper, 0);
        assertGraveyardCount(playerA, viper, 1);
        assertPermanentCount(playerA, corpse, 0);
        assertGraveyardCount(playerA, corpse, 0);
        assertExileCount(playerA, corpse, 1);
    }

    private static final String hexmage = "Vampire Hexmage";

    @Test
    public void testCounterRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 3);
        addCard(Zone.BATTLEFIELD, playerA, viper);
        addCard(Zone.BATTLEFIELD, playerA, hexmage);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, corpse);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B},", corpse);

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Sacrifice", corpse);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, corpse);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, viper, 0);
        assertGraveyardCount(playerA, viper, 1);
        assertPermanentCount(playerA, corpse, 0);
        assertGraveyardCount(playerA, corpse, 1);
        assertExileCount(playerA, corpse, 0);
    }

    private static final String unsummon = "Unsummon";

    @Test
    public void testCounterDontApply() {
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 1 + 1);
        addCard(Zone.BATTLEFIELD, playerA, viper);
        addCard(Zone.HAND, playerA, unsummon);
        addCard(Zone.GRAVEYARD, playerA, corpse);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B},", corpse);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, unsummon, corpse);

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, viper, 0);
        assertGraveyardCount(playerA, viper, 1);
        assertPermanentCount(playerA, corpse, 0);
        assertHandCount(playerA, corpse, 1);
        assertExileCount(playerA, corpse, 0);
    }
}
