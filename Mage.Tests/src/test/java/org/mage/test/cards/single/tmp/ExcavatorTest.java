package org.mage.test.cards.single.tmp;

import mage.abilities.keyword.LandwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExcavatorTest extends CardTestPlayerBase {

    /*
    Excavator
    {2}
    Artifact

    {T}, Sacrifice a basic land: Target creature gains landwalk of each of the land types of the sacrificed land until end of turn.
    (It can’t be blocked as long as defending player controls a land of any of those types.)
     */
    public static final String excavator = "Excavator";

    /*
    Leyline of the Guildpact
    {G/W}{G/U}{B/G}{R/G}
    Enchantment

    If this card is in your opening hand, you may begin the game with it on the battlefield.

    Each nonland permanent you control is all colors.

    Lands you control are every basic land type in addition to their other types.
     */
    public static final String leylineOfTheGuildpact = "Leyline of the Guildpact";

    @Test
    @Ignore("Failing because permanent LKI does not save MageObjectAttribute values")
    public void testExcavator() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, excavator);
        addCard(Zone.BATTLEFIELD, playerA, leylineOfTheGuildpact);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        setChoice(playerA, "Island");
        addTarget(playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbilityCount(playerA, "Balduvian Bears", LandwalkAbility.class, 5);
    }
}
