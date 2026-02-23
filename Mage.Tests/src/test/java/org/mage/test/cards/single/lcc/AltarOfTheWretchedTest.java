package org.mage.test.cards.single.lcc;

import mage.abilities.Ability;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jmlundeen
 */
public class AltarOfTheWretchedTest extends CardTestPlayerBase {

    /*
    Altar of the Wretched
    {2}{B}
    Artifact
    When Altar of the Wretched enters the battlefield, you may sacrifice a nontoken creature. If you do, draw X cards, then mill X cards, where X is that creature's power.
    Craft with one or more creatures {2}{B}{B}
    {2}{B}: Return Altar of the Wretched from your graveyard to your hand.

    Wretched Bonemass
    Color Indicator: Black
    Creature — Skeleton Horror
    Wretched Bonemass’s power and toughness are each equal to the total power of the exiled cards used to craft it.
    This creature has flying as long as an exiled card used to craft it has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance.
    0/0
    */
    private static final String altarOfTheWretched = "Altar of the Wretched";
    private static final String wretchedBoneMass = "Wretched Bonemass";

    /*
    Angel of Invention
    {3}{W}{W}
    Creature - Angel
    Flying, vigilance, lifelink
    Fabricate 2
    Other creatures you control get +1/+1.
    2/1
    */
    private static final String angelOfInvention = "Angel of Invention";


    @Test
    public void testAltarOfTheWretched() {
        addCard(Zone.BATTLEFIELD, playerA, altarOfTheWretched);
        addCard(Zone.BATTLEFIELD, playerA, angelOfInvention);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with one or more");
        addTarget(playerA, angelOfInvention);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, wretchedBoneMass, 2, 2);
        assertExileCount(playerA, angelOfInvention, 1);
        List<Ability> abilities = new ArrayList<>();
        abilities.add(FlyingAbility.getInstance());
        abilities.add(VigilanceAbility.getInstance());
        abilities.add(LifelinkAbility.getInstance());
        assertAbilities(playerA, wretchedBoneMass, abilities);
    }
}