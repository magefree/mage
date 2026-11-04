package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class MisterImmortalTest extends CardTestPlayerBase {

    // {2}{G}: Return this card from your graveyard or from exile to the battlefield tapped.
    private static final String misterImmortal = "Mister Immortal"; // 2/1
    private static final String ability = "{2}{G}: Return";

    @Test
    public void test_ReturnFromGraveyardTapped() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, misterImmortal);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, misterImmortal, 0);
        assertPermanentCount(playerA, misterImmortal, 1);
        assertTapped(misterImmortal, true);
    }

    @Test
    public void test_ReturnFromExileTapped() {
        setStrictChooseMode(true);

        addCard(Zone.EXILED, playerA, misterImmortal);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, ability);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, misterImmortal, 0);
        assertPermanentCount(playerA, misterImmortal, 1);
        assertTapped(misterImmortal, true);
    }

    @Test
    public void test_CantActivateFromBattlefieldOrHand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, misterImmortal);
        addCard(Zone.HAND, playerA, misterImmortal);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        checkPlayableAbility("battlefield and hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, ability, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
