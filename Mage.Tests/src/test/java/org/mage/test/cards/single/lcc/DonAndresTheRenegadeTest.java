package org.mage.test.cards.single.lcc;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DonAndresTheRenegadeTest extends CardTestPlayerBase {

    @Test
    public void test_FirstAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Don Andres, the Renegade");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Act of Treason");

        addCard(Zone.BATTLEFIELD, playerB, "Elvish Mystic");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Elvish Mystic");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 1, 1);
        assertAbility(playerA, "Llanowar Elves", new MenaceAbility(), false);
        assertAbility(playerA, "Llanowar Elves", DeathtouchAbility.getInstance(), false);
        assertNotSubtype("Llanowar Elves", SubType.PIRATE);

        assertPowerToughness(playerA, "Elvish Mystic", 3, 3);
        assertAbility(playerA, "Elvish Mystic", new MenaceAbility(), true);
        assertAbility(playerA, "Elvish Mystic", DeathtouchAbility.getInstance(), true);
        assertSubtype("Elvish Mystic", SubType.PIRATE);
    }

    @Test
    public void test_SecondAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Don Andres, the Renegade");
        addCard(Zone.HAND, playerA, "Dire Fleet Daredevil");

        addCard(Zone.GRAVEYARD, playerB, "Revitalize");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dire Fleet Daredevil");
        addTarget(playerA, "Revitalize");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Revitalize");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 2);
        assertTapped("Treasure Token", true);
    }
}
