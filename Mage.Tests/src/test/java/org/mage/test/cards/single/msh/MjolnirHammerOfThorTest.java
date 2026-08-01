package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MjolnirHammerOfThorTest extends CardTestPlayerBase {

    @Test
    public void testDoubleDamageOnlyFromEquippedCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mjolnir, Hammer of Thor");
        addCard(Zone.BATTLEFIELD, playerA, "Isamaru, Hound of Konda");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Isamaru, Hound of Konda");

        attack(1, playerA, "Isamaru, Hound of Konda");
        attack(1, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 14);
    }

    @Test
    public void testCanOnlyEquipWorthyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mjolnir, Hammer of Thor");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        checkPlayableAbility("equip requires a worthy creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip worthy", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Mjolnir, Hammer of Thor", "Grizzly Bears", false);
    }
}
