package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OsteomancerAdeptTest extends CardTestPlayerBase {

    @Test
    public void cannotCountTheCreatureBeingCast() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        checkDuoPlayable(false);
    }

    @Test
    public void canExileThreeOtherCards() {
        addCard(Zone.GRAVEYARD, playerA, "Forest", 3);
        checkDuoPlayable(true);
    }

    @Test
    public void canSacrificeFood() {
        addCard(Zone.BATTLEFIELD, playerA, "Gingerbrute");
        checkDuoPlayable(true);
    }

    private void checkDuoPlayable(boolean expected) {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Osteomancer Adept");
        addCard(Zone.GRAVEYARD, playerA, "Daggerfang Duo");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Until end of turn");
        checkPlayableAbility("graveyard cast", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Daggerfang Duo", expected);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }
}
