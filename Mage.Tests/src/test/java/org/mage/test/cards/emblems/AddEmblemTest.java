package org.mage.test.cards.emblems;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.command.emblems.ChandraTorchOfDefianceEmblem;
import mage.game.command.emblems.ElspethSunsChampionEmblem;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AddEmblemTest extends CardTestPlayerBase {

    /**
     * Emblem
     * Creatures you control get +2/+2 and have flying.
     */
    private static final Emblem elspethSunChampionEmblem = new ElspethSunsChampionEmblem();

    /**
     * Emblem
     * Whenever you cast a spell, this emblem deals 5 damage to any target.
     */
    private static final Emblem chandraTorchOfDefianceEmblem = new ChandraTorchOfDefianceEmblem();

    @Test
    public void test_ElpethSunChampionEmblem() {
        setStrictChooseMode(true);

        addEmblem(playerA, elspethSunChampionEmblem);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2 vanilla

        setStopAt(1, PhaseStep.UPKEEP);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
        assertCommandZoneCount(playerA, elspethSunChampionEmblem.getName(), 1);
    }

    @Test
    public void test_ChandraTorchOfDefianceEmblem() {
        setStrictChooseMode(true);

        addEmblem(playerA, chandraTorchOfDefianceEmblem);
        addCard(Zone.HAND, playerA, "Memnite", 2); // 1/1 for {0} vanilla

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        addTarget(playerA, playerB); // emblem trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        addTarget(playerA, playerB); // emblem trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 2);
        assertLife(playerB, 20 - 5 - 5);
        assertCommandZoneCount(playerA, chandraTorchOfDefianceEmblem.getName(), 1);
    }
}
