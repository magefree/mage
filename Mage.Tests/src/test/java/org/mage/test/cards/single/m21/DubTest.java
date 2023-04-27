package org.mage.test.cards.single.m21;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DubTest extends CardTestPlayerBase {

    @Test
    public void testBoostAndAbilities(){

        // Enchanted creature gets +2/+2, has first strike, and is a Knight in addition to its other types.
        addCard(Zone.HAND, playerA, "Dub");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Scryb Sprites");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dub", "Scryb Sprites");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertAbility(playerA, "Scryb Sprites", FirstStrikeAbility.getInstance(), true);
        assertAbility(playerA, "Scryb Sprites", FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, "Scryb Sprites", 3, 3);
        assertSubtype("Scryb Sprites", SubType.KNIGHT);
        assertSubtype("Scryb Sprites", SubType.FAERIE);
    }
}
