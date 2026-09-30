package org.mage.test.cards.single.msc;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlexWilderRunawayTest extends CardTestPlayerBase {

    private static final String alex = "Alex Wilder, Runaway";
    private static final String skywayRobber = "Skyway Robber";

    @Test
    public void test_NoBonus_WhenCastFromHand() {
        addCard(Zone.HAND, playerA, alex);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alex);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, alex, 1, 3);
        assertAbility(playerA, alex, HasteAbility.getInstance(), false);
    }

    @Test
    public void test_Bonus_WhenAnotherCreatureCastFromNonHand() {
        addCard(Zone.BATTLEFIELD, playerA, alex);
        addCard(Zone.GRAVEYARD, playerA, skywayRobber);
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, skywayRobber + " with Escape");
        setChoice(playerA, "Mountain^Mountain^Mountain^Mountain^Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, skywayRobber, 5, 3);
        assertAbility(playerA, skywayRobber, HasteAbility.getInstance(), true);
    }
}
