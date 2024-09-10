package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TenthDistrictHeroTest extends CardTestPlayerBase {

    private static final String hero = "Tenth District Hero";
    private static final String giant = "Hill Giant";
    private static final String mileva = "Mileva, the Stalwart";

    @Test
    public void testFirstAblityOnly() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.GRAVEYARD, playerA, giant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, hero, 4, 4);
        assertSubtype(hero, SubType.HUMAN);
        assertSubtype(hero, SubType.DETECTIVE);
        assertAbility(playerA, hero, VigilanceAbility.getInstance(), true);
        assertPermanentCount(playerA, mileva, 0);
    }

    @Test
    public void testSecondAblityOnly() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.GRAVEYARD, playerA, giant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, hero, 2, 3);
        assertSubtype(hero, SubType.HUMAN);
        assertNotSubtype(hero, SubType.DETECTIVE);
        assertAbility(playerA, hero, VigilanceAbility.getInstance(), false);
        assertPermanentCount(playerA, hero, 1);
        assertPermanentCount(playerA, mileva, 0);
    }

    @Test
    public void testBothAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 3);
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.GRAVEYARD, playerA, giant, 1 + 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, giant);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, mileva, 5, 5);
        assertSubtype(mileva, SubType.HUMAN);
        assertSubtype(mileva, SubType.DETECTIVE);
        assertAbility(playerA, mileva, VigilanceAbility.getInstance(), true);
        assertPermanentCount(playerA, mileva, 1);
        assertPermanentCount(playerA, hero, 0);
    }

    @Test
    public void testBothAbilitiesReversed() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 3);
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.GRAVEYARD, playerA, giant, 1 + 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}");
        setChoice(playerA, giant);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, hero, 4, 4);
        assertSubtype(hero, SubType.HUMAN);
        assertSubtype(hero, SubType.DETECTIVE);
        assertAbility(playerA, hero, VigilanceAbility.getInstance(), true);
        assertPermanentCount(playerA, mileva, 0);
    }
}
