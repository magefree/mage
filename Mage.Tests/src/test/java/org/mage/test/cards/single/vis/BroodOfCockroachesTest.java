package org.mage.test.cards.single.vis;

import java.util.UUID;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static mage.constants.Zone.*;
import static mage.constants.PhaseStep.*;

public class BroodOfCockroachesTest extends CardTestPlayerBase {

    private static final int TURN_1 = 1;
    private static final int ANY_LIFE_TOTAL = 17;
    private static final String BROOD_OF_COCKROACHES = "Brood of Cockroaches";
    private static final String SHOCK = "Shock";

    @Test
    public void should_display_correct_text() {
        String expectedText = "When {this} dies, at the beginning of the next end step, you lose 1 life and return Brood of Cockroaches to your hand.";

        playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase();

        setStopAt(TURN_1, END_TURN);
        execute();

        Permanent permanent = getPermanent(BROOD_OF_COCKROACHES, playerA);
        assertThat(permanent.getAbilities().get(1).toString(), is(expectedText));

    }

    @Test
    public void should_reduce_life_of_playerA_by_1_at_the_beginning_of_the_next_end_step() {
        playerA.setLife(ANY_LIFE_TOTAL, currentGame, UUID.randomUUID());

        playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase();

        brood_of_cockroaches_diesat_precombat_main_phase();

        setStopAt(TURN_1, END_TURN);
        execute();

        assertLife(playerA, ANY_LIFE_TOTAL - 1);
    }

    @Test
    public void should_not_reduce_life_of_playerA_by_1_at_post_combat_main_step() {
        playerA.setLife(ANY_LIFE_TOTAL, currentGame, UUID.randomUUID());

        playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase();

        brood_of_cockroaches_diesat_precombat_main_phase();

        setStopAt(TURN_1, PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, ANY_LIFE_TOTAL);
    }

    @Test
    public void should_return_Brood_of_Cockroaches_to_playerA_hand_end_of_turn() {
        playerA.setLife(ANY_LIFE_TOTAL, currentGame, UUID.randomUUID());

        playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase();

        brood_of_cockroaches_diesat_precombat_main_phase();

        setStopAt(TURN_1, END_TURN);
        execute();

        assertHandCount(playerA, BROOD_OF_COCKROACHES, 1);
    }

    @Test
    public void should_not_return_Brood_of_Cockroaches_to_playerA_at_post_combat_step() {
        playerA.setLife(ANY_LIFE_TOTAL, currentGame, UUID.randomUUID());

        playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase();

        brood_of_cockroaches_diesat_precombat_main_phase();

        setStopAt(TURN_1, POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, BROOD_OF_COCKROACHES, 0);
    }

    private void brood_of_cockroaches_diesat_precombat_main_phase() {
        addCard(BATTLEFIELD, playerB, "Mountain", 1);
        addCard(HAND, playerB, SHOCK, 1);
        castSpell(TURN_1, PRECOMBAT_MAIN, playerB, SHOCK, BROOD_OF_COCKROACHES);
    }

    private void playerA_casts_Brood_of_Cockroaches_at_precombat_main_phase() {
        addCard(BATTLEFIELD, playerA, "Swamp", 2);
        addCard(HAND, playerA, BROOD_OF_COCKROACHES, 1);
        castSpell(TURN_1, PRECOMBAT_MAIN, playerA, BROOD_OF_COCKROACHES);
    }

}
